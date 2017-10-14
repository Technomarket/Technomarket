package model.exceptions;

public class InvalidCreditDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidCreditDataException(){
		super("Invalid credit data! Pleace think again when entering it!");
	}
	
	public InvalidCreditDataException(Throwable cause){
		super("Invalid credit data! Pleace think again when entering it!", cause);
	}

}
