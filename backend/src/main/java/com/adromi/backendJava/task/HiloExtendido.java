package com.adromi.backendJava.task;

import java.util.logging.Logger;

public class HiloExtendido extends Thread {
    // Creamos la variable del logger, se lo pasamos por parámetro para que luego se lo inyectemos desde el main
    private Logger logger;

    public HiloExtendido(String nombre, Logger logger) {
        super(nombre);
        this.logger = logger;
    }

    @Override
    public void run() {
        try {
            logger.info(Thread.currentThread().getName() + " - Estado: " + Thread.currentThread().getState());
            // el hilo entra en TIMED_WAITING durante 500ms
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
