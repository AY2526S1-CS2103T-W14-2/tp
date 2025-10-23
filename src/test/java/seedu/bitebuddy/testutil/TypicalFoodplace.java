package seedu.bitebuddy.testutil;

import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_ADDRESS_MCRONALDS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_ADDRESS_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_CLOSE_TIME_MCRONALDS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_CLOSE_TIME_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_CUISINE_MCRONALDS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_CUISINE_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_EMAIL_MCRONALDS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_EMAIL_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_NAME_MCRONALDS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_NAME_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_NOTE_FAMOUS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_OPEN_TIME_MCRONALDS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_OPEN_TIME_SWENSWAN;
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

    public static final Foodplace PRATASHOP = new FoodplaceBuilder()
            .withName("Prata Palace")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@pratapalace.com")
            .withPhone("94351253")
            .withTags("hawker")
            .withNote("Serves the best appetisers!")
            .withCuisine("Indian")
            .withTiming("09:00", "21:00")
            .withRate("0")
            .withWishlist(false)
            .build();

    public static final Foodplace DAEBAKSHOP = new FoodplaceBuilder()
            .withName("Daebak Korean BBQ")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("john@daebakbbq.com")
            .withPhone("98765432")
            .withCuisine("Korean")
            .withTags("korean", "restaurant")
            .withNote("Best beef in the world!")
            .withRate("0")
            .withTiming("09:00", "21:00")
            .withWishlist(true)
            .build();

    public static final Foodplace CARLSHOP = new FoodplaceBuilder()
            .withName("Carls Junior")
            .withPhone("95352563")
            .withEmail("carls@junior.com")
            .withAddress("wall street")
            .withTags("fastfood")
            .withCuisine("American")
            .withTiming("10:00", "22:00")
            .withWishlist(false)
            .build();

    public static final Foodplace LAKSASHOP = new FoodplaceBuilder()
            .withName("Laksa Paradise")
            .withPhone("87652533")
            .withEmail("laksa@paradise.com")
            .withAddress("10th street")
            .withTags("hawker")
            .withCuisine("Malaysian")
            .withTiming("09:00", "20:00")
            .withWishlist(false)
            .build();

    public static final Foodplace SUSHISHOP = new FoodplaceBuilder()
            .withName("Sushi Tei")
            .withPhone("9482224")
            .withEmail("sushi@tei.com")
            .withAddress("michegan ave")
            .withCuisine("Japanese")
            .withTiming("11:00", "22:00")
            .withWishlist(false)
            .build();

    public static final Foodplace TEASHOP = new FoodplaceBuilder()
            .withName("ITea")
            .withPhone("9482427")
            .withEmail("i@tea.com")
            .withAddress("little tokyo")
            .withCuisine("")
            .withTiming("10:00", "20:00")
            .withWishlist(false)
            .build();

    public static final Foodplace PIZZASHOP = new FoodplaceBuilder()
            .withName("Dominoes Pizza")
            .withPhone("9482442")
            .withEmail("dominos@pizza.com")
            .withAddress("4th street")
            .withCuisine("")
            .withTiming("12:00", "23:00")
            .withWishlist(false)
            .build();

    // Manually added
    public static final Foodplace BEEHOONSHOP = new FoodplaceBuilder()
            .withName("Beehoon Meier")
            .withPhone("8482424")
            .withEmail("meier@beehoon.com")
            .withAddress("little india")
            .withCuisine("")
            .withTiming("09:00", "20:00")
            .withWishlist(false)
            .build();

    public static final Foodplace CAKESHOP = new FoodplaceBuilder()
            .withName("Cat and the fiddle")
            .withPhone("8482131")
            .withEmail("cat@fiddle.com")
            .withAddress("chicago ave")
            .withCuisine("")
            .withTiming("10:00", "22:00")
            .withWishlist(false)
            .build();

    // Manually added - Foodplace's details found in {@code CommandTestUtil}
    public static final Foodplace MCRONALDS = new FoodplaceBuilder()
            .withName(VALID_NAME_MCRONALDS)
            .withPhone(VALID_PHONE_MCRONALDS)
            .withEmail(VALID_EMAIL_MCRONALDS)
            .withAddress(VALID_ADDRESS_MCRONALDS)
            .withCuisine(VALID_CUISINE_MCRONALDS)
            .withTags(VALID_TAG_FASTFOOD)
            .withTiming(VALID_OPEN_TIME_MCRONALDS, VALID_CLOSE_TIME_MCRONALDS)
            .withWishlist(false)
            .build();

    public static final Foodplace SWENSWAN = new FoodplaceBuilder()
            .withName(VALID_NAME_SWENSWAN)
            .withPhone(VALID_PHONE_SWENSWAN)
            .withEmail(VALID_EMAIL_SWENSWAN)
            .withAddress(VALID_ADDRESS_SWENSWAN)
            .withCuisine(VALID_CUISINE_SWENSWAN)
            .withTags(VALID_TAG_RESTAURANT, VALID_TAG_FASTFOOD)
            .withNote(VALID_NOTE_FAMOUS)
            .withTiming(VALID_OPEN_TIME_SWENSWAN, VALID_CLOSE_TIME_SWENSWAN)
            .withWishlist(true)
            .build();

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
        return new ArrayList<>(Arrays.asList(PRATASHOP, DAEBAKSHOP, CARLSHOP, LAKSASHOP,
            SUSHISHOP, TEASHOP, PIZZASHOP));
    }
}
