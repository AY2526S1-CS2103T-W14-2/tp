package seedu.bitebuddy.logic.parser;

import static seedu.bitebuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_CLOSE;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_CUISINE;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_OPEN;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_RATE;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.bitebuddy.logic.commands.AddCommand;
import seedu.bitebuddy.logic.parser.exceptions.ParseException;
import seedu.bitebuddy.model.foodplace.Address;
import seedu.bitebuddy.model.foodplace.Blacklist;
import seedu.bitebuddy.model.foodplace.Cuisine;
import seedu.bitebuddy.model.foodplace.Email;
import seedu.bitebuddy.model.foodplace.Foodplace;
import seedu.bitebuddy.model.foodplace.Name;
import seedu.bitebuddy.model.foodplace.Note;
import seedu.bitebuddy.model.foodplace.Phone;
import seedu.bitebuddy.model.foodplace.Rate;
import seedu.bitebuddy.model.foodplace.Timing;
import seedu.bitebuddy.model.foodplace.Wishlist;
import seedu.bitebuddy.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                    PREFIX_CUISINE, PREFIX_TAG, PREFIX_NOTE, PREFIX_RATE, PREFIX_OPEN, PREFIX_CLOSE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_CUISINE, PREFIX_NOTE, PREFIX_RATE);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).orElse(""));
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).orElse(""));
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Cuisine cuisine = ParserUtil.parseCuisine(argMultimap.getValue(PREFIX_CUISINE).orElse(""));

        // parse timing: require both open and close
        if (!ParserUtil.areNoneOrBothPrefixesPresent(argMultimap, PREFIX_OPEN, PREFIX_CLOSE)) {
            throw new ParseException(ParserUtil.MESSAGE_BOTH_TIMES_REQUIRED);
        }
        String open = argMultimap.getValue(PREFIX_OPEN).orElse("");
        String close = argMultimap.getValue(PREFIX_CLOSE).orElse("");
        Timing timing = ParserUtil.parseTiming(open, close);

        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Note note = ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).orElse(""));
        Rate rate = ParserUtil.parseRatings(argMultimap.getAllValues(PREFIX_RATE));
        Wishlist wishlist = new Wishlist(false);
        Blacklist blacklist = new Blacklist(false);

        Foodplace foodplace = new Foodplace(name, phone, email, address, timing, cuisine,
                tagList, note, rate, wishlist, blacklist);

        return new AddCommand(foodplace);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
