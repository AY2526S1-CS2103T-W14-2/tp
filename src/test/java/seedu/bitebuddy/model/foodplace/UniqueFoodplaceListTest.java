package seedu.bitebuddy.model.foodplace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_ADDRESS_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_TAG_RESTAURANT;
import static seedu.bitebuddy.testutil.Assert.assertThrows;
import static seedu.bitebuddy.testutil.TypicalFoodplace.PRATA;
import static seedu.bitebuddy.testutil.TypicalFoodplace.SWENSWAN;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.bitebuddy.model.foodplace.exceptions.DuplicateFoodplaceException;
import seedu.bitebuddy.model.foodplace.exceptions.FoodplaceNotFoundException;
import seedu.bitebuddy.testutil.FoodplaceBuilder;

public class UniqueFoodplaceListTest {

    private final UniqueFoodplaceList uniqueFoodplaceList = new UniqueFoodplaceList();

    @Test
    public void contains_nullFoodplace_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFoodplaceList.contains(null));
    }

    @Test
    public void contains_foodplaceNotInList_returnsFalse() {
        assertFalse(uniqueFoodplaceList.contains(PRATA));
    }

    @Test
    public void contains_foodplaceInList_returnsTrue() {
        uniqueFoodplaceList.add(PRATA);
        assertTrue(uniqueFoodplaceList.contains(PRATA));
    }

    @Test
    public void contains_foodplaceWithSameIdentityFieldsInList_returnsTrue() {
        uniqueFoodplaceList.add(PRATA);
        Foodplace editedAlice = new FoodplaceBuilder(PRATA).withAddress(VALID_ADDRESS_SWENSWAN)
                .withTags(VALID_TAG_RESTAURANT).build();
        assertTrue(uniqueFoodplaceList.contains(editedAlice));
    }

    @Test
    public void add_nullFoodplace_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFoodplaceList.add(null));
    }

    @Test
    public void add_duplicateFoodplace_throwsDuplicateFoodplaceException() {
        uniqueFoodplaceList.add(PRATA);
        assertThrows(DuplicateFoodplaceException.class, () -> uniqueFoodplaceList.add(PRATA));
    }

    @Test
    public void setFoodplace_nullTargetFoodplace_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFoodplaceList.setFoodplace(null, PRATA));
    }

    @Test
    public void setFoodplace_nullEditedFoodplace_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFoodplaceList.setFoodplace(PRATA, null));
    }

    @Test
    public void setFoodplace_targetFoodplaceNotInList_throwsFoodplaceNotFoundException() {
        assertThrows(FoodplaceNotFoundException.class, () -> uniqueFoodplaceList.setFoodplace(PRATA, PRATA));
    }

    @Test
    public void setFoodplace_editedFoodplaceIsSameFoodplace_success() {
        uniqueFoodplaceList.add(PRATA);
        uniqueFoodplaceList.setFoodplace(PRATA, PRATA);
        UniqueFoodplaceList expectedUniqueFoodplaceList = new UniqueFoodplaceList();
        expectedUniqueFoodplaceList.add(PRATA);
        assertEquals(expectedUniqueFoodplaceList, uniqueFoodplaceList);
    }

    @Test
    public void setFoodplace_editedFoodplaceHasSameIdentity_success() {
        uniqueFoodplaceList.add(PRATA);
        Foodplace editedAlice = new FoodplaceBuilder(PRATA).withAddress(VALID_ADDRESS_SWENSWAN)
                .withTags(VALID_TAG_RESTAURANT).build();
        uniqueFoodplaceList.setFoodplace(PRATA, editedAlice);
        UniqueFoodplaceList expectedUniqueFoodplaceList = new UniqueFoodplaceList();
        expectedUniqueFoodplaceList.add(editedAlice);
        assertEquals(expectedUniqueFoodplaceList, uniqueFoodplaceList);
    }

    @Test
    public void setFoodplace_editedFoodplaceHasDifferentIdentity_success() {
        uniqueFoodplaceList.add(PRATA);
        uniqueFoodplaceList.setFoodplace(PRATA, SWENSWAN);
        UniqueFoodplaceList expectedUniqueFoodplaceList = new UniqueFoodplaceList();
        expectedUniqueFoodplaceList.add(SWENSWAN);
        assertEquals(expectedUniqueFoodplaceList, uniqueFoodplaceList);
    }

    @Test
    public void setFoodplace_editedFoodplaceHasNonUniqueIdentity_throwsDuplicateFoodplaceException() {
        uniqueFoodplaceList.add(PRATA);
        uniqueFoodplaceList.add(SWENSWAN);
        assertThrows(DuplicateFoodplaceException.class, () -> uniqueFoodplaceList.setFoodplace(PRATA, SWENSWAN));
    }

    @Test
    public void remove_nullFoodplace_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFoodplaceList.remove(null));
    }

    @Test
    public void remove_foodplaceDoesNotExist_throwsFoodplaceNotFoundException() {
        assertThrows(FoodplaceNotFoundException.class, () -> uniqueFoodplaceList.remove(PRATA));
    }

    @Test
    public void remove_existingFoodplace_removesFoodplace() {
        uniqueFoodplaceList.add(PRATA);
        uniqueFoodplaceList.remove(PRATA);
        UniqueFoodplaceList expectedUniqueFoodplaceList = new UniqueFoodplaceList();
        assertEquals(expectedUniqueFoodplaceList, uniqueFoodplaceList);
    }

    @Test
    public void setFoodplaces_nullUniqueFoodplaceList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFoodplaceList.setFoodplaces((UniqueFoodplaceList) null));
    }

    @Test
    public void setFoodplaces_uniqueFoodplaceList_replacesOwnListWithProvidedUniqueFoodplaceList() {
        uniqueFoodplaceList.add(PRATA);
        UniqueFoodplaceList expectedUniqueFoodplaceList = new UniqueFoodplaceList();
        expectedUniqueFoodplaceList.add(SWENSWAN);
        uniqueFoodplaceList.setFoodplaces(expectedUniqueFoodplaceList);
        assertEquals(expectedUniqueFoodplaceList, uniqueFoodplaceList);
    }

    @Test
    public void setFoodplaces_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFoodplaceList.setFoodplaces((List<Foodplace>) null));
    }

    @Test
    public void setFoodplaces_list_replacesOwnListWithProvidedList() {
        uniqueFoodplaceList.add(PRATA);
        List<Foodplace> foodplaceList = Collections.singletonList(SWENSWAN);
        uniqueFoodplaceList.setFoodplaces(foodplaceList);
        UniqueFoodplaceList expectedUniqueFoodplaceList = new UniqueFoodplaceList();
        expectedUniqueFoodplaceList.add(SWENSWAN);
        assertEquals(expectedUniqueFoodplaceList, uniqueFoodplaceList);
    }

    @Test
    public void setFoodplaces_listWithDuplicateFoodplaces_throwsDuplicateFoodplaceException() {
        List<Foodplace> listWithDuplicateFoodplaces = Arrays.asList(PRATA, PRATA);
        assertThrows(DuplicateFoodplaceException.class, () ->
                uniqueFoodplaceList.setFoodplaces(listWithDuplicateFoodplaces));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueFoodplaceList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueFoodplaceList.asUnmodifiableObservableList().toString(), uniqueFoodplaceList.toString());
    }
}
