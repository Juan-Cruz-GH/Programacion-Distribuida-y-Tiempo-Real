import threading

import grpc
import chat_pb2 as chat
import chat_pb2_grpc as rpc
from fpdf import FPDF

puerto = "11913"


class Client:
    def __init__(self, usuario: str):
        self.usuario = usuario
        direccion = f"localhost:{puerto}"
        self.canal = grpc.insecure_channel(target=direccion)
        self.conexion = rpc.ChatServerStub(channel=self.canal)

        self.conectar()

        # Crear un nuevo hilo para recibir mensajes mientras enviamos mensajes
        threading.Thread(target=self.escuchar_mensajes, daemon=True).start()
        self.loop()

    def escuchar_mensajes(self):
        for mensaje in self.conexion.ChatStream(chat.Vacio()):
            print(f"[{mensaje.timestamp}] {mensaje.nombre}: {mensaje.contenido}")

    def loop(self):
        while True:
            mensaje = input()
            try:
                if mensaje.lower() == "/salir":
                    self.desconectar()
                    break
                elif mensaje.lower() == "/historial":
                    self.solicitar_historial()
                else:
                    self.enviar_mensaje(mensaje=mensaje)
            except Exception:
                self.desconectar()
                break
            finally:
                self.desconectar()
                self.canal.close()
                break

    def conectar(self):
        """
        Le dice al servidor que se quiere conectar al chat.
        Si el nombre de usuario que eligió ya está en uso, deberá elegir otro.
        No podrá ingresar al chat hasta que su nombre de usuario sea válido.
        """
        respuesta = self.conexion.Conectar(chat.NombreCliente(nombre=self.usuario))
        while "ya está conectado" in respuesta.mensaje:
            print(respuesta.mensaje)
            self.usuario = input("Ingresa tu nombre de usuario: ")
            respuesta = self.conexion.Conectar(chat.NombreCliente(nombre=self.usuario))
        print(respuesta.mensaje)  # Mensaje de bienvenida

    def desconectar(self):
        """
        Le dice al servidor que se va a desconectar del chat
        de forma voluntaria.
        """
        respuesta = self.conexion.Desconectar(chat.NombreCliente(nombre=self.usuario))
        print(respuesta.mensaje)  # Mensaje de despedida

    def enviar_mensaje(self, mensaje: str):
        if mensaje:
            msj = chat.Mensaje()
            msj.nombre = self.usuario
            msj.contenido = mensaje

            # No es necesario asignar el timestamp, lo hace el servidor
            self.conexion.EnviarMensaje(msj)

    def solicitar_historial(self):
        respuesta = self.conexion.SolicitarHistorial(chat.Vacio())

        pdf = FPDF()
        pdf.set_auto_page_break(auto=True, margin=15)
        pdf.add_page()

        pdf.set_font("Arial", size=12)

        pdf.cell(
            0,
            10,
            "Formato: \n [Timestamp] usuario: mensaje",
            ln=True,
        )
        for mensaje in respuesta.mensajes:
            pdf.cell(
                0,
                10,
                f"[{mensaje.timestamp}] {mensaje.nombre}: {mensaje.contenido}",
                ln=True,
            )

        pdf.output(f"Historial para {self.usuario}.pdf")


if __name__ == "__main__":
    usuario = input("Ingresa tu nombre de usuario: ")
    cliente = Client(usuario)
