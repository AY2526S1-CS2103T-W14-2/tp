package seedu.bitebuddy.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.bitebuddy.commons.core.GuiSettings;
import seedu.bitebuddy.logic.commands.CommandResult;
import seedu.bitebuddy.logic.commands.exceptions.CommandException;
import seedu.bitebuddy.logic.parser.exceptions.ParseException;
import seedu.bitebuddy.model.ReadOnlyAddressBook;
import seedu.bitebuddy.model.foodplace.Foodplace;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the AddressBook.
     *
     * @see seedu.bitebuddy.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of foodplaces */
    ObservableList<Foodplace> getFilteredFoodplaceList();

    /**
     * Returns the user prefs' bitebuddy book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
