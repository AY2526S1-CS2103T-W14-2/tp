package seedu.bitebuddy.model.foodplace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_ADDRESS_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_EMAIL_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_NAME_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_PHONE_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_RATE_PRATASHOP;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_TAG_RESTAURANT;
import static seedu.bitebuddy.testutil.Assert.assertThrows;
import static seedu.bitebuddy.testutil.TypicalFoodplace.PRATASHOP;
import static seedu.bitebuddy.testutil.TypicalFoodplace.SWENSWAN;

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.testutil.FoodplaceBuilder;

public class FoodplaceTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Foodplace foodplace = new FoodplaceBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> foodplace.getTags().removeIf(t -> true));
    }

    @Test
    public void isSameFoodplace() {
        // same object -> returns true
        assertTrue(PRATASHOP.isSameFoodplace(PRATASHOP));

        // null -> returns false
        assertFalse(PRATASHOP.isSameFoodplace(null));

        // same identity fields, different tags -> returns true
        Foodplace editedAlice = new FoodplaceBuilder(PRATASHOP).withTags(VALID_TAG_RESTAURANT).build();
        assertTrue(PRATASHOP.isSameFoodplace(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new FoodplaceBuilder(PRATASHOP).withName(VALID_NAME_SWENSWAN).build();
        assertFalse(PRATASHOP.isSameFoodplace(editedAlice));

        // different phone -> returns false
        editedAlice = new FoodplaceBuilder(PRATASHOP).withPhone(VALID_PHONE_SWENSWAN).build();
        assertFalse(PRATASHOP.isSameFoodplace(editedAlice));

        // different email -> returns false
        editedAlice = new FoodplaceBuilder(PRATASHOP).withEmail(VALID_EMAIL_SWENSWAN).build();
        assertFalse(PRATASHOP.isSameFoodplace(editedAlice));

        // different address -> returns false
        editedAlice = new FoodplaceBuilder(PRATASHOP).withAddress(VALID_ADDRESS_SWENSWAN).build();
        assertFalse(PRATASHOP.isSameFoodplace(editedAlice));

        // name differs in case, all other attributes same -> returns true
        Foodplace editedBob = new FoodplaceBuilder(SWENSWAN).withName(VALID_NAME_SWENSWAN.toLowerCase()).build();
        assertTrue(SWENSWAN.isSameFoodplace(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Foodplace aliceCopy = new FoodplaceBuilder(PRATASHOP).build();
        assertTrue(PRATASHOP.equals(aliceCopy));

        // same object -> returns true
        assertTrue(PRATASHOP.equals(PRATASHOP));

        // null -> returns false
        assertNotEquals(PRATASHOP, null);

        // different type -> returns false
        assertFalse(PRATASHOP.equals(5));

        // different foodplace -> returns false
        assertFalse(PRATASHOP.equals(SWENSWAN));

        // different name -> returns false
        Foodplace editedAlice = new FoodplaceBuilder(PRATASHOP).withName(VALID_NAME_SWENSWAN).build();
        assertFalse(PRATASHOP.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new FoodplaceBuilder(PRATASHOP).withPhone(VALID_PHONE_SWENSWAN).build();
        assertFalse(PRATASHOP.equals(editedAlice));

        // different email -> returns false
        editedAlice = new FoodplaceBuilder(PRATASHOP).withEmail(VALID_EMAIL_SWENSWAN).build();
        assertFalse(PRATASHOP.equals(editedAlice));

        // different address -> returns false
        editedAlice = new FoodplaceBuilder(PRATASHOP).withAddress(VALID_ADDRESS_SWENSWAN).build();
        assertFalse(PRATASHOP.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new FoodplaceBuilder(PRATASHOP).withTags(VALID_TAG_RESTAURANT).build();
        assertFalse(PRATASHOP.equals(editedAlice));

        // different rating -> returns false
        editedAlice = new FoodplaceBuilder(PRATASHOP).withRate(VALID_RATE_PRATASHOP).build();
        assertFalse(PRATASHOP.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Foodplace.class.getCanonicalName() + "{name=" + PRATASHOP.getName() + ", phone="
                + PRATASHOP.getPhone()
                + ", email=" + PRATASHOP.getEmail() + ", address=" + PRATASHOP.getAddress()
                + ", cuisine=" + PRATASHOP.getCuisine() + ", tags=" + PRATASHOP.getTags() + ", rate="
                + PRATASHOP.getRate() + "}";
        assertEquals(expected, PRATASHOP.toString());
    }
}
