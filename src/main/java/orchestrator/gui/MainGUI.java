package orchestrator.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainGUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carica il file grafico che abbiamo messo nella cartella resources
        Parent root = FXMLLoader.load(getClass().getResource("/layout.fxml"));
        
        // Imposta il titolo e le dimensioni iniziali della finestra
        primaryStage.setTitle("Cloud Orchestrator - Pannello di Controllo");
        primaryStage.setScene(new Scene(root, 650, 450));
        
        // Impedisce che la finestra diventi troppo piccola
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(400);
        
        // Mostra la finestra sullo schermo
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Lancia l'applicazione JavaFX
        launch(args);
    }
}
