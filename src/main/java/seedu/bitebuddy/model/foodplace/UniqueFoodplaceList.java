package seedu.bitebuddy.model.foodplace;

import static java.util.Objects.requireNonNull;
import static seedu.bitebuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.bitebuddy.model.foodplace.exceptions.DuplicateFoodplaceException;
import seedu.bitebuddy.model.foodplace.exceptions.FoodplaceNotFoundException;

/**
 * A list of foodplaces that enforces uniqueness between its elements and does not allow nulls.
 * A foodplace is considered unique by comparing using {@code Foodplace#isSamePerson(Foodplace)}. As such, adding and updating of
 * foodplaces uses Foodplace#isSamePerson(Foodplace) for equality so as to ensure that the foodplace being added or updated is
 * unique in terms of identity in the UniqueFoodplaceList. However, the removal of a foodplace uses Foodplace#equals(Object) so
 * as to ensure that the foodplace with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Foodplace#isSameFoodplace(Foodplace)
 */
public class UniqueFoodplaceList implements Iterable<Foodplace> {

    private final ObservableList<Foodplace> internalList = FXCollections.observableArrayList();
    private final ObservableList<Foodplace> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent foodplace as the given argument.
     */
    public boolean contains(Foodplace toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameFoodplace);
    }

    /**
     * Adds a foodplace to the list.
     * The foodplace must not already exist in the list.
     */
    public void add(Foodplace toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateFoodplaceException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the foodplace {@code target} in the list with {@code editedFoodplace}.
     * {@code target} must exist in the list.
     * The foodplace identity of {@code editedFoodplace} must not be the same as another existing foodplace in the list.
     */
    public void setFoodplace(Foodplace target, Foodplace editedFoodplace) {
        requireAllNonNull(target, editedFoodplace);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new FoodplaceNotFoundException();
        }

        if (!target.isSameFoodplace(editedFoodplace) && contains(editedFoodplace)) {
            throw new DuplicateFoodplaceException();
        }

        internalList.set(index, editedFoodplace);
    }

    /**
     * Removes the equivalent foodplace from the list.
     * The foodplace must exist in the list.
     */
    public void remove(Foodplace toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new FoodplaceNotFoundException();
        }
    }

    public void setFoodplaces(UniqueFoodplaceList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code foodplaces}.
     * {@code foodplaces} must not contain duplicate foodplaces.
     */
    public void setFoodplaces(List<Foodplace> foodplaces) {
        requireAllNonNull(foodplaces);
        if (!foodplacesAreUnique(foodplaces)) {
            throw new DuplicateFoodplaceException();
        }

        internalList.setAll(foodplaces);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Foodplace> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Foodplace> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueFoodplaceList)) {
            return false;
        }

        UniqueFoodplaceList otherUniqueFoodplaceList = (UniqueFoodplaceList) other;
        return internalList.equals(otherUniqueFoodplaceList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code foodplaces} contains only unique foodplaces.
     */
    private boolean foodplacesAreUnique(List<Foodplace> foodplaces) {
        for (int i = 0; i < foodplaces.size() - 1; i++) {
            for (int j = i + 1; j < foodplaces.size(); j++) {
                if (foodplaces.get(i).isSameFoodplace(foodplaces.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
