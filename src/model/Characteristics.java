package model;

public class Characteristics {
	private int idNumber;
	private String name;
	private String typeCharacteristics;
	private Product product;
	public Characteristics(String name, String typeCharacteristics, Product product) {
		if(name != null && !name.isEmpty()){
		   this.name = name;
		}else{
			//throw CharacteristicsExceptions();
		}
		if(typeCharacteristics != null && !typeCharacteristics.isEmpty()){
		this.typeCharacteristics = typeCharacteristics;
		}else{
			//throw CharacteristicsExceptions();
		}
		if(product != null){
			this.product = product;
		}else{
			//throw CharacteristicsExceptions();
		}
		
	}
	
	
	
	

}
