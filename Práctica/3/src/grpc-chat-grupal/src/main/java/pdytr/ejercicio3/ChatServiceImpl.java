package pdytr.ejercicio3;

import io.grpc.stub.StreamObserver;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatServiceImpl extends ChatServiceGrpc.ChatServiceImplBase {
    private final CopyOnWriteArrayList<StreamObserver<Message>> clients = new CopyOnWriteArrayList<>();

    @Override
    public void connect(ClientInfo request, StreamObserver<ServerResponse> responseObserver) {
        String name = request.getName();
        String welcomeMessage = "Welcome " + name + " to the chat!";

        responseObserver.onNext(ServerResponse.newBuilder()
                .setMessage(welcomeMessage)
                .build());
        responseObserver.onCompleted();

        System.out.println(name + " connected.");
    }

    @Override
    public void disconnect(ClientInfo request, StreamObserver<ServerResponse> responseObserver) {
        String name = request.getName();
        String farewellMessage = "Goodbye " + name + "!";

        responseObserver.onNext(ServerResponse.newBuilder()
                .setMessage(farewellMessage)
                .build());
        responseObserver.onCompleted();

        System.out.println(name + " disconnected.");
    }

    @Override
    public StreamObserver<Message> sendMessage(StreamObserver<Message> responseObserver) {
        clients.add(responseObserver);

        return new StreamObserver<>() {
            @Override
            public void onNext(Message message) {
                System.out.println("Received message: " + message.getContent());

                // Retransmit message to all connected clients
                for (StreamObserver<Message> client : clients) {
                    client.onNext(message);
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error: " + t.getMessage());
                clients.remove(responseObserver);
            }

            @Override
            public void onCompleted() {
                System.out.println("Client disconnected.");
                clients.remove(responseObserver);
                responseObserver.onCompleted();
            }
        };
    }
}
