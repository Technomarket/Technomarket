package model;

import model.exceptions.InvalidCategoryDataException;

public class Category {
	
	private Category category;
	private String name;
	
	
	public Category(String name) throws InvalidCategoryDataException{
		if(name != null && !name.isEmpty()){
			this.name = name;
		}else{
			throw new InvalidCategoryDataException();
		}
	}
	
	public Category(String name, Category category) throws InvalidCategoryDataException {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Category other = (Category) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	

}
