package seedu.bitebuddy.logic.commands;

import static seedu.bitebuddy.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.bitebuddy.logic.Messages.MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX;

import java.util.List;

import seedu.bitebuddy.commons.core.index.Index;
import seedu.bitebuddy.logic.commands.exceptions.CommandException;
import seedu.bitebuddy.model.Model;
import seedu.bitebuddy.model.foodplace.Foodplace;
import seedu.bitebuddy.model.foodplace.Rate;

/**
 * Changes the rating of an existing foodplace in BiteBuddy.
 */
public class RateCommand extends Command {

    public static final String COMMAND_WORD = "rate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the rating of the foodplace identified "
            + "by the index number used in the last foodplace listing.\n"
            + "INDEX and RATING must be a positive integer. A RATING to be set must be between "
            + Rate.MIN + " and " + Rate.MAX
            + ". If RATING is 0, then the existing rating will be removed.\n"
            + "Any other input RATING will overwrite the existing rating.\n"
            + "Parameters: INDEX RATING\n"
            + "Example: " + COMMAND_WORD + " 5 " + "1";

    public static final String MESSAGE_ADD_RATE_SUCCESS = "Added rating to Foodplace: %1$s";
    public static final String MESSAGE_DELETE_RATE_SUCCESS = "Removed rating from Foodplace: %1$s";

    private final Index index;
    private final Rate rate;

    /**
     * @param index of the foodplace in the filtered foodplace list to edit the remark
     * @param rate of the foodplace to be updated to
     */
    public RateCommand(Index index, Rate rate) {
        requireAllNonNull(index, rate);

        this.index = index;
        this.rate = new Rate(rate.getValue());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Foodplace> lastShownList = model.getFilteredFoodplaceList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX);
        }

        Foodplace foodPlaceToEdit = lastShownList.get(index.getZeroBased());
        Foodplace editedFoodplace = new Foodplace(foodPlaceToEdit.getName(), foodPlaceToEdit.getPhone(),
                foodPlaceToEdit.getEmail(), foodPlaceToEdit.getAddress(), foodPlaceToEdit.getTiming(),
                foodPlaceToEdit.getCuisine(), foodPlaceToEdit.getTags(), foodPlaceToEdit.getNote(), rate,
                foodPlaceToEdit.getWishlist(), foodPlaceToEdit.getBlacklist(), foodPlaceToEdit.getPinned());

        model.setFoodplace(foodPlaceToEdit, editedFoodplace);
        model.updateFilteredFoodplaceList(Model.PREDICATE_SHOW_ALL_FOODPLACES);

        return new CommandResult(generateSuccessMessage(editedFoodplace));
    }

    /**
     * Generates a command execution success message based on whether the rate is added to or removed from
     * {@code foodplaceToEdit}.
     */
    private String generateSuccessMessage(Foodplace foodPlaceToEdit) {
        String message = rate.getValue().equals(Rate.DEFAULT) ? MESSAGE_DELETE_RATE_SUCCESS : MESSAGE_ADD_RATE_SUCCESS;
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

    @Override
    public int hashCode() {
        return index.hashCode() + rate.hashCode();
    }
}
