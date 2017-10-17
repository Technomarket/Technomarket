package model.exceptions;

public class IlligalUserActionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IlligalUserActionException(){
		super("User can not do this action!");
	}
	
	public IlligalUserActionException(Throwable cause){
		super("User can not do this action!", cause);
	}

}
