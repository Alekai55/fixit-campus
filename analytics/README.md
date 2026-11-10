# 📊 Análisis Gráfico de Incidencias

Este proyecto es una herramienta desarrollada en Python para leer, procesar y visualizar gráficamente un registro de incidencias (averías, problemas informáticos, mantenimiento) almacenadas en un archivo JSON.

El objetivo principal es transformar datos en crudo en gráficos visuales que ayuden a la toma de decisiones, permitiendo analizar los tiempos de respuesta, los costes asociados a diferentes proveedores y el volumen de incidencias por categoría y estado.

---

## 📁 Archivos necesarios

Antes de empezar, asegúrate de tener en la misma carpeta de tu proyecto los siguientes archivos:

1. El script de Python con el código (por ejemplo, `main.py`).
2. El archivo con los datos llamado `En este caso lo genera el propio codigo de python`.
3. Un archivo de texto llamado `requirements.txt` que debe contener exactamente estas dos líneas escritas en su interior:

matplotlib
flake8

---

## ⚙️ Requisitos previos

Para poder ejecutar este proyecto en tu equipo, necesitas:
- **Python 3.7 o superior**: Puedes descargarlo desde python.org.
- **Pip**: El gestor de paquetes de Python (normalmente viene incluido al instalar Python).

---

## 🚀 Instalación y Configuración

Abre la terminal en la carpeta de tu proyecto y sigue estos pasos para preparar tu entorno de trabajo:

**1. Crear un entorno virtual (Recomendado)**  
Es una buena práctica para aislar las librerías de este proyecto de las de tu ordenador. Ejecuta:

En Windows:
python -m venv venv **Genera el entrono virtual**
venv\Scripts\activate **Activa el entorno virtual**

En macOS/Linux:
python3 -m venv venv **Genera el entrono virtual**
source venv/bin/activate **Activa el entorno virtual**

**2. Instalación de dependencias (requirements.txt)**  
Para que el código funcione y cumpla con los estándares, Python necesita instalar las librerías que hemos definido en el archivo de texto. Ejecuta el siguiente comando:

pip install -r requirements.txt

Este comando descargará automáticamente **matplotlib** (para la generación de los gráficos) y **flake8** (para la revisión y calidad del código).

---

## 📈 ¿Qué hace el código?

Al ejecutar el script de Python, el sistema lee el listado de incidencias que crea `incidencias` y genera ventanas con gráficos distintos para analizar la situación:

1. **Composición Categórica:** Muestra el volumen de incidencias separadas por categoría y estado.
2. **Evolución Temporal:** Analiza el flujo dibujando una línea temporal de las incidencias reportadas.
3. **Análisis Financiero:** Calcula y grafica el coste estimado agrupado por proveedor.

---

## ▶️ Cómo ejecutar el proyecto

Una vez instalado todo, asegúrate de tener tu entorno virtual activado y ejecuta el script principal en tu terminal (cambia `script.py` por el nombre real de tu archivo, en nuestro caso `main.py`):

python script.py


**Para revisar la calidad del código:**
Si quieres detectar posibles errores de sintaxis o de formato con la herramienta que hemos instalado, ejecuta:

flake8 main.py **Esto tendras que ejecutarlo desde el terminal de CMD, para abrirlo tendremos que entrar en el terminal de VSCode y en la parte superior derecha dar a la flecha al lado del símbolo +, despues iniciaremos la llamada `Command Prompt`**