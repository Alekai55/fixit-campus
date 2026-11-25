# FixIt Campus — Backend Java

Backend del proyecto FixIt Campus que integra en un único punto de entrada las dos partes del módulo: **PSP**  y **Acceso a Datos**.

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

En un proyecto real, PSP y Acceso a Datos no son módulos independientes: las operaciones de ficheros y BBDD se ejecutan dentro de los hilos. Tener dos puntos de entrada separados rompía esa lógica y obligaba a arrancar el sistema dos veces.

Con un único `Main` el arranque es uno solo, los dos loggers se crean en el punto de entrada y se inyectan en `GestorTareas` por constructor. Cada parte hace lo suyo sin saber nada de la otra, y el sistema funciona como una única aplicación cohesionada.

---

## Credenciales de la BBDD

Las credenciales no están en el código y el archivo **no está en el repositorio** (está en el `.gitignore`). Hay que crearlo manualmente en `src/main/resources/db.properties` con este contenido:

```properties
# Conexion MySQL (Local)
mysql.url=jdbc:mysql://localhost:3306/fixit_campus
mysql.username=root
mysql.password=
```

Ajustar los valores según el entorno. Sin este archivo el programa no arranca.

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

## Logs

### acceso_datos.log — Operaciones de ficheros y BBDD

| Nivel | Dónde | Qué registra |
|---|---|---|
| `INFO` | `generarExcel` | Cabecera de inicio, hojas creadas y resumen de celdas correctas/incorrectas |
| `WARNING` | `generarExcel` | Error al escribir una celda concreta (columna + fila + hoja) |
| `SEVERE` | `generarExcel` | No se pudo crear la carpeta de destino |
| `INFO` | `leerExcel` | Filas leídas por hoja y resumen final de hojas procesadas |
| `WARNING` | `leerExcel` | Hoja no encontrada en el Excel o hoja desconocida |
| `WARNING` | `leerExcel` | Error al leer una celda concreta (columna + fila + hoja) |
| `SEVERE` | `leerExcel` | Proceso detenido porque una tabla falló y las siguientes dependen de ella |
| `INFO` | `insertarHoja` | Filas insertadas y filas ya existentes por tabla (`INSERT IGNORE`) |
| `SEVERE` | `insertarHoja` | Error al ejecutar el batch (FK, dato inválido…) o al abrir la conexión |
| `INFO` | `generarWord` | Inicio y confirmación de Word generado correctamente |
| `WARNING` | `generarWord` | No se pudo contar los registros de una tabla |
| `SEVERE` | `generarWord` | Error al crear/escribir el Word o al conectar con la BBDD |

### psp.log — Hilos y fases

| Nivel | Qué registra |
|---|---|
| `INFO` | Inicio y fin de cada fase |
| `INFO` | Resultado de cada tarea (lo que devuelve cada `Callable`) |
| `SEVERE` | Error en alguna tarea concurrente |

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

