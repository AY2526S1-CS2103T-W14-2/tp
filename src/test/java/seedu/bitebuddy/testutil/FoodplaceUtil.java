package seedu.bitebuddy.testutil;

import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_CLOSE;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_CUISINE;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_OPEN;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_RATE;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.bitebuddy.logic.commands.AddCommand;
import seedu.bitebuddy.logic.commands.EditCommand;
import seedu.bitebuddy.model.foodplace.Foodplace;
import seedu.bitebuddy.model.tag.Tag;

/**
 * A utility class for Foodplace.
 */
public class FoodplaceUtil {

    /**
     * Returns an add command string for adding the {@code foodplace}.
     */
    public static String getAddCommand(Foodplace foodplace) {
        return AddCommand.COMMAND_WORD + " " + getFoodplaceDetails(foodplace);
    }

    /**
     * Returns the part of command string for the given {@code foodplace}'s details.
     */
    public static String getFoodplaceDetails(Foodplace foodplace) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME).append(foodplace.getName().fullName).append(" ");
        sb.append(PREFIX_PHONE).append(foodplace.getPhone().value).append(" ");
        sb.append(PREFIX_EMAIL).append(foodplace.getEmail().value).append(" ");
        sb.append(PREFIX_ADDRESS).append(foodplace.getAddress().value).append(" ");
        sb.append(PREFIX_OPEN).append(foodplace.getTiming().getOpeningTime().toString()).append(" ");
        sb.append(PREFIX_CLOSE).append(foodplace.getTiming().getClosingTime().toString()).append(" ");
        sb.append(PREFIX_CUISINE).append(foodplace.getCuisine().value).append(" ");
        foodplace.getTags().stream().forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
        sb.append(PREFIX_NOTE).append(foodplace.getNote().value).append(" ");
        sb.append(PREFIX_RATE).append(String.valueOf(foodplace.getRate().getValue())).append(" ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditFoodplaceDescriptor}'s details.
     */
    public static String getEditFoodplaceDescriptorDetails(EditCommand.EditFoodplaceDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        descriptor.getCuisine().ifPresent(cuisine -> sb.append(PREFIX_CUISINE).append(cuisine.value).append(" "));
        descriptor.getNote().ifPresent(note -> sb.append(PREFIX_NOTE).append(note.value).append(" "));
        descriptor.getRate()
                .ifPresent(rate -> sb.append(PREFIX_RATE).append(String.valueOf(rate.getValue())).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
