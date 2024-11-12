package daos;

import database.DatabaseConnection;
import dtos.SaleDto;
import models.PaymentMethod;
import models.Professional;
import models.Sale;
import models.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SaleDao {
    public List<SaleDto> findAll() throws SQLException {
        List<SaleDto> sales = new ArrayList<>();
        String saleSql = "SELECT s.id, s.total, s.sold_at, p.document, p.name, pm.id AS payment_method_id, pm.name AS payment_method_name " +
                "FROM sale s " +
                "JOIN professional p ON s.professional_document = p.document " +
                "JOIN payment_method pm ON s.payment_method_id = pm.id";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(saleSql);
            ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                Professional professional = new Professional();
                professional.setDocument(rs.getString("document"));
                professional.setName(rs.getString("name"));

                PaymentMethod paymentMethod = new PaymentMethod();
                paymentMethod.setId(rs.getString("payment_method_id"));
                paymentMethod.setName(rs.getString("payment_method_name"));

                SaleDto sale = new SaleDto(
                        professional,
                        paymentMethod,
                        findServicesBySaleId(rs.getString("id")),
                        rs.getDouble("total"),
                        rs.getDate("sold_at")
                );
                sale.setId(rs.getString("id"));

                sales.add(sale);
            }
        }

        return sales;
    }

    private List<Service> findServicesBySaleId(String saleId) throws SQLException {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT s.id, s.name, s.price, s.is_active, s.created_at " +
                "FROM service s " +
                "JOIN sale_service ss ON s.id = ss.service_id " +
                "WHERE ss.sale_id = ?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, saleId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Service service = new Service();

                    service.setId(rs.getString("id"));
                    service.setName(rs.getString("name"));
                    service.setPrice(rs.getDouble("price"));
                    service.setIsActive(rs.getBoolean("is_active"));
                    service.setCreatedAt(rs.getDate("created_at"));

                    services.add(service);
                }
            }
        }

        return services;
    }

    public Sale findById(String id) throws SQLException {
        String sql = "SELECT * FROM sale WHERE id = ?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Sale sale = new Sale();

                    sale.setId(rs.getString("id"));
                    sale.setProfessionalDocument(rs.getString("professional_document"));
                    sale.setPaymentMethodId(rs.getString("payment_method_id"));
                    sale.setTotal(rs.getDouble("total"));
                    sale.setSoldAt(rs.getDate("sold_at"));

                    return sale;
                }
            }
        }

        return null;
    }

    public void createSaleWithServices(SaleDto sale) throws SQLException {
        String saleSql = "INSERT INTO sale (id, professional_document, payment_method_id, total, sold_at) VALUES (?, ?, ?, ?, ?)";
        String saleServiceSql = "INSERT INTO sale_service (sale_id, service_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            String saleId = UUID.randomUUID().toString();

            try (PreparedStatement saleStmt = conn.prepareStatement(saleSql, Statement.RETURN_GENERATED_KEYS)) {
                saleStmt.setString(1, saleId);
                saleStmt.setString(2, sale.getProfessional().getDocument());
                saleStmt.setString(3, sale.getPaymentMethod().getId());
                saleStmt.setDouble(4, sale.getTotal());
                saleStmt.setDate(5, new java.sql.Date(sale.getSoldAt().getTime()));

                saleStmt.executeUpdate();
            }

            try (PreparedStatement saleServiceStmt = conn.prepareStatement(saleServiceSql)) {
                for (Service service : sale.getServices()) {
                    saleServiceStmt.setString(1, saleId);
                    saleServiceStmt.setString(2, service.getId());
                    saleServiceStmt.addBatch();
                }

                saleServiceStmt.executeBatch();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void update(Sale sale) throws SQLException {
        String sql = "UPDATE sale SET professional_document = ?, payment_method_id = ?, total = ?, sold_at = ? WHERE id = ?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, sale.getProfessionalDocument());
            stmt.setString(2, sale.getPaymentMethodId());
            stmt.setDouble(3, sale.getTotal());
            stmt.setDate(4, new Date(sale.getSoldAt().getTime()));
            stmt.setString(5, sale.getId());

            stmt.executeUpdate();
        }
    }

    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM sale WHERE id = ?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, id);

            stmt.executeUpdate();
        }
    }
}