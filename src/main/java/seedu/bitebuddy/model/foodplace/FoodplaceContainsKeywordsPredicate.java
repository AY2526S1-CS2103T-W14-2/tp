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
        return keywords.stream().anyMatch(keyword ->
                containsIgnoreCase(foodplace.getName().fullName, keyword)
                        || containsIgnoreCase(foodplace.getAddress().value, keyword)
                        || containsIgnoreCase(foodplace.getEmail().value, keyword)
                        || containsIgnoreCase(foodplace.getPhone().value, keyword)
                        || containsIgnoreCase(foodplace.getNote().value, keyword)
                        || containsIgnoreCase(foodplace.getRate().getValue().toString(), keyword)
                        || containsIgnoreCase(foodplace.getCuisine().value, keyword)
                        || containsIgnoreCase(foodplace.getTiming().toString(), keyword)
                        || foodplace.getTags().stream().anyMatch(tag -> containsIgnoreCase(tag.tagName, keyword))
        );
    }

    private boolean containsIgnoreCase(String field, String keyword) {
        return StringUtil.containsSubstringIgnoreCase(field, keyword);
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
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
