import matplotlib.pyplot as plt
import numpy as np

# Datos
bytes_data_new = np.array(["10^1", "10^2", "10^3", "10^4", "10^5", "10^6"])
time_ms_new = np.array([3.19131, 2.37775, 2.04043, 2.26177, 2.90310, 13.36983])

# Gráfico de barras
plt.figure(figsize=(10, 6))
colors = ["blue", "green", "red", "cyan", "magenta", "orange"]
plt.bar(bytes_data_new, time_ms_new, color=colors)
plt.title("Tiempo de Comunicación en Milisegundos vs Cantidad de Bytes")
plt.xlabel("Cantidad de Bytes (10^x)")
plt.ylabel("Tiempo (milisegundos)")
plt.grid(axis="y")
plt.show()
