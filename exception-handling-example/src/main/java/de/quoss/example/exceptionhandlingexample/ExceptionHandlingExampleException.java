package de.quoss.example.exceptionhandlingexample;

public class ExceptionHandlingExampleException extends Exception {

    /**
     * Init with message.
     *
     * @param msg message
     */
    public ExceptionHandlingExampleException(String msg) {
        super(msg);
    }

    /**
     * Init with exception.
     *
     * @param e exception
     */
    public ExceptionHandlingExampleException(Exception e) {
        super(e);
    }

    /**
     * Init with message and exception.
     *
     * @param msg message
     * @param e exception
     */
    public ExceptionHandlingExampleException(String msg, Exception e) {
        super(msg, e);
    }

}
