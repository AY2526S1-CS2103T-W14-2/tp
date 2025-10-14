package seedu.bitebuddy.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.bitebuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.bitebuddy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.bitebuddy.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.bitebuddy.testutil.TypicalIndexes.INDEX_FIRST_FOODPLACE;
import static seedu.bitebuddy.testutil.TypicalIndexes.INDEX_SECOND_FOODPLACE;
import static seedu.bitebuddy.testutil.TypicalIndexes.INDEX_THIRD_FOODPLACE;

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.commons.core.index.Index;
import seedu.bitebuddy.logic.commands.NoteCommand;
import seedu.bitebuddy.logic.parser.exceptions.ParseException;
import seedu.bitebuddy.model.foodplace.Note;

public class NoteCommandParserTest {
    private static final String VALID_NOTE = "Some note.";
    private static final String INVALID_CHAR_NOTE = "Nice place! \u0081";
    private static final String INVALID_LONG_NOTE = "a".repeat(150);
    private NoteCommandParser parser = new NoteCommandParser();

    @Test
    public void parse_indexSpecified_success() {
        // have note
        Index targetIndex = INDEX_FIRST_FOODPLACE;
        String userInput = targetIndex.getOneBased() + " " + VALID_NOTE;
        NoteCommand expectedCommand = new NoteCommand(INDEX_FIRST_FOODPLACE, new Note(VALID_NOTE));
        assertParseSuccess(parser, userInput, expectedCommand);

        // no note
        userInput = targetIndex.getOneBased() + " ";
        expectedCommand = new NoteCommand(INDEX_FIRST_FOODPLACE, new Note(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidIndex_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "one " + VALID_NOTE, expectedMessage);
        assertParseFailure(parser, "-1 " + VALID_NOTE, expectedMessage);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, NoteCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, NoteCommand.COMMAND_WORD + " " + VALID_NOTE, expectedMessage);
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        NoteCommandParser parser = new NoteCommandParser();
        assertThrows(ParseException.class, () -> parser.parse(""),
                "Expected ParseException when no arguments are provided");
        assertThrows(ParseException.class, () -> parser.parse("   "),
                "Expected ParseException when only whitespace is provided");
    }

    @Test
    public void parse_validNote_success() {
        Index targetIndex = INDEX_FIRST_FOODPLACE;
        String userInput = targetIndex.getOneBased() + " " + VALID_NOTE;
        NoteCommand expectedCommand = new NoteCommand(targetIndex, new Note(VALID_NOTE));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noteExceedingMaxLength_failure() {
        Index targetIndex = INDEX_SECOND_FOODPLACE;
        String userInput = targetIndex.getOneBased() + " " + INVALID_LONG_NOTE;
        String expectedMessage = Note.MESSAGE_CONSTRAINTS;

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_noteWithInvalidCharacters_failure() {
        Index targetIndex = INDEX_THIRD_FOODPLACE;
        String userInput = targetIndex.getOneBased() + " " + INVALID_CHAR_NOTE;
        String expectedMessage = Note.MESSAGE_CONSTRAINTS;

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_emptyNote_success() {
        Index targetIndex = INDEX_FIRST_FOODPLACE;
        String userInput = targetIndex.getOneBased() + " ";
        NoteCommand expectedCommand = new NoteCommand(targetIndex, new Note(""));

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
