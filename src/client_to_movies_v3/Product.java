package client_to_movies_v3;

import java.io.Serializable;

public abstract class Product implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2242197926343097327L;
	protected String id = IdGenerator.idGenerator(this);
	protected String title;
	protected Person person;
	
	public Product(String title, Person person) {
		this.title = title;
		this.person = person;
	}
	
	public String getTitle() { return title; }
	public Person getPerson() { return person; }
	public String getID() { return id; }
	public abstract long getInvestement();
}
