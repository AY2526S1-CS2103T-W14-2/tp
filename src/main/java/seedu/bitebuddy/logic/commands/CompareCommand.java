package seedu.bitebuddy.logic.commands;

import static seedu.bitebuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import seedu.bitebuddy.commons.core.index.Index;
import seedu.bitebuddy.commons.util.ToStringBuilder;
import seedu.bitebuddy.logic.Messages;
import seedu.bitebuddy.logic.commands.exceptions.CommandException;
import seedu.bitebuddy.model.Model;
import seedu.bitebuddy.model.foodplace.Foodplace;

/**
 * Compares two foodplaces in BiteBuddy.
 */
public class CompareCommand extends Command {

    public static final String COMMAND_WORD = "compare";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Compares the two foodplaces identified "
            + "by the index numbers used in the last foodplace listing.\n"
            + "Parameters: INDEX1 and INDEX2 (must be positive integers)\n"
            + "compare [INDEX1] [INDEX2]\n"
            + "Example: " + COMMAND_WORD + " 1 5";

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

        if (firstIndex.getZeroBased() >= lastShownList.size() || secondIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX);
        }

        Foodplace firstFoodplaceToCompare = lastShownList.get(firstIndex.getZeroBased());
        Foodplace secondFoodplaceToCompare = lastShownList.get(secondIndex.getZeroBased());

        model.updateFilteredFoodplaceList(fp ->
                fp.equals(firstFoodplaceToCompare) || fp.equals(secondFoodplaceToCompare)
        );

        return new CommandResult(String.format(generateCompareMessage(firstFoodplaceToCompare,
                secondFoodplaceToCompare)));
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

        String commonStr = commonTags.isEmpty() ? "--" : String.join(", ", commonTags);
        String firstUniqueStr = firstUnique.isEmpty() ? "--" : String.join(", ", firstUnique);
        String secondUniqueStr = secondUnique.isEmpty() ? "--" : String.join(", ", secondUnique);

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
