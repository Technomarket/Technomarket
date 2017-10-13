package model;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Product {
	private long productId;
	private String name;
	private BigDecimal price;
	private String productNumber;
	private Credit credit;
	private Category category;
	private ArrayList<Characteristics> characteristics;
	private int worranty;
	private double percentPromo;
	private boolean isNewProduct;

	public Product(String name, String price, String productNumber, Credit credit, Category category, int worranty,
			double percentPromo, boolean isNewProduct) {

		if (correctName(name)) {
			this.name = name;
		} else {
			// throw productDontCorrectDataExceptions();
		}
		BigDecimal big = new BigDecimal(price);
		if (big.compareTo(new BigDecimal("0")) < 0) {
			// throw productDontCorrectDataExceptions();
		} else {
			this.price = big;
		}
		if (credit != null) {
			this.credit = credit;
		}else{
			// throw productDontCorrectDataExceptions();
		}
		if(characteristics != null){
			this.characteristics = characteristics;
		}else{
			// throw productDontCorrectDataExceptions();
		}
		if(worranty >= 0){
			this.worranty = worranty;
		}else{
			// throw productDontCorrectDataExceptions();
		}
		if(percentPromo >= 0){
			this.percentPromo = percentPromo;
		}else{
			// throw productDontCorrectDataExceptions();
		}
		this.isNewProduct = isNewProduct;
	}

	private boolean correctName(String name) {
		if (name != null && !name.isEmpty() && name.length() >= 2) {
			return true;
		}
		return false;
	}
	
	public BigDecimal getPrice() {
		return price;
	}

}
