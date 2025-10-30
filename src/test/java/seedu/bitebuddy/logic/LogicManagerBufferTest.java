package seedu.bitebuddy.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.bitebuddy.commons.exceptions.DataLoadingException;
import seedu.bitebuddy.model.ModelManager;
import seedu.bitebuddy.model.ReadOnlyAddressBook;
import seedu.bitebuddy.model.ReadOnlyUserPrefs;
import seedu.bitebuddy.model.UserPrefs;
import seedu.bitebuddy.storage.Storage;

/**
 * Unit tests for LogicManager command buffer retrieval.
 */
public class LogicManagerBufferTest {

    private LogicManager logicManager;

    @BeforeEach
    public void setUp() {
        // Ensure clean buffer state for each test
        CommandBuffer.clear();

        // Minimal storage stub: methods not used by retrieveCommandFromBuffer
        Storage storageStub = new Storage() {
            @Override
            public Path getUserPrefsFilePath() {
                return Path.of(".");
            }

            @Override
            public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
                return Optional.empty();
            }

            @Override
            public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
                // no-op
            }

            @Override
            public Path getAddressBookFilePath() {
                return Path.of(".");
            }

            @Override
            public Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException {
                return Optional.empty();
            }

            @Override
            public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataLoadingException {
                return Optional.empty();
            }

            @Override
            public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
                // no-op
            }

            @Override
            public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
                // no-op
            }
        };

        logicManager = new LogicManager(new ModelManager(), storageStub);
    }

    @Test
    public void retrieveCommandFromBuffer_emptyBuffer_returnsCurrentText() {
        String input = "typing now";
        String result = logicManager.retrieveCommandFromBuffer(true, input);
        assertEquals(input, result);
    }

    @Test
    public void retrieveCommandFromBuffer_prevThenNext_preservesPendingAndReturnsCorrectCommands() {
        // populate buffer with two commands
        CommandBuffer.push("cmd1");
        CommandBuffer.push("cmd2");

        // navigate up (previous) from head; pending should be set to current text
        String pending = "in progress text";
        String prev = logicManager.retrieveCommandFromBuffer(true, pending);
        // prev should be the most recently pushed command
        assertEquals("cmd2", prev);

        // navigate down (next) to return to head; should return pending text
        String next = logicManager.retrieveCommandFromBuffer(false, pending);
        assertEquals(pending, next);
    }
}
