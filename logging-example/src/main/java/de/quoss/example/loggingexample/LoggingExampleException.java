package de.quoss.example.loggingexample;

/**
 * Logging example exception in case of error
 * 
 * @author Clemens Quoß
 *
 */
public class LoggingExampleException extends Exception {

	/**
	 * default serial version id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor with message
	 * 
	 * @param msg
	 *            message
	 */
	public LoggingExampleException(String msg) {
		super(msg);
	}

	/**
	 * Constructor with exception
	 * 
	 * @param e
	 *            exception
	 */
	public LoggingExampleException(Exception e) {
		super(e);
	}

	/**
	 * Constructor with message and exception
	 * 
	 * @param msg
	 *            message
	 * @param e
	 *            exception
	 */
	public LoggingExampleException(String msg, Exception e) {
		super(msg, e);
	}

}
