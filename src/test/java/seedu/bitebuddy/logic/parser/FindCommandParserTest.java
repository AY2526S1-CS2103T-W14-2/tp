package seedu.bitebuddy.logic.parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.bitebuddy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.bitebuddy.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.logic.commands.FindCommand;
import seedu.bitebuddy.model.foodplace.Cuisine;
import seedu.bitebuddy.model.foodplace.FoodplaceContainsKeywordsPredicate;
import seedu.bitebuddy.model.foodplace.FoodplaceMatchesCriteriaPredicate;
import seedu.bitebuddy.model.foodplace.Rate;
import seedu.bitebuddy.model.tag.Tag;

public class FindCommandParserTest {

    private final FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validKeywordArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand = new FindCommand(
                Optional.of(new FoodplaceContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"))),
                Optional.empty());

        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validFieldArgs_returnsFindCommand() {
        FindCommand expectedFindCommand = new FindCommand(
                Optional.empty(),
                Optional.of(new FoodplaceMatchesCriteriaPredicate(
                        Collections.singletonList("hawker"),
                        Optional.of(new Cuisine("Japanese")),
                        Optional.of(new Rate(8)))));

        assertParseSuccess(parser, "t/hawker c/Japanese r/8", expectedFindCommand);
    }

    @Test
    public void parse_invalidRating_throwsParseException() {
        assertParseFailure(parser, "r/abc",
                "Ratings should only contain numbers, and be an integer between 0 to 10 "
                        + "(use 0 for unrated food places)");

        assertParseFailure(parser, "r/15",
                "Ratings should only contain numbers, and be an integer between 0 to 10 "
                        + "(use 0 for unrated food places)");
    }

    @Test
    public void parse_emptyPrefixValue_throwsParseException() {
        // empty tag
        assertParseFailure(parser, "t/",
                "Prefix provided without value.\n" + FindCommand.MESSAGE_USAGE);

        // empty cuisine
        assertParseFailure(parser, "c/",
                "Prefix provided without value.\n" + FindCommand.MESSAGE_USAGE);

        // empty rating
        assertParseFailure(parser, "r/",
                "Prefix provided without value.\n" + FindCommand.MESSAGE_USAGE);
    }

    @Test
    public void buildFieldPredicate_allEmpty_returnsEmptyOptional() throws Exception {
        Method m = FindCommandParser.class.getDeclaredMethod("buildFieldPredicate",
                List.class, Optional.class, Optional.class);
        m.setAccessible(true);

        Optional<?> result = (Optional<?>) m.invoke(parser, List.of(), Optional.empty(), Optional.empty());
        assertFalse(result.isPresent());
    }

    @Test
    public void buildFieldPredicate_tagPresent_returnsPredicate() throws Exception {
        Method m = FindCommandParser.class.getDeclaredMethod("buildFieldPredicate",
                List.class, Optional.class, Optional.class);
        m.setAccessible(true);

        Optional<?> result = (Optional<?>) m.invoke(parser, List.of("FastFood"), Optional.empty(), Optional.empty());
        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof FoodplaceMatchesCriteriaPredicate);
    }

    @Test
    public void buildFieldPredicate_cuisinePresent_returnsPredicate() throws Exception {
        Method m = FindCommandParser.class.getDeclaredMethod("buildFieldPredicate",
                List.class, Optional.class, Optional.class);
        m.setAccessible(true);

        Optional<?> result = (Optional<?>) m.invoke(parser, List.of(),
                Optional.of(new Cuisine("Italian")), Optional.empty());
        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof FoodplaceMatchesCriteriaPredicate);
    }

    @Test
    public void buildFieldPredicate_ratingPresent_returnsPredicate() throws Exception {
        Method m = FindCommandParser.class.getDeclaredMethod("buildFieldPredicate",
                List.class, Optional.class, Optional.class);
        m.setAccessible(true);

        Optional<?> result = (Optional<?>) m.invoke(parser, List.of(), Optional.empty(), Optional.of(new Rate(5)));
        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof FoodplaceMatchesCriteriaPredicate);
    }

    @Test
    public void parse_invalidCuisine_throwsParseException() {
        assertParseFailure(parser, "c/**1",
                FindCommandParser.MESSAGE_INVALID_CUISINE);
    }

    @Test
    public void parse_invalidTag_throwsParseException() {
        assertParseFailure(parser, "t/**1",
                Tag.MESSAGE_CONSTRAINTS);
    }
}
