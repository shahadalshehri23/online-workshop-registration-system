package online_workshop_regisration_syastem_project.controllers.auth;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import dao.UserDAO;
import models.User;
import online_workshop_regisration_syastem_project.controllers.admin.AdminController;
import online_workshop_regisration_syastem_project.controllers.participant.ParticipantController;
import online_workshop_regisration_syastem_project.controllers.trainer.TrainerController;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Label errorLabel;

    private UserDAO userDAO = new UserDAO();

    @FXML
    public void initialize() {
        if (loginButton != null) {
            loginButton.setOnAction(event -> handleLogin());
        }
    }

    @FXML
    private void handleLogin() {
        try {
            String email = emailField.getText();
            String password = passwordField.getText();

            if (email.isEmpty() || password.isEmpty()) {
                errorLabel.setText("Please fill in all fields!");
                return;
            }

            User user = userDAO.getUserByEmail(email);

            if (user == null) {
                errorLabel.setText("User not found!");
                return;
            }

            if (!user.getPassword().equals(password)) {
                errorLabel.setText("Invalid password!");
                return;
            }

            errorLabel.setText("");

            String role = user.getRole();
            String fxmlPath = "";

            if (role.equals("Participant")) {
                fxmlPath = "/online_workshop_regisration_syastem_project/views/participant/ParticipantScreen.fxml";
            } else if (role.equals("Trainer")) {
                fxmlPath = "/online_workshop_regisration_syastem_project/views/trainer/TrainerScreen.fxml";
            } else if (role.equals("Administrator")) {
                fxmlPath = "/online_workshop_regisration_syastem_project/views/admin/AdminScreen.fxml";
            } else {
                errorLabel.setText("Unknown role: " + role);
                return;
            }

            loadScreen(fxmlPath, user);

        } catch (Exception e) {
            errorLabel.setText("Login failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadScreen(String fxmlPath, User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Object controller = loader.getController();
            if (controller instanceof ParticipantController) {
                ((ParticipantController) controller).setCurrentUser(user);
            } else if (controller instanceof TrainerController) {
                ((TrainerController) controller).setCurrentUser(user);
            } else if (controller instanceof AdminController) {
                ((AdminController) controller).setCurrentUser(user);
            }

            Scene scene = new Scene(root, 900, 650);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            errorLabel.setText("Failed to load screen: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
