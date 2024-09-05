/*
* Client.java
 * Just sends stdin read data to and receives back some data from the server
 *
 * usage:
 * java Client serverhostname port
 */

 import java.io.*;
 import java.net.*;
 import java.util.concurrent.TimeUnit;
 import java.lang.Math;
 import java.text.DecimalFormat;
 
 public class ClientEj3 {
   public static void main(String[] args) throws IOException
   {
     /* Check the number of command line parameters */
     if ((args.length != 2) || (Integer.valueOf(args[1]) <= 0) )
     {
       System.out.println("2 arguments needed: serverhostname port");
       System.exit(1);
     }
 
     /* The socket to connect to the echo server */
     Socket socketwithserver = null;
 
     try /* Connection with the server */
     { 
       socketwithserver = new Socket(args[0], Integer.valueOf(args[1]));
     }
     catch (Exception e)
     {
       System.out.println("ERROR connecting");
       System.exit(1);
     } 
 
     /* Streams from/to server */
     DataInputStream  fromserver;
     DataOutputStream toserver;
 
     /* Streams for I/O through the connected socket */
     fromserver = new DataInputStream(socketwithserver.getInputStream());
     toserver   = new DataOutputStream(socketwithserver.getOutputStream());
 
     /* Buffer to use with communications (and its length) */
     byte[] buffer = new byte[(int) Math.pow(10, 1)];
 
     long startTime =  System.nanoTime();
     /* Send read data to server */
     toserver.write(buffer, 0, buffer.length);
     
     /* Recv data back from server (get space) */
     buffer = new byte[256];
     fromserver.read(buffer);

     long finishTime = System.nanoTime();
     double timeInMs = TimeUnit.NANOSECONDS.toMillis(finishTime - startTime);
     DecimalFormat df = new DecimalFormat("#.##");
 
     /* Show data received from server */
     String resp = new String(buffer);
     System.out.println(resp);
     System.out.println("Time: " + df.format(timeInMs) + " ms");
     
     fromserver.close();
     toserver.close();
     socketwithserver.close();
   }
 }