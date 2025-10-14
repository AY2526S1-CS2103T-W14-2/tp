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
    public void classLoading_staticInitializer_executed() throws Exception {
        // Force initialization of the class so the static block and class declaration are executed
        Class.forName("seedu.bitebuddy.logic.commands.CommandRegistry", true,
                    Thread.currentThread().getContextClassLoader());

        Optional<String> usage = CommandRegistry.getUsage(HelpCommand.COMMAND_WORD);
        assertTrue(usage.isPresent(), "CommandRegistry static init should have populated mappings");
        assertEquals(HelpCommand.MESSAGE_USAGE, usage.get());
    }

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
    public void getUsage_caseSensitive_returnsEmpty() {
        assertTrue(CommandRegistry.getUsage(FindCommand.COMMAND_WORD.toUpperCase()).isEmpty());
    }

    @Test
    public void getUsage_knownCommands_returnExpectedUsage() {
        String[][] pairs = new String[][]{
                {AddCommand.COMMAND_WORD, AddCommand.MESSAGE_USAGE},
                {DeleteCommand.COMMAND_WORD, DeleteCommand.MESSAGE_USAGE},
                {EditCommand.COMMAND_WORD, EditCommand.MESSAGE_USAGE},
                {FindCommand.COMMAND_WORD, FindCommand.MESSAGE_USAGE},
                {ListCommand.COMMAND_WORD, ListCommand.MESSAGE_USAGE},
                {ExitCommand.COMMAND_WORD, ExitCommand.MESSAGE_USAGE},
                {HelpCommand.COMMAND_WORD, HelpCommand.MESSAGE_USAGE},
                {ClearCommand.COMMAND_WORD, ClearCommand.MESSAGE_USAGE},
                {NoteCommand.COMMAND_WORD, NoteCommand.MESSAGE_USAGE},
                {TagCommand.COMMAND_WORD, TagCommand.MESSAGE_USAGE},
                {RateCommand.COMMAND_WORD, RateCommand.MESSAGE_USAGE}
        };

        for (String[] pair : pairs) {
            String cmdWord = pair[0];
            String expected = pair[1];
            Optional<String> got = CommandRegistry.getUsage(cmdWord);
            assertTrue(got.isPresent(), "Expected usage to be present for command: " + cmdWord);
            assertEquals(expected, got.get(), "Unexpected usage for command: " + cmdWord);
        }
    }
}
