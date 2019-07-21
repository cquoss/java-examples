package de.quoss.example.propertiesexample;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Java example using properties and logging (SLF4J and logback-classic)
 * 
 * @author clemens
 *
 */
public class PropertiesExample {

	/**
	 * class name
	 */
	private static final String CLASS_NAME = PropertiesExample.class.getName();

	/**
	 * logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesExample.class);

	/**
	 * properties
	 */
	private Properties properties = new Properties();

	/**
	 * main method
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		try {
			new PropertiesExample().run(args);
		} catch (PropertiesExampleException e) {
			LOGGER.error("", e);
			System.exit(1);
		}
	}

	/**
	 * run process logic
	 */
	public void run(String[] args) throws PropertiesExampleException {
		checkCommandLineArguments(args);
		loadProperties();
		LOGGER.info("{}", getProperty("message", "Hello,World!"));
	}

	/**
	 * get property from system properties, if not found from property file, if
	 * still not found use defaultValue. If defaultValue is null, throw 'mandatory
	 * property missing' exception
	 * 
	 * @param key
	 *            property short key, is expanded to full key
	 * @param defaultValue
	 *            default value or null, if none exist (property is mandatory)
	 * @return String value of property
	 * @throws PropertiesExampleException
	 *             in case of error or if mandatory property not provided
	 */
	private String getProperty(String key, String defaultValue) throws PropertiesExampleException {
		String fullKey = CLASS_NAME.concat(".").concat(key);
		String value = System.getProperty(fullKey);
		if (value == null) {
			value = properties.getProperty(fullKey);
			if (value == null) {
				if (defaultValue == null) {
					throw new PropertiesExampleException(
							String.format("Mandatory property value not found: %s", fullKey));
				} else {
					return defaultValue;
				}
			}
		}
		return value;
	}

	/**
	 * load property file from class path, throws NPE if file not found
	 * 
	 * @throws PropertiesExampleException
	 *             in case of error
	 */
	private void loadProperties() throws PropertiesExampleException {
		try {
			properties.load(
					PropertiesExample.class.getClassLoader().getResourceAsStream(CLASS_NAME.concat(".properties")));
		} catch (IOException e) {
			throw new PropertiesExampleException(e);
		}
	}

	/**
	 * check command line arguments
	 * 
	 * @param args
	 *            command line arguments
	 * @throws PropertiesExampleException
	 *             in case of error
	 */
	private void checkCommandLineArguments(String[] args) throws PropertiesExampleException {
		if (args.length != 0) {
			throw new PropertiesExampleException(String.format("USAGE: java %s", CLASS_NAME));
		}

	}

}
