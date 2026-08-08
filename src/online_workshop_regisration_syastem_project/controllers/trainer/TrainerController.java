package online_workshop_regisration_syastem_project.controllers.trainer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import models.User;

public class TrainerController {

    @FXML private Button addWorkshopButton;
    @FXML private Button updateWorkshopButton;
    @FXML private Button removeWorkshopButton;
    @FXML private Button monitorParticipantsButton;
    @FXML private Button logoutButton;

    private User currentUser;

    @FXML
    public void initialize() {
        if (addWorkshopButton != null) {
            addWorkshopButton.setOnAction(event -> loadScreen(
                "/online_workshop_regisration_syastem_project/views/trainer/AddWorkshopScreen.fxml"));
        }
        if (updateWorkshopButton != null) {
            updateWorkshopButton.setOnAction(event -> loadScreen(
                "/online_workshop_regisration_syastem_project/views/trainer/UpdateWorkshopScreen.fxml"));
        }
        if (removeWorkshopButton != null) {
            removeWorkshopButton.setOnAction(event -> loadScreen(
                "/online_workshop_regisration_syastem_project/views/trainer/RemoveWorkshopScreen.fxml"));
        }
        if (monitorParticipantsButton != null) {
            monitorParticipantsButton.setOnAction(event -> loadScreen(
                "/online_workshop_regisration_syastem_project/views/trainer/MonitorParticipantsScreen.fxml"));
        }
        if (logoutButton != null) {
            logoutButton.setOnAction(event -> logout());
        }
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    private void loadScreen(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Object controller = loader.getController();
            if (controller instanceof AddWorkshopController) {
                ((AddWorkshopController) controller).setCurrentUser(currentUser);
            } else if (controller instanceof UpdateWorkshopController) {
                ((UpdateWorkshopController) controller).setCurrentUser(currentUser);
            } else if (controller instanceof RemoveWorkshopController) {
                ((RemoveWorkshopController) controller).setCurrentUser(currentUser);
            } else if (controller instanceof MonitorParticipantsController) {
                ((MonitorParticipantsController) controller).setCurrentUser(currentUser);
            }

            Scene scene = new Scene(root, 900, 650);
            Stage stage = (Stage) addWorkshopButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Error loading screen: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void logout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(
                "/online_workshop_regisration_syastem_project/views/auth/LoginScreen.fxml"));
            Scene scene = new Scene(root, 800, 600);
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Error during logout: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
