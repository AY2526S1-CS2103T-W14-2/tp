package seedu.bitebuddy.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.bitebuddy.commons.exceptions.IllegalValueException;
import seedu.bitebuddy.model.AddressBook;
import seedu.bitebuddy.model.ReadOnlyAddressBook;
import seedu.bitebuddy.model.foodplace.Foodplace;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_FOODPLACE = "Foodplace list contains duplicate foodplace(s).";

    private final List<JsonAdaptedFoodplace> foodplaces = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given foodplaces.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("foodplaces") List<JsonAdaptedFoodplace> foodplaces) {
        this.foodplaces.addAll(foodplaces);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        foodplaces.addAll(source.getFoodplaceList().stream().map(JsonAdaptedFoodplace::new).collect(Collectors.toList()));
    }

    /**
     * Converts this bitebuddy book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedFoodplace jsonAdaptedFoodplace : foodplaces) {
            Foodplace foodplace = jsonAdaptedFoodplace.toModelType();
            if (addressBook.hasFoodplace(foodplace)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_FOODPLACE);
            }
            addressBook.addFoodplace(foodplace);
        }
        return addressBook;
    }

}
