package de.quoss.example.basicexample;

import java.util.logging.Logger;

/**
 * basic java example
 * 
 * @author Clemens Quoß
 *
 */
public class BasicExample {

	/**
	 * main method
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		new BasicExample().run();
	}

	/**
	 * run process logic 
	 */
	public void run() {
		Logger.getAnonymousLogger().info("Hello, World!");
	}

}
