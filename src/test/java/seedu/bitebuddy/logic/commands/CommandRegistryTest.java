package seedu.bitebuddy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link CommandRegistry}.
 */
public class CommandRegistryTest {

    @Test
    public void getUsage_null_returnsEmpty() {
        Optional<String> res = CommandRegistry.getUsage(null);
        assertTrue(res.isEmpty(), "Expected empty Optional when passing null");
    }

    @Test
    public void getUsage_unknownOrBlank_returnsEmpty() {
        assertTrue(CommandRegistry.getUsage("unknowncommand").isEmpty());
        assertTrue(CommandRegistry.getUsage("").isEmpty());
        assertTrue(CommandRegistry.getUsage(" find").isEmpty());
    }

    @Test
    public void getUsage_knownCommands_returnExpectedUsage() {
        Optional<String> helpUsage = CommandRegistry.getUsage(HelpCommand.COMMAND_WORD);
        assertTrue(helpUsage.isPresent());
        assertEquals(HelpCommand.MESSAGE_USAGE, helpUsage.get());
        Optional<String> findUsage = CommandRegistry.getUsage(FindCommand.COMMAND_WORD);
        assertTrue(findUsage.isPresent());
        assertEquals(FindCommand.MESSAGE_USAGE, findUsage.get());

        Optional<String> addUsage = CommandRegistry.getUsage(AddCommand.COMMAND_WORD);
        assertTrue(addUsage.isPresent());
        assertEquals(AddCommand.MESSAGE_USAGE, addUsage.get());

        Optional<String> deleteUsage = CommandRegistry.getUsage(DeleteCommand.COMMAND_WORD);
        assertTrue(deleteUsage.isPresent());
        assertEquals(DeleteCommand.MESSAGE_USAGE, deleteUsage.get());

        Optional<String> editUsage = CommandRegistry.getUsage(EditCommand.COMMAND_WORD);
        assertTrue(editUsage.isPresent());
        assertEquals(EditCommand.MESSAGE_USAGE, editUsage.get());

        Optional<String> listUsage = CommandRegistry.getUsage(ListCommand.COMMAND_WORD);
        assertTrue(listUsage.isPresent());
        assertEquals(ListCommand.MESSAGE_USAGE, listUsage.get());

        Optional<String> exitUsage = CommandRegistry.getUsage(ExitCommand.COMMAND_WORD);
        assertTrue(exitUsage.isPresent());
        assertEquals(ExitCommand.MESSAGE_USAGE, exitUsage.get());

        Optional<String> clearUsage = CommandRegistry.getUsage(ClearCommand.COMMAND_WORD);
        assertTrue(clearUsage.isPresent());
        assertEquals(ClearCommand.MESSAGE_USAGE, clearUsage.get());

        Optional<String> noteUsage = CommandRegistry.getUsage(NoteCommand.COMMAND_WORD);
        assertTrue(noteUsage.isPresent());
        assertEquals(NoteCommand.MESSAGE_USAGE, noteUsage.get());

        Optional<String> tagUsage = CommandRegistry.getUsage(TagCommand.COMMAND_WORD);
        assertTrue(tagUsage.isPresent());
        assertEquals(TagCommand.MESSAGE_USAGE, tagUsage.get());

        Optional<String> rateUsage = CommandRegistry.getUsage(RateCommand.COMMAND_WORD);
        assertTrue(rateUsage.isPresent());
        assertEquals(RateCommand.MESSAGE_USAGE, rateUsage.get());
    }
}
