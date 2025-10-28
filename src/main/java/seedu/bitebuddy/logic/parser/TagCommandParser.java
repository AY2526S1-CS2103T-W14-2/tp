package seedu.bitebuddy.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.bitebuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.bitebuddy.logic.Messages.MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import seedu.bitebuddy.commons.core.index.Index;
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
        List<String> tokens = splitTokens(args);

        if (tokens.size() < 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    TagCommand.MESSAGE_USAGE));
        }

        Index index = parseIndexToken(tokens.get(0));

        boolean isDelete = isDeleteToken(tokens);
        List<String> tagTokens = extractTagTokens(tokens, isDelete);
        Set<Tag> tags = ParserUtil.parseTags(tagTokens);

        return new TagCommand(index, tags, isDelete);
    }

    /**
     * Splits tokens by whitespace.
     * @param args Arguemnt from user input.
     * @return List of arguments
     */
    private List<String> splitTokens(String args) {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return List.of();
        }
        return Arrays.asList(trimmedArgs.split("\\s+"));
    }

    /**
     * Parses index token.
     * @param token Index token
     * @return Index
     * @throws ParseException
     */
    private Index parseIndexToken(String token) throws ParseException {
        try {
            return ParserUtil.parseIndex(token);
        } catch (ParseException pe) {
            // preserve the user-facing message about invalid displayed index
            throw new ParseException(MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX, pe);
        }
    }

    /**
     * Checks if token is delete token
     * @param tokens
     * @return true if token is the delete token, false otherwise.
     */
    private boolean isDeleteToken(List<String> tokens) {
        return tokens.size() > 1 && "/d".equals(tokens.get(1));
    }

    /**
     * Extracts tag tokens
     * @param tokens
     * @param isDelete
     * @return List of raw tags
     */
    private List<String> extractTagTokens(List<String> tokens, boolean isDelete) {
        if (isDelete) {
            if (tokens.size() <= 2) {
                return List.of();
            }
            return tokens.subList(2, tokens.size());
        }
        return tokens.subList(1, tokens.size());
    }
}
