package com.adromi.backendJava.task;

import com.adromi.backendJava.data.Datos;
import com.adromi.backendJava.service.GestorArchivos;

import java.io.IOException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

// Gestiona la ejecucion de tareas usando ExecutorService.
// Demuestra las 4 formas de creacion de hilos integradas en el pool,
// y lanza las operaciones reales del proyecto como Callable.
// Ambos loggers se reciben por constructor desde Main: GestorTareas no crea ninguno internamente.
public class GestorTareas {

    // logger: apunta a psp.log. Solo registra lo de PSP: estados de hilos, fases y resultados.
    private final Logger logger;
    // loggerArchivos: apunta a acceso_datos.log. Se lo pasamos a GestorArchivos para separar los logs.
    private final Logger loggerArchivos;

    // AtomicInteger: contador seguro para hilos.
    // Varios hilos pueden llamar a incrementAndGet() a la vez sin synchronized.
    private final AtomicInteger tareasCompletadas = new AtomicInteger(0);

    // Inyeccion de ambos loggers por constructor: quien llama (Main) decide a que archivo apunta cada uno.
    public GestorTareas(Logger logger, Logger loggerArchivos) {
        this.logger = logger;
        this.loggerArchivos = loggerArchivos;
    }

    public void ejecutar() throws InterruptedException, ExecutionException, IOException {

        // ExecutorService es una interfaz, no se puede instanciar directamente.
        // Su clase concreta es ThreadPoolExecutor, pero es compleja de configurar a mano.
        // Executors es una clase de utilidad (como Math) que simplifica su creacion:
        // newFixedThreadPool(4) hace el new ThreadPoolExecutor(...) por nosotros y devuelve
        // ese objeto como ExecutorService. A partir de aqui usamos executor para lanzar tareas.
        ExecutorService executor = Executors.newFixedThreadPool(4);

        // -----------------------------------------------------------------------
        // FASE 1 — lanzar tareas
        // Los 4 submit() se lanzan seguidos, los 4 hilos arrancan a la vez y trabajan en paralelo.
        // submit() recibe un Callable<String> (interfaz funcional: un solo metodo call()
        // que devuelve un valor) y devuelve un Future<String> (el recibo del resultado).
        // El hilo main NO espera aqui, sigue lanzando los demas submit() sin bloquearse.
        // -----------------------------------------------------------------------

        logger.info("\n======== FASE 1 — 4 hilos en paralelo ========");

        // HILO 1, forma: Creación del hilo con lambda, tarea real: generar Excel.
        // Lambda que implementa Callable<String> porque GestorArchivos.generarExcel devuelve String.
        // Estado del hilo: RUNNABLE mientras genera el archivo.
        Future<String> futureExcel = executor.submit(() -> {
            logger.info(" Hilo(Lambda - hilo1): " + Thread.currentThread().getName() + " - Estado: " + Thread.currentThread().getState() + " - iniciando generarExcel");
            return GestorArchivos.generarExcel(loggerArchivos);
        });

        // HILO 2, forma: Creacion del hilo con clase anónima, tarea real: logguear las incidencias usadas en Datos.
        // Implementa Callable<String> de forma explicita (sin lambda).
        // Loguea su estado y devuelve el numero de incidencias cargadas en Datos (por aplicar algo más realista)
        // Estado del hilo: RUNNABLE mientras ejecuta call().
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

        // HILO 3, forma: Implements Runnable, tarea real: logguear su nombre y estado.
        // submit() tambien acepta Runnable (no solo Callable).
        // En este caso devuelve Future<?> y get() retorna null — solo sirve para esperar.
        // Estado del hilo: RUNNABLE mientras ejecuta run().
        Future<?> futureRunnable = executor.submit(new HiloConRunnable(logger));

        // HILO 4, forma: Extends Thread, gestionado por el pool.
        // extends Thread no es compatible directamente con el pool (el pool gestiona
        // sus propios hilos), pero un hilo del pool puede crear y gestionar uno manualmente.
        // Hilo4 crea HiloExtendido, lo arranca con start() y espera con join().
        // Estado de hilo4: RUNNABLE → WAITING (en join()) → RUNNABLE (cuando HiloExtendido termina)
        // Estado de HiloExtendido: NEW → RUNNABLE → TIMED_WAITING (sleep) → TERMINATED.
        Future<String> futureExtendido = executor.submit(() -> {
            HiloExtendido hiloExtendido = new HiloExtendido("HiloExtendido", logger);
            // Estado NEW: el hilo existe pero aun no ha arrancado
            logger.info(" Hilo(Extendido): " + hiloExtendido.getName() + " - Estado: " + hiloExtendido.getState());
            hiloExtendido.start();
            // Esperamos 50ms para que HiloExtendido entre en su sleep() y capturamos TIMED_WAITING
            Thread.sleep(50);
            logger.info(" Hilo(Extendido): " + hiloExtendido.getName() + " - Estado durante sleep: " + hiloExtendido.getState());
            // join(): hilo4 entra en WAITING hasta que HiloExtendido termine
            hiloExtendido.join();
            // Estado TERMINATED: HiloExtendido ha terminado
            logger.info(" Hilo(Extendido): " + hiloExtendido.getName() + " - Estado tras join(): " + hiloExtendido.getState());
            return "HiloExtendido completado";
        });

        // -----------------------------------------------------------------------
        // FASE 1 — recoger resultados.
        // Cada get() bloquea el hilo MAIN en estado WAITING hasta que ese Future termina.
        // Cuando termina, sumamos 1 al AtomicInteger de forma segura entre hilos.
        // -----------------------------------------------------------------------

        // Hilo main en WAITING → recoge resultado de generarExcel (hilo1)
        logger.info("RESULTADO generarExcel (fase 1 - hilo1 - lambda): " + futureExcel.get()); tareasCompletadas.incrementAndGet();
        // Hilo main en WAITING → recoge resultado de clase anonima (hilo2)
        logger.info("RESULTADO (fase 1 - hilo2 - clase anonima): " + futureAnonima.get()); tareasCompletadas.incrementAndGet();
        // Hilo main en WAITING → recoge resultado de ImplementarRunnable (hilo3)
        // get() devuelve null porque submit(Runnable) no tiene valor de retorno
        futureRunnable.get(); logger.info("RESULTADO (fase 1 - hilo3 - implements Runnable): completado"); tareasCompletadas.incrementAndGet();
        // Hilo main en WAITING → recoge resultado de HiloExtendido (hilo4)
        logger.info("RESULTADO (fase 1 - hilo4 - extends Thread): " + futureExtendido.get()); tareasCompletadas.incrementAndGet();

        // -----------------------------------------------------------------------
        // FASE 2 — leerExcel: depende de que el Excel exista → secuencial.
        // Al terminar Fase 1, los 4 hilos del pool quedan libres.
        // Uno de ellos coge esta tarea. El hilo main lanza submit() y luego
        // llama a get(), entrando en WAITING hasta que ese hilo del pool termina.
        // -----------------------------------------------------------------------
        logger.info("\n======== FASE 2 — leerExcel secuencial ========");
        Future<String> futureBBDD = executor.submit(() -> {
            logger.info("Hilo que ejecuta leerExcel: " + Thread.currentThread().getName());
            return GestorArchivos.leerExcel(loggerArchivos);
        });
        logger.info("RESULTADO leerExcel (fase 2 - secuencial): " + futureBBDD.get()); tareasCompletadas.incrementAndGet();

        // -----------------------------------------------------------------------
        // FASE 3 — generarWord: depende de que los datos esten en la BBDD → secuencial.
        // El hilo del pool que hizo leerExcel queda libre de nuevo.
        // Otro hilo del pool (o el mismo) coge esta tarea. Mismo patron: submit() + get().
        // -----------------------------------------------------------------------
        logger.info("\n======== FASE 3 — generarWord secuencial ========");
        Future<String> futureWord = executor.submit(() -> {
            logger.info("Hilo que ejecuta generarWord: " + Thread.currentThread().getName());
            return GestorArchivos.generarWord(loggerArchivos);
        });
        logger.info("RESULTADO generarWord (fase 3 - secuencial): " + futureWord.get()); tareasCompletadas.incrementAndGet();

        // Resumen final usando el AtomicInteger
        logger.info("Tareas completadas: " + tareasCompletadas.get() + "/6");

        // shutdown(): el pool deja de aceptar tareas nuevas y libera los hilos al terminar
        executor.shutdown();
    }
}
