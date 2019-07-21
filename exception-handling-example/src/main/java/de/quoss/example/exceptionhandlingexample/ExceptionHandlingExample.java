package de.quoss.example.exceptionhandlingexample;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This example class demonstrates how my opinion of exception handling in java libraries is.
 */
public class ExceptionHandlingExample {

    /**
     * Class name.
     */
    private static final String CLASS_NAME = ExceptionHandlingExample.class.getName();

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getFormatterLogger();

    private String[] tokens = {"tokenA", "tokenB"};

    private String[] urls = {"urlA", "urlB"};

    /**
     * Run example code.
     *
     * @throws ExceptionHandlingExampleException in case of error
     */
    private void run(String token) throws ExceptionHandlingExampleException {
        // build url lookup map
        List<String> tokenList = Arrays.asList(tokens);
        List<String> urlList = Arrays.asList(urls);
        Map<String, String> lookup = new HashMap<>();
        int i = 0;
        for (String thisToken : tokenList) {
            lookup.put(thisToken, urlList.get(i++));
        }
        // lookup url
        if (lookup.containsKey(token)) {
            queryUrl(lookup.get(token));
        } else {
            throw new ExceptionHandlingExampleException(String.format("No url found for token %s.", token));
        }

    }

    private void queryUrl(String url) throws ExceptionHandlingExampleException {
        throw new ExceptionHandlingExampleException("Not implemented yet.");
    }

    private static String usage() {
        return String.format("USAGE: java %s token", CLASS_NAME);
    }

    /**
     * Main method.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            LOGGER.error(usage());
            System.exit(1);
        }
        try {
            new ExceptionHandlingExample().run(args[0]);
        } catch (ExceptionHandlingExampleException e) {
            LOGGER.error(e);
            System.exit(1);
        }
    }

}
