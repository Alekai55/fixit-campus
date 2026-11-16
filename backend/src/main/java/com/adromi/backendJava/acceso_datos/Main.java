package com.adromi.backendJava.acceso_datos;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.*;

public class Main {
    static Logger logger = LogAD.getLogger();
    static int datos_correctos = 0;
    static int datos_incorrectos = 0;

    private static List<String> columnas; // Se utiliza para evaluar el nombre de la hoja
    private static List<String[]> filas = null;// Se usa para cargar los datos correspondientes de cada hoja

    // Cabeceras de cada hoja
    private static List<String> rol = new ArrayList<>(List.of("id_rol", "nombre"));
    private static List<String> usuario = new ArrayList<>(List.of("id_usuario", "nombre", "apellidos", "email", "contrasena", "id_rol"));
    private static List<String> espacio = new ArrayList<>(List.of("id_espacio", "edificio", "planta", "nombre_espacio", "tipo", "en_uso"));
    private static List<String> categoria = new ArrayList<>(List.of("id_categoria", "nombre"));
    private static List<String> proveedor = new ArrayList<>(List.of("id_proveedor", "razon_social", "nif", "especialidad", "telefono", "email", "precio_hora"));
    private static List<String> incidencia = new ArrayList<>(List.of("id_incidencia", "titulo", "descripcion", "foto_url", "prioridad", "estado", "fecha_creacion", "fecha_objetivo", "fecha_resolucion", "id_espacio", "id_categoria", "id_usuario", "id_usuario_tecnico", "id_proveedor"));
    private static List<String> comentario = new ArrayList<>(List.of("id_comentario", "texto", "fecha_creacion", "id_incidencia", "id_usuario"));
    private static List<String> historial_estado = new ArrayList<>(List.of("id_historial_estado", "estado_anterior", "estado_nuevo", "fecha_cambio", "observaciones", "id_incidencia", "id_usuario"));
    private static List<String> historial_prioridad = new ArrayList<>(List.of("id_historial_prioridad", "prioridad_anterior", "prioridad_nueva", "fecha_cambio", "motivo", "id_incidencia", "id_usuario"));

    public static void main(String[] args) throws IOException {
        LogAD.crearLog();
        generarExcel();
        leerExcel();
    }

    private static void generarExcel() throws IOException {
        // Generar estructura de carpetas donde se almacenaran los archivos generados
        File carpetas = new File("backend/archivos_creados");
        if (!carpetas.exists()){
            carpetas.mkdirs();
        }

        // Nombres de las hojas del libro
        List<String> hojas = new ArrayList<>(List.of(
                "rol", "usuario", "espacio", "categoria", "proveedor",
                "incidencia", "comentario", "historial_estado", "historial_prioridad"));


        //Creamos el libro de excel
        try (XSSFWorkbook libro = new XSSFWorkbook(); FileOutputStream archivo_excel = new FileOutputStream("backend/archivos_creados/excel_BDD.xlsx")) {

            for (String nombreHoja : hojas) {
                //Creamos una hoja en excel
                Sheet hoja = libro.createSheet(nombreHoja);
                //Fila 0 de la hoja: los titulos
                Row cabecera = hoja.createRow(0);

                // Evaluamos el nombre de la hoja para saber que lista de titulos usar
                // 'filas' guarda los datos de la hoja actual. Cada elemento de la lista es una fila
                // del Excel, y cada texto del array es una columna. El switch de abajo le pide los
                // datos al metodo de Datos que corresponde a esta hoja (cargarRol, cargarUsuario...).

                switch (nombreHoja) {
                    case "rol": columnas = rol; filas = Datos.cargarRol(); break;
                    case "usuario": columnas = usuario; filas = Datos.cargarUsuario(); break;
                    case "espacio": columnas = espacio; filas = Datos.cargarEspacio(); break;
                    case "categoria": columnas = categoria; filas = Datos.cargarCategoria(); break;
                    case "proveedor": columnas = proveedor; filas = Datos.cargarProveedor(); break;
                    case "incidencia": columnas = incidencia; filas = Datos.cargarIncidencia(); break;
                    case "comentario": columnas = comentario; filas = Datos.cargarComentario(); break;
                    case "historial_estado": columnas = historial_estado; filas = Datos.cargarHistorialEstado(); break;
                    case "historial_prioridad": columnas = historial_prioridad; filas = Datos.cargarHistorialPrioridad(); break;
                    default:
                        logger.warning("Hoja desconocida, se omiten sus cabeceras: " + nombreHoja);
                        continue;
                }

                for (int a = 0; a < columnas.size(); a++) {
                    cabecera.createCell(a).setCellValue(columnas.get(a));
                }
                logger.info("Hoja '" + nombreHoja + "' creada con " + columnas.size() + " columnas");

                // ---------- RELLENAR LA HOJA CON LOS DATOS ----------
                // Este mismo bloque se ejecuta para TODAS las hojas: lo unico que cambia en cada vuelta
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
                                // (un texto vacio "" deja la celda vacia)
                                fila.createCell(c).setCellValue(datos[c]);
                                datos_correctos+=1;
                            } catch (Exception e) {
                                // Algo fallo al escribir esta columna: se anota y se cuenta como incorrecta
                                logger.warning("Error al agregar la columna " + c + " de la fila " + (i + 1) + " de la hoja " + nombreHoja);
                                datos_incorrectos += 1;
                            }
                        }
                    }
                }
            }

            // Codigo para ejecutar y crear libro
            libro.write(archivo_excel);
            logger.info("-------------------Resumen de datos -------------------\nDatos cargado correctamente: " + datos_correctos+"\nDatos cargados incorrectamente: "+datos_incorrectos);

        }

    }

    private static void leerExcel() throws IOException {
        // DataFormatter convierte cualquier celda (texto, numero, fecha) a String tal como se ve en Excel
        DataFormatter formato = new DataFormatter();

        // try-with-resources: cierra archivo y libro solos al terminar, aunque haya error
        try (FileInputStream archivo = new FileInputStream("backend/archivos_creados/excel_BDD.xlsx");
             XSSFWorkbook libro = new XSSFWorkbook(archivo)) {

            // Bucle de HOJAS: una vuelta por cada hoja del libro
            for (int i = 0; i < libro.getNumberOfSheets(); i++) {
                Sheet hoja = libro.getSheetAt(i);
                String nombreHoja = hoja.getSheetName();

                // Segun la hoja, elegimos que lista de cabeceras corresponde.
                // El break es obligatorio: sin el, Java sigue ejecutando los case de abajo.
                switch (nombreHoja) {
                    case "rol": columnas = rol; break;
                    case "usuario": columnas = usuario; break;
                    case "espacio": columnas = espacio; break;
                    case "categoria": columnas = categoria; break;
                    case "proveedor": columnas = proveedor; break;
                    case "incidencia": columnas = incidencia; break;
                    case "comentario": columnas = comentario; break;
                    case "historial_estado": columnas = historial_estado; break;
                    case "historial_prioridad": columnas = historial_prioridad; break;
                    default:
                        // Hoja que no conocemos: se avisa en el log y se salta a la siguiente
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
                    String[] datos = new String[columnas.size()-1];

                    // Bucle de COLUMNAS: una vuelta por cada cabecera
                    for (int c = 1; c < columnas.size(); c++) {
                        // Guardamos cada elemento de la fila en una variable llamada celda para operar con ella
                        Cell celda = fila.getCell(c);
                        // Sentencia id/else en caso de ser nulo lo coloca como "", si no es nulo lo formatea para que sea un String siempre con el DataFormatter
                        datos[c-1] = (celda == null) ? "" : formato.formatCellValue(celda);
                    }
                    leidas.add(datos);
                }
                leidas.forEach(a -> System.out.println(java.util.Arrays.toString(a)));
                logger.info("Hoja '" + nombreHoja + "': " + leidas.size() + " filas leidas");
            }
        }
    }
}
