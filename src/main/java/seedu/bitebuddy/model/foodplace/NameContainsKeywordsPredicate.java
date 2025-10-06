package seedu.bitebuddy.model.foodplace;

import java.util.List;
import java.util.function.Predicate;

import seedu.bitebuddy.commons.util.StringUtil;
import seedu.bitebuddy.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Foodplace}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Foodplace> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Foodplace foodplace) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(foodplace.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsKeywordsPredicate)) {
            return false;
        }

        NameContainsKeywordsPredicate otherNameContainsKeywordsPredicate = (NameContainsKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
