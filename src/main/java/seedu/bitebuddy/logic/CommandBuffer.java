package seedu.bitebuddy.logic;

/**
 * Represents a node in linked list. Used to track command history across the session of BiteBuddy.
 */
public class CommandBuffer {
    private static CommandBuffer head;
    private static CommandBuffer current;
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

        buffer.prev = CommandBuffer.head;
        if (head != null) {
            CommandBuffer.head.next = buffer;
        }

        CommandBuffer.head = buffer;
        CommandBuffer.current = buffer;
    }

    /**
     * Returns the stored command in a node.
     */
    public String getCommand() {
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
}
