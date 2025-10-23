package seedu.bitebuddy.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.bitebuddy.model.AddressBook;
import seedu.bitebuddy.model.ReadOnlyAddressBook;
import seedu.bitebuddy.model.foodplace.Address;
import seedu.bitebuddy.model.foodplace.Blacklist;
import seedu.bitebuddy.model.foodplace.Cuisine;
import seedu.bitebuddy.model.foodplace.Email;
import seedu.bitebuddy.model.foodplace.Foodplace;
import seedu.bitebuddy.model.foodplace.Name;
import seedu.bitebuddy.model.foodplace.Note;
import seedu.bitebuddy.model.foodplace.Phone;
import seedu.bitebuddy.model.foodplace.Rate;
import seedu.bitebuddy.model.foodplace.Timing;
import seedu.bitebuddy.model.foodplace.Wishlist;
import seedu.bitebuddy.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final Note EMPTY_NOTE = new Note("");

    public static Foodplace[] getSampleFoodplaces() {
        return new Foodplace[] {
            new Foodplace(new Name("McRonalds"), new Phone("68765432"), new Email("info@mcronalds.com"),
                new Address("Blk 451, Clementi Ave 3 #01-309, S120451"), new Timing("10:00", "22:00"),
                new Cuisine("Western"), getTagSet("FastFood", "Cheap"), EMPTY_NOTE, new Rate(),
                    new Wishlist(true), new Blacklist(false)),
            new Foodplace(new Name("SistersRamen"), new Phone("68765431"), new Email("info@sisramen.com"),
                new Address("10 Anson Rd, International Plaza, #01-20, S079903"), new Timing("11:30", "23:00"),
                new Cuisine("Japanese"), getTagSet("Ramen"), EMPTY_NOTE, new Rate(9),
                    new Wishlist(false), new Blacklist(false)),
            new Foodplace(new Name("Changibee"), new Phone("98765432"), new Email("support@changibee.com"),
                new Address("7 Pasir Ris Central, #B2-27, S519612"), new Timing("09:00", "21:00"),
                new Cuisine("Western"), getTagSet("FastFood"), EMPTY_NOTE, new Rate(3),
                    new Wishlist(false), new Blacklist(true)),
            new Foodplace(new Name("Fung Tai Din"), new Phone("87654321"), new Email("cs@fungdaitin.com"),
                new Address("23 Serangoon Central, #B1-10, S556083"), new Timing("12:00", "23:59"),
                new Cuisine("Chinese"), getTagSet("Expensive"), EMPTY_NOTE, new Rate(7),
                    new Wishlist(false), new Blacklist(false)),
            new Foodplace(new Name("Gucci Y Guaca"), new Phone("65655656"), new Email("go@gyg.com"),
                new Address("1 Tampines Walk, #01-99 Our Tampines Hub, S528523"), new Timing("10:30", "22:30"),
                new Cuisine("Mexican"), getTagSet("FastFood"), EMPTY_NOTE, new Rate(5),
                    new Wishlist(false), new Blacklist(true)),
            new Foodplace(new Name("Agent 47 Thai Food"), new Phone("98762345"), new Email("agent@47thai.com"),
                new Address("354 Clementi Ave 2, #01-235, S120354"), new Timing("10:00", "20:00"),
                new Cuisine("Thai"), getTagSet("Expensive"), EMPTY_NOTE, new Rate(),
                    new Wishlist(true), new Blacklist(false))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Foodplace sampleFoodplace : getSampleFoodplaces()) {
            sampleAb.addFoodplace(sampleFoodplace);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
