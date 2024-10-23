    package pdytr.ejercicio3;

    import io.grpc.Server;
    import io.grpc.ServerBuilder;
    import io.grpc.stub.StreamObserver;

    import java.io.FileWriter;
    import java.io.IOException;
    import java.text.SimpleDateFormat;
    import java.util.Date;
    import java.util.concurrent.ConcurrentHashMap;
    import java.util.ArrayList;
    import java.util.List;

    public class ChatServer {

        private static ConcurrentHashMap<String, StreamObserver<ServerResponse>> clients = new ConcurrentHashMap<>();
        static List<Message> messageHistory = new ArrayList<>();

        public static void main(String[] args) throws IOException, InterruptedException {
            Server server = ServerBuilder.forPort(8080)
                    .addService(new ChatServiceImpl())
                    .build();
            System.out.println("Chat Server started...");
            server.start();
            server.awaitTermination();
        }

        public static class ChatServiceImpl extends ChatServiceGrpc.ChatServiceImplBase {

            @Override
            public void connect(ClientInfo request, StreamObserver<ServerResponse> responseObserver) {
                String clientName = request.getName();
                String welcomeMessage = clientName + " has joined the chat!";
                System.out.println(welcomeMessage);

                clients.put(clientName, responseObserver);

                // Notify all clients about the new connection
                broadcastMessage("Server", welcomeMessage);

                ServerResponse response = ServerResponse.newBuilder()
                        .setMessage("Welcome to the chat, " + clientName + "!")
                        .build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }

            @Override
            public void disconnect(ClientInfo request, StreamObserver<ServerResponse> responseObserver) {
                String clientName = request.getName();
                clients.remove(clientName);

                String goodbyeMessage = clientName + " has left the chat.";
                System.out.println(goodbyeMessage);

                broadcastMessage("Server", goodbyeMessage);

                ServerResponse response = ServerResponse.newBuilder()
                        .setMessage("Goodbye, " + clientName)
                        .build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }

            @Override
            public void getHistory(HistoryRequest request, StreamObserver<Message> responseObserver) {
                for (Message message : messageHistory) {
                    responseObserver.onNext(message);
                }
                responseObserver.onCompleted();
            }

            private void broadcastMessage(String clientName, String content) {
                String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                Message message = Message.newBuilder()
                        .setName(clientName)
                        .setContent(content)
                        .setTimestamp(timestamp)
                        .build();

                for (StreamObserver<Message> client : clients.values()) {
                    client.onNext(message);
                }
            }

            private void saveToHistoryFile(Message message) {
                try (FileWriter writer = new FileWriter("chat_history.txt", true)) {
                    writer.write("[" + message.getTimestamp() + "] " + message.getName() + ": " + message.getContent() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
