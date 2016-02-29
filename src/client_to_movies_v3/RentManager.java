package client_to_movies_v3;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import client_to_movies_v3.Movie;
import client_to_movies_v3.Movie.Genre;
import client_to_movies_v3.Person.Gender;


public class RentManager {
	
	public enum Command { GET, PUT, EXIT }
	
	public static long totalIncome(List<Buyable> products) {
		long income = 0;
		for (Buyable buyable : products) {
			income += buyable.getPrice();
		}
		return income;
	}
	
	public static String menu() {
		String input;
		do {
			System.out.println("Please choose a menupoint: ");
			System.out.println("1. PUT");
			System.out.println("2. GET");
			System.out.println("3. EXIT");
			input = new Scanner(System.in).nextLine();
		} while (!(input.equals("1") || input.equals("2") || input.equals("3")));
		return input;
	}
	
	public static void main(String[] args) {

		// customers
		Person me = new Person("Adam", "Vegh", Gender.MALE);
		Person lukacs = new Person("Lukacs", "Zsori", Gender.MALE);
		Person danci = new Person("Daniel", "Koics", Gender.MALE);
		Person balazs = new Person("Balazs", "Benedek", Gender.MALE);
		Person anna = new Person("Anna", "Zsorine", Gender.FEMALE);
		
		
		
		// authors
		Person stroustrup = new Person("Bjarne", "Stroustrup", Gender.MALE, 10000);
		Person sierra = new Person("Kathy", "Sierra", Gender.FEMALE, 5000);
		
		// books
		Book cpp = new Book(stroustrup, "The C++ Programming Language", me);
		Book java = new Book(sierra, "Head First Java", danci);
		
		
		
		// stuff
		List<Person> stuffOfMortal = new ArrayList<>();
		Person boon = new Person("Ed", "Boon", Gender.MALE, 2000);
		Person edwards = new Person("John", "Edwards", Gender.MALE, 1600);
		Person garcia = new Person("Paulo", "Garcia", Gender.MALE, 1500);
		stuffOfMortal.add(boon);
		stuffOfMortal.add(edwards);
		stuffOfMortal.add(garcia);
		
		List<Person> stuffOfF12015 = new ArrayList<>();
		Person jeal = new Person("Paul", "Jeal", Gender.MALE, 1800);
		Person rDarling = new Person("Richard", "Darling", Gender.MALE, 1700);
		Person dDarling = new Person("David", "Darling", Gender.MALE, 1700);
		Person jDarling = new Person("Jim", "Darling", Gender.MALE, 1700);
		Person ambani = new Person("Anil", "Ambani", Gender.MALE, 1600);
		stuffOfF12015.add(jeal);
		stuffOfF12015.add(rDarling);
		stuffOfF12015.add(dDarling);
		stuffOfF12015.add(jDarling);
		stuffOfF12015.add(ambani);
		
		// games
		Game mortalKombatX = new Game("Mortal Kombat X", true, (ArrayList<Person>) stuffOfMortal, 100, balazs);
		Game f12015 = new Game("F1 2015", false, (ArrayList<Person>) stuffOfF12015, 80, me);
		
		
		
		// cast
		List<Person> castOfTitanic = new ArrayList<>();
		Person dicaprio = new Person("Leonardo", "Dicaprio", Gender.MALE, 2000000);
		Person winslet = new Person("Kate", "Winslet", Gender.FEMALE, 1000000);
		castOfTitanic.add(dicaprio);
		castOfTitanic.add(winslet);
		
		List<Person> castOfMatrix = new ArrayList<>();
		Person reeves = new Person("Keanu", "Reeves", Gender.MALE, 100000000);
		Person moss = new Person("Carrie-Anne", "Moss", Gender.FEMALE, 10000000);
		castOfMatrix.add(reeves);
		castOfMatrix.add(moss);
		
		// movies
		Movie titanic = new Movie("Titanic", Genre.ROMANTIC, 200, 4.8, (ArrayList<Person>) castOfTitanic, 300, anna);
		Movie theMatrix = new Movie("The Matrix", Genre.SCI_FI, 140, 5.0, (ArrayList<Person>) castOfMatrix, 250, lukacs);
		
		
		
		// list of products
		List<Product> products = new ArrayList<>();
		products.add(cpp);
		products.add(java);
		products.add(mortalKombatX);
		products.add(f12015);
		products.add(titanic);
		products.add(theMatrix);
		
		// list of buyable products
		List<Buyable> buyable = new ArrayList<>();
		for (Product product : products) {
			if (product instanceof Buyable) {
				Buyable buyableProduct = (Buyable) product;
				buyable.add(buyableProduct);
			}
		}
		
//		System.out.println("Let's print out the investement of every product:");
//		for (Product product : products) {
//			System.out.println("\t- " + product.getTitle() + " : " + product.getInvestement() + " $(USD)");
//		}
//		System.out.println("\nThe total income by buyable products: " + totalIncome(buyable) + " $(USD)");
//		System.out.println(cpp.id);
		
		
		
		Socket client = null;
		boolean isConnected = false;
		while (!isConnected) {
			try {
				System.out.println("Try to connect...");
				client = new Socket("localhost", 3333);
				isConnected = true;
				System.out.println("Connection is success to the server " + client.getRemoteSocketAddress());
			} catch (IOException e) {
				System.out.println(e);
			} 
		}
		
		while (true) {
			try {
				OutputStream toServer = client.getOutputStream();
				ObjectOutputStream out = new ObjectOutputStream(toServer);

				InputStream fromServer = client.getInputStream();
				ObjectInputStream in = new ObjectInputStream(fromServer);

				String menuPoint = menu();
				Command cmd = menuPoint.equals("1") ? Command.PUT : 
					(menuPoint.equals("2") ? Command.GET : Command.EXIT);
				
				if (cmd.equals(Command.PUT)) {
					System.out.println("Sending " + cmd + " command to server!");
					out.writeObject(cmd);
					out.writeObject(cpp);
					out.writeObject(java);
					out.writeObject(mortalKombatX);
					out.writeObject(f12015);
					out.writeObject(titanic);
					out.writeObject(theMatrix);

					System.out.println("Getting objects from server: ");
					System.out.println(in.readObject());
				} 
				else if (cmd.equals(Command.GET)) {
					System.out.println("Sending " + cmd + " command to server!");
					out.writeObject(cmd);
					
					List<Object> listObject = new ArrayList<>();
					listObject = (List) in.readObject();
					for (Object object : listObject) {
						System.out.println(object);					
					}
				}
				else if (cmd.equals(Command.EXIT)) {
					out.writeObject(cmd);
					System.out.println("Getting message from server: ");
					System.out.println(in.readObject());
					client.close();
					out.close();
					in.close();
					System.out.println("Now client is shotted down too! BYE!");
					System.exit(0);
				}
			} catch (ClassNotFoundException | IOException e) {
				System.out.println(e);
			} 
		}
		
	}
}
