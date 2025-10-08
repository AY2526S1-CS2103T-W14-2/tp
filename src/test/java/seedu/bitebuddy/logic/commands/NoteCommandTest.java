package seedu.bitebuddy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_NOTE_FAMOUS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_NOTE_SERVICE;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.showFoodplaceAtIndex;
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
import seedu.bitebuddy.model.foodplace.Note;
import seedu.bitebuddy.testutil.FoodplaceBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for noteCommand.
 */
public class NoteCommandTest {

    private static final String NOTE_STUB = "Some note";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addNoteUnfilteredList_success() {
        Foodplace firstFoodplace = model.getFilteredFoodplaceList().get(INDEX_FIRST_FOODPLACE.getZeroBased());
        Foodplace editedFoodplace = new FoodplaceBuilder(firstFoodplace).withNote(NOTE_STUB).build();

        NoteCommand noteCommand = new NoteCommand(INDEX_FIRST_FOODPLACE, new Note(editedFoodplace.getNote().value));

        String expectedMessage = String.format(NoteCommand.MESSAGE_ADD_NOTE_SUCCESS, editedFoodplace);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFoodplace(firstFoodplace, editedFoodplace);

        assertCommandSuccess(noteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deletenoteUnfilteredList_success() {
        Foodplace firstFoodplace = model.getFilteredFoodplaceList().get(INDEX_FIRST_FOODPLACE.getZeroBased());
        Foodplace editedFoodplace = new FoodplaceBuilder(firstFoodplace).withNote("").build();

        NoteCommand noteCommand = new NoteCommand(INDEX_FIRST_FOODPLACE,
                new Note(editedFoodplace.getNote().toString()));

        String expectedMessage = String.format(NoteCommand.MESSAGE_DELETE_NOTE_SUCCESS, editedFoodplace);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFoodplace(firstFoodplace, editedFoodplace);

        assertCommandSuccess(noteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showFoodplaceAtIndex(model, INDEX_FIRST_FOODPLACE);

        Foodplace firstFoodplace = model.getFilteredFoodplaceList().get(INDEX_FIRST_FOODPLACE.getZeroBased());
        Foodplace editedFoodplace = new FoodplaceBuilder(model.getFilteredFoodplaceList()
                .get(INDEX_FIRST_FOODPLACE.getZeroBased())).withNote(NOTE_STUB).build();

        NoteCommand noteCommand = new NoteCommand(INDEX_FIRST_FOODPLACE, new Note(editedFoodplace.getNote().value));

        String expectedMessage = String.format(NoteCommand.MESSAGE_ADD_NOTE_SUCCESS, editedFoodplace);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFoodplace(firstFoodplace, editedFoodplace);

        assertCommandSuccess(noteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidFoodplaceIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFoodplaceList().size() + 1);
        NoteCommand noteCommand = new NoteCommand(outOfBoundIndex, new Note(VALID_NOTE_FAMOUS));

        assertCommandFailure(noteCommand, model, Messages.MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX);
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

        NoteCommand noteCommand = new NoteCommand(outOfBoundIndex, new Note(VALID_NOTE_FAMOUS));
        assertCommandFailure(noteCommand, model, Messages.MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final NoteCommand standardCommand = new NoteCommand(INDEX_FIRST_FOODPLACE,
                new Note(VALID_NOTE_SERVICE));

        // same values -> returns true
        NoteCommand commandWithSameValues = new NoteCommand(INDEX_FIRST_FOODPLACE,
                new Note(VALID_NOTE_SERVICE));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new NoteCommand(INDEX_SECOND_FOODPLACE,
                new Note(VALID_NOTE_SERVICE))));

        // different note -> returns false
        assertFalse(standardCommand.equals(new NoteCommand(INDEX_FIRST_FOODPLACE,
                new Note(VALID_NOTE_FAMOUS))));
    }
}
