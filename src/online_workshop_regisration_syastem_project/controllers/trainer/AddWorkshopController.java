package online_workshop_regisration_syastem_project.controllers.trainer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import models.User;
import models.Workshop;
import services.impl.TrainerServiceImpl;
import exceptions.InvalidCredentialsException;

public class AddWorkshopController {

    @FXML private TextField titleField;
    @FXML private TextField descriptionField;
    @FXML private TextField dateField;
    @FXML private TextField capacityField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private TrainerServiceImpl trainerService = new TrainerServiceImpl();
    private User currentUser;

    @FXML
    public void initialize() {
        if (saveButton != null) {
            saveButton.setOnAction(event -> saveWorkshop());
        }
        if (cancelButton != null) {
            cancelButton.setOnAction(event -> goBack());
        }
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    private void saveWorkshop() {
        try {
            String title = titleField.getText();
            String description = descriptionField.getText();
            String capacityStr = capacityField.getText();

            if (title.isEmpty() || description.isEmpty() || capacityStr.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Missing Fields");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all fields!");
                alert.showAndWait();
                return;
            }

            int capacity = Integer.parseInt(capacityStr);

            Workshop workshop = new Workshop();
            workshop.setTitle(title);
            workshop.setDescription(description);
            workshop.setCapacity(capacity);
            workshop.setRegisteredCount(0);
            if (currentUser != null) {
                workshop.setTrainerId(currentUser.getUserId());
            }

            trainerService.addWorkshop(workshop);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Workshop added successfully!");
            alert.showAndWait();
            goBack();

        } catch (NumberFormatException e) {
            System.out.println("Capacity must be a number!");
        } catch (InvalidCredentialsException e) {
            System.out.println("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }
}
