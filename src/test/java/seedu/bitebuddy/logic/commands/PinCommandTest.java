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

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.commons.core.index.Index;
import seedu.bitebuddy.logic.Messages;
import seedu.bitebuddy.model.Model;
import seedu.bitebuddy.model.ModelManager;
import seedu.bitebuddy.model.UserPrefs;
import seedu.bitebuddy.model.foodplace.Foodplace;
import seedu.bitebuddy.model.foodplace.Pinned;

public class PinCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private void setPinned(Foodplace foodplace, boolean pinned) {
        Foodplace updated = new Foodplace(
                foodplace.getName(), foodplace.getPhone(), foodplace.getEmail(), foodplace.getAddress(),
                foodplace.getTiming(), foodplace.getCuisine(), foodplace.getTags(), foodplace.getNote(),
                foodplace.getRate(), foodplace.getWishlist(), foodplace.getBlacklist(),
                new Pinned(pinned)
        );
        model.setFoodplace(foodplace, updated);
    }

    private Foodplace createPinned(Foodplace foodplace) {
        return new Foodplace(
                foodplace.getName(), foodplace.getPhone(), foodplace.getEmail(), foodplace.getAddress(),
                foodplace.getTiming(), foodplace.getCuisine(), foodplace.getTags(), foodplace.getNote(),
                foodplace.getRate(), foodplace.getWishlist(), foodplace.getBlacklist(),
                new Pinned(true)
        );
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Foodplace foodplace = model.getFilteredFoodplaceList().get(INDEX_FIRST_FOODPLACE.getZeroBased());
        PinCommand pinCommand = new PinCommand(INDEX_FIRST_FOODPLACE);
        Foodplace pinnedFoodplace = createPinned(foodplace);

        String expectedMessage = String.format(PinCommand.MESSAGE_PIN_FOODPLACE_SUCCESS,
                Messages.format(pinnedFoodplace));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setFoodplace(foodplace, pinnedFoodplace);
        expectedModel.updateFilteredFoodplaceList(Model.PREDICATE_SHOW_PINNED_FOODPLACES);

        assertCommandSuccess(pinCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFoodplaceList().size() + 1);
        PinCommand pinCommand = new PinCommand(outOfBoundIndex);
        assertCommandFailure(pinCommand, model, Messages.MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFoodplaceAtIndex(model, INDEX_FIRST_FOODPLACE);
        Foodplace foodplace = model.getFilteredFoodplaceList().get(INDEX_FIRST_FOODPLACE.getZeroBased());
        PinCommand pinCommand = new PinCommand(INDEX_FIRST_FOODPLACE);

        Foodplace pinnedFoodplace = createPinned(foodplace);
        String expectedMessage = String.format(PinCommand.MESSAGE_PIN_FOODPLACE_SUCCESS,
                Messages.format(pinnedFoodplace));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setFoodplace(foodplace, pinnedFoodplace);
        expectedModel.updateFilteredFoodplaceList(Model.PREDICATE_SHOW_PINNED_FOODPLACES);

        assertCommandSuccess(pinCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFoodplaceAtIndex(model, INDEX_FIRST_FOODPLACE);
        Index outOfBoundIndex = INDEX_SECOND_FOODPLACE;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getFoodplaceList().size());
        PinCommand pinCommand = new PinCommand(outOfBoundIndex);
        assertCommandFailure(pinCommand, model, Messages.MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_alreadyPinned_returnsAlreadyPinnedMessage() {
        Foodplace foodplace = model.getFilteredFoodplaceList().get(INDEX_FIRST_FOODPLACE.getZeroBased());
        setPinned(foodplace, true);

        PinCommand pinCommand = new PinCommand(INDEX_FIRST_FOODPLACE);
        assertCommandSuccess(pinCommand, model, PinCommand.MESSAGE_ALREADY_PINNED, model);
    }

    @Test
    public void execute_maxPinReached_returnsMaxPinMessage() {
        for (int i = 0; i < 5; i++) {
            Foodplace foodplace = model.getFilteredFoodplaceList().get(i);
            setPinned(foodplace, true);
        }
        PinCommand pinCommand = new PinCommand(INDEX_FIRST_FOODPLACE);
        assertCommandSuccess(pinCommand, model, PinCommand.MESSAGE_MAX_PIN_REACHED, model);
    }

    @Test
    public void equals() {
        PinCommand pinFirstCommand = new PinCommand(INDEX_FIRST_FOODPLACE);
        PinCommand pinSecondCommand = new PinCommand(INDEX_SECOND_FOODPLACE);

        assertTrue(pinFirstCommand.equals(pinFirstCommand));
        PinCommand pinFirstCommandCopy = new PinCommand(INDEX_FIRST_FOODPLACE);
        assertTrue(pinFirstCommand.equals(pinFirstCommandCopy));
        assertFalse(pinFirstCommand.equals(1));
        assertNotEquals(pinFirstCommand, null);
        assertFalse(pinFirstCommand.equals(pinSecondCommand));
    }

    @Test
    public void hashcode() {
        PinCommand pinFirstCommand = new PinCommand(INDEX_FIRST_FOODPLACE);
        PinCommand pinFirstCommandCopy = new PinCommand(INDEX_FIRST_FOODPLACE);
        PinCommand pinSecondCommand = new PinCommand(INDEX_SECOND_FOODPLACE);

        assertEquals(pinFirstCommand.hashCode(), pinFirstCommand.hashCode());
        assertEquals(pinFirstCommand.hashCode(), pinFirstCommandCopy.hashCode());
        assertNotEquals(pinFirstCommand.hashCode(), pinSecondCommand.hashCode());
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        PinCommand pinCommand = new PinCommand(targetIndex);
        String expected = PinCommand.class.getCanonicalName() + "{index=" + targetIndex + "}";
        assertEquals(expected, pinCommand.toString());
    }
}
