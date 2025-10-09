package seedu.bitebuddy.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_RATE = new Prefix("r/");

    /* Position (whitespace) definitions */
    public static final int INDEX_NOTE_ENTRY_INDEX = 0;
    public static final int INDEX_NOTE_NOTE_STRING = 1;
}
