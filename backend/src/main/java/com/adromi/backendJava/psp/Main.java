package com.adromi.backendJava.psp;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main{
    private static Logger logger = Logger.getLogger("Mi Log");

    public static void main(String[] args) throws IOException {

        System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tF [%4$s] - %5$s%6$s%n"); //modifacamos el formato del sistema para texo más legible

        //Ruta relativa para crear la carpeta que contiene el archivo logs
        String rutaCarpetaLog = "backend/archivos_creados/archivos-log";
        //Nombre archivo para los logs
        String rutaArchivoLog = rutaCarpetaLog + "/archivo.log";

        // 2. Crear la carpeta si no existe en el proyecto
        File directorio = new File(rutaCarpetaLog);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }

        // Configurar el FileHandler con la ruta relativa
        FileHandler fh = new FileHandler(rutaArchivoLog, false); //por ahora en false, lo cambiaremos a true para que no pise
        logger.setLevel(Level.ALL); //el logger coge todos los niveles
        fh.setLevel(Level.ALL); //el handler en su filtro, coge todos los niveles
        logger.setUseParentHandlers(false); // No se muestra por pantalla



        logger.addHandler(fh);
        fh.setFormatter(new SimpleFormatter()); //le pasamos al handler el formato legible

        //Hilo creacion anónima, con una pausa al terminar, para que el hilo3 lo captura en espera
        Thread hilo1 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    logger.info(Thread.currentThread().getName()+"\nEstado de "+Thread.currentThread().getName()+": "+Thread.currentThread().getState()+"\n---------------------------------------------------------------");
                    Thread.currentThread().join(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"Hilo1 (Inicial)");

        //Estado Inicial(new) hilo1 desde main
        logger.info(Thread.currentThread().getName()+"\nEstado de "+hilo1.getName()+": "+hilo1.getState()+"\n---------------------------------------------------------------");

        //Iniciar Run del hilo 1
        hilo1.start();

        //Creacion Hilo Extendiendo Thread
        HiloExtendido hiloExtendido = new HiloExtendido("Hilo2(Extendido)",logger);
        //Iniciar Run del hilo 2
        hiloExtendido.start();

        //Creacion Hilo Lambda, le damos una pausa inicial para capturar la espera del hilo1
        Thread hilo3 = new Thread(() ->{
            try {
                Thread.sleep(50);
                logger.info(Thread.currentThread().getName()+"\nEstado de "+hilo1.getName()+": "+hilo1.getState()+"\n---------------------------------------------------------------");
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        },"Hilo3 (comprobar estado Hilo1)");

        //Iniciar Run del hilo 3
        hilo3.start();

        //Creación e inicio del hilo usando la clase que implementa runnable
        ImplementarRunnable runnable = new ImplementarRunnable(logger);
        Thread hilo4 = new Thread(runnable, "Hilo4 (Runnable)");
        hilo4.start();



    }
}
