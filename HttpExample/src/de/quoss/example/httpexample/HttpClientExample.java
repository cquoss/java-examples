package de.quoss.example.httpexample;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * <p>
 * example how to send a http request and receive the response
 * </p>
 *
 * <p>
 * TODO use properties to customize client behaviour
 * </p>
 * <p>
 * TODO integrate client 2 example
 * </p>
 * 
 * @author Clemens Quoss
 *
 */
class HttpClientExample {

	/**
	 * classname
	 */
	private static final String CLASS_NAME = HttpClientExample.class.getName();

	/**
	 * logger
	 */
	private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

	/**
	 * properties
	 */
	private Properties properties = new Properties();

	/**
	 * private working constructor
	 * 
	 * @throws HttpExampleException
	 *             in case of error
	 */
	private HttpClientExample() throws HttpExampleException {

		// call super
		super();

		// start message
		LOGGER.log(Level.INFO, "start");

		// try to load properties
		try {
			properties.load(new FileInputStream(CLASS_NAME.concat(".properties")));
		} catch (IOException e) {
			throw new HttpExampleException(e);
		}

		// try to open http connection
		HttpURLConnection httpURLConnection = null;
		try {
			httpURLConnection = (HttpURLConnection) new URL(getProperty("url", null)).openConnection();
		} catch (IOException e) {
			throw new HttpExampleException(e);
		}

		// try to set GET request method
		try {
			httpURLConnection.setRequestMethod(getProperty("requestMethod", null));
		} catch (ProtocolException e) {
			throw new HttpExampleException(e);
		}

		// try to connect
		try {
			httpURLConnection.connect();
		} catch (IOException e) {
			throw new HttpExampleException(e);
		}

		// try to log response code
		try {
			int responseCode = httpURLConnection.getResponseCode();
			LOGGER.log(Level.INFO, "Response code: {0}", new Object[] { responseCode });
		} catch (IOException e) {
			throw new HttpExampleException(e);
		}

		// try to log response message
		try {
			String responseMessage = httpURLConnection.getResponseMessage();
			LOGGER.log(Level.INFO, "Response message: {0}", new Object[] { responseMessage });
		} catch (IOException e) {
			throw new HttpExampleException(e);
		}

		// end message
		LOGGER.log(Level.INFO, "end");

	}

	/**
	 * get property value
	 * 
	 * @param key
	 *            short key
	 * @param defaultValue
	 *            default value
	 * @return String property value
	 * @throws HttpExampleException
	 *             in case of missing mandatory property
	 */
	private String getProperty(String key, String defaultValue) throws HttpExampleException {

		// build full key
		String fullKey = CLASS_NAME.concat(".").concat(key);

		// get property value
		String value = properties.getProperty(fullKey, defaultValue);

		// check if mandatory else return value
		if (value == null) {
			throw new HttpExampleException(String.format("Mandatory property missing [fullKey=%s]", fullKey));
		} else {
			return value;
		}

	}

	/**
	 * main method
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {

		// try to call private working constructor
		try {
			new HttpClientExample();
		} catch (HttpExampleException e) {
			LOGGER.log(Level.SEVERE, "", e);
			System.exit(1);
		}

	}

}
