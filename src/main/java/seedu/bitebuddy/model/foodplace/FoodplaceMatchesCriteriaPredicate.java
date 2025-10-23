package seedu.bitebuddy.model.foodplace;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.bitebuddy.commons.util.ToStringBuilder;
import seedu.bitebuddy.model.tag.Tag;

/**
 * Tests that a {@code Foodplace} matches the given search criteria.
 */
public class FoodplaceMatchesCriteriaPredicate implements Predicate<Foodplace> {
    private final List<String> tags;
    private final Optional<Cuisine> cuisine;
    private final Optional<Rate> rating;

    /**
     * Constructs a {@code FoodplaceMatchesCriteriaPredicate} to test if a {@code Foodplace}
     * matches the given search criteria.
     *
     * @param tags    List of tag keywords to match exactly (case-insensitive).
     * @param cuisine Optional cuisine to match.
     * @param rating  Optional rating to match.
     */
    public FoodplaceMatchesCriteriaPredicate(List<String> tags, Optional<Cuisine> cuisine, Optional<Rate> rating) {
        this.tags = tags;
        this.cuisine = cuisine;
        this.rating = rating;
    }

    @Override
    public boolean test(Foodplace foodplace) {
        // Build tag fields
        StringBuilder tagField = new StringBuilder();
        for (Tag tag : foodplace.getTags()) {
            tagField.append(tag.tagName).append(" ");
        }

        // Build rating field
        Rate ratingField = foodplace.getRate();

        // Build cuisine field
        Cuisine cuisineField = foodplace.getCuisine();

        // Determine active filters
        boolean hasTagFilter = !tags.isEmpty();
        boolean hasRatingFilter = rating.isPresent();
        boolean hasCuisineFilter = cuisine.isPresent();

        boolean matchesTag = !hasTagFilter
                || tags.stream().allMatch(tagKeyword ->
                        foodplace.getTags().stream()
                                .anyMatch(tag -> tag.tagName.equalsIgnoreCase(tagKeyword)));
        boolean matchesRating = !hasRatingFilter || ratingField.equals(rating.get());
        boolean matchesCuisine = !hasCuisineFilter || cuisineField.equals(cuisine.get());

        return matchesTag && matchesCuisine && matchesRating;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FoodplaceMatchesCriteriaPredicate)) {
            return false;
        }

        FoodplaceMatchesCriteriaPredicate otherPredicate = (FoodplaceMatchesCriteriaPredicate) other;
        return tags.equals(otherPredicate.tags)
                && cuisine.equals(otherPredicate.cuisine)
                && rating.equals(otherPredicate.rating);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("tags", tags)
                .add("cuisine", cuisine)
                .add("rating", rating)
                .toString();
    }
}
