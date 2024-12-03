package pdytr.ejercicio3;

import io.grpc.stub.StreamObserver;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatServiceImpl extends ChatServiceGrpc.ChatServiceImplBase {
    private static final Logger logger = Logger.getLogger(ChatServiceImpl.class.getName());
    private final CopyOnWriteArrayList<StreamObserver<Message>> clients = new CopyOnWriteArrayList<>();

    @Override
    public void connect(ClientInfo request, StreamObserver<ServerResponse> responseObserver) {
        String name = request.getName();

        // Validación de entrada
        if (name == null || name.trim().isEmpty()) {
            handleClientError(responseObserver, "Client name cannot be null or empty.", io.grpc.Status.INVALID_ARGUMENT);
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

        responseObserver.onNext(ServerResponse.newBuilder()
                .setMessage(farewellMessage)
                .build());
        responseObserver.onCompleted();

        logger.info(name + " disconnected.");

        // TODO agregar aca la lógica de desconectar un cliente. Capaz necesito cambiar la estructura de datos que uso para guardar los clientes
    }

    @Override
    public StreamObserver<Message> sendMessage(StreamObserver<Message> responseObserver) {
        clients.add(responseObserver);

        return new StreamObserver<>() {
            @Override
            public void onNext(Message message) {
                if (message.getContent().isEmpty()) {
                    // Notificar al cliente sobre un error de validación
                    handleClientError(responseObserver, "Message content cannot be null or empty.", io.grpc.Status.INVALID_ARGUMENT);
                    return;
                }

                try {
                    // Procesar y retransmitir el mensaje
                    String formattedTime = Utils.formatTime(Long.parseLong(message.getTimestamp()));
                    System.out.println("[Server] [" + formattedTime + "] " + message.getName() + ": " + message.getContent());
                    retransmitMessage(message);
                } catch (Exception e) {
                    handleServerError(responseObserver, e, "Unexpected error processing message.");

                }
            }

            @Override
            public void onError(Throwable t) {
                logger.log(Level.SEVERE, "Error: " + t.getMessage(), t);
                clients.remove(responseObserver);
            }

            @Override
            public void onCompleted() {
                System.out.println("Cliente desconectado.");
                clients.remove(responseObserver);
            }
        };
    }

    private void retransmitMessage(Message message) {
        for (StreamObserver<Message> client : clients) {
            try {
                client.onNext(message);
            } catch (Exception e) {
                logger.log(Level.WARNING, "Error sending message to client. Removing client.", e);
                disconnectClient(client);
            }
        }
    }

    private void disconnectClient(StreamObserver<Message> client) {
        if (clients.remove(client)) {
            logger.info("Client removed successfully.");
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
