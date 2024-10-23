import grpc
import threading
import chat_pb2 as chat
import chat_pb2_grpc as rpc

puerto = "11913"


class Client:
    def __init__(self, usuario: str):
        self.usuario = usuario
        direccion = f"localhost:{puerto}"
        canal = grpc.insecure_channel(target=direccion)
        self.conexion = rpc.ChatServerStub(channel=canal)

        # Conectar al servidor
        self.conectar()

        # Crear un nuevo hilo para recibir mensajes mientras enviamos mensajes
        threading.Thread(target=self.escuchar_mensajes, daemon=True).start()
        self.loop()

    def conectar(self):
        # Enviar el nombre del cliente para conectarse
        response = self.conexion.Conectar(chat.NombreCliente(nombre=self.usuario))
        print(response.mensaje)  # Mensaje de bienvenida

    def desconectar(self):
        # Enviar el nombre del cliente para desconectarse
        response = self.conexion.Desconectar(chat.NombreCliente(nombre=self.usuario))
        print(response.mensaje)  # Mensaje de despedida

    def escuchar_mensajes(self):
        for msg in self.conexion.ChatStream(chat.Empty()):
            print(f"\n[{msg.timestamp}] [{msg.nombre}] {msg.contenido}")

    def enviar_mensaje(self, mensaje: str):
        if mensaje:
            # Crear un mensaje con el nombre del usuario y el contenido
            n = chat.Mensaje()
            n.nombre = self.usuario
            n.contenido = mensaje
            # No es necesario setear timestamp, lo hará el servidor
            response = self.conexion.EnviarMensaje(n)
            print(response.mensaje)  # Confirmación de envío

    def solicitar_historial(self):
        # Solicitar el historial de mensajes
        response = self.conexion.SolicitarHistorial(chat.Empty())
        for msg in response.mensajes:
            print(f"[{msg.timestamp}] [{msg.nombre}] {msg.contenido}")

    def loop(self):
        print(f"Bienvenido al chat, {self.usuario}!")
        while True:
            message = input()
            if message.lower() == "/salir":
                self.desconectar()
                break
            elif message.lower() == "/historial":
                self.solicitar_historial()
            else:
                self.enviar_mensaje(message)


if __name__ == "__main__":
    usuario = input("Ingresa tu nombre de usuario: ")
    cliente = Client(usuario)
