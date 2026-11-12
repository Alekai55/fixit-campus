"""Módulo para filtrar incidencias según su estado."""


def filtrar(lista):
    """Devuelve las incidencias con estado ABIERTA."""
    return list(
        filter(
            lambda incidencia: incidencia["estado"] == "ABIERTA",
            lista,
        )
    )
