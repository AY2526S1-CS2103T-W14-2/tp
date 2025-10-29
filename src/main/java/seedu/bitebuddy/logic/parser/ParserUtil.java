package seedu.bitebuddy.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.bitebuddy.commons.core.index.Index;
import seedu.bitebuddy.commons.util.StringUtil;
import seedu.bitebuddy.logic.parser.exceptions.ParseException;
import seedu.bitebuddy.model.foodplace.Address;
import seedu.bitebuddy.model.foodplace.Cuisine;
import seedu.bitebuddy.model.foodplace.Email;
import seedu.bitebuddy.model.foodplace.Name;
import seedu.bitebuddy.model.foodplace.Note;
import seedu.bitebuddy.model.foodplace.Phone;
import seedu.bitebuddy.model.foodplace.Rate;
import seedu.bitebuddy.model.foodplace.Timing;
import seedu.bitebuddy.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_BOTH_TIMES_REQUIRED = "Both opening and closing time "
            + "(ot/ and ct/) must be provided";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        if (phone.isEmpty()) {
            return new Phone("");
        }
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String bitebuddy} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code bitebuddy} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        if (email.isEmpty()) {
            return new Email("");
        }
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String cuisine} into an {@code Cuisine}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code cuisine} is invalid.
     */
    public static Cuisine parseCuisine(String cuisine) throws ParseException {
        requireNonNull(cuisine);
        String trimmedCuisine = cuisine.trim();
        if (!Cuisine.isValidCuisine(trimmedCuisine)) {
            throw new ParseException(Cuisine.MESSAGE_CONSTRAINTS);
        }
        return new Cuisine(trimmedCuisine);
    }

    /**
     * Parses a {@code String time} into a {@code LocalTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code time} is invalid.
     */
    public static LocalTime parseTime(String time) throws ParseException {
        requireNonNull(time);
        String trimmed = time.trim();
        try {
            return LocalTime.parse(trimmed);
        } catch (DateTimeParseException dte) {
            throw new ParseException(Timing.MESSAGE_INVALID_TIME);
        }
    }

    /**
     * Parses a {@code String open} and {@code String close} into a {@code Timing}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code open} and {@code close} is invalid.
     */
    public static Timing parseTiming(String open, String close) throws ParseException {
        requireNonNull(open);
        requireNonNull(close);
        if (open.isEmpty() || close.isEmpty()) {
            throw new ParseException(MESSAGE_BOTH_TIMES_REQUIRED);
        }
        String trimmedOpen = open.trim();
        String trimmedClose = close.trim();
        if (!Timing.isValidTime(trimmedOpen)) {
            throw new ParseException(Timing.MESSAGE_INVALID_TIME);
        }
        if (!Timing.isValidTime(trimmedClose)) {
            throw new ParseException(Timing.MESSAGE_INVALID_TIME);
        }
        LocalTime opening = LocalTime.parse(trimmedOpen);
        LocalTime closing = LocalTime.parse(trimmedClose);
        if (closing.isBefore(opening)) {
            throw new ParseException(Timing.MESSAGE_CONSTRAINTS);
        }
        return new Timing(opening, closing);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String note} into an {@code note}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code note} is invalid.
     */
    public static Note parseNote(String note) throws ParseException {
        requireNonNull(note);
        String trimmedNote = note.trim();
        if (!Note.isValidNote(trimmedNote)) {
            throw new ParseException(Note.MESSAGE_CONSTRAINTS);
        }
        return new Note(trimmedNote);
    }

    /**
     * Parses {@code Collection<String> Rate} into a {@code Set<Rate>}.
     * Used to support how ratings are optional.
     */
    public static Rate parseRatings(Collection<String> ratings) throws ParseException {
        requireNonNull(ratings);
        String mostRecentRating = ratings.stream().reduce((prev, next) -> next).orElse(null);
        if (mostRecentRating == null) {
            return new Rate();
        }

        // Case where >= 1 Rate's CLI tag have been detected, take latest one
        Integer rating;
        Rate rate;
        try {
            rating = Integer.valueOf(mostRecentRating);
            rate = new Rate(rating);
        } catch (IllegalArgumentException iae) {
            throw new ParseException(Rate.MESSAGE_CONSTRAINTS);
        }
        return rate;
    }

    /**
     * Returns true if none or both of the prefixes are present in the given {@code ArgumentMultimap}.
     */
    public static boolean areNoneOrBothPrefixesPresent(ArgumentMultimap argumentMultimap,
            Prefix prefix1, Prefix prefix2) {
        boolean isPrefix1Present = argumentMultimap.getValue(prefix1).isPresent();
        boolean isPrefix2Present = argumentMultimap.getValue(prefix2).isPresent();
        return (isPrefix1Present && isPrefix2Present) || (!isPrefix1Present && !isPrefix2Present);
    }

    /**
     * Returns true if both of the prefixes are present in the given {@code ArgumentMultimap}.
     */
    public static boolean areBothPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix prefix1, Prefix prefix2) {
        boolean isPrefix1Present = argumentMultimap.getValue(prefix1).isPresent();
        boolean isPrefix2Present = argumentMultimap.getValue(prefix2).isPresent();
        return isPrefix1Present && isPrefix2Present;
    }

    /**
     * Returns true if neither of the prefixes are present in the given {@code ArgumentMultimap}.
     */
    public static boolean areNeitherPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix prefix1, Prefix prefix2) {
        boolean isPrefix1Present = argumentMultimap.getValue(prefix1).isPresent();
        boolean isPrefix2Present = argumentMultimap.getValue(prefix2).isPresent();
        return !isPrefix1Present && !isPrefix2Present;
    }
}
