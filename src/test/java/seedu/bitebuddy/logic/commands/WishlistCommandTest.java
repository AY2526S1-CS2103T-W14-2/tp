package seedu.bitebuddy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.showFoodplaceAtIndex;
import static seedu.bitebuddy.model.Model.PREDICATE_SHOW_ALL_FOODPLACES;
import static seedu.bitebuddy.model.Model.PREDICATE_SHOW_ALL_WISHLISTED_FOODPLACES;
import static seedu.bitebuddy.testutil.TypicalFoodplace.getTypicalAddressBook;
import static seedu.bitebuddy.testutil.TypicalIndexes.INDEX_FIRST_FOODPLACE;
import static seedu.bitebuddy.testutil.TypicalIndexes.INDEX_SECOND_FOODPLACE;

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.commons.core.index.Index;
import seedu.bitebuddy.logic.Messages;
import seedu.bitebuddy.model.AddressBook;
import seedu.bitebuddy.model.Model;
import seedu.bitebuddy.model.ModelManager;
import seedu.bitebuddy.model.UserPrefs;
import seedu.bitebuddy.model.foodplace.Foodplace;
import seedu.bitebuddy.testutil.FoodplaceBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for WishlistCommand.
 */
public class WishlistCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_displayWishlist_success() {
        WishlistCommand command = new WishlistCommand(null);

        String expectedMessage = WishlistCommand.MESSAGE_DISPLAY_SUCCESS;

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateFilteredFoodplaceList(PREDICATE_SHOW_ALL_WISHLISTED_FOODPLACES);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(model.getFilteredFoodplaceList(), expectedModel.getFilteredFoodplaceList());
    }

    @Test
    public void execute_addWishlistUnfilteredList_success() {
        // Assumes the first foodplace is NOT wishlisted by default
        Foodplace foodplaceToAdd = model.getFilteredFoodplaceList().get(INDEX_FIRST_FOODPLACE.getZeroBased());
        Foodplace editedFoodplace = new FoodplaceBuilder(foodplaceToAdd).withWishlist(true).build();

        WishlistCommand wishlistCommand = new WishlistCommand(INDEX_FIRST_FOODPLACE);

        String expectedMessage = String.format(WishlistCommand.MESSAGE_ADD_WISHLIST_SUCCESS, editedFoodplace);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFoodplace(foodplaceToAdd, editedFoodplace);

        assertCommandSuccess(wishlistCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeWishlistUnfilteredList_success() {
        // Assumes the second foodplace IS wishlisted by default
        Foodplace foodplaceToRemove = model.getFilteredFoodplaceList().get(INDEX_SECOND_FOODPLACE.getZeroBased());
        Foodplace editedFoodplace = new FoodplaceBuilder(foodplaceToRemove).withWishlist(false).build();

        WishlistCommand wishlistCommand = new WishlistCommand(INDEX_SECOND_FOODPLACE);

        String expectedMessage = String.format(WishlistCommand.MESSAGE_REMOVE_WISHLIST_SUCCESS, editedFoodplace);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFoodplace(foodplaceToRemove, editedFoodplace);

        assertCommandSuccess(wishlistCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addWishlistFilteredList_success() {
        showFoodplaceAtIndex(model, INDEX_FIRST_FOODPLACE);

        // Assumes the first foodplace is NOT wishlisted by default
        Foodplace foodplaceToAdd = model.getFilteredFoodplaceList().get(INDEX_FIRST_FOODPLACE.getZeroBased());
        Foodplace editedFoodplace = new FoodplaceBuilder(foodplaceToAdd).withWishlist(true).build();

        WishlistCommand wishlistCommand = new WishlistCommand(INDEX_FIRST_FOODPLACE);

        String expectedMessage = String.format(WishlistCommand.MESSAGE_ADD_WISHLIST_SUCCESS, editedFoodplace);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFoodplace(foodplaceToAdd, editedFoodplace);
        expectedModel.updateFilteredFoodplaceList(PREDICATE_SHOW_ALL_FOODPLACES); // Command resets filter

        assertCommandSuccess(wishlistCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidFoodplaceIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFoodplaceList().size() + 1);
        WishlistCommand wishlistCommand = new WishlistCommand(outOfBoundIndex);

        assertCommandFailure(wishlistCommand, model, Messages.MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidFoodplaceIndexFilteredList_failure() {
        showFoodplaceAtIndex(model, INDEX_FIRST_FOODPLACE);
        Index outOfBoundIndex = INDEX_SECOND_FOODPLACE;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getFoodplaceList().size());

        WishlistCommand wishlistCommand = new WishlistCommand(outOfBoundIndex);
        assertCommandFailure(wishlistCommand, model, Messages.MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final WishlistCommand standardCommand = new WishlistCommand(INDEX_FIRST_FOODPLACE);
        final WishlistCommand displayCommand = new WishlistCommand(null);

        // same values -> returns true
        WishlistCommand commandWithSameValues = new WishlistCommand(INDEX_FIRST_FOODPLACE);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertNotEquals(standardCommand, null);

        // different types -> returns false
        assertNotEquals(standardCommand, new ClearCommand());

        // different index -> returns false
        assertFalse(standardCommand.equals(new WishlistCommand(INDEX_SECOND_FOODPLACE)));

        // index vs null -> returns false
        assertFalse(standardCommand.equals(displayCommand));

        // null vs index -> returns false
        assertFalse(displayCommand.equals(standardCommand));

        // null vs null -> returns true
        assertTrue(displayCommand.equals(new WishlistCommand(null)));
    }

    @Test
    public void hashcode() {
        WishlistCommand command1 = new WishlistCommand(INDEX_FIRST_FOODPLACE);
        WishlistCommand command1Copy = new WishlistCommand(INDEX_FIRST_FOODPLACE);
        WishlistCommand command2 = new WishlistCommand(INDEX_SECOND_FOODPLACE);
        WishlistCommand displayCommand = new WishlistCommand(null);
        WishlistCommand displayCommandCopy = new WishlistCommand(null);

        // same index
        assertEquals(command1.hashCode(), command1Copy.hashCode());

        // different index
        assertNotEquals(command1.hashCode(), command2.hashCode());

        // null index
        assertEquals(displayCommand.hashCode(), displayCommandCopy.hashCode());

        // index vs null index
        assertNotEquals(command2.hashCode(), displayCommand.hashCode());
    }
}
