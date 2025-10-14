package seedu.bitebuddy.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

public class LogsCenterTest {

    @Test
    public void init_setsLogLevel_andAddsHandlers() {
        Config cfg = configWithLevel(Level.FINE);
        LogsCenter.init(cfg);

        Logger base = baseLogger();
        assertBaseLevel(Level.FINE, base);
        assertChildIsLoggable("testChild", Level.FINE);
        assertHasConsoleAndFile(base);

        // best-effort cleanup
        cleanupLoggerAndFiles(base);
    }

    /* Helper methods */

    private Config configWithLevel(Level level) {
        Config cfg = new Config();
        cfg.setLogLevel(level);
        return cfg;
    }

    private Logger baseLogger() {
        return Logger.getLogger("bitebuddy");
    }

    private void assertBaseLevel(Level expected, Logger base) {
        assertEquals(expected, base.getLevel());
    }

    private void assertChildIsLoggable(String childName, Level level) {
        Logger child = LogsCenter.getLogger(childName);
        assertTrue(child.isLoggable(level));
    }

    private void assertHasConsoleAndFile(Logger base) {
        boolean hasConsole = false;
        boolean hasFile = false;
        for (Handler h : base.getHandlers()) {
            if (h instanceof ConsoleHandler) {
                hasConsole = true;
            }
            if (h instanceof FileHandler) {
                hasFile = true;
            }
        }
        assertTrue(hasConsole, "Expected base logger to have a ConsoleHandler");
        assertTrue(hasFile, "Expected base logger to have a FileHandler");
    }

    private void cleanupLoggerAndFiles(Logger base) {
        Handler[] handlers = base.getHandlers();
        for (Handler h : handlers) {
            try {
                h.close();
            } catch (SecurityException ignored) {
                // ignore close failures
            }
            base.removeHandler(h);
        }

        // Delete log file and rotated files if present (best-effort)
        tryDelete(Paths.get("bitebuddy.log"));
        for (int i = 0; i < 5; i++) {
            tryDelete(Paths.get("bitebuddy.log." + i));
        }
        tryDelete(Paths.get("bitebuddy.log.lck"));
    }

    private void tryDelete(Path p) {
        try {
            Files.deleteIfExists(p);
        } catch (IOException ignored) {
            // ignore delete failures
        }
    }
}
