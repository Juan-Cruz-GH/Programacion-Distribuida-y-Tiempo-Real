    package pdytr.ejercicio3;

    import io.grpc.Server;
    import io.grpc.ServerBuilder;

    import java.io.IOException;

    public class ChatServer {

        public static void main(String[] args) throws IOException, InterruptedException {
            Server server = ServerBuilder.forPort(50051)
                    .addService(new ChatServiceImpl())
                    .build();
            System.out.println("\n\n\nChat Server iniciado en el puerto 50051.");
            server.start();
            server.awaitTermination();
        }
    }
