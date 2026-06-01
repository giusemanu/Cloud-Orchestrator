package orchestrator.utils;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;

/**
 * Utility per la lettura e il parsing del file di configurazione XML.
 * Carica in memoria i parametri critici di sistema (es. porta server e credenziali DB)
 * all'avvio dell'applicazione.
 */

public class Parser {
    
    public static int serverPort;
    public static String dbUrl;
    public static String dbUser;
    public static String dbPassword;

    public static void loadConfig(String fileName) {
        try {
            File file = new File(fileName);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);
            
            document.getDocumentElement().normalize();

            serverPort = Integer.parseInt(document.getElementsByTagName("ServerPort").item(0).getTextContent());
            dbUrl = document.getElementsByTagName("DbUrl").item(0).getTextContent();
            dbUser = document.getElementsByTagName("DbUser").item(0).getTextContent();
            dbPassword = document.getElementsByTagName("DbPassword").item(0).getTextContent();
            
            System.out.println("[SISTEMA] Configurazione XML caricata con successo.");
            
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
