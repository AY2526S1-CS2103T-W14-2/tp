package seedu.bitebuddy.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.bitebuddy.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.commons.exceptions.IllegalValueException;
import seedu.bitebuddy.commons.util.JsonUtil;
import seedu.bitebuddy.model.AddressBook;
import seedu.bitebuddy.model.foodplace.Foodplace;
import seedu.bitebuddy.testutil.TypicalFoodplace;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_FOODPLACES_FILE =
            TEST_DATA_FOLDER.resolve("typicalFoodplaceAddressBook.json");
    private static final Path INVALID_FOODPLACES_FILE =
            TEST_DATA_FOLDER.resolve("invalidFoodplaceAddressBook.json");
    private static final Path DUPLICATE_FOODPLACES_FILE =
            TEST_DATA_FOLDER.resolve("duplicateFoodplaceAddressBook.json");
    private static final Path CONFLICTING_WISHLIST_BLACKLIST_FILE =
            TEST_DATA_FOLDER.resolve("conflictingWishlistBlacklist.json");
    private static final Path NULL_NOTE_FILE =
            TEST_DATA_FOLDER.resolve("nullNoteFoodplaceAddressBook.json");

    @Test
    public void toModelType_typicalFoodplacesFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_FOODPLACES_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalFoodplacesAddressBook = TypicalFoodplace.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalFoodplacesAddressBook);
    }

    @Test
    public void toModelType_invalidFoodplaceFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_FOODPLACES_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    // Duplicate foodplaces (same fields, only differing in case) should be rejected
    @Test
    public void toModelType_duplicateFoodplaces_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_FOODPLACES_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_FOODPLACE,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_conflictingWishlistBlacklist_autofixesToNoStatus() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(CONFLICTING_WISHLIST_BLACKLIST_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        Foodplace fp = addressBookFromFile.getFoodplaceList().get(0);
        assertEquals(false, fp.getWishlist().isWishlisted());
        assertEquals(false, fp.getBlacklist().isBlacklisted());
    }

    @Test
    public void toModelType_nullNote_autofixesToEmptyNote() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(NULL_NOTE_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        Foodplace fp = addressBookFromFile.getFoodplaceList().get(0);
        assertEquals("", fp.getNote().value);
    }

}
