package seedu.bitebuddy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.showFoodplaceAtIndex;
import static seedu.bitebuddy.testutil.TypicalFoodplace.getTypicalAddressBook;
import static seedu.bitebuddy.testutil.TypicalIndexes.INDEX_FIRST_FOODPLACE;
import static seedu.bitebuddy.testutil.TypicalIndexes.INDEX_SECOND_FOODPLACE;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.commons.core.index.Index;
import seedu.bitebuddy.logic.Messages;
import seedu.bitebuddy.model.AddressBook;
import seedu.bitebuddy.model.Model;
import seedu.bitebuddy.model.ModelManager;
import seedu.bitebuddy.model.UserPrefs;
import seedu.bitebuddy.model.foodplace.Foodplace;
import seedu.bitebuddy.model.tag.Tag;
import seedu.bitebuddy.testutil.FoodplaceBuilder;

/**
 * Contains integration tests and unit tests for {@code TagCommand}.
 */
public class TagCommandTest {

    private static final Tag TAG_FASTFOOD = new Tag("FastFood");
    private static final Tag TAG_CHEAP = new Tag("Cheap");

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addTagsUnfilteredList_success() {
        Foodplace targetFoodplace = model.getFilteredFoodplaceList().get(INDEX_FIRST_FOODPLACE.getZeroBased());

        // Combine existing + new tags
        Set<Tag> expectedTags = new HashSet<>(targetFoodplace.getTags());
        expectedTags.add(TAG_FASTFOOD);
        expectedTags.add(TAG_CHEAP);

        Foodplace editedFoodplace = new FoodplaceBuilder(targetFoodplace)
                .withTags(expectedTags.stream().map(tag -> tag.tagName).toArray(String[]::new))
                .build();

        TagCommand tagCommand = new TagCommand(INDEX_FIRST_FOODPLACE, Set.of(TAG_FASTFOOD, TAG_CHEAP));

        String expectedMessage = String.format(TagCommand.MESSAGE_SUCCESS, Messages.format(editedFoodplace));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFoodplace(targetFoodplace, editedFoodplace);

        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFoodplaceList().size() + 1);
        TagCommand tagCommand = new TagCommand(outOfBoundIndex, Set.of(TAG_FASTFOOD));

        assertCommandFailure(tagCommand, model, Messages.MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_addTagsFilteredList_success() {
        showFoodplaceAtIndex(model, INDEX_FIRST_FOODPLACE);
        Foodplace targetFoodplace = model.getFilteredFoodplaceList().get(INDEX_FIRST_FOODPLACE.getZeroBased());

        Set<Tag> expectedTags = new HashSet<>(targetFoodplace.getTags());
        expectedTags.add(TAG_FASTFOOD);

        Foodplace editedFoodplace = new FoodplaceBuilder(targetFoodplace)
                .withTags(expectedTags.stream().map(tag -> tag.tagName).toArray(String[]::new))
                .build();

        TagCommand tagCommand = new TagCommand(INDEX_FIRST_FOODPLACE, Set.of(TAG_FASTFOOD));

        String expectedMessage = String.format(TagCommand.MESSAGE_SUCCESS, Messages.format(editedFoodplace));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFoodplace(targetFoodplace, editedFoodplace);

        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showFoodplaceAtIndex(model, INDEX_FIRST_FOODPLACE);
        Index outOfBoundIndex = INDEX_SECOND_FOODPLACE;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getFoodplaceList().size());

        TagCommand tagCommand = new TagCommand(outOfBoundIndex, Set.of(TAG_FASTFOOD));
        assertCommandFailure(tagCommand, model, Messages.MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Set<Tag> tagSetFastFood = Set.of(TAG_FASTFOOD);
        Set<Tag> tagSetCheap = Set.of(TAG_CHEAP);

        TagCommand tagFirstFastFood = new TagCommand(INDEX_FIRST_FOODPLACE, tagSetFastFood);
        TagCommand tagFirstCheap = new TagCommand(INDEX_FIRST_FOODPLACE, tagSetCheap);
        TagCommand tagSecondFastFood = new TagCommand(INDEX_SECOND_FOODPLACE, tagSetFastFood);

        // same values -> true
        assertTrue(tagFirstFastFood.equals(new TagCommand(INDEX_FIRST_FOODPLACE, tagSetFastFood)));

        // same object -> true
        assertTrue(tagFirstFastFood.equals(tagFirstFastFood));

        // null -> false
        assertNotEquals(tagFirstFastFood, null);

        // different type -> false
        assertNotEquals(tagFirstFastFood, new ClearCommand());

        // different index -> false
        assertFalse(tagFirstFastFood.equals(tagSecondFastFood));

        // different tags -> false
        assertFalse(tagFirstFastFood.equals(tagFirstCheap));
    }

    @Test
    public void hashcode() {
        Set<Tag> tagSetFastFood = Set.of(TAG_FASTFOOD);
        Set<Tag> tagSetCheap = Set.of(TAG_CHEAP);

        TagCommand tagFirstFastFood = new TagCommand(INDEX_FIRST_FOODPLACE, tagSetFastFood);
        TagCommand tagFirstFastFoodCopy = new TagCommand(INDEX_FIRST_FOODPLACE, tagSetFastFood);
        TagCommand tagSecondCheap = new TagCommand(INDEX_SECOND_FOODPLACE, tagSetCheap);

        // same object -> same hashcode
        assertTrue(tagFirstFastFood.hashCode() == tagFirstFastFood.hashCode());

        // same values -> same hashcode
        assertTrue(tagFirstFastFood.hashCode() == tagFirstFastFoodCopy.hashCode());

        // different values -> different hashcode
        assertFalse(tagFirstFastFood.hashCode() == tagSecondCheap.hashCode());
    }
}
