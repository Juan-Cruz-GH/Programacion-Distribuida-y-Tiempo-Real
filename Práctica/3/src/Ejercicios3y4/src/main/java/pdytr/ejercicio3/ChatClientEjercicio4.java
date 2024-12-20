package pdytr.ejercicio3;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ChatClientEjercicio4 {
    private final ChatServiceGrpc.ChatServiceStub asyncStub;
    private final ManagedChannel channel;
    private final String clientName;
    private final CountDownLatch latch = new CountDownLatch(1); // Sincronizador para manejar el cierre.

    public ChatClientEjercicio4(String host, int port, String clientName) {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext(true)
                .build();
        this.asyncStub = ChatServiceGrpc.newStub(channel);
        this.clientName = clientName;
    }

    public void connect() {
        ClientInfo request = ClientInfo.newBuilder()
                .setName(clientName)
                .build();

        asyncStub.connect(request, new StreamObserver<>() {
            @Override
            public void onNext(ServerResponse response) {
                System.out.println("Server: " + response.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error connectando: " + t.getMessage());

            }

            @Override
            public void onCompleted() {
                System.out.println("Conectado al servidor exitosamente.");
                System.out.println("Escribe tus mensajes debajo. Escribe /salir para salir, " +
                        "o /historial para obtener el .txt con el historial de mensajes.");
            }
        });
    }

    public void chat() {
        StreamObserver<Message> requestObserver = asyncStub.sendMessage(new StreamObserver<>() {
            @Override
            public void onNext(Message message) {
                if (!message.getName().equals(clientName)) {
                    String formattedTime = Utils.formatTime(Long.parseLong(message.getTimestamp()));
                    System.out.println(" [" + formattedTime + "] " + message.getName() + ": " + message.getContent());
                }
            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onCompleted() {

            }
        });

        // Scanner scanner = new Scanner(System.in);

        try
        {
            // Envio un mensaje para conectarme al chat y poder recibir mensajes.
            Message messageToConnect = Message.newBuilder()
                    .setName(clientName)
                    .setContent("/connect")
                    .setTimestamp(String.valueOf(System.currentTimeMillis()))
                    .build();
            requestObserver.onNext(messageToConnect);

            for (int i = 0; i < 3; i++) {
                // String input = scanner.nextLine();
                String input = "Un mensaje";
    
                if ("/salir".equalsIgnoreCase(input)) {
                    break;
                }
    
                Message message = Message.newBuilder()
                        .setName(clientName)
                        .setContent(input)
                        .setTimestamp(String.valueOf(System.currentTimeMillis()))
                        .build();
                requestObserver.onNext(message);
                System.out.println("El cliente " + clientName + " envio un mensaje de prueba al ChatServer.");
            } 
        } catch (Exception e) {
            System.err.println("Cerrando el cliente de forma involuntaria");
        } finally {
            //scanner.close();
        }
    }

    public void disconnect() {
        ClientInfo request = ClientInfo.newBuilder()
                .setName(clientName)
                .build();

        asyncStub.disconnect(request, new StreamObserver<>() {
            @Override
            public void onNext(ServerResponse response) {
                System.out.println("Respuesta del servidor al intentar desconectarse: " + response.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                //System.err.println("Error desconectandose: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Desconectado exitosamente.");
            }
        });
    }

    public void shutdown() {
        try {
            channel.shutdown(); // Inicia el cierre del canal.
            if (!channel.awaitTermination(5, TimeUnit.SECONDS)) {
                // System.err.println("Forzando shutdown del cliente...");
                channel.shutdownNow(); // Forzar cierre si no responde.
            }
            System.out.println("Cliente apagado.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}