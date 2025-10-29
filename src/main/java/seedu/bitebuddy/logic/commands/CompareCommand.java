package seedu.bitebuddy.logic.commands;

import static seedu.bitebuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import seedu.bitebuddy.commons.core.index.Index;
import seedu.bitebuddy.commons.util.ToStringBuilder;
import seedu.bitebuddy.logic.commands.exceptions.CommandException;
import seedu.bitebuddy.model.Model;
import seedu.bitebuddy.model.foodplace.Foodplace;

/**
 * Compares two foodplaces in BiteBuddy.
 */
public class CompareCommand extends Command {

    public static final String COMMAND_WORD = "compare";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Compares the two foodplaces identified "
            + "by the index numbers used in the displayed foodplace list.\n"
            + "• INDEX1 and INDEX2 must be positive integers. INDEX1 and INDEX2 must not be equal.\n"
            + "Parameters:\n"
            + "  " + "INDEX1 INDEX2\n"
            + "Example:\n"
            + "  " + COMMAND_WORD + " 1 5";

    public static final String MESSAGE_COMPARE_SAME_INDEX = "The two foodplace indexes cannot be the same";
    public static final String MESSAGE_COMPARE_BOTH_INDEX_INVALID = "Both foodplace indexes provided are invalid";
    public static final String MESSAGE_COMPARE_FIRST_INDEX_INVALID = "The first foodplace index provided is invalid";
    public static final String MESSAGE_COMPARE_SECOND_INDEX_INVALID = "The second foodplace index provided is invalid";

    private final Index firstIndex;
    private final Index secondIndex;

    /**
     * @param firstIndex of the foodplace in the filtered foodplace list being compared
     * @param secondIndex of the foodplace in the filtered foodplace list being compared
     */
    public CompareCommand(Index firstIndex, Index secondIndex) {
        requireAllNonNull(firstIndex, secondIndex);
        this.firstIndex = firstIndex;
        this.secondIndex = secondIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Foodplace> lastShownList = model.getFilteredFoodplaceList();
        validateIndexes(lastShownList);

        Foodplace firstFoodplaceToCompare = lastShownList.get(firstIndex.getZeroBased());
        Foodplace secondFoodplaceToCompare = lastShownList.get(secondIndex.getZeroBased());

        model.updateFilteredFoodplaceList(fp ->
                fp.equals(firstFoodplaceToCompare) || fp.equals(secondFoodplaceToCompare)
        );

        return new CommandResult(String.format(generateCompareMessage(firstFoodplaceToCompare,
                secondFoodplaceToCompare)));
    }

    private void validateIndexes(List<Foodplace> lastShownList) throws CommandException {
        int size = lastShownList.size();
        int first = firstIndex.getZeroBased();
        int second = secondIndex.getZeroBased();

        boolean firstInvalid = first >= size;
        boolean secondInvalid = second >= size;

        if (firstInvalid && secondInvalid) {
            throw new CommandException(MESSAGE_COMPARE_BOTH_INDEX_INVALID);
        }
        if (firstInvalid) {
            throw new CommandException(MESSAGE_COMPARE_FIRST_INDEX_INVALID);
        }
        if (secondInvalid) {
            throw new CommandException(MESSAGE_COMPARE_SECOND_INDEX_INVALID);
        }
        if (first == second) {
            throw new CommandException(MESSAGE_COMPARE_SAME_INDEX);
        }
    }

    private String generateCompareMessage(Foodplace first, Foodplace second) {
        List<String> firstTags = first.getTags().stream().map(tag -> tag.tagName).toList();
        List<String> secondTags = second.getTags().stream().map(tag -> tag.tagName).toList();

        List<String> commonTags = firstTags.stream()
                .filter(secondTags::contains)
                .collect(Collectors.toList());

        List<String> firstUnique = firstTags.stream()
                .filter(tag -> !commonTags.contains(tag))
                .collect(Collectors.toList());

        List<String> secondUnique = secondTags.stream()
                .filter(tag -> !commonTags.contains(tag))
                .collect(Collectors.toList());

        String commonStr = formatTagsForDisplay(commonTags);
        String firstUniqueStr = formatTagsForDisplay(firstUnique);
        String secondUniqueStr = formatTagsForDisplay(secondUnique);

        String firstRate = first.getRate().isSet() ? first.getRate().getValue() + "" : "--";
        String secondRate = second.getRate().isSet() ? second.getRate().getValue() + "" : "--";

        return String.format(
                "%s (Rating: %s) vs %s (Rating: %s)%n"
                        + "Common tags: %s%n"
                        + "Unique tags: %s (%s) | %s (%s)%n",
                first.getName().fullName, firstRate,
                second.getName().fullName, secondRate,
                commonStr,
                first.getName().fullName, firstUniqueStr,
                second.getName().fullName, secondUniqueStr
        );
    }

    private String formatTagsForDisplay(List<String> tagList) {
        return tagList.isEmpty() ? "--" : String.join(", ", tagList);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CompareCommand)) {
            return false;
        }

        CompareCommand otherCompareCommand = (CompareCommand) other;
        return firstIndex.equals(otherCompareCommand.firstIndex) && secondIndex.equals(otherCompareCommand.secondIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstIndex, secondIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("firstIndex", firstIndex)
                .add("secondIndex", secondIndex)
                .toString();
    }
}
