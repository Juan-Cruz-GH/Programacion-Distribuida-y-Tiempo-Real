package pdytr.ejercicio3;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestChat {
    public static void main(String[] args) throws IOException, InterruptedException {
        ChatClientEjercicio4[] clientes = new ChatClientEjercicio4[5];
        for (int i = 0; i < 5; i++) {
            String clientName = "Cliente-" + i;
            clientes[i] = new ChatClientEjercicio4("localhost", 50051, clientName);
        }

        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            int cliente = i;
            executor.submit(() -> {
                try {
                    clientes[cliente].connect();
                    try {
                        Thread.sleep(1500);
                    } catch (Exception e) {
                    }
                    clientes[cliente].chat();
               } finally {
                    try {
                        Thread.sleep(1500);
                    } catch (Exception e) {
                    }
                    clientes[cliente].disconnect();
                    clientes[cliente].shutdown();
               }
            });
        }

        executor.shutdown();
    }
}
