package seedu.bitebuddy.logic.commands;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Central registry mapping command words to their usage strings.
 */
public final class CommandRegistry {

    private static final Map<String, String> USAGE_MAP;

    private static final Class<?>[] COMMANDS = {
        AddCommand.class,
        ClearCommand.class,
        DeleteCommand.class,
        EditCommand.class,
        ExitCommand.class,
        FindCommand.class,
        HelpCommand.class,
        ListCommand.class,
        NoteCommand.class,
        TagCommand.class,
        RateCommand.class,
        PinCommand.class,
        UnpinCommand.class,
        WishlistCommand.class,
        BlacklistCommand.class,
        CompareCommand.class
    };

    static {
        Map<String, String> m = new HashMap<>();
        try {
            for (Class<?> command : COMMANDS) {
                Field cwField = command.getDeclaredField("COMMAND_WORD");
                Field muField = command.getDeclaredField("MESSAGE_USAGE");
                Object cwVal = cwField.get(null);
                Object muVal = muField.get(null);
                m.put(String.valueOf(cwVal), String.valueOf(muVal));
            }
        } catch (ReflectiveOperationException roe) {
            throw new ExceptionInInitializerError(roe);
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
