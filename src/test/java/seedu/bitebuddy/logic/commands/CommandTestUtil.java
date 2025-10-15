package seedu.bitebuddy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.bitebuddy.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.bitebuddy.commons.core.index.Index;
import seedu.bitebuddy.logic.commands.exceptions.CommandException;
import seedu.bitebuddy.model.AddressBook;
import seedu.bitebuddy.model.Model;
import seedu.bitebuddy.model.foodplace.Foodplace;
import seedu.bitebuddy.model.foodplace.FoodplaceContainsKeywordsPredicate;
import seedu.bitebuddy.testutil.EditFoodplaceDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_MCRONALDS = "McRonalds";
    public static final String VALID_NAME_SWENSWAN = "Swenswan";
    public static final String VALID_PHONE_MCRONALDS = "11111111";
    public static final String VALID_PHONE_SWENSWAN = "22222222";
    public static final String VALID_EMAIL_MCRONALDS = "info@mcronalds.com";
    public static final String VALID_EMAIL_SWENSWAN = "booking@swenswan.com";
    public static final String VALID_ADDRESS_MCRONALDS = "Rideout Tea Garden";
    public static final String VALID_ADDRESS_SWENSWAN = "Geneo Lvl 3";
    public static final String VALID_TAG_FASTFOOD = "fastfood";
    public static final String VALID_TAG_RESTAURANT = "restaurant";
    public static final String VALID_RATE_PRATASHOP = "3";
    public static final String VALID_RATE_DAEBAKSHOP = "8";
    public static final String VALID_NOTE_SERVICE = "Good customer service!";
    public static final String VALID_NOTE_FAMOUS = "Famous for desserts";

    public static final String NAME_DESC_MCRONALDS = " " + PREFIX_NAME + VALID_NAME_MCRONALDS;
    public static final String NAME_DESC_SWENSWAN = " " + PREFIX_NAME + VALID_NAME_SWENSWAN;
    public static final String PHONE_DESC_MCRONALDS = " " + PREFIX_PHONE + VALID_PHONE_MCRONALDS;
    public static final String PHONE_DESC_SWENSWAN = " " + PREFIX_PHONE + VALID_PHONE_SWENSWAN;
    public static final String EMAIL_DESC_MCRONALDS = " " + PREFIX_EMAIL + VALID_EMAIL_MCRONALDS;
    public static final String EMAIL_DESC_SWENSWAN = " " + PREFIX_EMAIL + VALID_EMAIL_SWENSWAN;
    public static final String ADDRESS_DESC_MCRONALDS = " " + PREFIX_ADDRESS + VALID_ADDRESS_MCRONALDS;
    public static final String ADDRESS_DESC_SWENSWAN = " " + PREFIX_ADDRESS + VALID_ADDRESS_SWENSWAN;
    public static final String TAG_DESC_FASTFOOD = " " + PREFIX_TAG + VALID_TAG_FASTFOOD;
    public static final String TAG_DESC_RESTAURANT = " " + PREFIX_TAG + VALID_TAG_RESTAURANT;
    public static final String NOTE_DESC_MCRONALDS = " " + PREFIX_NOTE + VALID_NOTE_SERVICE;
    public static final String NOTE_DESC_SWENSWAN = " " + PREFIX_NOTE + VALID_NOTE_FAMOUS;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "kfc&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "bubbletea*"; // '*' not allowed in tags
    public static final String INVALID_NOTE_DESC = " " + PREFIX_NOTE + "Nice place! \u0081";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditFoodplaceDescriptor DESC_MCRONALDS;
    public static final EditCommand.EditFoodplaceDescriptor DESC_SWENSWAN;

    static {
        DESC_MCRONALDS = new EditFoodplaceDescriptorBuilder().withName(VALID_NAME_MCRONALDS)
                .withPhone(VALID_PHONE_MCRONALDS).withEmail(VALID_EMAIL_MCRONALDS).withAddress(VALID_ADDRESS_MCRONALDS)
                .withTags(VALID_TAG_FASTFOOD).build();
        DESC_SWENSWAN = new EditFoodplaceDescriptorBuilder().withName(VALID_NAME_SWENSWAN)
                .withPhone(VALID_PHONE_SWENSWAN).withEmail(VALID_EMAIL_SWENSWAN).withAddress(VALID_ADDRESS_SWENSWAN)
                .withTags(VALID_TAG_RESTAURANT).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the bitebuddy book, filtered foodplace list and selected foodplace in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Foodplace> expectedFilteredList = new ArrayList<>(actualModel.getFilteredFoodplaceList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredFoodplaceList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the foodplace at the given {@code targetIndex} in the
     * {@code model}'s bitebuddy book.
     */
    public static void showFoodplaceAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredFoodplaceList().size());

        Foodplace foodplace = model.getFilteredFoodplaceList().get(targetIndex.getZeroBased());
        final String[] splitName = foodplace.getName().fullName.split("\\s+");
        model.updateFilteredFoodplaceList(new FoodplaceContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredFoodplaceList().size());
    }

}
