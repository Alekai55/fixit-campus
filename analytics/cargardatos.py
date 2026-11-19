"""Módulo para cargar los datos de incidencias en formato JSON."""

import json
from pathlib import Path


def cargardatos():
    """Devuelve la lista de incidencias cargadas desde el JSON."""
    # Ruta al fichero de datos, relativa a la raíz del proyecto
    ruta = Path('analytics/datos.json')

    # Abrimos el JSON y lo devolvemos como lista de dicts
    with open(ruta, encoding='utf-8') as f:
        return json.load(f)
