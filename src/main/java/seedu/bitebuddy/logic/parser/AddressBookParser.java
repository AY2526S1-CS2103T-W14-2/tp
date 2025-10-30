package seedu.bitebuddy.logic.parser;

import static seedu.bitebuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.bitebuddy.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.bitebuddy.commons.core.LogsCenter;
import seedu.bitebuddy.logic.commands.AddCommand;
import seedu.bitebuddy.logic.commands.BlacklistCommand;
import seedu.bitebuddy.logic.commands.ClearCommand;
import seedu.bitebuddy.logic.commands.Command;
import seedu.bitebuddy.logic.commands.CompareCommand;
import seedu.bitebuddy.logic.commands.DeleteCommand;
import seedu.bitebuddy.logic.commands.EditCommand;
import seedu.bitebuddy.logic.commands.ExitCommand;
import seedu.bitebuddy.logic.commands.FindCommand;
import seedu.bitebuddy.logic.commands.HelpCommand;
import seedu.bitebuddy.logic.commands.ListCommand;
import seedu.bitebuddy.logic.commands.NoteCommand;
import seedu.bitebuddy.logic.commands.PinCommand;
import seedu.bitebuddy.logic.commands.RateCommand;
import seedu.bitebuddy.logic.commands.TagCommand;
import seedu.bitebuddy.logic.commands.UnpinCommand;
import seedu.bitebuddy.logic.commands.WishlistCommand;
import seedu.bitebuddy.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);
    private static final Map<String, Parser<? extends Command>> parserMap = new HashMap<>();
    private static final Map<String, Supplier<Command>> supplierMap = new HashMap<>();

    static {
        // Parsers for commands that need to parse arguments
        parserMap.put(AddCommand.COMMAND_WORD, new AddCommandParser());
        parserMap.put(NoteCommand.COMMAND_WORD, new NoteCommandParser());
        parserMap.put(EditCommand.COMMAND_WORD, new EditCommandParser());
        parserMap.put(DeleteCommand.COMMAND_WORD, new DeleteCommandParser());
        parserMap.put(FindCommand.COMMAND_WORD, new FindCommandParser());
        parserMap.put(HelpCommand.COMMAND_WORD, new HelpCommandParser());
        parserMap.put(TagCommand.COMMAND_WORD, new TagCommandParser());
        parserMap.put(RateCommand.COMMAND_WORD, new RateCommandParser());
        parserMap.put(CompareCommand.COMMAND_WORD, new CompareCommandParser());
        parserMap.put(WishlistCommand.COMMAND_WORD, new WishlistCommandParser());
        parserMap.put(BlacklistCommand.COMMAND_WORD, new BlacklistCommandParser());
        parserMap.put(PinCommand.COMMAND_WORD, new PinCommandParser());
        parserMap.put(UnpinCommand.COMMAND_WORD, new UnpinCommandParser());

        // Suppliers for simple no-argument commands
        supplierMap.put(ClearCommand.COMMAND_WORD, ClearCommand::new);
        supplierMap.put(ListCommand.COMMAND_WORD, ListCommand::new);
        supplierMap.put(ExitCommand.COMMAND_WORD, ExitCommand::new);
    }


    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        logger.log(Level.FINE, () -> "Command word: " + commandWord + "; Arguments: " + arguments);

        if (parserMap.containsKey(commandWord)) {
            Parser<? extends Command> p = parserMap.get(commandWord);
            return p.parse(arguments);
        }

        if (supplierMap.containsKey(commandWord)) {
            return supplierMap.get(commandWord).get();
        }

        logger.log(Level.FINER, () -> "This user input caused a ParseException: " + userInput);
        throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
    }

}
