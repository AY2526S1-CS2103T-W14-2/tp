package seedu.bitebuddy.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_ADDRESS_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_TAG_RESTAURANT;
import static seedu.bitebuddy.testutil.Assert.assertThrows;
import static seedu.bitebuddy.testutil.TypicalFoodplace.PRATA;
import static seedu.bitebuddy.testutil.TypicalFoodplace.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.bitebuddy.model.foodplace.Foodplace;
import seedu.bitebuddy.model.foodplace.exceptions.DuplicateFoodplaceException;
import seedu.bitebuddy.testutil.FoodplaceBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getFoodplaceList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateFoodplaces_throwsDuplicateFoodplaceException() {
        // Two foodplaces with the same identity fields
        Foodplace editedAlice = new FoodplaceBuilder(PRATA).withAddress(VALID_ADDRESS_SWENSWAN)
                .withTags(VALID_TAG_RESTAURANT).build();
        List<Foodplace> newFoodplaces = Arrays.asList(PRATA, editedAlice);
        AddressBookStub newData = new AddressBookStub(newFoodplaces);

        assertThrows(DuplicateFoodplaceException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasFoodplace_nullFoodplace_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasFoodplace(null));
    }

    @Test
    public void hasFoodplace_foodplaceNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasFoodplace(PRATA));
    }

    @Test
    public void hasFoodplace_foodplaceInAddressBook_returnsTrue() {
        addressBook.addFoodplace(PRATA);
        assertTrue(addressBook.hasFoodplace(PRATA));
    }

    @Test
    public void hasFoodplace_foodplaceWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addFoodplace(PRATA);
        Foodplace editedAlice = new FoodplaceBuilder(PRATA).withAddress(VALID_ADDRESS_SWENSWAN)
                .withTags(VALID_TAG_RESTAURANT).build();
        assertTrue(addressBook.hasFoodplace(editedAlice));
    }

    @Test
    public void getFoodplaceList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getFoodplaceList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{foodplaces=" + addressBook.getFoodplaceList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose foodplaces list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Foodplace> foodplaces = FXCollections.observableArrayList();

        AddressBookStub(Collection<Foodplace> foodplaces) {
            this.foodplaces.setAll(foodplaces);
        }

        @Override
        public ObservableList<Foodplace> getFoodplaceList() {
            return foodplaces;
        }
    }

}
