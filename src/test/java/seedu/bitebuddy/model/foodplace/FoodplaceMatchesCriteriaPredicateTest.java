package seedu.bitebuddy.model.foodplace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

        FoodplaceMatchesCriteriaPredicate firstPredicate =
                new FoodplaceMatchesCriteriaPredicate(firstTagList,
                        Optional.of(new Cuisine("Japanese")), Optional.of(new Rate(8)));

        // same object -> true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> true
        FoodplaceMatchesCriteriaPredicate firstPredicateCopy =
                new FoodplaceMatchesCriteriaPredicate(firstTagList,
                        Optional.of(new Cuisine("Japanese")), Optional.of(new Rate(8)));
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> false
        assertNotEquals(firstPredicate, 1);

        // null -> false
        assertNotEquals(firstPredicate, null);

        // different tags -> false
        List<String> secondTagList = Arrays.asList("restaurant", "cheap");
        FoodplaceMatchesCriteriaPredicate secondPredicate =
                new FoodplaceMatchesCriteriaPredicate(secondTagList,
                        Optional.of(new Cuisine("Japanese")), Optional.of(new Rate(8)));
        assertFalse(firstPredicate.equals(secondPredicate));

        // different cuisine -> false
        secondPredicate = new FoodplaceMatchesCriteriaPredicate(firstTagList,
                Optional.of(new Cuisine("Italian")), Optional.of(new Rate(8)));
        assertFalse(firstPredicate.equals(secondPredicate));

        // different rating -> false
        secondPredicate = new FoodplaceMatchesCriteriaPredicate(firstTagList,
                Optional.of(new Cuisine("Japanese")), Optional.of(new Rate(7)));
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void hashcode() {
        List<String> tagList = Collections.singletonList("hawker");

        FoodplaceMatchesCriteriaPredicate predicate1 =
                new FoodplaceMatchesCriteriaPredicate(tagList,
                        Optional.of(new Cuisine("Japanese")), Optional.of(new Rate(8)));

        FoodplaceMatchesCriteriaPredicate predicate1copy =
                new FoodplaceMatchesCriteriaPredicate(tagList,
                        Optional.of(new Cuisine("Japanese")), Optional.of(new Rate(8)));

        assertEquals(predicate1.hashCode(), predicate1copy.hashCode());

        FoodplaceMatchesCriteriaPredicate predicate2 =
                new FoodplaceMatchesCriteriaPredicate(tagList,
                        Optional.of(new Cuisine("Italian")), Optional.of(new Rate(8)));
        assertNotEquals(predicate1.hashCode(), predicate2.hashCode());
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
