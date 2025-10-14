package seedu.bitebuddy.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.bitebuddy.model.AddressBook;
import seedu.bitebuddy.model.Model;

/**
 * Clears the bitebuddy book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears the address book. "
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
