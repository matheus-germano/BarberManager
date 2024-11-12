package servlets;

import daos.PaymentMethodDao;
import models.PaymentMethod;

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

@WebServlet("/payment-methods")
public class PaymentMethodServlet extends HttpServlet {
    private PaymentMethodDao paymentMethodDao;

    @Override
    public void init() {
        paymentMethodDao = new PaymentMethodDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null) {
                listPaymentMethods(request, response);
            } else {
                switch (action) {
                    case "new":
                        showNewForm(request, response);
                        break;
                    case "edit":
                        showEditForm(request, response);
                        break;
                    case "delete":
                        deletePaymentMethod(request, response);
                        break;
                    default:
                        listPaymentMethods(request, response);
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
                insertPaymentMethod(request, response);
            } else if ("update".equals(action)) {
                updatePaymentMethod(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listPaymentMethods(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<PaymentMethod> listPaymentMethod = paymentMethodDao.findAll();

        if (listPaymentMethod == null) {
            listPaymentMethod = new ArrayList<>();
        }

        request.setAttribute("listPaymentMethod", listPaymentMethod);
        request.getRequestDispatcher("payment-method-list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("payment-method-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String id = request.getParameter("id");
        PaymentMethod existingPaymentMethod = paymentMethodDao.findById(id);

        request.setAttribute("paymentMethod", existingPaymentMethod);
        request.getRequestDispatcher("payment-method-form.jsp").forward(request, response);
    }

    private void insertPaymentMethod(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String name = request.getParameter("name");
        boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));
        Date createdAt = new Date();

        PaymentMethod newPaymentMethod = new PaymentMethod();
        newPaymentMethod.setName(name);
        newPaymentMethod.setIsActive(isActive);
        newPaymentMethod.setCreatedAt(createdAt);

        try
        {
            paymentMethodDao.create(newPaymentMethod);
            request.getSession().setAttribute("toastMessage", "Método de pagamento cadastrado com sucesso!");
            request.getSession().setAttribute("toastType", "success");
        }
        catch(Exception e)
        {
            request.getSession().setAttribute("toastMessage", "Erro ao criar cadastrar de pagamento.");
            request.getSession().setAttribute("toastType", "error");
        }

        response.sendRedirect("payment-methods");
    }

    private void updatePaymentMethod(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));

        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setId(id);
        paymentMethod.setName(name);
        paymentMethod.setIsActive(isActive);

        try
        {
            paymentMethodDao.update(paymentMethod);
            request.getSession().setAttribute("toastMessage", "Método de pagamento atualizado com sucesso!");
            request.getSession().setAttribute("toastType", "success");
        }
        catch(Exception e)
        {
            request.getSession().setAttribute("toastMessage", "Erro ao atualizar método de pagamento.");
            request.getSession().setAttribute("toastType", "error");
        }

        response.sendRedirect("payment-methods");
    }

    private void deletePaymentMethod(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String id = request.getParameter("id");

        try
        {
            paymentMethodDao.delete(id);
            request.getSession().setAttribute("toastMessage", "Método de pagamento deletado com sucesso!");
            request.getSession().setAttribute("toastType", "success");
        }
        catch(Exception e)
        {
            request.getSession().setAttribute("toastMessage", "Erro ao deletar método de pagamento.");
            request.getSession().setAttribute("toastType", "error");
        }

        response.sendRedirect("payment-methods");
    }
}