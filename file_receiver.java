//Java file transfer client
//Author: Ian Lindquist

import java.io.*;
import java.net.Socket;

public class file_receiver{

//main
public static void main(String[] args){
	int portNumber=Integer.parseInt(args[0]);
	try {
	ServerSocket socket = new ServerSocket(portNumber);
	String saveDir = "/tmp/downloads";
	
	while(true){
		Socket client = serverSocket.accept();
		InputStream in = client.getInputStream();
		byte[] buffer = new byte[4096]; //create the byte buffer
		//To Do: parsing in filename
	}
	
	}
	catch (Exception e){
		e.printStackTrace();
	}

}

}