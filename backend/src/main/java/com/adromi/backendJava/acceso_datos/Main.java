package com.adromi.backendJava.acceso_datos;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public class Main {
    static Logger logger = LogAD.getLogger();
    static int datos_correctos = 0;
    static int datos_incorrectos = 0;

    public static void main(String[] args) throws IOException {
        LogAD.crearLog();
        generarExcel();
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

        // Cabeceras de cada hoja
        List<String> rol = new ArrayList<>(List.of("id_rol", "nombre"));
        List<String> usuario = new ArrayList<>(List.of("id_usuario", "nombre", "apellidos", "email", "contrasena", "id_rol"));
        List<String> espacio = new ArrayList<>(List.of("id_espacio", "edificio", "planta", "nombre_espacio", "tipo", "en_uso"));
        List<String> categoria = new ArrayList<>(List.of("id_categoria", "nombre"));
        List<String> proveedor = new ArrayList<>(List.of("id_proveedor", "razon_social", "nif", "especialidad", "telefono", "email", "precio_hora"));
        List<String> incidencia = new ArrayList<>(List.of("id_incidencia", "titulo", "descripcion", "foto_url", "prioridad", "estado", "fecha_creacion", "fecha_objetivo", "fecha_resolucion", "id_espacio", "id_categoria", "id_usuario", "id_usuario_tecnico", "id_proveedor"));
        List<String> comentario = new ArrayList<>(List.of("id_comentario", "texto", "fecha_creacion", "id_incidencia", "id_usuario"));
        List<String> historial_estado = new ArrayList<>(List.of("id_historial_estado", "estado_anterior", "estado_nuevo", "fecha_cambio", "observaciones", "id_incidencia", "id_usuario"));
        List<String> historial_prioridad = new ArrayList<>(List.of("id_historial_prioridad", "prioridad_anterior", "prioridad_nueva", "fecha_cambio", "motivo", "id_incidencia", "id_usuario"));

        //Creamos el libro de excel
        try (XSSFWorkbook libro = new XSSFWorkbook(); FileOutputStream archivo_excel = new FileOutputStream("backend/archivos_creados/excel_BDD.xlsx")) {

            for (String nombreHoja : hojas) {
                //Creamos una hoja en excel
                Sheet hoja = libro.createSheet(nombreHoja);
                //Fila 0 de la hoja: los titulos
                Row cabecera = hoja.createRow(0);

                // Evaluamos el nombre de la hoja para saber que lista de titulos usar
                List<String> columnas;
                // 'filas' guarda los datos de la hoja actual. Cada elemento de la lista es una fila
                // del Excel, y cada texto del array es una columna. El switch de abajo le pide los
                // datos al metodo de Datos que corresponde a esta hoja (cargarRol, cargarUsuario...).
                List<String[]> filas = null;
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
                        // try/catch: si una fila falla, se anota en el log y el programa sigue
                        try {
                            // Bucle de COLUMNAS: una vuelta por cada texto de la fila
                            for (int c = 0; c < datos.length; c++) {
                                // Crea la celda de la columna c y escribe dentro el texto datos[c]
                                // (un texto vacio "" deja la celda vacia)
                                fila.createCell(c).setCellValue(datos[c]);
                                datos_correctos+=1;
                            }
                        } catch (Exception e) {
                            // Algo fallo al escribir esta fila: se anota y, si es incidencia, se cuenta como incorrecta
                            logger.warning("Error al agregar la fila " + (i + 1) + " de la hoja " + nombreHoja);
                            datos_incorrectos += 1;
                        }
                    }
                }
            }

            // Codigo para ejecutar y crear libro
            libro.write(archivo_excel);
            logger.info("-------------------Resumen de datos -------------------\nDatos cargado correctamente: " + datos_correctos+"\nDatos cargados incorrectamente: "+datos_incorrectos);

        }

    }
}