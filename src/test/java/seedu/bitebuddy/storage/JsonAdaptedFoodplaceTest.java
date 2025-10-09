package seedu.bitebuddy.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.bitebuddy.storage.JsonAdaptedFoodplace.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.bitebuddy.testutil.Assert.assertThrows;
import static seedu.bitebuddy.testutil.TypicalFoodplace.DAEBAKSHOP;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.commons.exceptions.IllegalValueException;
import seedu.bitebuddy.model.foodplace.Address;
import seedu.bitebuddy.model.foodplace.Email;
import seedu.bitebuddy.model.foodplace.Name;
import seedu.bitebuddy.model.foodplace.Note;
import seedu.bitebuddy.model.foodplace.Phone;

public class JsonAdaptedFoodplaceTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final Integer INVALID_RATING = -5;

    private static final String VALID_NAME = DAEBAKSHOP.getName().toString();
    private static final String VALID_PHONE = DAEBAKSHOP.getPhone().toString();
    private static final String VALID_EMAIL = DAEBAKSHOP.getEmail().toString();
    private static final String VALID_ADDRESS = DAEBAKSHOP.getAddress().toString();
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
                new JsonAdaptedFoodplace(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_NOTE,
                        VALID_RATING);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, foodplace::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedFoodplace foodplace = new JsonAdaptedFoodplace(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, VALID_NOTE, VALID_RATING);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, foodplace::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedFoodplace foodplace =
                new JsonAdaptedFoodplace(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_NOTE,
                        VALID_RATING);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, foodplace::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedFoodplace foodplace = new JsonAdaptedFoodplace(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, VALID_NOTE, VALID_RATING);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, foodplace::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedFoodplace foodplace =
                new JsonAdaptedFoodplace(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_NOTE,
                        VALID_RATING);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, foodplace::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedFoodplace foodplace = new JsonAdaptedFoodplace(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                VALID_TAGS, VALID_NOTE, VALID_RATING);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, foodplace::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedFoodplace foodplace =
                new JsonAdaptedFoodplace(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_TAGS, VALID_NOTE,
                        VALID_RATING);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, foodplace::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedFoodplace foodplace = new JsonAdaptedFoodplace(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_TAGS, VALID_NOTE, VALID_RATING);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, foodplace::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedFoodplace foodplace =
                new JsonAdaptedFoodplace(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, invalidTags, VALID_NOTE,
                        VALID_RATING);
        assertThrows(IllegalValueException.class, foodplace::toModelType);
    }

    @Test
    public void toModelType_nullNote_throwsIllegalValueException() {
        // Null note is considered a kind of invalid note as well
        JsonAdaptedFoodplace foodplace =
                new JsonAdaptedFoodplace(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, null,
                        VALID_RATING);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Note.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, foodplace::toModelType);
    }

}
