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
    public static final Prefix PREFIX_CUISINE = new Prefix("c/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_NOTE = new Prefix(("no/"));
    public static final Prefix PREFIX_RATE = new Prefix("r/");
    public static final Prefix PREFIX_OPEN = new Prefix("ot/");
    public static final Prefix PREFIX_CLOSE = new Prefix("ct/");

    /* Position (whitespace) definitions */
    public static final int INDEX_NOTE_ENTRY_INDEX = 0;
    public static final int INDEX_NOTE_NOTE_STRING = 1;
    public static final int INDEX_WISHLIST_ENTRY_INDEX = 0;
    public static final int INDEX_BLACKLIST_ENTRY_INDEX = 0;
    public static final int INDEX_COMPARE_FIRST_INDEX = 0;
    public static final int INDEX_COMPARE_SECOND_INDEX = 1;
}
