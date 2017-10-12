package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;

public class Order {
	
	private long orderId;
	private HashMap<Product, Integer> products;
	private BigDecimal price;
	private LocalDateTime time;
	private String address;
	private BigDecimal payment;
	private boolean isConfirmed;
	private String notes;
	private String shipingType;
	private boolean isPaid;
	
}
