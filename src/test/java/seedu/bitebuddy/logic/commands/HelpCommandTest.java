package seedu.bitebuddy.logic.commands;

import static seedu.bitebuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.bitebuddy.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.model.Model;
import seedu.bitebuddy.model.ModelManager;

public class HelpCommandTest {
    private final Model model = new ModelManager();
    private final Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpWithUsage_showsUsage() {
        String usage = "help for find command";
        // when HelpCommand is constructed with a usage string, execute should return that string
        assertCommandSuccess(new HelpCommand(usage), model, usage, expectedModel);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        HelpCommand helpCommand = new HelpCommand();
        // same object -> returns true
        assert(helpCommand.equals(helpCommand));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        HelpCommand helpCommand = new HelpCommand();
        // different type -> returns false
        assert(!helpCommand.equals("not a HelpCommand"));
    }

    @Test
    public void equals_null_returnsFalse() {
        HelpCommand helpCommand = new HelpCommand();
        // null -> returns false
        assert(!helpCommand.equals(null));
    }

    @Test
    public void equals_differentUsage_returnsFalse() {
        HelpCommand helpCommand1 = new HelpCommand("usage1");
        HelpCommand helpCommand2 = new HelpCommand("usage2");
        // different usage -> returns false
        assert(!helpCommand1.equals(helpCommand2));
    }

    @Test
    public void equals_sameUsage_returnsTrue() {
        HelpCommand helpCommand1 = new HelpCommand("usage");
        HelpCommand helpCommand2 = new HelpCommand("usage");
        // same usage -> returns true
        assert(helpCommand1.equals(helpCommand2));
    }

    @Test
    public void equals_bothNullUsage_returnsTrue() {
        HelpCommand helpCommand1 = new HelpCommand();
        HelpCommand helpCommand2 = new HelpCommand();
        // both usage null -> returns true
        assert(helpCommand1.equals(helpCommand2));
    }

    @Test
    public void equals_oneNullUsage_returnsFalse() {
        HelpCommand helpCommand1 = new HelpCommand();
        HelpCommand helpCommand2 = new HelpCommand("usage");
        // one usage null, one not -> returns false
        assert(!helpCommand1.equals(helpCommand2));
        assert(!helpCommand2.equals(helpCommand1));
    }
}
