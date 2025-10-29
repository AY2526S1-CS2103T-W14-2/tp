package seedu.bitebuddy.storage;

import java.util.Objects;

/**
 * Represents a small record of an automatic fix applied when loading data from JSON.
 */
public class AutoFixRecord {
    private final String foodplaceName;
    private final String fieldChanged;

    /**
     * Constructs an AutoFixRecord with the given foodplace name and field changed.
     *
     * @param foodplaceName the name of the foodplace
     * @param fieldChanged the field that was auto-fixed (changed)
     */
    public AutoFixRecord(String foodplaceName, String fieldChanged) {
        this.foodplaceName = foodplaceName;
        this.fieldChanged = fieldChanged;
    }

    public String getFoodplaceName() {
        return foodplaceName;
    }

    public String getFieldChanged() {
        return fieldChanged;
    }

    @Override
    public String toString() {
        return foodplaceName + ": " + fieldChanged;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof AutoFixRecord)) {
            return false;
        }

        AutoFixRecord other = (AutoFixRecord) o;
        return Objects.equals(foodplaceName, other.foodplaceName) && Objects.equals(fieldChanged, other.fieldChanged);
    }

    @Override
    public int hashCode() {
        return Objects.hash(foodplaceName, fieldChanged);
    }
}

