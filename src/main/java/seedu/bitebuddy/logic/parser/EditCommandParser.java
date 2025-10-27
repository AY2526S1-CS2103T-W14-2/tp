package seedu.bitebuddy.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.bitebuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_CLOSE;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_CUISINE;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_OPEN;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_RATE;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.bitebuddy.commons.core.index.Index;
import seedu.bitebuddy.logic.commands.EditCommand;
import seedu.bitebuddy.logic.parser.exceptions.ParseException;
import seedu.bitebuddy.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_OPEN, PREFIX_CLOSE, PREFIX_CUISINE, PREFIX_TAG, PREFIX_NOTE, PREFIX_RATE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_CUISINE, PREFIX_NOTE, PREFIX_RATE, PREFIX_OPEN, PREFIX_CLOSE);

        EditCommand.EditFoodplaceDescriptor editFoodplaceDescriptor = buildDescriptor(argMultimap);

        if (!editFoodplaceDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editFoodplaceDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

    /**
     * Builds an EditFoodplaceDescriptor from the provided ArgumentMultimap.
     * Keeps parsing details out of the top-level parse method to maintain a single
     * level of abstraction there.
     */
    private EditCommand.EditFoodplaceDescriptor buildDescriptor(ArgumentMultimap argMultimap) throws ParseException {
        EditCommand.EditFoodplaceDescriptor descriptor = new EditCommand.EditFoodplaceDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            descriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            descriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            descriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            descriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(descriptor::setTags);
        if (argMultimap.getValue(PREFIX_NOTE).isPresent()) {
            descriptor.setNote(ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get()));
        }

        if (argMultimap.getValue(PREFIX_CUISINE).isPresent()) {
            descriptor.setCuisine(ParserUtil.parseCuisine(argMultimap.getValue(PREFIX_CUISINE).get()));
        }

        // parse rating values (support multiple rate prefixes but use the last one)
        if (!argMultimap.getAllValues(PREFIX_RATE).isEmpty()) {
            descriptor.setRate(ParserUtil.parseRatings(argMultimap.getAllValues(PREFIX_RATE)));
        }

        // parse timing: require both open and close
        if (ParserUtil.areBothPrefixesPresent(argMultimap, PREFIX_OPEN, PREFIX_CLOSE)) {
            String open = argMultimap.getValue(PREFIX_OPEN).orElse("");
            String close = argMultimap.getValue(PREFIX_CLOSE).orElse("");
            descriptor.setTiming(ParserUtil.parseTiming(open, close));
        } else if (!ParserUtil.areNeitherPrefixesPresent(argMultimap, PREFIX_OPEN, PREFIX_CLOSE)) {
            throw new ParseException(ParserUtil.MESSAGE_BOTH_TIMES_REQUIRED);
        }

        return descriptor;
    }
}
