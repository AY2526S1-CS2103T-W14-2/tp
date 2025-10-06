package seedu.bitebuddy.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
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
    private HBox cardPane;
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
    private FlowPane tags;

    /**
     * Creates a {@code PersonCode} with the given {@code Foodplace} and index to display.
     */
    public FoodplaceCard(Foodplace foodplace, int displayedIndex) {
        super(FXML);
        this.foodplace = foodplace;
        id.setText(displayedIndex + ". ");
        name.setText(foodplace.getName().fullName);
        phone.setText(foodplace.getPhone().value);
        address.setText(foodplace.getAddress().value);
        email.setText(foodplace.getEmail().value);
        foodplace.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
