package servlets;

import daos.ServiceDao;
import models.Service;

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

@WebServlet("/services")
public class ServiceServlet extends HttpServlet {
    private ServiceDao serviceDao;

    @Override
    public void init() {
        serviceDao = new ServiceDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null) {
                listServices(request, response);
            } else {
                switch (action) {
                    case "new":
                        showNewForm(request, response);
                        break;
                    case "edit":
                        showEditForm(request, response);
                        break;
                    case "delete":
                        deleteService(request, response);
                        break;
                    default:
                        listServices(request, response);
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
                insertService(request, response);
            } else if ("update".equals(action)) {
                updateService(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listServices(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Service> listService = serviceDao.findAll();

        if (listService == null) {
            listService = new ArrayList<>();
        }

        request.setAttribute("listService", listService);
        request.getRequestDispatcher("service-list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("service-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String id = request.getParameter("id");
        Service existingService = serviceDao.findById(id);

        request.setAttribute("service", existingService);
        request.getRequestDispatcher("service-form.jsp").forward(request, response);
    }

    private void insertService(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        boolean isActive = "true".equals(request.getParameter("isActive"));
        Date createdAt = new Date();

        Service newService = new Service();
        newService.setName(name);
        newService.setPrice(price);
        newService.setIsActive(isActive);
        newService.setCreatedAt(createdAt);

        serviceDao.create(newService);
        response.sendRedirect("services");
    }

    private void updateService(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        boolean isActive = "true".equals(request.getParameter("isActive"));
        Date createdAt = new Date();

        Service updatedService = new Service();

        updatedService.setId(id);
        updatedService.setName(name);
        updatedService.setPrice(price);
        updatedService.setCreatedAt(createdAt);
        updatedService.setIsActive(isActive);

        serviceDao.update(updatedService);
        response.sendRedirect("services");
    }

    private void deleteService(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String id = request.getParameter("id");

        serviceDao.delete(id);
        response.sendRedirect("services");
    }
}