package de.quoss.example.propertiesexample;

/**
 * Java example using properties and logging (SLF4J with logback-classic)
 * 
 * @author Clemens Quoß
 *
 */
public class PropertiesExampleException extends Exception {

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
	public PropertiesExampleException(String msg) {
		super(msg);
	}

	/**
	 * Constructor with exception
	 * 
	 * @param e
	 *            exception
	 */
	public PropertiesExampleException(Exception e) {
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
	public PropertiesExampleException(String msg, Exception e) {
		super(e);
	}
	
}
