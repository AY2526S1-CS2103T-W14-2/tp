package seedu.bitebuddy.testutil;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Small utility to capture java.util.logging output during tests.
 */
public class LogCapture implements AutoCloseable {
    private final String loggerName;
    private final TestHandler handler = new TestHandler();

    public LogCapture(Logger logger) {
        this.loggerName = logger.getName();
        Logger.getLogger(loggerName).addHandler(handler);
    }

    public boolean contains(String substring) {
        return handler.contains(substring);
    }

    @Override
    public void close() {
        Logger.getLogger(loggerName).removeHandler(handler);
    }

    private static class TestHandler extends Handler {
        final List<LogRecord> records = new ArrayList<>();

        @Override
        public void publish(LogRecord record) {
            records.add(record);
        }

        @Override
        public void flush() {}

        @Override
        public void close() throws SecurityException {}

        boolean contains(String substring) {
            return records.stream().anyMatch(r -> r.getMessage().contains(substring));
        }
    }
}
