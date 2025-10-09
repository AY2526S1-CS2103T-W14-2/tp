package seedu.bitebuddy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_RATE_AMY;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_RATE_BOB;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.showFoodplaceAtIndex;
import static seedu.bitebuddy.testutil.TypicalIndexes.INDEX_FIRST_FOODPLACE;
import static seedu.bitebuddy.testutil.TypicalIndexes.INDEX_SECOND_FOODPLACE;
import static seedu.bitebuddy.testutil.TypicalFoodplace.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.commons.core.index.Index;
import seedu.bitebuddy.logic.Messages;
import seedu.bitebuddy.model.AddressBook;
import seedu.bitebuddy.model.Model;
import seedu.bitebuddy.model.ModelManager;
import seedu.bitebuddy.model.UserPrefs;
import seedu.bitebuddy.model.foodplace.Foodplace;
import seedu.bitebuddy.model.foodplace.Rate;
import seedu.bitebuddy.testutil.FoodplaceBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RateCommand.
 * Stubbed for now.
 */
public class RateCommandTest {

//    private static final String RATE_STUB = "Some rate";
//
//    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
//
//    @Test
//    public void execute_addRateUnfilteredList_success() {
//        Foodplace firstPerson = model.getFilteredFoodplaceList().get(INDEX_FIRST_FOODPLACE.getZeroBased());
//        Foodplace editedPerson = new FoodplaceBuilder(firstPerson).withRate(RATE_STUB).build();
//
//        RateCommand rateCommand = new RateCommand(INDEX_FIRST_FOODPLACE, new Rate(editedPerson.getRate().value));
//
//        String expectedMessage = String.format(RateCommand.MESSAGE_ADD_RATE_SUCCESS, editedPerson);
//
//        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
//        expectedModel.setFoodplace(firstPerson, editedPerson);
//
//        assertCommandSuccess(rateCommand, model, expectedMessage, expectedModel);
//    }
//
//    @Test
//    public void execute_deleteRateUnfilteredList_success() {
//        Foodplace firstPerson = model.getFilteredFoodplaceList().get(INDEX_FIRST_FOODPLACE.getZeroBased());
//        Foodplace editedFoodplace = new FoodplaceBuilder(firstPerson).withRate("").build();
//
//        RateCommand remarkCommand = new RateCommand(INDEX_FIRST_FOODPLACE,
//                new Rate(editedFoodplace.getRate().value));
//
//        String expectedMessage = String.format(RateCommand.MESSAGE_DELETE_RATE_SUCCESS, editedFoodplace);
//
//        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
//        expectedModel.setFoodplace(firstPerson, editedFoodplace);
//
//        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
//    }
//
//    @Test
//    public void execute_filteredList_success() {
//        showFoodplaceAtIndex(model, INDEX_FIRST_FOODPLACE);
//
//        Foodplace firstPerson = model.getFilteredFoodplaceList().get(INDEX_FIRST_FOODPLACE.getZeroBased());
//        Foodplace editedPerson = new FoodplaceBuilder(model.getFilteredFoodplaceList().get(INDEX_FIRST_FOODPLACE.getZeroBased()))
//                .withRate(RATE_STUB).build();
//
//        RateCommand remarkCommand = new RateCommand(INDEX_FIRST_FOODPLACE, new Rate(editedPerson.getRate().value));
//
//        String expectedMessage = String.format(RateCommand.MESSAGE_ADD_RATE_SUCCESS, editedPerson);
//
//        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
//        expectedModel.setFoodplace(firstPerson, editedPerson);
//
//        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
//    }
//
//    @Test
//    public void execute_invalidPersonIndexUnfilteredList_failure() {
//        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFoodplaceList().size() + 1);
//        RateCommand remarkCommand = new RateCommand(outOfBoundIndex, new Rate(Integer.valueOf(VALID_RATE_BOB)));
//
//        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX);
//    }
//
//    /**
//     * Edit filtered list where index is larger than size of filtered list,
//     * but smaller than size of address book
//     */
//    @Test
//    public void execute_invalidPersonIndexFilteredList_failure() {
//        showFoodplaceAtIndex(model, INDEX_FIRST_FOODPLACE);
//        Index outOfBoundIndex = INDEX_SECOND_FOODPLACE;
//        // ensures that outOfBoundIndex is still in bounds of address book list
//        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getFoodplaceList().size());
//
//        RateCommand remarkCommand = new RateCommand(outOfBoundIndex, new Rate(Integer.valueOf(VALID_RATE_BOB)));
//
//        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX);
//    }
//
//    @Test
//    public void equals() {
//        final RateCommand standardCommand = new RateCommand(INDEX_FIRST_FOODPLACE,
//                new Rate(Integer.valueOf(VALID_RATE_AMY)));
//
//        // same values -> returns true
//        RateCommand commandWithSameValues = new RateCommand(INDEX_FIRST_FOODPLACE,
//                new Rate(Integer.valueOf(VALID_RATE_AMY)));
//        assertTrue(standardCommand.equals(commandWithSameValues));
//
//        // same object -> returns true
//        assertTrue(standardCommand.equals(standardCommand));
//
//        // null -> returns false
//        assertFalse(standardCommand.equals(null));
//
//        // different types -> returns false
//        assertFalse(standardCommand.equals(new ClearCommand()));
//
//        // different index -> returns false
//        assertFalse(standardCommand.equals(new RateCommand(INDEX_SECOND_FOODPLACE,
//                new Rate(Integer.valueOf(VALID_RATE_AMY)))));
//
//        // different rate -> returns false
//        assertFalse(standardCommand.equals(new RateCommand(INDEX_FIRST_FOODPLACE,
//                new Rate(Integer.valueOf(VALID_RATE_BOB)))));
//    }
}
