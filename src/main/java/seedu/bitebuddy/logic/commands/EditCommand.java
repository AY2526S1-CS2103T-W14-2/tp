package seedu.bitebuddy.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_RATE;
import static seedu.bitebuddy.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.bitebuddy.model.Model.PREDICATE_SHOW_ALL_FOODPLACES;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.bitebuddy.commons.core.index.Index;
import seedu.bitebuddy.commons.util.CollectionUtil;
import seedu.bitebuddy.commons.util.ToStringBuilder;
import seedu.bitebuddy.logic.Messages;
import seedu.bitebuddy.logic.commands.exceptions.CommandException;
import seedu.bitebuddy.model.Model;
import seedu.bitebuddy.model.foodplace.*;
import seedu.bitebuddy.model.foodplace.Rate;
import seedu.bitebuddy.model.tag.Tag;

/**
 * Edits the details of an existing foodplace in the bitebuddy book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the foodplace identified "
            + "by the index number used in the displayed foodplace list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG] "
            + "[" + PREFIX_RATE + "RATING]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_FOODPLACE_SUCCESS = "Edited Foodplace: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_FOODPLACE = "This foodplace already exists in the bitebuddy book.";

    private final Index index;
    private final EditFoodplaceDescriptor editFoodplaceDescriptor;

    /**
     * @param index of the foodplace in the filtered foodplace list to edit
     * @param editFoodplaceDescriptor details to edit the foodplace with
     */
    public EditCommand(Index index, EditFoodplaceDescriptor editFoodplaceDescriptor) {
        requireNonNull(index);
        requireNonNull(editFoodplaceDescriptor);

        this.index = index;
        this.editFoodplaceDescriptor = new EditFoodplaceDescriptor(editFoodplaceDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Foodplace> lastShownList = model.getFilteredFoodplaceList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FOODPLACE_DISPLAYED_INDEX);
        }

        Foodplace foodplaceToEdit = lastShownList.get(index.getZeroBased());
        Foodplace editedFoodplace = createEditedFoodplace(foodplaceToEdit, editFoodplaceDescriptor);

        if (!foodplaceToEdit.isSameFoodplace(editedFoodplace) && model.hasFoodplace(editedFoodplace)) {
            throw new CommandException(MESSAGE_DUPLICATE_FOODPLACE);
        }

        model.setFoodplace(foodplaceToEdit, editedFoodplace);
        model.updateFilteredFoodplaceList(PREDICATE_SHOW_ALL_FOODPLACES);
        return new CommandResult(String.format(MESSAGE_EDIT_FOODPLACE_SUCCESS, Messages.format(editedFoodplace)));
    }

    /**
     * Creates and returns a {@code Foodplace} with the details of {@code foodplaceToEdit}
     * edited with {@code editFoodplaceDescriptor}.
     */
    private static Foodplace createEditedFoodplace(Foodplace foodplaceToEdit,
                                                   EditFoodplaceDescriptor editFoodplaceDescriptor) {
        assert foodplaceToEdit != null;

        Name updatedName = editFoodplaceDescriptor.getName().orElse(foodplaceToEdit.getName());
        Phone updatedPhone = editFoodplaceDescriptor.getPhone().orElse(foodplaceToEdit.getPhone());
        Email updatedEmail = editFoodplaceDescriptor.getEmail().orElse(foodplaceToEdit.getEmail());
        Address updatedAddress = editFoodplaceDescriptor.getAddress().orElse(foodplaceToEdit.getAddress());
        Set<Tag> updatedTags = editFoodplaceDescriptor.getTags().orElse(foodplaceToEdit.getTags());
        Rate updatedRate = editFoodplaceDescriptor.getRating().orElse(foodplaceToEdit.getRate());

        return new Foodplace(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags, updatedRate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editFoodplaceDescriptor.equals(otherEditCommand.editFoodplaceDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editFoodplaceDescriptor", editFoodplaceDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the foodplace with. Each non-empty field value will replace the
     * corresponding field value of the foodplace.
     */
    public static class EditFoodplaceDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;
        private Rate rate;

        public EditFoodplaceDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditFoodplaceDescriptor(EditFoodplaceDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
            setRating(toCopy.rate);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        public void setRating(Rate rate) {
            this.rate = rate;
        }

        public Optional<Rate> getRating() {
            return Optional.ofNullable(rate);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditFoodplaceDescriptor)) {
                return false;
            }

            EditFoodplaceDescriptor otherEditFoodplaceDescriptor = (EditFoodplaceDescriptor) other;
            return Objects.equals(name, otherEditFoodplaceDescriptor.name)
                    && Objects.equals(phone, otherEditFoodplaceDescriptor.phone)
                    && Objects.equals(email, otherEditFoodplaceDescriptor.email)
                    && Objects.equals(address, otherEditFoodplaceDescriptor.address)
                    && Objects.equals(tags, otherEditFoodplaceDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("bitebuddy", address)
                    .add("tags", tags)
                    .toString();
        }
    }
}
