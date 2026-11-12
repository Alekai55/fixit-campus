"""Módulo principal que realiza la carga de datos, el filtrado si es necesario y la generación de gráficas."""

from pathlib import Path

import cargardatos
import filtro
import graficos

ruta = Path("analytics/graficos")
ruta.mkdir(parents=True, exist_ok=True)

datos = cargardatos.cargardatos()
incidencias_filtradas = filtro.filtrar(datos)

graficos.grafica_incidencias_por_espacio(incidencias_filtradas)
graficos.grafica_media_resolucion(datos)
