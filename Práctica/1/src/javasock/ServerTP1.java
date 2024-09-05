 import java.io.*;
 import java.net.*;
 
 // Cómo usar:
 // 1. Compilar: javac ServerTP1.java
 // 2. Ejecutar: java ServerTP1 8080
 public class ServerTP1
 {
   public static void chequearArgumentos(String[] args) {
    if ((args.length != 1) || (Integer.valueOf(args[0]) <= 0) )
    {
      System.out.println("Error en el argumento.");
      System.exit(1);
    }
   }
   public static void main(String[] args) throws IOException
   {
     chequearArgumentos(args);
 
     ServerSocket serverSocket = null;    
     try
     {
       serverSocket = new ServerSocket(Integer.valueOf(args[0]));
     } 
     catch (Exception e)
     {
       System.out.println("Error creando el ServerSocket.");
       System.exit(1);
     }
 
     /* The socket to be created on the connection with the client */
     Socket connectedSocket = null;
 
     try 
     {
      connectedSocket = serverSocket.accept();
     }
     catch (IOException e)
     {
       System.err.println("Error aceptando la conexión.");
       System.exit(1);
     }
 
     DataInputStream fromClient = new DataInputStream(connectedSocket.getInputStream());
     DataOutputStream toClient  = new DataOutputStream(connectedSocket.getOutputStream());
 
     byte[] buffer = new byte[256];
     
     // Leer {buffer} cantidad de datos del Socket.
     fromClient.read(buffer);
 
     String str = new String(buffer);
 
     System.out.println("Here is the message: " +  str);
 
     String strresp = "I got your message";
 
     System.out.println("strrsp " + strresp);
 
     buffer = strresp.getBytes();
 
     toClient.write(buffer, 0, buffer.length);
 
    // Cerrar todas las conexiones.
     fromClient.close();
     toClient.close();
     connectedSocket.close();
     serverSocket.close();
   }
 }
 