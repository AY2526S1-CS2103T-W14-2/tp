package seedu.bitebuddy.logic.commands;

import seedu.bitebuddy.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Parameters:\n"
            + "  " + "[COMMAND] \n"
            + "Examples:\n"
            + "  " + COMMAND_WORD + "\n"
            + "  " + COMMAND_WORD + " find";

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    private final String usage;

    /**
     * Creates a HelpCommand to show the general help page.
     */
    public HelpCommand() {
        this.usage = null;
    }

    /**
     * Creates a HelpCommand that will display the provided usage string.
     * @param usage usage string to display for a specific command
     */
    public HelpCommand(String usage) {
        this.usage = usage;
    }

    @Override
    public CommandResult execute(Model model) {
        if (usage != null) {
            return new CommandResult(usage, false, false);
        }
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof HelpCommand)) {
            return false;
        }
        HelpCommand otherCommand = (HelpCommand) other;
        return usage != null ? usage.equals(otherCommand.usage) : otherCommand.usage == null;
    }

    @Override
    public int hashCode() {
        return usage != null ? usage.hashCode() : 0;
    }
}
