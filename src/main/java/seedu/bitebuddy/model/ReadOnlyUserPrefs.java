package seedu.bitebuddy.model;

import java.nio.file.Path;

import seedu.bitebuddy.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getAddressBookFilePath();

}
