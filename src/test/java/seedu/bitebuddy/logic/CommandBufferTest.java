package seedu.bitebuddy.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class CommandBufferTest {
    private static final String COMMAND_STUB = "THIS IS A COMMAND STUB";
    private static final String[] COMMAND_ARRAY_STUB = new String[] {
        "SAMPLE",
        "TEST COMMAND",
        "OTHER NEXT COMMAND",
        "THE FOLLOWING NEXT COMMAND",
        "ADD n/THIS e/COMMAND@email.com a/SINGAPORE r/STUB"
    };

    @BeforeEach
    public void setup() {
        CommandBuffer.clear();
    }

    @Test
    public void push_emptyBuffer_success() {
        CommandBuffer.push(COMMAND_STUB);
        assertEquals(1, CommandBuffer.getSize());
        // Read from top of valid commands
        CommandBuffer.getPrev();
        assertEquals(COMMAND_STUB, CommandBuffer.getCurrent().getCommand());
    }

    @Test
    public void push_nonEmptyBuffer_success() {
        for (String i : COMMAND_ARRAY_STUB) {
            CommandBuffer.push(i);
        }
        assertEquals(COMMAND_ARRAY_STUB.length, CommandBuffer.getSize());
        // Read from top of valid commands
        CommandBuffer.getPrev();
        assertEquals(COMMAND_ARRAY_STUB[COMMAND_ARRAY_STUB.length - 1], CommandBuffer.getCurrent().getCommand());
    }

    @Test
    public void getCommand_emptyBuffer_fail() {
        assertThrows(NullPointerException.class, () -> CommandBuffer.getCurrent().getCommand());
    }

    @Test
    public void getCommand_nonEmptyBuffer_success() {
        CommandBuffer.push(COMMAND_STUB);
        // Read from top of valid commands
        CommandBuffer.getPrev();
        assertEquals(COMMAND_STUB, CommandBuffer.getCurrent().getCommand());
    }

    @Test
    public void getCommand_isHeadHasPendingCommand_returnsPendingCommand() {
        CommandBuffer.push(COMMAND_ARRAY_STUB[0]);
        CommandBuffer.setPendingCommand(COMMAND_ARRAY_STUB[1]);
        assertEquals(COMMAND_ARRAY_STUB[1], CommandBuffer.getCurrent().getCommand());
    }

    @Test
    public void getCommand_isHeadNoPendingCommand_returnsPendingCommand() {
        CommandBuffer.push(COMMAND_ARRAY_STUB[0]);
        assertEquals("", CommandBuffer.getCurrent().getCommand());
    }

    @Test
    public void getNext_emptyBuffer_fail() {
        assertThrows(NullPointerException.class, CommandBuffer::getNext);
    }

    @Test
    public void getNext_nonEmptyBuffer_success() {
        for (String i : COMMAND_ARRAY_STUB) {
            CommandBuffer.push(i);
        }
        assertEquals(COMMAND_ARRAY_STUB.length, CommandBuffer.getSize());
        // Attempt to increment to next command
        CommandBuffer.getNext();
        // Read from top of valid commands
        CommandBuffer.getPrev();
        assertEquals(COMMAND_ARRAY_STUB[COMMAND_ARRAY_STUB.length - 1], CommandBuffer.getCurrent().getCommand());
    }

    @Test
    public void getNext_nonEmptyBufferNoNext_success() {
        CommandBuffer.push(COMMAND_STUB);
        CommandBuffer before = CommandBuffer.getCurrent();
        CommandBuffer.getNext();
        assertEquals(before, CommandBuffer.getCurrent());
    }

    @Test
    public void getNext_nonEmptyBufferBackAndForth_success() {
        for (String i : COMMAND_ARRAY_STUB) {
            CommandBuffer.push(i);
        }
        assertEquals(COMMAND_ARRAY_STUB.length, CommandBuffer.getSize());
        CommandBuffer before = CommandBuffer.getCurrent();
        CommandBuffer.getPrev();
        CommandBuffer.getNext();
        assertEquals(before, CommandBuffer.getCurrent());
    }

    @Test
    public void getPrev_emptyBuffer_fail() {
        assertThrows(NullPointerException.class, CommandBuffer::getPrev);
    }

    @Test
    public void getPrev_nonEmptyBuffer_success() {
        for (String i : COMMAND_ARRAY_STUB) {
            CommandBuffer.push(i);
        }
        assertEquals(COMMAND_ARRAY_STUB.length, CommandBuffer.getSize());
        for (String i : COMMAND_ARRAY_STUB) {
            CommandBuffer.getPrev();
        }
        assertEquals(COMMAND_ARRAY_STUB[0], CommandBuffer.getCurrent().getCommand());
    }

    @Test
    public void getPrev_nonEmptyBufferNoPrev_success() {
        CommandBuffer.push(COMMAND_STUB);
        // Read from top of valid commands
        CommandBuffer.getPrev();

        CommandBuffer before = CommandBuffer.getCurrent();
        // Attempt going back out of bounds
        CommandBuffer.getPrev();
        assertEquals(before, CommandBuffer.getCurrent());
    }

    @Test
    public void getCurrent_emptyBuffer_success() {
        assertNull(CommandBuffer.getCurrent());
    }

    @Test
    public void getCurrent_nonEmptyBuffer_success() {
        CommandBuffer.push(COMMAND_ARRAY_STUB[0]);
        CommandBuffer before = CommandBuffer.getCurrent();
        CommandBuffer.push(COMMAND_ARRAY_STUB[1]);
        assertNotEquals(before, CommandBuffer.getCurrent());
    }

    @Test
    public void clear_nonEmptyBuffer_success() {
        CommandBuffer.push(COMMAND_STUB);
        CommandBuffer.clear();
        assertEquals(0, CommandBuffer.getSize());
        assertNull(CommandBuffer.getCurrent());
    }

    @Test
    public void size_emptyBuffer_success() {
        assertEquals(0, CommandBuffer.getSize());
    }

    @Test
    public void size_nonEmptyBuffer_success() {
        for (String i : COMMAND_ARRAY_STUB) {
            CommandBuffer.push(i);
        }
        assertEquals(COMMAND_ARRAY_STUB.length, CommandBuffer.getSize());
    }

    @Test
    public void isHead_emptyBuffer_fail() {
        assertFalse(CommandBuffer.isHead());
    }

    @Test
    public void isHead_nonEmptyBuffer_success() {
        // Check if head is updated
        CommandBuffer.push(COMMAND_STUB);
        assertTrue(CommandBuffer.isHead());

        // Check if pointer is no longer at head
        CommandBuffer.getPrev();
        assertFalse(CommandBuffer.isHead());
    }

    @Test
    public void setPendingCommand_anyBuffer_success() {
        CommandBuffer.push(COMMAND_ARRAY_STUB[0]);
        CommandBuffer.getPrev();
        assertEquals(COMMAND_ARRAY_STUB[0], CommandBuffer.getCurrent().getCommand());
        CommandBuffer.push(COMMAND_ARRAY_STUB[1]);
        CommandBuffer.setPendingCommand(COMMAND_ARRAY_STUB[1]);
        assertEquals(COMMAND_ARRAY_STUB[1], CommandBuffer.getCurrent().getCommand());
    }
}
