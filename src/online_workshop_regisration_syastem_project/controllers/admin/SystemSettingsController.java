package online_workshop_regisration_syastem_project.controllers.admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.User;

public class SystemSettingsController {

    @FXML private TextField systemNameField;
    @FXML private TextField maxCapacityField;
    @FXML private TextField openDateField;
    @FXML private TextField closeDateField;
    @FXML private CheckBox allowRegistrationsCheck;
    @FXML private CheckBox emailNotificationsCheck;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private User adminUser;

    @FXML
    public void initialize() {
        if (saveButton != null) {
            saveButton.setOnAction(event -> saveSettings());
        }
        if (cancelButton != null) {
            cancelButton.setOnAction(event -> goBack());
        }
    }

    public void setAdminUser(User user) {
        this.adminUser = user;
    }

    private void saveSettings() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Settings Saved");
        alert.setHeaderText(null);
        alert.setContentText("Settings saved successfully!");
        alert.showAndWait();
        goBack();
    }

    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/online_workshop_regisration_syastem_project/views/admin/AdminScreen.fxml"));
            Parent root = loader.load();

            Object controller = loader.getController();
            if (controller instanceof AdminController) {
                ((AdminController) controller).setCurrentUser(adminUser);
            }

            Scene scene = new Scene(root, 900, 650);
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Error going back: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
