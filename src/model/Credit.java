package model;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;

import model.exceptions.InvalidCreditDataException;

public class Credit {
	
	public enum Bank{UNICREDIT, TBI_BANK};
	private Bank bank;
	private LocalDate date;
	private Product product;
	private String assurance;
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
	
	public Credit(Bank bank, LocalDate date, Product product, String assurance, int userAge, BigDecimal creditSum, int timeOfCredit,
			BigDecimal monthPay, BigDecimal allSum, BigDecimal firstPayment, BigDecimal intest, String info,
			BigDecimal insurancePremium, double yearCostPercentage, double yearInterestRatePercent) throws InvalidCreditDataException {
		this.bank = bank;
		
		this.date = date;
		skipWeekends(date);
		
		this.product = product;
		this.assurance = assurance;
		this.userAge = userAge;
		this.creditSum = creditSum;
		
		if(isTimeOfCreditValid(timeOfCredit)){
			this.timeOfCredit = timeOfCredit;
		}else{
			throw new InvalidCreditDataException();
		}
		this.monthPay = monthPay;
		this.allSum = allSum;
		this.firstPayment = firstPayment;
		this.intest = intest;
		this.info = info;
		this.insurancePremium = insurancePremium;
		this.yearCostPercentage = yearCostPercentage;
		this.yearInterestRatePercent = yearInterestRatePercent;
	}

	private boolean skipWeekends(LocalDate date) {
		if(date.getDayOfWeek() == DayOfWeek.SATURDAY){
			this.date.plusDays(2);
		}else if(date.getDayOfWeek() == DayOfWeek.SUNDAY){
			this.date.plusDays(1);
		}
		return false;
	}

	private boolean isTimeOfCreditValid(int timeOfCredit) {
		if(this.bank == Bank.UNICREDIT && (timeOfCredit < 3 || timeOfCredit > 36)){
			return false;
		}else if(this.bank == Bank.TBI_BANK && (timeOfCredit < 12 || timeOfCredit > 30)){
			return false;
		}else{
			return true;
		}
		
	}
	
	
	
	
	
	
	
	
}