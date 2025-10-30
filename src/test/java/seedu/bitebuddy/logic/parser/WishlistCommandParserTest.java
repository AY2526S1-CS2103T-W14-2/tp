package seedu.bitebuddy.logic.parser;

import static seedu.bitebuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.bitebuddy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.bitebuddy.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.bitebuddy.testutil.TypicalIndexes.INDEX_FIRST_FOODPLACE;

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.logic.commands.WishlistCommand;

public class WishlistCommandParserTest {

    private final WishlistCommandParser parser = new WishlistCommandParser();

    @Test
    public void parse_indexSpecified_success() {
        // with leading/trailing whitespace
        String userInput = "  " + INDEX_FIRST_FOODPLACE.getOneBased() + "  ";
        WishlistCommand expectedCommand = new WishlistCommand(INDEX_FIRST_FOODPLACE);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noIndex_success() {
        // no arguments
        String userInput = "";
        WishlistCommand expectedCommand = new WishlistCommand(null);
        assertParseSuccess(parser, userInput, expectedCommand);

        // only whitespace
        String userInputWhitespace = "   ";
        assertParseSuccess(parser, userInputWhitespace, expectedCommand);
    }

    @Test
    public void parse_invalidIndex_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, WishlistCommand.MESSAGE_USAGE);

        // non-numeric index
        assertParseFailure(parser, "one", expectedMessage);

        // zero index
        assertParseFailure(parser, "0", expectedMessage);

        // negative index
        assertParseFailure(parser, "-5", expectedMessage);
    }

    @Test
    public void parse_tooManyArgs_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, WishlistCommand.MESSAGE_USAGE);

        // index and extra text
        assertParseFailure(parser, "1 some random text", expectedMessage);

        // multiple indexes
        assertParseFailure(parser, "1 2", expectedMessage);
    }
}
