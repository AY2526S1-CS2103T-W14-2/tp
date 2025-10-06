package seedu.bitebuddy.model.foodplace.exceptions;

/**
 * Signals that the operation will result in duplicate Foodplaces (Foodplaces are considered duplicates if they have
 * the same identity).
 */
public class DuplicateFoodplaceException extends RuntimeException {
    public DuplicateFoodplaceException() {
        super("Operation would result in duplicate foodplaces");
    }
}
