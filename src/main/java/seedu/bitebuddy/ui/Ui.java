package seedu.bitebuddy.ui;

import javafx.stage.Stage;

/**
 * API of UI component
 */
public interface Ui {

    /** Starts the UI (and the App).  */
    void start(Stage primaryStage);

    /**
     * Optional hook to display auto-fix summary detected during data load.
     * Default implementation does nothing.
     */
    void showAutoFixSummary(java.util.List<seedu.bitebuddy.storage.AutoFixRecord> fixes);
}
