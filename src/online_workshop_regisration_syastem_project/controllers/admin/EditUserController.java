package online_workshop_regisration_syastem_project.controllers.admin;

import dao.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import models.User;

public class EditUserController {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private RadioButton participantRadio;
    @FXML private RadioButton trainerRadio;
    @FXML private RadioButton adminRadio;
    @FXML private ToggleGroup roleGroup;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private UserDAO userDAO = new UserDAO();
    private User adminUser;
    private User targetUser;

    @FXML
    public void initialize() {
        if (saveButton != null) saveButton.setOnAction(e -> saveUser());
        if (cancelButton != null) cancelButton.setOnAction(e -> goBack());
    }

    public void setAdminUser(User user) {
        this.adminUser = user;
    }

    public void setTargetUser(User user) {
        this.targetUser = user;
        nameField.setText(user.getName());
        emailField.setText(user.getEmail());
        passwordField.setText(user.getPassword());
        if ("Trainer".equals(user.getRole()) && trainerRadio != null) trainerRadio.setSelected(true);
        else if ("Administrator".equals(user.getRole()) && adminRadio != null) adminRadio.setSelected(true);
        else if (participantRadio != null) participantRadio.setSelected(true);
    }

    private void saveUser() {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            System.out.println("Please fill in all fields!");
            return;
        }
        String role = "Participant";
        if (trainerRadio != null && trainerRadio.isSelected()) role = "Trainer";
        else if (adminRadio != null && adminRadio.isSelected()) role = "Administrator";

        targetUser.setName(name);
        targetUser.setEmail(email);
        targetUser.setPassword(password);
        targetUser.setRole(role);
        userDAO.updateUser(targetUser);
        System.out.println("User updated successfully!");
        goBack();
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
