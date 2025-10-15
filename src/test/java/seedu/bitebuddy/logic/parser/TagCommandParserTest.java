package seedu.bitebuddy.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.bitebuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.bitebuddy.logic.Messages.MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.commons.core.index.Index;
import seedu.bitebuddy.logic.commands.TagCommand;
import seedu.bitebuddy.logic.parser.exceptions.ParseException;
import seedu.bitebuddy.model.tag.Tag;

/**
 * Contains unit tests for {@code TagCommandParser}.
 */
public class TagCommandParserTest {

    private final TagCommandParser parser = new TagCommandParser();

    @Test
    public void parse_validArgsSingleTag() throws Exception {
        String input = "1 FastFood";
        Set<Tag> expectedTags = new HashSet<>();
        expectedTags.add(new Tag("FastFood"));
        TagCommand expectedCommand = new TagCommand(Index.fromOneBased(1), expectedTags, false);

        assertEquals(expectedCommand, parser.parse(input));
    }

    @Test
    public void parse_validArgsMultipleTags() throws Exception {
        String input = "2 Cheap Popular Local";
        Set<Tag> expectedTags = new HashSet<>();
        expectedTags.add(new Tag("Cheap"));
        expectedTags.add(new Tag("Popular"));
        expectedTags.add(new Tag("Local"));
        TagCommand expectedCommand = new TagCommand(Index.fromOneBased(2), expectedTags, false);

        assertEquals(expectedCommand, parser.parse(input));
    }

    @Test
    public void parse_missingTagsThrowsParseException() {
        String input = "3"; // no tags
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(input));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE),
                exception.getMessage());
    }

    @Test
    public void parse_invalidIndexThrowsParseException() {
        String input = "abc FastFood";
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(input));
        assertEquals(MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX, exception.getMessage());
    }

    @Test
    public void parse_emptyTagThrowsParseException() {
        String input = "1   ";
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(input));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE),
                exception.getMessage());
    }

    @Test
    public void parse_whitespaceOnlyThrowsParseException() {
        String input = "   ";
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(input));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE),
                exception.getMessage());
    }

    @Test
    public void parse_duplicateTagsNoDuplicates() throws Exception {
        String input = "1 FastFood FastFood cheap";
        Set<Tag> expectedTags = new HashSet<>();
        expectedTags.add(new Tag("FastFood"));
        expectedTags.add(new Tag("cheap"));
        TagCommand expectedCommand = new TagCommand(Index.fromOneBased(1), expectedTags, false);

        assertEquals(expectedCommand, parser.parse(input));
    }

    @Test
    public void parse_deleteAllTags_success() throws Exception {
        String input = "1 /d";
        Set<Tag> expectedTags = new HashSet<>();
        TagCommand expectedCommand = new TagCommand(Index.fromOneBased(1), expectedTags, true);

        assertEquals(expectedCommand, parser.parse(input));
    }

    @Test
    public void parse_deleteSpecificTags_success() throws Exception {
        String input = "2 /d FastFood Cheap";
        Set<Tag> expectedTags = new HashSet<>();
        expectedTags.add(new Tag("FastFood"));
        expectedTags.add(new Tag("Cheap"));

        TagCommand expectedCommand = new TagCommand(Index.fromOneBased(2), expectedTags, true);
        assertEquals(expectedCommand, parser.parse(input));
    }

    @Test
    public void parse_deleteNoArgsAfterIndex_throwsParseException() {
        String input = "3";
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(input));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE),
                exception.getMessage());
    }

    @Test
    public void parse_deleteFlagButNoIndex_throwsParseException() {
        String input = "/d";
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(input));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE),
                exception.getMessage());
    }

}
