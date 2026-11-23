package com.adromi.backendJava.task;

import java.util.logging.Logger;

public class HiloConRunnable implements Runnable {
    private Logger logger;
    // Igual que en la clase de extends, le inyectamos el logger desde el main
    public HiloConRunnable(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void run() {
        logger.info(" Hilo(Runnable - hilo3): " + Thread.currentThread().getName() + " - Estado: " + Thread.currentThread().getState());
    }
}
