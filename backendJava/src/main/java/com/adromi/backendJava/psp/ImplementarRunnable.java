package com.adromi.backendJava.psp;

import java.util.logging.Logger;

public class ImplementarRunnable implements Runnable {
    private Logger logger;
    //Igual que en la clase de extends, le inyectamos el logger desde el main
    public ImplementarRunnable(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void run() {
        logger.info(Thread.currentThread().getName() + " ha sido creado  y se encuentra en estado " + Thread.currentThread().getState() + "\n---------------------------------------------------------------");
    }
}
//Prueba github
