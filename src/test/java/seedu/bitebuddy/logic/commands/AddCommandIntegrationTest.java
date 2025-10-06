package seedu.bitebuddy.logic.commands;

import static seedu.bitebuddy.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.bitebuddy.testutil.TypicalFoodplace.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.bitebuddy.logic.Messages;
import seedu.bitebuddy.model.Model;
import seedu.bitebuddy.model.ModelManager;
import seedu.bitebuddy.model.UserPrefs;
import seedu.bitebuddy.model.foodplace.Foodplace;
import seedu.bitebuddy.testutil.FoodplaceBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newFoodplace_success() {
        Foodplace validFoodplace = new FoodplaceBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addFoodplace(validFoodplace);

        assertCommandSuccess(new AddCommand(validFoodplace), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validFoodplace)),
                expectedModel);
    }

    @Test
    public void execute_duplicateFoodplace_throwsCommandException() {
        Foodplace foodplaceInList = model.getAddressBook().getFoodplaceList().get(0);
        assertCommandFailure(new AddCommand(foodplaceInList), model,
                AddCommand.MESSAGE_DUPLICATE_FOODPLACE);
    }

}
