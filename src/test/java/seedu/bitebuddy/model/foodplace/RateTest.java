package seedu.bitebuddy.model.foodplace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class RateTest {

    @Test
    public void constructor_null_success() {
        Rate rate = new Rate();
        assertEquals(new Rate(), rate);
    }

    @Test
    public void constructor_valid_success() {
        Rate rate = new Rate(5);
        assertEquals(new Rate(5), rate);
    }

    @Test
    public void isValidRating_valid_true() {
        assertTrue(Rate.isValidRating(0));
        assertTrue(Rate.isValidRating(1));
        assertTrue(Rate.isValidRating(10));
    }

    @Test
    public void isValidRating_invalid_false() {
        assertFalse(Rate.isValidRating(Integer.MIN_VALUE));
        assertFalse(Rate.isValidRating(-1));
        assertFalse(Rate.isValidRating(Integer.MAX_VALUE));
    }

    @Test
    public void setRate_valid_true() {
        Rate rate = new Rate();
        rate.setRate(5);
        assertEquals(5, rate.getValue());
        rate.setRate(0);
        assertEquals(0, rate.getValue());
        rate.setRate(1);
        assertEquals(1, rate.getValue());
        rate.setRate(10);
        assertEquals(10, rate.getValue());
    }

    @Test
    public void setRate_invalid_false() {
        Rate rate = new Rate();

        try {
            rate.setRate(Integer.MIN_VALUE);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        assertNotEquals(Integer.MIN_VALUE, rate.getValue());

        try {
            rate.setRate(-1);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        assertNotEquals(-1, rate.getValue());

        try {
            rate.setRate(Integer.MAX_VALUE);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        assertNotEquals(Integer.MAX_VALUE, rate.getValue());
    }

    @Test
    public void reset_valid_success() {
        Rate rate = new Rate();
        rate.reset();
        assertEquals(new Rate(), rate);
    }

    @Test
    public void isSet_valueNotDefault_true() {
        assertTrue(new Rate(5).isSet());
    }

    @Test
    public void isSet_valueDefault_false() {
        assertFalse(new Rate().isSet());
    }

    @Test
    public void equals_valid_true() {
        Rate rate = new Rate(5);
        assertEquals(rate, rate);
        assertEquals(new Rate(5), rate);
        assertEquals(new Rate(3), new Rate(3));
        assertEquals(new Rate(), new Rate());
    }

    @Test
    public void equals_invalid_false() {
        Rate rate = new Rate(5);
        assertNotEquals(new Rate(), rate);
        assertNotEquals(new Rate(1), rate);
        assertNotEquals(new Rate(10), rate);
    }
}
