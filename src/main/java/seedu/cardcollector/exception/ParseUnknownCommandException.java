package seedu.cardcollector.exception;

/**
 * Signals an unknown command error during the parsing of a command.
 */
public class ParseUnknownCommandException extends ParseException {
    /**
     * Constructs a new exception with a specified message.
     *
     * @param message A message which is
     *                the name of unknown command the user has entered.
     *
     */
    public ParseUnknownCommandException(String message) {
        super(message);
    }
}
