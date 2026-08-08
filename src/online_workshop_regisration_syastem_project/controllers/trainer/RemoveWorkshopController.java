package online_workshop_regisration_syastem_project.controllers.trainer;

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

public class RemoveWorkshopController {

    @FXML private Button backButton;
    @FXML private GridPane workshopsGrid;

    private User currentUser;
    private WorkshopDAO workshopDAO = new WorkshopDAO();

    @FXML
    public void initialize() {
        if (backButton != null) backButton.setOnAction(e -> goBack());
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
        loadWorkshops();
    }

    private void loadWorkshops() {
        if (workshopsGrid == null || currentUser == null) return;
        workshopsGrid.getChildren().removeIf(node ->
            GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > 0);

        List<Workshop> workshops = workshopDAO.getWorkshopsByTrainerId(currentUser.getUserId());
        for (int i = 0; i < workshops.size(); i++) {
            Workshop w = workshops.get(i);
            int row = i + 1;

            Label titleLabel      = new Label(w.getTitle());
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
            workshopsGrid.add(capacityLabel,   1, row);
            workshopsGrid.add(registeredLabel, 2, row);
            workshopsGrid.add(removeBtn,       3, row);
        }
    }

    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/online_workshop_regisration_syastem_project/views/trainer/TrainerScreen.fxml"));
            Parent root = loader.load();
            Object controller = loader.getController();
            if (controller instanceof TrainerController) {
                ((TrainerController) controller).setCurrentUser(currentUser);
            }
            Scene scene = new Scene(root, 900, 650);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Error going back: " + e.getMessage());
        }
    }
}
