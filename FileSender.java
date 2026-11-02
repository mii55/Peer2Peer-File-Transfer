//Accept a destination IP address
//Accept a destination port
//Accept a filename
//Connect to the receiver
//Transmit the file

//input as        IP            Port  File
//java FileSender 192.168.1.102 5000 report.pdf
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;


class Protocol{
    //TO DO: implement protocol
    //when waiting print : Connecting...
    //upon connection print Connected.
    //when sending print Sending: + file name
    //on success print Trasnfer Complete
}


public class FileSender{
    public static void main(String[] args) throws IOException{

        if (args.length != 3) {
            System.err.println("Usage: java FileSender <IP address> <port number> <File>");
            System.exit(1);
        }
        
        InetAddress ip = InetAddress.getByName(args[0]);
        int portNumber = Integer.parseInt(args[1]);
        Path file = Paths.get(args[2]);

        BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class); // ? is this correct way to store a file

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

        }catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }


    }
}