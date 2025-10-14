package seedu.bitebuddy.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.testutil.SerializableTestClass;
import seedu.bitebuddy.testutil.TestUtil;

/**
 * Tests JSON Read and Write
 */
public class JsonUtilTest {

    private static final Path SERIALIZATION_FILE = TestUtil.getFilePathInSandboxFolder("serialize.json");

    @Test
    public void serializeObjectToJsonFile_noExceptionThrown() throws IOException {
        SerializableTestClass serializableTestClass = new SerializableTestClass();
        serializableTestClass.setTestValues();

        JsonUtil.serializeObjectToJsonFile(SERIALIZATION_FILE, serializableTestClass);

        assertEquals(FileUtil.readFromFile(SERIALIZATION_FILE), SerializableTestClass.JSON_STRING_REPRESENTATION);
    }

    @Test
    public void deserializeObjectFromJsonFile_noExceptionThrown() throws IOException {
        FileUtil.writeToFile(SERIALIZATION_FILE, SerializableTestClass.JSON_STRING_REPRESENTATION);

        SerializableTestClass serializableTestClass = JsonUtil
                .deserializeObjectFromJsonFile(SERIALIZATION_FILE, SerializableTestClass.class);

        assertEquals(serializableTestClass.getName(), SerializableTestClass.getNameTestValue());
        assertEquals(serializableTestClass.getListOfLocalDateTimes(), SerializableTestClass.getListTestValues());
        assertEquals(serializableTestClass.getMapOfIntegerToString(), SerializableTestClass.getHashMapTestValues());
    }

    @Test
    public void jsonUtil_readJsonStringToObjectInstance_correctObject() {
        try {
            SerializableTestClass serializableTestClass = JsonUtil.fromJsonString(
                    SerializableTestClass.JSON_STRING_REPRESENTATION, SerializableTestClass.class);
            assertEquals(serializableTestClass.getName(), SerializableTestClass.getNameTestValue());
            assertEquals(serializableTestClass.getListOfLocalDateTimes(), SerializableTestClass.getListTestValues());
            assertEquals(serializableTestClass.getMapOfIntegerToString(), SerializableTestClass.getHashMapTestValues());
        } catch (IOException e) {
            throw new AssertionError("IOException occurred during test", e);
        }
    }

    @Test
    public void jsonUtil_writeThenReadObjectToJson_correctObject() {
        try {
            SerializableTestClass original = new SerializableTestClass();
            original.setTestValues();
            String jsonString = JsonUtil.toJsonString(original);
            SerializableTestClass fromJson = JsonUtil.fromJsonString(jsonString, SerializableTestClass.class);
            assertEquals(original.getName(), fromJson.getName());
            assertEquals(original.getListOfLocalDateTimes(), fromJson.getListOfLocalDateTimes());
            assertEquals(original.getMapOfIntegerToString(), fromJson.getMapOfIntegerToString());
        } catch (IOException e) {
            throw new AssertionError("IOException occurred during test", e);
        }
    }
}
