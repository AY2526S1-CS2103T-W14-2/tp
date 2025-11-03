package seedu.bitebuddy.logic.commands;

import static seedu.bitebuddy.model.Model.PREDICATE_SHOW_ALL_BLACKLISTED_FOODPLACES;
import static seedu.bitebuddy.model.Model.PREDICATE_SHOW_ALL_FOODPLACES;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import seedu.bitebuddy.commons.core.LogsCenter;
import seedu.bitebuddy.commons.core.index.Index;
import seedu.bitebuddy.logic.Messages;
import seedu.bitebuddy.logic.commands.exceptions.CommandException;
import seedu.bitebuddy.model.Model;
import seedu.bitebuddy.model.foodplace.Foodplace;
import seedu.bitebuddy.model.foodplace.Wishlist;

/**
 * Changes the blacklist of an existing foodplace in the address book.
 */
public class BlacklistCommand extends Command {

    public static final String COMMAND_WORD = "blacklist";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Blacklists the foodplace identified "
            + "by the index number used in the displayed foodplace list.\n"
            + "If no INDEX is supplied, displays all blacklisted foodplaces instead.\n"
            + "• If INDEX is supplied, INDEX must be a positive integer.\n"
            + "• Blacklisting an already-blacklisted foodplace removes its blacklist status.\n"
            + "Parameters:\n"
            + "  " + "[INDEX]\n"
            + "Examples:\n"
            + "  " + COMMAND_WORD + " 1\n"
            + "  " + COMMAND_WORD;

    public static final String MESSAGE_DISPLAY_SUCCESS = "Listed all foodplaces that are blacklisted";
    public static final String MESSAGE_ADD_BLACKLIST_SUCCESS = "Add Foodplace to blacklist: %1$s";
    public static final String MESSAGE_REMOVE_BLACKLIST_SUCCESS = "Remove Foodplace from blacklist: %1$s";
    public static final String MESSAGE_REMOVE_WISHLIST_STATUS_SUCCESS = "Additionally removed"
            + " Foodplace from wishlist";

    // Used for debugging purposes only (fine level)
    private static final Logger logger = LogsCenter.getLogger(BlacklistCommand.class);

    private final Index index;

    /**
     * @param index of the foodplace in the filtered foodplace list to edit the blacklist
     */
    public BlacklistCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.fine("------------------------------ Executing BlacklistCommand");

        // No index specified --> Display blacklist
        if (index == null) {
            model.updateFilteredFoodplaceList(PREDICATE_SHOW_ALL_BLACKLISTED_FOODPLACES);
            logger.fine("Successfully executed BlacklistCommand (Display blacklist)");
            return new CommandResult(MESSAGE_DISPLAY_SUCCESS);
        }

        List<Foodplace> lastShownList = model.getFilteredFoodplaceList();

        // Check if index is valid
        if (index.getZeroBased() >= lastShownList.size()) {
            logger.fine(String.format("Error executing BlacklistCommand for index: %d; listSize: %d",
                    index.getOneBased(), lastShownList.size()));
            throw new CommandException(Messages.MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX);
        }

        Foodplace foodPlaceToEdit = lastShownList.get(index.getZeroBased());
        Wishlist foodPlaceWishlistState = foodPlaceToEdit.getWishlist();
        boolean isFoodPlaceToEditWishlisted = false;

        // If foodplace is already wishlisted --> change wishlist to false, so that there is no conflict
        if (foodPlaceWishlistState.isWishlisted()) {
            isFoodPlaceToEditWishlisted = true;
            foodPlaceWishlistState = foodPlaceWishlistState.getOpposite();
        }

        Foodplace editedFoodPlace = new Foodplace(foodPlaceToEdit.getName(), foodPlaceToEdit.getPhone(),
                foodPlaceToEdit.getEmail(), foodPlaceToEdit.getAddress(), foodPlaceToEdit.getTiming(),
                foodPlaceToEdit.getCuisine(), foodPlaceToEdit.getTags(), foodPlaceToEdit.getNote(),
                foodPlaceToEdit.getRate(), foodPlaceWishlistState, foodPlaceToEdit.getBlacklist().getOpposite(),
                foodPlaceToEdit.getPinned());

        assert !(editedFoodPlace.getBlacklist().isBlacklisted() && editedFoodPlace.getWishlist().isWishlisted())
                : "A foodplace should not be both blacklisted and wishlisted here.";
        model.setFoodplace(foodPlaceToEdit, editedFoodPlace);
        model.updateFilteredFoodplaceList(PREDICATE_SHOW_ALL_FOODPLACES);

        logger.fine(String.format("Successfully executed BlacklistCommand (Edited blacklist status to '%s')",
                editedFoodPlace.getBlacklist().isBlacklisted));
        return new CommandResult(generateSuccessMessage(editedFoodPlace, isFoodPlaceToEditWishlisted));
    }

    /**
     * Generates a command execution success message based on whether to display blacklist,
     * a foodplace is added to blacklist or a foodplace is removed from blacklist
     * {@code foodPlaceToEdit}.
     */
    private String generateSuccessMessage(Foodplace foodPlaceToEdit, boolean isWishlisted) {
        String message = foodPlaceToEdit.getBlacklist().isBlacklisted()
                ? MESSAGE_ADD_BLACKLIST_SUCCESS : MESSAGE_REMOVE_BLACKLIST_SUCCESS;
        if (isWishlisted) {
            message = message + "\n" + MESSAGE_REMOVE_WISHLIST_STATUS_SUCCESS;
        }
        return String.format(message, Messages.format(foodPlaceToEdit));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BlacklistCommand)) {
            return false;
        }

        BlacklistCommand e = (BlacklistCommand) other;
        return Objects.equals(index, e.index);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(index);
    }
}
