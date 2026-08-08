package services.impl;

import services.AdminService;
import models.User;
import models.Workshop;
import dao.UserDAO;
import dao.WorkshopDAO;
import exceptions.InvalidCredentialsException;
import java.util.List;

public class AdminServiceImpl implements AdminService {

    private UserDAO userDAO = new UserDAO();
    private WorkshopDAO workshopDAO = new WorkshopDAO();

    @Override
    public boolean addUser(User user) throws InvalidCredentialsException {
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new InvalidCredentialsException("User name cannot be empty!");
        }
        if (!isValidEmail(user.getEmail())) {
            throw new InvalidCredentialsException("Invalid email format!");
        }
        if (user.getPassword() == null || user.getPassword().length() < 4) {
            throw new InvalidCredentialsException("Password must be at least 4 characters!");
        }
        if (user.getRole() == null || user.getRole().isEmpty()) {
            throw new InvalidCredentialsException("User role cannot be empty!");
        }
        User existingUser = userDAO.getUserByEmail(user.getEmail());
        if (existingUser != null) {
            throw new InvalidCredentialsException("Email already exists!");
        }
        return userDAO.addUser(user);
    }

    @Override
    public boolean updateUser(User user) throws InvalidCredentialsException {
        User existing = userDAO.getUserById(user.getUserId());
        if (existing == null) {
            throw new InvalidCredentialsException("User not found!");
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new InvalidCredentialsException("User name cannot be empty!");
        }
        if (!isValidEmail(user.getEmail())) {
            throw new InvalidCredentialsException("Invalid email format!");
        }
        if (user.getPassword() == null || user.getPassword().length() < 4) {
            throw new InvalidCredentialsException("Password must be at least 4 characters!");
        }
        return userDAO.updateUser(user);
    }

    @Override
    public boolean removeUser(int userId) {
        User user = userDAO.getUserById(userId);
        if (user == null) {
            return false;
        }
        return userDAO.deleteUser(userId);
    }

    @Override
    public List<Workshop> viewAllWorkshops() {
        return workshopDAO.getAllWorkshops();
    }

    @Override
    public List<User> viewAllUsers() {
        return userDAO.getAllUsers();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email != null && email.matches(emailRegex);
    }
}
