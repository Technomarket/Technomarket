package model.exceptions;

public class InvalidOrderDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidOrderDataException(){
		super("Invalid order data! Pleace think again when entering it!");
	}
	
	public InvalidOrderDataException(Throwable cause){
		super("Invalid order data! Pleace think again when entering it!", cause);
	}

}
