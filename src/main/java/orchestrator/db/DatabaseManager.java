package orchestrator.db;

import orchestrator.utils.Parser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.lang.String;

/**
 * Componente responsabile della persistenza dei dati e dello storico delle operazioni (Logging).
 * Interagisce con il database relazionale tramite driver JDBC.
 */

public class DatabaseManager {

    /**
     * Salva in modo persistente l'operazione eseguita su una macchina nel database.
     * Il metodo è <b>synchronized</b> per garantire la thread-safety ed evitare race condition 
     * durante l'accesso concorrente alla tabella dei log.
     * * @param action Il tipo di azione eseguita (es. START, STOP)
     * @param type   La tipologia di risorsa (es. DOCKER, VBOX)
     * @param name   Il nome univoco della macchina interessata
     */    

    public static synchronized void salvaLog(String action, String type, String name) {
        
        String query = "INSERT INTO log (action, type, name) VALUES (?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(Parser.dbUrl, Parser.dbUser, Parser.dbPassword);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, action);
            pstmt.setString(2, type);
            pstmt.setString(3, name);
            
            pstmt.executeUpdate();
            
            System.out.println("[DB] Log salvato: " + action + " [" + type.toUpperCase() + "] su " + name);
            
        } catch (SQLException e) {
            System.out.println("[DB ERRORE] Impossibile salvare il log: " + e.getMessage());
        }
    }
}
