package project2048;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameApp extends Application{

    @Override
    public void start(Stage stage) throws Exception, NumberFormatException {
        stage.setTitle("2048");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Game.fxml"));
        Parent root = fxmlLoader.load();
        GameController controller = fxmlLoader.getController();
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
        scene.setOnKeyPressed(e -> controller.handleMovement(e));
    }

    public static void main(String[] args) {
        GameApp.launch(args);
    }
    
}
