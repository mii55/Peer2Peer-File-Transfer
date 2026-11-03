//Java file transfer client
//Author: Ian Lindquist

import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;

public class FileReceiver{

//main
public static void main(String[] args){
	
	 if (args.length != 1) {
            System.err.println("Usage: java FileReceiver <port number>");
            System.exit(1);
        }
	int portNumber=Integer.parseInt(args[0]);
	
	try {
		ServerSocket socket = new ServerSocket(portNumber);
		String saveDir = "/tmp/downloads";
		System.out.println("Listening on port: " + portNumber);
		
		while(true){
			Socket client = socket.accept(); //Wait for connection and accept it 
			System.out.println("Connection accepted");
			DataInputStream in = new DataInputStream(client.getInputStream()); // Set up an input stream
			ByteArrayOutputStream fileInfoReader = new ByteArrayOutputStream(); // set up byte reader in preparation to read the file name & size
			String fileName = in.readUTF(); //read in the filename
			long fileSize = in.readLong(); //read in the file size
			System.out.println("Receiving file: " + fileName + " (" + fileSize + " bytes)");
			File dir = new File(saveDir);
		if (!dir.exists()) dir.mkdirs(); //if directory doesn't exist, create it
			File saveFile = new File (saveDir, fileName); //set save location/file using the file name
		try (BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(saveFile))) {
			byte[] buffer = new byte[4096]; //create the byte buffer, 4K in size
			int bytesRead; //bytes read during current iteration
			long totalBytesRead = 0; //running total of bytes written to file
            // read up to the expected file size
		while (totalBytesRead < fileSize && (bytesRead = in.read(buffer, 0, (int)Math.min(buffer.length, fileSize - totalBytesRead))) != -1) {
				fos.write(buffer, 0, bytesRead);
				totalBytesRead += bytesRead;
		}
			fos.flush(); //write remaining bytes in memory to file
			System.out.println("Saved file: " +fileName);
			client.close(); //close input stream and socket
		}catch (IOException e) {
                    System.err.println("Error: " + e.getMessage());
                }
		}
	
	} catch (Exception e){
		System.err.println("Could not listen on port " + portNumber + ": " + e.getMessage());
	}

}

}