package online_workshop_regisration_syastem_project.controllers.admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import models.User;
import services.impl.AdminServiceImpl;
import exceptions.InvalidCredentialsException;

public class AddUserController {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private RadioButton participantRadio;
    @FXML private RadioButton trainerRadio;
    @FXML private RadioButton adminRadio;
    @FXML private ToggleGroup roleGroup;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private AdminServiceImpl adminService = new AdminServiceImpl();
    private User adminUser;

    @FXML
    public void initialize() {
        if (saveButton != null) {
            saveButton.setOnAction(event -> saveUser());
        }
        if (cancelButton != null) {
            cancelButton.setOnAction(event -> goBack());
        }
    }

    public void setAdminUser(User user) {
        this.adminUser = user;
    }

    private void saveUser() {
        try {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String role = getSelectedRole();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                System.out.println("Please fill in all fields!");
                return;
            }

            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            user.setRole(role);

            adminService.addUser(user);
            System.out.println("User added successfully!");
            goBack();

        } catch (InvalidCredentialsException e) {
            System.out.println("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String getSelectedRole() {
        if (trainerRadio != null && trainerRadio.isSelected()) {
            return "Trainer";
        } else if (adminRadio != null && adminRadio.isSelected()) {
            return "Administrator";
        } else {
            return "Participant";
        }
    }

    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/online_workshop_regisration_syastem_project/views/admin/ManageUsersScreen.fxml"));
            Parent root = loader.load();

            Object controller = loader.getController();
            if (controller instanceof ManageUsersController) {
                ((ManageUsersController) controller).setAdminUser(adminUser);
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
