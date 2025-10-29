package seedu.bitebuddy.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.bitebuddy.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.commons.core.GuiSettings;

public class UserPrefsTest {

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefs userPref = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setAddressBookFilePath(null));
    }

    @Test
    public void equals() {
        UserPrefs userPrefs = new UserPrefs();
        // same values -> returns true
        UserPrefs anotherUserPrefs = new UserPrefs();
        assertEquals(userPrefs, anotherUserPrefs);

        // same object -> returns true
        assertEquals(userPrefs, userPrefs);

        // null -> returns false
        assertNotEquals(userPrefs, null);

        // different types -> returns false
        assertNotEquals(userPrefs, 5);

        // different addressBookFilePath -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(java.nio.file.Paths.get("different/file/path"));
        assertNotEquals(userPrefs, differentUserPrefs);

        // different guiSettings -> returns false
        differentUserPrefs = new UserPrefs();
        differentUserPrefs.setGuiSettings(new GuiSettings(2, 2, 2, 2));
        assertNotEquals(userPrefs, differentUserPrefs);
    }

}
