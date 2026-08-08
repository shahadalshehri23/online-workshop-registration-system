package services.impl;

import services.ParticipantService;
import models.Workshop;
import models.User;
import dao.WorkshopDAO;
import dao.RegistrationDAO;
import dao.UserDAO;
import exceptions.WorkshopFullException;
import exceptions.UserNotFoundException;
import models.Registration;
import java.util.Date;
import java.util.List;

public class ParticipantServiceImpl implements ParticipantService {

    private WorkshopDAO workshopDAO = new WorkshopDAO();
    private RegistrationDAO registrationDAO = new RegistrationDAO();
    private UserDAO userDAO = new UserDAO();

    @Override
    public List<Workshop> viewAvailableWorkshops() {
        return workshopDAO.getAvailableWorkshops();
    }

    @Override
    public boolean registerForWorkshop(int userId, int workshopId) throws WorkshopFullException, UserNotFoundException {
        User user = userDAO.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
        Workshop workshop = workshopDAO.getWorkshopById(workshopId);
        if (workshop == null) {
            throw new UserNotFoundException("Workshop not found with ID: " + workshopId);
        }
        if (workshop.getRegisteredCount() >= workshop.getCapacity()) {
            throw new WorkshopFullException("Workshop is full! No more registrations allowed.");
        }
        if (registrationDAO.isUserRegistered(userId, workshopId)) {
            throw new WorkshopFullException("User is already registered for this workshop!");
        }
        Registration registration = new Registration();
        registration.setUserId(userId);
        registration.setWorkshopId(workshopId);
        registration.setRegistrationDate(new Date());
        registration.setStatus("Active");
        boolean registered = registrationDAO.addRegistration(registration);
        if (registered) {
            workshop.setRegisteredCount(workshop.getRegisteredCount() + 1);
            workshopDAO.updateWorkshop(workshop);
        }
        return registered;
    }

    @Override
    public boolean dropWorkshop(int userId, int workshopId) throws UserNotFoundException {
        User user = userDAO.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
        Registration registration = registrationDAO.getRegistrationByUserAndWorkshop(userId, workshopId);
        if (registration == null) {
            return false;
        }
        boolean dropped = registrationDAO.dropRegistration(registration.getRegistrationId());
        if (dropped) {
            Workshop workshop = workshopDAO.getWorkshopById(workshopId);
            if (workshop != null) {
                workshop.setRegisteredCount(workshop.getRegisteredCount() - 1);
                workshopDAO.updateWorkshop(workshop);
            }
        }
        return dropped;
    }

    @Override
    public List<Workshop> viewMyWorkshops(int userId) throws UserNotFoundException {
        User user = userDAO.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
        return registrationDAO.getUserWorkshops(userId);
    }
}
