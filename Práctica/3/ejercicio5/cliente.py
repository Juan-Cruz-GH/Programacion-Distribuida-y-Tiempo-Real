import grpc
import time
import experimento_pb2
import experimento_pb2_grpc
import statistics


def experimento():
    with grpc.insecure_channel("localhost:50051") as channel:
        stub = experimento_pb2_grpc.ExperimentoStub(channel)

        enviar_100_000_bytes = (b"a" * 100_000, "Experimento enviando 10^5 bytes")
        enviar_1000_000_bytes = (b"a" * 1_000_000, "Experimento enviando 10^6 bytes")

        datos = [enviar_100_000_bytes, enviar_1000_000_bytes]

        for dato in datos:
            tiempos = []

            for _ in range(1000):
                tiempo_inicio = time.time()
                stub.Respuesta(experimento_pb2.Request(datos=dato[0]))
                tiempo_fin = time.time()

                tiempos.append((tiempo_fin - tiempo_inicio) * 1000)

            tiempo_promedio = sum(tiempos) / len(tiempos)
            tiempo_min = min(tiempos)
            desviacion_estandar = statistics.stdev(tiempos)

            print(f"{dato[1]}\n")

            print(f"Tiempo mínimo: {tiempo_min:.6f} ms")
            print(f"Tiempo promedio: {tiempo_promedio:.6f} ms")
            print(f"Desviación estándar: {desviacion_estandar:.6f} ms\n")


if __name__ == "__main__":
    experimento()
