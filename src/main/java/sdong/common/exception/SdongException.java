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

    @Override
    public String getMessage() {
        return reason;
    }

    /**
     * Get exception throwable position info
     * 
     * @return class.method:lineNumber
     */
    public String getErrorPosition() {
        StackTraceElement[] stElements = getStackTrace();
        if (stElements == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        StackTraceElement st = stElements[0];
        sb.append(st.getClassName()).append(".").append(st.getMethodName()).append(":")
                .append(st.getLineNumber());
        return sb.toString();
    }
}
