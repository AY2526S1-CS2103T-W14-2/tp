package seedu.bitebuddy.logic.commands;

import static seedu.bitebuddy.model.Model.PREDICATE_SHOW_ALL_FOODPLACES;
import static seedu.bitebuddy.model.Model.PREDICATE_SHOW_ALL_WISHLISTED_FOODPLACES;

import java.util.List;

import seedu.bitebuddy.commons.core.index.Index;
import seedu.bitebuddy.logic.Messages;
import seedu.bitebuddy.logic.commands.exceptions.CommandException;
import seedu.bitebuddy.model.Model;
import seedu.bitebuddy.model.foodplace.Foodplace;

/**
 * Changes the wishlist of an existing foodplace in the address book.
 */
public class WishlistCommand extends Command {

    public static final String COMMAND_WORD = "wishlist";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Wishlists the foodplace identified "
            + "by the index number used in the last foodplace listing. "
            + "Wishlisting a foodplace that is already wishlisted removes its wishlist status\n"
            + "Displays all foodplaces with wishlist if no index is specified\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "wishlist [INDEX] [NOTE]\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_DISPLAY_SUCCESS = "Listed all foodplaces that are wishlisted";
    public static final String MESSAGE_ADD_WISHLIST_SUCCESS = "Add Foodplace to wishlist: %1$s";
    public static final String MESSAGE_REMOVE_WISHLIST_SUCCESS = "Remove Foodplace from wishlist: %1$s";

    private final Index index;

    /**
     * @param index of the foodplace in the filtered foodplace list to edit the wishlist
     */
    public WishlistCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        // No index specified --> Display wishlist
        if (index == null) {
            model.updateFilteredFoodplaceList(PREDICATE_SHOW_ALL_WISHLISTED_FOODPLACES);
            return new CommandResult(MESSAGE_DISPLAY_SUCCESS);
        }

        // Modify wishlist status at specified index
        List<Foodplace> lastShownList = model.getFilteredFoodplaceList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX);
        }

        Foodplace foodPlaceToEdit = lastShownList.get(index.getZeroBased());
        Foodplace editedFoodPlace = new Foodplace(foodPlaceToEdit.getName(), foodPlaceToEdit.getPhone(),
                foodPlaceToEdit.getEmail(), foodPlaceToEdit.getAddress(), foodPlaceToEdit.getCuisine(),
                foodPlaceToEdit.getTags(), foodPlaceToEdit.getNote(), foodPlaceToEdit.getRate(),
                foodPlaceToEdit.getWishlist().getOpposite());

        model.setFoodplace(foodPlaceToEdit, editedFoodPlace);
        model.updateFilteredFoodplaceList(PREDICATE_SHOW_ALL_FOODPLACES);

        return new CommandResult(generateSuccessMessage(editedFoodPlace));
    }

    /**
     * Generates a command execution success message based on whether to display wishlist,
     * a foodplace is added to wishlist or a foodplace is removed from wishlist
     * {@code foodPlaceToEdit}.
     */
    private String generateSuccessMessage(Foodplace foodPlaceToEdit) {
        String message = foodPlaceToEdit.getWishlist().isWishlisted
                ? MESSAGE_ADD_WISHLIST_SUCCESS : MESSAGE_REMOVE_WISHLIST_SUCCESS;
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
        return index.equals(e.index);
    }
}
