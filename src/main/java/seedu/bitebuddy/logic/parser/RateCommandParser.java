package seedu.bitebuddy.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.bitebuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.bitebuddy.commons.core.index.Index;
import seedu.bitebuddy.logic.commands.RateCommand;
import seedu.bitebuddy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code RateCommand} object
 */
public class RateCommandParser implements Parser<RateCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code RemarkCommand}
     * and returns a {@code RemarkCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentPositionMapper stringArgs = new ArgumentPositionMapper(args);

        Index index;
        Integer rate;
        try {
            index = ParserUtil.parseIndex(stringArgs.getArgument(0));
            rate = Integer.valueOf(stringArgs.getArgument(1));
        } catch (NumberFormatException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE), ive);
        }


        return new RateCommand(index, rate);
    }
}
