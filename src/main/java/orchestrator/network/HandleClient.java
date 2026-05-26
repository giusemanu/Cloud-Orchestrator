package orchestrator.network;

import orchestrator.model.*;
import orchestrator.db.DatabaseManager;

import java.net.Socket;
import java.io.*;

public class HandleClient extends Thread{
    private Socket client;

    public HandleClient(Socket client){
        this.client = client;
    }

    @Override
    public void run(){
		BufferedReader reader;
        PrintWriter writer;
		try{
			reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        	writer = new PrintWriter(client.getOutputStream(), true);

            String request = reader.readLine();

            if(request != null){
                String[] reqSplit = request.split(":");
                if(reqSplit.length != 3){
                    writer.println("ERRORE: Formato non valido. Usa il formato AZIONE:TIPO:NOME");
                }else{
                    String operation = reqSplit[0];
                    String type = reqSplit[1]; 
                    String name = reqSplit[2];
                                 
                    ResourceCloud machine; 
                    if(type.equalsIgnoreCase("docker"))
                        machine = new Docker(name); 
                    else
                        machine = new VirtualBox(name); 
                    
                    if (operation.equalsIgnoreCase("start")){
                        machine.start();
                        DatabaseManager.salvaLog("START", type, name);
                        writer.println("Eseguito avvio per: " + name); 
                    }else if(operation.equalsIgnoreCase("stop")){
                        machine.stop();
                        DatabaseManager.salvaLog("STOP", type, name);
                        writer.println("Eseguito arresto per: " + name); 
                    }else if (operation.equalsIgnoreCase("list")){
                        StringBuilder response = new StringBuilder();
                        
                        response.append("DOCKER=");
                        Process pDocker = new ProcessBuilder("docker", "ps", "-a", "--format", "{{.Names}}").start();
                        BufferedReader readerDocker = new BufferedReader(new InputStreamReader(pDocker.getInputStream()));
                        String lineD;
                        while ((lineD = readerDocker.readLine()) != null) {
                            response.append(lineD).append(",");
                        }
                        
                        response.append("|VBOX=");
                        Process pVbox = new ProcessBuilder("VBoxManage", "list", "vms").start();
                        BufferedReader readerVbox = new BufferedReader(new InputStreamReader(pVbox.getInputStream()));
                        String lineV;
                        while ((lineV = readerVbox.readLine()) != null) {
                            String[] parts = lineV.split("\"");
                            if (parts.length > 1) {
                                response.append(parts[1]).append(",");
                            }
                        }
                        
                        writer.println(response.toString());
                    }else{
                        writer.println("ERRORE: Comando '" + operation + "' non riconosciuto.");
                    }
                }
            }

			reader.close();
			writer.close();
			client.close();	
		}catch(IOException e){
			System.out.println(e.getMessage());
        }
    }
}
