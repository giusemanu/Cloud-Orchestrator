package orchestrator.model;

import java.io.IOException;

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
