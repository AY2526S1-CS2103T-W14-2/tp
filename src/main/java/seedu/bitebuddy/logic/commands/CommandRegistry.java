package seedu.bitebuddy.logic.commands;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Central registry mapping command words to their usage strings.
 */
public final class CommandRegistry {

    private static final Map<String, String> USAGE_MAP;
    
    private static final String[][] COMMANDS = {
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

    static {
        Map<String, String> m = new HashMap<>();
        for (String[] command : COMMANDS) {
            m.put(command[0], command[1]);
        }
        USAGE_MAP = Collections.unmodifiableMap(m);
    }

    public static Optional<String> getUsage(String commandWord) {
        if (commandWord == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(USAGE_MAP.get(commandWord));
    }
}
