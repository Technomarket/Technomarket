package model;

import java.util.HashMap;

public class Store {
	//Exceptions мисля да е някъв глобален за store и да гърми когато има празни полета
//	private String city;
//	private String address;
	//Използвам адрес зашото иначе трябват много методи за валидаци 
	//и така обединяваме името на града и адрес
	//за да попълним в базата само викаме гет методите и готово
	private long idNumber;
	private Addres addres;
	private HashMap<Product, Integer> product;
	private String phoneNumber;
	private String email;
	private String workingTime;
	private String gps;
	public Store(String phoneNumber, String email,
			String workingTime, String gps, Addres addres) {
		
//		this.city = city;
//		this.address = address;
		if(addres != null){
			this.addres = addres;
		}else{
			//throw ExceptionsForStore();
		}
		//////////////////////////////
		if(correctTelNumber(phoneNumber)){
			this.phoneNumber = phoneNumber;
		}else{
			//throw ExceptionsForStore();
		}
		/////////////////////////////
		if(correctEmail(email)){
			this.email = email;
		}else{
			//throw ExceptionsForStore();
		}
		//////////////////////////
		if(workingTime != null && !workingTime.isEmpty()){
			this.workingTime = workingTime;
		}else{
			//throw ExceptionsForStore();
		}
		if(gps != null && !gps.isEmpty()){
			this.gps = gps;
		}else{
			//throw ExceptionsForStore();
		}
		this.product = new HashMap<>();
	}
	private boolean correctTelNumber(String telNumber){
		//regex for telNumber;
		return false;
		
	}
	private boolean correctEmail(String email){
		//regex for email;
		return false;
		
		
	}
	class Addres{
		private String city;
		private String addres;
		
		public Addres(String city, String addres) {
			if(city != null && !city.isEmpty()){
				this.city  = city;
			}else{
				//throw Exceptions(); -> Nqkuv
			}
			if(addres != null && !addres.isEmpty()){
				this.addres = addres;
			}else{
				//throw Exceptions();
			}
		}
		
		public String getCity() {
			return city;
		}
		public String getAddres() {
			return addres;
		}
	}
	
	public void setIdNumber(long idNumber) {
		this.idNumber = idNumber;
	}
	
	
	
	
}
