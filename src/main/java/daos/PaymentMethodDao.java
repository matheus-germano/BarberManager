package daos;

import database.DatabaseConnection;
import models.PaymentMethod;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentMethodDao {
    public List<PaymentMethod> findAll() throws SQLException {
        List<PaymentMethod> paymentMethods = new ArrayList<>();
        String sql = "SELECT * FROM payment_method";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                PaymentMethod paymentMethod = new PaymentMethod();

                paymentMethod.setId(rs.getString("id"));
                paymentMethod.setName(rs.getString("name"));
                paymentMethod.setIsActive(rs.getBoolean("is_active"));
                paymentMethod.setCreatedAt(rs.getDate("created_at"));

                paymentMethods.add(paymentMethod);
            }
        }

        return paymentMethods;
    }

    public PaymentMethod findById(String id) throws SQLException {
        String sql = "SELECT * FROM payment_method WHERE id = ?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    PaymentMethod paymentMethod = new PaymentMethod();

                    paymentMethod.setId(rs.getString("id"));
                    paymentMethod.setName(rs.getString("name"));
                    paymentMethod.setIsActive(rs.getBoolean("is_active"));
                    paymentMethod.setCreatedAt(rs.getDate("created_at"));

                    return paymentMethod;
                }
            }
        }

        return null;
    }

    public void create(PaymentMethod paymentMethod) throws SQLException {
        String sql = "INSERT INTO payment_method (name, is_active, created_at) VALUES (?, ?, ?)";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, paymentMethod.getName());
            stmt.setBoolean(2, paymentMethod.isActive());
            stmt.setDate(3, new Date(paymentMethod.getCreatedAt().getTime()));

            stmt.executeUpdate();
        }
    }

    public void update(PaymentMethod paymentMethod) throws SQLException {
        String sql = "UPDATE payment_method SET name = ?, is_active = ? WHERE id = ?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, paymentMethod.getName());
            stmt.setBoolean(2, paymentMethod.isActive());
            stmt.setString(3, paymentMethod.getId());

            stmt.executeUpdate();
        }
    }

    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM payment_method WHERE id = ?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, id);

            stmt.executeUpdate();
        }
    }
}