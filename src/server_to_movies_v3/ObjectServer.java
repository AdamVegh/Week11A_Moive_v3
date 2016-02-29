package server_to_movies_v3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import client_to_movies_v3.RentManager.Command;

public class ObjectServer {
	
	enum ServerMode { LOAD, SAVE }
	
	public static Object load() {
		List<Object> objList = new ArrayList<>();
		try {
			FileInputStream fileIn = new FileInputStream("output.ser");
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			while (true) {
				objList.add(objectIn.readObject());
			}
		} catch (Exception e) {
		}
		return objList;
	}
	
//	public static void save() {
//		
//	}
	
	public static void main(String[] args) {
		System.out.println("Press enter to start server!");
		new Scanner(System.in).nextLine();
		
		ServerSocket sS = null;
		Socket server = null;
		try {
			sS = new ServerSocket(3333);
			System.out.println("Server socket is built up!");
			System.out.println("Waiting for the client connection...");
			server = sS.accept();
			System.out.println("Connection to the remote socket " + server.getLocalPort());
		} catch (IOException e) {
			System.out.println(e);
		}

		while (true) {
			try {
				InputStream fromClient = server.getInputStream();
				ObjectInputStream in = new ObjectInputStream(fromClient);

				OutputStream toClient = server.getOutputStream();
				ObjectOutputStream out = new ObjectOutputStream(toClient);

				Object command = in.readObject();
				System.out.println("Get command from client: ");
				System.out.println(command);

				if (command.equals(Command.PUT)) {
					// fixing f*cking header problem...
					if (!new File("output.ser").exists()) {
						FileOutputStream fOS = new FileOutputStream(new File("output.ser"));
						ObjectOutputStream oOS = new ObjectOutputStream(fOS);
						oOS.close();
					}
					FileOutputStream fOS = new FileOutputStream(new File("output.ser"), true);
					AppendingObjectOutputStream oOS = new AppendingObjectOutputStream(fOS);
				
					oOS.writeObject(in.readObject());
					oOS.writeObject(in.readObject());
					oOS.writeObject(in.readObject());
					oOS.writeObject(in.readObject());
					oOS.writeObject(in.readObject());
					oOS.writeObject(in.readObject());
					
					System.out.println("Sending objects into the file!");
					out.writeObject("OK, I'm putting the objects into the file!");
				}
				else if (command.equals(Command.GET)) {
					
					out.writeObject(load());
					System.out.println("Send objects to client!");
				}
				else if (command.equals(Command.EXIT)) {
					
					sS.close();
					String closedConn = "Server is shotted down!";
					System.out.println(closedConn);
					out.writeObject(closedConn);
					in.close();
					out.close();
					System.exit(0);
				}
				
			} catch (ClassNotFoundException | IOException e) {
				System.out.println(e);
			}
		}
	}
}
