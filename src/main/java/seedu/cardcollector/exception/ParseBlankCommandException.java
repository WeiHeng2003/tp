package seedu.cardcollector.exception;

/**
 * Signals a blank command error during the parsing of a command.
 */
public class ParseBlankCommandException extends ParseException {
    /**
     * Constructs a new exception.
     */
    public ParseBlankCommandException() {
        super("Command cannot be blank");
    }
}
