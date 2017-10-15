package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import model.exceptions.InvalidOrderDataException;

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
	private String payment;
	private boolean isPaid;
	
	public Order(HashMap<Product, Integer> products, String address, String userPhoneNumber, String zip, String notes, Shiping shipingType, String payment, LocalDateTime time, boolean isConfirmed, boolean isPaid) throws InvalidOrderDataException {
		this.products = products;
		//calculating the sum of all products and their numbers:
		this.price = calculatePriceOfOrder();
		
		//validating fields:
		if(isAddressValid(address)){
			this.address = address;
		}else{
			throw new InvalidOrderDataException();
		}
		if(isPhoneNumberValid(userPhoneNumber)){
			this.userPhoneNumber = userPhoneNumber;
		}else{
			throw new InvalidOrderDataException();
		}
		if(isZipValid(zip)){
			this.zip = zip;
		}else{
			throw new InvalidOrderDataException();
		}
		if(payment != null){
			this.payment = payment;
		}else{
			throw new InvalidOrderDataException();
		}
		if(notes != null){
			this.notes = notes;
		}else{
			throw new InvalidOrderDataException();
		}
		if(shipingType != null){
			this.shipingType = shipingType;
		}else{
			throw new InvalidOrderDataException();
		}
		
		//setting the date and time of creating a new order to .now():
		this.time = time;
		//setting boolean of status to false at the start:
		this.isConfirmed = isConfirmed;
		this.isPaid = isPaid;
	}

	public Order(){
		
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






	public boolean getIsConfirmed() {
		return isConfirmed;
	}






	public void setConfirmed(boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}






	public boolean getIsPaid() {
		return isPaid;
	}






	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}






	public long getOrderId() {
		return orderId;
	}






	public BigDecimal getPrice() {
		return price;
	}






	public LocalDateTime getTime() {
		return time;
	}






	public String getAddress() {
		return address;
	}






	public String getUserPhoneNumber() {
		return userPhoneNumber;
	}






	public String getNotes() {
		return notes;
	}






	public Shiping getShipingType() {
		return shipingType;
	}






	public String getPayment() {
		return payment;
	}






	public HashMap<Product, Integer> getProducts() {
		return (HashMap<Product, Integer>) Collections.unmodifiableMap(products);
	}






	public void setProducts(HashMap<Product, Integer> products) {
		this.products = products;
	}






	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setShipingType(Shiping shipingType) {
		this.shipingType = shipingType;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + (isConfirmed ? 1231 : 1237);
		result = prime * result + (isPaid ? 1231 : 1237);
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result + (int) (orderId ^ (orderId >>> 32));
		result = prime * result + ((payment == null) ? 0 : payment.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((products == null) ? 0 : products.hashCode());
		result = prime * result + ((shipingType == null) ? 0 : shipingType.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + ((userPhoneNumber == null) ? 0 : userPhoneNumber.hashCode());
		result = prime * result + ((zip == null) ? 0 : zip.hashCode());
		return result;
	}






	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (isConfirmed != other.isConfirmed)
			return false;
		if (isPaid != other.isPaid)
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		if (orderId != other.orderId)
			return false;
		if (payment == null) {
			if (other.payment != null)
				return false;
		} else if (!payment.equals(other.payment))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (products == null) {
			if (other.products != null)
				return false;
		} else if (!products.equals(other.products))
			return false;
		if (shipingType != other.shipingType)
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		if (userPhoneNumber == null) {
			if (other.userPhoneNumber != null)
				return false;
		} else if (!userPhoneNumber.equals(other.userPhoneNumber))
			return false;
		if (zip == null) {
			if (other.zip != null)
				return false;
		} else if (!zip.equals(other.zip))
			return false;
		return true;
	}
	
	
	
	
	

	
	
}
