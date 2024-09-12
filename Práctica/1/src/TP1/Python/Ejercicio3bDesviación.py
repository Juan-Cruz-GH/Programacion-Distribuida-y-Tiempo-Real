import matplotlib.pyplot as plt
import numpy as np

cantidad_bytes = np.array(["10^1", "10^2", "10^3", "10^4", "10^5", "10^6"])
tiempos = np.array([3.19131, 2.37775, 2.04043, 2.26177, 2.90310, 13.36983])

media_general = np.mean(tiempos)
desviacion_general = np.std(tiempos)

desviaciones = tiempos - media_general

desviaciones_estandar = desviaciones / desviacion_general

plt.figure(figsize=(12, 6))

barras = plt.bar(
    cantidad_bytes, desviaciones_estandar, color="skyblue", edgecolor="black"
)

plt.axhline(y=0, color="r", linestyle="-", linewidth=2)

plt.title("Desviación respecto de la media general en unidades de desviación estándar")
plt.xlabel("Cantidad de Bytes (10^x)")
plt.ylabel("Desviaciones estándar de la media")
plt.grid(axis="y", linestyle="--", alpha=0.7)

for barra in barras:
    altura = barra.get_altura()
    plt.text(
        barra.get_x() + barra.get_width() / 2.0,
        altura,
        f"{altura:.2f}σ",
        ha="center",
        va="bottom" if altura > 0 else "top",
    )

plt.tight_layout()
plt.show()
