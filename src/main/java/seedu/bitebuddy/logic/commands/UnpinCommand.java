package seedu.bitebuddy.logic.commands;

import static seedu.bitebuddy.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.bitebuddy.model.Model.PREDICATE_SHOW_ALL_FOODPLACES;

import java.util.List;

import seedu.bitebuddy.commons.core.index.Index;
import seedu.bitebuddy.commons.util.ToStringBuilder;
import seedu.bitebuddy.logic.Messages;
import seedu.bitebuddy.logic.commands.exceptions.CommandException;
import seedu.bitebuddy.model.Model;
import seedu.bitebuddy.model.foodplace.Foodplace;
import seedu.bitebuddy.model.foodplace.Pinned;

/**
 * Unpins a foodplace in BiteBuddy.
 */
public class UnpinCommand extends Command {

    public static final String COMMAND_WORD = "unpin";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unpins the foodplace identified "
            + "by the index number used in the last foodplace list.\n"
            + "• INDEX must be a positive integer.\n"
            + "Parameters:\n"
            + "  " + "INDEX\n"
            + "Example:\n"
            + "  " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNPIN_FOODPLACE_SUCCESS = "Unpinned foodplace: %1$s";
    public static final String MESSAGE_NOT_PINNED = "This foodplace was not pinned";

    private final Index index;

    /**
     * @param index of the foodplace in the filtered foodplace list to unpin
     */
    public UnpinCommand(Index index) {
        requireAllNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Foodplace foodplaceToUnpin = getFoodplaceToUnpin(model);

        if (isAlreadyUnpinned(foodplaceToUnpin)) {
            return new CommandResult(MESSAGE_NOT_PINNED);
        }

        Foodplace unpinnedFoodplace = unpinFoodplace(foodplaceToUnpin);
        model.setFoodplace(foodplaceToUnpin, unpinnedFoodplace);
        model.updateFilteredFoodplaceList(PREDICATE_SHOW_ALL_FOODPLACES);

        return new CommandResult(String.format(MESSAGE_UNPIN_FOODPLACE_SUCCESS, Messages.format(unpinnedFoodplace)));
    }

    private Foodplace getFoodplaceToUnpin(Model model) throws CommandException {
        List<Foodplace> lastShownList = model.getFilteredFoodplaceList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX);
        }
        return lastShownList.get(index.getZeroBased());
    }

    private boolean isAlreadyUnpinned(Foodplace foodplace) {
        return !foodplace.getPinned().isPinned();
    }

    private static Foodplace unpinFoodplace(Foodplace fp) {
        assert fp != null;
        return new Foodplace(fp.getName(), fp.getPhone(), fp.getEmail(), fp.getAddress(), fp.getTiming(),
                fp.getCuisine(), fp.getTags(), fp.getNote(), fp.getRate(), fp.getWishlist(), fp.getBlacklist(),
                new Pinned(false));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnpinCommand)) {
            return false;
        }

        UnpinCommand otherUnpinCommand = (UnpinCommand) other;
        return index.equals(otherUnpinCommand.index);
    }

    @Override
    public int hashCode() {
        return index.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .toString();
    }
}
