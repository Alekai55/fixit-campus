package com.adromi.backendJava.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

// Centraliza la creación de conexiones a la BBDD.
// Las credenciales se leen de db.properties para no tenerlas en el código.
// Cualquier clase que necesite conectarse usa Conexion.getConnection()
// en vez de repetir la URL y credenciales.
public class Conexion {

    // Ruta al archivo de propiedades donde estan las credenciales de la BBDD
    private static final String RUTA_PROPERTIES = "backend/src/main/resources/db.properties";

    private Conexion() {} //no se instancia, solo se usa el metodo estatico

    // Devuelve una nueva conexion a la BBDD lista para usar
    public static Connection getConnection() throws SQLException, IOException {
        Properties props = new Properties();
        // FileInputStream abre el archivo fisico y Properties.load() detecta los '=' y carga los valores
        try (FileInputStream fis = new FileInputStream(RUTA_PROPERTIES)) {
            props.load(fis);
        }
        return DriverManager.getConnection(
            props.getProperty("mysql.url"),
            props.getProperty("mysql.username"),
            props.getProperty("mysql.password")
        );
    }
}
