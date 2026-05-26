package orchestrator.db;

import orchestrator.utils.Parser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.lang.String;

public class DatabaseManager {
    
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
