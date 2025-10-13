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

    static {
        Map<String, String> m = new HashMap<>();
        m.put(AddCommand.COMMAND_WORD, AddCommand.MESSAGE_USAGE);
        m.put(DeleteCommand.COMMAND_WORD, DeleteCommand.MESSAGE_USAGE);
        m.put(EditCommand.COMMAND_WORD, EditCommand.MESSAGE_USAGE);
        m.put(FindCommand.COMMAND_WORD, FindCommand.MESSAGE_USAGE);
        m.put(ListCommand.COMMAND_WORD, ListCommand.MESSAGE_USAGE);
        m.put(ExitCommand.COMMAND_WORD, ExitCommand.MESSAGE_USAGE);
        m.put(HelpCommand.COMMAND_WORD, HelpCommand.MESSAGE_USAGE);
        m.put(ClearCommand.COMMAND_WORD, ClearCommand.MESSAGE_USAGE);
        m.put(NoteCommand.COMMAND_WORD, NoteCommand.MESSAGE_USAGE);
        m.put(TagCommand.COMMAND_WORD, TagCommand.MESSAGE_USAGE);
        m.put(RateCommand.COMMAND_WORD, RateCommand.MESSAGE_USAGE);
        USAGE_MAP = Collections.unmodifiableMap(m);
    }

    public static Optional<String> getUsage(String commandWord) {
        if (commandWord == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(USAGE_MAP.get(commandWord));
    }
}
