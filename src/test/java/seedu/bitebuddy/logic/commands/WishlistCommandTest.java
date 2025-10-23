package seedu.bitebuddy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.showFoodplaceAtIndex;
import static seedu.bitebuddy.logic.commands.WishlistCommand.MESSAGE_REMOVE_BLACKLIST_STATUS_SUCCESS;
import static seedu.bitebuddy.model.Model.PREDICATE_SHOW_ALL_FOODPLACES;
import static seedu.bitebuddy.model.Model.PREDICATE_SHOW_ALL_WISHLISTED_FOODPLACES;
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

public class WishlistCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addWishlistUnfilteredList_success() {
        Foodplace foodplaceToEdit = model.getFilteredFoodplaceList().get(INDEX_THIRD_FOODPLACE.getZeroBased());
        Foodplace editedFoodplace = new FoodplaceBuilder(foodplaceToEdit).withWishlist(true).build();
        WishlistCommand command = new WishlistCommand(INDEX_THIRD_FOODPLACE);
        String expectedMessage = String.format(WishlistCommand.MESSAGE_ADD_WISHLIST_SUCCESS, editedFoodplace);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFoodplace(foodplaceToEdit, editedFoodplace);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addWishlistFilteredList_success() {
        showFoodplaceAtIndex(model, INDEX_THIRD_FOODPLACE); // Show CARLSHOP
        Foodplace foodplaceToEdit = model.getFilteredFoodplaceList().get(INDEX_FIRST_FOODPLACE.getZeroBased());
        Foodplace editedFoodplace = new FoodplaceBuilder(foodplaceToEdit).withWishlist(true).build();
        WishlistCommand command = new WishlistCommand(INDEX_FIRST_FOODPLACE); // Filtered index is 1
        String expectedMessage = String.format(WishlistCommand.MESSAGE_ADD_WISHLIST_SUCCESS, editedFoodplace);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFoodplace(foodplaceToEdit, editedFoodplace);
        expectedModel.updateFilteredFoodplaceList(PREDICATE_SHOW_ALL_FOODPLACES); // Command resets filter
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeWishlistWasWishlistedUnfilteredList_success() {
        Foodplace foodplaceToEdit = model.getFilteredFoodplaceList().get(INDEX_SECOND_FOODPLACE.getZeroBased());
        Foodplace editedFoodplace = new FoodplaceBuilder(foodplaceToEdit).withWishlist(false).build();
        WishlistCommand command = new WishlistCommand(INDEX_SECOND_FOODPLACE);
        String expectedMessage = String.format(WishlistCommand.MESSAGE_REMOVE_WISHLIST_SUCCESS, editedFoodplace);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFoodplace(foodplaceToEdit, editedFoodplace);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeWishlistWasWishlistedFilteredList_success() {
        showFoodplaceAtIndex(model, INDEX_SECOND_FOODPLACE); // Show DAEBAKSHOP
        Foodplace foodplaceToEdit = model.getFilteredFoodplaceList().get(INDEX_FIRST_FOODPLACE.getZeroBased());
        Foodplace editedFoodplace = new FoodplaceBuilder(foodplaceToEdit).withWishlist(false).build();
        WishlistCommand command = new WishlistCommand(INDEX_FIRST_FOODPLACE); // Filtered index is 1
        String expectedMessage = String.format(WishlistCommand.MESSAGE_REMOVE_WISHLIST_SUCCESS, editedFoodplace);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFoodplace(foodplaceToEdit, editedFoodplace);
        expectedModel.updateFilteredFoodplaceList(PREDICATE_SHOW_ALL_FOODPLACES); // Command resets filter
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addWishlistWasBlacklistedUnfilteredList_success() {
        Foodplace foodplaceToEdit = model.getFilteredFoodplaceList().get(INDEX_FIRST_FOODPLACE.getZeroBased());
        Foodplace editedFoodplace = new FoodplaceBuilder(foodplaceToEdit).withWishlist(true)
                .withBlacklist(false).build();
        WishlistCommand command = new WishlistCommand(INDEX_FIRST_FOODPLACE);
        String expectedMessage = String.format(WishlistCommand.MESSAGE_ADD_WISHLIST_SUCCESS, editedFoodplace)
                + "\n" + MESSAGE_REMOVE_BLACKLIST_STATUS_SUCCESS;
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFoodplace(foodplaceToEdit, editedFoodplace);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addWishlistWasBlacklistedFilteredList_success() {
        showFoodplaceAtIndex(model, INDEX_FIRST_FOODPLACE); // Show PRATASHOP
        Foodplace foodplaceToEdit = model.getFilteredFoodplaceList().get(INDEX_FIRST_FOODPLACE.getZeroBased());
        Foodplace editedFoodplace = new FoodplaceBuilder(foodplaceToEdit).withWishlist(true)
                .withBlacklist(false).build();
        WishlistCommand command = new WishlistCommand(INDEX_FIRST_FOODPLACE); // Filtered index is 1
        String expectedMessage = String.format(WishlistCommand.MESSAGE_ADD_WISHLIST_SUCCESS, editedFoodplace)
                + "\n" + MESSAGE_REMOVE_BLACKLIST_STATUS_SUCCESS;
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFoodplace(foodplaceToEdit, editedFoodplace);
        expectedModel.updateFilteredFoodplaceList(PREDICATE_SHOW_ALL_FOODPLACES); // Command resets filter
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_displayWishlist_success() {
        WishlistCommand command = new WishlistCommand(null);
        String expectedMessage = WishlistCommand.MESSAGE_DISPLAY_SUCCESS;
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateFilteredFoodplaceList(PREDICATE_SHOW_ALL_WISHLISTED_FOODPLACES);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFoodplaceList().size() + 1);
        WishlistCommand command = new WishlistCommand(outOfBoundIndex);
        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showFoodplaceAtIndex(model, INDEX_FIRST_FOODPLACE); // Filtered list size is 1
        Index outOfBoundIndex = INDEX_SECOND_FOODPLACE; // Index 2 is out of bounds for filtered list
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getFoodplaceList().size());
        WishlistCommand command = new WishlistCommand(outOfBoundIndex);
        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        WishlistCommand standardCommand = new WishlistCommand(INDEX_FIRST_FOODPLACE);
        WishlistCommand displayCommand = new WishlistCommand(null);

        assertTrue(standardCommand.equals(new WishlistCommand(INDEX_FIRST_FOODPLACE))); // same values
        assertTrue(standardCommand.equals(standardCommand)); // same object
        assertFalse(standardCommand.equals(null)); // null
        assertFalse(standardCommand.equals(new ClearCommand())); // different types
        assertFalse(standardCommand.equals(new WishlistCommand(INDEX_SECOND_FOODPLACE))); // different index
        assertFalse(standardCommand.equals(displayCommand)); // index vs null
    }

    @Test
    public void hashcode() {
        WishlistCommand command1 = new WishlistCommand(INDEX_FIRST_FOODPLACE);
        WishlistCommand command1Copy = new WishlistCommand(INDEX_FIRST_FOODPLACE);
        WishlistCommand command2 = new WishlistCommand(INDEX_SECOND_FOODPLACE);
        WishlistCommand displayCommand = new WishlistCommand(null);

        assertEquals(command1.hashCode(), command1Copy.hashCode());
        assertNotEquals(command1.hashCode(), command2.hashCode());
        assertNotEquals(command2.hashCode(), displayCommand.hashCode());
    }
}
