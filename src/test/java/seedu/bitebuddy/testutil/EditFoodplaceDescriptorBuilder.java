package seedu.bitebuddy.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.bitebuddy.logic.commands.EditCommand;
import seedu.bitebuddy.logic.commands.EditCommand.EditFoodplaceDescriptor;
import seedu.bitebuddy.model.foodplace.Address;
import seedu.bitebuddy.model.foodplace.Email;
import seedu.bitebuddy.model.foodplace.Foodplace;
import seedu.bitebuddy.model.foodplace.Name;
import seedu.bitebuddy.model.foodplace.Phone;
import seedu.bitebuddy.model.tag.Tag;

/**
 * A utility class to help with building EditFoodplaceDescriptor objects.
 */
public class EditFoodplaceDescriptorBuilder {

    private EditFoodplaceDescriptor descriptor;

    public EditFoodplaceDescriptorBuilder() {
        descriptor = new EditCommand.EditFoodplaceDescriptor();
    }

    public EditFoodplaceDescriptorBuilder(EditFoodplaceDescriptor descriptor) {
        this.descriptor = new EditCommand.EditFoodplaceDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditFoodplaceDescriptor} with fields containing {@code foodplace}'s details
     */
    public EditFoodplaceDescriptorBuilder(Foodplace foodplace) {
        descriptor = new EditCommand.EditFoodplaceDescriptor();
        descriptor.setName(foodplace.getName());
        descriptor.setPhone(foodplace.getPhone());
        descriptor.setEmail(foodplace.getEmail());
        descriptor.setAddress(foodplace.getAddress());
        descriptor.setTags(foodplace.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditFoodplaceDescriptor} that we are building.
     */
    public EditFoodplaceDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditFoodplaceDescriptor} that we are building.
     */
    public EditFoodplaceDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditFoodplaceDescriptor} that we are building.
     */
    public EditFoodplaceDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditFoodplaceDescriptor} that we are building.
     */
    public EditFoodplaceDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditFoodplaceDescriptor}
     * that we are building.
     */
    public EditFoodplaceDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditCommand.EditFoodplaceDescriptor build() {
        return descriptor;
    }
}
