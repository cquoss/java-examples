package de.quoss.example.serviceloaderexample;

public class ServiceLoaderExampleException extends Exception {

    /**
     * Init with message.
     *
     * @param msg message
     */
    public ServiceLoaderExampleException(final String msg) {
        super(msg);
    }

    /**
     * Init with exception.
     *
     * @param e exception
     */
    public ServiceLoaderExampleException(final Exception e) {
        super(e);
    }

    /**
     * Init with message and exception.
     *
     * @param msg message
     * @param e   exception
     */
    public ServiceLoaderExampleException(final String msg, final Exception e) {
        super(msg, e);
    }

}
