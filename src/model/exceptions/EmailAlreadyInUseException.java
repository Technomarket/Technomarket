package model.exceptions;

	public class EmailAlreadyInUseException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public EmailAlreadyInUseException(){
			super("Admin can not do this action!");
		}
		
		public EmailAlreadyInUseException(Throwable cause){
			super("Admin can not do this action!", cause);
		}

	}
