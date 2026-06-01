package orchestrator.model;

/**
 * Interfaccia fondamentale che definisce il contratto per una risorsa Cloud genericamente orchestrabile.
 * Fornisce l'astrazione necessaria per implementare il polimorfismo nel sistema.
 * * @author Giuseppe Emanuele
 * @version 1.0
 */

public interface ResourceCloud {
    /**
     * Invia il segnale di avvio alla risorsa cloud nel sistema ospite.
     */
    void start();

    /**
     * Invia il segnale di arresto o spegnimento forzato alla risorsa cloud.
     */
    void stop();
}
