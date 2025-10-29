package seedu.bitebuddy.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.function.Predicate;

import seedu.bitebuddy.commons.util.ToStringBuilder;
import seedu.bitebuddy.logic.Messages;
import seedu.bitebuddy.model.Model;
import seedu.bitebuddy.model.foodplace.Foodplace;
import seedu.bitebuddy.model.foodplace.FoodplaceContainsKeywordsPredicate;
import seedu.bitebuddy.model.foodplace.FoodplaceMatchesCriteriaPredicate;

/**
 * Finds and lists all foodplaces in BiteBuddy whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all foodplaces in BiteBuddy whose entries match" +
            " the given keywords & specified fields (e.g. t/, c/, r/), then displays them as a list.\n"
            + "• Keywords and specified fields are case-insensitive.\n"
            + "• For keywords, foodplaces with fields that match any substring will be returned.\n"
            + "• For specified fields, only foodplaces that match exactly ALL specified fields are returned.\n"
            + "Parameters:\n"
            + "  " + "KEYWORD [MORE_KEYWORDS]... [t/TAG [MORE_TAGS]...] [c/CUISINE] [r/RATING]\n"
            + "Examples:\n"
            + "  " + COMMAND_WORD + " western cheap aircon\n"
            + "  " + COMMAND_WORD + " t/ hawker c/ japanese r/ 8\n"
            + "  " + COMMAND_WORD + " chicken t/ hawker";

    private final Optional<FoodplaceContainsKeywordsPredicate> keywordPredicate;
    private final Optional<FoodplaceMatchesCriteriaPredicate> fieldPredicate;

    /**
     * Creates a {@code FindCommand} that filters and lists food places in BiteBuddy
     * based on either general search keywords or specific field-based criteria.
     *
     * @param keywordPredicate Used for general keyword-based search.
     * @param fieldPredicate   Used for field-specific search (e.g., by cuisine, tag, or rating).
     */
    public FindCommand(Optional<FoodplaceContainsKeywordsPredicate> keywordPredicate,
                       Optional<FoodplaceMatchesCriteriaPredicate> fieldPredicate) {
        this.keywordPredicate = keywordPredicate;
        this.fieldPredicate = fieldPredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Predicate<Foodplace> combinedPredicate = foodplace -> true;

        if (keywordPredicate.isPresent()) {
            combinedPredicate = combinedPredicate.and(keywordPredicate.get());
        }
        if (fieldPredicate.isPresent()) {
            combinedPredicate = combinedPredicate.and(fieldPredicate.get());
        }

        model.updateFilteredFoodplaceList(combinedPredicate);

        return new CommandResult(
                String.format(Messages.MESSAGE_FOODPLACES_LISTED_OVERVIEW, model.getFilteredFoodplaceList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return keywordPredicate.equals(otherFindCommand.keywordPredicate)
                && fieldPredicate.equals(otherFindCommand.fieldPredicate);
    }

    @Override
    public int hashCode() {
        return keywordPredicate.hashCode() + fieldPredicate.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("keywordPredicate", keywordPredicate)
                .add("fieldPredicate", fieldPredicate)
                .toString();
    }
}
