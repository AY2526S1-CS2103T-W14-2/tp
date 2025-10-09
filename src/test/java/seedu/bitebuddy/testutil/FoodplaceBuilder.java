package seedu.bitebuddy.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.bitebuddy.model.foodplace.Address;
import seedu.bitebuddy.model.foodplace.Email;
import seedu.bitebuddy.model.foodplace.Foodplace;
import seedu.bitebuddy.model.foodplace.Name;
import seedu.bitebuddy.model.foodplace.Phone;
import seedu.bitebuddy.model.foodplace.Rate;
import seedu.bitebuddy.model.tag.Tag;
import seedu.bitebuddy.model.util.SampleDataUtil;

/**
 * A utility class to help with building Foodplace objects.
 */
public class FoodplaceBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_RATE = String.valueOf(Rate.DEFAULT);

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private Rate rate;

    /**
     * Creates a {@code FoodplaceBuilder} with the default details.
     */
    public FoodplaceBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        rate = new Rate(Integer.valueOf(DEFAULT_RATE));
    }

    /**
     * Initializes the FoodplaceBuilder with the data of {@code foodplaceToCopy}.
     */
    public FoodplaceBuilder(Foodplace foodplaceToCopy) {
        name = foodplaceToCopy.getName();
        phone = foodplaceToCopy.getPhone();
        email = foodplaceToCopy.getEmail();
        address = foodplaceToCopy.getAddress();
        tags = new HashSet<>(foodplaceToCopy.getTags());
        rate = foodplaceToCopy.getRate();
    }

    /**
     * Sets the {@code Name} of the {@code Foodplace} that we are building.
     */
    public FoodplaceBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Foodplace} that we are building.
     */
    public FoodplaceBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Foodplace} that we are building.
     */
    public FoodplaceBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Foodplace} that we are building.
     */
    public FoodplaceBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Foodplace} that we are building.
     */
    public FoodplaceBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Rate} of the {@code Foodplace} that we are building.
     */
    public FoodplaceBuilder withRate(String rate) {
        try {
            this.rate = new Rate(Integer.valueOf(rate));
        } catch (NumberFormatException e) {
            this.rate = new Rate();
        }
        return this;
    }

    public Foodplace build() {
        return new Foodplace(name, phone, email, address, tags, rate);
    }

}
