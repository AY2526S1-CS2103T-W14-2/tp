package seedu.bitebuddy.model.foodplace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.bitebuddy.testutil.Assert.assertThrows;
import static seedu.bitebuddy.testutil.TypicalFoodplace.ALICE;
import static seedu.bitebuddy.testutil.TypicalFoodplace.BOB;

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
        assertFalse(uniqueFoodplaceList.contains(ALICE));
    }

    @Test
    public void contains_foodplaceInList_returnsTrue() {
        uniqueFoodplaceList.add(ALICE);
        assertTrue(uniqueFoodplaceList.contains(ALICE));
    }

    @Test
    public void contains_foodplaceWithSameIdentityFieldsInList_returnsTrue() {
        uniqueFoodplaceList.add(ALICE);
        Foodplace editedAlice = new FoodplaceBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueFoodplaceList.contains(editedAlice));
    }

    @Test
    public void add_nullFoodplace_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFoodplaceList.add(null));
    }

    @Test
    public void add_duplicateFoodplace_throwsDuplicateFoodplaceException() {
        uniqueFoodplaceList.add(ALICE);
        assertThrows(DuplicateFoodplaceException.class, () -> uniqueFoodplaceList.add(ALICE));
    }

    @Test
    public void setFoodplace_nullTargetFoodplace_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFoodplaceList.setFoodplace(null, ALICE));
    }

    @Test
    public void setFoodplace_nullEditedFoodplace_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFoodplaceList.setFoodplace(ALICE, null));
    }

    @Test
    public void setFoodplace_targetFoodplaceNotInList_throwsFoodplaceNotFoundException() {
        assertThrows(FoodplaceNotFoundException.class, () -> uniqueFoodplaceList.setFoodplace(ALICE, ALICE));
    }

    @Test
    public void setFoodplace_editedFoodplaceIsSameFoodplace_success() {
        uniqueFoodplaceList.add(ALICE);
        uniqueFoodplaceList.setFoodplace(ALICE, ALICE);
        UniqueFoodplaceList expectedUniqueFoodplaceList = new UniqueFoodplaceList();
        expectedUniqueFoodplaceList.add(ALICE);
        assertEquals(expectedUniqueFoodplaceList, uniqueFoodplaceList);
    }

    @Test
    public void setFoodplace_editedFoodplaceHasSameIdentity_success() {
        uniqueFoodplaceList.add(ALICE);
        Foodplace editedAlice = new FoodplaceBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueFoodplaceList.setFoodplace(ALICE, editedAlice);
        UniqueFoodplaceList expectedUniqueFoodplaceList = new UniqueFoodplaceList();
        expectedUniqueFoodplaceList.add(editedAlice);
        assertEquals(expectedUniqueFoodplaceList, uniqueFoodplaceList);
    }

    @Test
    public void setFoodplace_editedFoodplaceHasDifferentIdentity_success() {
        uniqueFoodplaceList.add(ALICE);
        uniqueFoodplaceList.setFoodplace(ALICE, BOB);
        UniqueFoodplaceList expectedUniqueFoodplaceList = new UniqueFoodplaceList();
        expectedUniqueFoodplaceList.add(BOB);
        assertEquals(expectedUniqueFoodplaceList, uniqueFoodplaceList);
    }

    @Test
    public void setFoodplace_editedFoodplaceHasNonUniqueIdentity_throwsDuplicateFoodplaceException() {
        uniqueFoodplaceList.add(ALICE);
        uniqueFoodplaceList.add(BOB);
        assertThrows(DuplicateFoodplaceException.class, () -> uniqueFoodplaceList.setFoodplace(ALICE, BOB));
    }

    @Test
    public void remove_nullFoodplace_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFoodplaceList.remove(null));
    }

    @Test
    public void remove_foodplaceDoesNotExist_throwsFoodplaceNotFoundException() {
        assertThrows(FoodplaceNotFoundException.class, () -> uniqueFoodplaceList.remove(ALICE));
    }

    @Test
    public void remove_existingFoodplace_removesFoodplace() {
        uniqueFoodplaceList.add(ALICE);
        uniqueFoodplaceList.remove(ALICE);
        UniqueFoodplaceList expectedUniqueFoodplaceList = new UniqueFoodplaceList();
        assertEquals(expectedUniqueFoodplaceList, uniqueFoodplaceList);
    }

    @Test
    public void setFoodplaces_nullUniqueFoodplaceList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFoodplaceList.setFoodplaces((UniqueFoodplaceList) null));
    }

    @Test
    public void setFoodplaces_uniqueFoodplaceList_replacesOwnListWithProvidedUniqueFoodplaceList() {
        uniqueFoodplaceList.add(ALICE);
        UniqueFoodplaceList expectedUniqueFoodplaceList = new UniqueFoodplaceList();
        expectedUniqueFoodplaceList.add(BOB);
        uniqueFoodplaceList.setFoodplaces(expectedUniqueFoodplaceList);
        assertEquals(expectedUniqueFoodplaceList, uniqueFoodplaceList);
    }

    @Test
    public void setFoodplaces_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFoodplaceList.setFoodplaces((List<Foodplace>) null));
    }

    @Test
    public void setFoodplaces_list_replacesOwnListWithProvidedList() {
        uniqueFoodplaceList.add(ALICE);
        List<Foodplace> foodplaceList = Collections.singletonList(BOB);
        uniqueFoodplaceList.setFoodplaces(foodplaceList);
        UniqueFoodplaceList expectedUniqueFoodplaceList = new UniqueFoodplaceList();
        expectedUniqueFoodplaceList.add(BOB);
        assertEquals(expectedUniqueFoodplaceList, uniqueFoodplaceList);
    }

    @Test
    public void setFoodplaces_listWithDuplicateFoodplaces_throwsDuplicateFoodplaceException() {
        List<Foodplace> listWithDuplicateFoodplaces = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateFoodplaceException.class, () -> uniqueFoodplaceList.setFoodplaces(listWithDuplicateFoodplaces));
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
