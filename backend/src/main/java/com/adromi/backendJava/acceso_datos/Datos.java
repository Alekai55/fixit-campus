package com.adromi.backendJava.acceso_datos;

import java.util.ArrayList;
import java.util.List;

public class Datos {

    //Creamos todos los datos con new String[] ya que luego los manejaremos por String en vez de datos separads

    public static List<String[]> cargarRol() {
        List<String[]> filas = new ArrayList<>();
        filas.add(new String[]{"1", "Administrador"});
        filas.add(new String[]{"2", "Usuario"});
        filas.add(new String[]{"3", "Tecnico"});
        return filas;
    }

    public static List<String[]> cargarUsuario() {
        List<String[]> filas = new ArrayList<>();
        filas.add(new String[]{"7", "Pedro", "Lopez", "pedro@mail.com", "1234", "3"});
        filas.add(new String[]{"12", "Ana", "Garcia", "ana@mail.com", "1234", "2"});
        filas.add(new String[]{"23", "Luis", "Perez", "luis@mail.com", "1234", "2"});
        return filas;
    }

    public static List<String[]> cargarEspacio() {
        List<String[]> filas = new ArrayList<>();
        filas.add(new String[]{"45", "A", "0", "Aseos", "Aseos", "true"});
        filas.add(new String[]{"102", "B", "1", "Sala de Reuniones B", "Sala", "true"});
        return filas;
    }

    public static List<String[]> cargarCategoria() {
        List<String[]> filas = new ArrayList<>();
        filas.add(new String[]{"2", "Fontaneria"});
        filas.add(new String[]{"4", "Audiovisual"});
        return filas;
    }

    public static List<String[]> cargarProveedor() {
        List<String[]> filas = new ArrayList<>();
        filas.add(new String[]{"3", "Fontaneria Sanchez", "B12345678", "Fontaneria", "600111222", "fsanchez@mail.com", "30.0"});
        return filas;
    }

    public static List<String[]> cargarIncidencia() {
        List<String[]> filas = new ArrayList<>();
        filas.add(new String[]{"1", "Proyector averiado en Sala de Reuniones B",
                "El proyector principal no da imagen y la luz de estado parpadea en rojo.",
                "https://midominio.com/archivos/img_incidencia_001.jpg", "Alta", "Abierta",
                "2026-11-09 09:15", "2026-11-10 12:00", "", "102", "4", "23", "", ""});
        filas.add(new String[]{"2", "Fuga de agua en lavabos de la planta baja",
                "El lavabo numero 3 tiene un goteo constante en la tuberia inferior.",
                "https://midominio.com/archivos/img_incidencia_002.jpg", "Media", "En progreso",
                "2026-11-09 11:30", "2026-11-09 18:00", "", "45", "2", "12", "7", "3"});
        return filas;
    }

    public static List<String[]> cargarComentario() {
        List<String[]> filas = new ArrayList<>();
        filas.add(new String[]{"1", "Ya he avisado al tecnico", "2026-11-09 12:00", "2", "12"});
        filas.add(new String[]{"2", "Revisando el proyector", "2026-11-09 10:00", "1", "23"});
        return filas;
    }

    public static List<String[]> cargarHistorialEstado() {
        List<String[]> filas = new ArrayList<>();
        filas.add(new String[]{"1", "Abierta", "En progreso", "2026-11-09 11:45", "Asignada a tecnico", "2", "7"});
        return filas;
    }

    public static List<String[]> cargarHistorialPrioridad() {
        List<String[]> filas = new ArrayList<>();
        filas.add(new String[]{"1", "Baja", "Media", "2026-11-09 11:40", "Empeora la fuga", "2", "7"});
        return filas;
    }
}
