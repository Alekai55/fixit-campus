package com.adromi.backendJava;

import java.util.logging.Logger;

public class HiloExtendido extends Thread {
    //Creamos la variable del logger, se lo pasamos por parámetro para que luego se lo inyectemos desde el main
    private Logger logger;
    public HiloExtendido(String nombre, Logger logger) {
        super(nombre);
        this.logger = logger;
    }


    @Override
    public void run() {
        logger.info(Thread.currentThread().getName()+" ha sido creado y se encuentra en estado "+Thread.currentThread().getState()+"\n---------------------------------------------------------------");
    }
}
