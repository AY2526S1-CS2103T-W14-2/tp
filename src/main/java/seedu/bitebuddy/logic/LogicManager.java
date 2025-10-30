package seedu.bitebuddy.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.bitebuddy.commons.core.GuiSettings;
import seedu.bitebuddy.commons.core.LogsCenter;
import seedu.bitebuddy.logic.commands.Command;
import seedu.bitebuddy.logic.commands.CommandResult;
import seedu.bitebuddy.logic.commands.exceptions.CommandException;
import seedu.bitebuddy.logic.parser.AddressBookParser;
import seedu.bitebuddy.logic.parser.exceptions.ParseException;
import seedu.bitebuddy.model.Model;
import seedu.bitebuddy.model.ReadOnlyAddressBook;
import seedu.bitebuddy.model.foodplace.Foodplace;
import seedu.bitebuddy.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private static final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.log(Level.INFO, "----------------[USER COMMAND][{0}]", commandText);
        CommandBuffer.push(commandText);

        CommandResult commandResult;
        Command command = addressBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Foodplace> getFilteredFoodplaceList() {
        return model.getFilteredFoodplaceList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public String retrieveCommandFromBuffer(Boolean isPrev, String currentText) {
        // Check if to update pending command
        if (CommandBuffer.isHead()) {
            CommandBuffer.setPendingCommand(currentText);
        }

        // Fetch command buffer
        CommandBuffer current = CommandBuffer.getCurrent();

        // Set command box content
        if (current != null) {
            if (isPrev) {
                CommandBuffer.getPrev();
            } else {
                CommandBuffer.getNext();
            }
            return CommandBuffer.getCurrent().getCommand();
        } else {
            return currentText;
        }
    }
}
