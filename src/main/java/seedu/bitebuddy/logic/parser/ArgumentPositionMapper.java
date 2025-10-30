package seedu.bitebuddy.logic.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Stores mapping of positional (non-prefixed) arguments for commands like "note 2 good taste"
 * or "rating 3 5".
 *
 * Supports basic positional argument access (can be found at CliSyntax.java):
 * - index 0: usually the first argument (e.g. index in list)
 * - index 1: usually the next value (e.g. note text or rating)
 *
 * This class complements {@code ArgumentMultimap} for commands that do not use prefixes.
 */
public class ArgumentPositionMapper {

    // List of space-separated arguments parsed from input string
    private final List<String> args;

    /**
     * Constructs a PositionalArgumentMapper by splitting the input string by whitespace.
     * @param argsString the string containing all positional arguments
     */
    public ArgumentPositionMapper(String argsString) {
        if (argsString == null || argsString.trim().isEmpty()) {
            this.args = new ArrayList<>();
        } else {
            // Split by whitespace
            String[] tokens = argsString.trim().split("\\s+");
            this.args = new ArrayList<>();
            Collections.addAll(this.args, tokens);
        }
    }

    /**
     * Constructs a PositionalArgumentMapper by splitting the input string by whitespace up till the given index.
     * @param argsString the string containing all positional arguments
     * @param ignoreSplitIndex ignoreSplitIndex the last index to split at, with all remaining text combined into one
     *                         final token (i.e., the maximum number of tokens to split into minus 1)
     */
    public ArgumentPositionMapper(String argsString, int ignoreSplitIndex) {
        if (argsString == null || argsString.trim().isEmpty()) {
            this.args = new ArrayList<>();
        } else {
            // Ensure ignoreSplitIndex is non-negative (because it splits the whole string if index is negative)
            if (ignoreSplitIndex < 0) {
                ignoreSplitIndex = 0;
            }
            // Split by whitespace till index
            String[] tokens = argsString.trim().split("\\s+", ignoreSplitIndex + 1);
            this.args = new ArrayList<>();
            Collections.addAll(this.args, tokens);
        }
    }

    /**
     * Returns the argument at the specified position, or an empty string if out of range.
     * @param position the position of the argument to retrieve
     */
    public String getArgument(int position) {
        if (position < 0 || position >= args.size()) {
            return "";
        }
        return args.get(position);
    }

    /**
     * Returns all arguments as an unmodifiable list.
     */
    public List<String> getAllArguments() {
        return Collections.unmodifiableList(args);
    }

    /**
     * Returns the number of positional arguments.
     */
    public int size() {
        return args.size();
    }

    /**
     * Returns the remaining text starting from the given position, joined with spaces.
     * @param startPosition the position to return remaining arguments from
     */
    public String getRemainingArguments(int startPosition) {
        if (startPosition < 0 || startPosition >= args.size()) {
            return "";
        }
        return String.join(" ", args.subList(startPosition, args.size()));
    }

    @Override
    public String toString() {
        return args.toString();
    }
}
