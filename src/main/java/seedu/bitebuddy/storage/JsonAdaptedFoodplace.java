package seedu.bitebuddy.storage;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.bitebuddy.commons.core.LogsCenter;
import seedu.bitebuddy.commons.exceptions.IllegalValueException;
import seedu.bitebuddy.model.foodplace.Address;
import seedu.bitebuddy.model.foodplace.Blacklist;
import seedu.bitebuddy.model.foodplace.Cuisine;
import seedu.bitebuddy.model.foodplace.Email;
import seedu.bitebuddy.model.foodplace.Foodplace;
import seedu.bitebuddy.model.foodplace.Name;
import seedu.bitebuddy.model.foodplace.Note;
import seedu.bitebuddy.model.foodplace.Phone;
import seedu.bitebuddy.model.foodplace.Pinned;
import seedu.bitebuddy.model.foodplace.Rate;
import seedu.bitebuddy.model.foodplace.Timing;
import seedu.bitebuddy.model.foodplace.Wishlist;
import seedu.bitebuddy.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Foodplace}.
 */
class JsonAdaptedFoodplace {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Foodplace's %s field is missing!";

    private static final Logger logger = LogsCenter.getLogger(JsonAdaptedFoodplace.class);

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String timing;
    private final String cuisine;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final String note;
    private final Integer rate;
    private final Boolean isWishlisted;
    private final Boolean isBlacklisted;
    private final Boolean isPinned;

    /**
     * Constructs a {@code JsonAdaptedFoodplace} with the given foodplace details.
     */
    @JsonCreator
    public JsonAdaptedFoodplace(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                                @JsonProperty("email") String email, @JsonProperty("address") String address,
                                @JsonProperty("timing") String timing, @JsonProperty("cuisine") String cuisine,
                                @JsonProperty("tags") List<JsonAdaptedTag> tags,
                                @JsonProperty("note") String note,
                                @JsonProperty("rate") Integer rate,
                                @JsonProperty("wishlist") Boolean isWishlisted,
                                @JsonProperty("blacklist") Boolean isBlacklisted,
                                @JsonProperty("isPinned") Boolean isPinned) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.timing = timing;
        this.cuisine = cuisine;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        this.note = note;
        this.rate = rate;
        this.isWishlisted = isWishlisted;
        this.isBlacklisted = isBlacklisted;
        this.isPinned = isPinned;
    }

    /**
     * Converts a given {@code Foodplace} into this class for Jackson use.
     */
    public JsonAdaptedFoodplace(Foodplace source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        // store timing as "HH:mm-HH:mm"
        timing = source.getTiming().toString();
        cuisine = source.getCuisine().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        note = source.getNote().value;
        rate = source.getRate().getValue();
        isWishlisted = source.getWishlist().isWishlisted();
        isBlacklisted = source.getBlacklist().isBlacklisted();
        isPinned = source.getPinned().isPinned();
    }

    /**
     * Converts this Jackson-friendly adapted foodplace object into the model's {@code Foodplace} object.
     *
     * Auto-fixes common issues introduced by manual edits:
     * - null or whitespace-only notes -> treated as empty note
     * - missing wishlist/blacklist fields -> default to false for both statuses
     * - conflicting wishlist & blacklist both true -> reset both status to false
     *
     * @throws IllegalValueException if there were any data constraints violated in
     *         the adapted foodplace (other than the above fixes).
     */
    public Foodplace toModelType() throws IllegalValueException {
        return toModelType(null);
    }

    public Foodplace toModelType(java.util.List<AutoFixRecord> fixes) throws IllegalValueException {
        final Name modelName = toModelName();
        final Phone modelPhone = toModelPhone();
        final Email modelEmail = toModelEmail();
        final Address modelAddress = toModelAddress();
        final Timing modelTiming = toModelTiming();
        final Rate modelRate = toModelRate();
        final Cuisine modelCuisine = toModelCuisine();
        final Set<Tag> modelTags = toModelTags();

        // Safe note - treat null as empty
        final String safeNote = (note == null) ? "" : note;
        // record if we auto-fixed a null note or a whitespace-only note
        boolean noteAutoFixed = false;
        if (note == null) {
            noteAutoFixed = true;
        } else if (note.length() > 0 && note.trim().isEmpty()) {
            // contains only whitespace characters, treat as auto-fixed
            noteAutoFixed = true;
        }
        final Note modelNote = toModelNote(safeNote);

        // Safe wishlist/blacklist - default missing to false
        final boolean safeWishlist = (isWishlisted == null) ? false : isWishlisted;
        final boolean safeBlacklist = (isBlacklisted == null) ? false : isBlacklisted;

        // If both wishlist and blacklist are true in the data, prefer no status (reset both to false)
        boolean finalWishlist = safeWishlist;
        boolean finalBlacklist = safeBlacklist;
        boolean conflictAutoFixed = false;
        if (safeWishlist && safeBlacklist) {
            logger.log(java.util.logging.Level.WARNING, "Both wishlist and blacklist set for foodplace {0}. "
                    + "Auto-fixing to no status.", name);
            finalWishlist = false;
            finalBlacklist = false;
            conflictAutoFixed = true;
        }

        final Wishlist modelWishlist = new Wishlist(finalWishlist);
        final Blacklist modelBlacklist = new Blacklist(finalBlacklist);
        final Pinned modelPinned = toModelPinned();

        // Append AutoFix records if provided
        if (fixes != null) {
            if (noteAutoFixed) {
                fixes.add(new AutoFixRecord(name == null ? "<unknown>" : name, "note -> empty"));
            }
            if (conflictAutoFixed) {
                fixes.add(new AutoFixRecord(name == null ? "<unknown>" : name,
                        "Remove Wishlist and Blacklist status"));
            }
            // also record missing wishlist/blacklist defaults
            if (isWishlisted == null) {
                fixes.add(new AutoFixRecord(name == null ? "<unknown>" : name, "wishlist -> false"));
            }
            if (isBlacklisted == null) {
                fixes.add(new AutoFixRecord(name == null ? "<unknown>" : name, "blacklist -> false"));
            }
        }

        return new Foodplace(modelName, modelPhone, modelEmail, modelAddress, modelTiming, modelCuisine,
                modelTags, modelNote, modelRate, modelWishlist, modelBlacklist, modelPinned);
    }

    private Name toModelName() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(name);
    }

    private Phone toModelPhone() throws IllegalValueException {
        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!phone.isEmpty() && !Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(phone);
    }

    private Email toModelEmail() throws IllegalValueException {
        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!email.isEmpty() && !Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(email);
    }

    private Address toModelAddress() throws IllegalValueException {
        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(address);
    }

    private Timing toModelTiming() throws IllegalValueException {
        if (timing == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Timing.class.getSimpleName()));
        }
        try {
            return new Timing(timing);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(Timing.MESSAGE_INVALID_TIME);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(Timing.MESSAGE_CONSTRAINTS);
        }
    }

    private Rate toModelRate() throws IllegalValueException {
        if (rate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Rate.class.getSimpleName()));
        }
        if (!Rate.isValidRating(rate)) {
            throw new IllegalValueException(Rate.MESSAGE_CONSTRAINTS);
        }
        return new Rate(rate);
    }

    private Cuisine toModelCuisine() throws IllegalValueException {
        if (cuisine == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Cuisine.class.getSimpleName()));
        }
        if (!Cuisine.isValidCuisine(cuisine)) {
            throw new IllegalValueException(Cuisine.MESSAGE_CONSTRAINTS);
        }
        return new Cuisine(cuisine);
    }

    private Set<Tag> toModelTags() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }
        return new HashSet<>(personTags);
    }

    private Note toModelNote(String note) throws IllegalValueException {
        if (!Note.isValidNote(note)) {
            throw new IllegalValueException(Note.MESSAGE_CONSTRAINTS);
        }
        return new Note(note);
    }

    private Pinned toModelPinned() throws IllegalValueException {
        if (isPinned == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Pinned.class.getSimpleName()));
        }
        return new Pinned(isPinned);
    }
}
