package seedu.bitebuddy.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.bitebuddy.commons.util.ToStringBuilder;
import seedu.bitebuddy.model.foodplace.Foodplace;
import seedu.bitebuddy.model.foodplace.UniqueFoodplaceList;

/**
 * Wraps all data at the bitebuddy-book level
 * Duplicates are not allowed (by .isSameFoodplace comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueFoodplaceList foodplaces;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        foodplaces = new UniqueFoodplaceList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Foodplaces in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the foodplace list with {@code foodplaces}.
     * {@code foodplaces} must not contain duplicate foodplaces.
     */
    public void setFoodplaces(List<Foodplace> foodplaces) {
        this.foodplaces.setFoodplaces(foodplaces);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setFoodplaces(newData.getFoodplaceList());
    }

    //// foodplace-level operations

    /**
     * Returns true if a foodplace with the same identity as {@code foodplace} exists in the bitebuddy book.
     */
    public boolean hasFoodplace(Foodplace foodplace) {
        requireNonNull(foodplace);
        return foodplaces.contains(foodplace);
    }

    /**
     * Adds a foodplace to the bitebuddy book.
     * The foodplace must not already exist in the bitebuddy book.
     */
    public void addFoodplace(Foodplace p) {
        foodplaces.add(p);
    }

    /**
     * Replaces the given foodplace {@code target} in the list with {@code editedFoodplace}.
     * {@code target} must exist in the bitebuddy book.
     * The foodplace identity of {@code editedFoodplace} must not be the same as another existing foodplace in the
     * bitebuddy book.
     */
    public void setFoodplace(Foodplace target, Foodplace editedFoodplace) {
        requireNonNull(editedFoodplace);

        foodplaces.setFoodplace(target, editedFoodplace);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the bitebuddy book.
     */
    public void removeFoodplace(Foodplace key) {
        foodplaces.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("foodplaces", foodplaces)
                .toString();
    }

    @Override
    public ObservableList<Foodplace> getFoodplaceList() {
        return foodplaces.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return foodplaces.equals(otherAddressBook.foodplaces);
    }

    @Override
    public int hashCode() {
        return foodplaces.hashCode();
    }
}
