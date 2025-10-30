package seedu.bitebuddy.logic.commands;

import static seedu.bitebuddy.model.Model.PREDICATE_SHOW_ALL_FOODPLACES;
import static seedu.bitebuddy.model.Model.PREDICATE_SHOW_ALL_WISHLISTED_FOODPLACES;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import seedu.bitebuddy.commons.core.LogsCenter;
import seedu.bitebuddy.commons.core.index.Index;
import seedu.bitebuddy.logic.Messages;
import seedu.bitebuddy.logic.commands.exceptions.CommandException;
import seedu.bitebuddy.model.Model;
import seedu.bitebuddy.model.foodplace.Blacklist;
import seedu.bitebuddy.model.foodplace.Foodplace;

/**
 * Changes the wishlist of an existing foodplace in the address book.
 */
public class WishlistCommand extends Command {

    public static final String COMMAND_WORD = "wishlist";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Wishlists the foodplace identified "
            + "by the index number used in the displayed foodplace list.\n"
            + "If no INDEX is supplied, displays all wishlisted foodplaces instead.\n"
            + "• If INDEX is supplied, INDEX must be a positive integer.\n"
            + "• Wishlisting an already-wishlisted foodplace removes its wishlist status.\n"
            + "Parameters:\n"
            + "  " + "[INDEX]\n"
            + "Examples:\n"
            + "  " + COMMAND_WORD + " 1\n"
            + "  " + COMMAND_WORD;

    public static final String MESSAGE_DISPLAY_SUCCESS = "Listed all foodplaces that are wishlisted";
    public static final String MESSAGE_ADD_WISHLIST_SUCCESS = "Add Foodplace to wishlist: %1$s";
    public static final String MESSAGE_REMOVE_WISHLIST_SUCCESS = "Remove Foodplace from wishlist: %1$s";
    public static final String MESSAGE_REMOVE_BLACKLIST_STATUS_SUCCESS = "Additionally removed"
            + " Foodplace from blacklist";

    // Used for debugging purposes only (fine level)
    private static final Logger logger = LogsCenter.getLogger(WishlistCommand.class);

    private final Index index;

    /**
     * @param index of the foodplace in the filtered foodplace list to edit the wishlist
     */
    public WishlistCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.fine("------------------------------ Executing WishlistCommand");

        // No index specified --> Display wishlist
        if (index == null) {
            model.updateFilteredFoodplaceList(PREDICATE_SHOW_ALL_WISHLISTED_FOODPLACES);
            logger.fine("Successfully executed WishlistCommand (Display wishlist)");
            return new CommandResult(MESSAGE_DISPLAY_SUCCESS);
        }

        List<Foodplace> lastShownList = model.getFilteredFoodplaceList();

        // Check if index is valid
        if (index.getZeroBased() >= lastShownList.size()) {
            logger.fine(String.format("Error executing WishlistCommand for index: %d; listSize: %d",
                    index.getOneBased(), lastShownList.size()));
            throw new CommandException(Messages.MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX);
        }

        Foodplace foodPlaceToEdit = lastShownList.get(index.getZeroBased());
        Blacklist foodPlaceBlacklistState = foodPlaceToEdit.getBlacklist();
        boolean isFoodPlaceToEditBlacklisted = false;

        // If foodplace is already blacklisted --> change blacklist to false, so that there is no conflict
        if (foodPlaceBlacklistState.isBlacklisted()) {
            isFoodPlaceToEditBlacklisted = true;
            foodPlaceBlacklistState = foodPlaceBlacklistState.getOpposite();
        }

        Foodplace editedFoodPlace = new Foodplace(foodPlaceToEdit.getName(), foodPlaceToEdit.getPhone(),
                foodPlaceToEdit.getEmail(), foodPlaceToEdit.getAddress(), foodPlaceToEdit.getTiming(),
                foodPlaceToEdit.getCuisine(), foodPlaceToEdit.getTags(), foodPlaceToEdit.getNote(),
                foodPlaceToEdit.getRate(), foodPlaceToEdit.getWishlist().getOpposite(),
                foodPlaceBlacklistState, foodPlaceToEdit.getPinned());

        assert !(editedFoodPlace.getBlacklist().isBlacklisted && editedFoodPlace.getWishlist().isWishlisted)
                : "A foodplace should not be both blacklisted and wishlisted here.";
        model.setFoodplace(foodPlaceToEdit, editedFoodPlace);
        model.updateFilteredFoodplaceList(PREDICATE_SHOW_ALL_FOODPLACES);

        logger.fine(String.format("Successfully executed WishlistCommand (Edited wishlist status to '%s')",
                editedFoodPlace.getWishlist().isWishlisted));
        return new CommandResult(generateSuccessMessage(editedFoodPlace, isFoodPlaceToEditBlacklisted));
    }

    /**
     * Generates a command execution success message based on whether to display wishlist,
     * a foodplace is added to wishlist or a foodplace is removed from wishlist
     * {@code foodPlaceToEdit}.
     */
    private String generateSuccessMessage(Foodplace foodPlaceToEdit, boolean isBlacklisted) {
        String message = foodPlaceToEdit.getWishlist().isWishlisted()
                ? MESSAGE_ADD_WISHLIST_SUCCESS : MESSAGE_REMOVE_WISHLIST_SUCCESS;
        if (isBlacklisted) {
            message = message + "\n" + MESSAGE_REMOVE_BLACKLIST_STATUS_SUCCESS;
        }
        return String.format(message, foodPlaceToEdit);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof WishlistCommand)) {
            return false;
        }

        WishlistCommand e = (WishlistCommand) other;
        return Objects.equals(index, e.index);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(index);
    }
}
