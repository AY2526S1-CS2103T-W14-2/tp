package seedu.bitebuddy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.logic.Messages.MESSAGE_FOODPLACES_LISTED_OVERVIEW;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.bitebuddy.testutil.TypicalFoodplace.CARLSHOP;
import static seedu.bitebuddy.testutil.TypicalFoodplace.LAKSASHOP;
import static seedu.bitebuddy.testutil.TypicalFoodplace.PRATASHOP;
import static seedu.bitebuddy.testutil.TypicalFoodplace.SUSHISHOP;
import static seedu.bitebuddy.testutil.TypicalFoodplace.TEASHOP;
import static seedu.bitebuddy.testutil.TypicalFoodplace.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.model.Model;
import seedu.bitebuddy.model.ModelManager;
import seedu.bitebuddy.model.UserPrefs;
import seedu.bitebuddy.model.foodplace.Cuisine;
import seedu.bitebuddy.model.foodplace.FoodplaceContainsKeywordsPredicate;
import seedu.bitebuddy.model.foodplace.FoodplaceMatchesCriteriaPredicate;
import seedu.bitebuddy.model.foodplace.Rate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        FoodplaceContainsKeywordsPredicate firstPredicate =
                new FoodplaceContainsKeywordsPredicate(Collections.singletonList("first"));
        FoodplaceContainsKeywordsPredicate secondPredicate =
                new FoodplaceContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(Optional.of(firstPredicate), Optional.empty());
        FindCommand findSecondCommand = new FindCommand(Optional.of(secondPredicate), Optional.empty());

        // same object -> true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> true
        FindCommand findFirstCommandCopy = new FindCommand(Optional.of(firstPredicate), Optional.empty());
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> false
        assertFalse(findFirstCommand.equals(1));

        // null -> false
        assertNotEquals(findFirstCommand, null);

        // different predicate -> false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noFoodplaceFound() {
        String expectedMessage = String.format(MESSAGE_FOODPLACES_LISTED_OVERVIEW, 0);
        FoodplaceContainsKeywordsPredicate predicate = prepareKeywordPredicate(" ");
        FindCommand command = new FindCommand(Optional.of(predicate), Optional.empty());
        expectedModel.updateFilteredFoodplaceList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredFoodplaceList());
    }

    @Test
    public void execute_multipleKeywords_multipleFoodplacesFound() {
        String expectedMessage = String.format(MESSAGE_FOODPLACES_LISTED_OVERVIEW, 3);
        FoodplaceContainsKeywordsPredicate predicate = prepareKeywordPredicate("Junior Tei ITea");
        FindCommand command = new FindCommand(Optional.of(predicate), Optional.empty());
        expectedModel.updateFilteredFoodplaceList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARLSHOP, SUSHISHOP, TEASHOP), model.getFilteredFoodplaceList());
    }

    @Test
    public void execute_fieldPredicate_filterByCuisineAndRating() {
        // Example: find c/Japanese r/0
        FoodplaceMatchesCriteriaPredicate fieldPredicate =
                new FoodplaceMatchesCriteriaPredicate(
                        Collections.emptyList(),
                        Optional.of(new Cuisine("Japanese")),
                        Optional.of(new Rate(0)));

        String expectedMessage = String.format(MESSAGE_FOODPLACES_LISTED_OVERVIEW, 1);
        FindCommand command = new FindCommand(Optional.empty(), Optional.of(fieldPredicate));

        expectedModel.updateFilteredFoodplaceList(fieldPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Update this depending on your test data
        assertEquals(Collections.singletonList(SUSHISHOP), model.getFilteredFoodplaceList());
    }

    @Test
    public void execute_fieldPredicate_filterByTags() {
        FoodplaceMatchesCriteriaPredicate fieldPredicate =
                new FoodplaceMatchesCriteriaPredicate(
                        Arrays.asList("hawker"),
                        Optional.empty(),
                        Optional.empty());

        String expectedMessage = String.format(MESSAGE_FOODPLACES_LISTED_OVERVIEW, 2);
        FindCommand command = new FindCommand(Optional.empty(), Optional.of(fieldPredicate));

        expectedModel.updateFilteredFoodplaceList(fieldPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        assertEquals(Arrays.asList(PRATASHOP, LAKSASHOP), model.getFilteredFoodplaceList());
    }


    @Test
    public void hashcode_samePredicates_sameHash() {
        FoodplaceContainsKeywordsPredicate predicate =
                new FoodplaceContainsKeywordsPredicate(Arrays.asList("first", "second"));
        FindCommand findCommand = new FindCommand(Optional.of(predicate), Optional.empty());
        FindCommand findCommandCopy = new FindCommand(Optional.of(predicate), Optional.empty());
        assertEquals(findCommand.hashCode(), findCommandCopy.hashCode());
    }

    @Test
    public void toStringMethod() {
        FoodplaceContainsKeywordsPredicate predicate =
                new FoodplaceContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindCommand findCommand = new FindCommand(Optional.of(predicate), Optional.empty());
        String expected = FindCommand.class.getCanonicalName()
                + "{keywordPredicate=" + Optional.of(predicate)
                + ", fieldPredicate=" + Optional.empty() + "}";
        assertEquals(expected, findCommand.toString());
    }

    // helper
    private FoodplaceContainsKeywordsPredicate prepareKeywordPredicate(String userInput) {
        return new FoodplaceContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
