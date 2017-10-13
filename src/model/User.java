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
	private boolean gender;
	private LocalDate birthDate;
	private boolean isAdmin;
	private HashSet<Product> favourites;
	private HashMap<Product, Integer> basket;
	private HashSet<Order> orders;
	
	
	public User(String firstName, String lastName, String email, String password, boolean gender, LocalDate birthDate,
			boolean isAdmin) {
        if(!correctDateForNameOfUser(firstName, lastName)){
//        	throw new UserNotCorrectDataExpetions();
        }else{
        	this.firstName = firstName;
        	this.lastName = lastName;
        }
        if(!correctEmailAdres(email)){
//        	throw new UserNotCorrectDataExpetions();
        }else{
        	this.email = email;
        }
        if(!correctPassword(password)){
//        	throw new UserNotCorrectDataExpetions();
        }else{
        	this.password = password;
        }
        //This field is not validate !
		this.gender = gender;
		this.birthDate = birthDate;
		this.isAdmin = isAdmin;
		this.favourites = new HashSet<>();
		this.basket = new HashMap<>();
		this.orders = new HashSet<>();
	}
	private boolean correctDateForNameOfUser(String firstName, String lastName){
		if(firstName == null || firstName.isEmpty() || firstName.length() < 2){
			return false;
		}
		if(lastName == null || lastName.isEmpty() || lastName.length() < 2){
			return false;
		}
		return true;
	}
	
	private boolean correctEmailAdres(String email){
		//Regex fo email address
		return false;
	}
	private boolean correctPassword(String password){
		//Regex for correct password
		//if is mach retur true;
		return false;
	}
	
	public void setId(int id){
		this.userId = id;
		
	}
	
	
}
