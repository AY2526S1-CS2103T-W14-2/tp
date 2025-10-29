package seedu.bitebuddy.model;

import static java.util.Objects.requireNonNull;
import static seedu.bitebuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.bitebuddy.commons.core.GuiSettings;
import seedu.bitebuddy.commons.core.LogsCenter;
import seedu.bitebuddy.model.foodplace.Foodplace;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final ObservableList<Foodplace> observableFoodplaces;
    private final SortedList<Foodplace> sortedFoodplaces;
    private final FilteredList<Foodplace> filteredFoodplaces;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.log(Level.FINE, () -> "Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        observableFoodplaces = this.addressBook.getFoodplaceList();
        sortedFoodplaces = new SortedList<>(observableFoodplaces, (fp1, fp2) -> {
            return comparePinnedStatus(fp1, fp2);
        });
        filteredFoodplaces = new FilteredList<>(sortedFoodplaces);
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    private int comparePinnedStatus(Foodplace fp1, Foodplace fp2) {
        // Pinned goes first
        int cmp = Boolean.compare(fp2.getPinned().isPinned(), fp1.getPinned().isPinned());
        if (cmp != 0) {
            return cmp;
        }

        // If both are pinned, sort alphabetically by name
        if (fp1.getPinned().isPinned() && fp2.getPinned().isPinned()) {
            return fp1.getName().fullName.compareToIgnoreCase(fp2.getName().fullName);
        }

        // If both are unpinned, keep original order (stable)
        return Integer.compare(
                observableFoodplaces.indexOf(fp1),
                observableFoodplaces.indexOf(fp2)
        );
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasFoodplace(Foodplace foodplace) {
        requireNonNull(foodplace);
        return addressBook.hasFoodplace(foodplace);
    }

    @Override
    public void deleteFoodplace(Foodplace target) {
        addressBook.removeFoodplace(target);
    }

    @Override
    public void addFoodplace(Foodplace foodplace) {
        addressBook.addFoodplace(foodplace);
        updateFilteredFoodplaceList(PREDICATE_SHOW_ALL_FOODPLACES);
    }

    @Override
    public void setFoodplace(Foodplace target, Foodplace editedFoodplace) {
        requireAllNonNull(target, editedFoodplace);

        addressBook.setFoodplace(target, editedFoodplace);
    }

    //=========== Filtered Foodplace List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Foodplace} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Foodplace> getFilteredFoodplaceList() {
        return filteredFoodplaces;
    }

    @Override
    public void updateFilteredFoodplaceList(Predicate<Foodplace> predicate) {
        requireNonNull(predicate);
        filteredFoodplaces.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredFoodplaces.equals(otherModelManager.filteredFoodplaces);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressBook, userPrefs, filteredFoodplaces);
    }
}
