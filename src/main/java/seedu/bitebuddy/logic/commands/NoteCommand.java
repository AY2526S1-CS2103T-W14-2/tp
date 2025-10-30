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
            + "by the index number used in the displayed foodplace list.\n"
            + "If NOTE is not supplied, the current note of the foodplace will be erased instead.\n"
            + "• INDEX must be a positive integer. NOTE has a 100 ASCII-character limit.\n"
            + "• Existing notes will be overwritten by the input.\n"
            + "Parameters:\n"
            + "  " + "INDEX [NOTE]\n"
            + "Examples:\n"
            + "  " + COMMAND_WORD + " 1 " + "Very good customer service\n"
            + "  " + COMMAND_WORD + " 3";

    public static final String MESSAGE_ADD_NOTE_SUCCESS = "Added notes to Foodplace: %1$s";
    public static final String MESSAGE_DELETE_NOTE_SUCCESS = "Removed notes from Foodplace: %1$s";
    public static final String MESSAGE_NO_NOTE_TO_REMOVE = "No notes to remove from specified Foodplace";

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
        if (note.value.isEmpty() && foodPlaceToEdit.getNote().value.isEmpty()) {
            throw new CommandException(MESSAGE_NO_NOTE_TO_REMOVE);
        }
        Foodplace editedFoodPlace = new Foodplace(foodPlaceToEdit.getName(), foodPlaceToEdit.getPhone(),
                foodPlaceToEdit.getEmail(), foodPlaceToEdit.getAddress(), foodPlaceToEdit.getTiming(),
                foodPlaceToEdit.getCuisine(), foodPlaceToEdit.getTags(), note, foodPlaceToEdit.getRate(),
                foodPlaceToEdit.getWishlist(), foodPlaceToEdit.getBlacklist(), foodPlaceToEdit.getPinned());

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
