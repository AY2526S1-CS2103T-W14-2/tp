package seedu.bitebuddy.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.bitebuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.bitebuddy.logic.parser.CliSyntax.INDEX_WISHLIST_ENTRY_INDEX;

import seedu.bitebuddy.commons.core.index.Index;
import seedu.bitebuddy.commons.exceptions.IllegalValueException;
import seedu.bitebuddy.logic.commands.WishlistCommand;
import seedu.bitebuddy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code WishlistCommand} object
 */
public class WishlistCommandParser implements Parser<WishlistCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code WishlistCommand}
     * and returns a {@code WishlistCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public WishlistCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentPositionMapper mapper = new ArgumentPositionMapper(args);
        Index index;

        if (mapper.size() == 0) { // No index specified --> display wishlist (no parsing required)
            return new WishlistCommand(null);
        }

        if (mapper.size() == 1) { // Index specified in command --> parse index
            try {
                index = ParserUtil.parseIndex(mapper.getArgument(INDEX_WISHLIST_ENTRY_INDEX));
            } catch (IllegalValueException e) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        WishlistCommand.MESSAGE_USAGE), e);
            }
            return new WishlistCommand(index);
        }

        // Else --> Invalid command
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, WishlistCommand.MESSAGE_USAGE));
    }
}
