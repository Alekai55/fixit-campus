package com.adromi.backendJava.data;

import java.util.ArrayList;
import java.util.List;

public class Datos {

    //Creamos todos los datos con new String[] ya que luego los manejaremos por String en vez de datos separados

    public static List<String[]> cargarRol() {
        List<String[]> filas = new ArrayList<>();
        filas.add(new String[]{"1", "ADMIN"});
        filas.add(new String[]{"2", "TECNICO"});
        filas.add(new String[]{"3", "USUARIO"});
        return filas;
    }

    public static List<String[]> cargarUsuario() {
        List<String[]> filas = new ArrayList<>();
        filas.add(new String[]{"1", "Ana", "Administradora", "admin@fixit.es", "1234", "1"});
        filas.add(new String[]{"2", "Carlos", "Técnico IT", "carlos.tech@fixit.es", "1234", "2"});
        filas.add(new String[]{"3", "Lucía", "Mantenimiento", "lucia.mantenimiento@fixit.es", "1234", "2"});
        filas.add(new String[]{"4", "Alejandro", "García", "alex.alumno@fixit.es", "1234", "3"});
        filas.add(new String[]{"5", "María", "Profesora", "maria.profesora@fixit.es", "1234", "3"});
        filas.add(new String[]{"6", "Recepción", "Campus", "recepcion@fixit.es", "1234", "3"});
        return filas;
    }

    public static List<String[]> cargarEspacio() {
        List<String[]> filas = new ArrayList<>();
        filas.add(new String[]{"1", "Edificio Principal", "Planta 0", "El Ágora", "zona_comun", "1"});
        filas.add(new String[]{"2", "Edificio Principal", "Planta 0", "Recepción", "zona_comun", "1"});
        filas.add(new String[]{"3", "Edificio Principal", "Planta 1", "Aula 101", "aula", "1"});
        filas.add(new String[]{"4", "Edificio Principal", "Planta 1", "Sala de Profesores", "departamento", "1"});
        filas.add(new String[]{"5", "Edificio Principal", "Planta 1", "Office / Comedor de Profesores", "zona_comun", "1"});
        filas.add(new String[]{"6", "Edificio Principal", "Planta 2", "Aula 201", "aula", "1"});
        filas.add(new String[]{"7", "Edificio Principal", "Planta 2", "Departamento Tech e Informática", "departamento", "1"});
        filas.add(new String[]{"8", "Edificio Principal", "Planta 3", "Laboratorio de Innovación 301", "laboratorio", "1"});
        filas.add(new String[]{"9", "Edificio Principal", "Planta 4", "Aula 401", "aula", "1"});
        filas.add(new String[]{"10", "Edificio Principal", "Planta 4", "Aula 402", "aula", "1"});
        filas.add(new String[]{"11", "Edificio de Talleres", "Planta 0", "Taller de Electrónica", "taller", "1"});
        filas.add(new String[]{"12", "Edificio de Talleres", "Planta 0", "Taller de Fabricación Digital", "taller", "1"});
        filas.add(new String[]{"13", "Edificio de Talleres", "Planta 0", "Aseos Planta 0", "aseo", "1"});
        filas.add(new String[]{"14", "Edificio de Talleres", "Planta 1", "Laboratorio de Redes", "laboratorio", "1"});
        filas.add(new String[]{"15", "Edificio de Talleres", "Planta 1", "Aula T-101", "aula", "1"});
        filas.add(new String[]{"16", "Edificio de Talleres", "Planta 1", "Aseos Planta 1", "aseo", "1"});
        return filas;
    }

    public static List<String[]> cargarCategoria() {
        List<String[]> filas = new ArrayList<>();
        filas.add(new String[]{"1", "Electricidad"});
        filas.add(new String[]{"2", "Fontanería y Climatización"});
        filas.add(new String[]{"3", "Informática y Audio/Visual"});
        filas.add(new String[]{"4", "Mobiliario y Electrodomésticos"});
        filas.add(new String[]{"5", "Elevación y Ascensores"});
        return filas;
    }

    public static List<String[]> cargarProveedor() {
        List<String[]> filas = new ArrayList<>();
        filas.add(new String[]{"1", "Samsung Electronics España", "A28000001", "Pantallas, Displays y Televisores", "910000101", "soporte.corporate@samsung.com", "45.00"});
        filas.add(new String[]{"2", "Schindler Ascensores S.A.", "A28000002", "Mantenimiento de Ascensores y Montacargas", "910000102", "averias@schindler.es", "55.00"});
        filas.add(new String[]{"3", "Schneider Electric España", "A28000003", "Instalaciones Eléctricas y Cuadros Centrales", "910000103", "servicio.tecnico@se.com", "50.00"});
        filas.add(new String[]{"4", "Dell Technologies Service", "A28000004", "Sistemas informáticos y Monitores", "910000104", "soporte@dell.es", "40.00"});
        filas.add(new String[]{"5", "Clima Madrid S.L.", "B28000005", "Climatización y Ventilación", "910000105", "info@climamadrid.es", "35.00"});
        filas.add(new String[]{"6", "Fontanería y Sanitarios", "B28000006", "Fontanería General y Desatascos", "910000107", "urgencias@fontaneria.es", "30.00"});
        filas.add(new String[]{"7", "Carpintería y Mantenimiento", "B28000007", "Mobiliario, Puertas y Persianas", "910000108", "contacto@carpinteriasur.es", "28.00"});
        filas.add(new String[]{"8", "Redes y Conectividad Global", "B28000008", "Cableado Estructurado y Redes HDMI/AV", "910000109", "soporte@redesglobal.es", "38.00"});
        return filas;
    }

    public static List<String[]> cargarIncidencia() {
        List<String[]> filas = new ArrayList<>();
        filas.add(new String[]{"1", "Rotura cable ascensor", "El ascensor principal está bloqueado.", "fotos/ascensor_planta.jpg", "Urgente", "EN_CURSO", "2026-11-11 08:50:00", "2026-11-11 20:00:00", "", "1", "5", "3", "", "2"});
        filas.add(new String[]{"2", "Pantalla fundida", "La pantalla interactiva Samsung del Aula 401 no enciende.", "fotos/pantalla_planta4.jpg", "Alta", "RESUELTA", "2026-11-10 08:00:00", "2026-11-11 20:00:00", "2026-11-11 13:00:00", "9", "3", "5", "2", "1"});
        filas.add(new String[]{"3", "Fusible quemado", "Sin suministro en el cuadro principal.", "fotos/cuadro_electrico.jpg", "Urgente", "EN_CURSO", "2026-11-11 07:45:00", "2026-11-11 20:00:00", "", "7", "1", "2", "2", "3"});
        filas.add(new String[]{"4", "Monitor estropeado", "El monitor de recepción no reconoce la señal HDMI.", "fotos/hdmi_recepcion.jpg", "Media", "ABIERTA", "2026-11-11 09:10:00", "2026-11-16 20:00:00", "", "2", "3", "6", "", ""});
        filas.add(new String[]{"5", "Microondas del office no calienta", "El microondas enciende pero no calienta.", "fotos/microondas_office.jpg", "Baja", "RESUELTA", "2026-11-07 10:00:00", "2026-11-20 20:00:00", "2026-11-07 13:00:00", "5", "4", "5", "3", ""});
        return filas;
    }

    public static List<String[]> cargarComentario() {
        List<String[]> filas = new ArrayList<>();
        filas.add(new String[]{"1", "Aviso urgente enviado a Schindler Ascensores. Técnico de camino.", "2026-11-11 09:05:00", "1", "3"});
        filas.add(new String[]{"2", "Se confirma la rotura del cable de tracción.", "2026-11-11 11:30:00", "1", "3"});
        return filas;
    }

    public static List<String[]> cargarHistorialEstado() {
        List<String[]> filas = new ArrayList<>();
        filas.add(new String[]{"1", "", "ABIERTA", "2026-11-11 08:50:00", "Incidencia creada", "1", "3"});
        filas.add(new String[]{"2", "ABIERTA", "EN_CURSO", "2026-11-11 09:05:00", "Asignada al proveedor externo Schindler", "1", "3"});
        filas.add(new String[]{"3", "", "ABIERTA", "2026-11-10 08:00:00", "Incidencia creada", "2", "5"});
        filas.add(new String[]{"4", "ABIERTA", "RESUELTA", "2026-11-11 13:00:00", "Samsung sustituye la pantalla", "2", "2"});
        return filas;
    }

    public static List<String[]> cargarHistorialPrioridad() {
        List<String[]> filas = new ArrayList<>();
        filas.add(new String[]{"1", "Media", "Alta", "2026-11-11 09:45:00", "Afecta a todos los equipos del laboratorio", "1", "2"});
        return filas;
    }
}
