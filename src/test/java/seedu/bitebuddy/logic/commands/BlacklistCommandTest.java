package seedu.bitebuddy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.logic.commands.BlacklistCommand.MESSAGE_REMOVE_WISHLIST_STATUS_SUCCESS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.showFoodplaceAtIndex;
import static seedu.bitebuddy.model.Model.PREDICATE_SHOW_ALL_BLACKLISTED_FOODPLACES;
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
import seedu.bitebuddy.testutil.FoodplaceBuilder;

public class BlacklistCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addBlacklistUnfilteredList_success() {
        Foodplace foodplaceToEdit = model.getFilteredFoodplaceList().get(INDEX_THIRD_FOODPLACE.getZeroBased());
        Foodplace editedFoodplace = new FoodplaceBuilder(foodplaceToEdit).withBlacklist(true).build();

        BlacklistCommand command = new BlacklistCommand(INDEX_THIRD_FOODPLACE);
        String expectedMessage = String.format(BlacklistCommand.MESSAGE_ADD_BLACKLIST_SUCCESS,
                Messages.format(editedFoodplace));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFoodplace(foodplaceToEdit, editedFoodplace);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addBlacklistFilteredList_success() {
        showFoodplaceAtIndex(model, INDEX_THIRD_FOODPLACE); // Show CARLSHOP
        Foodplace foodplaceToEdit = model.getFilteredFoodplaceList().get(INDEX_FIRST_FOODPLACE.getZeroBased());
        Foodplace editedFoodplace = new FoodplaceBuilder(foodplaceToEdit).withBlacklist(true).build();

        BlacklistCommand command = new BlacklistCommand(INDEX_FIRST_FOODPLACE);
        String expectedMessage = String.format(BlacklistCommand.MESSAGE_ADD_BLACKLIST_SUCCESS,
                Messages.format(editedFoodplace));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFoodplace(foodplaceToEdit, editedFoodplace);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeBlacklistUnfilteredList_success() {
        Foodplace foodplaceToEdit = model.getFilteredFoodplaceList().get(INDEX_FIRST_FOODPLACE.getZeroBased());
        Foodplace editedFoodplace = new FoodplaceBuilder(foodplaceToEdit).withBlacklist(false).build();

        BlacklistCommand command = new BlacklistCommand(INDEX_FIRST_FOODPLACE);
        String expectedMessage = String.format(BlacklistCommand.MESSAGE_REMOVE_BLACKLIST_SUCCESS,
                Messages.format(editedFoodplace));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFoodplace(foodplaceToEdit, editedFoodplace);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    // Not the most efficient since filteredList is tested for addBlacklist already, included for exhaustiveness
    @Test
    public void execute_removeBlacklistFilteredList_success() {
        showFoodplaceAtIndex(model, INDEX_FIRST_FOODPLACE); // Show PRATASHOP
        Foodplace foodplaceToEdit = model.getFilteredFoodplaceList().get(INDEX_FIRST_FOODPLACE.getZeroBased());
        Foodplace editedFoodplace = new FoodplaceBuilder(foodplaceToEdit).withBlacklist(false).build();

        BlacklistCommand command = new BlacklistCommand(INDEX_FIRST_FOODPLACE);
        String expectedMessage = String.format(BlacklistCommand.MESSAGE_REMOVE_BLACKLIST_SUCCESS,
                Messages.format(editedFoodplace));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFoodplace(foodplaceToEdit, editedFoodplace);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addBlacklistWasWishlistedUnfilteredList_success() {
        Foodplace foodplaceToEdit = model.getFilteredFoodplaceList().get(INDEX_SECOND_FOODPLACE.getZeroBased());
        Foodplace editedFoodplace = new FoodplaceBuilder(foodplaceToEdit).withWishlist(false)
                .withBlacklist(true).build();

        BlacklistCommand command = new BlacklistCommand(INDEX_SECOND_FOODPLACE);
        String expectedMessage = String.format(BlacklistCommand.MESSAGE_ADD_BLACKLIST_SUCCESS,
                Messages.format(editedFoodplace))
                + "\n" + MESSAGE_REMOVE_WISHLIST_STATUS_SUCCESS;
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFoodplace(foodplaceToEdit, editedFoodplace);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    // Not the most efficient since filteredList is tested for addBlacklist already, included for exhaustiveness
    @Test
    public void execute_addBlacklistWasWishlistedFilteredList_success() {
        showFoodplaceAtIndex(model, INDEX_SECOND_FOODPLACE); // Show DAEBAKSHOP
        Foodplace foodplaceToEdit = model.getFilteredFoodplaceList().get(INDEX_FIRST_FOODPLACE.getZeroBased());
        Foodplace editedFoodplace = new FoodplaceBuilder(foodplaceToEdit).withWishlist(false)
                .withBlacklist(true).build();

        BlacklistCommand command = new BlacklistCommand(INDEX_FIRST_FOODPLACE);
        String expectedMessage = String.format(BlacklistCommand.MESSAGE_ADD_BLACKLIST_SUCCESS,
                Messages.format(editedFoodplace))
                + "\n" + MESSAGE_REMOVE_WISHLIST_STATUS_SUCCESS;
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFoodplace(foodplaceToEdit, editedFoodplace);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_displayBlacklist_success() {
        BlacklistCommand command = new BlacklistCommand(null);
        String expectedMessage = BlacklistCommand.MESSAGE_DISPLAY_SUCCESS;
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateFilteredFoodplaceList(PREDICATE_SHOW_ALL_BLACKLISTED_FOODPLACES);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFoodplaceList().size() + 1);
        BlacklistCommand command = new BlacklistCommand(outOfBoundIndex);
        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showFoodplaceAtIndex(model, INDEX_FIRST_FOODPLACE); // Filtered list now has size 1
        Index outOfBoundIndex = INDEX_SECOND_FOODPLACE;
        BlacklistCommand command = new BlacklistCommand(outOfBoundIndex);

        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getFoodplaceList().size());
        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        BlacklistCommand standardCommand = new BlacklistCommand(INDEX_FIRST_FOODPLACE);
        BlacklistCommand displayCommand = new BlacklistCommand(null);

        assertTrue(standardCommand.equals(new BlacklistCommand(INDEX_FIRST_FOODPLACE))); // same values
        assertTrue(standardCommand.equals(standardCommand)); // same object
        assertNotEquals(standardCommand, null); // null
        assertNotEquals(standardCommand, new ClearCommand()); // different types
        assertFalse(standardCommand.equals(new BlacklistCommand(INDEX_SECOND_FOODPLACE))); // different index
        assertFalse(standardCommand.equals(displayCommand)); // index vs null
    }

    @Test
    public void hashcode() {
        BlacklistCommand command1 = new BlacklistCommand(INDEX_FIRST_FOODPLACE);
        BlacklistCommand command1Copy = new BlacklistCommand(INDEX_FIRST_FOODPLACE);
        BlacklistCommand command2 = new BlacklistCommand(INDEX_SECOND_FOODPLACE);
        BlacklistCommand displayCommand = new BlacklistCommand(null);

        assertEquals(command1.hashCode(), command1Copy.hashCode());
        assertNotEquals(command1.hashCode(), command2.hashCode());
        assertNotEquals(command2.hashCode(), displayCommand.hashCode());
    }
}
