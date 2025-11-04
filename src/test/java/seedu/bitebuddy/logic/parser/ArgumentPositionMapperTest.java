package seedu.bitebuddy.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ArgumentPositionMapperTest {

    private static final String SINGLE_WORD = "hello";

    private static final String NOTE_MULTI_WORD = "note 2 good customer service";
    private static final String NOTE_MULTI_WORD_NO_COMMAND_WORD = "2 good customer service";
    private static final String NOTE_MULTI_WORD_WITH_WHITESPACES = "2 good   customer   service";
    private static final String NOTE_MULTI_WORD_NOTE_ARG = "good customer service";
    private static final int NOTE_MULTI_WORD_LENGTH = NOTE_MULTI_WORD.split(" ").length;

    private static final String RATING_MULTI_WORD = "rating 3 5";
    private static final int RATING_MULTI_WORD_LENGTH = RATING_MULTI_WORD.split(" ").length;

    @Test
    public void constructor_emptyString_returnsEmptyList() {
        ArgumentPositionMapper mapper = new ArgumentPositionMapper("");
        assertTrue(mapper.getAllArguments().isEmpty(), "Expected empty list for empty input");
        assertEquals(0, mapper.size());
    }

    @Test
    public void constructor_nullString_returnsEmptyList() {
        ArgumentPositionMapper mapper = new ArgumentPositionMapper(null);
        assertTrue(mapper.getAllArguments().isEmpty(), "Expected empty list for null input");
        assertEquals(0, mapper.size());
    }

    @Test
    public void constructor_singleWord_returnsSingleElement() {
        ArgumentPositionMapper mapper = new ArgumentPositionMapper(SINGLE_WORD);
        assertEquals(1, mapper.size());
        assertEquals(SINGLE_WORD, mapper.getArgument(0));
    }

    @Test
    public void constructor_multipleWords_splitsByWhitespace() {
        ArgumentPositionMapper mapper = new ArgumentPositionMapper(NOTE_MULTI_WORD);
        assertEquals(5, mapper.size());
        assertEquals(List.of(NOTE_MULTI_WORD.split(" ")), mapper.getAllArguments());
    }

    @Test
    public void constructor2_withIgnoreSplitIndex_combinesRemainingTokens() {
        ArgumentPositionMapper mapper = new ArgumentPositionMapper(NOTE_MULTI_WORD_WITH_WHITESPACES, 1);
        assertEquals(2, mapper.size());
        assertEquals("2", mapper.getArgument(0));
        assertEquals("good   customer   service", mapper.getArgument(1));
    }

    @Test
    public void constructor2_negativeIgnoreSplitIndex_combinesRemainingTokens() {
        ArgumentPositionMapper mapper = new ArgumentPositionMapper(NOTE_MULTI_WORD_WITH_WHITESPACES, -1);
        assertEquals(1, mapper.size());
        assertEquals("2 good   customer   service", mapper.getArgument(0));
    }

    @Test
    public void constructor2_zeroIgnoreSplitIndex_combinesRemainingTokens() {
        ArgumentPositionMapper mapper = new ArgumentPositionMapper(NOTE_MULTI_WORD_WITH_WHITESPACES, 0);
        assertEquals(1, mapper.size());
        assertEquals("2 good   customer   service", mapper.getArgument(0));
    }

    @Test
    public void constructor2_ignoreSplitIndexExceedsLength_returnsAllAsSeparateTokens() {
        ArgumentPositionMapper mapper = new ArgumentPositionMapper(NOTE_MULTI_WORD_WITH_WHITESPACES,
                NOTE_MULTI_WORD_LENGTH + 1);
        assertEquals(4, mapper.size());
    }

    @Test
    public void constructor2_emptyString_returnsEmptyList() {
        ArgumentPositionMapper mapper = new ArgumentPositionMapper("", 2);
        assertTrue(mapper.getAllArguments().isEmpty(), "Expected empty list for empty input");
        assertEquals(0, mapper.size());
    }

    @Test
    public void constructor2_nullString_returnsEmptyList() {
        ArgumentPositionMapper mapper = new ArgumentPositionMapper(null, 2);
        assertTrue(mapper.getAllArguments().isEmpty(), "Expected empty list for null input");
        assertEquals(0, mapper.size());
    }

    @Test
    public void constructor2_singleWord_returnsSingleElement() {
        ArgumentPositionMapper mapper = new ArgumentPositionMapper(SINGLE_WORD, 2);
        assertEquals(1, mapper.size());
        assertEquals(SINGLE_WORD, mapper.getArgument(0));
    }

    @Test
    public void getArgument_outOfBounds_returnsEmptyString() {
        ArgumentPositionMapper mapper = new ArgumentPositionMapper(NOTE_MULTI_WORD);
        assertEquals("", mapper.getArgument(-1), "Expected empty string for negative index");
        assertEquals("", mapper.getArgument(NOTE_MULTI_WORD_LENGTH),
                "Expected empty string for out-of-range index");
    }

    @Test
    public void getRemainingArguments_validIndex_returnsJoinedString() {
        ArgumentPositionMapper mapper = new ArgumentPositionMapper(NOTE_MULTI_WORD_NO_COMMAND_WORD);
        assertEquals(NOTE_MULTI_WORD_NOTE_ARG, mapper.getRemainingArguments(1));
    }

    @Test
    public void getRemainingArguments_indexOutOfRange_returnsEmptyString() {
        ArgumentPositionMapper mapper = new ArgumentPositionMapper(NOTE_MULTI_WORD);
        assertEquals("", mapper.getRemainingArguments(NOTE_MULTI_WORD_LENGTH));
    }

    @Test
    public void getRemainingArguments_startAtZero_returnsFullString() {
        ArgumentPositionMapper mapper = new ArgumentPositionMapper(NOTE_MULTI_WORD);
        assertEquals(NOTE_MULTI_WORD, mapper.getRemainingArguments(0));
    }

    @Test
    public void getRemainingArguments_negativeIndex_returnsEmptyString() {
        ArgumentPositionMapper mapper = new ArgumentPositionMapper(RATING_MULTI_WORD);
        assertEquals("", mapper.getRemainingArguments(-1),
                "Expected empty string for negative startPosition");
    }

    @Test
    public void getRemainingArguments_startAtSize_returnsEmptyString() {
        ArgumentPositionMapper mapper = new ArgumentPositionMapper(RATING_MULTI_WORD);
        assertEquals("", mapper.getRemainingArguments(mapper.size()),
                "Expected empty string for startPosition == size");
    }

    @Test
    public void size_returnsCorrectCount() {
        ArgumentPositionMapper mapper = new ArgumentPositionMapper(RATING_MULTI_WORD);
        assertEquals(RATING_MULTI_WORD_LENGTH, mapper.size());
    }

    @Test
    public void getAllArguments_returnsUnmodifiableList() {
        ArgumentPositionMapper mapper = new ArgumentPositionMapper(NOTE_MULTI_WORD);
        List<String> args = mapper.getAllArguments();
        try {
            args.add(SINGLE_WORD);
        } catch (UnsupportedOperationException e) {
            assertTrue(true, "List should be unmodifiable");
            return;
        }
        throw new AssertionError("Expected UnsupportedOperationException when modifying returned list");
    }

    @Test
    public void toString_returnsReadableRepresentation() {
        ArgumentPositionMapper mapper = new ArgumentPositionMapper(RATING_MULTI_WORD);
        String expected = Arrays.toString(RATING_MULTI_WORD.split(" "));
        assertEquals(expected, mapper.toString());
    }
}
