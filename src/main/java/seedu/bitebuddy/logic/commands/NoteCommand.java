package seedu.bitebuddy.logic.commands;

import static seedu.bitebuddy.commons.util.CollectionUtil.requireAllNonNull;
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
import seedu.bitebuddy.model.foodplace.Note;

/**
 * Changes the note of an existing foodplace in BiteBuddy.
 */
public class NoteCommand extends Command {

    public static final String COMMAND_WORD = "note";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the notes of the foodplace identified "
            + "by the index number used in the displayed foodplace list.\n"
            + "If NOTE is not supplied, the current note of the foodplace will be erased instead.\n"
            + "• INDEX must be a positive integer. NOTE has a 100 ASCII-character limit. "
            + "Refer to http://facweb.cs.depaul.edu/sjost/it212/documents/ascii-pr.htm for accepted characters\n"
            + "• Existing notes will be overwritten by the input.\n"
            + "Parameters:\n"
            + "  " + "INDEX [NOTE]\n"
            + "Examples:\n"
            + "  " + COMMAND_WORD + " 1 " + "Very good customer service\n"
            + "  " + COMMAND_WORD + " 3";

    public static final String MESSAGE_ADD_NOTE_SUCCESS = "Added notes to Foodplace: %1$s\n%2$s";
    public static final String MESSAGE_DELETE_NOTE_SUCCESS = "Removed notes from Foodplace: %1$s\n%2$s";
    // Set as success messages but no changes will be made to the address book
    public static final String MESSAGE_NO_NOTE_TO_REMOVE_SUCCESS = "No notes to remove from specified Foodplace\n%s";
    public static final String MESSAGE_SAME_NOTE_SUCCESS = "The new note is the same as the current note\n%s";

    // Used for debugging purposes only (fine level)
    private static final Logger logger = LogsCenter.getLogger(NoteCommand.class);

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
        logger.fine("------------------------------ Executing NoteCommand");
        List<Foodplace> lastShownList = model.getFilteredFoodplaceList();

        // Check if index is valid
        if (index.getZeroBased() >= lastShownList.size()) {
            logger.fine(String.format("Error executing NoteCommand for index: %d; listSize: %d",
                    index.getOneBased(), lastShownList.size()));
            throw new CommandException(Messages.MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX);
        }

        Foodplace foodPlaceToEdit = lastShownList.get(index.getZeroBased());
        String noteToEdit = foodPlaceToEdit.getNote().value;

        // If no existing note to remove --> return with no change made
        if (note.value.isEmpty() && noteToEdit.isEmpty()) {
            logger.fine("Successfully executed NoteCommand (No existing note to remove)");
            return new CommandResult(String.format(MESSAGE_NO_NOTE_TO_REMOVE_SUCCESS,
                    generateNoteChangeMessage(noteToEdit, note.value)));
        }
        // If new note is the same as existing note --> return with no change made
        if (note.value.equals(noteToEdit)) {
            logger.fine(String.format("Successfully executed NoteCommand (Same existing note: '%s')", noteToEdit));
            return new CommandResult(String.format(MESSAGE_SAME_NOTE_SUCCESS,
                    generateNoteChangeMessage(noteToEdit, note.value)));
        }

        Foodplace editedFoodPlace = new Foodplace(foodPlaceToEdit.getName(), foodPlaceToEdit.getPhone(),
                foodPlaceToEdit.getEmail(), foodPlaceToEdit.getAddress(), foodPlaceToEdit.getTiming(),
                foodPlaceToEdit.getCuisine(), foodPlaceToEdit.getTags(), note, foodPlaceToEdit.getRate(),
                foodPlaceToEdit.getWishlist(), foodPlaceToEdit.getBlacklist(), foodPlaceToEdit.getPinned());

        model.setFoodplace(foodPlaceToEdit, editedFoodPlace);
        model.updateFilteredFoodplaceList(PREDICATE_SHOW_ALL_FOODPLACES);
        logger.fine(String.format("Successfully executed NoteCommand (Edited existing note from:'%s' to: '%s')",
                foodPlaceToEdit.getNote().value, editedFoodPlace.getNote().value));
        return new CommandResult(generateSuccessMessage(editedFoodPlace, noteToEdit, note.value));
    }

    /**
     * Generates a command execution success message based on whether the note is added to or removed from
     * {@code foodPlaceToEdit}.
     */
    private String generateSuccessMessage(Foodplace foodPlaceToEdit, String noteBefore, String noteAfter) {
        String message = !noteAfter.isEmpty() ? MESSAGE_ADD_NOTE_SUCCESS : MESSAGE_DELETE_NOTE_SUCCESS;
        return String.format(message, foodPlaceToEdit, generateNoteChangeMessage(noteBefore, noteAfter));
    }

    private String generateNoteChangeMessage(String noteBefore, String noteAfter) {
        if (Objects.equals(noteBefore, noteAfter)) {
            return "No changes were made.";
        } else {
            String formattedNoteBefore = noteBefore.isEmpty() ? "<Empty note>" : "\"" + noteBefore + "\"";
            String formattedNoteAfter = noteAfter.isEmpty() ? "<Empty note>" : "\"" + noteAfter + "\"";
            return String.format("Note changed from %s to %s.", formattedNoteBefore, formattedNoteAfter);
        }
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
