package model.exceptions;

public class InvalidCategoryDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidCategoryDataException(){
		super("Invalid category data! Pleace think again when entering it!");
	}
	
	public InvalidCategoryDataException(Throwable cause){
		super("Invalid category data! Pleace think again when entering it!", cause);
	}

}
