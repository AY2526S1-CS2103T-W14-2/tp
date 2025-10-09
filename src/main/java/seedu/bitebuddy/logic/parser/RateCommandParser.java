package seedu.bitebuddy.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.bitebuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_RATE;

import seedu.bitebuddy.commons.core.index.Index;
import seedu.bitebuddy.commons.exceptions.IllegalValueException;
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
    public RateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_RATE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE), ive);
        }

        String rate = argMultimap.getValue(PREFIX_RATE).orElse("");

        return new RateCommand(index, new Rate(Integer.valueOf(rate)));
    }
}