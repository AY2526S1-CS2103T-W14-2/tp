package seedu.bitebuddy.commons.core.index;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class IndexTest {

    @Test
    public void createOneBasedIndex() {
        // invalid index
        assertThrows(IndexOutOfBoundsException.class, () -> Index.fromOneBased(0));

        // check equality using the same base
        assertEquals(1, Index.fromOneBased(1).getOneBased());
        assertEquals(5, Index.fromOneBased(5).getOneBased());

        // convert from one-based index to zero-based index
        assertEquals(0, Index.fromOneBased(1).getZeroBased());
        assertEquals(4, Index.fromOneBased(5).getZeroBased());
    }

    @Test
    public void createZeroBasedIndex() {
        // invalid index
        assertThrows(IndexOutOfBoundsException.class, () -> Index.fromZeroBased(-1));

        // check equality using the same base
        assertEquals(0, Index.fromZeroBased(0).getZeroBased());
        assertEquals(5, Index.fromZeroBased(5).getZeroBased());

        // convert from zero-based index to one-based index
        assertEquals(1, Index.fromZeroBased(0).getOneBased());
        assertEquals(6, Index.fromZeroBased(5).getOneBased());
    }

    @Test
    public void equals() {
        final Index fifthFoodplaceIndex = Index.fromOneBased(5);

        // same values -> returns true
        assertTrue(fifthFoodplaceIndex.equals(Index.fromOneBased(5)));
        assertTrue(fifthFoodplaceIndex.equals(Index.fromZeroBased(4)));

        // same object -> returns true
        assertTrue(fifthFoodplaceIndex.equals(fifthFoodplaceIndex));

        // null -> returns false
        assertNotNull(fifthFoodplaceIndex);

        // different types -> returns false
        assertFalse(fifthFoodplaceIndex.equals(5.0f));

        // different index -> returns false
        assertFalse(fifthFoodplaceIndex.equals(Index.fromOneBased(1)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromZeroBased(0);
        String expected = Index.class.getCanonicalName() + "{zeroBasedIndex=" + index.getZeroBased() + "}";
        assertEquals(expected, index.toString());
    }

    @Test
    public void hashcode() {
        // same values -> same hashcode
        Index indexA = Index.fromOneBased(1);
        Index indexB = Index.fromOneBased(1);
        assertEquals(indexA.hashCode(), indexB.hashCode());

        // different values -> different hashcode
        Index indexC = Index.fromOneBased(5);
        assertFalse(indexA.hashCode() == indexC.hashCode());

        // same object -> same hashcode
        Index index = Index.fromOneBased(1);
        assertEquals(index.hashCode(), index.hashCode());
    }
}
