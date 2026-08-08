package workshopsystemproject;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WorkshopSystemProject extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(
                    "/online_workshop_regisration_syastem_project/views/auth/LoginScreen.fxml"));

            Scene scene = new Scene(root, 800, 600);

            primaryStage.setTitle("Online Workshop Registration System");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            System.out.println("Error loading Login Screen: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
