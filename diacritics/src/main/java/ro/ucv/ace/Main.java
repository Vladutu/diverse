package ro.ucv.ace;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Hello world!
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/openWindow.fxml"));
        primaryStage.setTitle("Diacritics Remover");
        primaryStage.setScene(new Scene(root, 515, 117));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(String.valueOf(getClass().getClassLoader().getResource("diacritics.jpg"))));
        primaryStage.show();
    }
}
