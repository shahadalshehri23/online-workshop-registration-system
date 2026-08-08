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
import services.impl.TrainerServiceImpl;
import java.util.List;

public class MonitorParticipantsController {

    @FXML private GridPane workshopsGrid;
    @FXML private GridPane participantsGrid;
    @FXML private Button backButton;

    private User currentUser;
    private WorkshopDAO workshopDAO = new WorkshopDAO();
    private TrainerServiceImpl trainerService = new TrainerServiceImpl();

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

            Label titleLabel = new Label(w.getTitle());
            Label countLabel = new Label(w.getRegisteredCount() + " registered");

            Button viewBtn = new Button("View");
            viewBtn.setStyle("-fx-background-color: #117a65; -fx-text-fill: white; -fx-font-weight: bold;");
            viewBtn.setOnAction(e -> showParticipants(w));

            workshopsGrid.add(titleLabel, 0, row);
            workshopsGrid.add(countLabel, 1, row);
            workshopsGrid.add(viewBtn,    2, row);
        }
    }

    private void showParticipants(Workshop w) {
        if (participantsGrid == null) return;
        participantsGrid.getChildren().removeIf(node ->
            GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > 0);

        List<User> participants = trainerService.viewRegisteredParticipants(w.getWorkshopId());
        if (participants.isEmpty()) {
            participantsGrid.add(new Label("No participants registered yet."), 0, 1);
            return;
        }
        for (int i = 0; i < participants.size(); i++) {
            User p = participants.get(i);
            participantsGrid.add(new Label(p.getName()),  0, i + 1);
            participantsGrid.add(new Label(p.getEmail()), 1, i + 1);
            participantsGrid.add(new Label("Active"),     2, i + 1);
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
