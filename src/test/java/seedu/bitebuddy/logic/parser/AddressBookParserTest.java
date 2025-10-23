package seedu.bitebuddy.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.bitebuddy.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.bitebuddy.testutil.Assert.assertThrows;
import static seedu.bitebuddy.testutil.TypicalIndexes.INDEX_FIRST_FOODPLACE;
import static seedu.bitebuddy.testutil.TypicalIndexes.INDEX_SECOND_FOODPLACE;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.logic.commands.AddCommand;
import seedu.bitebuddy.logic.commands.ClearCommand;
import seedu.bitebuddy.logic.commands.CompareCommand;
import seedu.bitebuddy.logic.commands.DeleteCommand;
import seedu.bitebuddy.logic.commands.EditCommand;
import seedu.bitebuddy.logic.commands.EditCommand.EditFoodplaceDescriptor;
import seedu.bitebuddy.logic.commands.ExitCommand;
import seedu.bitebuddy.logic.commands.FindCommand;
import seedu.bitebuddy.logic.commands.HelpCommand;
import seedu.bitebuddy.logic.commands.ListCommand;
import seedu.bitebuddy.logic.commands.NoteCommand;
import seedu.bitebuddy.logic.commands.PinCommand;
import seedu.bitebuddy.logic.commands.RateCommand;
import seedu.bitebuddy.logic.commands.WishlistCommand;
import seedu.bitebuddy.logic.commands.UnpinCommand;
import seedu.bitebuddy.logic.parser.exceptions.ParseException;
import seedu.bitebuddy.model.foodplace.Foodplace;
import seedu.bitebuddy.model.foodplace.FoodplaceContainsKeywordsPredicate;
import seedu.bitebuddy.model.foodplace.Note;
import seedu.bitebuddy.model.foodplace.Rate;
import seedu.bitebuddy.testutil.EditFoodplaceDescriptorBuilder;
import seedu.bitebuddy.testutil.FoodplaceBuilder;
import seedu.bitebuddy.testutil.FoodplaceUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Foodplace foodplace = new FoodplaceBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(FoodplaceUtil.getAddCommand(foodplace));
        assertEquals(new AddCommand(foodplace), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_FOODPLACE.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_FOODPLACE), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Foodplace foodplace = new FoodplaceBuilder().build();
        EditFoodplaceDescriptor descriptor = new EditFoodplaceDescriptorBuilder(foodplace).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_FOODPLACE.getOneBased() + " "
                + FoodplaceUtil.getEditFoodplaceDescriptorDetails(descriptor));
        // Should not check for wishlist
        EditFoodplaceDescriptor expectedDescriptor = new EditFoodplaceDescriptor(descriptor);
        expectedDescriptor.setWishlist(null);
        assertEquals(new EditCommand(INDEX_FIRST_FOODPLACE, expectedDescriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new FoodplaceContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertEquals(new HelpCommand(FindCommand.MESSAGE_USAGE),
                parser.parseCommand(HelpCommand.COMMAND_WORD + " " + FindCommand.COMMAND_WORD));
        // when an unknown argument is provided, the HelpCommandParser should throw a ParseException
        assertThrows(ParseException.class,
                MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand(HelpCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_note() throws Exception {
        final Note note = new Note("Some note.");
        NoteCommand command = (NoteCommand) parser.parseCommand(NoteCommand.COMMAND_WORD + " "
                + INDEX_FIRST_FOODPLACE.getOneBased() + " " + note.value);
        assertEquals(new NoteCommand(INDEX_FIRST_FOODPLACE, note), command);
    }

    @Test
    public void parseCommand_rate() throws Exception {
        final Rate rate = new Rate(3);
        RateCommand command = (RateCommand) parser.parseCommand(RateCommand.COMMAND_WORD + " "
                + INDEX_FIRST_FOODPLACE.getOneBased() + " " + rate.getValue());
        assertEquals(new RateCommand(INDEX_FIRST_FOODPLACE, rate.getValue()), command);
    }

    @Test
    public void parseCommand_wishlist() throws Exception {
        WishlistCommand command = (WishlistCommand) parser.parseCommand(WishlistCommand.COMMAND_WORD + " "
                + INDEX_FIRST_FOODPLACE.getOneBased());
        assertEquals(new WishlistCommand(INDEX_FIRST_FOODPLACE), command);
    }

    @Test
    public void parseCommand_compare() throws Exception {
        CompareCommand command = (CompareCommand) parser.parseCommand(CompareCommand.COMMAND_WORD + " "
                + INDEX_FIRST_FOODPLACE.getOneBased() + " " + INDEX_SECOND_FOODPLACE.getOneBased());
        assertEquals(new CompareCommand(INDEX_FIRST_FOODPLACE, INDEX_SECOND_FOODPLACE), command);
    }

    @Test
    public void parseCommand_pin() throws Exception {
        PinCommand command = (PinCommand) parser.parseCommand(
                PinCommand.COMMAND_WORD + " " + INDEX_FIRST_FOODPLACE.getOneBased());
        assertEquals(new PinCommand(INDEX_FIRST_FOODPLACE), command);
    }

    @Test
    public void parseCommand_unpin() throws Exception {
        UnpinCommand command = (UnpinCommand) parser.parseCommand(
                UnpinCommand.COMMAND_WORD + " " + INDEX_FIRST_FOODPLACE.getOneBased());
        assertEquals(new UnpinCommand(INDEX_FIRST_FOODPLACE), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
