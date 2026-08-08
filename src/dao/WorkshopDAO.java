package dao;

import models.Workshop;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkshopDAO {

    public boolean addWorkshop(Workshop workshop) {
        String sql = "INSERT INTO workshops (title, description, capacity, registered_count, trainer_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, workshop.getTitle());
            pstmt.setString(2, workshop.getDescription());
            pstmt.setInt(3, workshop.getCapacity());
            pstmt.setInt(4, workshop.getRegisteredCount());
            pstmt.setInt(5, workshop.getTrainerId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error adding workshop: " + e.getMessage());
            return false;
        }
    }

    public Workshop getWorkshopById(int workshopId) {
        String sql = "SELECT * FROM workshops WHERE workshop_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, workshopId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Workshop workshop = new Workshop();
                workshop.setWorkshopId(rs.getInt("workshop_id"));
                workshop.setTitle(rs.getString("title"));
                workshop.setDescription(rs.getString("description"));
                workshop.setCapacity(rs.getInt("capacity"));
                workshop.setRegisteredCount(rs.getInt("registered_count"));
                workshop.setTrainerId(rs.getInt("trainer_id"));
                return workshop;
            }
        } catch (SQLException e) {
            System.out.println("Error getting workshop by ID: " + e.getMessage());
        }
        return null;
    }

    public boolean updateWorkshop(Workshop workshop) {
        String sql = "UPDATE workshops SET title = ?, description = ?, capacity = ?, registered_count = ? WHERE workshop_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, workshop.getTitle());
            pstmt.setString(2, workshop.getDescription());
            pstmt.setInt(3, workshop.getCapacity());
            pstmt.setInt(4, workshop.getRegisteredCount());
            pstmt.setInt(5, workshop.getWorkshopId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating workshop: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteWorkshop(int workshopId) {
        String sql = "DELETE FROM workshops WHERE workshop_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, workshopId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting workshop: " + e.getMessage());
            return false;
        }
    }

    public List<Workshop> getAllWorkshops() {
        List<Workshop> workshops = new ArrayList<>();
        String sql = "SELECT * FROM workshops";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Workshop workshop = new Workshop();
                workshop.setWorkshopId(rs.getInt("workshop_id"));
                workshop.setTitle(rs.getString("title"));
                workshop.setDescription(rs.getString("description"));
                workshop.setCapacity(rs.getInt("capacity"));
                workshop.setRegisteredCount(rs.getInt("registered_count"));
                workshop.setTrainerId(rs.getInt("trainer_id"));
                workshops.add(workshop);
            }
        } catch (SQLException e) {
            System.out.println("Error getting all workshops: " + e.getMessage());
        }
        return workshops;
    }

    public List<Workshop> getWorkshopsByTrainerId(int trainerId) {
        List<Workshop> workshops = new ArrayList<>();
        String sql = "SELECT * FROM workshops WHERE trainer_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, trainerId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Workshop workshop = new Workshop();
                workshop.setWorkshopId(rs.getInt("workshop_id"));
                workshop.setTitle(rs.getString("title"));
                workshop.setDescription(rs.getString("description"));
                workshop.setCapacity(rs.getInt("capacity"));
                workshop.setRegisteredCount(rs.getInt("registered_count"));
                workshop.setTrainerId(rs.getInt("trainer_id"));
                workshops.add(workshop);
            }
        } catch (SQLException e) {
            System.out.println("Error getting workshops by trainer: " + e.getMessage());
        }
        return workshops;
    }

    public List<Workshop> getAvailableWorkshops() {
        List<Workshop> workshops = new ArrayList<>();
        String sql = "SELECT * FROM workshops WHERE registered_count < capacity";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Workshop workshop = new Workshop();
                workshop.setWorkshopId(rs.getInt("workshop_id"));
                workshop.setTitle(rs.getString("title"));
                workshop.setDescription(rs.getString("description"));
                workshop.setCapacity(rs.getInt("capacity"));
                workshop.setRegisteredCount(rs.getInt("registered_count"));
                workshop.setTrainerId(rs.getInt("trainer_id"));
                workshops.add(workshop);
            }
        } catch (SQLException e) {
            System.out.println("Error getting available workshops: " + e.getMessage());
        }
        return workshops;
    }
}
