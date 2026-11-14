package com.adromi.backendJava.acceso_datos;

import java.util.ArrayList;
import java.util.List;

public class Datos {

    //Creamos todos los datos con new String[] ya que luego los manejaremos por String en vez de datos separados

    // ROLES (3)
    public static List<String[]> cargarRol() {
        List<String[]> filas = new ArrayList<>();
        filas.add(new String[]{"1", "ADMIN"});
        filas.add(new String[]{"2", "TECNICO"});
        filas.add(new String[]{"3", "USUARIO"});
        return filas;
    }

    // USUARIOS (6): nombre, apellidos, email, contrasena, id_rol
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

    // ESPACIOS (16): edificio, planta, nombre_espacio, tipo, en_uso
    public static List<String[]> cargarEspacio() {
        List<String[]> filas = new ArrayList<>();
        filas.add(new String[]{"1", "Edificio Principal", "Planta 0", "El Ágora", "zona_comun", "true"});
        filas.add(new String[]{"2", "Edificio Principal", "Planta 0", "Recepción", "zona_comun", "true"});
        filas.add(new String[]{"3", "Edificio Principal", "Planta 1", "Aula 101", "aula", "true"});
        filas.add(new String[]{"4", "Edificio Principal", "Planta 1", "Sala de Profesores", "departamento", "true"});
        filas.add(new String[]{"5", "Edificio Principal", "Planta 1", "Office / Comedor de Profesores", "zona_comun", "true"});
        filas.add(new String[]{"6", "Edificio Principal", "Planta 2", "Aula 201", "aula", "true"});
        filas.add(new String[]{"7", "Edificio Principal", "Planta 2", "Departamento Tech e Informática", "departamento", "true"});
        filas.add(new String[]{"8", "Edificio Principal", "Planta 3", "Laboratorio de Innovación 301", "laboratorio", "true"});
        filas.add(new String[]{"9", "Edificio Principal", "Planta 4", "Aula 401", "aula", "true"});
        filas.add(new String[]{"10", "Edificio Principal", "Planta 4", "Aula 402", "aula", "true"});
        filas.add(new String[]{"11", "Edificio de Talleres", "Planta 0", "Taller de Electrónica", "taller", "true"});
        filas.add(new String[]{"12", "Edificio de Talleres", "Planta 0", "Taller de Fabricación Digital", "taller", "true"});
        filas.add(new String[]{"13", "Edificio de Talleres", "Planta 0", "Aseos Planta 0", "aseo", "true"});
        filas.add(new String[]{"14", "Edificio de Talleres", "Planta 1", "Laboratorio de Redes", "laboratorio", "true"});
        filas.add(new String[]{"15", "Edificio de Talleres", "Planta 1", "Aula T-101", "aula", "true"});
        filas.add(new String[]{"16", "Edificio de Talleres", "Planta 1", "Aseos Planta 1", "aseo", "true"});
        return filas;
    }

    // CATEGORIAS (5)
    public static List<String[]> cargarCategoria() {
        List<String[]> filas = new ArrayList<>();
        filas.add(new String[]{"1", "Electricidad"});
        filas.add(new String[]{"2", "Fontanería y Climatización"});
        filas.add(new String[]{"3", "Informática y Audio/Visual"});
        filas.add(new String[]{"4", "Mobiliario y Electrodomésticos"});
        filas.add(new String[]{"5", "Elevación y Ascensores"});
        return filas;
    }

    // PROVEEDORES (8): razon_social, nif, especialidad, telefono, email, precio_hora
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

    // INCIDENCIAS (22): titulo, descripcion, foto_url, prioridad, estado, fecha_creacion, fecha_objetivo,
    // fecha_resolucion, id_espacio, id_categoria, id_usuario, id_usuario_tecnico, id_proveedor
    public static List<String[]> cargarIncidencia() {
        List<String[]> filas = new ArrayList<>();
        filas.add(new String[]{"1", "Rotura cable ascensor",
                "El ascensor principal está bloqueado. Al llamarlo ha sonado un estruendo enorme y no se mueve; está ligeramente descolgado, puede que por uno de sus cables.",
                "fotos/ascensor_planta.jpg", "Urgente", "EN_CURSO", "2026-11-11 08:50:00", "2026-11-11 20:00:00", "", "1", "5", "3", "", "2"});
        filas.add(new String[]{"2", "Pantalla fundida",
                "La pantalla interactiva Samsung del Aula 401 no enciende tras una subida de tensión. Sonó un clic y dejó de emitir imagen.",
                "fotos/pantalla_planta4.jpg", "Alta", "RESUELTA", "2026-11-10 08:00:00", "2026-11-11 20:00:00", "2026-11-11 13:00:00", "9", "3", "5", "2", "1"});
        filas.add(new String[]{"3", "Fusible quemado",
                "Sin suministro en el cuadro principal que alimenta el Departamento Tech. El panel se ha ennegrecido, posiblemente por quemadura.",
                "fotos/cuadro_electrico.jpg", "Urgente", "EN_CURSO", "2026-11-11 07:45:00", "2026-11-11 20:00:00", "", "7", "1", "2", "2", "3"});
        filas.add(new String[]{"4", "Monitor estropeado",
                "El monitor de información de recepción no reconoce la señal del puerto HDMI al conectar un portátil.",
                "fotos/hdmi_recepcion.jpg", "Media", "ABIERTA", "2026-11-11 09:10:00", "2026-11-16 20:00:00", "", "2", "3", "6", "", ""});
        filas.add(new String[]{"5", "Microondas del office no calienta",
                "El microondas del comedor de profesores enciende y gira, pero no calienta la comida.",
                "fotos/microondas_office.jpg", "Baja", "RESUELTA", "2026-11-07 10:00:00", "2026-11-20 20:00:00", "2026-11-07 13:00:00", "5", "4", "5", "3", ""});
        filas.add(new String[]{"6", "Aire acondicionado no enfría",
                "El aire acondicionado del laboratorio expulsa aire caliente y la temperatura supera los 28 ºC.",
                "fotos/aire_lab301.jpg", "Alta", "ABIERTA", "2026-11-09 12:05:00", "2026-11-10 20:00:00", "", "8", "2", "4", "", ""});
        filas.add(new String[]{"7", "Proyector del aula 101 sin imagen",
                "El proyector del Aula 101 enciende, pero no muestra imagen al conectar el portátil por HDMI. Hay clase a las 12:00.",
                "fotos/proyector_aula101.jpg", "Alta", "EN_CURSO", "2026-11-11 08:20:00", "2026-11-12 20:00:00", "", "3", "3", "5", "2", ""});
        filas.add(new String[]{"8", "Enchufes sin corriente",
                "Los enchufes de la fila de la ventana del Aula 401 no tienen corriente.",
                "fotos/enchufes_aula401.jpg", "Media", "ABIERTA", "2026-11-11 10:00:00", "2026-11-16 20:00:00", "", "9", "1", "4", "2", ""});
        filas.add(new String[]{"9", "Sin red wifi en el laboratorio",
                "Ningún equipo del Laboratorio de Innovación 301 se conecta a la red wifi.",
                "fotos/wifi_lab301.jpg", "Alta", "EN_CURSO", "2026-11-11 09:30:00", "2026-11-12 20:00:00", "", "8", "3", "4", "2", "8"});
        filas.add(new String[]{"10", "Persiana atascada",
                "La persiana del Aula 401 se ha quedado atascada a media altura.",
                "fotos/persiana_aula401.jpg", "Media", "ABIERTA", "2026-11-11 11:00:00", "2026-11-16 20:00:00", "", "9", "4", "5", "", ""});
        filas.add(new String[]{"11", "Radiador frío en sala de profesores",
                "El radiador de la sala de profesores no calienta aunque la calefacción está encendida.",
                "fotos/radiador_sala.jpg", "Baja", "CERRADA", "2026-10-23 09:00:00", "2026-11-06 20:00:00", "2026-10-24 12:00:00", "4", "2", "5", "", "5"});
        filas.add(new String[]{"12", "Silla rota",
                "Una de las sillas del Ágora tiene una pata rota y cojea.",
                "fotos/silla_agora.jpg", "Baja", "ABIERTA", "2026-10-22 13:00:00", "2026-11-05 20:00:00", "", "1", "4", "6", "", ""});
        filas.add(new String[]{"13", "Cable eléctrico suelto en la entrada",
                "Hay un cable eléctrico suelto junto a la entrada del Ágora, a la altura del suelo.",
                "fotos/cable_agora.jpg", "Urgente", "ABIERTA", "2026-11-11 12:00:00", "2026-11-11 20:00:00", "", "1", "1", "6", "2", ""});
        filas.add(new String[]{"14", "Fluorescente fundido en el Aula 201",
                "Dos fluorescentes del Aula 201 parpadean y uno se ha fundido del todo.",
                "fotos/fluorescente_aula201.jpg", "Media", "CERRADA", "2026-11-02 09:00:00", "2026-11-05 20:00:00", "2026-11-03 13:00:00", "6", "1", "5", "3", ""});
        filas.add(new String[]{"15", "Diferencial saltado en el Aula 101",
                "Ha saltado el diferencial del Aula 101 y no hay luz ni enchufes en toda el aula.",
                "fotos/diferencial_aula101.jpg", "Alta", "CERRADA", "2026-11-04 08:30:00", "2026-11-05 20:00:00", "2026-11-04 11:30:00", "3", "1", "4", "3", ""});
        filas.add(new String[]{"16", "Ascensor detenido entre plantas",
                "El ascensor se ha detenido entre la planta 0 y la 1 sin nadie dentro y no responde a la llamada.",
                "fotos/ascensor_detenido.jpg", "Urgente", "CERRADA", "2026-10-27 10:00:00", "2026-10-27 20:00:00", "2026-10-27 15:00:00", "1", "5", "6", "", "2"});
        filas.add(new String[]{"17", "Ordenador del profesor no arranca",
                "El ordenador del puesto del profesor del Aula 402 no pasa de la pantalla de inicio.",
                "fotos/pc_aula402.jpg", "Alta", "CERRADA", "2026-11-05 08:00:00", "2026-11-06 20:00:00", "2026-11-05 12:00:00", "10", "3", "5", "2", ""});
        filas.add(new String[]{"18", "Puerta del Aula 201 no cierra",
                "La puerta del Aula 201 roza con el marco y no se puede cerrar con llave.",
                "fotos/puerta_aula201.jpg", "Media", "CERRADA", "2026-10-29 09:00:00", "2026-11-03 20:00:00", "2026-10-30 12:00:00", "6", "4", "4", "", "7"});
        // Edificio de Talleres
        filas.add(new String[]{"19", "Fuga de agua en los aseos",
                "Sale agua de una tubería bajo los lavabos de los aseos de la planta 0 y el suelo está encharcado hasta el pasillo.",
                "fotos/fuga_aseos_talleres.jpg", "Urgente", "CERRADA", "2026-11-06 09:00:00", "2026-11-06 20:00:00", "2026-11-06 14:00:00", "13", "2", "6", "", "6"});
        filas.add(new String[]{"20", "Puestos de soldadura sin corriente",
                "Los puestos de soldadura del Taller de Electrónica no tienen corriente; no se puede dar la práctica.",
                "fotos/soldadura_taller.jpg", "Alta", "CERRADA", "2026-11-03 08:30:00", "2026-11-04 20:00:00", "2026-11-03 16:30:00", "11", "1", "5", "", "3"});
        filas.add(new String[]{"21", "Switch del laboratorio de redes averiado",
                "El switch principal del Laboratorio de Redes tiene todos los puertos apagados y ningún puesto tiene red.",
                "fotos/switch_lab_redes.jpg", "Alta", "RESUELTA", "2026-11-09 08:00:00", "2026-11-10 20:00:00", "2026-11-10 10:00:00", "14", "3", "4", "", "8"});
        filas.add(new String[]{"22", "Cisterna que no descarga",
                "La cisterna del segundo inodoro de los aseos de la planta 1 no descarga al pulsar el botón.",
                "fotos/cisterna_aseos_p1.jpg", "Media", "ABIERTA", "2026-11-10 11:00:00", "2026-11-13 20:00:00", "", "16", "2", "5", "", ""});
        return filas;
    }

    // COMENTARIOS (8): texto, fecha_creacion, id_incidencia, id_usuario
    public static List<String[]> cargarComentario() {
        List<String[]> filas = new ArrayList<>();
        filas.add(new String[]{"1", "Aviso urgente enviado a Schindler Ascensores. Técnico de camino.", "2026-11-11 09:05:00", "1", "3"});
        filas.add(new String[]{"2", "Se confirma la rotura del cable de tracción. Se contacta con el responsable de la garantía del ascensor.", "2026-11-11 11:30:00", "1", "3"});
        filas.add(new String[]{"3", "Se procede a tramitar la garantía directamente con Samsung España para la pantalla del Aula 401.", "2026-11-10 09:00:00", "2", "2"});
        filas.add(new String[]{"4", "Cuando conecto el portátil al monitor por HDMI no aparece imagen.", "2026-11-11 09:12:00", "4", "6"});
        filas.add(new String[]{"5", "Hace mucho calor en el laboratorio, cuesta trabajar con los equipos encendidos.", "2026-11-09 12:10:00", "6", "4"});
        filas.add(new String[]{"6", "Lo necesito para la clase de las 12:00, ¿se podrá arreglar hoy?", "2026-11-11 08:25:00", "7", "5"});
        filas.add(new String[]{"7", "Revisado: falla el cable HDMI del aula. Lo sustituyo esta mañana.", "2026-11-11 08:40:00", "7", "2"});
        filas.add(new String[]{"8", "El agua llega hasta el pasillo; hemos cerrado los aseos de la planta 0.", "2026-11-06 09:05:00", "19", "6"});
        return filas;
    }

    // HISTORIAL DE ESTADOS (56): estado_anterior, estado_nuevo, fecha_cambio, observaciones, id_incidencia, id_usuario
    public static List<String[]> cargarHistorialEstado() {
        List<String[]> filas = new ArrayList<>();
        // Incidencia 1
        filas.add(new String[]{"1", "", "ABIERTA", "2026-11-11 08:50:00", "Incidencia creada", "1", "3"});
        filas.add(new String[]{"2", "ABIERTA", "EN_CURSO", "2026-11-11 09:05:00", "Incidencia asignada al proveedor externo Schindler", "1", "3"});
        // Incidencia 2
        filas.add(new String[]{"3", "", "ABIERTA", "2026-11-10 08:00:00", "Incidencia creada", "2", "5"});
        filas.add(new String[]{"4", "ABIERTA", "EN_CURSO", "2026-11-10 09:00:00", "Garantía tramitada con Samsung España", "2", "2"});
        filas.add(new String[]{"5", "EN_CURSO", "RESUELTA", "2026-11-11 13:00:00", "Samsung sustituye la pantalla del Aula 401", "2", "2"});
        // Incidencia 3
        filas.add(new String[]{"6", "", "ABIERTA", "2026-11-11 07:45:00", "Incidencia creada", "3", "2"});
        filas.add(new String[]{"7", "ABIERTA", "EN_CURSO", "2026-11-11 08:00:00", "Asignada al proveedor Schneider Electric para reemplazo del fusible principal", "3", "2"});
        // Incidencia 4
        filas.add(new String[]{"8", "", "ABIERTA", "2026-11-11 09:10:00", "Incidencia creada", "4", "6"});
        // Incidencia 5
        filas.add(new String[]{"9", "", "ABIERTA", "2026-11-07 10:00:00", "Incidencia creada", "5", "5"});
        filas.add(new String[]{"10", "ABIERTA", "EN_CURSO", "2026-11-07 11:00:00", "Lucía revisa el microondas del office", "5", "3"});
        filas.add(new String[]{"11", "EN_CURSO", "RESUELTA", "2026-11-07 13:00:00", "Microondas sustituido por uno de repuesto por el equipo interno", "5", "3"});
        // Incidencia 6
        filas.add(new String[]{"12", "", "ABIERTA", "2026-11-09 12:05:00", "Incidencia creada", "6", "4"});
        // Incidencia 7
        filas.add(new String[]{"13", "", "ABIERTA", "2026-11-11 08:20:00", "Incidencia creada", "7", "5"});
        filas.add(new String[]{"14", "ABIERTA", "EN_CURSO", "2026-11-11 08:36:00", "Asignada a Carlos Técnico IT", "7", "2"});
        // Incidencia 8
        filas.add(new String[]{"15", "", "ABIERTA", "2026-11-11 10:00:00", "Incidencia creada", "8", "4"});
        // Incidencia 9
        filas.add(new String[]{"16", "", "ABIERTA", "2026-11-11 09:30:00", "Incidencia creada", "9", "4"});
        filas.add(new String[]{"17", "ABIERTA", "EN_CURSO", "2026-11-11 10:00:00", "Asignada al proveedor Redes y Conectividad Global", "9", "2"});
        // Incidencia 10
        filas.add(new String[]{"18", "", "ABIERTA", "2026-11-11 11:00:00", "Incidencia creada", "10", "5"});
        // Incidencia 11
        filas.add(new String[]{"19", "", "ABIERTA", "2026-10-23 09:00:00", "Incidencia creada", "11", "5"});
        filas.add(new String[]{"20", "ABIERTA", "EN_CURSO", "2026-10-23 10:00:00", "Asignada al proveedor Clima Madrid", "11", "3"});
        filas.add(new String[]{"21", "EN_CURSO", "RESUELTA", "2026-10-24 12:00:00", "Clima Madrid purga el circuito y el radiador vuelve a calentar", "11", "3"});
        filas.add(new String[]{"22", "RESUELTA", "CERRADA", "2026-10-27 10:00:00", "Administración confirma la reparación y cierra la incidencia", "11", "1"});
        // Incidencia 12
        filas.add(new String[]{"23", "", "ABIERTA", "2026-10-22 13:00:00", "Incidencia creada", "12", "6"});
        // Incidencia 13
        filas.add(new String[]{"24", "", "ABIERTA", "2026-11-11 12:00:00", "Incidencia creada", "13", "6"});
        // Incidencia 14
        filas.add(new String[]{"25", "", "ABIERTA", "2026-11-02 09:00:00", "Incidencia creada", "14", "5"});
        filas.add(new String[]{"26", "ABIERTA", "EN_CURSO", "2026-11-02 10:00:00", "Asignada a Lucía Mantenimiento", "14", "3"});
        filas.add(new String[]{"27", "EN_CURSO", "RESUELTA", "2026-11-03 13:00:00", "Fluorescentes sustituidos", "14", "3"});
        filas.add(new String[]{"28", "RESUELTA", "CERRADA", "2026-11-04 09:00:00", "Administración cierra la incidencia", "14", "1"});
        // Incidencia 15
        filas.add(new String[]{"29", "", "ABIERTA", "2026-11-04 08:30:00", "Incidencia creada", "15", "4"});
        filas.add(new String[]{"30", "ABIERTA", "EN_CURSO", "2026-11-04 08:45:00", "Asignada a Lucía Mantenimiento", "15", "3"});
        filas.add(new String[]{"31", "EN_CURSO", "RESUELTA", "2026-11-04 11:30:00", "Diferencial rearmado; el fallo venía de una regleta del puesto 12", "15", "3"});
        filas.add(new String[]{"32", "RESUELTA", "CERRADA", "2026-11-05 09:00:00", "Administración cierra la incidencia", "15", "1"});
        // Incidencia 16
        filas.add(new String[]{"33", "", "ABIERTA", "2026-10-27 10:00:00", "Incidencia creada", "16", "6"});
        filas.add(new String[]{"34", "ABIERTA", "EN_CURSO", "2026-10-27 10:15:00", "Incidencia asignada al proveedor externo Schindler", "16", "3"});
        filas.add(new String[]{"35", "EN_CURSO", "RESUELTA", "2026-10-27 15:00:00", "Schindler reinicia el controlador y revisa los frenos", "16", "3"});
        filas.add(new String[]{"36", "RESUELTA", "CERRADA", "2026-10-28 09:00:00", "Administración cierra la incidencia", "16", "1"});
        // Incidencia 17
        filas.add(new String[]{"37", "", "ABIERTA", "2026-11-05 08:00:00", "Incidencia creada", "17", "5"});
        filas.add(new String[]{"38", "ABIERTA", "EN_CURSO", "2026-11-05 08:20:00", "Asignada a Carlos Técnico IT", "17", "2"});
        filas.add(new String[]{"39", "EN_CURSO", "RESUELTA", "2026-11-05 12:00:00", "Disco duro sustituido y sistema reinstalado", "17", "2"});
        filas.add(new String[]{"40", "RESUELTA", "CERRADA", "2026-11-06 09:00:00", "Administración cierra la incidencia", "17", "1"});
        // Incidencia 18
        filas.add(new String[]{"41", "", "ABIERTA", "2026-10-29 09:00:00", "Incidencia creada", "18", "4"});
        filas.add(new String[]{"42", "ABIERTA", "EN_CURSO", "2026-10-29 11:00:00", "Asignada al proveedor Carpintería y Mantenimiento", "18", "3"});
        filas.add(new String[]{"43", "EN_CURSO", "RESUELTA", "2026-10-30 12:00:00", "Puerta cepillada y bisagras ajustadas", "18", "3"});
        filas.add(new String[]{"44", "RESUELTA", "CERRADA", "2026-10-31 09:00:00", "Administración cierra la incidencia", "18", "1"});
        // Incidencia 19
        filas.add(new String[]{"45", "", "ABIERTA", "2026-11-06 09:00:00", "Incidencia creada", "19", "6"});
        filas.add(new String[]{"46", "ABIERTA", "EN_CURSO", "2026-11-06 09:15:00", "Asignada al proveedor Fontanería y Sanitarios", "19", "3"});
        filas.add(new String[]{"47", "EN_CURSO", "RESUELTA", "2026-11-06 14:00:00", "Tubería sellada y llave de paso sustituida", "19", "3"});
        filas.add(new String[]{"48", "RESUELTA", "CERRADA", "2026-11-09 09:00:00", "Administración cierra la incidencia", "19", "1"});
        // Incidencia 20
        filas.add(new String[]{"49", "", "ABIERTA", "2026-11-03 08:30:00", "Incidencia creada", "20", "5"});
        filas.add(new String[]{"50", "ABIERTA", "EN_CURSO", "2026-11-03 09:00:00", "Asignada al proveedor Schneider Electric", "20", "3"});
        filas.add(new String[]{"51", "EN_CURSO", "RESUELTA", "2026-11-03 16:30:00", "Sustituido el magnetotérmico de la línea de los puestos de soldadura", "20", "3"});
        filas.add(new String[]{"52", "RESUELTA", "CERRADA", "2026-11-04 09:00:00", "Administración cierra la incidencia", "20", "1"});
        // Incidencia 21
        filas.add(new String[]{"53", "", "ABIERTA", "2026-11-09 08:00:00", "Incidencia creada", "21", "4"});
        filas.add(new String[]{"54", "ABIERTA", "EN_CURSO", "2026-11-09 08:30:00", "Asignada al proveedor Redes y Conectividad Global", "21", "2"});
        filas.add(new String[]{"55", "EN_CURSO", "RESUELTA", "2026-11-10 10:00:00", "Switch sustituido y puertos reconfigurados", "21", "2"});
        // Incidencia 22
        filas.add(new String[]{"56", "", "ABIERTA", "2026-11-10 11:00:00", "Incidencia creada", "22", "5"});
        return filas;
    }

    // HISTORIAL DE PRIORIDAD (2): prioridad_anterior, prioridad_nueva, fecha_cambio, motivo, id_incidencia, id_usuario
    public static List<String[]> cargarHistorialPrioridad() {
        List<String[]> filas = new ArrayList<>();
        filas.add(new String[]{"1", "Media", "Alta", "2026-11-11 09:45:00", "Afecta a todos los equipos del laboratorio: impide dar clase", "9", "2"});
        filas.add(new String[]{"2", "Baja", "Media", "2026-11-11 11:30:00", "La persiana impide oscurecer el aula para proyectar", "10", "3"});
        return filas;
    }
}