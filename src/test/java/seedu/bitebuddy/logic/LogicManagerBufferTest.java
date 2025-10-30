package seedu.bitebuddy.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.bitebuddy.model.ModelManager;
import seedu.bitebuddy.testutil.TestStubs;

/**
 * Unit tests for LogicManager command buffer retrieval.
 */
public class LogicManagerBufferTest {

    private LogicManager logicManager;

    @BeforeEach
    public void setUp() {
        // Ensure clean buffer state for each test
        CommandBuffer.clear();

        TestStubs.StorageStub storageStub = new TestStubs.StorageStub();

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
