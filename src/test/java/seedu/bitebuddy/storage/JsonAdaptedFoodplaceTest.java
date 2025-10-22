package seedu.bitebuddy.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.bitebuddy.storage.JsonAdaptedFoodplace.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.bitebuddy.testutil.Assert.assertThrows;
import static seedu.bitebuddy.testutil.TypicalFoodplace.DAEBAKSHOP;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

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

public class JsonAdaptedFoodplaceTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_NOTE = "Nice place! \u0081";
    private static final String INVALID_CUISINE = "!!invalid!!";
    private static final String INVALID_TIMING = "25:00-26:00";
    private static final Integer INVALID_RATING = -5;

    private static final String VALID_NAME = DAEBAKSHOP.getName().toString();
    private static final String VALID_PHONE = DAEBAKSHOP.getPhone().toString();
    private static final String VALID_EMAIL = DAEBAKSHOP.getEmail().toString();
    private static final String VALID_ADDRESS = DAEBAKSHOP.getAddress().toString();
    private static final String VALID_TIMING = DAEBAKSHOP.getTiming().toString();
    private static final String VALID_CUISINE = DAEBAKSHOP.getCuisine().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = DAEBAKSHOP.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final String VALID_NOTE = DAEBAKSHOP.getNote().toString();
    private static final Integer VALID_RATING = 0;

    @Test
    public void toModelType_validFoodplaceDetails_returnsFoodplace() throws Exception {
        JsonAdaptedFoodplace foodplace = new JsonAdaptedFoodplace(DAEBAKSHOP);
        assertEquals(DAEBAKSHOP, foodplace.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedFoodplace foodplace =
                new JsonAdaptedFoodplace(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_TIMING, VALID_CUISINE, VALID_TAGS, VALID_NOTE, VALID_RATING);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, foodplace::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedFoodplace foodplace = new JsonAdaptedFoodplace(null, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TIMING, VALID_CUISINE, VALID_TAGS, VALID_NOTE, VALID_RATING);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, foodplace::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedFoodplace foodplace =
                new JsonAdaptedFoodplace(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                         VALID_TIMING, VALID_CUISINE, VALID_TAGS, VALID_NOTE, VALID_RATING);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, foodplace::toModelType);
    }

    @Test
    public void toModelType_emptyPhone_returnsPhone() {
        JsonAdaptedFoodplace foodplace =
                new JsonAdaptedFoodplace(VALID_NAME, "", VALID_EMAIL, VALID_ADDRESS,
                        VALID_TIMING, VALID_CUISINE, VALID_TAGS, VALID_NOTE, VALID_RATING);
        Foodplace model;
        try {
            model = foodplace.toModelType();
            assertEquals("", model.getPhone().value);
        } catch (IllegalValueException e) {
            fail();
        }
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedFoodplace foodplace = new JsonAdaptedFoodplace(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_TIMING, VALID_CUISINE, VALID_TAGS, VALID_NOTE, VALID_RATING);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, foodplace::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedFoodplace foodplace =
                new JsonAdaptedFoodplace(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_TIMING,
                        VALID_CUISINE, VALID_TAGS, VALID_NOTE, VALID_RATING);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, foodplace::toModelType);
    }

    @Test
    public void toModelType_emptyEmail_returnsEmail() {
        JsonAdaptedFoodplace foodplace =
                new JsonAdaptedFoodplace(VALID_NAME, VALID_PHONE, "", VALID_ADDRESS,
                        VALID_TIMING, VALID_CUISINE, VALID_TAGS, VALID_NOTE,
                        VALID_RATING);
        Foodplace model;
        try {
            model = foodplace.toModelType();
            assertEquals("", model.getEmail().value);
        } catch (IllegalValueException e) {
            fail();
        }
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedFoodplace foodplace = new JsonAdaptedFoodplace(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                VALID_TIMING, VALID_CUISINE, VALID_TAGS, VALID_NOTE, VALID_RATING);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, foodplace::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedFoodplace foodplace =
                new JsonAdaptedFoodplace(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_TIMING,
                        VALID_CUISINE, VALID_TAGS, VALID_NOTE, VALID_RATING);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, foodplace::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedFoodplace foodplace = new JsonAdaptedFoodplace(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_TIMING, VALID_CUISINE, VALID_TAGS, VALID_NOTE, VALID_RATING);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, foodplace::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedFoodplace foodplace =
                new JsonAdaptedFoodplace(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_CUISINE, VALID_CUISINE, invalidTags, VALID_NOTE, VALID_RATING);
        assertThrows(IllegalValueException.class, foodplace::toModelType);
    }

    @Test
    public void toModelType_nullNote_throwsIllegalValueException() {
        // Null note is considered a kind of invalid note as well
        JsonAdaptedFoodplace foodplace =
                new JsonAdaptedFoodplace(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_TIMING, VALID_CUISINE, VALID_TAGS, null, VALID_RATING);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Note.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, foodplace::toModelType);
    }

    @Test
    public void toModelType_invalidNote_throwsIllegalValueException() {
        JsonAdaptedFoodplace foodplace =
                new JsonAdaptedFoodplace(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_TIMING, VALID_CUISINE, VALID_TAGS, INVALID_NOTE, VALID_RATING);
        String expectedMessage = Note.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, foodplace::toModelType);
    }

    @Test
    public void toModelType_nullRating_throwsIllegalValueException() {
        JsonAdaptedFoodplace foodplace =
                new JsonAdaptedFoodplace(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_TIMING, VALID_CUISINE, VALID_TAGS, VALID_NOTE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Rate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, foodplace::toModelType);
    }

    @Test
    public void toModelType_invalidRating_throwsIllegalValueException() {
        JsonAdaptedFoodplace foodplace =
                new JsonAdaptedFoodplace(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_TIMING, VALID_CUISINE, VALID_TAGS, VALID_NOTE, INVALID_RATING);
        String expectedMessage = Rate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, foodplace::toModelType);
    }

    @Test
    public void toModelType_nullCuisine_throwsIllegalValueException() {
        JsonAdaptedFoodplace foodplace =
                new JsonAdaptedFoodplace(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_TIMING, null, VALID_TAGS, VALID_NOTE, VALID_RATING);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Cuisine.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, foodplace::toModelType);
    }

    @Test
    public void toModelType_invalidCuisine_throwsIllegalValueException() {
        JsonAdaptedFoodplace adapted = new JsonAdaptedFoodplace(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TIMING, INVALID_CUISINE, VALID_TAGS, VALID_NOTE, VALID_RATING);

        try {
            adapted.toModelType();
        } catch (IllegalValueException ive) {
            assertEquals(Cuisine.MESSAGE_CONSTRAINTS, ive.getMessage());
            return;
        }
        throw new AssertionError("Expected IllegalValueException for invalid cuisine");
    }

    @Test
    public void toModelType_invalidTiming_throwsIllegalValueException() {
        JsonAdaptedFoodplace adapted = new JsonAdaptedFoodplace(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                INVALID_TIMING, VALID_CUISINE, VALID_TAGS, VALID_NOTE, VALID_RATING);

        String expectedMessage = Timing.MESSAGE_INVALID_TIME;
        assertThrows(IllegalValueException.class, expectedMessage, adapted::toModelType);
    }

    @Test
    public void toModelType_nullTiming_throwsIllegalValueException() {
        JsonAdaptedFoodplace adapted = new JsonAdaptedFoodplace(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                null, VALID_CUISINE, VALID_TAGS, VALID_NOTE, VALID_RATING);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Timing.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, adapted::toModelType);
    }

}
