//Java File Sender
//Author: Michael Iurco 2026

//input as        IP            Port  File
//java FileSender 192.168.1.102 5000 report.pdf
import java.io.*;
import java.net.*;

public class FileSender{
    public static void main(String[] args) throws IOException{

        if (args.length != 3) {
            System.err.println("Usage: java FileSender <IP address> <port number> <File>");
            System.exit(1);
        }
        
        InetAddress ip = InetAddress.getByName(args[0]);
        int portNumber = Integer.parseInt(args[1]);
        File file = new File (args[2]);
        String fileName = file.getName();
        int fileSize = (int)file.length();

        System.out.println("Connecting...");
        
        try (
            Socket socket = new Socket(ip,portNumber);
            PrintWriter out =
                new PrintWriter(socket.getOutputStream(), true);
        ){
            System.out.println("Connected.");

            //send file name first
            out.println(fileName);

            //send size of file in bytes
            out.println(fileSize);
            
            // "try-with-resources": the stream will be closed automatically
            try (FileInputStream fos = new FileInputStream(file)) {
                System.out.println("Sending:\n" + fileName);
                int i;
                while ((i = fos.read()) != -1){
                    out.print((char)i);
                }
                out.close();

                System.out.println("Transfer complete.");
            } catch (IOException e) {
                System.out.println("An error occurred: " + e.getMessage());
            }

        }catch (IOException e) {
            System.out.println("Exception caught when trying to connect to" + ip + "on port"
                + portNumber);
            System.out.println(e.getMessage());
        }
    }
}