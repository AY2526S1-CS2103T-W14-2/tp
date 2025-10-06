package seedu.bitebuddy.logic.commands;

import static seedu.bitebuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.showFoodplaceAtIndex;
import static seedu.bitebuddy.testutil.TypicalFoodplace.getTypicalAddressBook;
import static seedu.bitebuddy.testutil.TypicalIndexes.INDEX_FIRST_FOODPLACE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.bitebuddy.model.Model;
import seedu.bitebuddy.model.ModelManager;
import seedu.bitebuddy.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showFoodplaceAtIndex(model, INDEX_FIRST_FOODPLACE);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
