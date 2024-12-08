package pdytr.ejercicio3;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestChat {
    public static void main(String[] args) throws IOException, InterruptedException {
        ChatClient[] clientes = new ChatClient[10];
        for (int i = 0; i < 10; i++) {
            String clientName = "Cliente-" + i;
            clientes[i] = new ChatClient("localhost", 50051, clientName);
        }

        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            int cliente = i;
            executor.submit(() -> {
                try {
                    clientes[cliente].connect();
                    clientes[cliente].chat();
               } finally {
                    clientes[cliente].disconnect();
                    clientes[cliente].shutdown();
               }
            });
        }

        executor.shutdown();
    }
}
