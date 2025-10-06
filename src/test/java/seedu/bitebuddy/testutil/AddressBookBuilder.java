package seedu.bitebuddy.testutil;

import seedu.bitebuddy.model.AddressBook;
import seedu.bitebuddy.model.foodplace.Foodplace;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withFoodplace("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Foodplace} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withFoodplace(Foodplace foodplace) {
        addressBook.addFoodplace(foodplace);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
