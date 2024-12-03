package pdytr.ejercicio3;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Scanner;

public class ChatClient {
    private final ChatServiceGrpc.ChatServiceBlockingStub blockingStub;
    private final ChatServiceGrpc.ChatServiceStub asyncStub;
    private final String clientName;

    public ChatClient(String host, int port, String clientName) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext(true)
                .build();
        this.blockingStub = ChatServiceGrpc.newBlockingStub(channel);
        this.asyncStub = ChatServiceGrpc.newStub(channel);
        this.clientName = clientName;
    }

    public void connect() {
        ClientInfo request = ClientInfo.newBuilder()
                .setName(clientName)
                .build();

        ServerResponse response = blockingStub.connect(request);
        System.out.println("Server: " + response.getMessage());
    }

    public void disconnect() {
        ClientInfo request = ClientInfo.newBuilder()
                .setName(clientName)
                .build();

        ServerResponse response = blockingStub.disconnect(request);
        System.out.println("Server: " + response.getMessage());
    }

    public void sendMessage(String messageContent) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        Message request = Message.newBuilder()
                .setName(clientName)
                .setContent(messageContent)
                .setTimestamp(timestamp)
                .build();

        ServerResponse response = blockingStub.sendMessage(request);
        System.out.println("Server: " + response.getMessage());
    }

    public void listenForMessages() {
        // Implementación para escuchar mensajes del servidor (puede extenderse con streams bidireccionales)
        System.out.println("Listening for messages from the server (currently not implemented)...");
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: ChatClient <host> <port> <name>");
            System.exit(1);
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);
        String name = args[2];

        ChatClient client = new ChatClient(host, port, name);
        client.connect();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Type your messages below. Type 'exit' to quit.");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();

            if ("exit".equalsIgnoreCase(input)) {
                client.disconnect();
                break;
            }

            client.sendMessage(input);
        }

        scanner.close();
    }
}
