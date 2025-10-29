package seedu.bitebuddy.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.bitebuddy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.bitebuddy.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.logic.commands.CommandRegistry;
import seedu.bitebuddy.logic.commands.HelpCommand;
import seedu.bitebuddy.model.ModelManager;

public class HelpCommandParserTest {
    private final HelpCommandParser parser = new HelpCommandParser();

    @Test
    public void parse_noArgs_success() throws Exception {
        assertParseSuccess(parser, "", new HelpCommand());
    }

    @Test
    public void parse_knownCommandWord_success() throws Exception {
        assertParseSuccess(parser, "find", new HelpCommand(CommandRegistry.getUsage("find").get()));
    }

    @Test
    public void parse_unknownCommand_throwsParseException() {
        assertParseFailure(parser, "unknowncommand", MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void parse_nullArgs_returnsGeneralHelp() throws Exception {
        HelpCommand cmd = parser.parse(null);
        var result = cmd.execute(new ModelManager());
        assertEquals(HelpCommand.SHOWING_HELP_MESSAGE, result.getFeedbackToUser());
        assertTrue(result.isShowHelp());
    }

    @Test
    public void parse_blankArgs_returnsGeneralHelp() throws Exception {
        HelpCommand cmd = parser.parse("   ");
        var result = cmd.execute(new ModelManager());
        assertEquals(HelpCommand.SHOWING_HELP_MESSAGE, result.getFeedbackToUser());
        assertTrue(result.isShowHelp());
    }
}
