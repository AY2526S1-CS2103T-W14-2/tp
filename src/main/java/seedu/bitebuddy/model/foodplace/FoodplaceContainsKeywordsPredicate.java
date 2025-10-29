package seedu.bitebuddy.model.foodplace;

import java.util.List;
import java.util.function.Predicate;

import seedu.bitebuddy.commons.util.StringUtil;
import seedu.bitebuddy.commons.util.ToStringBuilder;
import seedu.bitebuddy.model.tag.Tag;

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
        StringBuilder searchableFields = new StringBuilder();
        searchableFields.append(foodplace.getName().fullName).append(" ");
        searchableFields.append(foodplace.getAddress().value).append(" ");
        searchableFields.append(foodplace.getEmail().value).append(" ");
        searchableFields.append(foodplace.getPhone().value).append(" ");
        searchableFields.append(foodplace.getNote().value).append(" ");
        searchableFields.append(foodplace.getRate().getValue()).append(" ");

        for (Tag tag : foodplace.getTags()) {
            searchableFields.append(tag.tagName).append(" ");
        }

        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsSubstringIgnoreCase(searchableFields.toString(), keyword));
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
