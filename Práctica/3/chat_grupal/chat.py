import grpc
from concurrent import futures
import time
import chat_pb2
import chat_pb2_grpc
import datetime


class ChatService(chat_pb2_grpc.ChatServiceServicer):
    def __init__(self):
        self.clients = []  # Lista de clientes conectados
        self.messages = []  # Para almacenar mensajes

    def EnviarMensaje(self, request, context):
        mensaje = chat_pb2.Mensaje(
            nombre=request.nombre,
            contenido=request.contenido,
            timestamp=str(datetime.datetime.now()),
        )
        self.messages.append(mensaje)
        # Enviar el mensaje a todos los clientes conectados
        for nombre, cliente_context in self.clients:
            cliente_context.send_mensaje(mensaje)

    def Conectar(self, request, context):
        self.clients.append((request.nombre, context))
        return chat_pb2.Respuesta(mensaje=f"{request.nombre} se ha unido al chat.")

    def Desconectar(self, request, context):
        self.clients = [
            (nombre, cliente_context)
            for nombre, cliente_context in self.clients
            if nombre != request.nombre
        ]
        return chat_pb2.Respuesta(mensaje=f"{request.nombre} se ha desconectado.")

    def ObtenerHistorial(self, request, context):
        return chat_pb2.HistorialResponse(
            mensajes=[
                chat_pb2.Mensaje(
                    nombre=mensaje.nombre,
                    contenido=mensaje.contenido,
                    timestamp=mensaje.timestamp,
                )
                for mensaje in self.messages
            ]
        )


def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    chat_pb2_grpc.add_ChatServiceServicer_to_server(ChatService(), server)
    server.add_insecure_port("[::]:50051")
    server.start()
    try:
        while True:
            time.sleep(86400)  # Mantiene el servidor en ejecución
    except KeyboardInterrupt:
        server.stop(0)


if __name__ == "__main__":
    serve()
