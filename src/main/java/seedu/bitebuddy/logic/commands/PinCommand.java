package seedu.bitebuddy.logic.commands;

import static seedu.bitebuddy.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.bitebuddy.model.Model.PREDICATE_SHOW_PINNED_FOODPLACES;

import java.util.List;

import seedu.bitebuddy.commons.core.index.Index;
import seedu.bitebuddy.commons.util.ToStringBuilder;
import seedu.bitebuddy.logic.Messages;
import seedu.bitebuddy.logic.commands.exceptions.CommandException;
import seedu.bitebuddy.model.Model;
import seedu.bitebuddy.model.foodplace.Foodplace;
import seedu.bitebuddy.model.foodplace.Pinned;

/**
 * Pins a foodplace in BiteBuddy.
 */
public class PinCommand extends Command {

    public static final String COMMAND_WORD = "pin";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Pins the foodplace identified "
            + "by the index number used in the last foodplace listing.\n"
            + "INDEX must be a positive integer.\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_PIN_FOODPLACE_SUCCESS = "Pinned foodplace: %1$s";
    public static final String MESSAGE_ALREADY_PINNED = "This foodplace is already pinned";
    public static final String MESSAGE_MAX_PIN_REACHED = "Maximum of 5 pins";

    private final Index index;

    /**
     * @param index of the foodplace in the filtered foodplace list to pin
     */
    public PinCommand(Index index) {
        requireAllNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Foodplace foodplaceToPin = getFoodplaceToPin(model);

        showPinnedFoodplaces(model);
        if (isMaxPinsReached(model)) {
            return new CommandResult(MESSAGE_MAX_PIN_REACHED);
        }

        if (isAlreadyPinned(foodplaceToPin)) {
            return new CommandResult(MESSAGE_ALREADY_PINNED);
        }

        Foodplace pinnedFoodplace = pinFoodplace(foodplaceToPin);
        model.setFoodplace(foodplaceToPin, pinnedFoodplace);

        showPinnedFoodplaces(model);
        return new CommandResult(String.format(MESSAGE_PIN_FOODPLACE_SUCCESS, Messages.format(pinnedFoodplace)));
    }

    private Foodplace getFoodplaceToPin(Model model) throws CommandException {
        List<Foodplace> lastShownList = model.getFilteredFoodplaceList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX);
        }
        return lastShownList.get(index.getZeroBased());
    }

    private boolean isMaxPinsReached(Model model) {
        return model.getFilteredFoodplaceList().size() >= 5;
    }

    private boolean isAlreadyPinned(Foodplace foodplace) {
        return foodplace.getPinned().isPinned();
    }

    private void showPinnedFoodplaces(Model model) {
        model.updateFilteredFoodplaceList(PREDICATE_SHOW_PINNED_FOODPLACES);
    }

    private static Foodplace pinFoodplace(Foodplace fp) {
        assert fp != null;
        return new Foodplace(fp.getName(), fp.getPhone(), fp.getEmail(), fp.getAddress(), fp.getTiming(),
                fp.getCuisine(), fp.getTags(), fp.getNote(), fp.getRate(), fp.getWishlist(), fp.getBlacklist(),
                new Pinned(true));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PinCommand)) {
            return false;
        }

        PinCommand otherPinCommand = (PinCommand) other;
        return index.equals(otherPinCommand.index);
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
