package seedu.bitebuddy.logic.parser;

import static seedu.bitebuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.ADDRESS_DESC_MCRONALDS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.ADDRESS_DESC_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.EMAIL_DESC_MCRONALDS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.EMAIL_DESC_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.NAME_DESC_MCRONALDS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.NAME_DESC_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.NOTE_DESC_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.PHONE_DESC_MCRONALDS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.PHONE_DESC_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.TAG_DESC_FASTFOOD;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.TAG_DESC_RESTAURANT;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_ADDRESS_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_EMAIL_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_NAME_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_NOTE_FAMOUS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_PHONE_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_TAG_FASTFOOD;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_TAG_RESTAURANT;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.bitebuddy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.bitebuddy.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.bitebuddy.testutil.TypicalFoodplace.MCRONALDS;
import static seedu.bitebuddy.testutil.TypicalFoodplace.SWENSWAN;

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.logic.Messages;
import seedu.bitebuddy.logic.commands.AddCommand;
import seedu.bitebuddy.model.foodplace.Address;
import seedu.bitebuddy.model.foodplace.Email;
import seedu.bitebuddy.model.foodplace.Foodplace;
import seedu.bitebuddy.model.foodplace.Name;
import seedu.bitebuddy.model.foodplace.Phone;
import seedu.bitebuddy.model.tag.Tag;
import seedu.bitebuddy.testutil.FoodplaceBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Foodplace expectedFoodplace = new FoodplaceBuilder(SWENSWAN).withTags(VALID_TAG_FASTFOOD).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_SWENSWAN + PHONE_DESC_SWENSWAN + EMAIL_DESC_SWENSWAN
                + ADDRESS_DESC_SWENSWAN + TAG_DESC_FASTFOOD, new AddCommand(expectedFoodplace));


        // multiple tags - all accepted
        Foodplace expectedFoodplaceMultipleTags = new FoodplaceBuilder(SWENSWAN).withTags(VALID_TAG_FASTFOOD,
                        VALID_TAG_RESTAURANT)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_SWENSWAN + PHONE_DESC_SWENSWAN + EMAIL_DESC_SWENSWAN + ADDRESS_DESC_SWENSWAN
                        + TAG_DESC_RESTAURANT + TAG_DESC_FASTFOOD,
                new AddCommand(expectedFoodplaceMultipleTags));

        // with notes
        Foodplace expectedFoodplaceNotes = new FoodplaceBuilder(SWENSWAN).withTags(VALID_TAG_FASTFOOD,
                        VALID_TAG_RESTAURANT).withNote(VALID_NOTE_FAMOUS)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_SWENSWAN + PHONE_DESC_SWENSWAN + EMAIL_DESC_SWENSWAN + ADDRESS_DESC_SWENSWAN
                        + TAG_DESC_RESTAURANT + TAG_DESC_FASTFOOD + NOTE_DESC_SWENSWAN,
                new AddCommand(expectedFoodplaceNotes));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedFoodplaceString = NAME_DESC_SWENSWAN + PHONE_DESC_SWENSWAN + EMAIL_DESC_SWENSWAN
                + ADDRESS_DESC_SWENSWAN + TAG_DESC_FASTFOOD;

        // multiple names
        assertParseFailure(parser, NAME_DESC_MCRONALDS + validExpectedFoodplaceString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_MCRONALDS + validExpectedFoodplaceString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_MCRONALDS + validExpectedFoodplaceString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_MCRONALDS + validExpectedFoodplaceString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedFoodplaceString + PHONE_DESC_MCRONALDS + EMAIL_DESC_MCRONALDS + NAME_DESC_MCRONALDS
                        + ADDRESS_DESC_MCRONALDS
                        + validExpectedFoodplaceString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedFoodplaceString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedFoodplaceString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedFoodplaceString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid bitebuddy
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedFoodplaceString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedFoodplaceString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedFoodplaceString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedFoodplaceString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid bitebuddy
        assertParseFailure(parser, validExpectedFoodplaceString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Foodplace expectedFoodplace = new FoodplaceBuilder(MCRONALDS).withTags().build();
        assertParseSuccess(parser, NAME_DESC_MCRONALDS + PHONE_DESC_MCRONALDS + EMAIL_DESC_MCRONALDS
                + ADDRESS_DESC_MCRONALDS,
                new AddCommand(expectedFoodplace));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_SWENSWAN + PHONE_DESC_SWENSWAN + EMAIL_DESC_SWENSWAN
                + ADDRESS_DESC_SWENSWAN, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_SWENSWAN + VALID_PHONE_SWENSWAN + EMAIL_DESC_SWENSWAN
                + ADDRESS_DESC_SWENSWAN, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_SWENSWAN + PHONE_DESC_SWENSWAN + VALID_EMAIL_SWENSWAN
                + ADDRESS_DESC_SWENSWAN, expectedMessage);

        // missing bitebuddy prefix
        assertParseFailure(parser, NAME_DESC_SWENSWAN + PHONE_DESC_SWENSWAN + EMAIL_DESC_SWENSWAN
                + VALID_ADDRESS_SWENSWAN, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_SWENSWAN + VALID_PHONE_SWENSWAN + VALID_EMAIL_SWENSWAN
                + VALID_ADDRESS_SWENSWAN, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_SWENSWAN + EMAIL_DESC_SWENSWAN + ADDRESS_DESC_SWENSWAN
                + TAG_DESC_RESTAURANT + TAG_DESC_FASTFOOD, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_SWENSWAN + INVALID_PHONE_DESC + EMAIL_DESC_SWENSWAN + ADDRESS_DESC_SWENSWAN
                + TAG_DESC_RESTAURANT + TAG_DESC_FASTFOOD, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_SWENSWAN + PHONE_DESC_SWENSWAN + INVALID_EMAIL_DESC + ADDRESS_DESC_SWENSWAN
                + TAG_DESC_RESTAURANT + TAG_DESC_FASTFOOD, Email.MESSAGE_CONSTRAINTS);

        // invalid bitebuddy
        assertParseFailure(parser, NAME_DESC_SWENSWAN + PHONE_DESC_SWENSWAN + EMAIL_DESC_SWENSWAN + INVALID_ADDRESS_DESC
                + TAG_DESC_RESTAURANT + TAG_DESC_FASTFOOD, Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_SWENSWAN + PHONE_DESC_SWENSWAN + EMAIL_DESC_SWENSWAN
                + ADDRESS_DESC_SWENSWAN + INVALID_TAG_DESC + VALID_TAG_FASTFOOD, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_SWENSWAN + EMAIL_DESC_SWENSWAN + INVALID_ADDRESS_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_SWENSWAN + PHONE_DESC_SWENSWAN + EMAIL_DESC_SWENSWAN
                + ADDRESS_DESC_SWENSWAN + TAG_DESC_RESTAURANT + TAG_DESC_FASTFOOD,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
