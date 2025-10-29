package seedu.bitebuddy.model.foodplace;

import java.util.List;
import java.util.function.Predicate;

import seedu.bitebuddy.commons.util.StringUtil;
import seedu.bitebuddy.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Foodplace}'s {@code Name} matches any of the keywords given.
 */
public class FoodplaceContainsKeywordsPredicate implements Predicate<Foodplace> {
    private final List<String> keywords;

    public FoodplaceContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Foodplace foodplace) {
        StringBuilder searchable = new StringBuilder();
        appendIfNotNull(searchable, foodplace.getName().fullName);
        appendIfNotNull(searchable, foodplace.getAddress().value);
        appendIfNotNull(searchable, foodplace.getEmail().value);
        appendIfNotNull(searchable, foodplace.getPhone().value);
        appendIfNotNull(searchable, foodplace.getNote().value);
        appendIfNotNull(searchable, foodplace.getRate().getValue().toString());
        appendIfNotNull(searchable, foodplace.getCuisine().value);
        appendIfNotNull(searchable, foodplace.getTiming().toString());
        foodplace.getTags().forEach(tag -> appendIfNotNull(searchable, tag.tagName));

        String searchableString = searchable.toString();

        return keywords.stream().anyMatch(keyword ->
                StringUtil.containsSubstringIgnoreCase(searchableString, keyword));
    }

    private void appendIfNotNull(StringBuilder sb, String field) {
        if (field != null) {
            sb.append(field).append(" ");
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FoodplaceContainsKeywordsPredicate)) {
            return false;
        }

        FoodplaceContainsKeywordsPredicate otherFoodplaceContainsKeywordsPredicate =
                (FoodplaceContainsKeywordsPredicate) other;
        return keywords.equals(otherFoodplaceContainsKeywordsPredicate.keywords);
    }

    @Override
    public int hashCode() {
        return keywords.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
