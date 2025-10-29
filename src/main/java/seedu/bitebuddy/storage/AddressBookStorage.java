package seedu.bitebuddy.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.bitebuddy.commons.exceptions.DataLoadingException;
import seedu.bitebuddy.model.ReadOnlyAddressBook;

/**
 * Represents a storage for {@link seedu.bitebuddy.model.AddressBook}.
 */
public interface AddressBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyAddressBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyAddressBook} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyAddressBook)
     */
    void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException;

    /**
     * Returns the list of auto-fix records collected during the last call to {@link ReadOnlyAddressBook}.
     * Implementations may return an empty list if no auto-fixes were applied.
     */
    java.util.List<AutoFixRecord> getLastAutoFixes();
}
