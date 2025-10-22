package seedu.bitebuddy.model.foodplace;

import static seedu.bitebuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.bitebuddy.commons.util.ToStringBuilder;
import seedu.bitebuddy.model.tag.Tag;

/**
 * Represents a Foodplace in the bitebuddy book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Foodplace {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Address address;

    // Data fields
    private final Pinned pinned;

    // Optional fields
    private final Set<Tag> tags = new HashSet<>();
    private final Note note;
    private final Rate rate;

    /**
     * Constructs a {@code Foodplace} with all fields, defaulting {@link Pinned} to false.
     *
     * <p>Every field must be present and not null.
     *
     * @param name    The name of the foodplace
     * @param phone   The phone number of the foodplace
     * @param email   The email address of the foodplace
     * @param address The physical address of the foodplace
     * @param tags    The set of tags associated with the foodplace
     * @param note    The note for the foodplace
     * @param rate    The rating of the foodplace
     */
    public Foodplace(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Note note, Rate rate) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.note = note;
        this.rate = rate;
        this.pinned = new Pinned(false);
    }

    /**
     * Constructs a {@code Foodplace} with all fields, including the pinned status.
     *
     * <p>Every field must be present and not null.
     *
     * @param name    The name of the foodplace
     * @param phone   The phone number of the foodplace
     * @param email   The email address of the foodplace
     * @param address The physical address of the foodplace
     * @param tags    The set of tags associated with the foodplace
     * @param note    The note for the foodplace
     * @param rate    The rating of the foodplace
     * @param pinned  The pinned status of the foodplace
     */
    public Foodplace(Name name, Phone phone, Email email, Address address,
                     Set<Tag> tags, Note note, Rate rate, Pinned pinned) {
        requireAllNonNull(name, phone, email, address, tags, pinned);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.note = note;
        this.rate = rate;
        this.pinned = pinned;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Note getNote() {
        return note;
    }

    public Rate getRate() {
        return rate;
    }

    public Pinned getPinned() {
        return pinned;
    }

    /**
     * Returns true if both foodplaces have the same name.
     * This defines a weaker notion of equality between two foodplaces.
     */
    public boolean isSameFoodplace(Foodplace otherFoodplace) {
        if (otherFoodplace == this) {
            return true;
        }

        return otherFoodplace != null
                && otherFoodplace.getName().equals(getName())
                && otherFoodplace.getAddress().equals(getAddress())
                && otherFoodplace.getEmail().equals(getEmail())
                && otherFoodplace.getPhone().equals(getPhone());
    }

    /**
     * Returns true if both foodplaces have the same identity and data fields.
     * This defines a stronger notion of equality between two foodplaces.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Foodplace)) {
            return false;
        }

        Foodplace otherFoodplace = (Foodplace) other;
        return name.equals(otherFoodplace.name)
                && phone.equals(otherFoodplace.phone)
                && email.equals(otherFoodplace.email)
                && address.equals(otherFoodplace.address)
                && tags.equals(otherFoodplace.tags)
                && rate.equals(otherFoodplace.rate);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, rate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("rate", rate)
                .toString();
    }

}
