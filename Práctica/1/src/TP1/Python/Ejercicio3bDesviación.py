import matplotlib.pyplot as plt
import numpy as np

# Data
bytes_data_new = np.array(["10^1", "10^2", "10^3", "10^4", "10^5", "10^6"])
time_ms_new = np.array([3.19131, 2.37775, 2.04043, 2.26177, 2.90310, 13.36983])

# Calculate overall mean and standard deviation
overall_mean = np.mean(time_ms_new)
overall_std = np.std(time_ms_new)

# Calculate deviations from the mean
deviations = time_ms_new - overall_mean

# Calculate how many standard deviations each point is from the mean
std_deviations = deviations / overall_std

# Create the plot
plt.figure(figsize=(12, 6))

# Bar plot of standard deviations
bars = plt.bar(bytes_data_new, std_deviations, color="skyblue", edgecolor="black")

# Add horizontal line at y=0 (mean)
plt.axhline(y=0, color="r", linestyle="-", linewidth=2)

# Customize the plot
plt.title("Desviación respecto de la media general en unidades de desviación estándar")
plt.xlabel("Cantidad de Bytes (10^x)")
plt.ylabel("Desviaciones estándar de la media")
plt.grid(axis="y", linestyle="--", alpha=0.7)

# Add value labels on top of each bar
for bar in bars:
    height = bar.get_height()
    plt.text(
        bar.get_x() + bar.get_width() / 2.0,
        height,
        f"{height:.2f}σ",
        ha="center",
        va="bottom" if height > 0 else "top",
    )

# Show the plot
plt.tight_layout()
plt.show()

# Print additional information
print(f"Overall Mean: {overall_mean:.5f}")
print(f"Overall Standard Deviation: {overall_std:.5f}")
print("\nDeviations from mean (in standard deviation units):")
for bytes, time, std_dev in zip(bytes_data_new, time_ms_new, std_deviations):
    print(f"Bytes: {bytes}, Time: {time:.5f} ms, Deviation: {std_dev:.2f}σ")
