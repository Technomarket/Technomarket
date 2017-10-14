package model.exceptions;

public class IlligalAdminActionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IlligalAdminActionException(){
		super("Admin can not do this action!");
	}
	
	public IlligalAdminActionException(Throwable cause){
		super("Admin can not do this action!", cause);
	}

}
