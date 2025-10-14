package seedu.bitebuddy.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.bitebuddy.commons.core.index.Index;
import seedu.bitebuddy.commons.util.ToStringBuilder;
import seedu.bitebuddy.logic.Messages;
import seedu.bitebuddy.logic.commands.exceptions.CommandException;
import seedu.bitebuddy.model.Model;
import seedu.bitebuddy.model.foodplace.Foodplace;

/**
 * Deletes a foodplace identified using it's displayed index from BiteBuddy.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the foodplace identified by the index number used in the displayed foodplace list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_FOODPLACE_SUCCESS = "Deleted Foodplace: %1$s";

    private final Index targetIndex;

    /**
     * Creates a DeleteCommand to delete the foodplace at the specified {@code Index}.
     * @param targetIndex index of the foodplace in the filtered foodplace list to delete
     */
    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Foodplace> lastShownList = model.getFilteredFoodplaceList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX);
        }

        Foodplace foodplaceToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteFoodplace(foodplaceToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_FOODPLACE_SUCCESS, Messages.format(foodplaceToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public int hashCode() {
        return targetIndex.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
