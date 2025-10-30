---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# BiteBuddy Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well }_

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `FoodplaceListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Foodplace` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a foodplace).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

Argument processing helper classes:

There are two common styles of argument processing used by command parsers:
- Prefix-based commands use `ArgumentTokenizer` + `ArgumentMultimap` (e.g. `AddCommand`, `EditCommand`, etc.).
    - For commands that accept labelled fields using prefixes (e.g. `add n/NAME p/PHONE t/TAG ...`).
    - Tokenize the raw arguments with `ArgumentTokenizer` using the expected prefixes, then read values from the returned `ArgumentMultimap`.

- Positional-based (whitespace-separated) commands use `ArgumentPositionMapper` (e.g. `NoteCommand`, `RateCommand`, `CompareCommand`, etc.).
    - For commands that accept a fixed sequence of whitespace-separated values (e.g. `note 1 Good customer service!`).
    - `ArgumentPositionMapper` maps tokens by their position into parameters.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Foodplace` objects (which are contained in a `UniqueFoodplaceList` object).
* stores the currently 'selected' `Foodplace` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Foodplace>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Foodplace` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Foodplace` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.bitebuddy.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th foodplace in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new foodplace. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the foodplace was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the foodplace being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* food enthusiasts or anyone who wants to keep track of their favourite food places
* has a need to manage a significant number of food places contacts
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Persona (Gordon)**:

* Gordon likes to eat food and goes to many places and try out different kind of food
* Gordon does not have a way to record the places he likes and dislikes
* Gordon often forget places that he has tried before, what he rates them and what he thinks about them
* Gordon has difficulty recommending food places for his friends to try out because he cannot remember them
* Gordon has a wishlist of places he wished to try in his head but often forgets part of them

**Value proposition**:

* Provide a platform to collate information about food places that the user has visited such as, name, address, rating, cuisine, location, notes, etc.
* Users can quickly access any information they want through extensive sorting and searching feature to make quick and informed decisions
* Blacklist food places that the user dislikes
* Wishlist food places to keep track of places they want to try next
* Pin food places that users can frequently refer to (e.g., favourite food places, use for recommendation purposes, etc.)


### User stories
Legends: ✅ - Implemented, ❌ - Not implemented, ½ - Partially implemented
Priorities: High (must have) - `* * *`, Medium (good to have) - `* *`, Low (might have if time permits) - `*`

| Implemented? | Priority | As a …​                          | I want to …​                                                           | So that I can…​                                                                                   | Remarks                                              |
|--------------|----------|----------------------------------|------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------|------------------------------------------------------|
| ✅            | `* * *`  | user                             | add food places entries                                                | revisit these entries or recommend them                                                           | AddCommand                                           |
| ✅            | `* * *`  | user                             | delete an existing entry                                               | remove food places that I no longer need to refer to                                              | DeleteCommand                                        |
| ✅            | `* * *`  | user                             | edit an existing entry                                                 | update details if they change without having to delete and adding it again                        | EditCommand                                          |
| ✅            | `* * *`  | user                             | rate an existing entry                                                 | refer to the rating to know how good the food place is based on my rating                         | RateCommand                                          |
| ✅            | `* * *`  | user                             | add notes to an existing entry                                         | keep track of what I liked or disliked at the restaurant                                          | NoteCommand                                          |
| ✅            | `* * *`  | user                             | tag food places with my own custom keywords                            | filter by personal context                                                                        | TagCommand                                           |
| ✅            | `* * *`  | user                             | search for a specific food place quickly                               | don't have to spend too long finding a place I visited before                                     | FindCommand                                          |
| ✅            | `* * *`  | user                             | search based on rating / cuisine / tags                                | choose a food place that I want to go based on the specified parameters                           | FindCommand                                          |
| ✅            | `* * *`  | first-time user                  | have a clear guidance or help commands                                 | learn how to use and navigate around the app easily                                               | HelpCommand                                          |
| ✅            | `* * *`  | frequent user                    | pin my favourite food places                                           | quickly refer to them again next time                                                             | PinCommand, UnpinCommand                             |
| ✅            | `* * *`  | user                             | have a wishlist of food places I want to visit                         | plan for a future visit                                                                           | WishlistCommand                                      |
| ❌            | `* *`    | user                             | create different tabs / groups                                         | organise and categorise the food places however I want                                            |                                                      |
| ❌            | `* *`    | expert user                      | add alias to commands                                                  | have shortcuts to execute commands faster                                                         |                                                      |
| ❌            | `* *`    | indecisive user                  | let the app randomly pick a food place for me                          | decide on what I want to eat without deciding by myself                                           |                                                      |
| ✅            | `* *`    | potential user exploring the app | see the app pre-populated with sample data                             | easily see how the app will look when it is in use                                                | If no address book is found, default data is created |
| ✅            | `* *`    | user                             | keep track of the food I dislike                                       | avoid them                                                                                        | BlacklistCommand                                     |
| ❌            | `* *`    | user                             | filter food places that are open or closed based on current time       | so that I can decide where to eat based on restaurants that are currently open only               |                                                      |
| ❌            | `* *`    | user                             | sort by distance from my current location                              | know which food places is most conveniently located                                               |                                                      |
| ½            | `* *`    | user                             | add an average dining cost of an existing entry                        | choose a food place that best fits my current budget                                              | Can use NoteCommand tentatively                      |
| ½            | `* *`    | expert user                      | add common keyboard shortcuts                                          | quickly add and update new food places                                                            | ↑ and ↓ arrows to cycle command execution history    |
| ❌            | `* *`    | frequent typer                   | autocomplete suggestions when entering name of food places or cuisines | add food places faster                                                                            |                                                      |
| ✅            | `* *`    | user                             | compare two or more food places side by side                           | make a better decision when choosing where to eat                                                 | CompareCommand                                       |
| ❌            | `*`      | user                             | know what kind of cuisines I often frequent to                         | know what cuisines I might be favourable to                                                       |                                                      |
| ❌            | `*`      | user                             | click on the food place link / email                                   | quickly contact them for reservation                                                              |                                                      |
| ❌            | `*`      | user with poor eyesight          | adjust text / UI size                                                  | see the address book entries more clearly                                                         |                                                      |
| ❌            | `*`      | user                             | find a place in my data using multiple filters                         | find a place that I have forgotten the name of                                                    |                                                      |
| ½            | `*`      | user                             | display a list of all food places I have recommended before            | easily share my top picks with friends                                                            | Can use PinCommand tentatively                       |
| ❌            | `*`      | user                             | view my dining history in reverse chronological order                  | recall food places I have visited recently                                                        |                                                      |
| ❌            | `*`      | user                             | see statistics on characteristics of food places I have visited        | discover patterns about my eating preferences and get recommendations for cuisines I rarely tried |                                                      |

*{More may be added in the future}*

### Use cases

(For all use cases below, the **System** is `BiteBuddy` and the **Actor** is the `user`, unless specified otherwise)


**Use case: UC01 - Add a food place**

System: BiteBuddy
Actor: User
Preconditions: BiteBuddy is running.
Guarantees: If successful, the food place is stored in BiteBuddy without duplicates.

**MSS**

1. User provides the details for the food place(Name, phone number, email address and postal address)
2. BiteBuddy adds the food place and displays confirmation.

    Use case ends.

**Extensions**

* 1a. User omits a required detail.
    * 1a1. BiteBuddy shows an error message for missing fields.
    
      Use case ends.

* 2a. Validation fails (e.g., phone number not numeric, email format invalid, or duplicate entry).
    * 2a1. BiteBuddy shows an appropriate error message.

      Use case ends.

**Use case: UC02 - Delete a food place**

System: BiteBuddy
Actor: User
Preconditions: At least one food place exists.
Guarantees: The food place is deleted if index is valid.

**MSS**

1. User requests to list food places
2. BiteBuddy shows a list of food places
3. User chooses to delete a specific food place from the list.
4. BiteBuddy deletes the food place and displays confirmation.

   Use case ends.

**Extensions**

* 3a. The given index is invalid.
    * 3a1. BiteBuddy shows an error for invalid index.

      Use case ends.

* 3b. Multiple indexes are given.
    * 3b1. BiteBuddy shows an error message that details correct usage.

      Use case ends.

**Use case: UC03 - Edit a food place**

System: BiteBuddy  
Actor: User  
Preconditions: At least one food place exists.  
Guarantees: If successful, the specified food place is updated with the new details.

**MSS**

1. User requests to list food places
2. BiteBuddy shows a list of food places
3. User chooses to edit an existing food place from the list and provides the index of the food place and the new fields to update (e.g., name, address, rating, tags).
4. BiteBuddy updates the food place and displays confirmation.  

    Use case ends.

**Extensions**

* 3a. User omits all editable fields.
    * 3a1. BiteBuddy shows an error message indicating no fields provided.  

      Use case ends.

* 3b. The given index is invalid.
    * 3b1. BiteBuddy shows an error for invalid index. 

      Use case ends.

* 3c. One or more given fields are invalid.
    * 3c1. BiteBuddy shows an appropriate error message and does not apply changes.  

      Use case ends.

* 3a. Edit results in a duplicate (conflicts with an existing entry).
    * 3a1. BiteBuddy rejects the edit and shows a duplicate-entry error. 
    
      Use case ends.

**Use case: UC04 - List all food places**

System: BiteBuddy  
Actor: User  
Preconditions: BiteBuddy is running.  
Guarantees: The full list of food places is displayed.

**MSS**
1. User requests to list all food places.
2. BiteBuddy retrieves the full address book and updates the displayed list to show all food places.

Use case ends.

**Extensions**
* 1a. Address book contains pinned foodplaces.
    * 1a1. BiteBuddy displays pinned foodplaces at the top of the list.
    
    Use case resumes at step 2.

**Use case: UC05 - Find food places**

System: BiteBuddy  
Actor: User  
Preconditions: At least one food place exists.  
Guarantees: If successful, a filtered list of food places matching the query is displayed.

**MSS**
1. User requests to find foodplace with one or more search keywords or filters.
2. BiteBuddy updates the displayed list to show only matching food places and displays a summary message indicating the number of results found.

Use case ends.

**Extensions**
* 1a. No keywords/filters provided.
    * 1a1. BiteBuddy shows an error message with correct usage.
    
      Use case ends.

* 2a. No matches found.
    * 2a1. BiteBuddy displays an empty list and a message indicating no results.
    
      Use case ends.

* 2b. Invalid filter/value provided.
    * 2b1. BiteBuddy shows an error message describing the invalid filter or value.
    
      Use case ends.

**Use case: UC06 - Add tag(s) to a food place**

System: BiteBuddy
Actor: User
Preconditions: At least one food place exists.
Guarantees: Valid tags are added and duplicates are ignored.

**MSS**

1. User requests to list food places
2. BiteBuddy shows a list of food places
3. User requests to add tag(s) to a specific food place in the list.
4. BiteBuddy updates the food place with the new tag(s) and displays confirmation.

   Use case ends.

**Extensions**

* 3a. The given index is invalid.
    * 3a1. BiteBuddy shows an error message for invalid index.

      Use case ends.

* 3b. The given tag is empty.
    * 3b1. BiteBuddy shows an error message for empty tag.

      Use case ends.

* 3c. The user provides duplicate tags.
    * 3a1. BiteBuddy ignores duplicates and keeps only unique tags.

      Use case ends.


**Use case: UC07 - Add a note to a food place**

System: BiteBuddy
Actor: User
Preconditions: At least one food place exists.
Guarantees: A note is stored for the food place and existing note is overwritten if present.

**MSS**

1. User requests to list food places
2. BiteBuddy shows a list of food places
3. User chooses to add a note to a specific food place in the list.
4. BiteBuddy updates the food place with the note and displays confirmation.

   Use case ends.

**Extensions**

* 3a. The given index is invalid.
    * 3a1. BiteBuddy shows an error for invalid index.

      Use case ends.

* 3b. The given note is empty.
    * 3b1. BiteBuddy treats this as discarding the existing note.

      Use case ends.

* 3c. The given note is a duplicate of the existing note.
    * 3c1. BiteBuddy does not change the entry.

      Use case ends.

**Use case: UC08 - Rate a food place**

System: BiteBuddy
Actor: User
Preconditions: At least one food place exists.
Guarantees: A rating between 1–10 is stored and existing rating is overwritten.

**MSS**

1. User requests to list food places
2. BiteBuddy shows a list of food places
3. User chooses to rate a specific food place from the list.
4. BiteBuddy updates the food place with the rating and displays confirmation.

   Use case ends.

**Extensions**

* 3a. The given index is invalid.
    * 3a1. BiteBuddy shows an error for invalid index.

      Use case ends.

* 3b. The given rating is invalid.
    * 3b1. BiteBuddy shows an error message for invalid rating.

      Use case ends.

**Use case: UC09 - Add a food place to wishlist**

System: BiteBuddy  
Actor: User  
Preconditions: At least one food place exists. The specified food place is not wishlisted 
Guarantees: If successful, the food place is added to the user's wishlist.

**MSS**

1. User requests to list food places
2. BiteBuddy shows a list of food places
3. User chooses to add a food place in the list to the wishlist.
4. BiteBuddy adds the food place to the wishlist and displays confirmation.  

    Use case ends.

**Extensions**

* 3a. The given index is invalid.
    * 3a1. BiteBuddy shows an error for invalid index.  

      Use case ends.

* 3b. The chosen food place is blacklisted.
    * 3b1. BiteBuddy removes the food place from the blacklist

      Use case resumes from step 4.

**Use case: UC10 - Remove a food place from wishlist**

System: BiteBuddy  
Actor: User  
Preconditions: At least one food place exists. The specified food place is currently on the user's wishlist.  
Guarantees: If successful, the food place is removed from the user's wishlist.

**MSS**

1. User requests to list food places.
2. BiteBuddy shows a list of food places.
3. User requests to unwishlist a specific food place in the list.
4. BiteBuddy removes the food place from the wishlist and displays a confirmation message.

    Use case ends.

**Extensions**

* 3a. The given index is invalid.
    * 3a1. BiteBuddy shows an error message for invalid index.  

     Use case ends.

**Use case: UC11 - List all wishlisted food places**

System: BiteBuddy  
Actor: User  
Preconditions: At least one wishlisted food place exists.
Guarantees: The full list of wishlisted food places is displayed.

**MSS**
1. User requests to list all wishlisted food places.
2. BiteBuddy retrieves all wishlisted food places and updates the displayed list to show all food places.

Use case ends.

**Use case: UC12 - Add a food place to the blacklist**

System: BiteBuddy  
Actor: User  
Preconditions: At least one food place exists.  
Guarantees: If successful, the food place is marked as blacklisted and excluded from certain recommendations or listings.

**MSS**

1. User requests to list food places.
2. BiteBuddy shows a list of food places.
3. User chooses to blacklist a specific food place from the list.
4. BiteBuddy marks the food place as blacklisted and displays confirmation.  

    Use case ends.

**Extensions**

* 3a. The given index is invalid.
    * 3a1. BiteBuddy shows an error for invalid index. 

      Use case ends.

* 3b. The chosen food place is on the wishlist.
    * 3a1. BiteBuddy removes the food place from the wishlist
    
      Use case resumes from step 4.

**Use case: UC13 - Remove a food place from blacklist**

System: BiteBuddy  
Actor: User  
Preconditions: At least one food place exists. The specified food place is currently on the user's blacklist.  
Guarantees: If successful, the food place is removed from the user's blacklist.

**MSS**

1. User requests to list food places.
2. BiteBuddy shows a list of food places.
3. User requests to un-blacklist a specific food place in the list.
4. BiteBuddy removes the food place from the blacklist and displays a confirmation message.

    Use case ends.

**Extensions**

* 3a. The given index is invalid.
    * 3a1. BiteBuddy shows an error message for invalid index.  

        Use case ends.

**Use case: UC14 - List all blacklisted food places**

System: BiteBuddy  
Actor: User  
Preconditions: At least one blacklisted food place exists.
Guarantees: The full list of blacklisted food places is displayed.

**MSS**
1. User requests to list all blacklisted food places.
2. BiteBuddy retrieves all blacklisted food places and updates the displayed list to show all food places.

Use case ends.

**Use case: UC15 - Pin a food place**

System: BiteBuddy  
Actor: User  
Preconditions: At least one food place exists.  
Guarantees: If successful, the food place is pinned.

**MSS**

1. User requests to list food places.
2. BiteBuddy shows a list of food places.
3. User chooses to pin a specific food place on the list.
4. BiteBuddy pins the food place and displays confirmation.  

    Use case ends.

**Extensions**

* 3a. The given index is invalid.
    * 3a1. BiteBuddy shows an error for invalid index. 

      Use case ends.

* 3b. Food place already pinned.
    * 3b1. BiteBuddy informs the user and makes no change.

      Use case ends.

* 3c. Pin limit reached.
    * 3c1. BiteBuddy informs the user that the pin limit has been reached and makes no change.

      Use case ends.

**Use case: UC16 - Unpin a food place**

System: BiteBuddy  
Actor: User  
Preconditions: At least one food place exists. The specified food place is currently pinned.  
Guarantees: If successful, the food place is unpinned.

**MSS**

1. User requests to list food places.  
2. BiteBuddy shows a list of food places.  
3. User chooses to unpin a specific food place in the list.  
4. BiteBuddy validates the index, unpins the food place, and displays a confirmation message.

Use case ends.

**Extensions**

* 3a. The given index is invalid.
    * 3a1. BiteBuddy shows an error message for invalid index.  

      Use case ends.

* 3b. The chosen food place is not pinned.
    * 3b1. BiteBuddy informs the user that the food place is not pinned and makes no change.

      Use case ends.


**Use case: UC17 - Compare two or more food places**

System: BiteBuddy  
Actor: User  
Preconditions: At least two food places exist.  
Guarantees: If successful, a comparison view or summary of the selected food places is presented to the user.

**MSS**

1. User requests to list food places.  
2. BiteBuddy shows a list of food places.  
3. User issues the compare command with two valid indexes.  
4. BiteBuddy retrieves the corresponding food places, and displays a comparison view and summary showing key attributes (e.g., name, rating, cuisine, notes, tags).  

    Use case ends.

**Extensions**

* 3a. Fewer than two indexes are provided.  
    * 3a1. BiteBuddy shows an error message with correct usage example.  

      Use case ends.

* 3b. One or more given indexes are invalid.  
    * 3b1. BiteBuddy shows an error message identifying the invalid index(es) and does not perform the comparison.  

      Use case ends.

* 3c. Duplicate indexes are provided.  
    * 3c1. BiteBuddy shows an error message that states duplicate indexes are not allowed.  

      Use case ends.

* 3d. More than two indexes are provided.  
    * 3d1. BiteBuddy compares the first 2 indexes only and ignores the additional indexes.  

      Use case resumes from step 4.

**Use case: UC18 - Display help information**

System: BiteBuddy  
Actor: User  
Preconditions: BiteBuddy is running.  
Guarantees: Relevant help information is shown to the user.

**MSS**
1. User requests for help with a specific command.
2. BiteBuddy displays detailed usage for the specified command.

Use case ends.

**Extensions**
* 1a. The given command is invalid.
    * 1a1. BiteBuddy shows a message that the command is unrecognized.
    
    Use case ends.

**Use case: UC19 - Clear all food places**

System: BiteBuddy  
Actor: User  
Preconditions: At least one food place exists.
Guarantees: If successful, all food places are removed from the address book.

**MSS**
1. User requests to clear all food places.
2. BiteBuddy deletes all food places from the address book and displays a confirmation message.

Use case ends.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 food places without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  Error messages must explicitly state the error and provide guidance on correcting the input.
5.  Should not lose data if the application crashes unexpectedly.
6.  Should store all user data locally in a human-editable text file.
7.  Should be a single-user application and not require networked or multi-user support.
8.  The GUI should work well at 1920×1080 and higher (100%–125% scale) and remain usable at 1280×720 and higher (150% scale).
9.  Should not depend on a remote server.
10. The product should be packaged as a single file and not exceed 500 MB; document files should not exceed 15 MB each.

### Glossary
Organized in alphabetical order

* **CLI (Command Line Interface)**: A text-based interface that allows users to interact with the application by typing commands.
* **Command**: An instruction entered by the user to perform a specific action in the application (e.g., add, delete, list).
* **Custom Keyword**: A user-defined word or phrase that can be used to filter, search, or tag specific entries in BiteBuddy.
* **Food Place**: Any food place, including but not limited to restaurants, hawker stalls, food trucks, and cafes.
* **GUI (Graphical User Interface)**: The visual interface of the application that allows users to interact with it using graphical elements.
* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Tag**: A label that can be assigned to food places to categorize or group them.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.


### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a foodplace

1. Deleting a foodplace while all foodplaces are being shown

   1. Prerequisites: List all foodplaces using the `list` command. Multiple foodplaces in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No foodplace is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
