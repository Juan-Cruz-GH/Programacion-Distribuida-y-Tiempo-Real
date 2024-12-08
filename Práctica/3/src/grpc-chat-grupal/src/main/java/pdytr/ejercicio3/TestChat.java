package pdytr.ejercicio3;

import java.io.ByteArrayInputStream;

public class TestChat {
    public static void main(String[] args) {
        // ChatServer server = new ChatServer();

        for (int i = 0; i < 10; i++) {
            int clientId = i;
            Runnable task = () -> {
                String clientName = "pepe" + clientId;
                ChatClient client = new ChatClient("localhost", 50051, clientName);

                try {
                  client.connect();

                  String simulatedInput = "Mensaje";
                  ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
                  System.setIn(inputStream);    
                        
                  client.chat();
               } finally {
                  client.disconnect();
                  client.shutdown();
               }
            };

            Thread thread = new Thread(task);
            thread.start();
        }
    }
}
