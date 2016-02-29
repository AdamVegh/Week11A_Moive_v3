package client_to_movies_v3;

import java.io.Serializable;
import java.util.ArrayList;

public class Movie extends Product implements Buyable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -325394741253359296L;

	public enum Genre { ACTION, THRILLER, HORROR, SCI_FI, ROMANTIC, COMEDY, DRAMA };
	public Genre genre;
	private long duration;
	private double rate;
	private ArrayList<Person> cast;
	private int price;
	
	Movie(String title, Genre genre, long duration, double rate, ArrayList<Person> cast, int price, Person person) {
		super(title, person);
		this.genre = genre;
		this.duration = duration;
		this.rate = rate;
		this.cast = cast;
		this.price = price;
	}
	
	public Genre getGenre() { return genre; }
	public void setGenre(Genre genre) { this.genre = genre; }

	public long getDuration() { return duration; }
	public void setDuration(long duration) { this.duration = duration; }

	public double getRate() { return rate; }
	public void setRate(double rate) { this.rate = rate; }

	public ArrayList<Person> getCast() { return cast; }
	public void setCast(ArrayList<Person> cast) { this.cast = cast; }

	public int getPrice() { return price; }
	public void setPrice(int price) { this.price = price; }
	
	@Override
	public long getInvestement() {
		long totalInvestement = 0;
		for (Person person : cast) {
			totalInvestement += person.getSalary();
		}
		return totalInvestement;
	}
	
	@Override
	public String toString()
	{
		StringBuffer movieData = new StringBuffer();
		movieData.append("Movie title: " + getTitle() + "\n");
		movieData.append("Movie ID: " + getID() + "\n");
		movieData.append("Genre: " + getGenre() + "\n");
		movieData.append("Duration: " + getDuration() + " minutes\n");
		movieData.append("IMDB rate: " + getRate() + "\n");
		movieData.append("Movie protagonists: " + getCast() + "\n");
		movieData.append("Movie's total investments: " + getInvestement() + " $\n");
		movieData.append("Movie DVD price: " + getPrice() + "\n");
		movieData.append("Movie renter: " + getPerson() + "\n");
		movieData.append("\n----------------------------------------------------------\n");
		return movieData.toString();
	}
	
}
