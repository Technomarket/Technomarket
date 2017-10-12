package model;

import java.util.HashMap;
import java.util.HashSet;

public class User {
	
	private long userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String gender;
	private String birthDate;
	private boolean isAdmin;
	private HashSet<Product> favourites;
	private HashMap<Product, Integer> basket;
	private HashSet<Order> orders;
	
	

}
