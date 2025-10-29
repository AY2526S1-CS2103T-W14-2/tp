package seedu.bitebuddy.testutil;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import seedu.bitebuddy.model.foodplace.Address;
import seedu.bitebuddy.model.foodplace.Blacklist;
import seedu.bitebuddy.model.foodplace.Cuisine;
import seedu.bitebuddy.model.foodplace.Email;
import seedu.bitebuddy.model.foodplace.Foodplace;
import seedu.bitebuddy.model.foodplace.Name;
import seedu.bitebuddy.model.foodplace.Note;
import seedu.bitebuddy.model.foodplace.Phone;
import seedu.bitebuddy.model.foodplace.Rate;
import seedu.bitebuddy.model.foodplace.Timing;
import seedu.bitebuddy.model.foodplace.Wishlist;
import seedu.bitebuddy.model.tag.Tag;
import seedu.bitebuddy.model.util.SampleDataUtil;

/**
 * A utility class to help with building Foodplace objects.
 */
public class FoodplaceBuilder {

    public static final String DEFAULT_NAME = "Amy Bee Cafe";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amybeecafe@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_CUISINE = "Italian";
    public static final String DEFAULT_NOTE = "Serves the best appetisers!";
    public static final String DEFAULT_RATE = String.valueOf(0);
    public static final String DEFAULT_OPEN = "09:00";
    public static final String DEFAULT_CLOSE = "21:00";
    public static final Boolean DEFAULT_WISHLIST = false;
    public static final Boolean DEFAULT_BLACKLIST = false;

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Cuisine cuisine;
    private Timing timing;
    private Set<Tag> tags;
    private Note note;
    private Rate rate;
    private Wishlist wishlist;
    private Blacklist blacklist;

    /**
     * Creates a {@code FoodplaceBuilder} with the default details.
     */
    public FoodplaceBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        cuisine = new Cuisine(DEFAULT_CUISINE);
        tags = new HashSet<>();
        note = new Note(DEFAULT_NOTE);
        rate = new Rate(Integer.valueOf(DEFAULT_RATE));
        timing = new Timing(LocalTime.parse(DEFAULT_OPEN), LocalTime.parse(DEFAULT_CLOSE));
        wishlist = new Wishlist(DEFAULT_WISHLIST);
        blacklist = new Blacklist(DEFAULT_BLACKLIST);
    }

    /**
     * Initializes the FoodplaceBuilder with the data of {@code foodplaceToCopy}.
     */
    public FoodplaceBuilder(Foodplace foodplaceToCopy) {
        name = foodplaceToCopy.getName();
        phone = foodplaceToCopy.getPhone();
        email = foodplaceToCopy.getEmail();
        address = foodplaceToCopy.getAddress();
        timing = foodplaceToCopy.getTiming();
        cuisine = foodplaceToCopy.getCuisine();
        tags = new HashSet<>(foodplaceToCopy.getTags());
        note = foodplaceToCopy.getNote();
        rate = foodplaceToCopy.getRate();
        wishlist = foodplaceToCopy.getWishlist();
        blacklist = foodplaceToCopy.getBlacklist();
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
     * Sets the {@code Note} of the {@code Foodplace} that we are building.
     */
    public FoodplaceBuilder withNote(String note) {
        this.note = new Note(note);
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

    /**
     * Sets the {@code Cuisine} of the {@code Foodplace} that we are building.
     */
    public FoodplaceBuilder withCuisine(String cuisine) {
        this.cuisine = new Cuisine(cuisine);
        return this;
    }

    /**
     * Sets the {@code Timing} of the {@code Foodplace} that we are building.
     * Expects opening and closing times in HH:mm format.
     */
    public FoodplaceBuilder withTiming(String open, String close) {
        this.timing = new Timing(open, close);
        return this;
    }

    /**
     * Sets the {@code Timing} of the {@code Foodplace} that we are building.
     * Expects time range in "HH:mm-HH:mm" format.
     */
    public FoodplaceBuilder withTiming(String timeRange) {
        this.timing = new Timing(timeRange);
        return this;
    }

    /**
     * Sets the {@code Wishlist} of the {@code Foodplace} that we are building.
     */
    public FoodplaceBuilder withWishlist(Boolean isWishlisted) {
        this.wishlist = new Wishlist(isWishlisted);
        return this;
    }

    /**
     * Sets the {@code Blacklist} of the {@code Foodplace} that we are building.
     */
    public FoodplaceBuilder withBlacklist(Boolean isBlacklisted) {
        this.blacklist = new Blacklist(isBlacklisted);
        return this;
    }

    public Foodplace build() {
        return new Foodplace(name, phone, email, address, timing, cuisine, tags, note, rate, wishlist, blacklist);
    }

}
