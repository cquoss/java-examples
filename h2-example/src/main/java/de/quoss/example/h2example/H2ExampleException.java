package de.quoss.example.h2example;

import java.io.Serial;

public class H2ExampleException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public H2ExampleException(final String s) {
        super(s);
    }

    public H2ExampleException(final Throwable t) {
        super(t);
    }

    public H2ExampleException(final String s, final Throwable t) {
        super(s, t);
    }

}
