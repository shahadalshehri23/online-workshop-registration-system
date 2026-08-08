package online_workshop_regisration_syastem_project.controllers.admin;

import dao.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.User;
import java.util.List;

public class ManageUsersController {

    @FXML private Button addUserButton;
    @FXML private Button backButton;
    @FXML private GridPane usersGrid;

    private User adminUser;

    @FXML
    public void initialize() {
        loadUsers();
        if (addUserButton != null) {
            addUserButton.setOnAction(event -> loadAddUserScreen());
        }
        if (backButton != null) {
            backButton.setOnAction(event -> goBack());
        }
    }

    private void loadUsers() {
        if (usersGrid == null) return;
        // Remove all rows except the header (row 0)
        usersGrid.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > 0);

        UserDAO userDAO = new UserDAO();
        List<User> users = userDAO.getAllUsers();

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            int row = i + 1;

            Label nameLabel  = new Label(user.getName());
            Label emailLabel = new Label(user.getEmail());
            Label roleLabel  = new Label(user.getRole());

            Button editBtn = new Button("Edit");
            editBtn.setStyle("-fx-background-color: #117a65; -fx-text-fill: white; -fx-font-weight: bold;");

            Button removeBtn = new Button("Remove");
            removeBtn.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white; -fx-font-weight: bold;");

            final User selectedUser = user;
            final int userId = user.getUserId();
            editBtn.setOnAction(e -> openEditUser(selectedUser));
            removeBtn.setOnAction(e -> {
                userDAO.deleteUser(userId);
                loadUsers();
            });

            usersGrid.add(nameLabel,  0, row);
            usersGrid.add(emailLabel, 1, row);
            usersGrid.add(roleLabel,  2, row);
            usersGrid.add(editBtn,    3, row);
            usersGrid.add(removeBtn,  4, row);
        }
    }

    public void setAdminUser(User user) {
        this.adminUser = user;
    }

    private void openEditUser(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/online_workshop_regisration_syastem_project/views/admin/EditUserScreen.fxml"));
            Parent root = loader.load();
            EditUserController controller = loader.getController();
            controller.setAdminUser(adminUser);
            controller.setTargetUser(user);
            Scene scene = new Scene(root, 900, 650);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Error loading Edit User screen: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadAddUserScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/online_workshop_regisration_syastem_project/views/admin/AddUserScreen.fxml"));
            Parent root = loader.load();

            Object controller = loader.getController();
            if (controller instanceof AddUserController) {
                ((AddUserController) controller).setAdminUser(adminUser);
            }

            Scene scene = new Scene(root, 900, 650);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Error loading Add User screen: " + e.getMessage());
            e.printStackTrace();
        }
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
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Error going back: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
