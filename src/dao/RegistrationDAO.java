package dao;

import models.Registration;
import models.Workshop;
import models.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistrationDAO {

    public boolean addRegistration(Registration registration) {
        String sql = "INSERT INTO registrations (user_id, workshop_id, registration_date, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, registration.getUserId());
            pstmt.setInt(2, registration.getWorkshopId());
            pstmt.setDate(3, new java.sql.Date(registration.getRegistrationDate().getTime()));
            pstmt.setString(4, registration.getStatus());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error adding registration: " + e.getMessage());
            return false;
        }
    }

    public Registration getRegistrationById(int registrationId) {
        String sql = "SELECT * FROM registrations WHERE registration_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, registrationId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Registration registration = new Registration();
                registration.setRegistrationId(rs.getInt("registration_id"));
                registration.setUserId(rs.getInt("user_id"));
                registration.setWorkshopId(rs.getInt("workshop_id"));
                registration.setRegistrationDate(rs.getDate("registration_date"));
                registration.setStatus(rs.getString("status"));
                return registration;
            }
        } catch (SQLException e) {
            System.out.println("Error getting registration by ID: " + e.getMessage());
        }
        return null;
    }

    public boolean dropRegistration(int registrationId) {
        String sql = "UPDATE registrations SET status = 'Dropped' WHERE registration_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, registrationId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error dropping registration: " + e.getMessage());
            return false;
        }
    }

    public List<Workshop> getUserWorkshops(int userId) {
        List<Workshop> workshops = new ArrayList<>();
        String sql = "SELECT w.* FROM workshops w " +
                     "JOIN registrations r ON w.workshop_id = r.workshop_id " +
                     "WHERE r.user_id = ? AND r.status = 'Active'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
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
            System.out.println("Error getting user workshops: " + e.getMessage());
        }
        return workshops;
    }

    public List<User> getWorkshopParticipants(int workshopId) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.* FROM users u " +
                     "JOIN registrations r ON u.user_id = r.user_id " +
                     "WHERE r.workshop_id = ? AND r.status = 'Active'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, workshopId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error getting workshop participants: " + e.getMessage());
        }
        return users;
    }

    public boolean isUserRegistered(int userId, int workshopId) {
        String sql = "SELECT COUNT(*) FROM registrations WHERE user_id = ? AND workshop_id = ? AND status = 'Active'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, workshopId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error checking registration: " + e.getMessage());
        }
        return false;
    }

    public Registration getRegistrationByUserAndWorkshop(int userId, int workshopId) {
        String sql = "SELECT * FROM registrations WHERE user_id = ? AND workshop_id = ? AND status = 'Active'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, workshopId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Registration registration = new Registration();
                registration.setRegistrationId(rs.getInt("registration_id"));
                registration.setUserId(rs.getInt("user_id"));
                registration.setWorkshopId(rs.getInt("workshop_id"));
                registration.setRegistrationDate(rs.getDate("registration_date"));
                registration.setStatus(rs.getString("status"));
                return registration;
            }
        } catch (SQLException e) {
            System.out.println("Error getting registration: " + e.getMessage());
        }
        return null;
    }

    public List<Registration> getAllRegistrations() {
        List<Registration> registrations = new ArrayList<>();
        String sql = "SELECT * FROM registrations";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Registration registration = new Registration();
                registration.setRegistrationId(rs.getInt("registration_id"));
                registration.setUserId(rs.getInt("user_id"));
                registration.setWorkshopId(rs.getInt("workshop_id"));
                registration.setRegistrationDate(rs.getDate("registration_date"));
                registration.setStatus(rs.getString("status"));
                registrations.add(registration);
            }
        } catch (SQLException e) {
            System.out.println("Error getting all registrations: " + e.getMessage());
        }
        return registrations;
    }
}
