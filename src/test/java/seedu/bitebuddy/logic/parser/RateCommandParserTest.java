package seedu.bitebuddy.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.bitebuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.bitebuddy.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.logic.commands.RateCommand;
import seedu.bitebuddy.model.foodplace.Rate;
import seedu.bitebuddy.logic.parser.exceptions.ParseException;

public class RateCommandParserTest {
    private final RateCommandParser parser = new RateCommandParser();

    @Test
    public void parse_invalidArgs_throwsNumberFormatException() {
        String userInput = "1 invalidInput";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String userInput = "1 -1";
        assertParseFailure(parser, userInput, Rate.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_nonIntegerRate_throwsParseException() {
        String input = "1 abc"; // non-numeric rate
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(input));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE), exception.getMessage());
    }
}
