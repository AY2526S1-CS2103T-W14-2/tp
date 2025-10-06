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

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Foodplace(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
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

    /**
     * Returns true if both foodplaces have the same name.
     * This defines a weaker notion of equality between two foodplaces.
     */
    public boolean isSameFoodplace(Foodplace otherFoodplace) {
        if (otherFoodplace == this) {
            return true;
        }

        return otherFoodplace != null
                && otherFoodplace.getName().equals(getName());
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
                && tags.equals(otherFoodplace.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("bitebuddy", address)
                .add("tags", tags)
                .toString();
    }

}
