package seedu.bitebuddy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.logic.commands.EditCommand.EditFoodplaceDescriptor;
import seedu.bitebuddy.testutil.EditFoodplaceDescriptorBuilder;

public class EditFoodplaceDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditFoodplaceDescriptor descriptorWithSameValues = new EditFoodplaceDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditCommand.EditFoodplaceDescriptor editedAmy = new EditFoodplaceDescriptorBuilder(DESC_AMY)
                .withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditFoodplaceDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditFoodplaceDescriptorBuilder(DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different bitebuddy -> returns false
        editedAmy = new EditFoodplaceDescriptorBuilder(DESC_AMY).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditFoodplaceDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditCommand.EditFoodplaceDescriptor editFoodplaceDescriptor = new EditCommand.EditFoodplaceDescriptor();
        String expected = EditCommand.EditFoodplaceDescriptor.class.getCanonicalName() + "{name="
                + editFoodplaceDescriptor.getName().orElse(null) + ", phone="
                + editFoodplaceDescriptor.getPhone().orElse(null) + ", email="
                + editFoodplaceDescriptor.getEmail().orElse(null) + ", bitebuddy="
                + editFoodplaceDescriptor.getAddress().orElse(null) + ", tags="
                + editFoodplaceDescriptor.getTags().orElse(null) + "}";
        assertEquals(expected, editFoodplaceDescriptor.toString());
    }
}
