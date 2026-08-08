package online_workshop_regisration_syastem_project.controllers.admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import models.User;

public class AdminController {

    @FXML private Button manageUsersButton;
    @FXML private Button overseeWorkshopsButton;
    @FXML private Button systemSettingsButton;
    @FXML private Button logoutButton;

    private User currentUser;

    @FXML
    public void initialize() {
        if (manageUsersButton != null) {
            manageUsersButton.setOnAction(event -> loadScreen(
                "/online_workshop_regisration_syastem_project/views/admin/ManageUsersScreen.fxml"));
        }
        if (overseeWorkshopsButton != null) {
            overseeWorkshopsButton.setOnAction(event -> loadScreen(
                "/online_workshop_regisration_syastem_project/views/admin/OverseeWorkshopsScreen.fxml"));
        }
        if (systemSettingsButton != null) {
            systemSettingsButton.setOnAction(event -> loadScreen(
                "/online_workshop_regisration_syastem_project/views/admin/SystemSettingsScreen.fxml"));
        }
        if (logoutButton != null) {
            logoutButton.setOnAction(event -> logout());
        }
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    private void loadScreen(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Object controller = loader.getController();
            if (controller instanceof ManageUsersController) {
                ((ManageUsersController) controller).setAdminUser(currentUser);
            } else if (controller instanceof OverseeWorkshopsController) {
                ((OverseeWorkshopsController) controller).setAdminUser(currentUser);
            }

            Scene scene = new Scene(root, 900, 650);
            Stage stage = (Stage) manageUsersButton.getScene().getWindow();
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
