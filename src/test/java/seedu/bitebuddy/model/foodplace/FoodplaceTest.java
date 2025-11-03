package seedu.bitebuddy.model.foodplace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_ADDRESS_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_BLACKLIST_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_CUISINE_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_EMAIL_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_NAME_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_NOTE_SERVICE;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_PHONE_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_RATE_PRATASHOP;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_TAG_RESTAURANT;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_WISHLIST_SWENSWAN;
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
        Foodplace editedPrata = new FoodplaceBuilder(PRATASHOP).withTags(VALID_TAG_RESTAURANT).build();
        assertTrue(PRATASHOP.isSameFoodplace(editedPrata));

        // different name, all other attributes same -> returns false
        editedPrata = new FoodplaceBuilder(PRATASHOP).withName(VALID_NAME_SWENSWAN).build();
        assertFalse(PRATASHOP.isSameFoodplace(editedPrata));

        // different address -> returns false
        editedPrata = new FoodplaceBuilder(PRATASHOP).withAddress(VALID_ADDRESS_SWENSWAN).build();
        assertFalse(PRATASHOP.isSameFoodplace(editedPrata));

        // name differs in case, all other attributes same -> returns true
        Foodplace editedSwenswan = new FoodplaceBuilder(SWENSWAN).withName(VALID_NAME_SWENSWAN.toLowerCase()).build();
        assertTrue(SWENSWAN.isSameFoodplace(editedSwenswan));
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
        assertNotEquals(PRATASHOP, 5);

        // different foodplace -> returns false
        assertFalse(PRATASHOP.equals(SWENSWAN));

        // different name -> returns false
        Foodplace editedPrata = new FoodplaceBuilder(PRATASHOP).withName(VALID_NAME_SWENSWAN).build();
        assertFalse(PRATASHOP.equals(editedPrata));

        // different phone -> returns false
        editedPrata = new FoodplaceBuilder(PRATASHOP).withPhone(VALID_PHONE_SWENSWAN).build();
        assertFalse(PRATASHOP.equals(editedPrata));

        // different email -> returns false
        editedPrata = new FoodplaceBuilder(PRATASHOP).withEmail(VALID_EMAIL_SWENSWAN).build();
        assertFalse(PRATASHOP.equals(editedPrata));

        // different address -> returns false
        editedPrata = new FoodplaceBuilder(PRATASHOP).withAddress(VALID_ADDRESS_SWENSWAN).build();
        assertFalse(PRATASHOP.equals(editedPrata));

        // different tags -> returns false
        editedPrata = new FoodplaceBuilder(PRATASHOP).withTags(VALID_TAG_RESTAURANT).build();
        assertFalse(PRATASHOP.equals(editedPrata));

        // different note -> return false
        editedPrata = new FoodplaceBuilder(PRATASHOP).withNote(VALID_NOTE_SERVICE).build();
        assertFalse(PRATASHOP.equals(editedPrata));

        // different rating -> returns false
        editedPrata = new FoodplaceBuilder(PRATASHOP).withRate(VALID_RATE_PRATASHOP).build();
        assertFalse(PRATASHOP.equals(editedPrata));

        // different wishlist state -> return false
        editedPrata = new FoodplaceBuilder(PRATASHOP).withWishlist(VALID_WISHLIST_SWENSWAN).build();
        assertFalse(PRATASHOP.equals(editedPrata));

        // different blacklist state -> return false
        editedPrata = new FoodplaceBuilder(PRATASHOP).withBlacklist(VALID_BLACKLIST_SWENSWAN).build();
        assertFalse(PRATASHOP.equals(editedPrata));

        // different cuisine -> returns false
        editedPrata = new FoodplaceBuilder(PRATASHOP).withCuisine(VALID_CUISINE_SWENSWAN).build();
        assertFalse(PRATASHOP.equals(editedPrata));

        // different timing -> returns false
        editedPrata = new FoodplaceBuilder(PRATASHOP)
                .withTiming("10:00", "22:00").build();
        assertFalse(PRATASHOP.equals(editedPrata));
    }

    @Test
    public void toStringMethod() {
        String expected = "{name=" + PRATASHOP.getName() + ", phone="
                + PRATASHOP.getPhone()
                + ", email=" + PRATASHOP.getEmail() + ", address=" + PRATASHOP.getAddress()
                + ", timing=" + PRATASHOP.getTiming() + ", cuisine=" + PRATASHOP.getCuisine()
                + ", tags=" + PRATASHOP.getTags() + ", note=" + PRATASHOP.getNote()
                + ", rate=" + PRATASHOP.getRate() + ", " + PRATASHOP.getWishlist()
                + ", " + PRATASHOP.getBlacklist() + "}";
        assertEquals(expected, PRATASHOP.toString());
    }
}
