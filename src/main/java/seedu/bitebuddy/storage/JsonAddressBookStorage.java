package seedu.bitebuddy.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.bitebuddy.commons.core.LogsCenter;
import seedu.bitebuddy.commons.exceptions.DataLoadingException;
import seedu.bitebuddy.commons.exceptions.IllegalValueException;
import seedu.bitebuddy.commons.util.FileUtil;
import seedu.bitebuddy.commons.util.JsonUtil;
import seedu.bitebuddy.model.ReadOnlyAddressBook;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonAddressBookStorage implements AddressBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAddressBookStorage.class);

    private final Path filePath;
    private java.util.List<AutoFixRecord> lastAutoFixes = new java.util.ArrayList<>();

    public JsonAddressBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getAddressBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException {
        return readAddressBook(filePath);
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableAddressBook> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableAddressBook.class);
        if (!jsonAddressBook.isPresent()) {
            return Optional.empty();
        }

        try {
            java.util.List<AutoFixRecord> fixes = new java.util.ArrayList<>();
            ReadOnlyAddressBook model = jsonAddressBook.get().toModelType(fixes);
            this.lastAutoFixes = fixes;
            return Optional.of(model);
        } catch (IllegalValueException ive) {
            logger.log(Level.INFO, "Illegal values found in {0}: {1}", new Object[] { filePath, ive.getMessage() });
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyAddressBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAddressBook(addressBook), filePath);
    }

    // Expose last auto-fix records for callers to display a summary if desired
    public java.util.List<AutoFixRecord> getLastAutoFixes() {
        return new java.util.ArrayList<>(lastAutoFixes);
    }

}
