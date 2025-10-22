package seedu.bitebuddy.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.bitebuddy.commons.core.GuiSettings;
import seedu.bitebuddy.model.foodplace.Foodplace;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Foodplace> PREDICATE_SHOW_ALL_FOODPLACES = unused -> true;
    Predicate<Foodplace> PREDICATE_SHOW_ALL_WISHLISTED_FOODPLACES = fp -> fp.getWishlist().isWishlisted;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' bitebuddy book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' bitebuddy book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces bitebuddy book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a foodplace with the same identity as {@code foodplace} exists in the bitebuddy book.
     */
    boolean hasFoodplace(Foodplace foodplace);

    /**
     * Deletes the given foodplace.
     * The foodplace must exist in the bitebuddy book.
     */
    void deleteFoodplace(Foodplace target);

    /**
     * Adds the given foodplace.
     * {@code foodplace} must not already exist in the bitebuddy book.
     */
    void addFoodplace(Foodplace foodplace);

    /**
     * Replaces the given foodplace {@code target} with {@code editedFoodplace}.
     * {@code target} must exist in the bitebuddy book.
     * The foodplace identity of {@code editedFoodplace} must not be the same as another existing foodplace in the
     * bitebuddy book.
     */
    void setFoodplace(Foodplace target, Foodplace editedFoodplace);

    /** Returns an unmodifiable view of the filtered foodplace list */
    ObservableList<Foodplace> getFilteredFoodplaceList();

    /**
     * Updates the filter of the filtered foodplace list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredFoodplaceList(Predicate<Foodplace> predicate);
}
