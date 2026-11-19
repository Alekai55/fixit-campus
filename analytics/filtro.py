"""Módulo para filtrar incidencias según su estado."""


def filtrar(lista):
    """Devuelve las incidencias con estado ABIERTA.

    Args:
        lista (list): Lista de incidencias a filtrar.

    Returns:
        list: Incidencias cuyo estado es ABIERTA.
    """
    return list(
        filter(
            lambda incidencia: incidencia["estado"] == "ABIERTA",
            lista,
        )
    )
