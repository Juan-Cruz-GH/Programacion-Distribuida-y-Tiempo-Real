package pdytr.ejercicio3;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ChatClient {
    private final ChatServiceGrpc.ChatServiceStub asyncStub;
    private final ManagedChannel channel;
    private final String clientName;
    private final CountDownLatch latch = new CountDownLatch(1); // Sincronizador para manejar el cierre.

    public ChatClient(String host, int port, String clientName) {
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
                System.err.println("Error connecting: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Connection completed.");
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
                System.err.println("Error in chat: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Chat ended by server.");
            }
        });

        Scanner scanner = new Scanner(System.in);
        System.out.println("Type your messages below. Type 'exit' to quit.");

        try {
            while (true) {
                String input = scanner.nextLine();

                if ("exit".equalsIgnoreCase(input)) {
                    requestObserver.onCompleted();
                    break;
                }

                if (input.trim().isEmpty()) {
                    System.out.println("Cannot send an empty message.");
                    continue;
                }

                Message message = Message.newBuilder()
                        .setName(clientName)
                        .setContent(input)
                        .setTimestamp(String.valueOf(System.currentTimeMillis()))
                        .build();
                requestObserver.onNext(message);
            }
        } catch (Exception e) {
            System.err.println("Error sending message: " + e.getMessage());
        } finally {
            scanner.close();
            try {
                latch.await(5, TimeUnit.SECONDS); // Esperar que el chat termine.
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void disconnect() {
        ClientInfo request = ClientInfo.newBuilder()
                .setName(clientName)
                .build();

        asyncStub.disconnect(request, new StreamObserver<>() {
            @Override
            public void onNext(ServerResponse response) {
                System.out.println("Server: " + response.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error disconnecting: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Disconnection completed.");
            }
        });
    }

    public void shutdown() {
        try {
            channel.shutdown(); // Inicia el cierre del canal.
            if (!channel.awaitTermination(5, TimeUnit.SECONDS)) {
                System.err.println("Forcing shutdown due to timeout.");
                channel.shutdownNow(); // Forzar cierre si no responde.
            }
            System.out.println("Client shut down.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Usage: ChatClient <host> <port> <name>");
            System.exit(1);
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);
        String name = args[2];

        ChatClient client = new ChatClient(host, port, name);

        try {
            client.connect();
            client.chat();
        } finally {
            client.disconnect();
            client.shutdown();
        }
    }
}