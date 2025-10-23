package seedu.bitebuddy.logic.parser;

import static seedu.bitebuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_CUISINE;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_RATE;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.bitebuddy.logic.commands.FindCommand;
import seedu.bitebuddy.logic.parser.exceptions.ParseException;
import seedu.bitebuddy.model.foodplace.Cuisine;
import seedu.bitebuddy.model.foodplace.FoodplaceContainsKeywordsPredicate;
import seedu.bitebuddy.model.foodplace.FoodplaceMatchesCriteriaPredicate;
import seedu.bitebuddy.model.foodplace.Rate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (trimmedArgs.startsWith("t/") || trimmedArgs.startsWith("c/") || trimmedArgs.startsWith("r/")) {
            trimmedArgs = " " + trimmedArgs;
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(trimmedArgs, PREFIX_TAG, PREFIX_CUISINE, PREFIX_RATE);

        String preamble = argMultimap.getPreamble().trim();
        List<String> keywords = preamble.isEmpty()
                ? Collections.emptyList()
                : Arrays.asList(preamble.split("\\s+"));

        List<String> tags = argMultimap.getAllValues(PREFIX_TAG).stream()
                .flatMap(value -> Arrays.stream(value.trim().split("\\s+")))
                .filter(s -> !s.isEmpty())
                .toList();

        Optional<Cuisine> cuisine = argMultimap.getValue(PREFIX_CUISINE)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Cuisine::new);

        Optional<Rate> rating;
        try {
            rating = argMultimap.getValue(PREFIX_RATE)
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(Integer::parseInt)
                    .map(Rate::new);
        } catch (IllegalArgumentException e) {
            throw new ParseException("Ratings should only contain numbers, and be an integer between 1 to 10");
        }

        boolean hasEmptyTag =
                argMultimap.getAllValues(PREFIX_TAG).stream()
                        .anyMatch(String::isBlank);

        boolean hasEmptyCuisine =
                argMultimap.getValue(PREFIX_CUISINE).map(String::isBlank).orElse(false);

        boolean hasEmptyRate =
                argMultimap.getValue(PREFIX_RATE).map(String::isBlank).orElse(false);

        boolean hasEmptyPrefixValue = hasEmptyTag || hasEmptyCuisine || hasEmptyRate;

        if (hasEmptyPrefixValue) {
            throw new ParseException("Prefix provided without value.\n" + FindCommand.MESSAGE_USAGE);
        }

        Optional<FoodplaceContainsKeywordsPredicate> keywordPredicate =
                keywords.isEmpty()
                        ? Optional.empty()
                        : Optional.of(new FoodplaceContainsKeywordsPredicate(keywords));

        Optional<FoodplaceMatchesCriteriaPredicate> fieldPredicate = (!tags.isEmpty() || cuisine.isPresent()
                || rating.isPresent())
                        ? Optional.of(new FoodplaceMatchesCriteriaPredicate(tags, cuisine, rating))
                        : Optional.empty();

        if (keywordPredicate.isEmpty() && fieldPredicate.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(keywordPredicate, fieldPredicate);
    }

}
