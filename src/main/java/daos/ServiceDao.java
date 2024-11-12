package daos;

import database.DatabaseConnection;
import models.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDao {
    public List<Service> findAll() throws SQLException {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT * FROM service";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {
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

        return services;
    }

    public Service findById(String id) throws SQLException {
        String sql = "SELECT * FROM service WHERE id = ?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Service service = new Service();

                    service.setId(rs.getString("id"));
                    service.setName(rs.getString("name"));
                    service.setPrice(rs.getDouble("price"));
                    service.setIsActive(rs.getBoolean("is_active"));
                    service.setCreatedAt(rs.getDate("created_at"));

                    return service;
                }
            }
        }

        return null;
    }

    public List<Service> findServicesByIds(String[] serviceIds) throws SQLException {
        List<Service> services = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM service WHERE id IN (");

        for (int i = 0; i < serviceIds.length; i++) {
            sql.append("?");

            if (i < serviceIds.length - 1) {
                sql.append(",");
            }
        }

        sql.append(")");

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < serviceIds.length; i++) {
                stmt.setString(i + 1, serviceIds[i]);
            }

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

    public void create(Service service) throws SQLException {
        String sql = "INSERT INTO service (name, price, is_active, created_at) VALUES (?, ?, ?, ?)";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, service.getName());
            stmt.setDouble(2, service.getPrice());
            stmt.setBoolean(3, service.isActive());
            stmt.setDate(4, new Date(service.getCreatedAt().getTime()));

            stmt.executeUpdate();
        }
    }

    public void update(Service service) throws SQLException {
        String sql = "UPDATE service SET name = ?, price = ?, is_active = ? WHERE id = ?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, service.getName());
            stmt.setDouble(2, service.getPrice());
            stmt.setBoolean(3, service.isActive());
            stmt.setString(4, service.getId());

            stmt.executeUpdate();
        }
    }

    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM service WHERE id = ?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, id);

            stmt.executeUpdate();
        }
    }
}
