package seedu.bitebuddy.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.bitebuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.bitebuddy.commons.core.index.Index;
import seedu.bitebuddy.commons.exceptions.IllegalValueException;
import seedu.bitebuddy.logic.commands.TagCommand;
import seedu.bitebuddy.logic.parser.exceptions.ParseException;
import seedu.bitebuddy.model.tag.Tag;

/**
 * Parses input arguments and creates a new {@code TagCommand} object.
 * Format: tag INDEX TAG1 [TAG2] ...
 */
public class TagCommandParser implements Parser<TagCommand> {

    /**
     * Parses the given arguments in the context of the {@code TagCommand}.
     *
     * @param args Argument from user input.
     * @return TagCommand.
     * @throws ParseException if user doesnt follow the format.
     */
    @Override
    public TagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();

        // Split into tokens by whitespace
        List<String> tokens = Arrays.asList(trimmedArgs.split("\\s+"));

        // Must have at least index + 1 tag
        if (tokens.size() < 2) {
            throw new ParseException("Missing index and/or tag! " + "\n"
                + "Format: tag INDEX TAG1 [TAG2]...");
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(tokens.get(0));
        } catch (IllegalValueException e) {
            throw new ParseException("The index provided must be a positive integer!", e);
        }

        // Remaining tokens are tags
        Set<Tag> tags = new HashSet<>();
        for (int i = 1; i < tokens.size(); i++) {
            String trimmed = tokens.get(i).trim();
            if (trimmed.isEmpty()) {
                throw new ParseException("TAG cannot be empty!");
            }
            tags.add(new Tag(trimmed));
        }

        if (tags.isEmpty()) {
            throw new ParseException("At least one tag must be provided!");
        }

        return new TagCommand(index, tags);
    }
}
