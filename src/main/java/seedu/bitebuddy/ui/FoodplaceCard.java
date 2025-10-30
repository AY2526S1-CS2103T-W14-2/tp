package seedu.bitebuddy.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.bitebuddy.model.foodplace.Foodplace;

/**
 * An UI component that displays information of a {@code Foodplace}.
 */
public class FoodplaceCard extends UiPart<Region> {

    private static final String FXML = "FoodplaceListCard.fxml";
    private static final String PIN_ICON_PATH = "/images/pin.png";

    private static final String MESSAGE_NO_PHONE = "## No phone number yet ##";
    private static final String MESSAGE_NO_EMAIL = "\\\\ No email yet //";
    private static final String MESSAGE_NO_TIMING = "** No timing specified **";
    private static final String MESSAGE_NO_CUISINE = "== No cuisine specified ==";
    private static final String MESSAGE_NO_NOTE = "-- No notes yet --";
    private static final String MESSAGE_NO_RATE = ">> No rating yet <<";
    private static final String MESSAGE_WISHLIST = "Wishlisted";
    private static final String MESSAGE_BLACKLIST = "Blacklisted";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Foodplace foodplace;

    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label timing;
    @FXML
    private Label cuisine;
    @FXML
    private FlowPane tags;
    @FXML
    private Label note;
    @FXML
    private Label rate;
    @FXML
    private Label wishlist;
    @FXML
    private Label blacklist;
    @FXML
    private ImageView pinIcon;

    /**
     * Creates a {@code PersonCode} with the given {@code Foodplace} and index to display.
     */
    public FoodplaceCard(Foodplace foodplace, int displayedIndex) {
        super(FXML);
        this.foodplace = foodplace;
        id.setText(displayedIndex + ". ");
        name.setText(foodplace.getName().fullName);
        address.setText(foodplace.getAddress().value);

        // Optional fields
        setLabelText(phone, foodplace.getPhone().value, MESSAGE_NO_PHONE);
        setLabelText(email, foodplace.getEmail().value, MESSAGE_NO_EMAIL);
        setLabelText(cuisine, foodplace.getCuisine().value, MESSAGE_NO_CUISINE);
        setLabelText(note, foodplace.getNote().value, MESSAGE_NO_NOTE);
        setLabelText(rate, foodplace.getRate().isSet() ? foodplace.getRate().toString() : "", MESSAGE_NO_RATE);
        setTimingLabel(timing, foodplace.getTiming().toString(), foodplace.getTiming().isSet());
        populateTags(foodplace);
        wishlist.setText(foodplace.getWishlist().isWishlisted() ? MESSAGE_WISHLIST : "");
        blacklist.setText(foodplace.getBlacklist().isBlacklisted() ? MESSAGE_BLACKLIST : "");
        setPinIcon(foodplace.getPinned().isPinned());
    }

    private void setLabelText(Label label, String value, String defaultMessage) {
        label.setText(value != null && !value.isEmpty() ? value : defaultMessage);
    }

    private void setTimingLabel(Label label, String timingValue, boolean isSet) {
        label.setText(isSet && timingValue != null && !timingValue.isEmpty() ? timingValue : MESSAGE_NO_TIMING);
    }

    private void populateTags(Foodplace foodplace) {
        tags.getChildren().clear();
        foodplace.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    private void setPinIcon(boolean pinned) {
        if (pinned) {
            pinIcon.setVisible(true);
            pinIcon.setImage(new Image(getClass().getResourceAsStream(PIN_ICON_PATH)));
        } else {
            pinIcon.setVisible(false);
        }
    }
}
