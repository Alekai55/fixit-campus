# FixIt Campus — Backend Java

Backend del proyecto FixIt Campus que integra en un único punto de entrada las dos partes del módulo: **PSP** (programación concurrente) y **Acceso a Datos** (Excel, BBDD, Word).

---

## Estructura del proyecto

```
src/main/java/com/adromi/backendJava/
├── data/
│   └── Datos.java              — Datos hardcodeados del sistema
├── db/
│   └── Conexion.java           — Conexión a la BBDD (lee credenciales de db.properties)
├── service/
│   └── GestorArchivos.java     — Genera Excel, lee Excel e inserta en BBDD, genera Word
├── task/
│   ├── GestorTareas.java       — ExecutorService, 4 hilos, 3 fases
│   ├── HiloConRunnable.java    — Hilo 3: implements Runnable
│   └── HiloExtendido.java      — Hilo 4: extends Thread
├── util/
│   └── GestorLog.java          — Fábrica de loggers
└── Main.java                   — Punto de entrada único

src/main/resources/
└── db.properties               — Credenciales de la BBDD (no subir al repositorio)
```

---

## Por qué un único Main

La versión original tenía dos puntos de entrada separados: `psp/Main` y `acceso_datos/Main`. El problema era que `GestorTareas` (PSP) necesitaba crear internamente el logger de acceso a datos para pasárselo a `GestorArchivos`, lo que lo acoplaba a algo que no era su responsabilidad.

Con un único `Main` se crean los dos loggers en el único sitio donde tiene sentido — el punto de entrada — y se inyectan en `GestorTareas` por constructor. Así `GestorTareas` no sabe nada de archivos de log: solo recibe lo que necesita.

---

## Credenciales de la BBDD

Las credenciales no están en el código. Se leen del archivo `src/main/resources/db.properties`:

```properties
# Conexion MySQL (Local)
mysql.url=jdbc:mysql://localhost:3306/fixit_campus
mysql.username=root
mysql.password=
```

Cambiar los valores según el entorno. **Este archivo no debe subirse al repositorio.**

---

## Requisitos

- Java 17+
- MySQL con la base de datos `fixit_campus` creada (ejecutar el script SQL del proyecto)
- Maven (dependencias: `poi-ooxml 5.3.0`, `mysql-connector-j 9.1.0`)

---

## Ejecución

1. Crear la BBDD ejecutando el script SQL maestro en DBeaver o MySQL Workbench
2. Ajustar las credenciales en `db.properties` si es necesario
3. Ejecutar `Main.java`

Los archivos generados se guardan en `backend/archivos_creados/`:

| Archivo | Qué es |
|---|---|
| `excel_BDD.xlsx` | Excel con 9 hojas (una por tabla) |
| `informe_BBDD.docx` | Word con el listado de tablas |
| `archivos-log/psp.log` | Log de hilos, fases y resultados |
| `archivos-log/acceso_datos.log` | Log de operaciones Excel, BBDD y Word |

---

## Flujo de ejecución

```
Main
 ├── Crea logger → psp.log
 ├── Crea loggerArchivos → acceso_datos.log
 └── GestorTareas.ejecutar()
      ├── FASE 1 (paralelo): generarExcel · leer incidencias · HiloRunnable · HiloExtendido
      ├── FASE 2 (secuencial): leerExcel → inserta en BBDD
      └── FASE 3 (secuencial): generarWord
```

