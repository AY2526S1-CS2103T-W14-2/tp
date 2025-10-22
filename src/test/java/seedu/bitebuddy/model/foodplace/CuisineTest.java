package seedu.bitebuddy.model.foodplace;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CuisineTest {

    @Test
    public void equals_nonCuisineObject_returnsFalse() {
        Cuisine c = new Cuisine("Italian");
        assertNotEquals(c, null);
        assertNotEquals(c, "Italian");
    }

    @Test
    public void equals_sameValueDifferentCase_returnsTrue() {
        Cuisine c1 = new Cuisine("Indian");
        Cuisine c2 = new Cuisine("indian");
        assertTrue(c1.equals(c2));
    }
}
