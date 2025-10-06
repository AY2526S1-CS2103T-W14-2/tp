package seedu.bitebuddy.model.foodplace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.bitebuddy.testutil.Assert.assertThrows;
import static seedu.bitebuddy.testutil.TypicalFoodplace.ALICE;
import static seedu.bitebuddy.testutil.TypicalFoodplace.BOB;

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
        assertTrue(ALICE.isSameFoodplace(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameFoodplace(null));

        // same name, all other attributes different -> returns true
        Foodplace editedAlice = new FoodplaceBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameFoodplace(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new FoodplaceBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameFoodplace(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Foodplace editedBob = new FoodplaceBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSameFoodplace(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new FoodplaceBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSameFoodplace(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Foodplace aliceCopy = new FoodplaceBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different foodplace -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Foodplace editedAlice = new FoodplaceBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new FoodplaceBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new FoodplaceBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different bitebuddy -> returns false
        editedAlice = new FoodplaceBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new FoodplaceBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Foodplace.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", bitebuddy=" + ALICE.getAddress() + ", tags=" + ALICE.getTags() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
