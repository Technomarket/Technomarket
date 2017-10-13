package model;

public class Characteristics {
	
	private long characteristicsId;
	private String name;
	private String typeCharacteristics;
	private Product product;
	
	public Characteristics(String name, String typeCharacteristics, Product product) {
		if(name != null && !name.isEmpty()){
		   this.name = name;
		}else{
			//throw new InvalidCharacteristicsDataException();
		}
		if(typeCharacteristics != null && !typeCharacteristics.isEmpty()){
		this.typeCharacteristics = typeCharacteristics;
		}else{
			//throw new InvalidCharacteristicsDataException();
		}
		if(product != null){
			this.product = product;
		}else{
			//throw new InvalidCharacteristicsDataException();
		}
		
	}

	public String getName() {
		return name;
	}

	public String getTypeCharacteristics() {
		return typeCharacteristics;
	}

	public long getCharacteristicsId() {
		return characteristicsId;
	}

	public void setCharacteristicsId(long characteristicsId) {
		this.characteristicsId = characteristicsId;
	}
	
	
	
	

}
