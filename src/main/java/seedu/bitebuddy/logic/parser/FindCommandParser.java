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
import seedu.bitebuddy.model.tag.Tag;


/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    public static final String MESSAGE_INVALID_RATING = "Ratings should only contain numbers,"
            + " and be an integer between 0 to 10 (use 0 for unrated food places)";
    public static final String MESSAGE_INVALID_CUISINE = "Cuisine should only contain alphanumeric characters";
    public static final String MESSAGE_NO_PREFIX = "Prefix provided without value.\n" + FindCommand.MESSAGE_USAGE;

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE)
            );
        }

        // normalize when the first token is a prefix so ArgumentTokenizer treats it as a preamble
        if (trimmedArgs.startsWith("t/") || trimmedArgs.startsWith("c/") || trimmedArgs.startsWith("r/")) {
            trimmedArgs = " " + trimmedArgs;
        }

        ArgumentMultimap argMultimap =
                        ArgumentTokenizer.tokenize(trimmedArgs, PREFIX_TAG, PREFIX_CUISINE, PREFIX_RATE);

        String preamble = argMultimap.getPreamble().trim();
        List<String> keywords = extractKeywords(preamble);

        List<String> tags = extractTags(argMultimap);

        Optional<Cuisine> cuisine = extractCuisine(argMultimap);
        Optional<Rate> rating = extractRating(argMultimap);

        ensureNoEmptyPrefixValues(argMultimap);

        Optional<FoodplaceContainsKeywordsPredicate> keywordPredicate = buildKeywordPredicate(keywords);
        Optional<FoodplaceMatchesCriteriaPredicate> fieldPredicate = buildFieldPredicate(tags, cuisine, rating);

        if (keywordPredicate.isEmpty() && fieldPredicate.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(keywordPredicate, fieldPredicate);
    }

    private List<String> extractKeywords(String preamble) {
        if (preamble.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.asList(preamble.split("\\s+"));
    }

    private List<String> extractTags(ArgumentMultimap argMultimap) throws ParseException {
        List<String> tags = argMultimap.getAllValues(PREFIX_TAG).stream()
                .flatMap(value -> Arrays.stream(value.trim().split("\\s+")))
                .filter(s -> !s.isEmpty())
                .toList();
        for (String tag : tags) {
            if (!Tag.isValidTagName(tag)) {
                throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
            }
        }

        return tags;
    }

    private Optional<Cuisine> extractCuisine(ArgumentMultimap argMultimap) throws ParseException {
        try {
            return argMultimap.getValue(PREFIX_CUISINE)
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(Cuisine::new);
        } catch (IllegalArgumentException e) {
            throw new ParseException(MESSAGE_INVALID_CUISINE);
        }
    }

    private Optional<Rate> extractRating(ArgumentMultimap argMultimap) throws ParseException {
        try {
            return argMultimap.getValue(PREFIX_RATE)
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(Integer::parseInt)
                    .map(Rate::new);
        } catch (IllegalArgumentException iae) {
            throw new ParseException(MESSAGE_INVALID_RATING);
        }
    }

    private void ensureNoEmptyPrefixValues(ArgumentMultimap argMultimap) throws ParseException {
        boolean hasEmptyTag = argMultimap.getAllValues(PREFIX_TAG).stream().anyMatch(String::isBlank);
        boolean hasEmptyCuisine = argMultimap.getValue(PREFIX_CUISINE).map(String::isBlank).orElse(false);
        boolean hasEmptyRate = argMultimap.getValue(PREFIX_RATE).map(String::isBlank).orElse(false);

        if (hasEmptyTag || hasEmptyCuisine || hasEmptyRate) {
            throw new ParseException(MESSAGE_NO_PREFIX);
        }
    }

    private Optional<FoodplaceContainsKeywordsPredicate> buildKeywordPredicate(List<String> keywords) {
        return keywords.isEmpty() ? Optional.empty() : Optional.of(new FoodplaceContainsKeywordsPredicate(keywords));
    }

    private Optional<FoodplaceMatchesCriteriaPredicate> buildFieldPredicate(List<String> tags,
                    Optional<Cuisine> cuisine, Optional<Rate> rating) {
        return (!tags.isEmpty() || cuisine.isPresent() || rating.isPresent())
                ? Optional.of(new FoodplaceMatchesCriteriaPredicate(tags, cuisine, rating))
                : Optional.empty();
    }
}
