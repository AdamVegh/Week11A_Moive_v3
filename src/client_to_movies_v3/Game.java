package client_to_movies_v3;

import java.io.Serializable;
import java.util.ArrayList;

public class Game extends Product implements Buyable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4756136709090072535L;
	private boolean preOrdered;
	private ArrayList<Person> staff;
	private int price;
	
	Game(String title, boolean preOrdered, ArrayList<Person> staff, int price, Person person) {
		super(title, person);
		this.preOrdered = preOrdered;
		this.staff = staff;
		this.price = price;
	}
	
	public boolean isPreOrdered() { return preOrdered; }
	public void setPreOrdered(boolean preOrdered) { this.preOrdered = preOrdered; }

	public ArrayList<Person> getStaff() { return staff; }
	public void setStaff(ArrayList<Person> staff) { this.staff = staff; }

	public void setPrice(int price) { this.price = price; }

	@Override
	public int getPrice() { return preOrdered ? (int)(0.8 * price) : price; }

	@Override
	public long getInvestement() {
		long totalInvestement = 0;
		for (Person person : staff) {
			totalInvestement += person.getSalary();
		}
		return totalInvestement;
	}

	@Override
	public String toString() {
		StringBuffer gameData = new StringBuffer();

		gameData.append("Game title: " + getTitle() + "\n");
		gameData.append("Game ID: " + getID() + "\n");
		gameData.append("Game's staff: " + getStaff() + "\n");
		gameData.append("Game's total investments: " + getInvestement() + " $\n");
		if (preOrdered) {
			gameData.append(
			        "Game price: " + getPrice() + " $ (Original price is " + getPrice() * 1.25 + ")\n");
		} else {
			gameData.append("Game price: " + getPrice() + "\n");
		}
		gameData.append("Product renter: " + getPerson() + "\n");
		gameData.append("\n----------------------------------------------------------\n");

		return gameData.toString();
	}
	
}
