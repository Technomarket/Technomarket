package model.exceptions;


public class InvalidStoreDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidStoreDataException(){
		super("Invalid store data! Pleace think again when entering it!");
	}
	
	public InvalidStoreDataException(Throwable cause){
		super("Invalid store data! Pleace think again when entering it!", cause);
	}

}
