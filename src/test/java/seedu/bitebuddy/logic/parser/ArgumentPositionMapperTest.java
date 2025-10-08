package seedu.bitebuddy.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class ArgumentPositionMapperTest {

    @Test
    public void constructor_emptyString_returnsEmptyList() {
        ArgumentPositionMapper mapper = new ArgumentPositionMapper("");
        assertTrue(mapper.getAllArguments().isEmpty(), "Expected empty list for empty input");
        assertEquals(0, mapper.size());
    }

    @Test
    public void constructor_singleWord_returnsSingleElement() {
        ArgumentPositionMapper mapper = new ArgumentPositionMapper("hello");
        assertEquals(1, mapper.size());
        assertEquals("hello", mapper.getArgument(0));
    }

    @Test
    public void constructor_multipleWords_splitsByWhitespace() {
        ArgumentPositionMapper mapper = new ArgumentPositionMapper("note 2 good customer service");
        assertEquals(5, mapper.size());
        assertEquals(List.of("note", "2", "good", "customer", "service"), mapper.getAllArguments());
    }

    @Test
    public void getArgument_outOfBounds_returnsEmptyString() {
        ArgumentPositionMapper mapper = new ArgumentPositionMapper("one two three");
        assertEquals("", mapper.getArgument(-1), "Expected empty string for negative index");
        assertEquals("", mapper.getArgument(10), "Expected empty string for out-of-range index");
    }

    @Test
    public void getRemainingArguments_validIndex_returnsJoinedString() {
        ArgumentPositionMapper mapper = new ArgumentPositionMapper("2 good customer service");
        assertEquals("good customer service", mapper.getRemainingArguments(1));
    }

    @Test
    public void getRemainingArguments_indexOutOfRange_returnsEmptyString() {
        ArgumentPositionMapper mapper = new ArgumentPositionMapper("note 1 something");
        assertEquals("", mapper.getRemainingArguments(5));
    }

    @Test
    public void getRemainingArguments_startAtZero_returnsFullString() {
        ArgumentPositionMapper mapper = new ArgumentPositionMapper("note 2 testing behavior");
        assertEquals("note 2 testing behavior", mapper.getRemainingArguments(0));
    }

    @Test
    public void size_returnsCorrectCount() {
        ArgumentPositionMapper mapper = new ArgumentPositionMapper("rating 3 5");
        assertEquals(3, mapper.size());
    }

    @Test
    public void getAllArguments_returnsUnmodifiableList() {
        ArgumentPositionMapper mapper = new ArgumentPositionMapper("a b c");
        List<String> args = mapper.getAllArguments();
        try {
            args.add("d");
        } catch (UnsupportedOperationException e) {
            assertTrue(true, "List should be unmodifiable");
            return;
        }
        throw new AssertionError("Expected UnsupportedOperationException when modifying returned list");
    }

    @Test
    public void toString_returnsReadableRepresentation() {
        ArgumentPositionMapper mapper = new ArgumentPositionMapper("rating 1 9");
        String expected = "[rating, 1, 9]";
        assertEquals(expected, mapper.toString());
    }
}
