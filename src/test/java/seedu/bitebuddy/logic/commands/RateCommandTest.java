package seedu.bitebuddy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.bitebuddy.logic.Messages.MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.bitebuddy.testutil.TypicalFoodplace.getTypicalAddressBook;
import static seedu.bitebuddy.testutil.TypicalIndexes.INDEX_FIRST_FOODPLACE;
import static seedu.bitebuddy.testutil.TypicalIndexes.INDEX_SECOND_FOODPLACE;

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.commons.core.index.Index;
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

    private static final String RATE_SUCCESS_STUB = "5";
    private static final String RATE_DEFAULT_STUB = "0";

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addRate_success() {
        Foodplace firstPerson = model.getFilteredFoodplaceList().get(INDEX_FIRST_FOODPLACE.getZeroBased());
        Foodplace editedPerson = new FoodplaceBuilder(firstPerson).withRate(RATE_SUCCESS_STUB).build();

        RateCommand rateCommand = new RateCommand(INDEX_FIRST_FOODPLACE, editedPerson.getRate());

        String expectedMessage = String.format(RateCommand.MESSAGE_ADD_RATE_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFoodplace(firstPerson, editedPerson);

        assertCommandSuccess(rateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_resetRate_success() {
        Foodplace firstPerson = model.getFilteredFoodplaceList().get(INDEX_FIRST_FOODPLACE.getZeroBased());
        Foodplace editedPerson = new FoodplaceBuilder(firstPerson).withRate(RATE_DEFAULT_STUB).build();

        RateCommand rateCommand = new RateCommand(INDEX_FIRST_FOODPLACE, editedPerson.getRate());

        String expectedMessage = String.format(RateCommand.MESSAGE_DELETE_RATE_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFoodplace(firstPerson, editedPerson);

        assertCommandSuccess(rateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_failure() {
        Rate rate = new Rate(Integer.valueOf(RATE_SUCCESS_STUB));
        RateCommand rateCommand = new RateCommand(Index.fromOneBased(1000), rate);

        assertCommandFailure(rateCommand, model, MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX);
    }

    @Test
    public void equals_duplicate_true() throws Exception {
        RateCommand rateCommand1 = new RateCommand(INDEX_FIRST_FOODPLACE, new Rate(Integer.valueOf(RATE_SUCCESS_STUB)));
        RateCommand rateCommand2 = new RateCommand(INDEX_FIRST_FOODPLACE, new Rate(Integer.valueOf(RATE_SUCCESS_STUB)));

        assertEquals(rateCommand1, rateCommand1);
        assertEquals(rateCommand1, rateCommand2);
    }

    @Test
    public void equals_different_false() throws Exception {
        RateCommand rateCommand1 = new RateCommand(INDEX_FIRST_FOODPLACE, new Rate(Integer.valueOf(RATE_SUCCESS_STUB)));
        RateCommand rateCommand2 = new RateCommand(INDEX_SECOND_FOODPLACE,
                new Rate(Integer.valueOf(RATE_SUCCESS_STUB)));
        RateCommand rateCommand3 = new RateCommand(INDEX_FIRST_FOODPLACE, new Rate(Integer.valueOf(RATE_DEFAULT_STUB)));

        assertNotEquals(rateCommand1, "invalidRateCommandObject");
        assertNotEquals(rateCommand1, null);
        assertNotEquals(rateCommand2, rateCommand1);
        assertNotEquals(rateCommand3, rateCommand1);
    }

    @Test
    public void hashcode() {
        RateCommand rateCommand1 = new RateCommand(INDEX_FIRST_FOODPLACE, new Rate(Integer.valueOf(RATE_SUCCESS_STUB)));
        RateCommand rateCommand2 = new RateCommand(INDEX_FIRST_FOODPLACE, new Rate(Integer.valueOf(RATE_SUCCESS_STUB)));
        RateCommand rateCommand3 = new RateCommand(INDEX_SECOND_FOODPLACE,
                new Rate(Integer.valueOf(RATE_SUCCESS_STUB)));
        assertEquals(rateCommand1.hashCode(), rateCommand2.hashCode());
        assertNotEquals(rateCommand1.hashCode(), rateCommand3.hashCode());
    }
}
