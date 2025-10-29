package seedu.bitebuddy.logic;

/**
 * Represents a node in linked list. Used to track command history across the session of BiteBuddy.
 */
public class CommandBuffer {
    private static CommandBuffer head;
    private static CommandBuffer current;
    private static int size = 0;
    private static String pendingCommand = null;

    private CommandBuffer next;
    private CommandBuffer prev;
    private final String command;

    /**
     * Creates a CommandBuffer node. This can only be called from the Push method.
     * @param command The command to be saved.
     */
    private CommandBuffer(String command) {
        this.command = command;
    }

    /**
     * Adds a new node to the head of the linked list.
     * @param command The command to be saved.
     */
    public static void push(String command) {
        CommandBuffer buffer = new CommandBuffer(command);

        // Check if already initialized
        if (head != null) {
            buffer.prev = head.prev;
            head.prev.next = buffer;
        }

        // Create new head specifically to point to latest command
        head = new CommandBuffer("");
        head.prev = buffer;
        // Stub
        buffer.next = head;
        current = head;

        // Reset pending command
        pendingCommand = null;

        size += 1;
    }

    /**
     * Returns the stored command in a node.
     */
    public String getCommand() {
        if (current == head && pendingCommand != null) {
            return pendingCommand;
        }
        return command;
    }

    /**
     * Sets to the next node in the linked list.
     */
    public static void getNext() {
        if (current.next != null) {
            current = current.next;
        }
    }

    /**
     * Sets to the previous node in the linked list.
     */
    public static void getPrev() {
        if (current.prev != null) {
            current = current.prev;
        }
    }

    /**
     * Returns the head of the linked list.
     */
    public static CommandBuffer getCurrent() {
        return current;
    }

    /**
     * Sets the current command that hasn't been passed yet to be saved
     */
    public static void setPendingCommand(String command) {
        pendingCommand = command;
    }

    /**
     * Returns if the current node is the head or not.
     */
    public static boolean isHead() {
        if (current != null && head != null) {
            return current == head;
        }
        return false;
    }

    /**
     * Empties the command buffer. Used for tests.
     */
    public static void clear() {
        head = null;
        current = null;
        size = 0;
    }

    /**
     * Returns the size of the command buffer. Used for tests.
     */
    public static int getSize() {
        return size;
    }
}
