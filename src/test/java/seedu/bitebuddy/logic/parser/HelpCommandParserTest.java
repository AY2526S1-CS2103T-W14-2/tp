package seedu.bitebuddy.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.logic.commands.CommandRegistry;
import seedu.bitebuddy.logic.commands.HelpCommand;
import seedu.bitebuddy.logic.parser.exceptions.ParseException;
import seedu.bitebuddy.model.ModelManager;

public class HelpCommandParserTest {
    private final HelpCommandParser parser = new HelpCommandParser();

    @Test
    public void parse_noArgs_success() throws Exception {
        HelpCommand cmd = parser.parse("");
        assertTrue(cmd instanceof HelpCommand);
        var result = cmd.execute(new ModelManager());
        assertEquals(HelpCommand.SHOWING_HELP_MESSAGE, result.getFeedbackToUser());
        assertTrue(result.isShowHelp());

        // whitespace only
        cmd = parser.parse("   ");
        result = cmd.execute(new ModelManager());
        assertEquals(HelpCommand.SHOWING_HELP_MESSAGE, result.getFeedbackToUser());
    }

    @Test
    public void parse_knownCommandWord_success() throws Exception {
        String input = "find";
        HelpCommand cmd = parser.parse(input);
        var result = cmd.execute(new ModelManager());
        Optional<String> usage = CommandRegistry.getUsage(input);
        assertTrue(usage.isPresent());
        assertEquals(usage.get(), result.getFeedbackToUser());
    }

    @Test
    public void parse_unknownCommand_throwsParseException() {
        try {
            parser.parse("unknowncommand");
            throw new AssertionError("Expected ParseException to be thrown");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }
}
