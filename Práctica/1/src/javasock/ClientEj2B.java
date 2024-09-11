import java.io.*;
import java.net.*;
import java.util.zip.CRC32;

public class ClientEj2B {
    public static void main(String[] args) throws IOException {
        // Validar los argumentos
        if ((args.length != 2) || (Integer.valueOf(args[1]) <= 0)) {
            System.out.println("Error en los argumentos.");
            System.exit(1);
        }

        // Establecer conexión con el servidor
        Socket socketwithserver = null;
        try {
            socketwithserver = new Socket(args[0], Integer.valueOf(args[1]));
        } catch (Exception e) {
            System.out.println("Error creando el Socket.");
            System.exit(1);
        }

        socketwithserver.setSoTimeout(30000); // 30 segundos de timeout

        // Crear streams de entrada/salida
        DataInputStream fromServer = new DataInputStream(socketwithserver.getInputStream());
        DataOutputStream toServer = new DataOutputStream(socketwithserver.getOutputStream());

        // Tamaños de buffer
        int[] bufferSizes = {
            (int) Math.pow(10, 3),  // 10^3
            (int) Math.pow(10, 4),  // 10^4
            (int) Math.pow(10, 5),  // 10^5
            (int) Math.pow(10, 6)   // 10^6
        };

        // Ejecutar con diferentes tamaños de buffer
        for (int size : bufferSizes) {
            System.out.println("Enviando un buffer de tamaño: " + size + " bytes");

            // Crear buffer del tamaño actual
            byte[] buffer = new byte[size];
            
            // Llenar el buffer con datos simulados
            for (int i = 0; i < size; i++) {
                buffer[i] = (byte) i;
            }

            // Calcular el checksum del buffer
            CRC32 crc = new CRC32();
            crc.update(buffer, 0, buffer.length);
            long checksum = crc.getValue();
  
        
            // Enviar datos al servidor
            toServer.write(buffer, 0, buffer.length);
            toServer.flush();

            // Enviar el checksum al servidor
            toServer.writeLong(checksum);
            toServer.flush();

             // Recibir respuesta del servidor
             byte[] responseBuffer = new byte[size];
             int totalBytesRead = 0;
             int bytesRead;
             
             while (totalBytesRead < size) {
                 bytesRead = fromServer.read(responseBuffer, totalBytesRead, size - totalBytesRead);
                 if (bytesRead == -1) break;
                 totalBytesRead += bytesRead;
             }

        }

        // Cerrar conexiones
        fromServer.close();
        toServer.close();
        socketwithserver.close();
    }
}
