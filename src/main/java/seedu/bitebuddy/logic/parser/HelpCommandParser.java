package seedu.bitebuddy.logic.parser;

import java.util.Optional;

import seedu.bitebuddy.logic.Messages;
import seedu.bitebuddy.logic.commands.CommandRegistry;
import seedu.bitebuddy.logic.commands.HelpCommand;
import seedu.bitebuddy.logic.parser.exceptions.ParseException;

/**
 * Parses input for the Help command. Supports optional single argument specifying a command word
 * whose usage should be shown. If the provided command word is unknown, a ParseException is thrown.
 */
public class HelpCommandParser implements Parser<HelpCommand> {

    // Use CommandRegistry to obtain usage strings

    @Override
    public HelpCommand parse(String args) throws ParseException {
        if (args == null || args.isBlank()) {
            return new HelpCommand();
        }

        ArgumentPositionMapper argMapper = new ArgumentPositionMapper(args);
        String commandWord = argMapper.getArgument(0).trim();

        if (commandWord.isEmpty()) {
            return new HelpCommand();
        }

        Optional<String> usage = CommandRegistry.getUsage(commandWord);
        if (usage.isEmpty()) {
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
        return new HelpCommand(usage.get());
    }
}
