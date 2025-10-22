package seedu.bitebuddy.storage;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.bitebuddy.commons.exceptions.IllegalValueException;
import seedu.bitebuddy.model.foodplace.Address;
import seedu.bitebuddy.model.foodplace.Cuisine;
import seedu.bitebuddy.model.foodplace.Email;
import seedu.bitebuddy.model.foodplace.Foodplace;
import seedu.bitebuddy.model.foodplace.Name;
import seedu.bitebuddy.model.foodplace.Note;
import seedu.bitebuddy.model.foodplace.Phone;
import seedu.bitebuddy.model.foodplace.Rate;
import seedu.bitebuddy.model.foodplace.Timing;
import seedu.bitebuddy.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Foodplace}.
 */
class JsonAdaptedFoodplace {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Foodplace's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String timing;
    private final String cuisine;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final String note;
    private final Integer rate;

    /**
     * Constructs a {@code JsonAdaptedFoodplace} with the given foodplace details.
     */
    @JsonCreator
    public JsonAdaptedFoodplace(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                                @JsonProperty("email") String email, @JsonProperty("address") String address,
                                @JsonProperty("timing") String timing, @JsonProperty("cuisine") String cuisine,
                                @JsonProperty("tags") List<JsonAdaptedTag> tags,
                                @JsonProperty("note") String note,
                                @JsonProperty("rate") Integer rate) {
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
    }

    /**
     * Converts this Jackson-friendly adapted foodplace object into the model's {@code Foodplace} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted foodplace.
     */
    public Foodplace toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!phone.isEmpty() && !Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!email.isEmpty() && !Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (timing == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Timing.class.getSimpleName()));
        }
        final Timing modelTiming;
        try {
            modelTiming = new Timing(timing);
        } catch (Exception e) {
            if (e instanceof DateTimeParseException
                    || Timing.MESSAGE_INVALID_TIME.equals(e.getMessage())) {
                throw new IllegalValueException(Timing.MESSAGE_INVALID_TIME);
            } else {
                throw new IllegalValueException(Timing.MESSAGE_CONSTRAINTS);
            }
        }

        if (rate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Rate.class.getSimpleName()));
        }
        if (!Rate.isValidRating(rate)) {
            throw new IllegalValueException(Rate.MESSAGE_CONSTRAINTS);
        }
        final Rate modelRate = new Rate(rate);

        if (cuisine == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Cuisine.class.getSimpleName()));
        }
        if (!Cuisine.isValidCuisine(cuisine)) {
            throw new IllegalValueException(Cuisine.MESSAGE_CONSTRAINTS);
        }
        final Cuisine modelCuisine = new Cuisine(cuisine);

        final Set<Tag> modelTags = new HashSet<>(personTags);

        if (note == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Note.class.getSimpleName()));
        }
        if (!Note.isValidNote(note)) {
            throw new IllegalValueException(Note.MESSAGE_CONSTRAINTS);
        }
        final Note modelNote = new Note(note);
        return new Foodplace(modelName, modelPhone, modelEmail, modelAddress, modelTiming,
                modelCuisine, modelTags, modelNote, modelRate);
    }

}
