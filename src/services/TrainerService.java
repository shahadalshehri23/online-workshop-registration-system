package services;

import models.Workshop;
import models.User;
import exceptions.InvalidCredentialsException;
import java.util.List;

public interface TrainerService {
    boolean addWorkshop(Workshop workshop) throws InvalidCredentialsException;
    boolean updateWorkshop(Workshop workshop) throws InvalidCredentialsException;
    boolean removeWorkshop(int workshopId);
    List<User> viewRegisteredParticipants(int workshopId);
    List<Workshop> getTrainerWorkshops(int trainerId);
}
