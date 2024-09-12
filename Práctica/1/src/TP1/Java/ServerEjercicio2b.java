import java.io.*;
import java.net.*;
import java.util.zip.CRC32;

public class ServerEjercicio2b {
    public static void main(String[] args) throws IOException {
        // Validar los argumentos
        if ((args.length != 1) || (Integer.valueOf(args[0]) <= 0)) {
            System.out.println("1 argumento necesario: puerto");
            System.exit(1);
        }

        // Crear el server socket
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(Integer.valueOf(args[0]));
        } catch (Exception e) {
            System.out.println("Error creando el server socket.");
            System.exit(1);
        }

        // Esperar conexión de un cliente
        Socket connectedSocket = serverSocket.accept();

        // Crear streams de entrada/salida
        DataInputStream fromClient = new DataInputStream(connectedSocket.getInputStream());
        DataOutputStream toClient = new DataOutputStream(connectedSocket.getOutputStream());

        // Tamaños de buffer esperados del cliente
        int[] expectedBufferSizes = {
            (int) Math.pow(10, 3),  // 10^3
            (int) Math.pow(10, 4),  // 10^4
            (int) Math.pow(10, 5),  // 10^5
            (int) Math.pow(10, 6)   // 10^6
        };

        // Procesar cada uno de los tamaños de buffer
        for (int expectedSize : expectedBufferSizes) {
            // Crear un buffer grande para recibir el mensaje
            byte[] buffer = new byte[expectedSize];

            int totalBytesRead = 0;
            int bytesRead;
            // Leer los datos del cliente hasta completar el tamaño esperado
            while (totalBytesRead < expectedSize) {
                bytesRead = fromClient.read(buffer, totalBytesRead, expectedSize - totalBytesRead);
                if (bytesRead == -1) break;
                totalBytesRead += bytesRead;
            }

            // Leer el checksum enviado por el cliente
            long receivedChecksum = fromClient.readLong();

            // Calcular el checksum de los datos recibidos
            CRC32 crc = new CRC32();
            crc.update(buffer, 0, totalBytesRead);
            long computedChecksum = crc.getValue();

            // Verificar si el checksum es correcto
            if (computedChecksum == receivedChecksum) {
                System.out.println("Checksum recibido: " + receivedChecksum);
                System.out.println("Checksum computado: " + computedChecksum);
            } else {
                System.out.println("Error de checksum.");
            }

            // Mostrar cuántos bytes fueron leídos en total para el buffer actual
            System.out.println("Recibidos " + totalBytesRead + " bytes para el buffer de tamaño: " + expectedSize);

            // Enviar la respuesta de vuelta al cliente
            toClient.write(buffer, 0, totalBytesRead);
            toClient.flush();
        }

        // Cerrar las conexiones
        fromClient.close();
        toClient.close();
        connectedSocket.close();
        serverSocket.close();
    }
}
