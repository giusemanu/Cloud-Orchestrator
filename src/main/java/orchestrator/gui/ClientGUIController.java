package orchestrator.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller dell'architettura MVC per l'interfaccia JavaFX.
 * Gestisce gli eventi generati dall'utente e orchestra la comunicazione di rete (Socket)
 * verso il Server per l'invio dei comandi.
 */

public class ClientGUIController implements Initializable {

    @FXML private ListView<String> dockerList;
    @FXML private ListView<String> vboxList;
    @FXML private TextArea logArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try (Socket socket = new Socket("127.0.0.1", 8080);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            
            writer.println("list:all:machines");
            String risposta = reader.readLine(); 
            
            if (risposta != null && risposta.contains("|")) {
                String[] sezioni = risposta.split("\\|");
                
                String[] dockerNames = sezioni[0].replace("DOCKER=", "").split(",");
                for (String d : dockerNames) {
                    if (!d.trim().isEmpty()) dockerList.getItems().add(d);
                }
                
                String[] vboxNames = sezioni[1].replace("VBOX=", "").split(",");
                for (String v : vboxNames) {
                    if (!v.trim().isEmpty()) vboxList.getItems().add(v);
                }
            }
            
        } catch (Exception ex) {
            logArea.appendText("[!] Impossibile caricare le macchine: Server offline.\n");
        }
    }

    @FXML
    public void startDocker() {
        String selezionato = dockerList.getSelectionModel().getSelectedItem();
        if (selezionato != null) {
            inviaComando("start:docker:" + selezionato);
        } else {
            logArea.appendText("[!] Seleziona un container dalla lista!\n");
        }
    }

    @FXML
    public void stopDocker() {
        String selezionato = dockerList.getSelectionModel().getSelectedItem();
        if (selezionato != null) {
            inviaComando("stop:docker:" + selezionato);
        }
    }

    @FXML
    public void startVBox() {
        String selezionato = vboxList.getSelectionModel().getSelectedItem();
        if (selezionato != null) {
            inviaComando("start:vbox:" + selezionato);
        } else {
            logArea.appendText("[!] Seleziona una VM dalla lista!\n");
        }
    }

    @FXML
    public void stopVBox() {
        String selezionato = vboxList.getSelectionModel().getSelectedItem();
        if (selezionato != null) {
            inviaComando("stop:vbox:" + selezionato);
        }
    }

    private void inviaComando(String comando) {
        try (Socket socket = new Socket("127.0.0.1", 8080);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            
            writer.println(comando);
            String risposta = reader.readLine();
            
            logArea.appendText(">> " + comando + "\n");
            logArea.appendText("<< " + risposta + "\n\n");
            
        } catch (Exception ex) {
            logArea.appendText("[!] Errore di connessione col Server.\n\n");
        }
    }
}
