package pdytr.ejercicio3;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class ChatServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        ChatServiceImpl chatService = new ChatServiceImpl();

        Server server = ServerBuilder.forPort(50051)
                .addService(chatService)
                .build();
        System.out.println("\n\n\nChat Server iniciado en el puerto 50051.");
        server.start();
        server.awaitTermination();
    }
}
