package pdytr.ejercicio3;

import io.grpc.Status;
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

        try {
            responseObserver.onNext(ServerResponse.newBuilder()
                    .setMessage("Bienvenido " + name + " al chat.")
                    .build());
            responseObserver.onCompleted();

            logger.info(name + " conectado.");
        } catch (Exception e) {
            logger.info("Error inesperado durante la conexión");
        }
    }

    @Override
    public void disconnect(ClientInfo request, StreamObserver<ServerResponse> responseObserver) {
        String name = request.getName();

        if (clients.remove(name) != null) {
            responseObserver.onNext(ServerResponse.newBuilder()
                    .setMessage("Adios " + name + "!")
                    .build());
            logger.info("Cliente " + name + " removido exitosamente.");
        }
    }

    @Override
    public StreamObserver<Message> sendMessage(StreamObserver<Message> responseObserver) {
        return new StreamObserver<>() {
            private String clientName;

            @Override
            public void onNext(Message message) {
                if (message.getContent().isEmpty()) {
                    responseObserver.onError(io.grpc.Status.INVALID_ARGUMENT
                            .withDescription("El mensaje no puede estar vacío.")
                            .asException());
                    return;
                }

                if (message.getContent().equals("/historial")) {
                    exportMessageHistory(message.getName());
                    return;
                }

                try {
                    clientName = message.getName();
                    if (message.getContent().equals("/connect")) {
                        if (clients.containsKey(clientName)) {
                            responseObserver.onError(io.grpc.Status.ALREADY_EXISTS
                                    .withDescription("El cliente ya está conectado. Elija otro nombre.")
                                    .asException());
                            return;
                        }
                        clients.put(clientName, responseObserver);
                        logger.info(clientName + " conectado exitosamente.");
                    }
                    else {
                        // Procesar y retransmitir el mensaje
                        String formattedTime = Utils.formatTime(Long.parseLong(message.getTimestamp()));
                        messageHistory.add("[" + formattedTime + "] " + message.getName() + ": " + message.getContent());
                        retransmitMessage(message);
                    }
                } catch (Exception e) {
                    logger.info("se ejecuto el catch del chat del server");
                }
            }

            @Override
            public void onError(Throwable t) {
                disconnectClient(clientName);
            }

            @Override
            public void onCompleted() {
            }
        };
    }

    private void retransmitMessage(Message message) {
        for (Map.Entry<String, StreamObserver<Message>> entry : clients.entrySet()) {
            StreamObserver<Message> clientStream = entry.getValue();
            try {
                clientStream.onNext(message);
            } catch (Exception e) {
                logger.log(Level.WARNING, "Error enviando mensaje al cliente: " );
            }
        }
    }

    private void disconnectClient(String clientName) {
        if (clients.remove(clientName) != null) {
            logger.info("Cliente " + clientName + " removido exitosamente.");
        }
    }

    private void exportMessageHistory(String clientName) {
        String path = clientName + " (historial).txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (String msg : messageHistory) {
                writer.write(msg);
                writer.newLine();
            }
            System.out.println("Mensajes exportados a " + path);
        } catch (IOException e) {
            System.err.println("Ocurrió un error al exportar los mensajes al .txt: " + e.getMessage());
        }
    }

}
