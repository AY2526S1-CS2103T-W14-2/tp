package seedu.bitebuddy.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.bitebuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.bitebuddy.logic.Messages.MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX;

import java.util.Arrays;
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
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    TagCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(tokens.get(0));

        } catch (IllegalValueException e) {
            throw new ParseException(MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX, e);
        }

        boolean isDelete = false;
        List<String> tagTokens;
        if (tokens.get(1).equals("/d")) {
            isDelete = true;
            if (tokens.size() == 2) {
                tagTokens = List.of();
            } else {
                tagTokens = tokens.subList(2, tokens.size());
            }
        } else {
            tagTokens = tokens.subList(1, tokens.size());
        }
        Set<Tag> tags = ParserUtil.parseTags(tagTokens);

        return new TagCommand(index, tags, isDelete);
    }
}
