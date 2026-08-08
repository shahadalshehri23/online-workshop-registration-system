package online_workshop_regisration_syastem_project.controllers.participant;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import models.User;

public class ParticipantController {

    @FXML private Button browseWorkshopsButton;
    @FXML private Button myWorkshopsButton;
    @FXML private Button logoutButton;

    private User currentUser;

    @FXML
    public void initialize() {
        if (browseWorkshopsButton != null) {
            browseWorkshopsButton.setOnAction(event -> loadScreen(
                "/online_workshop_regisration_syastem_project/views/participant/BrowseWorkshopsScreen.fxml"));
        }
        if (myWorkshopsButton != null) {
            myWorkshopsButton.setOnAction(event -> loadScreen(
                "/online_workshop_regisration_syastem_project/views/participant/MyWorkshopsScreen.fxml"));
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
            if (controller instanceof BrowseWorkshopsController) {
                ((BrowseWorkshopsController) controller).setCurrentUser(currentUser);
            } else if (controller instanceof MyWorkshopsController) {
                ((MyWorkshopsController) controller).setCurrentUser(currentUser);
            }

            Scene scene = new Scene(root, 900, 650);
            Stage stage = (Stage) browseWorkshopsButton.getScene().getWindow();
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
