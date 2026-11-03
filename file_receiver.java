//Java file transfer client
//Author: Ian Lindquist

import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;

public class file_receiver{

//main
public static void main(String[] args){
	int portNumber=Integer.parseInt(args[0]);
	try {
		ServerSocket socket = new ServerSocket(portNumber);
		String saveDir = "/tmp/downloads";
		System.out.println("Listening on port: " + portNumber);
		
		while(true){
			Socket client = socket.accept(); //Wait for connection and accept it 
			System.out.println("Connection accepted");
			InputStream in = client.getInputStream();  // Set up an input stream
			ByteArrayOutputStream fileInfoReader = new ByteArrayOutputStream(); // set up byte reader in preparation to read the file name & size
			int i;
			while((i=in.read()) != -1 && i != '\n'){ //read in bytes until newline hit
				fileInfoReader.write(i);
			} 
			String fileName = fileInfoReader.toString("UTF-8").trim(); //convert to string filename
			fileInfoReader.reset(); //clears the memory buffer
			while((i=in.read()) != -1 && i != '\n'){ //read in bytes until newline hit
				fileInfoReader.write(i);
			}
			int bufSize = Integer.parseInt(fileInfoReader.toString("UTF-8").trim());
			System.out.println(bufSize);
			System.out.println("Downloading file: " + fileName);
			File dir = new File(saveDir);
		if (!dir.exists()) dir.mkdirs(); //if directory doesn't exist, create it
			File saveFile = new File (saveDir, fileName); //set save location/file using the file name
			FileOutputStream outFile = new FileOutputStream(saveFile); //create output handle to file
			byte[] buffer = new byte[4096]; //create the byte buffer, 4K in size
			int bytesRemaining = bufSize; // Need to know total size which we parsed earlier
			while ((bytesRemaining >0)){
				int bytesToRead = Math.min(buffer.length, bytesRemaining); //Use Math.min to create boundary on what's pulled in, i.e. if there's less than buffer size remaining it won't write the entire buffer.
				int bytesRead = in.read(buffer, 0, bytesToRead);
				if (bytesRead == -1){
				System.err.println("ERROR: Connection dropped!!");
				break;
			}
			outFile.write(buffer,0,bytesRead);
			bytesRemaining -= bytesRead; //Decrementing remaining bytes by just proceessed bytes
			}
			outFile.close(); //close outfile when done
			System.out.println("Saved file: " +fileName);
			client.close(); //close input stream and socket
		}
	
	}
	catch (Exception e){
		e.printStackTrace(); //error catcher and output stack trace
	}

}

}