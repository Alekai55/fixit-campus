package com.adromi.backendJava.util;

import java.io.File;
import java.io.IOException;
import java.util.logging.*;

// Clase de utilidad compartida entre PSP y Acceso a Datos.
// Se encarga de crear y configurar un Logger listo para usar.
public class GestorLog {

    private GestorLog() {} // No se instancia, solo se usa el metodo estatico

    // Crea un Logger con el nombre indicado que escribe en el archivo de la ruta indicada.
    // Si la carpeta no existe, la crea automaticamente.
    public static Logger inicializarLogger(String nombreLog, String rutaArchivo) throws IOException {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tF %1$tT [%4$s] - %5$s%6$s%n");

        // Crear la carpeta del log si no existe
        File archivoLog = new File(rutaArchivo);
        File carpeta = archivoLog.getParentFile();
        if (carpeta != null && !carpeta.exists()) {
            carpeta.mkdirs();
        }

        Logger logger = Logger.getLogger(nombreLog);
        logger.setLevel(Level.ALL);

        // false = sobreescribe el archivo cada ejecucion 
        FileHandler fh = new FileHandler(rutaArchivo, false);
        fh.setLevel(Level.ALL);
        fh.setFormatter(new SimpleFormatter());

        logger.setUseParentHandlers(false); // Evita que los logs aparezcan tambien por consola
        logger.addHandler(fh);

        return logger;
    }
}
