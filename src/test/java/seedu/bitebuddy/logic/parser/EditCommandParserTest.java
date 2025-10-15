package seedu.bitebuddy.logic.parser;

import static seedu.bitebuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.ADDRESS_DESC_MCRONALDS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.ADDRESS_DESC_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.EMAIL_DESC_MCRONALDS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.EMAIL_DESC_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.INVALID_NOTE_DESC;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.NAME_DESC_MCRONALDS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.NOTE_DESC_MCRONALDS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.PHONE_DESC_MCRONALDS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.PHONE_DESC_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.TAG_DESC_FASTFOOD;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.TAG_DESC_RESTAURANT;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_ADDRESS_MCRONALDS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_EMAIL_MCRONALDS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_NAME_MCRONALDS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_NOTE_SERVICE;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_PHONE_MCRONALDS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_PHONE_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_TAG_FASTFOOD;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_TAG_RESTAURANT;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.bitebuddy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.bitebuddy.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.bitebuddy.testutil.TypicalIndexes.INDEX_FIRST_FOODPLACE;
import static seedu.bitebuddy.testutil.TypicalIndexes.INDEX_SECOND_FOODPLACE;
import static seedu.bitebuddy.testutil.TypicalIndexes.INDEX_THIRD_FOODPLACE;

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.commons.core.index.Index;
import seedu.bitebuddy.logic.Messages;
import seedu.bitebuddy.logic.commands.EditCommand;
import seedu.bitebuddy.model.foodplace.Address;
import seedu.bitebuddy.model.foodplace.Email;
import seedu.bitebuddy.model.foodplace.Name;
import seedu.bitebuddy.model.foodplace.Note;
import seedu.bitebuddy.model.foodplace.Phone;
import seedu.bitebuddy.model.tag.Tag;
import seedu.bitebuddy.testutil.EditFoodplaceDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_MCRONALDS, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_MCRONALDS, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_MCRONALDS, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS); // invalid bitebuddy
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_MCRONALDS, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Foodplace} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FASTFOOD + TAG_DESC_RESTAURANT + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FASTFOOD + TAG_EMPTY + TAG_DESC_RESTAURANT, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FASTFOOD + TAG_DESC_RESTAURANT, Tag.MESSAGE_CONSTRAINTS);

        // invalid note
        assertParseFailure(parser, "1" + INVALID_NOTE_DESC, Note.MESSAGE_CONSTRAINTS); // invalid phone

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_MCRONALDS
                + VALID_PHONE_MCRONALDS, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_FOODPLACE;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_SWENSWAN + TAG_DESC_RESTAURANT
                + EMAIL_DESC_MCRONALDS + ADDRESS_DESC_MCRONALDS + NAME_DESC_MCRONALDS + TAG_DESC_FASTFOOD
                + NOTE_DESC_MCRONALDS;

        EditCommand.EditFoodplaceDescriptor descriptor = new EditFoodplaceDescriptorBuilder()
                .withName(VALID_NAME_MCRONALDS).withPhone(VALID_PHONE_SWENSWAN).withEmail(VALID_EMAIL_MCRONALDS)
                .withAddress(VALID_ADDRESS_MCRONALDS).withTags(VALID_TAG_RESTAURANT, VALID_TAG_FASTFOOD)
                .withNote(VALID_NOTE_SERVICE).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_FOODPLACE;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_SWENSWAN + EMAIL_DESC_MCRONALDS;

        EditCommand.EditFoodplaceDescriptor descriptor = new EditFoodplaceDescriptorBuilder()
                .withPhone(VALID_PHONE_SWENSWAN).withEmail(VALID_EMAIL_MCRONALDS).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_FOODPLACE;
        String userInput = targetIndex.getOneBased() + NAME_DESC_MCRONALDS;
        EditCommand.EditFoodplaceDescriptor descriptor = new EditFoodplaceDescriptorBuilder()
                .withName(VALID_NAME_MCRONALDS).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_MCRONALDS;
        descriptor = new EditFoodplaceDescriptorBuilder().withPhone(VALID_PHONE_MCRONALDS).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_MCRONALDS;
        descriptor = new EditFoodplaceDescriptorBuilder().withEmail(VALID_EMAIL_MCRONALDS).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // bitebuddy
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_MCRONALDS;
        descriptor = new EditFoodplaceDescriptorBuilder().withAddress(VALID_ADDRESS_MCRONALDS).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FASTFOOD;
        descriptor = new EditFoodplaceDescriptorBuilder().withTags(VALID_TAG_FASTFOOD).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // note
        userInput = targetIndex.getOneBased() + NOTE_DESC_MCRONALDS;
        descriptor = new EditFoodplaceDescriptorBuilder().withNote(VALID_NOTE_SERVICE).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_FOODPLACE;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_SWENSWAN;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + PHONE_DESC_SWENSWAN + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // mulltiple valid fields repeated
        userInput = targetIndex.getOneBased() + PHONE_DESC_MCRONALDS + ADDRESS_DESC_MCRONALDS + EMAIL_DESC_MCRONALDS
                + TAG_DESC_FASTFOOD + PHONE_DESC_MCRONALDS + ADDRESS_DESC_MCRONALDS + EMAIL_DESC_MCRONALDS
                + TAG_DESC_FASTFOOD + PHONE_DESC_SWENSWAN + ADDRESS_DESC_SWENSWAN + EMAIL_DESC_SWENSWAN
                + TAG_DESC_RESTAURANT;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC + INVALID_EMAIL_DESC
                + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC + INVALID_EMAIL_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS));
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_FOODPLACE;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditCommand.EditFoodplaceDescriptor descriptor = new EditFoodplaceDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
