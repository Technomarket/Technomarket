package model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;

public class User {

	private long userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String gender;
	private LocalDate birthDate;
	private boolean isAdmin;
	private boolean isAbonat;
	private boolean isBanned;
	private HashSet<Product> favourites;
	private HashMap<Product, Integer> basket;
	private HashSet<Order> orders;

	public User() {

	}

	public User(String firstName, String lastName, String email, String password, String gender, LocalDate birthDate,
			boolean isAdmin, boolean isAbonat, boolean isBanned) {
		if (!correctDateForNameOfUser(firstName, lastName)) {
			// throw new UserNotCorrectDataExpetions();
		} else {
			this.firstName = firstName;
			this.lastName = lastName;
		}
		if (!correctEmailAdres(email)) {
			// throw new UserNotCorrectDataExpetions();
		} else {
			this.email = email;
		}
		if (!correctPassword(password)) {
			// throw new UserNotCorrectDataExpetions();
		} else {
			this.password = password;
		}
		// This field is not validate !
		if (gender != null && !gender.isEmpty()) {
			this.gender = gender;
		} else {
			// throw new UserNotCorrectDataExpetions();
		}

		this.birthDate = birthDate;
		this.isAdmin = isAdmin;
		this.isAbonat = isAbonat;
		this.isBanned = isBanned;
		this.favourites = new HashSet<>();
		this.basket = new HashMap<>();
		this.orders = new HashSet<>();
	}

	private boolean correctDateForNameOfUser(String firstName, String lastName) {
		if (firstName == null || firstName.isEmpty() || firstName.length() < 2) {
			return false;
		}
		if (lastName == null || lastName.isEmpty() || lastName.length() < 2) {
			return false;
		}
		return true;
	}

	private boolean correctEmailAdres(String email) {
		// Regex fo email address
		return false;
	}

	private boolean correctPassword(String password) {
		// Regex for correct password
		// if is mach retur true;
		return false;
	}

	public void setId(long id) {
		this.userId = id;

	}
	
	public void setOrders(HashSet<Order> orders) {
		this.orders = orders;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public String getGender() {
		return gender;
	}

	public boolean getIsAbonat() {
		return this.isAbonat;
	}

	public boolean getIsBanned() {
		return this.isBanned;
	}

	public boolean getIsAdmin() {
		return this.isAdmin;
	}

	public void setAbonat(boolean isAbonat) {
		this.isAbonat = isAbonat;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public void setBanned(boolean isBanned) {
		this.isBanned = isBanned;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
