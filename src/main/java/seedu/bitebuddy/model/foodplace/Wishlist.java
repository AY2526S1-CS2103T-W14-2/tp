package seedu.bitebuddy.model.foodplace;

import static java.util.Objects.requireNonNull;

/**
 * Represents if the foodplace is wishlisted in BiteBuddy.
 * Guarantees: immutable; is always valid
 */
public class Wishlist {
    public final Boolean isWishlisted;

    /**
     * Constructs Wishlist object
     * @param wishlist the wishlist for a foodplace
     */
    public Wishlist(Boolean wishlist) {
        requireNonNull(wishlist);
        this.isWishlisted = wishlist;
    }

    /**
     * Creates a Wishlist object with the opposite boolean
     * @return the wishlist object of the opposite boolean
     */
    public Wishlist getOpposite() {
        return new Wishlist(!isWishlisted);
    }

    /**
     * Gets isWishlisted
     * @return the isWishlisted value
     */
    public Boolean isWishlisted() {
        return isWishlisted ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public String toString() {
        return isWishlisted ? "Wishlisted" : "Not wishlisted";
    }

    @Override
    public boolean equals(Object other) {
        // Check if same object reference
        if (this == other) {
            return true;
        }

        // Check if other is not a Wishlist or is null
        if (!(other instanceof Wishlist)) {
            return false;
        }

        // Safe to cast since we know it is a wishlist at this point and compare value
        Wishlist otherWishlist = (Wishlist) other;
        return this.isWishlisted.equals(otherWishlist.isWishlisted);
    }

    @Override
    public int hashCode() {
        return isWishlisted.hashCode();
    }
}
