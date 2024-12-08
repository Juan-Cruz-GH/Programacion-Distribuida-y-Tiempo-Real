import grpc
from concurrent import futures
import time
import experimento_pb2
import experimento_pb2_grpc


class Experimento(experimento_pb2_grpc.ExperimentoServicer):
    def Respuesta(self, request, context):
        cantidad_datos = len(request.datos)
        print(f"Recibí {cantidad_datos} bytes del cliente.")
        return experimento_pb2.Response(mensaje="Recibido.")


def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    experimento_pb2_grpc.add_ExperimentoServicer_to_server(Experimento(), server)
    server.add_insecure_port("[::]:50051")
    server.start()
    print("Servidor iniciado en el puerto: 50051")
    try:
        while True:
            time.sleep(86400)
    except KeyboardInterrupt:
        server.stop(0)


if __name__ == "__main__":
    serve()
