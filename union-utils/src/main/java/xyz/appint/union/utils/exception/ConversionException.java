package xyz.appint.union.utils.exception;

public class ConversionException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------- Constructors

    /**
     * Construct a new exception with the specified message.
     *
     * @param message The message describing this exception
     */
    public ConversionException(String message) {

        super(message);

    }


    /**
     * Construct a new exception with the specified message and root cause.
     *
     * @param message The message describing this exception
     * @param cause   The root cause of this exception
     */
    public ConversionException(String message, Throwable cause) {

        super(message);
        this.cause = cause;

    }


    /**
     * Construct a new exception with the specified root cause.
     *
     * @param cause The root cause of this exception
     */
    public ConversionException(Throwable cause) {

        super(cause.getMessage());
        this.cause = cause;

    }


    // ------------------------------------------------------------- Properties


    /**
     * The root cause of this <code>ConversionException</code>, compatible with
     * JDK 1.4's extensions to <code>java.lang.Throwable</code>.
     */
    protected Throwable cause = null;

    /**
     * Return the root cause of this conversion exception.
     *
     * @return the root cause of this conversion exception
     */
    public Throwable getCause() {
        return (this.cause);
    }


}
