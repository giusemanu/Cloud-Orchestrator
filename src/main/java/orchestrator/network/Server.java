package orchestrator.network;

import orchestrator.utils.Parser;
import orchestrator.model.Docker;
import orchestrator.model.ResourceCloud;

import java.lang.Thread;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

/**
 * Entry point del backend. 
 * Inizializza il ServerSocket in ascolto sulla porta configurata e implementa 
 * un loop infinito per accettare le connessioni in ingresso, delegandole a thread separati.
 */

public class Server {
    public static void main(String[] args) {
        System.out.println("--- Avvio Cloud Orchestrator ---");
        
        Parser.loadConfig("src/main/resources/config.xml");         
        try{
            System.out.println("[SISTEMA] Avvio infrastruttura di logging (cloud_db)...");
            ResourceCloud dbContainer = new Docker("cloud_db");
            dbContainer.start(); 
            ServerSocket server = new ServerSocket(Parser.serverPort);
			while(true){
				Socket client = server.accept();
				HandleClient hC = new HandleClient(client);
				hC.start();
			}
        }catch(IOException e){
			System.out.println(e.getMessage());
        }catch (Exception e){
            System.out.println("[ATTENZIONE] Errore avvio DB automatico: " + e.getMessage());
        }
    }
}
