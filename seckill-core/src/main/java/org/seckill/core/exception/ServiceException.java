package org.seckill.core.exception;

public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = -8106894796482859351L;
	
	public ServiceException(String message) {
		super(message);
	}
	
	@Override
	public String getMessage() {
		return super.getMessage();
	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}

}
