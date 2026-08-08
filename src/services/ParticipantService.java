package services;

import models.Workshop;
import exceptions.WorkshopFullException;
import exceptions.UserNotFoundException;
import java.util.List;

public interface ParticipantService {
    List<Workshop> viewAvailableWorkshops();
    boolean registerForWorkshop(int userId, int workshopId) throws WorkshopFullException, UserNotFoundException;
    boolean dropWorkshop(int userId, int workshopId) throws UserNotFoundException;
    List<Workshop> viewMyWorkshops(int userId) throws UserNotFoundException;
}
