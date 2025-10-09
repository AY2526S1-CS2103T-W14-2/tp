package seedu.bitebuddy.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.bitebuddy.model.Model.PREDICATE_SHOW_ALL_FOODPLACES;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.bitebuddy.commons.core.index.Index;
import seedu.bitebuddy.logic.Messages;
import seedu.bitebuddy.logic.commands.exceptions.CommandException;
import seedu.bitebuddy.model.Model;
import seedu.bitebuddy.model.foodplace.Foodplace;
import seedu.bitebuddy.model.tag.Tag;

/**
 * Adds new tags to an existing food place in the address book.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds new tag(s) to the foodplace identified "
            + "by the index number used in the displayed foodplace list. "
            + "Existing tags will be preserved, and duplicate tags (case-insensitive) will be ignored.\n"
            + "Note: Tags cannot contain spaces.\n"
            + "Parameters: INDEX (must be a positive integer) TAG1 [TAG2]...\n"
            + "Example: " + COMMAND_WORD + " 3 FastFood Expensive";


    public static final String MESSAGE_SUCCESS = "Updated tags for Foodplace: %1$s";
    public static final String MESSAGE_INVALID_INDEX = "The index is out of range!";

    private final Index index;
    private final Set<Tag> newTags;

    /**
     * @param index of the foodplace in the foodplace list to add new tags
     * @param newTags of the foodplace to be added to
     */
    public TagCommand(Index index, Set<Tag> newTags) {
        requireNonNull(index);
        requireNonNull(newTags);
        this.index = index;
        this.newTags = newTags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Foodplace> lastShownList = model.getFilteredFoodplaceList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        Foodplace foodplaceToEdit = lastShownList.get(index.getZeroBased());

        // Combine existing + new tags
        Set<Tag> updatedTags = new HashSet<>(foodplaceToEdit.getTags());
        updatedTags.addAll(newTags);

        // Create a new Foodplace with updated tags, keeping all other fields the same
        Foodplace updatedFoodplace = new Foodplace(
                foodplaceToEdit.getName(),
                foodplaceToEdit.getPhone(),
                foodplaceToEdit.getEmail(),
                foodplaceToEdit.getAddress(),
                updatedTags,
                foodplaceToEdit.getNote()
        );

        model.setFoodplace(foodplaceToEdit, updatedFoodplace);
        model.updateFilteredFoodplaceList(PREDICATE_SHOW_ALL_FOODPLACES);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(updatedFoodplace)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TagCommand)) {
            return false;
        }

        TagCommand otherCommand = (TagCommand) other;
        return index.equals(otherCommand.index)
                && newTags.equals(otherCommand.newTags);
    }
}
