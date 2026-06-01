package orchestrator.model;

import java.io.IOException;

/**
 * Implementazione concreta di {@link Machine}.
 * Gestisce l'interazione specifica con il sistema sottostante per l'avvio e l'arresto
 * di questa specifica tipologia di risorsa.
 */

public class VirtualBox extends Machine{
    public VirtualBox(String name){
        setName(name);
    }

    @Override
    public void start(){
        try{
            ProcessBuilder pB = new ProcessBuilder("VBoxManage", "startvm", this.getName(), "--type", "headless");
            Process p = pB.start();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void stop(){
        try{
            ProcessBuilder pB = new ProcessBuilder("VBoxManage", "controlvm", this.getName(), "poweroff");
            Process p = pB.start();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
