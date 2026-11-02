//Connect to the receiver
//Transmit the file

//input as        IP            Port  File
//java FileSender 192.168.1.102 5000 report.pdf
import java.io.*;
import java.net.*;
//import java.nio.file.*;
//import java.nio.file.attribute.BasicFileAttributes;

public class FileSender{
    public static void main(String[] args) throws IOException{

        if (args.length != 3) {
            System.err.println("Usage: java FileSender <IP address> <port number> <File>");
            System.exit(1);
        }
        
        InetAddress ip = InetAddress.getByName(args[0]);
        int portNumber = Integer.parseInt(args[1]);
        File file = new File (args[2]);

        //BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
        
        try ( 
            ServerSocket serverSocket = new ServerSocket(portNumber,10,ip);
            //waits until a client starts up and requests a connection on the host and port of this server
            Socket clientSocket = serverSocket.accept();
            PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        ){
            String inputLine, outputLine;
            //TO DO: implement input/output line from protocol
            //TO DO: implement sending file through byte stream

            
            // "try-with-resources": the stream will be closed automatically
            try (FileInputStream fos = new FileInputStream(file)) {//client should use FileOutputStream
                int i;
                while ((i = fos.read()) != -1){
                    System.out.print((char)i); //PLACEHOLDER
                }
            } catch (IOException e) {
                System.out.println("An error occurred: " + e.getMessage());
            }

        }catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}