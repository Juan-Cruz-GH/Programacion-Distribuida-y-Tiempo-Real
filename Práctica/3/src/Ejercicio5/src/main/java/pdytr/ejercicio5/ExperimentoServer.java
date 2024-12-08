package pdytr.ejercicio5;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class ExperimentoServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(50051)
                .addService(new ExperimentoServiceImpl())
                .build();

        System.out.println("Servidor iniciado en el puerto: 50051");
        server.start();
        server.awaitTermination();
    }
}
