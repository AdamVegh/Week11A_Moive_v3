package client_to_movies_v3;

import java.io.Serializable;

public class Book extends Product implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7643168934776574697L;
	private Person author;
	
	Book(Person author, String title, Person person) {
		super(title, person);
		this.author = author;
	}
	
	public Person getAuthor() { return author; }
	public void setAuthor(Person author) { this.author = author; }
	
	@Override
	public long getInvestement() {
		return author.getSalary();
	}

	@Override
	public String toString() {
		StringBuffer bookData = new StringBuffer();

		bookData.append("Book title: " + getTitle() + "\n");
		bookData.append("Book ID: " + getID() + "\n");
		bookData.append("Book's total investments: " + getInvestement() + " $\n");
		bookData.append("Author: " + getAuthor() + "\n");
		bookData.append("Renter: " + getPerson() + "\n");
		bookData.append("\n----------------------------------------------------------\n");

		return bookData.toString();
	}
	
	
	
}
