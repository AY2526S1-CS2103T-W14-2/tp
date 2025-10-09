package seedu.bitebuddy.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.bitebuddy.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.commons.exceptions.IllegalValueException;
import seedu.bitebuddy.commons.util.JsonUtil;
import seedu.bitebuddy.model.AddressBook;
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

}
