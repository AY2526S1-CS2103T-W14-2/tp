package seedu.bitebuddy.model.foodplace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class BlacklistTest {

    @Test
    public void constructor_validValue_success() {
        Blacklist trueBlacklist = new Blacklist(true);
        assertTrue(trueBlacklist.isBlacklisted);

        Blacklist falseBlacklist = new Blacklist(false);
        assertFalse(falseBlacklist.isBlacklisted);
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Blacklist(null));
    }

    @Test
    public void getOpposite_isTrue_returnsFalse() {
        Blacklist blacklist = new Blacklist(true);
        Blacklist oppositeBlacklist = blacklist.getOpposite();
        assertFalse(oppositeBlacklist.isBlacklisted);
    }

    @Test
    public void getOpposite_isFalse_returnsTrue() {
        Blacklist blacklist = new Blacklist(false);
        Blacklist oppositeBlacklist = blacklist.getOpposite();
        assertTrue(oppositeBlacklist.isBlacklisted);
    }

    @Test
    public void isBlacklisted_getter_success() {
        Blacklist trueBlacklist = new Blacklist(true);
        assertTrue(trueBlacklist.isBlacklisted());

        Blacklist falseBlacklist = new Blacklist(false);
        assertFalse(falseBlacklist.isBlacklisted());
    }

    @Test
    public void toString_wishlistTrue_returnsCorrectString() {
        Blacklist trueBlacklist = new Blacklist(true);
        assertEquals("Blacklisted", trueBlacklist.toString());
    }

    @Test
    public void toString_wishlistFalse_returnsCorrectString() {
        Blacklist falseBlacklist = new Blacklist(false);
        assertEquals("Not blacklisted", falseBlacklist.toString());
    }

    @Test
    public void equals() {
        Blacklist blacklistTrue = new Blacklist(true);
        Blacklist blacklistFalse = new Blacklist(false);

        // same object -> returns true
        assertTrue(blacklistTrue.equals(blacklistTrue));

        // same values -> returns true
        Blacklist blacklistTrueCopy = new Blacklist(true);
        assertTrue(blacklistTrue.equals(blacklistTrueCopy));

        // different types -> returns false
        assertNotEquals(blacklistTrue, true);

        // null -> returns false
        assertNotEquals(blacklistTrue, null);

        // different values -> returns false
        assertFalse(blacklistTrue.equals(blacklistFalse));
    }

    @Test
    public void hashCode_sameValue_sameHash() {
        Blacklist blacklistTrue = new Blacklist(true);
        Blacklist blacklistTrueCopy = new Blacklist(true);
        assertEquals(blacklistTrue.hashCode(), blacklistTrueCopy.hashCode());
    }

    @Test
    public void hashCode_differentValue_differentHash() {
        Blacklist blacklistTrue = new Blacklist(true);
        Blacklist blacklistFalse = new Blacklist(false);
        assertNotEquals(blacklistTrue.hashCode(), blacklistFalse.hashCode());
    }
}
