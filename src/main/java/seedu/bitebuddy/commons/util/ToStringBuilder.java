package seedu.bitebuddy.commons.util;

import java.util.Arrays;
import java.util.List;

/**
 * Builds a string representation of an object that is suitable as the return value of {@link Object#toString()}.
 */
public class ToStringBuilder {
    private static final String OBJECT_PREFIX = "{";
    private static final String OBJECT_SUFFIX = "}";
    private static final String FIELD_SEPARATOR = ", ";
    private static final String FIELD_NAME_VALUE_SEPARATOR = "=";
    // Field names to omit display
    private static final List<String> DO_NOT_DISPLAY_FIELD_NAMES = Arrays.asList("wishlist", "blacklist");

    private final StringBuilder stringBuilder = new StringBuilder();
    private boolean hasField;

    /**
     * Constructs a {@code ToStringBuilder} whose formatted output will be prefixed with {@code objectName}.
     */
    public ToStringBuilder(String objectName) {
        stringBuilder.append(objectName).append(OBJECT_PREFIX);
    }

    /**
     * Constructs a {@code ToStringBuilder} whose formatted output will be prefixed with the
     * canonical class name of {@code object}.
     */
    public ToStringBuilder(Object object) {
        this(object.getClass().getCanonicalName());
    }

    /**
     * Constructs a {@code ToStringBuilder} with just the formatted output without the object name prefix.
     */
    public ToStringBuilder() {
        stringBuilder.append(OBJECT_PREFIX);
    }

    /**
     * Adds a field name/value pair to the output string.
     *
     * @param fieldName The name of the field.
     * @param fieldValue The value of the field.
     * @return A reference to this {@code ToStringBuilder} object, allowing method calls to be chained.
     */
    public ToStringBuilder add(String fieldName, Object fieldValue) {
        if (hasField) {
            stringBuilder.append(FIELD_SEPARATOR);
        }
        if (DO_NOT_DISPLAY_FIELD_NAMES.contains(fieldName)) {
            stringBuilder.append(fieldValue);
        } else {
            stringBuilder.append(fieldName).append(FIELD_NAME_VALUE_SEPARATOR).append(fieldValue);
        }
        hasField = true;
        return this;
    }

    /**
     * Returns the built formatted string representation.
     */
    @Override
    public String toString() {
        return stringBuilder.toString() + OBJECT_SUFFIX;
    }
}
