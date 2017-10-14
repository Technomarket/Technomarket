package model.exceptions;

public class InvalidCharacteristicsDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidCharacteristicsDataException(){
		super("Invalid characteristics data! Pleace think again when entering it!");
	}
	
	public InvalidCharacteristicsDataException(Throwable cause){
		super("Invalid characteristics data! Pleace think again when entering it!", cause);
	}

}
