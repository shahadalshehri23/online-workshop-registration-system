package online_workshop_regisration_syastem_project.controllers.trainer;

import dao.WorkshopDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.User;
import models.Workshop;
import services.impl.TrainerServiceImpl;
import java.util.List;

public class UpdateWorkshopController {

    @FXML private GridPane workshopsGrid;
    @FXML private TextField titleField;
    @FXML private TextField descriptionField;
    @FXML private TextField dateField;
    @FXML private TextField capacityField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private User currentUser;
    private Workshop selectedWorkshop;
    private WorkshopDAO workshopDAO = new WorkshopDAO();
    private TrainerServiceImpl trainerService = new TrainerServiceImpl();

    @FXML
    public void initialize() {
        if (saveButton != null) saveButton.setOnAction(e -> saveWorkshop());
        if (cancelButton != null) cancelButton.setOnAction(e -> goBack());
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

            Label titleLabel    = new Label(w.getTitle());
            Label capacityLabel = new Label(String.valueOf(w.getCapacity()));

            Button selectBtn = new Button("Select");
            selectBtn.setStyle("-fx-background-color: #117a65; -fx-text-fill: white; -fx-font-weight: bold;");
            selectBtn.setOnAction(e -> fillForm(w));

            workshopsGrid.add(titleLabel,    0, row);
            workshopsGrid.add(capacityLabel, 1, row);
            workshopsGrid.add(selectBtn,     2, row);
        }
    }

    private void fillForm(Workshop w) {
        selectedWorkshop = w;
        titleField.setText(w.getTitle());
        descriptionField.setText(w.getDescription());
        capacityField.setText(String.valueOf(w.getCapacity()));
        dateField.setText("");
    }

    private void saveWorkshop() {
        if (selectedWorkshop == null) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setHeaderText(null);
            a.setContentText("Please select a workshop first!");
            a.showAndWait();
            return;
        }
        String title = titleField.getText();
        String description = descriptionField.getText();
        String capacityStr = capacityField.getText();
        if (title.isEmpty() || description.isEmpty() || capacityStr.isEmpty()) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setHeaderText(null);
            a.setContentText("Please fill in all fields!");
            a.showAndWait();
            return;
        }
        try {
            selectedWorkshop.setTitle(title);
            selectedWorkshop.setDescription(description);
            selectedWorkshop.setCapacity(Integer.parseInt(capacityStr));
            trainerService.updateWorkshop(selectedWorkshop);
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText(null);
            a.setContentText("Workshop updated successfully!");
            a.showAndWait();
            loadWorkshops();
            titleField.clear(); descriptionField.clear(); capacityField.clear();
            selectedWorkshop = null;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
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
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Error going back: " + e.getMessage());
        }
    }
}
