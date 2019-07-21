package de.quoss.example.loggingexample;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * example using the SLF4J logging facade with logback-classic implementation
 * 
 * @author Clemens Quoß
 *
 */
public class LoggingExample {

	/**
	 * class name
	 */
	private static final String CLASS_NAME = LoggingExample.class.getName();

	/**
	 * logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingExample.class);

	/**
	 * main method
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		try {
			new LoggingExample().run(args);
		} catch (LoggingExampleException e) {
			LOGGER.error("", e);
			System.exit(1);
		}
	}

	/**
	 * Run program logic
	 * 
	 * @param args
	 *            command line arguments
	 * @throws LoggingExampleException
	 *             in case of error
	 */
	public void run(String[] args) throws LoggingExampleException {
		checkCommandLineArguments(args);
	}

	/**
	 * check command line arguments
	 * 
	 * @param args
	 *            command line arguments
	 */
	private void checkCommandLineArguments(String[] args) throws LoggingExampleException {
		if (args.length != 0) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Unknown command line options: {}", Arrays.toString(args));
			}
			throw new LoggingExampleException(String.format("USAGE: java %s", CLASS_NAME));
		}
	}

}
