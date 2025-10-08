package seedu.bitebuddy.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.bitebuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.bitebuddy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.bitebuddy.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.bitebuddy.testutil.TypicalIndexes.INDEX_FIRST_FOODPLACE;

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.commons.core.index.Index;
import seedu.bitebuddy.logic.commands.NoteCommand;
import seedu.bitebuddy.logic.parser.exceptions.ParseException;
import seedu.bitebuddy.model.foodplace.Note;

public class NoteCommandParserTest {
    private NoteCommandParser parser = new NoteCommandParser();
    private final String nonEmptyNote = "Some note.";

    @Test
    public void parse_indexSpecified_success() {
        // have note
        Index targetIndex = INDEX_FIRST_FOODPLACE;
        String userInput = targetIndex.getOneBased() + " " + nonEmptyNote;
        NoteCommand expectedCommand = new NoteCommand(INDEX_FIRST_FOODPLACE, new Note(nonEmptyNote));
        assertParseSuccess(parser, userInput, expectedCommand);

        // no note
        userInput = targetIndex.getOneBased() + " ";
        expectedCommand = new NoteCommand(INDEX_FIRST_FOODPLACE, new Note(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, NoteCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, NoteCommand.COMMAND_WORD + " " + nonEmptyNote, expectedMessage);
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        NoteCommandParser parser = new NoteCommandParser();
        assertThrows(ParseException.class, () -> parser.parse(""),
                "Expected ParseException when no arguments are provided");
        assertThrows(ParseException.class, () -> parser.parse("   "),
                "Expected ParseException when only whitespace is provided");
    }
}
