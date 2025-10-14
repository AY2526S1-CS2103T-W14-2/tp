package seedu.bitebuddy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.DESC_MCRONALDS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.DESC_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_ADDRESS_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_EMAIL_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_NAME_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_NOTE_FAMOUS;
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
        assertFalse(DESC_MCRONALDS.equals(null));

        // different types -> returns false
        assertFalse(DESC_MCRONALDS.equals(5));

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
        editedMcronalds = new EditFoodplaceDescriptorBuilder(DESC_MCRONALDS).withAddress(VALID_ADDRESS_SWENSWAN).build();
        assertFalse(DESC_MCRONALDS.equals(editedMcronalds));

        // different tags -> returns false
        editedMcronalds = new EditFoodplaceDescriptorBuilder(DESC_MCRONALDS).withTags(VALID_TAG_RESTAURANT).build();
        assertFalse(DESC_MCRONALDS.equals(editedMcronalds));

        // different note -> returns false
        editedMcronalds = new EditFoodplaceDescriptorBuilder(DESC_MCRONALDS).withNote(VALID_NOTE_FAMOUS).build();
        assertFalse(DESC_MCRONALDS.equals(editedMcronalds));
    }

    @Test
    public void toStringMethod() {
        EditCommand.EditFoodplaceDescriptor editFoodplaceDescriptor = new EditCommand.EditFoodplaceDescriptor();
        String expected = EditCommand.EditFoodplaceDescriptor.class.getCanonicalName() + "{name="
                + editFoodplaceDescriptor.getName().orElse(null) + ", phone="
                + editFoodplaceDescriptor.getPhone().orElse(null) + ", email="
                + editFoodplaceDescriptor.getEmail().orElse(null) + ", bitebuddy="
                + editFoodplaceDescriptor.getAddress().orElse(null) + ", tags="
                + editFoodplaceDescriptor.getTags().orElse(null) + ", note="
                + editFoodplaceDescriptor.getNote().orElse(null) + "}";
        assertEquals(expected, editFoodplaceDescriptor.toString());
    }
}
