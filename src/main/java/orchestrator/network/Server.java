package orchestrator.network;

import orchestrator.utils.Parser;

import java.lang.Thread;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class Server {
    public static void main(String[] args) {
        System.out.println("--- Avvio Cloud Orchestrator ---");
        
        Parser.loadConfig("src/main/resources/config.xml");         
        try{
            ServerSocket server = new ServerSocket(Parser.serverPort);
			while(true){
				Socket client = server.accept();
				HandleClient hC = new HandleClient(client);
				hC.start();
			}
        }catch(IOException e){
			System.out.println(e.getMessage());
		}       
    }
}
