package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class Order {
	
	public enum Shiping{HOME_ADDRESS, STORE};
	private long orderId;
	private HashMap<Product, Integer> products;
	private BigDecimal price;
	private LocalDateTime time;
	private String address;
	private String userPhoneNumber;
	private String zip;
	private boolean isConfirmed;
	private String notes;
	private Shiping shipingType;
	private boolean isPaid;
	
	public Order(HashMap<Product, Integer> products, String address, String userPhoneNumber, String zip, String notes, Shiping shipingType) {
		this.products = products;
		//calculating the sum of all products and their numbers:
		this.price = calculatePriceOfOrder();
		
		//validating fields:
		if(isAddressValid(address)){
			this.address = address;
		}else{
			// throw new InvalidOrderDataException();
		}
		if(isPhoneNumberValid(userPhoneNumber)){
			this.userPhoneNumber = userPhoneNumber;
		}else{
			// throw new InvalidOrderDataException();
		}
		if(isZipValid(zip)){
			this.zip = zip;
		}else{
			// throw new InvalidOrderDataException();
		}
		this.notes = notes;
		this.shipingType = shipingType;
		
		//setting the date and time of creating a new order to .now():
		this.time = LocalDateTime.now();
		//setting boolean of status to false at the start:
		this.isConfirmed = false;
		this.isPaid = false;
	}

	

	
	
	
	// private system methods:
	
	private BigDecimal calculatePriceOfOrder() {
		BigDecimal totalSum = new BigDecimal(0);
		for (Iterator<Entry<Product, Integer>> iterator = products.entrySet().iterator(); iterator.hasNext();) {
			Entry<Product, Integer> orderedProduct = iterator.next();
			totalSum.add(orderedProduct.getKey().getPrice().multiply(new BigDecimal(orderedProduct.getValue())));
		}
		return totalSum;
	}

	private boolean isZipValid(String zip) {
		if(zip.length() != 4 || zip.charAt(0) == '0'){
			return false;
		}else{
			return true;
		}
	}



	private boolean isPhoneNumberValid(String userPhoneNumber) {
		if(userPhoneNumber.length() != 9 || userPhoneNumber.charAt(0) != '0'){
			return false;
		}else{
			return true;
		}
	}



	private boolean isAddressValid(String address) {
		return address != null && !address.isEmpty();
	}


	//setters and getters:
	
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	
	
	
	
	

	
	
}
