package daos;

import database.DatabaseConnection;
import models.Professional;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessionalDao {
    public List<Professional> findAll() throws SQLException {
        List<Professional> professionals = new ArrayList<>();
        String sql = "SELECT * FROM professional";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                Professional professional = new Professional();

                professional.setId(rs.getString("id"));
                professional.setDocument(rs.getString("document"));
                professional.setName(rs.getString("name"));
                professional.setIsActive(rs.getBoolean("is_active"));
                professional.setCreatedAt(rs.getDate("created_at"));

                professionals.add(professional);
            }
        }
        return professionals;
    }

    public Professional findById(String id) throws SQLException {
        String sql = "SELECT * FROM professional WHERE id = ?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Professional professional = new Professional();
                    professional.setId(rs.getString("id"));
                    professional.setDocument(rs.getString("document"));
                    professional.setName(rs.getString("name"));
                    professional.setIsActive(rs.getBoolean("is_active"));
                    professional.setCreatedAt(rs.getDate("created_at"));

                    return professional;
                }
            }
        }

        return null;
    }

    public Professional findByDocument(String document) throws SQLException {
        String sql = "SELECT * FROM professional WHERE document = ?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, document);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Professional professional = new Professional();

                    professional.setId(rs.getString("id"));
                    professional.setDocument(rs.getString("document"));
                    professional.setName(rs.getString("name"));
                    professional.setIsActive(rs.getBoolean("is_active"));
                    professional.setCreatedAt(rs.getDate("created_at"));

                    return professional;
                }
            }
        }

        return null;
    }

    public void create(Professional professional) throws SQLException {
        String sql = "INSERT INTO professional (document, name, is_active, created_at) VALUES (?, ?, ?, ?)";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, professional.getDocument());
            stmt.setString(2, professional.getName());
            stmt.setBoolean(3, professional.isActive());
            stmt.setDate(4, new Date(professional.getCreatedAt().getTime()));

            stmt.executeUpdate();
        }
    }

    public void update(Professional professional) throws SQLException {
        String sql = "UPDATE professional SET document = ?, name = ?, is_active = ? WHERE id = ?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, professional.getDocument());
            stmt.setString(2, professional.getName());
            stmt.setBoolean(3, professional.isActive());
            stmt.setString(4, professional.getId());

            stmt.executeUpdate();
        }
    }

    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM professional WHERE id = ?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, id);

            stmt.executeUpdate();
        }
    }
}