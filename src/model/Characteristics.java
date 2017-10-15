package model;

import model.exceptions.InvalidCharacteristicsDataException;

public class Characteristics {
	
	private long characteristicsId;
	private String name;
	private String typeCharacteristics;
	
	public Characteristics(String name, String typeCharacteristics) throws InvalidCharacteristicsDataException {
		if(name != null && !name.isEmpty()){
		   this.name = name;
		}else{
			throw new InvalidCharacteristicsDataException();
		}
		if(typeCharacteristics != null && !typeCharacteristics.isEmpty()){
		this.typeCharacteristics = typeCharacteristics;
		}else{
			throw new InvalidCharacteristicsDataException();
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

	public void setName(String name) {
		this.name = name;
	}

	public void setTypeCharacteristics(String typeCharacteristics) {
		this.typeCharacteristics = typeCharacteristics;
	}
	
	
	
	

}
