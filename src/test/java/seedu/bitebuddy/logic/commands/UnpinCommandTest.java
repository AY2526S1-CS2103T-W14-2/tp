package seedu.bitebuddy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.showFoodplaceAtIndex;
import static seedu.bitebuddy.testutil.TypicalFoodplace.getTypicalAddressBook;
import static seedu.bitebuddy.testutil.TypicalIndexes.INDEX_FIRST_FOODPLACE;
import static seedu.bitebuddy.testutil.TypicalIndexes.INDEX_SECOND_FOODPLACE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.bitebuddy.commons.core.index.Index;
import seedu.bitebuddy.logic.Messages;
import seedu.bitebuddy.model.Model;
import seedu.bitebuddy.model.ModelManager;
import seedu.bitebuddy.model.UserPrefs;
import seedu.bitebuddy.model.foodplace.Foodplace;
import seedu.bitebuddy.model.foodplace.Pinned;

public class UnpinCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private void pinFoodplace(Foodplace foodplace) {
        Foodplace pinnedFoodplace = new Foodplace(foodplace.getName(), foodplace.getPhone(),
                foodplace.getEmail(), foodplace.getAddress(), foodplace.getTiming(), foodplace.getCuisine(), foodplace.getTags(),
                foodplace.getNote(), foodplace.getRate(), foodplace.getWishlist(), new Pinned(true));
        model.setFoodplace(foodplace, pinnedFoodplace);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Foodplace foodplaceToUnpin = model.getFilteredFoodplaceList().get(INDEX_FIRST_FOODPLACE.getZeroBased());
        pinFoodplace(foodplaceToUnpin);

        UnpinCommand unpinCommand = new UnpinCommand(INDEX_FIRST_FOODPLACE);

        Foodplace unpinnedFoodplace = new Foodplace(foodplaceToUnpin.getName(), foodplaceToUnpin.getPhone(),
                foodplaceToUnpin.getEmail(), foodplaceToUnpin.getAddress(), foodplaceToUnpin.getTiming(), foodplaceToUnpin.getCuisine(), foodplaceToUnpin.getTags(),
                foodplaceToUnpin.getNote(), foodplaceToUnpin.getRate(), foodplaceToUnpin.getWishlist(), new Pinned(false));

        String expectedMessage = String.format(UnpinCommand.MESSAGE_UNPIN_FOODPLACE_SUCCESS,
                Messages.format(unpinnedFoodplace));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setFoodplace(foodplaceToUnpin, unpinnedFoodplace);
        expectedModel.updateFilteredFoodplaceList(Model.PREDICATE_SHOW_PINNED_FOODPLACES);

        assertCommandSuccess(unpinCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFoodplaceList().size() + 1);
        UnpinCommand unpinCommand = new UnpinCommand(outOfBoundIndex);
        assertCommandFailure(unpinCommand, model, Messages.MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        Foodplace original = model.getFilteredFoodplaceList().get(INDEX_FIRST_FOODPLACE.getZeroBased());
        pinFoodplace(original);
        showFoodplaceAtIndex(model, INDEX_FIRST_FOODPLACE);

        Foodplace foodplaceToUnpin = model.getFilteredFoodplaceList().get(INDEX_FIRST_FOODPLACE.getZeroBased());
        UnpinCommand unpinCommand = new UnpinCommand(INDEX_FIRST_FOODPLACE);

        Foodplace unpinnedFoodplace = new Foodplace(foodplaceToUnpin.getName(), foodplaceToUnpin.getPhone(),
                foodplaceToUnpin.getEmail(), foodplaceToUnpin.getAddress(), foodplaceToUnpin.getTiming(), foodplaceToUnpin.getCuisine(), foodplaceToUnpin.getTags(),
                foodplaceToUnpin.getNote(), foodplaceToUnpin.getRate(), foodplaceToUnpin.getWishlist(), new Pinned(false));

        String expectedMessage = String.format(UnpinCommand.MESSAGE_UNPIN_FOODPLACE_SUCCESS,
                Messages.format(unpinnedFoodplace));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setFoodplace(foodplaceToUnpin, unpinnedFoodplace);
        expectedModel.updateFilteredFoodplaceList(Model.PREDICATE_SHOW_PINNED_FOODPLACES);

        assertCommandSuccess(unpinCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFoodplaceAtIndex(model, INDEX_FIRST_FOODPLACE);
        Index outOfBoundIndex = INDEX_SECOND_FOODPLACE;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getFoodplaceList().size());
        UnpinCommand unpinCommand = new UnpinCommand(outOfBoundIndex);
        assertCommandFailure(unpinCommand, model, Messages.MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_notPinned_returnsNotPinnedMessage() {
        Foodplace foodplace = model.getFilteredFoodplaceList().get(INDEX_FIRST_FOODPLACE.getZeroBased());
        UnpinCommand unpinCommand = new UnpinCommand(INDEX_FIRST_FOODPLACE);
        assertCommandSuccess(unpinCommand, model, UnpinCommand.MESSAGE_NOT_PINNED, model);
    }

    @Test
    public void equals() {
        UnpinCommand unpinFirstCommand = new UnpinCommand(INDEX_FIRST_FOODPLACE);
        UnpinCommand unpinSecondCommand = new UnpinCommand(INDEX_SECOND_FOODPLACE);

        assertTrue(unpinFirstCommand.equals(unpinFirstCommand));
        UnpinCommand unpinFirstCommandCopy = new UnpinCommand(INDEX_FIRST_FOODPLACE);
        assertTrue(unpinFirstCommand.equals(unpinFirstCommandCopy));
        assertFalse(unpinFirstCommand.equals(1));
        assertNotEquals(unpinFirstCommand, null);
        assertFalse(unpinFirstCommand.equals(unpinSecondCommand));
    }

    @Test
    public void hashcode() {
        UnpinCommand unpinFirstCommand = new UnpinCommand(INDEX_FIRST_FOODPLACE);
        UnpinCommand unpinFirstCommandCopy = new UnpinCommand(INDEX_FIRST_FOODPLACE);
        UnpinCommand unpinSecondCommand = new UnpinCommand(INDEX_SECOND_FOODPLACE);

        assertEquals(unpinFirstCommand.hashCode(), unpinFirstCommand.hashCode());
        assertEquals(unpinFirstCommand.hashCode(), unpinFirstCommandCopy.hashCode());
        assertNotEquals(unpinFirstCommand.hashCode(), unpinSecondCommand.hashCode());
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        UnpinCommand unpinCommand = new UnpinCommand(targetIndex);
        String expected = UnpinCommand.class.getCanonicalName() + "{index=" + targetIndex + "}";
        assertEquals(expected, unpinCommand.toString());
    }
}
