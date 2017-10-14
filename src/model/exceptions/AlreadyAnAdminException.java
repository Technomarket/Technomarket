package model.exceptions;

public class AlreadyAnAdminException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AlreadyAnAdminException(){
		super("User is already an admin!");
	}
	
	public AlreadyAnAdminException(Throwable cause){
		super("User is already an admin!", cause);
	}

}
