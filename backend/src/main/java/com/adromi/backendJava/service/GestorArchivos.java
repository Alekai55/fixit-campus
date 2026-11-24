package com.adromi.backendJava.service;

import com.adromi.backendJava.data.Datos;
import com.adromi.backendJava.db.Conexion;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

// Agrupa las operaciones de ficheros de la aplicación: generación del Excel con los datos de la BBDD,
// lectura del Excel e inserción en la BBDD, y generación del informe Word.
// El Logger se recibe como parámetro en cada método.
// Cada método devuelve un String con el resumen del resultado, para poder usarlo como Callable<String> desde task.
public class GestorArchivos {

    // Las credenciales de BBDD se centralizan en Conexion.java

    private static List<String> columnas;
    private static List<String[]> filas;

    private static final List<String> rol                 = new ArrayList<>(List.of("id_rol", "nombre"));
    private static final List<String> usuario             = new ArrayList<>(List.of("id_usuario", "nombre", "apellidos", "email", "contrasena", "id_rol"));
    private static final List<String> espacio             = new ArrayList<>(List.of("id_espacio", "edificio", "planta", "nombre_espacio", "tipo", "en_uso"));
    private static final List<String> categoria           = new ArrayList<>(List.of("id_categoria", "nombre"));
    private static final List<String> proveedor           = new ArrayList<>(List.of("id_proveedor", "razon_social", "nif", "especialidad", "telefono", "email", "precio_hora"));
    private static final List<String> incidencia          = new ArrayList<>(List.of("id_incidencia", "titulo", "descripcion", "foto_url", "prioridad", "estado", "fecha_creacion", "fecha_objetivo", "fecha_resolucion", "id_espacio", "id_categoria", "id_usuario", "id_usuario_tecnico", "id_proveedor"));
    private static final List<String> comentario          = new ArrayList<>(List.of("id_comentario", "texto", "fecha_creacion", "id_incidencia", "id_usuario"));
    private static final List<String> historial_estado    = new ArrayList<>(List.of("id_historial_estado", "estado_anterior", "estado_nuevo", "fecha_cambio", "observaciones", "id_incidencia", "id_usuario"));
    private static final List<String> historial_prioridad = new ArrayList<>(List.of("id_historial_prioridad", "prioridad_anterior", "prioridad_nueva", "fecha_cambio", "motivo", "id_incidencia", "id_usuario"));

    // Nombres de las tablas.
    // Se usa en generarExcel, leerExcel y generarWord.
    private static final List<String> nombreTablas = List.of("rol", "usuario", "espacio", "categoria", "proveedor",
            "incidencia", "comentario", "historial_estado", "historial_prioridad");

    private GestorArchivos() {} // No se instancia, solo se usan los metodos estaticos

    public static String generarExcel(Logger logger) throws IOException {
        int datos_correctos = 0;
        int datos_incorrectos = 0;

        logger.info("\n========================================\n  FixIt Campus — Acceso a Datos\n========================================");
        logger.info("\n--- generarExcel ---");

        // Si la carpeta no existe y no se puede crear (permisos, ruta invalida), se registra como error grave.
        File carpetas = new File("backend/archivos_creados");
        if (!carpetas.exists() && !carpetas.mkdirs()) {
            logger.severe("Error en generarExcel: no se pudo crear la carpeta de destino");
        }

        try (XSSFWorkbook libro = new XSSFWorkbook();
             FileOutputStream archivo_excel = new FileOutputStream("backend/archivos_creados/excel_BDD.xlsx")) {

            for (String nombreHoja : nombreTablas) {
                Sheet hoja = libro.createSheet(nombreHoja);
                Row cabecera = hoja.createRow(0);

                // Evaluamos el nombre de la hoja para saber que lista de titulos usar
                // 'filas' guarda los datos de la hoja actual. Cada elemento de la lista es una fila
                // del Excel, y cada texto del array es una columna. El switch de abajo le pide los
                // datos al metodo de Datos que corresponde a esta hoja (cargarRol, cargarUsuario...).
                switch (nombreHoja) {
                    case "rol":                columnas = rol;                filas = Datos.cargarRol();               break;
                    case "usuario":            columnas = usuario;            filas = Datos.cargarUsuario();           break;
                    case "espacio":            columnas = espacio;            filas = Datos.cargarEspacio();           break;
                    case "categoria":          columnas = categoria;          filas = Datos.cargarCategoria();         break;
                    case "proveedor":          columnas = proveedor;          filas = Datos.cargarProveedor();         break;
                    case "incidencia":         columnas = incidencia;         filas = Datos.cargarIncidencia();        break;
                    case "comentario":         columnas = comentario;         filas = Datos.cargarComentario();        break;
                    case "historial_estado":   columnas = historial_estado;   filas = Datos.cargarHistorialEstado();   break;
                    case "historial_prioridad":columnas = historial_prioridad;filas = Datos.cargarHistorialPrioridad();break;
                    default:
                        logger.warning("Hoja desconocida: " + nombreHoja);
                        continue;
                }

                for (int a = 0; a < columnas.size(); a++) {
                    cabecera.createCell(a).setCellValue(columnas.get(a));
                }
                logger.info("Hoja '" + nombreHoja + "' creada con " + columnas.size() + " columnas");

                
                // Este mismo bloque se ejecuta para todas las hojas: lo unico que cambia en cada vuelta
                // es la lista 'filas', que el switch ha pedido al metodo de Datos de esa hoja.
                // Si filas es null, la hoja no tiene datos que cargar y se queda solo con las cabeceras.
                if (filas != null) {
                    // Bucle de FILAS: una vuelta por cada fila de datos
                    for (int i = 0; i < filas.size(); i++) {
                        // i + 1 porque la fila 0 del Excel ya tiene las cabeceras (los titulos)
                        Row fila = hoja.createRow(i + 1);
                        // Fila de datos que toca ahora: un array con un texto por cada columna
                        String[] datos = filas.get(i);
                        // Bucle de COLUMNAS: una vuelta por cada texto de la fila
                        for (int c = 0; c < datos.length; c++) {
                            // try/catch: si una columna falla, se anota en el log y el programa sigue
                            try {
                                // Crea la celda de la columna c y escribe dentro el texto datos[c]
                                fila.createCell(c).setCellValue(datos[c]);
                                datos_correctos++;
                            } catch (Exception e) {
                                // Algo fallo al escribir esta columna: se anota y se cuenta como incorrecta
                                logger.warning("Error al agregar columna " + c + " fila " + (i + 1) + " hoja " + nombreHoja);
                                datos_incorrectos++;
                            }
                        }
                    }
                }
            }
            libro.write(archivo_excel);
        }

        String resumen = "\nExcel generado — correctos: " + datos_correctos + ", incorrectos: " + datos_incorrectos;
        logger.info(resumen);
        return resumen;
    }

    public static String leerExcel(Logger logger) throws IOException, SQLException {
        logger.info("\n--- leerExcel ---");

        // DataFormatter convierte cualquier celda (texto, numero, fecha) a String tal como se ve en Excel
        DataFormatter formato = new DataFormatter();
        int hojasProcesadas = 0;

        // try-with-resources: cierra archivo y libro solos al terminar, aunque haya error
        try (FileInputStream archivo = new FileInputStream("backend/archivos_creados/excel_BDD.xlsx");
             XSSFWorkbook libro = new XSSFWorkbook(archivo)) {

            // Bucle de HOJAS: recorremos la lista de tablas (en orden correcto) y pedimos cada hoja por su nombre
            for (String nombreHoja : nombreTablas) {
                Sheet hoja = libro.getSheet(nombreHoja);
                // getSheet devuelve null si esa hoja no existe en el Excel
                if (hoja == null) {
                    logger.warning("Falta la hoja: " + nombreHoja);
                    continue;
                }

                // Segun la hoja, elegimos que lista de cabeceras corresponde.
                // El break es obligatorio: sin el, Java sigue ejecutando los case de abajo.
                switch (nombreHoja) {
                    case "rol":                columnas = rol;                break;
                    case "usuario":            columnas = usuario;            break;
                    case "espacio":            columnas = espacio;            break;
                    case "categoria":          columnas = categoria;          break;
                    case "proveedor":          columnas = proveedor;          break;
                    case "incidencia":         columnas = incidencia;         break;
                    case "comentario":         columnas = comentario;         break;
                    case "historial_estado":   columnas = historial_estado;   break;
                    case "historial_prioridad":columnas = historial_prioridad;break;
                    default:
                        logger.warning("Hoja desconocida: " + nombreHoja);
                        continue;
                }

                // Aqui guardamos los datos leidos de esta hoja (una fila = un String[])
                List<String[]> leidas = new ArrayList<>();

                // Bucle de FILAS: la fila 0 son las cabeceras, asi que empezamos en 1.
                // getLastRowNum() es el indice de la ultima fila, por eso <= (incluye la ultima).
                for (int f = 1; f <= hoja.getLastRowNum(); f++) {
                    Row fila = hoja.getRow(f);
                    // Una fila totalmente vacia puede venir como null: se ignora
                    if (fila == null) continue;

                    // Crea un array de Strings vacío con tantos huecos como columnas tiene la hoja actual
                    String[] datos = new String[columnas.size()];

                    // Bucle de COLUMNAS: una vuelta por cada cabecera
                    for (int c = 0; c < columnas.size(); c++) {
                        // Guardamos cada elemento de la fila en una variable llamada celda para operar con ella
                        Cell celda = fila.getCell(c);
                        // Intentamos leer la celda. Si falla, lo anotamos en el log y dejamos el valor vacio
                        // para no romper el array y poder seguir con el resto de columnas
                        try {
                            datos[c] = (celda == null) ? "" : formato.formatCellValue(celda);
                        } catch (Exception e) {
                            logger.warning("Error al leer celda columna " + c + " fila " + f + " hoja " + nombreHoja);
                            datos[c] = "";
                        }
                    }
                    leidas.add(datos);
                }

                logger.info("Hoja '" + nombreHoja + "': " + leidas.size() + " filas leidas");

                // Si la inserción falla, paramos: las tablas siguientes pueden depender de esta
                boolean ok = insertarHoja(nombreHoja, columnas, leidas, logger);
                if (!ok) {
                    logger.severe("Inserción detenida en '" + nombreHoja + "' — las tablas siguientes pueden depender de esta.");
                    break;
                }
                hojasProcesadas++;
            }
        }

        String resumen = "\nExcel leido e insertado en BBDD — hojas procesadas: " + hojasProcesadas;
        logger.info(resumen);
        return resumen;
    }

    // Devuelve true si la inserción fue bien, false si falló.
    // leerExcel usa este resultado para parar si una tabla falla.
    private static boolean insertarHoja(String nombreHoja, List<String> cols, List<String[]> filasLeidas, Logger logger) throws IOException {
        // cols.size() - 1 porque si no se pondria 1 de mas al agregar el ? final.
        // Sin restar 1, terminaria en ", ?" y daria fallo de sintaxis SQL.
        String sql = "INSERT IGNORE INTO " + nombreHoja + " (" + String.join(", ", cols) + ") VALUES (" + "?, ".repeat(cols.size() - 1) + "?)";

        // Conexion.getConnection() centraliza las credenciales; aqui solo pedimos la conexion
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            for (String[] fila : filasLeidas) {
                for (int i = 0; i < fila.length; i++) {
                    // Leemos toda la fila valor a valor: si esta vacio lo colocamos como null
                    // para no romper la insercion (sumamos 1 porque PreparedStatement empieza en 1, no en 0)
                    ps.setString(i + 1, fila[i].isEmpty() ? null : fila[i]);
                }
                ps.addBatch();
            }
            // Intentamos ejecutar todas las inserciones de golpe.
            // Si algo falla (clave foranea, dato invalido...), lo registramos en el log como error grave.
            try {
                // executeBatch devuelve un array: 1 si la fila se insertó, 0 si ya existía (INSERT IGNORE la salta)
                int[] resultados = ps.executeBatch();
                int insertadas = 0;
                for (int r : resultados) {
                    if (r > 0) insertadas++;
                }
                logger.info("Tabla '" + nombreHoja + "': " + insertadas + " filas insertadas, " + (filasLeidas.size() - insertadas) + " ya existian");
                return true;
            } catch (SQLException e) {
                logger.severe("Error al insertar en tabla '" + nombreHoja + "': " + e.getMessage());
                return false;
            }
        } catch (SQLException e) {
            // Fallo al abrir la conexion o preparar el statement
            logger.severe("Error de conexion al insertar en tabla '" + nombreHoja + "': " + e.getMessage());
            return false;
        }
    }

    public static String generarWord(Logger logger) throws IOException {
        logger.info("\n--- generarWord ---");

        try (XWPFDocument document = new XWPFDocument();
             FileOutputStream archivo = new FileOutputStream("backend/archivos_creados/informe_BBDD.docx");
             Connection con = Conexion.getConnection()) {

            // Título
            XWPFParagraph parrafo1 = document.createParagraph();
            parrafo1.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun textoTitulo = parrafo1.createRun();
            textoTitulo.setBold(true);
            textoTitulo.setFontSize(24);
            textoTitulo.setText("Informe de BBDD para acceso a datos");

            // Subtítulo
            XWPFParagraph parrafo2 = document.createParagraph();
            parrafo2.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun textoSubtitulo = parrafo2.createRun();
            textoSubtitulo.setFontSize(14);
            textoSubtitulo.setText("FixIt Campus - " + nombreTablas.size() + " tablas");

            // Tabla: 1 fila de cabecera + 1 fila por tabla, 3 columnas
            XWPFTable tabla = document.createTable(nombreTablas.size() + 1, 3);
            tabla.setWidth("100%");

            // Cabecera (fila 0)
            XWPFRun cab1 = tabla.getRow(0).getCell(0).getParagraphs().get(0).createRun();
            cab1.setBold(true);
            cab1.setText("TABLA");
            XWPFRun cab2 = tabla.getRow(0).getCell(1).getParagraphs().get(0).createRun();
            cab2.setBold(true);
            cab2.setText("REGISTROS");
            XWPFRun cab3 = tabla.getRow(0).getCell(2).getParagraphs().get(0).createRun();
            cab3.setBold(true);
            cab3.setText("COLUMNAS");

            // Filas de datos
            for (int i = 1; i < tabla.getNumberOfRows(); i++) {
                String nombre = nombreTablas.get(i - 1);

                // Mismo switch que en generarExcel para elegir la lista de columnas
                switch (nombre) {
                    case "rol":                 columnas = rol;                 break;
                    case "usuario":             columnas = usuario;             break;
                    case "espacio":             columnas = espacio;             break;
                    case "categoria":           columnas = categoria;           break;
                    case "proveedor":           columnas = proveedor;           break;
                    case "incidencia":          columnas = incidencia;          break;
                    case "comentario":          columnas = comentario;          break;
                    case "historial_estado":    columnas = historial_estado;    break;
                    case "historial_prioridad": columnas = historial_prioridad; break;
                }

                // Celda 0: nombre de la tabla en negrita
                XWPFRun celdaNombre = tabla.getRow(i).getCell(0).getParagraphs().get(0).createRun();
                celdaNombre.setBold(true);
                celdaNombre.setText(nombre.toUpperCase());

                // Celda 1: número de registros, con la conexión de Conexion.java
                try (Statement st = con.createStatement();
                     ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM " + nombre)) {
                    rs.next();
                    tabla.getRow(i).getCell(1).setText(rs.getString(1));
                } catch (SQLException e) {
                    logger.warning("No se pudo contar la tabla " + nombre + ": " + e.getMessage());
                }

                // Celda 2: columnas de la tabla
                tabla.getRow(i).getCell(2).setText(String.join(", ", columnas));
            }

            document.write(archivo);
        } catch (IOException | SQLException e) {
            // Si falla al crear o escribir el archivo Word, o al conectar con la BBDD, lo registramos en el log
            logger.severe("Error al generar el Word: " + e.getMessage());
            return "Error al generar el Word";
        }

        String resumen = "Word generado correctamente";
        logger.info(resumen);
        return resumen;
    }
}
