package sdong.common.exception;

public class SdongException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 980983433918588495L;
	
	private String reason;

	public SdongException(Exception cause) {
		super(cause);
		this.reason = cause.getMessage();

	}

	public SdongException(Throwable cause) {
		super(cause);
		this.reason = cause.getMessage();
	}

	public SdongException(String message) {
		super(message);
		this.reason = message;
	}

    public SdongException(String message, Throwable cause) {
        super(message, cause);
		this.reason = message;
    }

	public String getMessage() {
		return reason;
	}
}
