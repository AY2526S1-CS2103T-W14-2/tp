package seedu.bitebuddy.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.bitebuddy.logic.parser.Prefix;
import seedu.bitebuddy.model.foodplace.Foodplace;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX = "The foodplace index provided is invalid";
    public static final String MESSAGE_FOODPLACES_LISTED_OVERVIEW = "%1$d foodplaces listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code foodplace} for display to the user.
     */
    public static String format(Foodplace foodplace) {
        final StringBuilder builder = new StringBuilder();
        builder.append(foodplace.getName())
                .append("; Phone: ")
                .append(foodplace.getPhone())
                .append("; Email: ")
                .append(foodplace.getEmail())
                .append("; Address: ")
                .append(foodplace.getAddress())
                .append("; Tags: ");
        foodplace.getTags().forEach(builder::append);
        return builder.toString();
    }

}
