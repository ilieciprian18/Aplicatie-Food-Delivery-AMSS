package MainApplicationUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFoodDeliveryApplication extends Application {

private static Stage stg;

    @Override
    public void start(Stage primaryStage) throws Exception {

        stg = primaryStage;
        primaryStage.setResizable(false);

        Parent root = FXMLLoader.load(getClass().getResource("../MainApplicationUI/login.fxml"));
        primaryStage.setScene(new Scene(root, 850, 725));
        primaryStage.setTitle("Food Delivery Application");
        primaryStage.getIcons().add(new Image("MainApplicationUI/Images/icon.png"));
        primaryStage.show();

    }

    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
    }

    public static void main(String[] args) {
        launch(args);
    }


}
