package orchestrator.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Entry point dell'interfaccia utente (Frontend).
 * Carica il layout FXML e inizializza lo stage principale di JavaFX.
 */

public class MainGUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/layout.fxml"));
        
        primaryStage.setTitle("Cloud Orchestrator - Pannello di Controllo");
        primaryStage.setScene(new Scene(root, 650, 450));
        
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(400);
        
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
