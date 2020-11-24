package de.quoss.example.jsonexample;

/**
 * exception in case of error
 * 
 * @author Clemens Quoss
 *
 */
public class JsonExampleException extends Exception {

	/**
	 * default serial version id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * constructor with message
	 * 
	 * @param msg
	 *            message
	 */
	public JsonExampleException(String msg) {
		super(msg);
	}

	/**
	 * constructor with exception
	 * 
	 * @param msg
	 *            message
	 */
	public JsonExampleException(Exception e) {
		super(e);
	}

	/**
	 * constructor with message and exception
	 * 
	 * @param msg
	 *            message
	 * @param e
	 *            exception
	 */
	public JsonExampleException(String msg, Exception e) {
		super(msg, e);
	}

}
