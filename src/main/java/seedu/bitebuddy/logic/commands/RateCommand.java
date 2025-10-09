package seedu.bitebuddy.logic.commands;

import static seedu.bitebuddy.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_RATE;

import java.util.List;

import seedu.bitebuddy.commons.core.index.Index;
import seedu.bitebuddy.logic.commands.exceptions.CommandException;
import seedu.bitebuddy.logic.Messages;
import seedu.bitebuddy.model.foodplace.Foodplace;
import seedu.bitebuddy.model.foodplace.Rate;
import seedu.bitebuddy.model.Model;

/**
 * Changes the remark of an existing foodplace in the address book.
 */
public class RateCommand extends Command {

    public static final String COMMAND_WORD = "rate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the rate of the foodplace identified "
            + "by the index number used in the last foodplace listing. "
            + "Existing rate will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_RATE + "[RATING (must be a positive integer)]\n"
            + "Example: " + COMMAND_WORD + " 5 " + PREFIX_RATE + "1";

    public static final String MESSAGE_ADD_RATE_SUCCESS = "Added rate to Foodplace: %1$s";
    public static final String MESSAGE_DELETE_RATE_SUCCESS = "Removed rate from Foodplace: %1$s";

    private final Index index;
    private final Rate rate;

    /**
     * @param index of the foodplace in the filtered foodplace list to edit the remark
     * @param rate  of the foodplace to be updated to
     */
    public RateCommand(Index index, Rate rate) {
        requireAllNonNull(index, rate);

        this.index = index;
        this.rate = rate;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Foodplace> lastShownList = model.getFilteredFoodplaceList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX);
        }

        Foodplace foodPlaceToEdit = lastShownList.get(index.getZeroBased());
        Foodplace editedFoodplace = new Foodplace(foodPlaceToEdit.getName(), foodPlaceToEdit.getPhone(), foodPlaceToEdit.getEmail(),
                foodPlaceToEdit.getAddress(), foodPlaceToEdit.getTags(), rate);

        model.setFoodplace(foodPlaceToEdit, editedFoodplace);
        model.updateFilteredFoodplaceList(Model.PREDICATE_SHOW_ALL_FOODPLACES);

        return new CommandResult(generateSuccessMessage(editedFoodplace));
    }

    /**
     * Generates a command execution success message based on whether the rate is added to or removed from
     * {@code foodplaceToEdit}.
     */
    private String generateSuccessMessage(Foodplace foodPlaceToEdit) {
        String message = rate.value.equals(Rate.DEFAULT) ? MESSAGE_DELETE_RATE_SUCCESS : MESSAGE_ADD_RATE_SUCCESS;
        return String.format(message, foodPlaceToEdit);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RateCommand)) {
            return false;
        }

        // state check
        RateCommand e = (RateCommand) other;
        return index.equals(e.index)
                && rate.equals(e.rate);
    }
}