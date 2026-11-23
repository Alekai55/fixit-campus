package com.adromi.backendJava;

import com.adromi.backendJava.task.GestorTareas;
import com.adromi.backendJava.util.GestorLog;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

// Un único punto de entrada que crea los dos loggers y los pasa a GestorTareas.
// Comparado con tener psp/Main y acceso_datos/Main por separado:
//   - Antes: loggerArchivos se creaba dentro de GestorTareas (acoplamiento interno).
//   - Ahora: GestorTareas recibe ambos loggers por constructor, no sabe nada de archivos de log.
public class Main {

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        System.out.println("========================================");
        System.out.println("  FixIt Campus — Main Unificado");
        System.out.println("  PSP + Acceso a Datos desde un unico punto de entrada");
        System.out.println("  Los dos loggers se crean aqui y se inyectan en GestorTareas");
        System.out.println("========================================");

        // Ambos loggers nacen aquí: cada uno apunta a su archivo.
        Logger logger = GestorLog.inicializarLogger("FixIt Campus PSP",    "backend/archivos_creados/archivos-log/psp.log");
        Logger loggerArchivos = GestorLog.inicializarLogger("FixIt Campus AD", "backend/archivos_creados/archivos-log/acceso_datos.log");

        // GestorTareas recibe los dos loggers: ya no tiene que crear nada internamente.
        GestorTareas gestorTareas = new GestorTareas(logger, loggerArchivos);
        gestorTareas.ejecutar();

        System.out.println("Ejecucion completada. Logs en:");
        System.out.println("  - psp.log (hilos y fases)");
        System.out.println("  - acceso_datos.log (operaciones de ficheros)");
    }
}
