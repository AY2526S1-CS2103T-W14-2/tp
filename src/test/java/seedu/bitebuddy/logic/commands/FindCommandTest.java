package seedu.bitebuddy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.logic.Messages.MESSAGE_FOODPLACES_LISTED_OVERVIEW;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.bitebuddy.testutil.TypicalFoodplace.CARLSHOP;
import static seedu.bitebuddy.testutil.TypicalFoodplace.SUSHISHOP;
import static seedu.bitebuddy.testutil.TypicalFoodplace.TEASHOP;
import static seedu.bitebuddy.testutil.TypicalFoodplace.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.model.Model;
import seedu.bitebuddy.model.ModelManager;
import seedu.bitebuddy.model.UserPrefs;
import seedu.bitebuddy.model.foodplace.FoodplaceContainsKeywordsPredicate;

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

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertNotEquals(findFirstCommand, null);

        // different foodplace -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noFoodplaceFound() {
        String expectedMessage = String.format(MESSAGE_FOODPLACES_LISTED_OVERVIEW, 0);
        FoodplaceContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredFoodplaceList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredFoodplaceList());
    }

    @Test
    public void execute_multipleKeywords_multipleFoodplacesFound() {
        String expectedMessage = String.format(MESSAGE_FOODPLACES_LISTED_OVERVIEW, 3);
        FoodplaceContainsKeywordsPredicate predicate = preparePredicate("Junior Tei ITea");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredFoodplaceList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARLSHOP, SUSHISHOP, TEASHOP), model.getFilteredFoodplaceList());
    }

    @Test
    public void hashcode() {
        FoodplaceContainsKeywordsPredicate predicate = new FoodplaceContainsKeywordsPredicate(
                Arrays.asList("first", "second"));
        FindCommand findCommand = new FindCommand(predicate);
        FindCommand findCommandCopy = new FindCommand(predicate);
        assertEquals(findCommand.hashCode(), findCommandCopy.hashCode());
    }

    @Test
    public void toStringMethod() {
        FoodplaceContainsKeywordsPredicate predicate = new FoodplaceContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindCommand findCommand = new FindCommand(predicate);
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private FoodplaceContainsKeywordsPredicate preparePredicate(String userInput) {
        return new FoodplaceContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
