package seedu.bitebuddy;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.logging.Level;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.bitebuddy.commons.core.Config;
import seedu.bitebuddy.commons.core.LogsCenter;
import seedu.bitebuddy.commons.core.Version;
import seedu.bitebuddy.commons.exceptions.DataLoadingException;
import seedu.bitebuddy.commons.util.ConfigUtil;
import seedu.bitebuddy.commons.util.StringUtil;
import seedu.bitebuddy.logic.Logic;
import seedu.bitebuddy.logic.LogicManager;
import seedu.bitebuddy.model.AddressBook;
import seedu.bitebuddy.model.Model;
import seedu.bitebuddy.model.ModelManager;
import seedu.bitebuddy.model.ReadOnlyAddressBook;
import seedu.bitebuddy.model.ReadOnlyUserPrefs;
import seedu.bitebuddy.model.UserPrefs;
import seedu.bitebuddy.model.util.SampleDataUtil;
import seedu.bitebuddy.storage.AddressBookStorage;
import seedu.bitebuddy.storage.JsonAddressBookStorage;
import seedu.bitebuddy.storage.JsonUserPrefsStorage;
import seedu.bitebuddy.storage.Storage;
import seedu.bitebuddy.storage.StorageManager;
import seedu.bitebuddy.storage.UserPrefsStorage;
import seedu.bitebuddy.ui.Ui;
import seedu.bitebuddy.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 4, 0, false);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing BiteBuddy ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());
        initLogging(config);

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        AddressBookStorage addressBookStorage = new JsonAddressBookStorage(userPrefs.getAddressBookFilePath());
        storage = new StorageManager(addressBookStorage, userPrefsStorage);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s bitebuddy book and {@code userPrefs}. <br>
     * The data from the sample bitebuddy book will be used instead if {@code storage}'s bitebuddy book is not found,
     * or an empty bitebuddy book will be used instead if errors occur when reading {@code storage}'s bitebuddy book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        logger.log(Level.INFO, "Using data file : {0}", storage.getAddressBookFilePath());

        Optional<ReadOnlyAddressBook> addressBookOptional;
        ReadOnlyAddressBook initialData;
        try {
            addressBookOptional = storage.readAddressBook();
            if (!addressBookOptional.isPresent()) {
                logger.log(Level.INFO, "Creating a new data file {0} populated with sample foodplaces.",
                        storage.getAddressBookFilePath());
            }
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataLoadingException e) {
            logger.log(Level.WARNING, "Data file at {0} could not be loaded. Will be starting with an empty bitebuddy.",
                    storage.getAddressBookFilePath());
            initialData = new AddressBook();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.log(Level.INFO, "Custom Config file specified {0}", configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.log(Level.INFO, "Using config file : {0}", configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            if (!configOptional.isPresent()) {
                logger.log(Level.INFO, "Creating new config file {0}", configFilePathUsed);
            }
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataLoadingException e) {
            logger.log(Level.WARNING, "Config file at {0} could not be loaded. Using default config properties.",
                    configFilePathUsed);
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Failed to save config file : {0}", StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.log(Level.INFO, "Using preference file : {0}", prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            if (!prefsOptional.isPresent()) {
                logger.log(Level.INFO, "Creating new preference file {0}", prefsFilePath);
            }
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataLoadingException e) {
            logger.log(Level.WARNING, "Preference file at {0} could not be loaded. Using default preferences.", prefsFilePath);
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Failed to save config file : {0}", StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.log(Level.INFO, "Starting BiteBuddy {0}", MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping BiteBuddy ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to save preferences {0}", StringUtil.getDetails(e));
        }
    }
}
