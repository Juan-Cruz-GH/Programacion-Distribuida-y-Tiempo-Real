from concurrent import futures
import grpc
import time
import chat_pb2 as chat
import chat_pb2_grpc as rpc
from datetime import datetime

class ChatServer(rpc.ChatServerServicer):
    def __init__(self):
        self.chats = []  # Lista de mensajes con su historial
        self.clientes_conectados = []  # Lista de clientes conectados

    def Conectar(self, request, context):
        nombre = request.nombre
        if nombre not in self.clientes_conectados:
            self.clientes_conectados.append(nombre)
            mensaje = f"Bienvenido {nombre} al chat grupal!"
            print(mensaje)
            return chat.Confirmacion(mensaje=mensaje)
        return chat.Confirmacion(mensaje=f"{nombre} ya está conectado.")

    def Desconectar(self, request, context):
        nombre = request.nombre
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
        nuevo_mensaje = chat.Mensaje(nombre=nombre, contenido=contenido, timestamp=timestamp)
        print(f"[{timestamp}] {nombre}: {contenido}")
        
        # Agregar el mensaje al historial
        self.chats.append(nuevo_mensaje)
        self.guardar_historial(nuevo_mensaje)
        return chat.Confirmacion(mensaje="Mensaje enviado y distribuido a todos los clientes.")

    def SolicitarHistorial(self, request, context):
        return chat.HistorialResponse(mensajes=self.chats)

    def ChatStream(self, request, context):
        lastindex = 0
        while True:
            while len(self.chats) > lastindex:
                mensaje = self.chats[lastindex]
                lastindex += 1
                yield mensaje

    def guardar_historial(self, mensaje):
        with open("historial.txt", "a") as f:
            f.write(f"[{mensaje.timestamp}] {mensaje.nombre}: {mensaje.contenido}\n")


if __name__ == "__main__":
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    rpc.add_ChatServerServicer_to_server(ChatServer(), server)
    puerto = "11913"
    print(f"Servidor en marcha en el puerto {puerto}")
    server.add_insecure_port(f"[::]:{puerto}")
    server.start()
    try:
        while True:
            time.sleep(86400)
    except KeyboardInterrupt:
        print("Servidor detenido")
        server.stop(0)
