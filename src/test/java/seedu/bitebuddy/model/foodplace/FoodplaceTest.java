package seedu.bitebuddy.model.foodplace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_ADDRESS_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_EMAIL_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_NAME_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_PHONE_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_TAG_RESTAURANT;
import static seedu.bitebuddy.testutil.Assert.assertThrows;
import static seedu.bitebuddy.testutil.TypicalFoodplace.PRATA;
import static seedu.bitebuddy.testutil.TypicalFoodplace.SWENSWAN;

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.testutil.FoodplaceBuilder;

public class FoodplaceTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Foodplace foodplace = new FoodplaceBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> foodplace.getTags().remove(0));
    }

    @Test
    public void isSameFoodplace() {
        // same object -> returns true
        assertTrue(PRATA.isSameFoodplace(PRATA));

        // null -> returns false
        assertFalse(PRATA.isSameFoodplace(null));

        // same name, all other attributes different -> returns true
        Foodplace editedAlice = new FoodplaceBuilder(PRATA).withPhone(VALID_PHONE_SWENSWAN)
                .withEmail(VALID_EMAIL_SWENSWAN).withAddress(VALID_ADDRESS_SWENSWAN)
                .withTags(VALID_TAG_RESTAURANT).build();
        assertTrue(PRATA.isSameFoodplace(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new FoodplaceBuilder(PRATA).withName(VALID_NAME_SWENSWAN).build();
        assertFalse(PRATA.isSameFoodplace(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Foodplace editedBob = new FoodplaceBuilder(SWENSWAN).withName(VALID_NAME_SWENSWAN.toLowerCase()).build();
        assertFalse(SWENSWAN.isSameFoodplace(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_SWENSWAN + " ";
        editedBob = new FoodplaceBuilder(SWENSWAN).withName(nameWithTrailingSpaces).build();
        assertFalse(SWENSWAN.isSameFoodplace(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Foodplace aliceCopy = new FoodplaceBuilder(PRATA).build();
        assertTrue(PRATA.equals(aliceCopy));

        // same object -> returns true
        assertTrue(PRATA.equals(PRATA));

        // null -> returns false
        assertFalse(PRATA.equals(null));

        // different type -> returns false
        assertFalse(PRATA.equals(5));

        // different foodplace -> returns false
        assertFalse(PRATA.equals(SWENSWAN));

        // different name -> returns false
        Foodplace editedAlice = new FoodplaceBuilder(PRATA).withName(VALID_NAME_SWENSWAN).build();
        assertFalse(PRATA.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new FoodplaceBuilder(PRATA).withPhone(VALID_PHONE_SWENSWAN).build();
        assertFalse(PRATA.equals(editedAlice));

        // different email -> returns false
        editedAlice = new FoodplaceBuilder(PRATA).withEmail(VALID_EMAIL_SWENSWAN).build();
        assertFalse(PRATA.equals(editedAlice));

        // different bitebuddy -> returns false
        editedAlice = new FoodplaceBuilder(PRATA).withAddress(VALID_ADDRESS_SWENSWAN).build();
        assertFalse(PRATA.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new FoodplaceBuilder(PRATA).withTags(VALID_TAG_RESTAURANT).build();
        assertFalse(PRATA.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Foodplace.class.getCanonicalName() + "{name=" + PRATA.getName() + ", phone="
                + PRATA.getPhone()
                + ", email=" + PRATA.getEmail() + ", bitebuddy=" + PRATA.getAddress() + ", tags="
                + PRATA.getTags() + "}";
        assertEquals(expected, PRATA.toString());
    }
}
