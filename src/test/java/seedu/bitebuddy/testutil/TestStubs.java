package seedu.bitebuddy.testutil;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import seedu.bitebuddy.commons.exceptions.DataLoadingException;
import seedu.bitebuddy.model.ReadOnlyAddressBook;
import seedu.bitebuddy.model.ReadOnlyUserPrefs;
import seedu.bitebuddy.model.UserPrefs;
import seedu.bitebuddy.storage.Storage;
import seedu.bitebuddy.ui.Ui;

/**
 * Small collection of reusable test stubs for unit tests.
 */
public final class TestStubs {

    private TestStubs() {}

    /**
     * A stub class for Storage.
     */
    public static class StorageStub implements Storage {
        private Optional<ReadOnlyAddressBook> addressBookOpt = Optional.empty();
        private Optional<UserPrefs> userPrefsOpt = Optional.empty();
        private boolean saveUserPrefsThrows = false;

        public StorageStub() {}

        /**
         * Sets the optional address book to return on readAddressBook.
         */
        public StorageStub withAddressBook(Optional<ReadOnlyAddressBook> ab) {
            this.addressBookOpt = ab;
            return this;
        }

        /**
         * Sets the optional user prefs to return on readUserPrefs.
         */
        public StorageStub withUserPrefs(Optional<UserPrefs> up) {
            this.userPrefsOpt = up;
            return this;
        }

        /**
         * Sets whether saveUserPrefs should throw an IOException.
         */
        public StorageStub withSaveUserPrefsThrows(boolean flag) {
            this.saveUserPrefsThrows = flag;
            return this;
        }

        @Override
        public Path getAddressBookFilePath() {
            return Paths.get("stub-addressbook.json");
        }

        @Override
        public Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException {
            return addressBookOpt;
        }

        @Override
        public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataLoadingException {
            return addressBookOpt;
        }

        @Override
        public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
            // no-op
        }

        @Override
        public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
            // no-op
        }

        @Override
        public Path getUserPrefsFilePath() {
            return Paths.get("stub-userprefs.json");
        }

        @Override
        public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
            return userPrefsOpt;
        }

        @Override
        public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
            if (saveUserPrefsThrows) {
                throw new IOException("stub save prefs failure");
            }
        }
    }

    /**
     * A stub class for Ui.
     */
    public static class UiStub implements Ui {
        private boolean started = false;
        private Path receivedStage = null;

        /**
         * Returns true if start() has been called.
         */
        public boolean isStarted() {
            return started;
        }

        /**
         * Returns the stage received in start().
         */
        public Path getReceivedStage() {
            return receivedStage;
        }

        @Override
        public void start(javafx.stage.Stage primaryStage) {
            started = true;
        }
    }

    /**
     * A stub class for UserPrefsStorage.
     */
    public static class UserPrefsStorageStub implements seedu.bitebuddy.storage.UserPrefsStorage {
        private boolean throwOnRead = false;
        private Path prefsPath = Paths.get("stub-userprefs.json");

        public UserPrefsStorageStub() {}

        /**
         * Sets whether readUserPrefs should throw a DataLoadingException.
         */
        public UserPrefsStorageStub withThrowOnRead(boolean flag) {
            this.throwOnRead = flag;
            return this;
        }

        @Override
        public Path getUserPrefsFilePath() {
            return prefsPath;
        }

        @Override
        public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
            if (throwOnRead) {
                throw new DataLoadingException(new Exception("stub prefs load failure"));
            }
            return Optional.of(new UserPrefs());
        }

        @Override
        public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
            // no-op
        }
    }
}
