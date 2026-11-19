# 📊 Análisis Gráfico de Incidencias

Este proyecto es una herramienta desarrollada en Python para leer, procesar y visualizar gráficamente un registro de incidencias (averías, problemas informáticos, mantenimiento) almacenadas en un archivo JSON.

El objetivo principal es transformar datos en crudo en gráficos visuales que ayuden a la toma de decisiones, permitiendo analizar los tiempos de respuesta, los costes asociados y el volumen de incidencias por categoría y estado.

---

## 📁 Archivos necesarios

La carpeta `analytics/` contiene todos los archivos del módulo:

- `main.py` — Script principal que orquesta la carga, filtrado y generación de gráficos.
- `cargardatos.py` — Carga las incidencias desde el JSON.
- `filtro.py` — Filtra las incidencias según criterios.
- `graficos.py` — Genera y guarda los gráficos.
- `datos.json` — Datos de incidencias en formato JSON.
- `requirements.txt` — Dependencias del proyecto.

---

## ⚙️ Requisitos previos

- **Python 3.7 o superior**: descargable desde python.org.
- **Pip**: incluido al instalar Python.

---

## 🚀 Instalación y Configuración

> ⚠️ **Importante:** el entorno virtual debe crearse **dentro de la carpeta `analytics/`**, no en la raíz del proyecto, para no contaminar el entorno general.

Abre la terminal, navega hasta `analytics/` y sigue estos pasos:

```bash
cd analytics
```

**1. Crear el entorno virtual**

En Windows:
```bash
python -m venv venv
venv\Scripts\activate
```

En macOS/Linux:
```bash
python3 -m venv venv
source venv/bin/activate
```

**2. Instalar dependencias**

```bash
pip install -r requirements.txt
```

Esto instalará **matplotlib** (para la generación de gráficos) y **flake8** (para la revisión de calidad del código).

---

## 📈 ¿Qué hace el código?

Al ejecutar el script principal, el sistema lee `datos.json` y genera los siguientes gráficos en `analytics/graficos/`:

1. **Incidencias por espacio:** barras con el volumen de incidencias abiertas por espacio.
2. **Media de resolución:** barras con la media de días hasta resolución por categoría.
3. **Distribución por estado:** gráfico de quesos con el reparto entre ABIERTA, EN_CURSO y RESUELTA.

---

## ▶️ Cómo ejecutar el proyecto

Con el entorno virtual activado y desde la **raíz del proyecto** (no desde `analytics/`):

```bash
python -m analytics.main
```

O bien desde dentro de `analytics/`:

```bash
python main.py
```

**Para revisar la calidad del código con flake8:**

> Para ejecutar flake8 desde VSCode, abre el terminal integrado y selecciona `Command Prompt` (flecha junto al símbolo `+` en la esquina superior derecha del terminal).

```bash
python -m flake8 cargardatos.py filtro.py graficos.py main.py
```
