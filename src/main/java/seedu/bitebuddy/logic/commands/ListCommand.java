package seedu.bitebuddy.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.bitebuddy.model.Model.PREDICATE_SHOW_ALL_FOODPLACES;

import seedu.bitebuddy.model.Model;

/**
 * Lists all foodplaces in the bitebuddy book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all foodplaces in the bitebuddy book. "
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Listed all foodplaces";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredFoodplaceList(PREDICATE_SHOW_ALL_FOODPLACES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
