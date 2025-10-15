package seedu.bitebuddy.testutil;

import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_PHONE;
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
        sb.append(PREFIX_NAME + foodplace.getName().fullName + " ");
        sb.append(PREFIX_PHONE + foodplace.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + foodplace.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + foodplace.getAddress().value + " ");
        foodplace.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        sb.append(PREFIX_NOTE + foodplace.getNote().value + " ");
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
        descriptor.getNote().ifPresent(note -> sb.append(PREFIX_NOTE).append(note.value).append(" "));
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
