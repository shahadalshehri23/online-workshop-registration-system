package services;

import models.User;
import models.Workshop;
import exceptions.InvalidCredentialsException;
import java.util.List;

public interface AdminService {
    boolean addUser(User user) throws InvalidCredentialsException;
    boolean updateUser(User user) throws InvalidCredentialsException;
    boolean removeUser(int userId);
    List<Workshop> viewAllWorkshops();
    List<User> viewAllUsers();
}
