package model.exceptions;

public class InvalidProductDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidProductDataException(){
		super("Invalid product data! Pleace think again when entering it!");
	}
	
	public InvalidProductDataException(Throwable cause){
		super("Invalid product data! Pleace think again when entering it!", cause);
	}

}
