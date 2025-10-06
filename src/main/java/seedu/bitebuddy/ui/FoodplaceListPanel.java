package seedu.bitebuddy.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.bitebuddy.commons.core.LogsCenter;
import seedu.bitebuddy.model.foodplace.Foodplace;

/**
 * Panel containing the list of foodplaces.
 */
public class FoodplaceListPanel extends UiPart<Region> {
    private static final String FXML = "FoodplaceListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(FoodplaceListPanel.class);

    @FXML
    private ListView<Foodplace> foodplaceListView;

    /**
     * Creates a {@code FoodplaceListPanel} with the given {@code ObservableList}.
     */
    public FoodplaceListPanel(ObservableList<Foodplace> foodplaceList) {
        super(FXML);
        foodplaceListView.setItems(foodplaceList);
        foodplaceListView.setCellFactory(listView -> new FoodplaceListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Foodplace} using a {@code FoodplaceCard}.
     */
    class FoodplaceListViewCell extends ListCell<Foodplace> {
        @Override
        protected void updateItem(Foodplace foodplace, boolean empty) {
            super.updateItem(foodplace, empty);

            if (empty || foodplace == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new FoodplaceCard(foodplace, getIndex() + 1).getRoot());
            }
        }
    }

}
