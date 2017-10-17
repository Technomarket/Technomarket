package model;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.exceptions.InvalidStoreDataException;
import util.RegexValidator;

public class Store {
	//Exceptions мисля да е някъв глобален за store и да гърми когато има празни полета
//	private String city;
//	private String address;
	//Използвам адрес зашото иначе трябват много методи за валидаци 
	//и така обединяваме името на града и адрес
	//за да попълним в базата само викаме гет методите и готово
	private long storeId;
	private Address address;
	private HashMap<Product, Integer> product;
	private String phoneNumber;
	private String email;
	private String workingTime;
	private String gps;
	
	public Store(String phoneNumber, String email,
			String workingTime, String gps, Address address) throws InvalidStoreDataException {
		
//		this.city = city;
//		this.address = address;
		if(address != null){
			this.address = address;
		}else{
			throw new InvalidStoreDataException();
		}
		//////////////////////////////
		if(RegexValidator.validateMobilePhoneNumber(phoneNumber)){
			this.phoneNumber = phoneNumber;
		}else{
			throw new InvalidStoreDataException();
		}
		/////////////////////////////
		if(RegexValidator.validateEmail(email)){
			this.email = email;
		}else{
			throw new InvalidStoreDataException();
		}
		//////////////////////////
		if(workingTime != null && !workingTime.isEmpty()){
			this.workingTime = workingTime;
		}else{
			throw new InvalidStoreDataException();
		}
		if(gps != null && !gps.isEmpty()){
			this.gps = gps;
		}else{
			throw new InvalidStoreDataException();
		}
		this.product = new HashMap<>();
	}
	
	public class Address{
		private String city;
		private String address;
		
		public Address(String city, String address) throws InvalidStoreDataException {
			if(city != null && !city.isEmpty()){
				this.city  = city;
			}else{
				throw new InvalidStoreDataException();
			}
			if(address != null && !address.isEmpty()){
				this.address = address;
			}else{
				throw new InvalidStoreDataException();
			}
		}
		
		public String getCity() {
			return city;
		}
		public String getAddres() {
			return address;
		}
	}
	
	public void setStoreId(long storeId) {
		this.storeId = storeId;
	}
	public long getStoreId() {
		return storeId;
	}
	public Address getAddres() {
		return address;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public String getWorkingTime() {
		return workingTime;
	}
	public String getGps() {
		return gps;
	}
	
	
	
	
}
