package pdytr.ejercicio3;

import io.grpc.stub.StreamObserver;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatServiceImpl extends ChatServiceGrpc.ChatServiceImplBase {
    private static final Logger logger = Logger.getLogger(ChatServiceImpl.class.getName());
    private final Map<String, StreamObserver<Message>> clients = new ConcurrentHashMap<>();
    private final List<Message> messageHistory = new CopyOnWriteArrayList<>();

    @Override
    public void connect(ClientInfo request, StreamObserver<ServerResponse> responseObserver) {
        String name = request.getName();

        // Validación de entrada
        if (name.trim().isEmpty()) {
            handleClientError(responseObserver, "Client name cannot be null or empty.", io.grpc.Status.INVALID_ARGUMENT);
            return;
        }

        if (clients.containsKey(name)) {
            handleClientError(responseObserver, "Client name already exists.", io.grpc.Status.ALREADY_EXISTS);
            return;
        }

        try {
            // Construcción del mensaje de bienvenida
            String welcomeMessage = "Welcome " + name + " to the chat!";
            responseObserver.onNext(ServerResponse.newBuilder()
                    .setMessage(welcomeMessage)
                    .build());
            responseObserver.onCompleted();

            logger.info(name + " connected.");
        } catch (Exception e) {
            // Manejo de errores inesperados
            handleServerError(responseObserver, e, "Unexpected error during connect.");
        }
    }

    @Override
    public void disconnect(ClientInfo request, StreamObserver<ServerResponse> responseObserver) {
        String name = request.getName();
        String farewellMessage = "Goodbye " + name + "!";

        // Desconexión por nombre
        if (clients.remove(name) != null) {
            responseObserver.onNext(ServerResponse.newBuilder()
                    .setMessage(farewellMessage)
                    .build());
            responseObserver.onCompleted();
            logger.info("Client " + name + " disconnected.");
        } else {
            handleClientError(responseObserver, "Client not connected.", io.grpc.Status.NOT_FOUND);
        }
    }

    @Override
    public StreamObserver<Message> sendMessage(StreamObserver<Message> responseObserver) {
        return new StreamObserver<>() {
            private String clientName;

            @Override
            public void onNext(Message message) {
                if (message.getContent().isEmpty()) {
                    // Notificar al cliente sobre un error de validación
                    handleClientError(responseObserver, "Message content cannot be null or empty.", io.grpc.Status.INVALID_ARGUMENT);
                    return;
                }

                try {
                    // TODO eliminar esto si no va
                    // Registro del cliente al recibir el primer mensaje
                    if (clientName == null) {
                        clientName = message.getName();
                        if (clients.containsKey(clientName)) {
                            handleClientError(responseObserver, "Client name already exists.", io.grpc.Status.ALREADY_EXISTS);
                            return;
                        }
                        clients.put(clientName, responseObserver);
                        logger.info(clientName + " registered successfully.");
                    }

                    // Procesar y retransmitir el mensaje
                    messageHistory.add(message);
                    String formattedTime = Utils.formatTime(Long.parseLong(message.getTimestamp()));
                    System.out.println("[Server] [" + formattedTime + "] " + message.getName() + ": " + message.getContent());
                    retransmitMessage(message);
                } catch (Exception e) {
                    handleServerError(responseObserver, e, "Unexpected error processing message.");

                }
            }

            @Override
            public void onError(Throwable t) {
                if (t instanceof io.grpc.StatusRuntimeException statusException &&
                        statusException.getStatus().getCode() == io.grpc.Status.Code.UNAVAILABLE) {
                    logger.warning("Client disconnected abruptly: " + clientName);
                } else {
                    logger.log(Level.SEVERE, "Stream error for client " + clientName, t);
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
                logger.log(Level.WARNING, "Error sending message to client: " + entry.getKey(), e);
                disconnectClient(entry.getKey());
            }
        }
    }


    private void disconnectClient(String clientName) {
        if (clients.remove(clientName) != null) {
            logger.info("Client " + clientName + " removed successfully.");
        } else {
            logger.warning("Attempted to remove client " + clientName + ", but it was not found in the list.");
        }
    }

    private <T> void handleClientError(StreamObserver<T> responseObserver, String errorMessage, io.grpc.Status status) {
        logger.log(Level.WARNING, "Client error: " + errorMessage);
        responseObserver.onError(status.withDescription(errorMessage).asRuntimeException());
    }


    private <T> void handleServerError(StreamObserver<T> responseObserver, Exception e, String logMessage) {
        logger.log(Level.SEVERE, logMessage, e);
        responseObserver.onError(io.grpc.Status.INTERNAL
                .withDescription("Internal server error. Please try again later.")
                .asRuntimeException());
    }
}
