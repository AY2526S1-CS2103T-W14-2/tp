package seedu.bitebuddy.model.foodplace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.testutil.FoodplaceBuilder;

public class FoodplaceMatchesCriteriaPredicateTest {

    @Test
    public void equals() {
        List<String> firstTagList = Collections.singletonList("hawker");
        List<String> secondTagList = Arrays.asList("hawker", "cheap");

        FoodplaceMatchesCriteriaPredicate firstPredicate =
                new FoodplaceMatchesCriteriaPredicate(firstTagList,
                        Optional.of(new Cuisine("Japanese")), Optional.of(new Rate(8)));
        FoodplaceMatchesCriteriaPredicate secondPredicate =
                new FoodplaceMatchesCriteriaPredicate(secondTagList,
                        Optional.of(new Cuisine("Korean")), Optional.of(new Rate(7)));

        // same object -> true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> true
        FoodplaceMatchesCriteriaPredicate firstPredicateCopy =
                new FoodplaceMatchesCriteriaPredicate(firstTagList,
                        Optional.of(new Cuisine("Japanese")), Optional.of(new Rate(8)));
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> false
        assertFalse(firstPredicate.equals(1));

        // null -> false
        assertFalse(firstPredicate.equals(null));

        // different values -> false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_foodplaceMatchesCriteria_returnsTrue() {
        // Matches tag
        FoodplaceMatchesCriteriaPredicate predicate = new FoodplaceMatchesCriteriaPredicate(
                Collections.singletonList("hawker"),
                Optional.empty(),
                Optional.empty());
        assertTrue(predicate.test(new FoodplaceBuilder().withTags("hawker", "cheap").build()));

        // Matches cuisine
        predicate = new FoodplaceMatchesCriteriaPredicate(
                Collections.emptyList(),
                Optional.of(new Cuisine("Japanese")),
                Optional.empty());
        assertTrue(predicate.test(new FoodplaceBuilder().withCuisine("Japanese").build()));

        // Matches rating
        predicate = new FoodplaceMatchesCriteriaPredicate(
                Collections.emptyList(),
                Optional.empty(),
                Optional.of(new Rate(8)));
        assertTrue(predicate.test(new FoodplaceBuilder().withRate("8").build()));

        // Matches all three
        predicate = new FoodplaceMatchesCriteriaPredicate(
                Arrays.asList("hawker"),
                Optional.of(new Cuisine("Indian")),
                Optional.of(new Rate(9)));
        assertTrue(predicate.test(new FoodplaceBuilder()
                .withTags("hawker", "cheap")
                .withCuisine("Indian")
                .withRate("9")
                .build()));

        // Case-insensitive tag and cuisine match
        predicate = new FoodplaceMatchesCriteriaPredicate(
                Arrays.asList("HaWkEr"),
                Optional.of(new Cuisine("jApAnEsE")),
                Optional.of(new Rate(0)));
        assertTrue(predicate.test(new FoodplaceBuilder()
                .withTags("hawker")
                .withCuisine("Japanese")
                .withRate("0")
                .build()));
    }

    @Test
    public void test_foodplaceDoesNotMatchCriteria_returnsFalse() {
        // Tag mismatch
        FoodplaceMatchesCriteriaPredicate predicate = new FoodplaceMatchesCriteriaPredicate(
                Collections.singletonList("restaurant"),
                Optional.empty(),
                Optional.empty());
        assertFalse(predicate.test(new FoodplaceBuilder().withTags("hawker").build()));

        // Cuisine mismatch
        predicate = new FoodplaceMatchesCriteriaPredicate(
                Collections.emptyList(),
                Optional.of(new Cuisine("Korean")),
                Optional.empty());
        assertFalse(predicate.test(new FoodplaceBuilder().withCuisine("Japanese").build()));

        // Rating mismatch
        predicate = new FoodplaceMatchesCriteriaPredicate(
                Collections.emptyList(),
                Optional.empty(),
                Optional.of(new Rate(5)));
        assertFalse(predicate.test(new FoodplaceBuilder().withRate("8").build()));

        // One criterion mismatch -> false
        predicate = new FoodplaceMatchesCriteriaPredicate(
                Arrays.asList("hawker"),
                Optional.of(new Cuisine("Indian")),
                Optional.of(new Rate(9)));
        assertFalse(predicate.test(new FoodplaceBuilder()
                .withTags("hawker")
                .withCuisine("Indian")
                .withRate("8")
                .build()));
    }

    @Test
    public void toStringMethod() {
        List<String> tags = List.of("hawker", "cheap");
        Optional<Cuisine> cuisine = Optional.of(new Cuisine("Japanese"));
        Optional<Rate> rating = Optional.of(new Rate(8));

        FoodplaceMatchesCriteriaPredicate predicate =
                new FoodplaceMatchesCriteriaPredicate(tags, cuisine, rating);

        String expected = FoodplaceMatchesCriteriaPredicate.class.getCanonicalName()
                + "{tags=" + tags
                + ", cuisine=" + cuisine
                + ", rating=" + rating + "}";
        assertEquals(expected, predicate.toString());
    }
}
