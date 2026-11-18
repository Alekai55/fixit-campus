package com.adromi.backendJava.acceso_datos;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xdgf.usermodel.XDGFDocument;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.*;

public class Main {
    static Logger logger = LogAD.getLogger();
    static int datos_correctos = 0;
    static int datos_incorrectos = 0;

    // Datos de conexion a la BDD (cambiar por los tuyos)
    private static final String URL = "jdbc:mysql://localhost:3306/fixit_campus";
    private static final String USUARIO_BDD = "root";
    private static final String CLAVE_BDD = "";

    private static List<String> columnas; // Se utiliza para evaluar el nombre de la hoja
    private static List<String[]> filas = null;// Se usa para cargar los datos correspondientes de cada hoja

    // Cabeceras de cada hoja
    private static List<String> rol = new ArrayList<>(List.of("nombre"));
    private static List<String> usuario = new ArrayList<>(List.of("nombre", "apellidos", "email", "contrasena", "id_rol"));
    private static List<String> espacio = new ArrayList<>(List.of("edificio", "planta", "nombre_espacio", "tipo", "en_uso"));
    private static List<String> categoria = new ArrayList<>(List.of("nombre"));
    private static List<String> proveedor = new ArrayList<>(List.of("razon_social", "nif", "especialidad", "telefono", "email", "precio_hora"));
    private static List<String> incidencia = new ArrayList<>(List.of("titulo", "descripcion", "foto_url", "prioridad", "estado", "fecha_creacion", "fecha_objetivo", "fecha_resolucion", "id_espacio", "id_categoria", "id_usuario", "id_usuario_tecnico", "id_proveedor"));
    private static List<String> comentario = new ArrayList<>(List.of("texto", "fecha_creacion", "id_incidencia", "id_usuario"));
    private static List<String> historial_estado = new ArrayList<>(List.of("estado_anterior", "estado_nuevo", "fecha_cambio", "observaciones", "id_incidencia", "id_usuario"));
    private static List<String> historial_prioridad = new ArrayList<>(List.of("prioridad_anterior", "prioridad_nueva", "fecha_cambio", "motivo", "id_incidencia", "id_usuario"));

    public static void main(String[] args) throws IOException, SQLException {
        LogAD.crearLog();
        //generarExcel();
       // leerExcel();
        generarWord();
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

    private static void leerExcel() throws IOException, SQLException {
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
                    String[] datos = new String[columnas.size()];

                    // Bucle de COLUMNAS: una vuelta por cada cabecera (incluye el id, para que las claves foraneas coincidan)
                    for (int c = 0; c < columnas.size(); c++) {
                        // Guardamos cada elemento de la fila en una variable llamada celda para operar con ella
                        Cell celda = fila.getCell(c);
                        // Sentencia id/else en caso de ser nulo lo coloca como "", si no es nulo lo formatea para que sea un String siempre con el DataFormatter
                        datos[c] = (celda == null) ? "" : formato.formatCellValue(celda);
                    }
                    leidas.add(datos);
                }
                leidas.forEach(a -> System.out.println(java.util.Arrays.toString(a)));
                logger.info("Hoja '" + nombreHoja + "': " + leidas.size() + " filas leidas");

                // Se insertan todas las columnas
                insertarHoja(nombreHoja, columnas, leidas);
            }
        }
    }

    // Inserta las filas de una hoja en la tabla con el mismo nombre
    private static void insertarHoja(String nombreHoja, List<String> cols, List<String[]> filasLeidas) throws SQLException {
        // se coloca cols.size -1 porque si no se pondria 1 de mas al agregar el ? final. Si no agregamos el ? final terminaria en un , y daria fallo
        String sql = "INSERT INTO " + nombreHoja + " (" + String.join(", ", cols) + ") VALUES ("+ "?, ".repeat(cols.size() - 1) + "?)";

        try (Connection con = DriverManager.getConnection(URL, USUARIO_BDD, CLAVE_BDD);
             PreparedStatement ps = con.prepareStatement(sql)) {
            for (String[] fila : filasLeidas) {
                for (int i = 0; i < fila.length; i++) {
                    // Leemos toda la fila, valor a valor, asi detectamos si alguno esta vacio y lo colocamos como null para no romper la inserción de datos
                    //sumamos 1 porque asi obtenemos el valor i es 0 y no podemos inyectar nada en la posicion 0
                    ps.setString(i + 1, fila[i].isEmpty() ? null : fila[i]);
                }
                ps.addBatch();
            }
            ps.executeBatch();
            logger.info("Tabla '" + nombreHoja + "': " + filasLeidas.size() + " filas insertadas");
        }
    }
    private static void generarWord(){
        try(XWPFDocument document = new XWPFDocument();FileOutputStream archivo = new FileOutputStream("backend/archivos_creados/informe_BBDD.docx")){
            // Lista de tablas creadas
            List<String> lista = List.of("rol","usuario","espacio","categoria","proveedor","incidencia","comentario","historial_estado","historial_prioridad");
            XWPFParagraph parrafo1 = document.createParagraph();
            parrafo1.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun textoTitulo = parrafo1.createRun();
            textoTitulo.setBold(true);
            textoTitulo.setFontSize(24);
            textoTitulo.setText("Informe de BBDD para acceso a datos");

            XWPFParagraph parrafo2 = document.createParagraph();
            parrafo2.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun tituloTabla = parrafo2.createRun();
            tituloTabla.setFontSize(16);
            tituloTabla.setText("\nTablas Generadas");

            XWPFTable tabla = document.createTable(lista.size() + 1,1);
            tabla.setWidth("100%");//En word web se ve mal, tiene que ser en la version de escritorio
            tabla.setTableAlignment(TableRowAlign.CENTER);
            for (int i = 1; i < tabla.getNumberOfRows(); i++) {
                tabla.getRow(i).getCell(0).setText(lista.get(i-1).toUpperCase());
            }
            document.write(archivo);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
