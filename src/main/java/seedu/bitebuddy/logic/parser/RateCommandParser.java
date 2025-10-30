package seedu.bitebuddy.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.bitebuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.bitebuddy.logic.parser.CliSyntax.INDEX_RATING_ENTRY_INDEX;
import static seedu.bitebuddy.logic.parser.CliSyntax.INDEX_RATING_RATING_VALUE;

import seedu.bitebuddy.commons.core.index.Index;
import seedu.bitebuddy.logic.commands.RateCommand;
import seedu.bitebuddy.logic.parser.exceptions.ParseException;
import seedu.bitebuddy.model.foodplace.Rate;

/**
 * Parses input arguments and creates a new {@code RateCommand} object
 */
public class RateCommandParser implements Parser<RateCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code RemarkCommand}
     * and returns a {@code RemarkCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public RateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentPositionMapper stringArgs = new ArgumentPositionMapper(args);

        Index index;
        Integer rating;
        try {
            index = ParserUtil.parseIndex(stringArgs.getArgument(INDEX_RATING_ENTRY_INDEX));
            rating = Integer.valueOf(stringArgs.getArgument(INDEX_RATING_RATING_VALUE));
        } catch (NumberFormatException nfe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE), nfe);
        }

        Rate rate;
        try {
            rate = new Rate(rating);
        } catch (IllegalArgumentException e) {
            throw new ParseException(Rate.MESSAGE_CONSTRAINTS);
        }

        return new RateCommand(index, rate);
    }
}
