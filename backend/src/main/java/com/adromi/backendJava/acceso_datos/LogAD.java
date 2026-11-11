package com.adromi.backendJava.acceso_datos;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.*;
import java.util.logging.SimpleFormatter;


public class LogAD {
    private static final Logger logger = Logger.getLogger("Sistema Logs Acceso a Datos");

    private LogAD() {

    } //Constructor vacio

    public static Logger getLogger(String sistemaLogsAccesoADatos) {
        return logger;
    }

    public static void crearLog() throws IOException {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tF %1$tT %4$s %n%5$s%6$s%n"); //modifacamos el formato del sistema para texo más legible

        //Ruta relativa para crear la carpeta que contiene el archivo logs
        String rutaCarpetaLog = "backend/archivos_creados/archivos-log";
        //Nombre archivo para los logs
        String rutaArchivoLog = rutaCarpetaLog + "/acceso_a_datos.log";

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
    }
}
