package seedu.bitebuddy.testutil;

import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_ADDRESS_MCRONALDS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_ADDRESS_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_EMAIL_MCRONALDS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_EMAIL_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_NAME_MCRONALDS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_NAME_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_PHONE_MCRONALDS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_PHONE_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_TAG_FASTFOOD;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_TAG_RESTAURANT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.bitebuddy.model.AddressBook;
import seedu.bitebuddy.model.foodplace.Foodplace;

/**
 * A utility class containing a list of {@code Foodplace} objects to be used in tests.
 */
public class TypicalFoodplace {

    public static final Foodplace PRATA = new FoodplaceBuilder().withName("Prata Palace")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@pratapalace.com")
            .withPhone("94351253")
            .withTags("hawker").build();
    public static final Foodplace DAEBAK = new FoodplaceBuilder().withName("Daebak Korean BBQ")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("korean", "restaurant").build();
    public static final Foodplace CARLSJR = new FoodplaceBuilder().withName("Carls Junior").withPhone("95352563")
             .withEmail("carls@junior.com").withAddress("wall street").withTags("fastfood").build();
    public static final Foodplace LAKSA = new FoodplaceBuilder().withName("Laksa Paradise").withPhone("87652533")
             .withEmail("laksa@paradise.com").withAddress("10th street").withTags("hawker").build();
    public static final Foodplace SUSHI = new FoodplaceBuilder().withName("Sushi Tei").withPhone("9482224")
             .withEmail("sushi@tei.com").withAddress("michegan ave").build();
    public static final Foodplace TEA = new FoodplaceBuilder().withName("ITea").withPhone("9482427")
             .withEmail("i@tea.com").withAddress("little tokyo").build();
    public static final Foodplace PIZZA = new FoodplaceBuilder().withName("Dominoes Pizza").withPhone("9482442")
             .withEmail("dominos@pizza.com").withAddress("4th street").build();

    // Manually added
    public static final Foodplace BEEHOON = new FoodplaceBuilder().withName("Beehoon Meier").withPhone("8482424")
             .withEmail("meier@beehoon.com").withAddress("little india").build();
    public static final Foodplace CAKE = new FoodplaceBuilder().withName("Cat and the fiddle").withPhone("8482131")
             .withEmail("cat@fiddle.com").withAddress("chicago ave").build();

    // Manually added - Foodplace's details found in {@code CommandTestUtil}
    public static final Foodplace MCRONALDS = new FoodplaceBuilder().withName(VALID_NAME_MCRONALDS)
            .withPhone(VALID_PHONE_MCRONALDS).withEmail(VALID_EMAIL_MCRONALDS).withAddress(VALID_ADDRESS_MCRONALDS)
            .withTags(VALID_TAG_FASTFOOD).build();
    public static final Foodplace SWENSWAN = new FoodplaceBuilder().withName(VALID_NAME_SWENSWAN)
            .withPhone(VALID_PHONE_SWENSWAN).withEmail(VALID_EMAIL_SWENSWAN).withAddress(VALID_ADDRESS_SWENSWAN)
            .withTags(VALID_TAG_RESTAURANT, VALID_TAG_FASTFOOD).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalFoodplace() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical foodplaces.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Foodplace foodplace : getTypicalFoodplaces()) {
            ab.addFoodplace(foodplace);
        }
        return ab;
    }

    public static List<Foodplace> getTypicalFoodplaces() {
        return new ArrayList<>(Arrays.asList(PRATA, DAEBAK, CARLSJR, LAKSA, SUSHI, TEA, PIZZA));
    }
}
