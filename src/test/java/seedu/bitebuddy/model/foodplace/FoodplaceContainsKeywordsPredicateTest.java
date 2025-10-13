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
    public void test_foodplaceContainsKeywords_returnsTrue() {
        // Name
        FoodplaceContainsKeywordsPredicate predicate = new FoodplaceContainsKeywordsPredicate(Collections.singletonList("Prata"));
        assertTrue(predicate.test(new FoodplaceBuilder().withName("Prata Place").build()));

        // Phone
        predicate = new FoodplaceContainsKeywordsPredicate(Collections.singletonList("12345"));
        assertTrue(predicate.test(new FoodplaceBuilder().withPhone("12345").build()));

        // Email
        predicate = new FoodplaceContainsKeywordsPredicate(Collections.singletonList("alice@email.com"));
        assertTrue(predicate.test(new FoodplaceBuilder().withEmail("alice@email.com").build()));

        // Address
        predicate = new FoodplaceContainsKeywordsPredicate(Collections.singletonList("Main"));
        assertTrue(predicate.test(new FoodplaceBuilder().withAddress("Main Street").build()));

        // Note
        predicate = new FoodplaceContainsKeywordsPredicate(Collections.singletonList("Delivery"));
        assertTrue(predicate.test(new FoodplaceBuilder().withNote("Fast delivery").build()));

        // Rate
        predicate = new FoodplaceContainsKeywordsPredicate(Collections.singletonList("5"));
        assertTrue(predicate.test(new FoodplaceBuilder().withRate("5").build()));

        // Tags
        predicate = new FoodplaceContainsKeywordsPredicate(Collections.singletonList("Spicy"));
        assertTrue(predicate.test(new FoodplaceBuilder().withTags("Spicy").build()));

        // Match in multiple fields: name, note, tags
        predicate = new FoodplaceContainsKeywordsPredicate(Arrays.asList("Prata", "Delivery", "Spicy"));
        assertTrue(predicate.test(new FoodplaceBuilder()
                .withName("Prata Place")
                .withNote("Fast delivery")
                .withTags("Spicy", "Takeaway")
                .build()));

        // Mixed-case keywords
        predicate = new FoodplaceContainsKeywordsPredicate(Arrays.asList("pRata", "dElIvErY", "sPiCy"));
        assertTrue(predicate.test(new FoodplaceBuilder()
                .withName("Prata Place")
                .withNote("Fast delivery")
                .withTags("Spicy", "Takeaway")
                .build()));
    }

    @Test
    public void test_foodplaceDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        FoodplaceContainsKeywordsPredicate predicate = new FoodplaceContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new FoodplaceBuilder().withName("Prata Place").build()));

        // Non-matching keyword
        predicate = new FoodplaceContainsKeywordsPredicate(Arrays.asList("Swensen"));
        assertFalse(predicate.test(new FoodplaceBuilder().withName("Prata Place").build()));

        // Multiple non-matching keywords
        predicate = new FoodplaceContainsKeywordsPredicate(Arrays.asList("9999", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new FoodplaceBuilder().withName("Prata Place").withPhone("12345")
                .withEmail("bob@email.com").withAddress("First Ave").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        FoodplaceContainsKeywordsPredicate predicate = new FoodplaceContainsKeywordsPredicate(keywords);

        String expected = FoodplaceContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
