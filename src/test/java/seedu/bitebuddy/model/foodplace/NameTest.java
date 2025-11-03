package seedu.bitebuddy.model.foodplace;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // contains non-alphanumeric characters
        assertFalse(Name.isValidName("&startsWithSpecial")); // starts with special character
        assertFalse(Name.isValidName(" startswithspace")); // starts with space

        // valid name
        assertTrue(Name.isValidName("olive garden")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("4 fingers")); // alphanumeric characters
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("Super long Restaurant name")); // long names
        assertTrue(Name.isValidName("McDonald's @ Downtown!")); // with special characters
        assertTrue(Name.isValidName("Cat, & the_fiddle: #deluxe-edition$.")); // with spaces
    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new Name("Valid Name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // additional spaces -> returns true
        assertTrue(name.equals(new Name("Valid        Name  ")));

        // null -> returns false
        assertNotEquals(name, null);

        // different types -> returns false
        assertNotEquals(name, 5.0f);

        // different values -> returns false
        assertFalse(name.equals(new Name("Other Valid Name")));
    }
}
