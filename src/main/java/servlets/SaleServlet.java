package servlets;

import daos.PaymentMethodDao;
import daos.ProfessionalDao;
import daos.SaleDao;
import daos.ServiceDao;
import dtos.SaleDto;
import models.PaymentMethod;
import models.Professional;
import models.Service;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/sales")
public class SaleServlet extends HttpServlet {
    private SaleDao saleDao;
    private ProfessionalDao professionalDao;
    private PaymentMethodDao paymentMethodDao;
    private ServiceDao serviceDao;

    @Override
    public void init() {
        saleDao = new SaleDao();
        professionalDao = new ProfessionalDao();
        paymentMethodDao = new PaymentMethodDao();
        serviceDao = new ServiceDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null) {
                listSales(request, response);
            } else if ("new".equals(action)) {
                showNewForm(request, response);
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
                insertSale(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listSales(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<SaleDto> listSale = saleDao.findAll();

        request.setAttribute("listSale", listSale);
        request.getRequestDispatcher("sale-list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Professional> professionals = professionalDao.findAll()
                .stream()
                .filter(professional -> professional.isActive())
                .collect(Collectors.toList());
        List<PaymentMethod> paymentMethods = paymentMethodDao.findAll()
                .stream()
                .filter(method -> method.isActive())
                .collect(Collectors.toList());
        List<Service> services = serviceDao.findAll()
                .stream()
                .filter(service -> service.isActive())
                .collect(Collectors.toList());

        request.setAttribute("professionals", professionals);
        request.setAttribute("paymentMethods", paymentMethods);
        request.setAttribute("services", services);
        request.getRequestDispatcher("sale-form.jsp").forward(request, response);
    }

    private void insertSale(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String professionalDocument = request.getParameter("professionalDocument");
        String paymentMethodId = request.getParameter("paymentMethodId");
        String[] serviceIds = request.getParameterValues("serviceIds");
        double total = Double.parseDouble(request.getParameter("total"));
        Date soldAt = new Date();

        Professional professional = professionalDao.findByDocument(professionalDocument);
        PaymentMethod paymentMethod = paymentMethodDao.findById(paymentMethodId);
        List<Service> services = serviceDao.findServicesByIds(serviceIds);

        SaleDto sale = new SaleDto(professional, paymentMethod, services, total, soldAt);

        try
        {
            saleDao.createSaleWithServices(sale);
            request.getSession().setAttribute("toastMessage", "Venda cadastrada com sucesso!");
            request.getSession().setAttribute("toastType", "success");
        }
        catch(Exception e)
        {
            request.getSession().setAttribute("toastMessage", "Erro ao cadastrar venda.");
            request.getSession().setAttribute("toastType", "error");
        }

        response.sendRedirect("sales");
    }
}