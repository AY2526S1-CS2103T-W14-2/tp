package seedu.bitebuddy.logic.parser;

import static seedu.bitebuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.bitebuddy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.bitebuddy.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.bitebuddy.testutil.TypicalIndexes.INDEX_FIRST_FOODPLACE;
import static seedu.bitebuddy.testutil.TypicalIndexes.INDEX_SECOND_FOODPLACE;
import static seedu.bitebuddy.testutil.TypicalIndexes.INDEX_THIRD_FOODPLACE;

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.logic.commands.CompareCommand;

public class CompareCommandParserTest {

    private final CompareCommandParser parser = new CompareCommandParser();

    @Test
    public void parse_validIndexes_success() {
        String userInput = INDEX_FIRST_FOODPLACE.getOneBased() + " " + INDEX_SECOND_FOODPLACE.getOneBased();
        CompareCommand expectedCommand = new CompareCommand(INDEX_FIRST_FOODPLACE, INDEX_SECOND_FOODPLACE);
        assertParseSuccess(parser, userInput, expectedCommand);

        userInput = INDEX_SECOND_FOODPLACE.getOneBased() + " " + INDEX_THIRD_FOODPLACE.getOneBased();
        expectedCommand = new CompareCommand(INDEX_SECOND_FOODPLACE, INDEX_THIRD_FOODPLACE);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidIndex_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompareCommand.MESSAGE_USAGE);

        assertParseFailure(parser, "one two", expectedMessage);
        assertParseFailure(parser, "-1 2", expectedMessage);
        assertParseFailure(parser, "1 -2", expectedMessage);
    }

    @Test
    public void parse_missingIndexes_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompareCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, CompareCommand.COMMAND_WORD, expectedMessage);

        // only one index
        assertParseFailure(parser, CompareCommand.COMMAND_WORD + " " + INDEX_FIRST_FOODPLACE.getOneBased(), expectedMessage);
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompareCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "   ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompareCommand.MESSAGE_USAGE));
    }
}