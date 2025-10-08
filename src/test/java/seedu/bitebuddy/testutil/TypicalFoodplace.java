package seedu.bitebuddy.testutil;

import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_ADDRESS_MCRONALDS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_ADDRESS_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_EMAIL_MCRONALDS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_EMAIL_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_NAME_MCRONALDS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_NAME_SWENSWAN;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_PHONE_MCRONALDS;
import static seedu.bitebuddy.logic.commands.CommandTestUtil.VALID_PHONE_SWENSAN;
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

    public static final Foodplace ALICE = new FoodplaceBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Foodplace BENSON = new FoodplaceBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Foodplace CARL = new FoodplaceBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").build();
    public static final Foodplace DANIEL = new FoodplaceBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends").build();
    public static final Foodplace ELLE = new FoodplaceBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Foodplace FIONA = new FoodplaceBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Foodplace GEORGE = new FoodplaceBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final Foodplace HOON = new FoodplaceBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Foodplace IDA = new FoodplaceBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Foodplace's details found in {@code CommandTestUtil}
    public static final Foodplace AMY = new FoodplaceBuilder().withName(VALID_NAME_MCRONALDS)
            .withPhone(VALID_PHONE_MCRONALDS).withEmail(VALID_EMAIL_MCRONALDS).withAddress(VALID_ADDRESS_MCRONALDS)
            .withTags(VALID_TAG_FASTFOOD).build();
    public static final Foodplace BOB = new FoodplaceBuilder().withName(VALID_NAME_SWENSWAN)
            .withPhone(VALID_PHONE_SWENSAN).withEmail(VALID_EMAIL_SWENSWAN).withAddress(VALID_ADDRESS_SWENSWAN)
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
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
