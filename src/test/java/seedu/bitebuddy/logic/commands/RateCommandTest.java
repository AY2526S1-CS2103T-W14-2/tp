package seedu.bitebuddy.logic.commands;

import static seedu.bitebuddy.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.bitebuddy.testutil.TypicalFoodplace.getTypicalAddressBook;
import static seedu.bitebuddy.testutil.TypicalIndexes.INDEX_FIRST_FOODPLACE;

import org.junit.jupiter.api.Test;

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
    private static final String RATE_OUT_OF_RANGE_STUB = "1000";
    private static final String RATE_INVALID_STUB = "invalid";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addRate_success() {
        Foodplace firstPerson = model.getFilteredFoodplaceList().get(INDEX_FIRST_FOODPLACE.getZeroBased());
        Foodplace editedPerson = new FoodplaceBuilder(firstPerson).withRate(RATE_SUCCESS_STUB).build();

        RateCommand rateCommand = new RateCommand(INDEX_FIRST_FOODPLACE, editedPerson.getRate().getValue());

        String expectedMessage = String.format(RateCommand.MESSAGE_ADD_RATE_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFoodplace(firstPerson, editedPerson);

        assertCommandSuccess(rateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_resetRate_success() {
        Foodplace firstPerson = model.getFilteredFoodplaceList().get(INDEX_FIRST_FOODPLACE.getZeroBased());
        Foodplace editedPerson = new FoodplaceBuilder(firstPerson).withRate(RATE_DEFAULT_STUB).build();

        RateCommand rateCommand = new RateCommand(INDEX_FIRST_FOODPLACE, editedPerson.getRate().getValue());

        String expectedMessage = String.format(RateCommand.MESSAGE_DELETE_RATE_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFoodplace(firstPerson, editedPerson);

        assertCommandSuccess(rateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidRangeOfRate_failure() {
        RateCommand rateCommand = new RateCommand(INDEX_FIRST_FOODPLACE, Integer.valueOf(RATE_OUT_OF_RANGE_STUB));

        assertCommandFailure(rateCommand, model, Rate.MESSAGE_CONSTRAINTS);
    }
}
