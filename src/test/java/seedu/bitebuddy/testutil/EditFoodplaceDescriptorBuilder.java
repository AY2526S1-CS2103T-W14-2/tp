package seedu.bitebuddy.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.bitebuddy.logic.commands.EditCommand;
import seedu.bitebuddy.logic.commands.EditCommand.EditFoodplaceDescriptor;
import seedu.bitebuddy.model.foodplace.Address;
import seedu.bitebuddy.model.foodplace.Blacklist;
import seedu.bitebuddy.model.foodplace.Email;
import seedu.bitebuddy.model.foodplace.Foodplace;
import seedu.bitebuddy.model.foodplace.Name;
import seedu.bitebuddy.model.foodplace.Note;
import seedu.bitebuddy.model.foodplace.Phone;
import seedu.bitebuddy.model.foodplace.Rate;
import seedu.bitebuddy.model.foodplace.Timing;
import seedu.bitebuddy.model.foodplace.Wishlist;
import seedu.bitebuddy.model.tag.Tag;

/**
 * A utility class to help with building EditFoodplaceDescriptor objects.
 */
public class EditFoodplaceDescriptorBuilder {

    private final EditFoodplaceDescriptor descriptor;

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
        descriptor.setCuisine(foodplace.getCuisine());
        descriptor.setTags(foodplace.getTags());
        descriptor.setNote(foodplace.getNote());
        descriptor.setRate(foodplace.getRate());
        descriptor.setWishlist(foodplace.getWishlist());
        descriptor.setBlacklist(foodplace.getBlacklist());
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
     * Sets the {@code Cuisine} of the {@code EditFoodplaceDescriptor} that we are building.
     */
    public EditFoodplaceDescriptorBuilder withCuisine(String cuisine) {
        descriptor.setCuisine(new seedu.bitebuddy.model.foodplace.Cuisine(cuisine));
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

    /**
     * Sets the {@code Note} of the {@code EditFoodplaceDescriptor} that we are building.
     */
    public EditFoodplaceDescriptorBuilder withNote(String note) {
        descriptor.setNote(new Note(note));
        return this;
    }

    /**
     * Sets the {@code Rate} of the {@code EditFoodplaceDescriptor} that we are building.
     */
    public EditFoodplaceDescriptorBuilder withRate(String rate) {
        descriptor.setRate(new Rate(Integer.valueOf(rate)));
        return this;
    }

    /**
     * Sets the {@code Timing} of the {@code EditFoodplaceDescriptor} that we are building.
     */
    public EditFoodplaceDescriptorBuilder withTiming(String openTime, String closeTime) {
        descriptor.setTiming(new Timing(openTime, closeTime));
        return this;
    }

    /**
     * Sets the {@code Wishlist} of the {@code EditFoodplaceDescriptor} that we are building.
     */
    public EditFoodplaceDescriptorBuilder withWishlist(Boolean isWishlisted) {
        descriptor.setWishlist(new Wishlist(isWishlisted));
        return this;
    }

    /**
     * Sets the {@code Blacklist} of the {@code EditFoodplaceDescriptor} that we are building.
     */
    public EditFoodplaceDescriptorBuilder withBlacklist(Boolean isBlacklisted) {
        descriptor.setBlacklist(new Blacklist(isBlacklisted));
        return this;
    }

    public EditCommand.EditFoodplaceDescriptor build() {
        return descriptor;
    }


}
