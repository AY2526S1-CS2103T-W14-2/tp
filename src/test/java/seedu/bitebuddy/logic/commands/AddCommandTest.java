package seedu.bitebuddy.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.testutil.Assert.assertThrows;
import static seedu.bitebuddy.testutil.TypicalFoodplace.PRATASHOP;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.bitebuddy.commons.core.GuiSettings;
import seedu.bitebuddy.logic.Messages;
import seedu.bitebuddy.logic.commands.exceptions.CommandException;
import seedu.bitebuddy.model.AddressBook;
import seedu.bitebuddy.model.Model;
import seedu.bitebuddy.model.ReadOnlyAddressBook;
import seedu.bitebuddy.model.ReadOnlyUserPrefs;
import seedu.bitebuddy.model.foodplace.Foodplace;
import seedu.bitebuddy.testutil.FoodplaceBuilder;

public class AddCommandTest {

    private static final Foodplace kfc = new FoodplaceBuilder().withName("kfc").build();
    private static final Foodplace wokhey = new FoodplaceBuilder().withName("wokhey").build();
    private static final AddCommand addKfcCommand = new AddCommand(kfc);
    private static final AddCommand addWokheyCommand = new AddCommand(wokhey);

    @Test
    public void constructor_nullFoodplace_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_foodplaceAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingFoodplaceAdded modelStub = new ModelStubAcceptingFoodplaceAdded();
        Foodplace validFoodplace = new FoodplaceBuilder().build();

        CommandResult commandResult = new AddCommand(validFoodplace).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validFoodplace)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validFoodplace), modelStub.foodplacesAdded);
    }

    @Test
    public void execute_duplicateFoodplace_throwsCommandException() {
        Foodplace validFoodplace = new FoodplaceBuilder().build();
        AddCommand addCommand = new AddCommand(validFoodplace);
        ModelStub modelStub = new ModelStubWithFoodplace(validFoodplace);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_FOODPLACE, () ->
                addCommand.execute(modelStub));
    }

    @Test
    public void equals() {

        // same object -> returns true
        assertTrue(addKfcCommand.equals(addKfcCommand));

        // same values -> returns true
        AddCommand addKfcCommandCopy = new AddCommand(kfc);
        assertTrue(addKfcCommand.equals(addKfcCommandCopy));

        // different types -> returns false
        assertFalse(addKfcCommand.equals(1));

        // null -> returns false
        assertNotEquals(addKfcCommand, null);

        // different foodplace -> returns false
        assertFalse(addKfcCommand.equals(addWokheyCommand));
    }

    @Test
    public void hashcode() {
        AddCommand addKfcCommandCopy = new AddCommand(kfc);

        // same object -> returns same hashcode
        assertEquals(addKfcCommand.hashCode(), addKfcCommand.hashCode());

        // same values -> returns same hashcode
        assertEquals(addKfcCommand.hashCode(), addKfcCommandCopy.hashCode());

        // different foodplace -> returns different hashcode
        assertNotEquals(addKfcCommand.hashCode(), addWokheyCommand.hashCode());
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(PRATASHOP);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + PRATASHOP + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addFoodplace(Foodplace foodplace) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasFoodplace(Foodplace foodplace) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteFoodplace(Foodplace target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFoodplace(Foodplace target, Foodplace editedFoodplace) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Foodplace> getFilteredFoodplaceList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredFoodplaceList(Predicate<Foodplace> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single foodplace.
     */
    private class ModelStubWithFoodplace extends ModelStub {
        private final Foodplace foodplace;

        ModelStubWithFoodplace(Foodplace foodplace) {
            requireNonNull(foodplace);
            this.foodplace = foodplace;
        }

        @Override
        public boolean hasFoodplace(Foodplace foodplace) {
            requireNonNull(foodplace);
            return this.foodplace.isSameFoodplace(foodplace);
        }
    }

    /**
     * A Model stub that always accept the foodplace being added.
     */
    private class ModelStubAcceptingFoodplaceAdded extends ModelStub {
        final ArrayList<Foodplace> foodplacesAdded = new ArrayList<>();

        @Override
        public boolean hasFoodplace(Foodplace foodplace) {
            requireNonNull(foodplace);
            return foodplacesAdded.stream().anyMatch(foodplace::isSameFoodplace);
        }

        @Override
        public void addFoodplace(Foodplace foodplace) {
            requireNonNull(foodplace);
            foodplacesAdded.add(foodplace);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
