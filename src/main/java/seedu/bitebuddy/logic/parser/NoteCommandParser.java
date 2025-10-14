package seedu.bitebuddy.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.bitebuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.bitebuddy.logic.parser.CliSyntax.INDEX_NOTE_ENTRY_INDEX;
import static seedu.bitebuddy.logic.parser.CliSyntax.INDEX_NOTE_NOTE_STRING;

import seedu.bitebuddy.commons.core.index.Index;
import seedu.bitebuddy.commons.exceptions.IllegalValueException;
import seedu.bitebuddy.logic.commands.NoteCommand;
import seedu.bitebuddy.logic.parser.exceptions.ParseException;
import seedu.bitebuddy.model.foodplace.Note;

/**
 * Parses input arguments and creates a new {@code NoteCommand} object
 */
public class NoteCommandParser implements Parser<NoteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code NoteCommand}
     * and returns a {@code NoteCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public NoteCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentPositionMapper mapper = new ArgumentPositionMapper(args);

        if (mapper.size() < 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(mapper.getArgument(INDEX_NOTE_ENTRY_INDEX));
        } catch (IllegalValueException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE), e);
        }

        Note note;
        String noteText = mapper.getRemainingArguments(INDEX_NOTE_NOTE_STRING);
        try {
            note = new Note(noteText);
        } catch (IllegalArgumentException e) {
            throw new ParseException(Note.MESSAGE_CONSTRAINTS);
        }
        return new NoteCommand(index, note);
    }
}
