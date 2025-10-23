package seedu.bitebuddy.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.bitebuddy.model.foodplace.Foodplace;

/**
 * An UI component that displays information of a {@code Foodplace}.
 */
public class FoodplaceCard extends UiPart<Region> {

    private static final String FXML = "FoodplaceListCard.fxml";

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

    /**
     * Creates a {@code PersonCode} with the given {@code Foodplace} and index to display.
     */
    public FoodplaceCard(Foodplace foodplace, int displayedIndex) {
        super(FXML);
        this.foodplace = foodplace;
        id.setText(displayedIndex + ". ");
        name.setText(foodplace.getName().fullName);
        address.setText(foodplace.getAddress().value);
        phone.setText(
                !(foodplace.getPhone().value.isEmpty())
                        ? foodplace.getPhone().value
                        : "## No phone number yet ##"
        );
        email.setText(
                !(foodplace.getEmail().value.isEmpty())
                        ? foodplace.getEmail().value
                        : "\\\\ No email yet //"
        );
        timing.setText(
                foodplace.getTiming().isSet()
                        ? foodplace.getTiming().toString()
                        : "** No timing specified **"
        );
        cuisine.setText(
                !(foodplace.getCuisine().value.isEmpty())
                        ? foodplace.getCuisine().value
                        : "== No cuisine specified =="
        );
        note.setText(
                !(foodplace.getNote().value.isEmpty())
                        ? foodplace.getNote().value
                        : "-- No notes yet --"
        );
        foodplace.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        rate.setText(
                foodplace.getRate().isSet()
                        ? foodplace.getRate().toString()
                        : ">> No Rating yet <<"
        );
        wishlist.setText(foodplace.getWishlist().isWishlisted() ? "Wishlisted" : "");
        blacklist.setText(foodplace.getBlacklist().isBlacklisted() ? "Blacklisted" : "");
    }
}
