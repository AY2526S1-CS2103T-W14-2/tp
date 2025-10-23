package seedu.bitebuddy.logic.parser;

import static seedu.bitebuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.bitebuddy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.bitebuddy.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.bitebuddy.testutil.TypicalIndexes.INDEX_FIRST_FOODPLACE;

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.logic.commands.UnpinCommand;

public class UnpinCommandParserTest {

    private UnpinCommandParser parser = new UnpinCommandParser();

    @Test
    public void parse_validArgs_returnsUnpinCommand() {
        assertParseSuccess(parser, "1", new UnpinCommand(INDEX_FIRST_FOODPLACE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnpinCommand.MESSAGE_USAGE));
    }
}
