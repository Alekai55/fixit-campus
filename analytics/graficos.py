"""Módulo para generar y guardar las gráficas de incidencias."""

import io
from datetime import datetime

import matplotlib.pyplot as plt


def grafica_incidencias_por_espacio(incidencias):
    """Genera un gráfico de barras con incidencias ABIERTA por espacio.

    Args:
        incidencias (list): Lista de incidencias a representar.

    Returns:
        None: Guarda el gráfico en analytics/graficos/.
    """
    x = [incidencia["espacio"] for incidencia in incidencias]
    y = [x.count(espacio) for espacio in x]

    plt.bar(x, y)
    plt.yticks(range(0, max(y) + 1))
    plt.xlabel("Espacios de la escuela")
    plt.ylabel("Número de incidencias")
    plt.title("Incidencias ABIERTA por espacio")
    plt.xticks(rotation=45)
    # Ajusta márgenes para que las etiquetas no se corten
    plt.tight_layout()
    # Guarda el gráfico en memoria y lo escribe en el archivo PNG
    buf = io.BytesIO()
    plt.savefig(buf, format="png")  # Renderiza en el buffer en memoria
    with open(
        "analytics/graficos/grafico_incidencias.png", "wb"
    ) as f:
        f.write(buf.getbuffer())  # Vuelca el buffer al archivo en disco
        
    # plt es global y acumula estado; clf() lo resetea para el siguiente gráfico
    plt.clf()


def grafica_media_resolucion(incidencias):
    """Genera un gráfico de barras con la media de días de resolución.

    Args:
        incidencias (list): Lista de incidencias a representar.

    Returns:
        None: Guarda el gráfico en analytics/graficos/.
    """
    # Acumuladores de días y conteo por categoría
    totales = {}
    conteos = {}

    # Recorre solo las incidencias ya resueltas
    for inc in incidencias:
        if inc["fecha_resolucion"] == "null":
            continue
        categoria = inc["categoria"]
        # Convierte los strings de fecha a objetos datetime para operar con ellos
        creacion = datetime.strptime(
            inc["fecha_creacion"], "%Y-%m-%d"
        )
        resolucion = datetime.strptime(
            inc["fecha_resolucion"], "%Y-%m-%d"
        )
        # Diferencia en días entre creación y resolución
        dias = (resolucion - creacion).days

        # Inicializa la categoría si es la primera vez
        if categoria not in totales:
            totales[categoria] = 0
            conteos[categoria] = 0
        totales[categoria] += dias
        conteos[categoria] += 1

    # Para cada categoría, divide el total de días entre el número
    # de incidencias resueltas para obtener la media
    medias = {
        cat: totales[cat] / conteos[cat]
        for cat in totales
    }

    # Dibuja el gráfico de barras con la media de días por categoría
    plt.bar(medias.keys(), medias.values(), color="purple")
    plt.xlabel("Categoría")
    plt.ylabel("Media de días hasta resolución")
    plt.title("Media de días hasta resolución por categoría")
    plt.xticks(rotation=45)
    plt.tight_layout()
    # Guarda el gráfico en memoria y lo escribe en el archivo PNG
    buf = io.BytesIO()
    plt.savefig(buf, format="png")
    with open(
        "analytics/graficos/grafico_media_resolucion.png", "wb"
    ) as f:
        f.write(buf.getbuffer())
