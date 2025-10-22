package seedu.bitebuddy.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.bitebuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.bitebuddy.logic.parser.CliSyntax.INDEX_COMPARE_FIRST_INDEX;
import static seedu.bitebuddy.logic.parser.CliSyntax.INDEX_COMPARE_SECOND_INDEX;

import seedu.bitebuddy.commons.core.index.Index;
import seedu.bitebuddy.commons.exceptions.IllegalValueException;
import seedu.bitebuddy.logic.commands.CompareCommand;
import seedu.bitebuddy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CompareCommand object
 */
public class CompareCommandParser implements Parser<CompareCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CompareCommand
     * and returns a CompareCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public CompareCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentPositionMapper mapper = new ArgumentPositionMapper(args);

        if (mapper.size() < 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompareCommand.MESSAGE_USAGE));
        }

        Index firstIndex;
        try {
            firstIndex = ParserUtil.parseIndex(mapper.getArgument(INDEX_COMPARE_FIRST_INDEX));
        } catch (IllegalValueException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompareCommand.MESSAGE_USAGE), e);
        }

        Index secondIndex;
        try {
            secondIndex = ParserUtil.parseIndex(mapper.getArgument(INDEX_COMPARE_SECOND_INDEX));
        } catch (IllegalValueException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompareCommand.MESSAGE_USAGE), e);
        }
        return new CompareCommand(firstIndex, secondIndex);
    }
}
