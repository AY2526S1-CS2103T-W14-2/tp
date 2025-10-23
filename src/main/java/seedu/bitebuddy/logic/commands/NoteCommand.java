package seedu.bitebuddy.logic.commands;

import static seedu.bitebuddy.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.bitebuddy.model.Model.PREDICATE_SHOW_ALL_FOODPLACES;

import java.util.List;

import seedu.bitebuddy.commons.core.index.Index;
import seedu.bitebuddy.logic.Messages;
import seedu.bitebuddy.logic.commands.exceptions.CommandException;
import seedu.bitebuddy.model.Model;
import seedu.bitebuddy.model.foodplace.Foodplace;
import seedu.bitebuddy.model.foodplace.Note;

/**
 * Changes the note of an existing foodplace in BiteBuddy.
 */
public class NoteCommand extends Command {

    public static final String COMMAND_WORD = "note";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the notes of the foodplace identified "
            + "by the index number used in the last foodplace listing. "
            + "Existing notes will be overwritten by the input.\n"
            + "Empty notes erase the current note in foodplace.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "note [INDEX] [NOTE]\n"
            + "Example: " + COMMAND_WORD + " 1 " + "Very good customer service\n"
            + "Restrictions: 100 characters limit";

    public static final String MESSAGE_ADD_NOTE_SUCCESS = "Added notes to Foodplace: %1$s";
    public static final String MESSAGE_DELETE_NOTE_SUCCESS = "Removed notes from Foodplace: %1$s";

    private final Index index;
    private final Note note;

    /**
     * @param index of the foodplace in the filtered foodplace list to edit the note
     * @param note of the foodplace to be updated to
     */
    public NoteCommand(Index index, Note note) {
        requireAllNonNull(index, note);

        this.index = index;
        this.note = note;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Foodplace> lastShownList = model.getFilteredFoodplaceList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX);
        }

        Foodplace foodPlaceToEdit = lastShownList.get(index.getZeroBased());
        Foodplace editedFoodPlace = new Foodplace(foodPlaceToEdit.getName(), foodPlaceToEdit.getPhone(),
                foodPlaceToEdit.getEmail(), foodPlaceToEdit.getAddress(), foodPlaceToEdit.getCuisine(),
                foodPlaceToEdit.getTags(), note, foodPlaceToEdit.getRate(), foodPlaceToEdit.getWishlist());

        model.setFoodplace(foodPlaceToEdit, editedFoodPlace);
        model.updateFilteredFoodplaceList(PREDICATE_SHOW_ALL_FOODPLACES);

        return new CommandResult(generateSuccessMessage(editedFoodPlace));
    }

    /**
     * Generates a command execution success message based on whether the note is added to or removed from
     * {@code foodPlaceToEdit}.
     */
    private String generateSuccessMessage(Foodplace foodPlaceToEdit) {
        String message = !note.value.isEmpty() ? MESSAGE_ADD_NOTE_SUCCESS : MESSAGE_DELETE_NOTE_SUCCESS;
        return String.format(message, foodPlaceToEdit);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NoteCommand)) {
            return false;
        }

        NoteCommand e = (NoteCommand) other;
        return index.equals(e.index)
                && note.equals(e.note);
    }

    @Override
    public int hashCode() {
        return index.hashCode() + note.hashCode();
    }
}
