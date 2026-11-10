def filtrar(lista):
    return list(
        filter(
            lambda incidencia: incidencia["estado"] == "ABIERTA",
            lista,
        )
    )
