package com.adromi.backendJava.task;

import com.adromi.backendJava.data.Datos;
import com.adromi.backendJava.service.GestorArchivos;

import java.io.IOException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

// Ejecuta las tareas del proyecto en 3 fases usando un pool de 4 hilos.
// Demuestra las 4 formas de crear hilos. Los loggers se inyectan por constructor desde Main.
public class GestorTareas {

    private final Logger logger; // psp.log: hilos, fases y resultados
    private final Logger loggerArchivos; // acceso_datos.log: Excel, BBDD y Word

    // AtomicInteger: contador seguro para hilos (no necesita synchronized)
    private final AtomicInteger tareasCompletadas = new AtomicInteger(0);

    public GestorTareas(Logger logger, Logger loggerArchivos) {
        this.logger = logger;
        this.loggerArchivos = loggerArchivos;
    }

    public void ejecutar() throws InterruptedException, ExecutionException, IOException {

        // Como ExecutorService es autoclosable, se puede usar try-with-resources para cerrar el pool automáticamente al salir del bloque.
        try (ExecutorService executor = Executors.newFixedThreadPool(4)) {

        
        // FASE 1  lanza los 4 hilos en paralelo
        // submit() lanza cada tarea sin bloquear el hilo main: los 4 arrancan a la vez.
       
        logger.info("\n======== FASE 1. Hilos en paralelo ========");

        // Hilo 1: Lambda: genera el Excel
        Future<String> futureExcel = executor.submit(() -> {
            logger.info(" Hilo(Lambda - hilo1): " + Thread.currentThread().getName() + " - Estado: " + Thread.currentThread().getState() + " - iniciando generarExcel");
            return GestorArchivos.generarExcel(loggerArchivos);
        });

        // Hilo 2: Clase anónima: lee el número de incidencias de Datos
        Future<String> futureAnonima = executor.submit(new Callable<String>() {
            @Override
            public String call() {
                int total = Datos.cargarIncidencia().size();
                logger.info(" Hilo(Clase anonima): " + Thread.currentThread().getName()
                        + " - Estado: " + Thread.currentThread().getState()
                        + " - leyendo numero de incidencias: " + total);
                return "Clase anonima completada, total de incidencias: " + total;
            }
        });

        // Hilo 3: implements Runnable: loguea nombre y estado del hilo
        // submit(Runnable) devuelve Future<?> cuyo get() retorna null (sin valor de retorno)
        Future<?> futureRunnable = executor.submit(new HiloConRunnable(logger));

        // Hilo 4: extends Thread: el pool crea un hilo que arranca HiloExtendido manualmente.
        // Estado de HiloExtendido: NEW → RUNNABLE → TIMED_WAITING (sleep) → TERMINATED
        Future<String> futureExtendido = executor.submit(() -> {
            HiloExtendido hiloExtendido = new HiloExtendido("HiloExtendido", logger);
            logger.info(" Hilo(Extendido): " + hiloExtendido.getName() + " - Estado: " + hiloExtendido.getState());
            hiloExtendido.start();
            Thread.sleep(50); // esperamos a que entre en sleep() para capturar TIMED_WAITING
            logger.info(" Hilo(Extendido): " + hiloExtendido.getName() + " - Estado durante sleep: " + hiloExtendido.getState());
            hiloExtendido.join(); // hilo4 entra en WAITING hasta que HiloExtendido termine
            logger.info(" Hilo(Extendido): " + hiloExtendido.getName() + " - Estado tras join(): " + hiloExtendido.getState());
            return "HiloExtendido completado";
        });

        // get() bloquea el hilo main hasta que cada Future termina, luego suma 1 al contador
        logger.info("RESULTADO generarExcel (fase 1 - hilo1 - lambda): " + futureExcel.get()); tareasCompletadas.incrementAndGet();
        logger.info("RESULTADO (fase 1 - hilo2 - clase anonima): " + futureAnonima.get()); tareasCompletadas.incrementAndGet();
        futureRunnable.get(); logger.info("RESULTADO (fase 1 - hilo3 - implements Runnable): completado"); tareasCompletadas.incrementAndGet();
        logger.info("RESULTADO (fase 1 - hilo4 - extends Thread): " + futureExtendido.get()); tareasCompletadas.incrementAndGet();

     
        // FASE 2 leerExcel (secuencial: necesita el Excel generado en Fase 1)
     
        logger.info("\n======== FASE 2. leerExcel secuencial ========");
        Future<String> futureBBDD = executor.submit(() -> {
            logger.info("Hilo que ejecuta leerExcel: " + Thread.currentThread().getName());
            return GestorArchivos.leerExcel(loggerArchivos);
        });
        logger.info("RESULTADO leerExcel (fase 2 - secuencial): " + futureBBDD.get()); tareasCompletadas.incrementAndGet();

       
        // FASE 3  generarWord (secuencial: necesita los datos insertados en Fase 2)
    
        logger.info("\n======== FASE 3. generarWord secuencial ========");
        Future<String> futureWord = executor.submit(() -> {
            logger.info("Hilo que ejecuta generarWord: " + Thread.currentThread().getName());
            return GestorArchivos.generarWord(loggerArchivos);
        });
        logger.info("RESULTADO generarWord (fase 3 - secuencial): " + futureWord.get()); tareasCompletadas.incrementAndGet();

        logger.info("Tareas completadas: " + tareasCompletadas.get() + "/6");

        } // fin try-with-resources: close() llama a shutdown() automaticamente
    }
}
