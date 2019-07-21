package de.quoss.example.httpexample;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
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
class HttpClient2Example {

	/**
	 * class name
	 */
	private static final String CLASS_NAME = HttpClient2Example.class.getName();

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
	private HttpClient2Example() throws HttpExampleException {

		// call super
		super();

		// start message
		LOGGER.log(Level.INFO, "start");

		try {

			// load properties
			properties.load(new FileInputStream(CLASS_NAME.concat(".properties")));

			// open http connection
			HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(getProperty("url", null)).openConnection();
			
			// set request method
			httpURLConnection.setRequestMethod(getProperty("requestMethod", null));
			
			// set do output
			httpURLConnection.setDoOutput(getProperty("doOutput", "false").equals("true"));
			
			// set accept charset
			httpURLConnection.setRequestProperty("Accept-Charset", getProperty("acceptCharset", "UTF-8"));
			
			// set content type
			httpURLConnection.setRequestProperty("Content-Type", getProperty("contentType", "text/plain"));
			
			// open input file
			InputStream inputStream = new FileInputStream("input\\".concat(getProperty("inputfileName", null)));

			// read input file
			byte[] b = new byte[1024];
			int bytesRead;
			int overallBytesRead = 0;
			StringBuilder stringBuilder = new StringBuilder();
			while ((bytesRead = inputStream.read(b)) != -1) {
				stringBuilder.append(new String(b).substring(0, bytesRead));
				overallBytesRead += bytesRead;
			}
			inputStream.close();
			
			// write input to http connection
			httpURLConnection.setRequestProperty("Content-Length", Integer.toString(overallBytesRead));
			OutputStream outputStream = httpURLConnection.getOutputStream();
		outputStream.write(stringBuilder.toString().getBytes());
		outputStream.close();
		int responseCode = httpURLConnection.getResponseCode();
		LOGGER.log(Level.INFO, "[responseCode={0}]", new Object[] { responseCode });
		inputStream = httpURLConnection.getInputStream();
		int contentLength = httpURLConnection.getContentLength();
		bytesRead = inputStream.read(b);
		while ((bytesRead = inputStream.read(b)) != -1) {
			LOGGER.log(Level.INFO, new String(b).substring(0, bytesRead));
		}
		inputStream.close();
		httpURLConnection.disconnect();
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
	 * 
	 */
	public static void main(String[] args) throws Exception {
		// try to call private working constructor
		try {
			new HttpClient2Example();
		} catch (HttpExampleException e) {
			LOGGER.log(Level.SEVERE, "", e);
			System.exit(1);
		}

	}

}
