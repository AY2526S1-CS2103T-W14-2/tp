package seedu.bitebuddy.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.bitebuddy.commons.util.ToStringBuilder;
import seedu.bitebuddy.logic.Messages;
import seedu.bitebuddy.logic.commands.exceptions.CommandException;
import seedu.bitebuddy.model.Model;
import seedu.bitebuddy.model.foodplace.Foodplace;

/**
 * Adds a foodplace to BiteBuddy.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a foodplace to BiteBuddy. "
            + "Parameters: "
            + PREFIX_NAME + "FOODPLACE NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "McRonald's "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "ronald@mcronalds.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "fastfood "
            + PREFIX_TAG + "cheap ";

    public static final String MESSAGE_SUCCESS = "New foodplace added: %1$s";
    public static final String MESSAGE_DUPLICATE_FOODPLACE = "This foodplace already exists in BiteBuddy";

    private final Foodplace toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Foodplace}
     */
    public AddCommand(Foodplace foodplace) {
        requireNonNull(foodplace);
        toAdd = foodplace;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasFoodplace(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_FOODPLACE);
        }

        model.addFoodplace(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public int hashCode() {
        return toAdd.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
