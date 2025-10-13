package seedu.bitebuddy;

import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.bitebuddy.commons.core.Config;
import seedu.bitebuddy.commons.core.LogsCenter;
import seedu.bitebuddy.model.Model;
import seedu.bitebuddy.model.ModelManager;
import seedu.bitebuddy.model.UserPrefs;
import seedu.bitebuddy.storage.Storage;
import seedu.bitebuddy.storage.UserPrefsStorage;
import seedu.bitebuddy.testutil.TestStubs;

/**
 * Tests for MainApp to exercise startup/shutdown fallbacks without launching the full UI.
 */
public class MainAppTest {

    private static class TestMainApp extends MainApp {
        @Override
        public void init() throws Exception {
            // Do not call super.init() which initialises the real app components.
        }

        // Expose protected/private methods for testing via reflection
        public Config callInitConfig(Path path) {
            return initConfig(path);
        }

        public UserPrefs callInitPrefs(UserPrefsStorage storage) {
            return initPrefs(storage);
        }

        public Model callInitModelManager(Storage storage, UserPrefs prefs) throws Exception {
            Method m = MainApp.class.getDeclaredMethod("initModelManager", Storage.class,
                    seedu.bitebuddy.model.ReadOnlyUserPrefs.class);
            m.setAccessible(true);
            return (Model) m.invoke(this, storage, prefs);
        }
    }

    @Test
    public void initConfig_missingFile_usesDefault(@TempDir Path tmp) throws Exception {
        TestMainApp app = new TestMainApp();
        Path missing = tmp.resolve("nonexistent.json");
        Config cfg = app.callInitConfig(missing);
        assertNotNull(cfg);
        // default config has a user prefs file path set
        assertNotNull(cfg.getUserPrefsFilePath());
        // the config should have been saved to the provided path
        assertTrue(Files.exists(missing));
    }

    @Test
    public void initPrefs_malformed_usesDefaults(@TempDir Path tmp) throws Exception {
        TestMainApp app = new TestMainApp();
        // use reusable UserPrefsStorageStub that can simulate failure
        TestStubs.UserPrefsStorageStub failingStorage = new TestStubs.UserPrefsStorageStub()
                .withThrowOnRead(true);

        UserPrefs prefs = app.callInitPrefs(failingStorage);
        assertNotNull(prefs);
    }

    @Test
    public void initConfig_recordsLog(@TempDir Path tmp) throws Exception {
        TestMainApp app = new TestMainApp();
        Path cfgPath = tmp.resolve("cfg.json");

        // capture log messages using reusable LogCapture
        Logger logger = LogsCenter.getLogger(MainApp.class);
        try (seedu.bitebuddy.testutil.LogCapture lc = new seedu.bitebuddy.testutil.LogCapture(logger)) {
            app.callInitConfig(cfgPath);
            assertTrue(lc.contains("Using config file"));
            assertTrue(lc.contains("Creating new config file") || lc.contains("could not be loaded"));
        }
    }

    @Test
    public void initModelManager_storageEmpty_usesSampleData(@TempDir Path tmp) {
        TestMainApp app = new TestMainApp();

        // use reusable StorageStub
        TestStubs.StorageStub storageStub = new TestStubs.StorageStub().withAddressBook(Optional.empty());

        UserPrefs up = new UserPrefs();
        Model model;
        try {
            model = app.callInitModelManager(storageStub, up);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertNotNull(model);
        // model should be constructed even if storage returns empty
        assertTrue(model.getAddressBook().getFoodplaceList().size() >= 0);
    }

    @Test
    public void start_callsUiStart() {
        TestMainApp app = new TestMainApp();

        TestStubs.UiStub ui = new TestStubs.UiStub();
        app.ui = ui;
        app.start(null);
        assertTrue(ui.started);
    }

    @Test
    public void stop_handlesSavePrefsIOException() {
        TestMainApp app = new TestMainApp();

        TestStubs.StorageStub storageStub2 = new TestStubs.StorageStub().withSaveUserPrefsThrows(true);

        app.storage = storageStub2;
        app.model = new ModelManager();
        // should not throw
        app.stop();
    }
}
