package model.exceptions;

public class InvalidUserDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidUserDataException(){
		super("Invalid user data! Pleace think again when entering it!");
	}
	
	public InvalidUserDataException(Throwable cause){
		super("Invalid user data! Pleace think again when entering it!", cause);
	}

}
