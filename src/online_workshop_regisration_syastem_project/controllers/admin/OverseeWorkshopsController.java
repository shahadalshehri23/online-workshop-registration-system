package online_workshop_regisration_syastem_project.controllers.admin;

import dao.UserDAO;
import dao.WorkshopDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.User;
import models.Workshop;
import java.util.List;

public class OverseeWorkshopsController {

    @FXML private Button backButton;
    @FXML private GridPane workshopsGrid;

    private User adminUser;
    private WorkshopDAO workshopDAO = new WorkshopDAO();
    private UserDAO userDAO = new UserDAO();

    @FXML
    public void initialize() {
        loadWorkshops();
        if (backButton != null) {
            backButton.setOnAction(event -> goBack());
        }
    }

    public void setAdminUser(User user) {
        this.adminUser = user;
    }

    private void loadWorkshops() {
        if (workshopsGrid == null) return;
        workshopsGrid.getChildren().removeIf(node ->
            GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > 0);

        List<Workshop> workshops = workshopDAO.getAllWorkshops();

        for (int i = 0; i < workshops.size(); i++) {
            Workshop w = workshops.get(i);
            int row = i + 1;

            String trainerName = "N/A";
            if (w.getTrainerId() > 0) {
                User trainer = userDAO.getUserById(w.getTrainerId());
                if (trainer != null) trainerName = trainer.getName();
            }

            Label titleLabel      = new Label(w.getTitle());
            Label trainerLabel    = new Label(trainerName);
            Label capacityLabel   = new Label(String.valueOf(w.getCapacity()));
            Label registeredLabel = new Label(String.valueOf(w.getRegisteredCount()));

            Button removeBtn = new Button("Remove");
            removeBtn.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white; -fx-font-weight: bold;");

            final int workshopId = w.getWorkshopId();
            removeBtn.setOnAction(e -> {
                workshopDAO.deleteWorkshop(workshopId);
                loadWorkshops();
            });

            workshopsGrid.add(titleLabel,      0, row);
            workshopsGrid.add(trainerLabel,    1, row);
            workshopsGrid.add(capacityLabel,   2, row);
            workshopsGrid.add(registeredLabel, 3, row);
            workshopsGrid.add(removeBtn,       4, row);
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
