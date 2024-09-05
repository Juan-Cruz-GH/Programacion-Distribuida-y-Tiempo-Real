 import java.io.*;
 import java.net.*;
 
 // Cómo usar:
 // 1. Compilar: javac ClientTP1.java
 // 2. Ejecutar: java ClientTP1 localhost 8080
 public class ClientTP1
 {
   public static void main(String[] args) throws IOException
   {
     if ((args.length != 2) || (Integer.valueOf(args[1]) <= 0) )
     {
       System.out.println("Error en los argumentos.");
       System.exit(1);
     }
 
     Socket socketwithserver = null;
     try 
     { 
       socketwithserver = new Socket(args[0], Integer.valueOf(args[1]));
     }
     catch (Exception e)
     {
       System.out.println("Error creando el Socket.");
       System.exit(1);
     } 
 
     DataInputStream fromServer = new DataInputStream(socketwithserver.getInputStream());
     DataOutputStream toServer = new DataOutputStream(socketwithserver.getOutputStream());
 
     byte[] buffer;
 
     // Create strings of specific lengths
     byte[] buffer1 = new String(new char[103]).replace('\0', 'a').getBytes(); // 103 characters
     byte[] buffer2 = new String(new char[104]).replace('\0', 'b').getBytes(); // 104 characters
     byte[] buffer3 = new String(new char[105]).replace('\0', 'c').getBytes(); // 105 characters
     byte[] buffer4 = new String(new char[106]).replace('\0', 'd').getBytes(); // 106 characters
 
     toServer.write(buffer1, 0, buffer1.length);
     toServer.write(buffer2, 0, buffer2.length);
     toServer.write(buffer3, 0, buffer3.length);
     toServer.write(buffer4, 0, buffer4.length);

     buffer = new byte[256];
     fromServer.read(buffer);
 
     String resp = new String(buffer);
     System.out.println(resp);
     
     // Cerrar todas las conexiones.
     fromServer.close();
     toServer.close();
     socketwithserver.close();
   }
 }
 