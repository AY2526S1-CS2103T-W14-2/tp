package seedu.bitebuddy.model.foodplace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.testutil.FoodplaceBuilder;

public class FoodplaceContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        FoodplaceContainsKeywordsPredicate firstPredicate = new FoodplaceContainsKeywordsPredicate(firstPredicateKeywordList);
        FoodplaceContainsKeywordsPredicate secondPredicate = new FoodplaceContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FoodplaceContainsKeywordsPredicate firstPredicateCopy = new FoodplaceContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different foodplace -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        FoodplaceContainsKeywordsPredicate predicate = new FoodplaceContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new FoodplaceBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new FoodplaceContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new FoodplaceBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new FoodplaceContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new FoodplaceBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new FoodplaceContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new FoodplaceBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        FoodplaceContainsKeywordsPredicate predicate = new FoodplaceContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new FoodplaceBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new FoodplaceContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new FoodplaceBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and bitebuddy, but does not match name
        predicate = new FoodplaceContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new FoodplaceBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        FoodplaceContainsKeywordsPredicate predicate = new FoodplaceContainsKeywordsPredicate(keywords);

        String expected = FoodplaceContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
