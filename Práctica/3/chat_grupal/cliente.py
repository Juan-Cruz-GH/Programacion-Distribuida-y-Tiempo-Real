import grpc
import threading
from chat_pb2 import Cliente, EnviarRequest, HistorialRequest
from chat_pb2_grpc import ChatServiceStub


def receive_messages(stub, nombre):
    # Aquí debemos hacer una solicitud que permita la escucha continua de los mensajes enviados
    request = EnviarRequest(nombre=nombre, contenido="")
    for mensaje in stub.EnviarMensaje(request):
        print(f"[{mensaje.timestamp}] {mensaje.nombre}: {mensaje.contenido}")


def main():
    nombre = input("Introduce tu nombre: ")
    channel = grpc.insecure_channel("localhost:50051")
    stub = ChatServiceStub(channel)

    # Conectar al chat
    respuesta = stub.Conectar(Cliente(nombre=nombre))
    print(respuesta.mensaje)

    # Hilo para recibir mensajes
    threading.Thread(target=receive_messages, args=(stub, nombre)).start()

    try:
        while True:
            contenido = input()
            if contenido == "/salir":
                break
            elif contenido == "/historial":
                stub.ObtenerHistorial(HistorialRequest(comando="/historial"))
            else:
                stub.EnviarMensaje(EnviarRequest(nombre=nombre, contenido=contenido))

    except KeyboardInterrupt:
        pass
    finally:
        stub.Desconectar(Cliente(nombre=nombre))
        channel.close()


if __name__ == "__main__":
    main()
