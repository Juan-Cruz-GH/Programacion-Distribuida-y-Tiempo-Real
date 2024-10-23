package pdytr.ejercicio3;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Scanner;

public class ChatClient {

    public static void main(String[] args) throws Exception {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext(true)
                .build();

        ChatServiceGrpc.ChatServiceStub asyncStub = ChatServiceGrpc.newStub(channel);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        // Connect to the chat server
        ClientInfo clientInfo = ClientInfo.newBuilder().setName(name).build();
        asyncStub.connect(clientInfo, new StreamObserver<ServerResponse>() {
            @Override
            public void onNext(ServerResponse value) {
                System.out.println(value.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                // Start listening to messages
                listenToMessages(asyncStub, name);
            }
        });

        // Send messages
        while (true) {
            String messageContent = scanner.nextLine();

            if (messageContent.equals("/exit")) {
                asyncStub.disconnect(clientInfo, new StreamObserver<ServerResponse>() {
                    @Override
                    public void onNext(ServerResponse value) {
                        System.out.println(value.getMessage());
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("Disconnected from chat.");
                        channel.shutdown();
                    }
                });
                break;
            }

            if (messageContent.equals("/historial")) {
                asyncStub.getHistory(HistoryRequest.newBuilder().build(), new StreamObserver<Message>() {
                    @Override
                    public void onNext(Message value) {
                        System.out.println("[" + value.getTimestamp() + "] " + value.getName() + ": " + value.getContent());
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("End of message history.");
                    }
                });
            } else {
                Message message = Message.newBuilder().setName(name).setContent(messageContent).build();
                asyncStub.sendMessage(message, new StreamObserver<ServerResponse>() {
                    @Override
                    public void onNext(ServerResponse value) {
                        System.out.println(value.getMessage());
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onCompleted() {
                        // Message sent successfully
                    }
                });
            }
        }
    }

    private static void listenToMessages(ChatServiceGrpc.ChatServiceStub asyncStub, String name) {
        // This method would handle receiving messages from the server
    }
}
