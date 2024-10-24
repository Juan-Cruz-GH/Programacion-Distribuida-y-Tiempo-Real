import time
import threading
from cliente import Client

def enviar_mensajes_automaticos(cliente):
    for i in range(20):  # Enviar 20 mensajes por cliente
        mensaje = f"Mensaje {i} del usuario {cliente.usuario}"
        cliente.enviar_mensaje(mensaje)
        print(f"Enviando: {mensaje}")
        time.sleep(0.1)  # Intervalo de 0.1 segundos entre cada mensaje
    cliente.desconectar()  # Desconectar el cliente después de enviar mensajes

if __name__ == "__main__":
    usuarios = [f"usuario_{i}" for i in range(10)]  # Crear 10 clientes
    for usuario in usuarios:
        print(f"Creando cliente para: {usuario}")
        cliente = Client(usuario)
        threading.Thread(target=enviar_mensajes_automaticos, args=(cliente,)).start()