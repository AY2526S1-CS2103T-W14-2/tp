package seedu.bitebuddy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.bitebuddy.testutil.TypicalFoodplace.getTypicalAddressBook;
import static seedu.bitebuddy.testutil.TypicalIndexes.INDEX_FIRST_FOODPLACE;
import static seedu.bitebuddy.testutil.TypicalIndexes.INDEX_SECOND_FOODPLACE;
import static seedu.bitebuddy.testutil.TypicalIndexes.INDEX_THIRD_FOODPLACE;

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.commons.core.index.Index;
import seedu.bitebuddy.logic.Messages;
import seedu.bitebuddy.model.AddressBook;
import seedu.bitebuddy.model.Model;
import seedu.bitebuddy.model.ModelManager;
import seedu.bitebuddy.model.UserPrefs;
import seedu.bitebuddy.model.foodplace.Foodplace;

/**
 * Contains integration tests (interaction with the Model) and unit tests for CompareCommand.
 */
public class CompareCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexesUnfilteredList_success() {
        Foodplace firstFoodplace = model.getFilteredFoodplaceList().get(INDEX_FIRST_FOODPLACE.getZeroBased());
        Foodplace thirdFoodplace = model.getFilteredFoodplaceList().get(INDEX_THIRD_FOODPLACE.getZeroBased());
        CompareCommand compareCommand = new CompareCommand(INDEX_FIRST_FOODPLACE, INDEX_THIRD_FOODPLACE);

        String expectedMessage = "Prata Palace (Rating: --) vs Carls Junior (Rating: --)\n"
                + "Common tags: --\n"
                + "Unique tags: Prata Palace (hawker) | Carls Junior (fastfood)\n";

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateFilteredFoodplaceList(fp -> fp.equals(firstFoodplace) || fp.equals(thirdFoodplace));

        assertCommandSuccess(compareCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFoodplaceList().size() + 1);
        CompareCommand compareCommand = new CompareCommand(INDEX_FIRST_FOODPLACE, outOfBoundIndex);

        assertCommandFailure(compareCommand, model, Messages.MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_filteredList_success() {
        // Select original items before filtering
        Foodplace originalFirst = model.getFilteredFoodplaceList().get(INDEX_FIRST_FOODPLACE.getZeroBased());
        Foodplace originalThird = model.getFilteredFoodplaceList().get(INDEX_THIRD_FOODPLACE.getZeroBased());

        // Filter so only these two remain; order should match how CompareCommand interprets indexes
        model.updateFilteredFoodplaceList(fp -> fp.equals(originalFirst) || fp.equals(originalThird));

        // Now, in the filtered list, these correspond to index 1 and index 2 (logically)
        CompareCommand compareCommand = new CompareCommand(INDEX_FIRST_FOODPLACE, INDEX_SECOND_FOODPLACE);

        String expectedMessage = "Prata Palace (Rating: --) vs Carls Junior (Rating: --)\n"
                + "Common tags: --\n"
                + "Unique tags: Prata Palace (hawker) | Carls Junior (fastfood)\n";

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateFilteredFoodplaceList(fp -> fp.equals(originalFirst) || fp.equals(originalThird));

        assertCommandSuccess(compareCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        CompareCommand compareFirstSecond = new CompareCommand(INDEX_FIRST_FOODPLACE, INDEX_SECOND_FOODPLACE);
        CompareCommand compareFirstSecondCopy = new CompareCommand(INDEX_FIRST_FOODPLACE, INDEX_SECOND_FOODPLACE);
        CompareCommand compareSecondFirst = new CompareCommand(INDEX_SECOND_FOODPLACE, INDEX_FIRST_FOODPLACE);

        // same object -> returns true
        assertTrue(compareFirstSecond.equals(compareFirstSecond));

        // same values -> returns true
        assertTrue(compareFirstSecond.equals(compareFirstSecondCopy));

        // null -> returns false
        assertNotEquals(compareFirstSecond, null);

        // different type -> returns false
        assertNotEquals(compareFirstSecond, new ClearCommand());

        // different order of indexes -> returns false
        assertFalse(compareFirstSecond.equals(compareSecondFirst));
    }

    @Test
    public void hashcode() {
        CompareCommand compareFirstSecond = new CompareCommand(INDEX_FIRST_FOODPLACE, INDEX_SECOND_FOODPLACE);
        CompareCommand compareFirstSecondCopy = new CompareCommand(INDEX_FIRST_FOODPLACE, INDEX_SECOND_FOODPLACE);
        CompareCommand compareSecondFirst = new CompareCommand(INDEX_SECOND_FOODPLACE, INDEX_FIRST_FOODPLACE);

        // same object -> same hashcode
        assertEquals(compareFirstSecond.hashCode(), compareFirstSecond.hashCode());

        // same values -> same hashcode
        assertEquals(compareFirstSecond.hashCode(), compareFirstSecondCopy.hashCode());

        // different values -> different hashcode
        assertNotEquals(compareFirstSecond.hashCode(), compareSecondFirst.hashCode());
    }

    @Test
    public void toStringMethod() {
        CompareCommand compareCommand = new CompareCommand(INDEX_FIRST_FOODPLACE, INDEX_SECOND_FOODPLACE);
        String expected = CompareCommand.class.getCanonicalName()
                + "{firstIndex=" + INDEX_FIRST_FOODPLACE
                + ", secondIndex=" + INDEX_SECOND_FOODPLACE + "}";
        assertEquals(expected, compareCommand.toString());
    }
}
