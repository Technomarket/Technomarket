package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Credit {
	public enum Bank{UniCredit, TBIBank};
	private LocalDate date;
	private BigDecimal priceOfProduct;
	private String insurance;
	private int userAge;
	private BigDecimal creditSum;
	private int timeOfCredit;
	private BigDecimal monthPay;
	private BigDecimal allSum;
	private BigDecimal firstPayment;
	private BigDecimal intest;
	private String info;
	private BigDecimal insurancePremium;
	private double yearCostPercentage;
	private double yearInterestRatePercent;
}