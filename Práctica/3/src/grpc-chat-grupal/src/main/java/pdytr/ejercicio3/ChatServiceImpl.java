package pdytr.ejercicio3;

import io.grpc.stub.StreamObserver;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ChatServiceImpl extends ChatServiceGrpc.ChatServiceImplBase {
    private static final Logger logger = Logger.getLogger(ChatServiceImpl.class.getName());
    private final Map<String, StreamObserver<Message>> clients = new ConcurrentHashMap<>();
    private final List<String> messageHistory = new CopyOnWriteArrayList<>();

    @Override
    public void connect(ClientInfo request, StreamObserver<ServerResponse> responseObserver) {
        String name = request.getName();

        // Validación de entrada
        if (name.trim().isEmpty()) {
            handleClientError(responseObserver, "El nombre del cliente no puede estar vacío.", io.grpc.Status.INVALID_ARGUMENT);
            return;
        }

        if (clients.containsKey(name)) {
            handleClientError(responseObserver, "El nombre del cliente ya existe.", io.grpc.Status.ALREADY_EXISTS);
            return;
        }

        try {
            responseObserver.onNext(ServerResponse.newBuilder()
                    .setMessage("Bienvenido " + name + " al chat.")
                    .build());
            responseObserver.onCompleted();

            logger.info(name + " conectado.");
        } catch (Exception e) {
            // Manejo de errores inesperados
            handleServerError(responseObserver, e, "Error inesperado durante la conexión.");
        }
    }

    @Override
    public void disconnect(ClientInfo request, StreamObserver<ServerResponse> responseObserver) {
        String name = request.getName();

        // Desconexión por nombre
        if (clients.remove(name) != null) {
            responseObserver.onNext(ServerResponse.newBuilder()
                    .setMessage("Adios " + name + "!")
                    .build());
            responseObserver.onCompleted();
            logger.info("Cliente " + name + " desconectado.");
        } else {
            handleClientError(responseObserver, "El cliente ya estaba desconectado.", io.grpc.Status.NOT_FOUND);
        }
    }

    @SuppressWarnings("unused")
    @Override
    public StreamObserver<Message> sendMessage(StreamObserver<Message> responseObserver) {
        return new StreamObserver<>() {
            private String clientName;

            @Override
            public void onNext(Message message) {
                if (message.getContent().isEmpty()) {
                    // Notificar al cliente sobre un error de validación
                    handleClientError(responseObserver, "No se puede enviar un mensaje vacío.", io.grpc.Status.INVALID_ARGUMENT);
                    return;
                }

                if (message.getContent().equals("/historial")) {
                    String path = message.getName() + " (historial).txt";

                    try ((BufferedWriter writer = new BufferedWriter(new FileWriter(path)) {
                        for (String msg : messageHistory) {
                            writer.write(msg);
                            writer.newLine(); 
                        }
                        System.out.println("Mensajes exportados a" + path);
                    } catch (IOException e) {
                        System.err.println("Ocurrió un error al exportar los mensajes al .txt: " + e.getMessage());
                    }
                }

                try {
                    // TODO eliminar esto si no va
                    // Registro del cliente al recibir el primer mensaje
                    if (clientName == null) {
                        clientName = message.getName();
                        if (clients.containsKey(clientName)) {
                            handleClientError(responseObserver, "El nombre del cliente ya existe.", io.grpc.Status.ALREADY_EXISTS);
                            return;
                        }
                        clients.put(clientName, responseObserver);
                        logger.info(clientName + " registrado exitosamente.");
                    }

                    // Procesar y retransmitir el mensaje
                    String formattedTime = Utils.formatTime(Long.parseLong(message.getTimestamp()));
                    messageHistory.add("[" + formattedTime + "] " + message.getName() + ": " + message.getContent());
                    retransmitMessage(message);
                } catch (Exception e) {
                    handleServerError(responseObserver, e, "Error inesperado procesando el mensaje.");

                }
            }

            @Override
            public void onError(Throwable t) {
                if (t instanceof io.grpc.StatusRuntimeException statusException &&
                        statusException.getStatus().getCode() == io.grpc.Status.Code.UNAVAILABLE) {
                    logger.warning("Cliente desconectado abruptamente: " + clientName);
                } else {
                    logger.log(Level.SEVERE, "Stream error para el cliente " + clientName, t);
                }
                disconnectClient(clientName);
            }

            @Override
            public void onCompleted() {
                System.out.println("Cliente desconectado.");
                disconnectClient(clientName);
            }
        };
    }

    private void retransmitMessage(Message message) {
        for (Map.Entry<String, StreamObserver<Message>> entry : clients.entrySet()) {
            StreamObserver<Message> clientStream = entry.getValue();
            try {
                clientStream.onNext(message);
            } catch (Exception e) {
                logger.log(Level.WARNING, "Error enviando mensaje al cliente: " + entry.getKey(), e);
                disconnectClient(entry.getKey());
            }
        }
    }


    private void disconnectClient(String clientName) {
        if (clients.remove(clientName) != null) {
            logger.info("Cliente " + clientName + " removido exitosamente.");
        } else {
            logger.warning("Se intentó remover al cliente " + clientName + ", pero no se lo encontró en la lista.");
        }
    }

    private <T> void handleClientError(StreamObserver<T> responseObserver, String errorMessage, io.grpc.Status status) {
        logger.log(Level.WARNING, "Error del cliente: " + errorMessage);
        responseObserver.onError(status.withDescription(errorMessage).asRuntimeException());
    }


    private <T> void handleServerError(StreamObserver<T> responseObserver, Exception e, String logMessage) {
        logger.log(Level.SEVERE, logMessage, e);
        responseObserver.onError(io.grpc.Status.INTERNAL
                .withDescription("Error interno del servidor.")
                .asRuntimeException());
    }
}
