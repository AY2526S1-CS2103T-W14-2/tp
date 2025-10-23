package seedu.bitebuddy.model.foodplace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class WishlistTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Wishlist(null));
    }

    @Test
    public void constructor_validValue_success() {
        Wishlist trueWishlist = new Wishlist(true);
        assertTrue(trueWishlist.isWishlisted);

        Wishlist falseWishlist = new Wishlist(false);
        assertFalse(falseWishlist.isWishlisted);
    }

    @Test
    public void getOpposite_isTrue_returnsFalse() {
        Wishlist wishlist = new Wishlist(true);
        Wishlist oppositeWishlist = wishlist.getOpposite();
        assertFalse(oppositeWishlist.isWishlisted);
    }

    @Test
    public void getOpposite_isFalse_returnsTrue() {
        Wishlist wishlist = new Wishlist(false);
        Wishlist oppositeWishlist = wishlist.getOpposite();
        assertTrue(oppositeWishlist.isWishlisted);
    }

    @Test
    public void isWishlisted_getter_success() {
        Wishlist trueWishlist = new Wishlist(true);
        assertTrue(trueWishlist.isWishlisted());

        Wishlist falseWishlist = new Wishlist(false);
        assertFalse(falseWishlist.isWishlisted());
    }

    @Test
    public void toString_returnsCorrectString() {
        Wishlist trueWishlist = new Wishlist(true);
        assertEquals("true", trueWishlist.toString());

        Wishlist falseWishlist = new Wishlist(false);
        assertEquals("false", falseWishlist.toString());
    }

    @Test
    public void equals() {
        Wishlist wishlistTrue = new Wishlist(true);
        Wishlist wishlistFalse = new Wishlist(false);

        // same object -> returns true
        assertTrue(wishlistTrue.equals(wishlistTrue));

        // same values -> returns true
        Wishlist wishlistTrueCopy = new Wishlist(true);
        assertTrue(wishlistTrue.equals(wishlistTrueCopy));

        // different types -> returns false
        assertNotEquals(wishlistTrue, true);

        // null -> returns false
        assertNotEquals(wishlistTrue, null);

        // different values -> returns false
        assertFalse(wishlistTrue.equals(wishlistFalse));
    }

    @Test
    public void hashCode_test() {
        Wishlist wishlistTrue = new Wishlist(true);
        Wishlist wishlistTrueCopy = new Wishlist(true);
        Wishlist wishlistFalse = new Wishlist(false);

        // same values -> same hashcode
        assertEquals(wishlistTrue.hashCode(), wishlistTrueCopy.hashCode());

        // different values -> different hashcode
        assertNotEquals(wishlistTrue.hashCode(), wishlistFalse.hashCode());
    }
}
