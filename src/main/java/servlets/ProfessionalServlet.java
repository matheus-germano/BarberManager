package servlets;

import daos.ProfessionalDao;
import models.Professional;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/professionals")
public class ProfessionalServlet extends HttpServlet {
    private ProfessionalDao professionalDao;

    @Override
    public void init() {
        professionalDao = new ProfessionalDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null) {
                listProfessionals(request, response);
            } else {
                switch (action) {
                    case "new":
                        showNewForm(request, response);
                        break;
                    case "edit":
                        showEditForm(request, response);
                        break;
                    case "delete":
                        deleteProfessional(request, response);
                        break;
                    default:
                        listProfessionals(request, response);
                        break;
                }
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("insert".equals(action)) {
                insertProfessional(request, response);
            } else if ("update".equals(action)) {
                updateProfessional(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listProfessionals(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Professional> listProfessional = professionalDao.findAll();

        if (listProfessional == null) {
            listProfessional = new ArrayList<>();
        }

        request.setAttribute("listProfessional", listProfessional);
        request.getRequestDispatcher("professional-list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("professional-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String id = request.getParameter("id");
        Professional existingProfessional = professionalDao.findById(id);

        request.setAttribute("professional", existingProfessional);
        request.getRequestDispatcher("professional-form.jsp").forward(request, response);
    }

    private void insertProfessional(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String document = request.getParameter("document");
        String name = request.getParameter("name");
        boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));
        Date createdAt = new Date();

        Professional newProfessional = new Professional();
        newProfessional.setDocument(document);
        newProfessional.setName(name);
        newProfessional.setIsActive(isActive);
        newProfessional.setCreatedAt(createdAt);

        professionalDao.create(newProfessional);
        response.sendRedirect("professionals");
    }

    private void updateProfessional(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String id = request.getParameter("id");
        String document = request.getParameter("document");
        String name = request.getParameter("name");
        boolean isActive = "true".equals(request.getParameter("isActive"));

        Professional professional = new Professional();
        professional.setId(id);
        professional.setDocument(document);
        professional.setName(name);
        professional.setIsActive(isActive);

        professionalDao.update(professional);
        response.sendRedirect("professionals");
    }

    private void deleteProfessional(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String id = request.getParameter("id");

        professionalDao.delete(id);
        response.sendRedirect("professionals");
    }
}