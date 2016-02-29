package client_to_movies_v3;

import java.io.Serializable;

public class Person implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1186072692285300701L;
	
	private String firstName;
	private String lastName;
	enum Gender { MALE, FEMALE };
	Gender gender;
	private int salary;
	
	Person(String firstName, String lastName, Gender gender, int salary) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.salary = salary;
	}
	

	Person(String firstName, String lastName, Gender gender) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
	}


	public String getFirstName() { return firstName; }
	public void setFirstName(String firstName) { this.firstName = firstName; }

	public String getLastName() { return lastName; }
	public void setLastName(String lastName) { this.lastName = lastName; }

	public Gender getGender() { return gender; }
	public void setGender(Gender gender) { this.gender = gender; }

	public int getSalary() { return salary; }
	public void setSalary(int salary) { this.salary = salary; }

	@Override
	public String toString() {
		return getFirstName() + ", " + getLastName() + ", " + getGender() + ", " + getSalary();
	}
}
