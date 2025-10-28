package seedu.bitebuddy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.DESC_MCRONALDS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.DESC_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_ADDRESS_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_BLACKLIST_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_CLOSE_TIME_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_CUISINE_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_EMAIL_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_NAME_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_NOTE_FAMOUS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_OPEN_TIME_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_PHONE_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_RATE_PRATASHOP;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_TAG_RESTAURANT;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_WISHLIST_SWENSWAN;

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.logic.commands.EditCommand.EditFoodplaceDescriptor;
import seedu.bitebuddy.testutil.EditFoodplaceDescriptorBuilder;

public class EditFoodplaceDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditFoodplaceDescriptor descriptorWithSameValues = new EditFoodplaceDescriptor(DESC_MCRONALDS);
        assertTrue(DESC_MCRONALDS.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_MCRONALDS.equals(DESC_MCRONALDS));

        // null -> returns false
        assertNotEquals(DESC_MCRONALDS, null);

        // different types -> returns false
        assertNotEquals(DESC_MCRONALDS, 5);

        // different values -> returns false
        assertFalse(DESC_MCRONALDS.equals(DESC_SWENSWAN));

        // different name -> returns false
        EditCommand.EditFoodplaceDescriptor editedMcronalds = new EditFoodplaceDescriptorBuilder(DESC_MCRONALDS)
                .withName(VALID_NAME_SWENSWAN).build();
        assertFalse(DESC_MCRONALDS.equals(editedMcronalds));

        // different phone -> returns false
        editedMcronalds = new EditFoodplaceDescriptorBuilder(DESC_MCRONALDS).withPhone(VALID_PHONE_SWENSWAN).build();
        assertFalse(DESC_MCRONALDS.equals(editedMcronalds));

        // different email -> returns false
        editedMcronalds = new EditFoodplaceDescriptorBuilder(DESC_MCRONALDS).withEmail(VALID_EMAIL_SWENSWAN).build();
        assertFalse(DESC_MCRONALDS.equals(editedMcronalds));

        // different bitebuddy -> returns false
        editedMcronalds = new EditFoodplaceDescriptorBuilder(DESC_MCRONALDS)
                .withAddress(VALID_ADDRESS_SWENSWAN).build();
        assertFalse(DESC_MCRONALDS.equals(editedMcronalds));

        // different tags -> returns false
        editedMcronalds = new EditFoodplaceDescriptorBuilder(DESC_MCRONALDS).withTags(VALID_TAG_RESTAURANT).build();
        assertFalse(DESC_MCRONALDS.equals(editedMcronalds));

        // different note -> returns false
        editedMcronalds = new EditFoodplaceDescriptorBuilder(DESC_MCRONALDS).withNote(VALID_NOTE_FAMOUS).build();
        assertFalse(DESC_MCRONALDS.equals(editedMcronalds));

        // different rate -> returns false
        editedMcronalds = new EditFoodplaceDescriptorBuilder(DESC_MCRONALDS).withRate(VALID_RATE_PRATASHOP).build();
        assertFalse(DESC_MCRONALDS.equals(editedMcronalds));

        // different cuisine -> returns false
        editedMcronalds = new EditFoodplaceDescriptorBuilder(DESC_MCRONALDS)
                .withCuisine(VALID_CUISINE_SWENSWAN).build();
        assertFalse(DESC_MCRONALDS.equals(editedMcronalds));

        // different timing -> returns false
        editedMcronalds = new EditFoodplaceDescriptorBuilder(DESC_MCRONALDS)
                .withTiming(VALID_OPEN_TIME_SWENSWAN, VALID_CLOSE_TIME_SWENSWAN).build();
        assertFalse(DESC_MCRONALDS.equals(editedMcronalds));

        // different wishlist -> returns false
        editedMcronalds = new EditFoodplaceDescriptorBuilder(DESC_MCRONALDS)
                .withWishlist(VALID_WISHLIST_SWENSWAN).build();
        assertFalse(DESC_MCRONALDS.equals(editedMcronalds));

        // different blacklist -> returns false
        editedMcronalds = new EditFoodplaceDescriptorBuilder(DESC_MCRONALDS)
                .withBlacklist(VALID_BLACKLIST_SWENSWAN).build();
        assertFalse(DESC_MCRONALDS.equals(editedMcronalds));
    }

    @Test
    public void toStringMethod() {
        EditFoodplaceDescriptor editFoodplaceDescriptor = new EditFoodplaceDescriptor();
        String expected = EditFoodplaceDescriptor.class.getCanonicalName() + "{name="
                + editFoodplaceDescriptor.getName().orElse(null) + ", phone="
                + editFoodplaceDescriptor.getPhone().orElse(null) + ", email="
                + editFoodplaceDescriptor.getEmail().orElse(null) + ", address="
                + editFoodplaceDescriptor.getAddress().orElse(null) + ", timing="
                + editFoodplaceDescriptor.getTiming().orElse(null) + ", cuisine="
                + editFoodplaceDescriptor.getCuisine().orElse(null) + ", tags="
                + editFoodplaceDescriptor.getTags().orElse(null) + ", note="
                + editFoodplaceDescriptor.getNote().orElse(null) + ", rate="
                + editFoodplaceDescriptor.getRate().orElse(null) + ", "
                + editFoodplaceDescriptor.getWishlist().orElse(null) + ", "
                + editFoodplaceDescriptor.getBlacklist().orElse(null) + "}";
        assertEquals(expected, editFoodplaceDescriptor.toString());
    }
}
