import matplotlib.pyplot as plt
import numpy as np

cantidad_bytes = np.array(["10^1", "10^2", "10^3", "10^4", "10^5", "10^6"])
tiempos = np.array([3.19131, 2.37775, 2.04043, 2.26177, 2.90310, 13.36983])

plt.figure(figsize=(10, 6))
colores = ["blue", "green", "red", "cyan", "magenta", "orange"]
plt.bar(cantidad_bytes, tiempos, color=colores)
plt.title("Tiempo de Comunicación en Milisegundos vs Cantidad de Bytes")
plt.xlabel("Cantidad de Bytes (10^x)")
plt.ylabel("Tiempo (milisegundos)")
plt.grid(axis="y")
plt.show()
