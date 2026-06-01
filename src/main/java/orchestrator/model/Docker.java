package orchestrator.model;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;

/**
 * Implementazione concreta di {@link Machine}.
 * Gestisce l'interazione specifica con il sistema sottostante per l'avvio e l'arresto
 * di questa specifica tipologia di risorsa.
 */

public class Docker extends Machine {
    private DockerClient dockerClient;

    public Docker(String name) {
        this.setName(name);
        
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
        
        DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
            .dockerHost(config.getDockerHost())
            .sslConfig(config.getSSLConfig())
            .build();
            
        this.dockerClient = DockerClientBuilder.getInstance(config)
            .withDockerHttpClient(httpClient)
            .build();
    }

    @Override
    public void start() {
        try {
            dockerClient.startContainerCmd(this.getName()).exec();
            System.out.println("Container " + this.getName() + " avviato.");
        } catch (NotFoundException e) {
            System.out.println("Errore: Il container non esiste. " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Errore generico API: " + e.getMessage());
        }
    }

    @Override
    public void stop() {
        try {
            dockerClient.stopContainerCmd(this.getName()).exec();
            System.out.println("Container " + this.getName() + " arrestato tramite API.");
        } catch (Exception e) {
            System.out.println("Errore API: " + e.getMessage());
        }
    }
}
