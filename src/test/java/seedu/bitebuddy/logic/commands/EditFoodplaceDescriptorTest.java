package seedu.bitebuddy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.DESC_MCRONALDS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.DESC_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_ADDRESS_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_EMAIL_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_NAME_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_PHONE_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_TAG_RESTAURANT;

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.logic.commands.EditCommand.EditFoodplaceDescriptor;
import seedu.bitebuddy.testutil.EditFoodplaceDescriptorBuilder;

public class EditFoodplaceDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditFoodplaceDescriptor descriptorWithSameValues = new EditFoodplaceDescriptor(DESC_MCRONALDS);
        assertTrue(DESC_MCRONALDS.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_MCRONALDS.equals(DESC_MCRONALDS));

        // null -> returns false
        assertNotEquals(DESC_MCRONALDS, null);

        // different types -> returns false
        assertFalse(DESC_MCRONALDS.equals(5));

        // different values -> returns false
        assertFalse(DESC_MCRONALDS.equals(DESC_SWENSWAN));

        // different name -> returns false
        EditCommand.EditFoodplaceDescriptor editedAmy = new EditFoodplaceDescriptorBuilder(DESC_MCRONALDS)
                .withName(VALID_NAME_SWENSWAN).build();
        assertFalse(DESC_MCRONALDS.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditFoodplaceDescriptorBuilder(DESC_MCRONALDS).withPhone(VALID_PHONE_SWENSWAN).build();
        assertFalse(DESC_MCRONALDS.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditFoodplaceDescriptorBuilder(DESC_MCRONALDS).withEmail(VALID_EMAIL_SWENSWAN).build();
        assertFalse(DESC_MCRONALDS.equals(editedAmy));

        // different bitebuddy -> returns false
        editedAmy = new EditFoodplaceDescriptorBuilder(DESC_MCRONALDS).withAddress(VALID_ADDRESS_SWENSWAN).build();
        assertFalse(DESC_MCRONALDS.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditFoodplaceDescriptorBuilder(DESC_MCRONALDS).withTags(VALID_TAG_RESTAURANT).build();
        assertFalse(DESC_MCRONALDS.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditCommand.EditFoodplaceDescriptor editFoodplaceDescriptor = new EditCommand.EditFoodplaceDescriptor();
        String expected = EditCommand.EditFoodplaceDescriptor.class.getCanonicalName() + "{name="
                + editFoodplaceDescriptor.getName().orElse(null) + ", phone="
                + editFoodplaceDescriptor.getPhone().orElse(null) + ", email="
                + editFoodplaceDescriptor.getEmail().orElse(null) + ", address="
                + editFoodplaceDescriptor.getAddress().orElse(null) + ", tags="
                + editFoodplaceDescriptor.getTags().orElse(null) + ", rate="
                + editFoodplaceDescriptor.getRate().orElse(null) + "}";
        assertEquals(expected, editFoodplaceDescriptor.toString());
    }
}
