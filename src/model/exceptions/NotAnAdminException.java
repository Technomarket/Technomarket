package model.exceptions;

public class NotAnAdminException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotAnAdminException(){
		super("User is not an admin! The action is allowed only for admins!");
	}
	
	public NotAnAdminException(Throwable cause){
		super("User is not an admin! The action is allowed only for admins!", cause);
	}

}
