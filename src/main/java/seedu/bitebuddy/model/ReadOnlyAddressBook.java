package seedu.bitebuddy.model;

import javafx.collections.ObservableList;
import seedu.bitebuddy.model.foodplace.Foodplace;

/**
 * Unmodifiable view of an bitebuddy book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the foodplaces list.
     * This list will not contain any duplicate foodplaces.
     */
    ObservableList<Foodplace> getFoodplaceList();

}
