package seedu.bitebuddy.model.foodplace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.testutil.Assert.assertThrows;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class TimingTest {

    @Test
    public void isValidTime() {
        assertFalse(Timing.isValidTime(null));
        assertFalse(Timing.isValidTime(""));
        assertTrue(Timing.isValidTime("00:00"));
        assertTrue(Timing.isValidTime("23:59"));
        assertFalse(Timing.isValidTime("24:00"));
        assertFalse(Timing.isValidTime("notatime"));
        assertFalse(Timing.isValidTime("25:60"));
    }

    @Test
    public void isValidTiming() {
        assertFalse(Timing.isValidTiming(null));
        assertFalse(Timing.isValidTiming(""));
        assertTrue(Timing.isValidTiming("09:00-17:00"));
        assertFalse(Timing.isValidTiming("17:00-09:00"));
        assertFalse(Timing.isValidTiming("not-a-range"));
    }

    @Test
    public void getters() {
        Timing t1 = new Timing("09:00", "17:00");
        assertEquals(LocalTime.of(9, 0), t1.getOpeningTime());
        assertEquals(LocalTime.of(17, 0), t1.getClosingTime());
    }

    @Test
    public void tostring() {
        Timing t1 = new Timing("09:00", "17:00");
        assertEquals("09:00-17:00", t1.toString());
    }

    @Test
    public void isOpenAt() {
        Timing t1 = new Timing("09:00", "17:00");
        assertTrue(t1.isOpenAt(LocalTime.of(9, 0)));
        assertTrue(t1.isOpenAt(LocalTime.of(12, 0)));
        assertTrue(t1.isOpenAt(LocalTime.of(17, 0)));
        assertFalse(t1.isOpenAt(LocalTime.of(8, 59)));
        assertFalse(t1.isOpenAt(LocalTime.of(17, 1)));
    }

    @Test
    public void constructor_unsetTiming_isNotSet() {
        Timing unset = new Timing("", "");
        assertEquals("", unset.toString());
        assertFalse(unset.isSet());
    }

    @Test
    public void constructor_invalidRange_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new Timing("09:00-08:00"));
        assertThrows(IllegalArgumentException.class, () -> new Timing("not-a-time-range"));
    }

    @Test
    public void equals() {
        Timing t1 = new Timing("09:00", "17:00");
        Timing t2 = new Timing("09:00", "17:00");
        Timing t3 = new Timing("09:00", "18:00");
        Timing t4 = new Timing("10:00", "17:00");
        Timing unset1 = new Timing("", "");
        Timing unset2 = new Timing("", "");

        // same values -> returns true
        assertTrue(t1.equals(t2));

        // same object -> returns true
        assertTrue(t1.equals(t1));

        // null -> returns false
        assertNotEquals(t1, null);

        // different types -> returns false
        assertNotEquals(t1, "some string");

        // different values -> returns false
        assertFalse(t1.equals(t3));
        assertFalse(t1.equals(t4));

        // unset timings with same values -> returns true
        assertTrue(unset1.equals(unset2));
    }
}
