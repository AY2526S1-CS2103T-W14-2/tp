package seedu.bitebuddy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.DESC_MCRONALDS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.DESC_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_NAME_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_PHONE_SWENSAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_TAG_RESTAURANT;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.showFoodplaceAtIndex;
import static seedu.bitebuddy.testutil.TypicalFoodplace.getTypicalAddressBook;
import static seedu.bitebuddy.testutil.TypicalIndexes.INDEX_FIRST_FOODPLACE;
import static seedu.bitebuddy.testutil.TypicalIndexes.INDEX_SECOND_FOODPLACE;

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.commons.core.index.Index;
import seedu.bitebuddy.logic.Messages;
import seedu.bitebuddy.logic.commands.EditCommand.EditFoodplaceDescriptor;
import seedu.bitebuddy.model.AddressBook;
import seedu.bitebuddy.model.Model;
import seedu.bitebuddy.model.ModelManager;
import seedu.bitebuddy.model.UserPrefs;
import seedu.bitebuddy.model.foodplace.Foodplace;
import seedu.bitebuddy.testutil.EditFoodplaceDescriptorBuilder;
import seedu.bitebuddy.testutil.FoodplaceBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Foodplace editedFoodplace = new FoodplaceBuilder().build();
        EditFoodplaceDescriptor descriptor = new EditFoodplaceDescriptorBuilder(editedFoodplace).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_FOODPLACE, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_FOODPLACE_SUCCESS,
                Messages.format(editedFoodplace));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFoodplace(model.getFilteredFoodplaceList().get(0), editedFoodplace);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastFoodplace = Index.fromOneBased(model.getFilteredFoodplaceList().size());
        Foodplace lastFoodplace = model.getFilteredFoodplaceList().get(indexLastFoodplace.getZeroBased());

        FoodplaceBuilder foodplaceInList = new FoodplaceBuilder(lastFoodplace);
        Foodplace editedFoodplace = foodplaceInList.withName(VALID_NAME_SWENSWAN).withPhone(VALID_PHONE_SWENSAN)
                .withTags(VALID_TAG_RESTAURANT).build();

        EditCommand.EditFoodplaceDescriptor descriptor = new EditFoodplaceDescriptorBuilder()
                .withName(VALID_NAME_SWENSWAN).withPhone(VALID_PHONE_SWENSAN).withTags(VALID_TAG_RESTAURANT).build();
        EditCommand editCommand = new EditCommand(indexLastFoodplace, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_FOODPLACE_SUCCESS,
                Messages.format(editedFoodplace));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFoodplace(lastFoodplace, editedFoodplace);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_FOODPLACE, new EditFoodplaceDescriptor());
        Foodplace editedFoodplace = model.getFilteredFoodplaceList().get(INDEX_FIRST_FOODPLACE.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_FOODPLACE_SUCCESS,
                Messages.format(editedFoodplace));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showFoodplaceAtIndex(model, INDEX_FIRST_FOODPLACE);

        Foodplace foodplaceInFilteredList = model.getFilteredFoodplaceList().get(INDEX_FIRST_FOODPLACE.getZeroBased());
        Foodplace editedFoodplace = new FoodplaceBuilder(foodplaceInFilteredList).withName(VALID_NAME_SWENSWAN).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_FOODPLACE,
                new EditFoodplaceDescriptorBuilder().withName(VALID_NAME_SWENSWAN).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_FOODPLACE_SUCCESS,
                Messages.format(editedFoodplace));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFoodplace(model.getFilteredFoodplaceList().get(0), editedFoodplace);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateFoodplaceUnfilteredList_failure() {
        Foodplace firstFoodplace = model.getFilteredFoodplaceList().get(INDEX_FIRST_FOODPLACE.getZeroBased());
        EditFoodplaceDescriptor descriptor = new EditFoodplaceDescriptorBuilder(firstFoodplace).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_FOODPLACE, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_FOODPLACE);
    }

    @Test
    public void execute_duplicateFoodplaceFilteredList_failure() {
        showFoodplaceAtIndex(model, INDEX_FIRST_FOODPLACE);

        // edit foodplace in filtered list into a duplicate in bitebuddy book
        Foodplace foodplaceInList = model.getAddressBook().getFoodplaceList()
                .get(INDEX_SECOND_FOODPLACE.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_FOODPLACE,
                new EditFoodplaceDescriptorBuilder(foodplaceInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_FOODPLACE);
    }

    @Test
    public void execute_invalidFoodplaceIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFoodplaceList().size() + 1);
        EditFoodplaceDescriptor descriptor = new EditFoodplaceDescriptorBuilder().withName(VALID_NAME_SWENSWAN).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of bitebuddy book
     */
    @Test
    public void execute_invalidFoodplaceIndexFilteredList_failure() {
        showFoodplaceAtIndex(model, INDEX_FIRST_FOODPLACE);
        Index outOfBoundIndex = INDEX_SECOND_FOODPLACE;
        // ensures that outOfBoundIndex is still in bounds of bitebuddy book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getFoodplaceList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditFoodplaceDescriptorBuilder().withName(VALID_NAME_SWENSWAN).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_FOODPLACE, DESC_MCRONALDS);

        // same values -> returns true
        EditFoodplaceDescriptor copyDescriptor = new EditFoodplaceDescriptor(DESC_MCRONALDS);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_FOODPLACE, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_FOODPLACE, DESC_MCRONALDS)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_FOODPLACE, DESC_SWENSWAN)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditCommand.EditFoodplaceDescriptor editFoodplaceDescriptor = new EditCommand.EditFoodplaceDescriptor();
        EditCommand editCommand = new EditCommand(index, editFoodplaceDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editFoodplaceDescriptor="
                + editFoodplaceDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
