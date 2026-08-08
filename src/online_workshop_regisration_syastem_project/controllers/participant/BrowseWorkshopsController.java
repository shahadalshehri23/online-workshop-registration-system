package online_workshop_regisration_syastem_project.controllers.participant;

import dao.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.User;
import models.Workshop;
import services.impl.ParticipantServiceImpl;
import java.util.List;

public class BrowseWorkshopsController {

    @FXML private GridPane workshopsGrid;
    @FXML private Button backButton;

    private User currentUser;
    private ParticipantServiceImpl participantService = new ParticipantServiceImpl();
    private UserDAO userDAO = new UserDAO();

    @FXML
    public void initialize() {
        if (backButton != null) backButton.setOnAction(e -> goBack());
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
        loadWorkshops();
    }

    private void loadWorkshops() {
        if (workshopsGrid == null) return;
        workshopsGrid.getChildren().removeIf(node ->
            GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > 0);

        List<Workshop> workshops = participantService.viewAvailableWorkshops();
        for (int i = 0; i < workshops.size(); i++) {
            Workshop w = workshops.get(i);
            int row = i + 1;

            String trainerName = "N/A";
            if (w.getTrainerId() > 0) {
                User trainer = userDAO.getUserById(w.getTrainerId());
                if (trainer != null) trainerName = trainer.getName();
            }

            Label titleLabel    = new Label(w.getTitle());
            Label trainerLabel  = new Label(trainerName);
            Label spotsLabel    = new Label((w.getCapacity() - w.getRegisteredCount()) + " spots left");

            Button registerBtn = new Button("Register");
            registerBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
            final int workshopId = w.getWorkshopId();
            registerBtn.setOnAction(e -> registerForWorkshop(workshopId));

            workshopsGrid.add(titleLabel,   0, row);
            workshopsGrid.add(trainerLabel, 1, row);
            workshopsGrid.add(spotsLabel,   2, row);
            workshopsGrid.add(registerBtn,  3, row);
        }
    }

    private void registerForWorkshop(int workshopId) {
        try {
            participantService.registerForWorkshop(currentUser.getUserId(), workshopId);
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText(null);
            a.setContentText("Registered successfully!");
            a.showAndWait();
            loadWorkshops();
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setHeaderText(null);
            a.setContentText(e.getMessage());
            a.showAndWait();
        }
    }

    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/online_workshop_regisration_syastem_project/views/participant/ParticipantScreen.fxml"));
            Parent root = loader.load();
            Object controller = loader.getController();
            if (controller instanceof ParticipantController) {
                ((ParticipantController) controller).setCurrentUser(currentUser);
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
