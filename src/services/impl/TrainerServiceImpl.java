package services.impl;

import services.TrainerService;
import models.Workshop;
import models.User;
import dao.WorkshopDAO;
import dao.RegistrationDAO;
import exceptions.InvalidCredentialsException;
import java.util.List;

public class TrainerServiceImpl implements TrainerService {

    private WorkshopDAO workshopDAO = new WorkshopDAO();
    private RegistrationDAO registrationDAO = new RegistrationDAO();

    @Override
    public boolean addWorkshop(Workshop workshop) throws InvalidCredentialsException {
        if (workshop.getTitle() == null || workshop.getTitle().isEmpty()) {
            throw new InvalidCredentialsException("Workshop title cannot be empty!");
        }
        if (workshop.getCapacity() <= 0) {
            throw new InvalidCredentialsException("Workshop capacity must be greater than 0!");
        }
        if (workshop.getDescription() == null || workshop.getDescription().isEmpty()) {
            throw new InvalidCredentialsException("Workshop description cannot be empty!");
        }
        workshop.setRegisteredCount(0);
        return workshopDAO.addWorkshop(workshop);
    }

    @Override
    public boolean updateWorkshop(Workshop workshop) throws InvalidCredentialsException {
        Workshop existing = workshopDAO.getWorkshopById(workshop.getWorkshopId());
        if (existing == null) {
            throw new InvalidCredentialsException("Workshop not found!");
        }
        if (workshop.getTitle() == null || workshop.getTitle().isEmpty()) {
            throw new InvalidCredentialsException("Workshop title cannot be empty!");
        }
        if (workshop.getCapacity() <= 0) {
            throw new InvalidCredentialsException("Workshop capacity must be greater than 0!");
        }
        return workshopDAO.updateWorkshop(workshop);
    }

    @Override
    public boolean removeWorkshop(int workshopId) {
        Workshop workshop = workshopDAO.getWorkshopById(workshopId);
        if (workshop == null) {
            return false;
        }
        return workshopDAO.deleteWorkshop(workshopId);
    }

    @Override
    public List<User> viewRegisteredParticipants(int workshopId) {
        return registrationDAO.getWorkshopParticipants(workshopId);
    }

    @Override
    public List<Workshop> getTrainerWorkshops(int trainerId) {
        return workshopDAO.getWorkshopsByTrainerId(trainerId);
    }
}
