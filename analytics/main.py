import matplotlib.pyplot as plt
import io
import cargardatos
import filtro

datos = cargardatos.cargardatos()
incidencias_filtradas = filtro.filtrar(datos)

x = [incidencia["espacio"] for incidencia in incidencias_filtradas]
y = [x.count(espacio) for espacio in x]

plt.bar(x, y)
plt.yticks(range(0, max(y) + 1))
plt.xlabel("Espacios de la escuela")
plt.ylabel("Número de incidencias")
plt.title("Incidencias ABIERTA por espacio")
plt.xticks(rotation=45)
plt.tight_layout()
# plt.show()
buf = io.BytesIO()
plt.savefig(buf, format="png")
with open("grafico_incidencias.png", "wb") as f:
    f.write(buf.getbuffer())
