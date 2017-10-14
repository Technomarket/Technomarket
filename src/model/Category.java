package model;

import model.exceptions.InvalidCategoryDataException;

public class Category {
	
	private Category category;
	private String name;
	
	public void setCategory(String name, Category category) throws InvalidCategoryDataException {
		if(category != null){
		   this.category = category;
		}else{
			throw new InvalidCategoryDataException();
		}
		if(name != null && !name.isEmpty()){
			this.name = name;
		}else{
			throw new InvalidCategoryDataException();
		}
	}

	public String getName() {
		return name;
	}
	
	

}
