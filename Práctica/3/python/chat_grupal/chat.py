import time
from datetime import datetime
from concurrent import futures

import grpc
import chat_pb2 as chat
import chat_pb2_grpc as rpc
import threading


puerto = "11913"


class ChatServer(rpc.ChatServerServicer):
    def __init__(self):
        self.chats = []
        self.clientes_conectados = []
        self.chats_lock = threading.Lock() # Lock para el historial de mensajes
        self.clientes_lock = threading.Lock() # Lock para la lista de clientes conectados

    def Conectar(self, request, context):
        nombre = request.nombre
        with self.clientes_lock:
            if nombre not in self.clientes_conectados:
                self.clientes_conectados.append(nombre)
                mensaje = f"Bienvenido/a {nombre} al chat grupal!"
                print(mensaje)
                return chat.Confirmacion(mensaje=mensaje)
            return chat.Confirmacion(mensaje=f"{nombre} ya está conectado.")

    def Desconectar(self, request, context):
        nombre = request.nombre
        with self.clientes_lock:
            if nombre in self.clientes_conectados:
                self.clientes_conectados.remove(nombre)
                mensaje = f"{nombre} ha dejado el chat."
                print(mensaje)
                return chat.Confirmacion(mensaje=mensaje)
            return chat.Confirmacion(mensaje=f"{nombre} no está conectado.")

    def EnviarMensaje(self, request, context):
        nombre = request.nombre
        contenido = request.contenido
        timestamp = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
        nuevo_mensaje = chat.Mensaje(
            nombre=nombre, contenido=contenido, timestamp=timestamp
        )
        print(f"[{timestamp}] {nombre}: {contenido}")

        with self.chats_lock:
            # Agregar el mensaje al historial
            self.chats.append(nuevo_mensaje)
            self.guardar_historial(nuevo_mensaje)
        return chat.Confirmacion(
            mensaje="Mensaje enviado y distribuido a todos los clientes."
        )

    def SolicitarHistorial(self, request, context):
        with self.chats_lock:
            return chat.HistorialResponse(mensajes=self.chats)


    def ChatStream(self, request, context):
        lastindex = 0
        while True:
            while len(self.chats) > lastindex:
                mensaje = self.chats[lastindex]
                lastindex += 1
                yield mensaje

    def guardar_historial(self, mensaje):
        with open(file="historial.txt", mode="a") as f:
            f.write(f"[{mensaje.timestamp}] {mensaje.nombre}: {mensaje.contenido}\n")


if __name__ == "__main__":
    server = grpc.server(thread_pool=futures.ThreadPoolExecutor(max_workers=3))
    rpc.add_ChatServerServicer_to_server(servicer=ChatServer(), server=server)

    print(f"Servidor iniciado en el puerto {puerto}")
    server.add_insecure_port(address=f"[::]:{puerto}")
    server.start()

    try:
        while True:
            time.sleep(86400)
    except KeyboardInterrupt:
        print("Servidor detenido")
        server.stop(0)
