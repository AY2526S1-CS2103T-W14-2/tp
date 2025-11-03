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

- SE-EDU AddressBook Level 3 project — used as the primary template for architecture, componentisation and many example snippets: https://github.com/se-edu/addressbook-level3
- PlantUML — used to author sequence/class/activity diagrams: https://plantuml.com/
- OpenJFX (JavaFX) — UI components and FXML usage: https://openjfx.io/
- Jackson (FasterXML) — JSON read/write helpers: https://github.com/FasterXML/jackson
- Gradle — build scripts and dependency management: https://gradle.org/
- JUnit 5 — unit testing framework: https://junit.org/junit5/
- OpenJDK / Java 17 — target runtime: https://openjdk.org/
- Markbind - used to generate app website: https://markbind.org/
- GitHub Copilot - used for autocompleting and drafting code, as well as doing initial reviews for pull requests: https://github.com/features/copilot

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

**`Main`** (consisting of classes [`Main`](https://github.com/AY2526S1-CS2103T-W14-2/tp/blob/master/src/main/java/seedu/bitebuddy/Main.java) and [`MainApp`](https://github.com/AY2526S1-CS2103T-W14-2/tp/blob/master/src/main/java/seedu/bitebuddy/MainApp.java)) is in charge of the app launch and shut down.
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

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2526S1-CS2103T-W14-2/tp/blob/master/src/main/java/seedu/bitebuddy/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `FoodplaceListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2526S1-CS2103T-W14-2/tp/blob/master/src/main/java/seedu/bitebuddy/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2526S1-CS2103T-W14-2/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Foodplace` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2526S1-CS2103T-W14-2/tp/blob/master/src/main/java/seedu/bitebuddy/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.

</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, the command is first saved to the `CommandBuffer`
1. The command is then passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
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
**API** : [`Model.java`](https://github.com/AY2526S1-CS2103T-W14-2/tp/blob/master/src/main/java/seedu/bitebuddy/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Foodplace` objects (which are contained in a `UniqueFoodplaceList` object).
* stores the currently 'selected' `Foodplace` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Foodplace>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

### Storage component

**API** : [`Storage.java`](https://github.com/AY2526S1-CS2103T-W14-2/tp/blob/master/src/main/java/seedu/bitebuddy/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)


<box type="info" seamless>

**Note:** In storage, `AddressBook` refers to the collection of `Foodplace`, as they all contain addresses as mandatory fields. We decided that keeping AddressBook from AB3 would make more sense than changing its symbol to mean something related to `Foodplace`, since the word Foodplaces implies an address.<br>

</box>


### Common classes

The following are a non-exhaustive list of common classes that live in `seedu.bitebuddy.commons` and are reused across multiple components:

- AppParameters — parses and holds command-line/startup arguments.
- Config and GuiSettings — store application and UI preferences (window size, position, theme, etc.).
- LogsCenter — configures and exposes java.util.logging loggers used throughout the app.
- Index — 1-based index wrapper used by commands and UI to avoid off-by-one errors.
- StringUtil, CollectionUtil, DateTimeUtil — small, well-tested utility helpers for common string, collection and date/time operations.
- IllegalValueException — exception type thrown when parsed input violates domain constraints.
- ConfigUtil and FileUtil — helpers for reading/writing config and data files in a safe, testable manner.

<box type="info" seamless>

**Guideline:** interact with these classes through their public APIs (interfaces/static helpers) rather than implementation details to preserve loose coupling and ease testing.<br>

**Disclaimer:** This is a non-exhaustive list of common classes meant to give the developer an idea on what kind of classes are contained within the package. Please see [`commons`](https://github.com/AY2526S1-CS2103T-W14-2/tp/tree/master/src/main/java/seedu/bitebuddy/commons) for a full list of classes.

</box>

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

<box type="info" seamless>

**Note:** Developers should refer to this section to verify features already implemented and continue working on features that needs implementing to align to the user needs.

</box>

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

<box type="info" seamless>

**Note:** For all use cases below, the **System** is `BiteBuddy` and the **Actor** is the `user`, unless specified otherwise

</box>


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
* 1a. Address book contains pinned food places.
    * 1a1. BiteBuddy displays pinned food places at the top of the list.
    
    Use case resumes at step 2.

**Use case: UC05 - Find food places**

System: BiteBuddy  
Actor: User  
Preconditions: At least one food place exists.  
Guarantees: If successful, a filtered list of food places matching the query is displayed.

**MSS**
1. User requests to find food places with one or more search keywords or filters.
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

**Use case: UC07 - Delete tag(s) from a food place**

System: BiteBuddy
Actor: User
Preconditions: At least one food place exists.
Guarantees: The specified tag(s) are removed from the food place. Deletion is case-insensitive, and if no tags are provided, all tags are cleared.

**MSS**

1. User requests to list food places
2. BiteBuddy shows a list of food places
3. User issues a delete-tag command with one or more tag names.
4. BiteBuddy removes the specified tag(s) from the target food place and displays a confirmation message.

   Use case ends.

**Extensions**

* 3a. The given index is invalid.
    * 3a1. BiteBuddy shows an error message for invalid index.

      Use case ends.

* 3b. The given tag(s) do not exist on the food place.
    * 3b1. BiteBuddy shows an error message for no matching tags found to delete.

      Use case ends.

* 3c. No tags given.
    * 3c1. BiteBuddy removes all tags from the specified food place.
    * 3c2. BiteBuddy displays a confirmation message that tags were updated.

      Use case ends.

**Use case: UC08 - Add a note to a food place**

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

**Use case: UC09 - Rate a food place**

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

**Use case: UC10 - Add a food place to wishlist**

System: BiteBuddy  
Actor: User  
Preconditions: At least one food place exists. The specified food place is not wishlisted.
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

**Use case: UC11 - Remove a food place from wishlist**

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

**Use case: UC12 - List all wishlisted food places**

System: BiteBuddy  
Actor: User  
Preconditions: At least one wishlisted food place exists.
Guarantees: The full list of wishlisted food places is displayed.

**MSS**
1. User requests to list all wishlisted food places.
2. BiteBuddy retrieves all wishlisted food places and updates the displayed list to show all food places.

    Use case ends.

**Use case: UC13 - Add a food place to the blacklist**

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

**Use case: UC14 - Remove a food place from blacklist**

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

**Use case: UC15 - List all blacklisted food places**

System: BiteBuddy  
Actor: User  
Preconditions: At least one blacklisted food place exists.
Guarantees: The full list of blacklisted food places is displayed.

**MSS**
1. User requests to list all blacklisted food places.
2. BiteBuddy retrieves all blacklisted food places and updates the displayed list to show all food places.

    Use case ends.

**Use case: UC16 - Pin a food place**

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

**Use case: UC17 - Unpin a food place**

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


**Use case: UC18 - Compare two or more food places**

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

**Use case: UC19 - Display help information**

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

**Use case: UC20 - Clear all food places**

System: BiteBuddy  
Actor: User  
Preconditions: At least one food place exists.  
Guarantees: If successful, all food places are removed from the address book.

**MSS**
1. User requests to clear all food places.
2. BiteBuddy deletes all food places from the address book and displays a confirmation message.

Use case ends.

**Use case: UC21 - Reuse previously entered commands**

System: BiteBuddy  
Actor: User  
Preconditions: At least one command has been used.  
Guarantees: If successful, user can reuse a previous command.

**MSS**
1. User requests to view a previous command.
2. BiteBuddy returns the user's previous command.
3. User selects this previous command to execute.
4. BiteBuddy executes the command.

    Use case ends.

**Extensions**

* 2a. There is no previous command.
    * 2a1. BiteBuddy does not change the current command.

      Use case resumes from step 4.

* 2b. There is another previous command.
    * 2b1. User can choose this other previous command instead.

      Use case resumes from step 3.

* 3b. User selects the latest command.
    * 3b1. BiteBuddy executes the latest command the user was originally typing.

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

##### Initial l:


   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample foodplaces. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Adding a foodplace

*Prerequisites:*
* BiteBuddy is running.
* Use the `list` command first to show current foodplaces and note the count.

##### Valid Test case 1 — Adding a complete foodplace:

Command: `add n/Golden Wok p/91234567 e/golden@example.com a/123 Orchard Rd t/chinese t/dinner`

Expected:
- Foodplace is added to the list; `list` shows one additional entry. 
- Details of the new foodplace shown in the status message: `Added Foodplace: Golden Wok ...`
- Tags `chinese`, `dinner` are present on the new entry.

##### Valid Test case 2 — Adding with only required fields:
    
Command: `add n/Coffee Corner a/50 Coffee St`

Expected:
- Foodplace is added successfully.
- Optional fields are empty/unset.
- Details of the new foodplace shown in the status message: `Added Foodplace: Coffee Corner ...`

##### Invalid Test case 1 — Adding with **missing required field**:

Command: `add p/91234567 e/a@b.com a/1 Example St`

Expected:
- No foodplace is added.
- Error details shown in the status message: `Invalid command format! add: Adds a foodplace...`

##### Invalid Test case 2 — Adding with **invalid optional field**:

Command: `add n/BadPhone p/phone123 a/12 Some Rd`

Expected:
- No foodplace is added.
- Error details shown in the status message: `Phone numbers should only contain numbers...`

##### Invalid Test case 3 — Adding a **duplicate foodplace**:

Steps:
- Add a foodplace: `add n/Duplicate a/10 Rd`
- Attempt to add the same details again.

Expected:
- Second add fails.
- Error details shown in the status message: `This foodplace already exists in BiteBuddy.`
- `list` shows only one instance.

##### Edge case 1 — Multiple tags and spacing handling: 

Command: `add n/TagEdge p/90000000 a/5 Lane t/fast t/ family  t/outdoor `

Expected:
- Multiple tags parsed correctly (whitespace around tags trimmed).
- Status message shows all unique tags attached.

### Deleting a foodplace

Deleting a foodplace while all foodplaces are being shown

*Prerequisites:*<br>
* BiteBuddy is running.
* **At least one foodplace** must exist in the list.
* Use the `list` command first to show current foodplaces and note the count.


##### Valid Test case - Deleting a valid foodplace:

Command: `delete 1`

Expected:
- First foodplace is deleted from the list.
- Details of the deleted foodplace shown in the status message: `Deleted Foodplace: ...`

##### Invalid Test case - Deleting a **foodplace at an invalid index**:

Command: `delete 0`

Expected:
- No foodplace is deleted.
- Error details shown in the status message: `Invalid command format! delete: Deletes the foodplace...`

### Editing a foodplace

Editing a foodplace while all foodplaces are being shown

*Prerequisites:*  
* BiteBuddy is running.
* **At least one foodplace** must exist in the list.  
* Use the `list` command first to list all foodplaces and note the current details.

##### Valid Test case — Editing all fields of a valid foodplace:

Command: `edit 1 n/Cafe Luna p/91234567 e/cafe@example.com a/20 Baker St t/cafe t/coffee`

Expected:
- Foodplace at index 1 is updated with the new name, phone, email, address and tags.
- Details of the updated foodplace shown in the status message: `Edited Foodplace: Cafe Luna ...`

##### Valid Test case — Editing only some fields:

Command: `edit 1 n/Cafe Luna`

Expected:
- Only the name of the first foodplace is changed; other fields remain unchanged.
- Status message shows the edited entry with unchanged fields preserved.

##### Valid Test case — Replacing tags with a new set:

Command: `edit 1 t/brunch t/outdoor`

Expected:
- Tags of the first foodplace are replaced by `brunch` and `outdoor`.
- Status message shows the edited entry with unchanged fields preserved.

##### Valid Test case — Clearing all tags:

Command: `edit 1 t/`

Expected:
- All tags are removed from the first foodplace.
- Status message shows the edited entry with unchanged fields preserved.

##### Invalid Test case — Editing a foodplace at an **invalid index**:

Command: `edit 0 n/NewName`

Expected:
- No changes applied.
- Error details shown in the status message: `Invalid command format! edit: Edits the details...`

##### Invalid Test case — Editing a foodplace at an **out-of-range index**:

Command: `edit 999 n/Nowhere`

Expected:
- No changes applied.
- Error details shown in the status message: `The foodplace index provided is invalid`

##### Invalid Test case — Editing with an **invalid field**:

Command: `edit 1 p/phone123`

Expected:
- No changes applied.
- Error details shown in the status message: `Phone numbers should only contain numbers...`

##### Invalid Test case — Editing results in a **duplicate entry**:

Steps:
- Run `add n/ExistingName a/Existing Address`
- Run `edit 1 n/ExistingName a/Existing Address`

Expected:
- No changes applied.
- Error details shown in the status message: `This foodplace already exists in BiteBuddy.`

##### Edge case — No fields provided to edit:

Command: `edit 1`

Expected:
- No changes applied.
- Error details shown in the status message: `At least one field to edit must be provided.`

### Adding/deleting tags to a foodplace

Adding/deleting tags to a foodplace while all foodplaces are being shown

*Prerequisites:*
* BiteBuddy is running.
* **At least one foodplace** must exist in the list. 
* Use the `list` command first to list all foodplaces and note the current details.


##### Valid Test case – Adding tags to a valid foodplace:

Command: `tag 1 FastFood Cheap`

Expected:
- First foodplace in the list will be assigned tags: `FastFood` and `Cheap`.
- Details of the updated foodplace shown in the status message: `Updated tags for Foodplace: ...`

##### Valid Test case – Deleting a specific tag from a valid foodplace:

Command: `tag 1 /d FastFood`

Expected:
- Tag `FastFood` is deleted from the first foodplace (case-insensitive).
- Details of the updated foodplace shown in the status message: `Updated tags for Foodplace: ...`

##### Valid Test case - Deleting all tags from a valid foodplace:

Command: `tag 1 /d`

Expected:
- All tags are cleared from the first foodplace.
- Details of the updated foodplace shown in the status message: `Updated tags for Foodplace: ...`

##### Valid Test case - Adding duplicate tags with different casing:

Command: `tag 1 cheap CHEAP`

Expected:
- Only one tag `cheap` remains (duplicate ignored case-insensitively).
- Details of the updated foodplace shown in the status message: `Updated tags for Foodplace: ...`

##### Valid Test case - Overriding existing tag casing:

Command: `tag 1 Cheap` followed by `tag 1 cheap`

Expected:
- The existing tag `Cheap` is replaced with `cheap`.
- Details of the updated foodplace shown in the status message: `Updated tags for Foodplace: ...`

##### Invalid Test case - Deleting a non-existent tag:

Command: `tag 1 /d NonExistent`

Expected:
- No tags are deleted.
- Message displayed: `No matching tags found to delete ...`

##### Invalid Test case - Adding tags to a foodplace at an invalid index:

Command: `tag 0 FastFood`

Expected:
- No foodplace will be updated.
- Error details shown in the status message: `The foodplace index provided is invalid`

##### Invalid Test case - Adding an empty tag value:

Command: `tag 1` (with a space after the index)

Expected:
- No foodplace will be updated.
- Error details shown in the status message: `Invalid command format! tag: Adds/deletes tags from...`

##### Invalid Test case - Adding a non-alphanumeric tag:

Command: `tag 1 $$$$`

Expected:
- No foodplace will be updated.
- Error details shown in the status message: `Tags names should be alphanumeric`

### Adding note to a foodplace

Adding note to a foodplace while all foodplaces are being shown

*Prerequisites:*
* BiteBuddy is running.
* **At least one foodplace** must exist in the list.
* Use the `list` command first to list all foodplaces and note the current details.


##### Valid Test case - Assigning a valid note to a valid foodplace:

Command: `note 1 Good customer service!`

Expected:
- First foodplace in the list will be assigned a note: `Good customer service!`
- Details of the updated foodplace shown in the status message: `Added notes to Foodplace: ...`

##### Valid Test case - Unassigning any existing note from a valid foodplace:

Command: `note 1`

Expected:
- First foodplace in the list will have no note: `-- No notes yet --`
- Details of the updated foodplace shown in the status message: `Removed notes from Foodplace: ...`

##### Valid Test case - Assigning same note to a valid foodplace:

Steps:
- Run `note 1 Good customer service!`
- Run `note 1 Good customer service!` again

Expected:
- No foodplace will be updated.
- Info details shown in the status message: `The new note is the same as the current note...`

##### Valid Test case - Unassigning any non-existent note from a valid foodplace:

Steps:
- Run `note 1`
- Run `note 1` again

Expected:
- No foodplace will be updated.
- Info details shown in the status message: `No notes to remove from specified Foodplace...`

##### Invalid Test case - Assigning a valid note to a **foodplace at an invalid index**:

Command: `note 0 Good customer service!`

Expected:
- No foodplace will be updated.
- Error details shown in the status message: `Invalid command format! note: Edits the notes...`

##### Invalid Test case - Assigning a valid note at an **out-of-range index**:

Command: `note 999 Good customer service!`

Expected:
- No foodplace will be updated.
- Error details shown in the status message: `The foodplace index provided is invalid`

##### Invalid Test case - Assigning a **note that exceeds the character limit of 100** to a valid foodplace:

Command: `note 1 aaaaaaa...` (101 `a`s)

Expected:
- No foodplace will be updated.
- Error details shown in the status message: `Note given either exceeds 100 characters OR contained non-ASCII-printable characters...`

##### Invalid Test case - Assigning a **note that contains non-ASCII-printable characters** to a valid foodplace:

Command: `note 1 ¤¤¤¤`

Expected:
- No foodplace will be updated.
- Error details shown in the status message: `Note given either exceeds 100 characters OR contained non-ASCII-printable characters...`


### Rating a foodplace

Rating a foodplace while all foodplaces are being shown

*Prerequisites:*
* BiteBuddy is running.
* **At least one foodplace** must exist in the list.
* Use the `list` command first to list all foodplaces and note the current details.


##### Valid Test case - Assigning a valid rating to a valid foodplace:

Command: `rate 1 5`

Expected:
- First foodplace in the list will be assigned a rating: `< 5/10 >`
- Details of the rated foodplace shown in the status message: `Added rating to Foodplace: ...`

##### Valid Test case - Unassigning any existing rating from a valid foodplace:

Command: `rate 1 0`

Expected: 
- First foodplace in the list will become unrated: `>> No rating yet <<`
- Details of the unrated foodplace shown in the status message: `Removed rating from Foodplace: ...`

##### Invalid Test case - Assigning a valid rating to a **foodplace at an invalid index**:

Command: `rate 0 5`

Expected:
- No foodplace will be rated.
- Error details shown in the status message: `Invalid command format! rate: Edits the rating ...`

##### Invalid Test case - Assigning a **negative rating** to a valid foodplace:

Command: `rate 1 -1`

Expected:
- No foodplace will be rated.
- Error details shown in the status message: `Ratings should only contain numbers, and either: A) be ...`

##### Invalid Test case - Assigning an **out-of-range rating** to a valid foodplace:

Command: `rate 1 11`

Expected:
- No foodplace will be rated.
- Error details shown in the status message: `Ratings should only contain numbers, and either: A) be ...`

##### Invalid Test case - Assigning an **unsigned non-integer rating** to a valid foodplace:

Command: `rate 1 1.1`

Expected:
- No foodplace will be rated.
- Error details shown in the status message: `Invalid command format! rate: Edits the rating ...`


### Wishlisting a foodplace

Wishlisting a foodplace while all foodplaces are being shown

*Prerequisites:*
* BiteBuddy is running.
* **At least one foodplace** must exist in the list.
* Use the `list` command first to list all foodplaces and note the current details.


##### Valid Test case - Adding a valid foodplace to wishlist:

Command: `wishlist 1`

Expected:
- First foodplace in the list will be added to wishlist.
- Details of the updated foodplace shown in the status message: `Add Foodplace to wishlist: ...`

##### Valid Test case - Removing a valid foodplace from wishlist:

Steps:
- Run `wishlist 1` to add the foodplace to wishlist first
- Run `wishlist 1` again to remove the foodplace from wishlist

Expected:
- First foodplace in the list will be removed from wishlist.
- Details of the updated foodplace shown in the status message: `Remove Foodplace from wishlist: ...`

##### Valid Test case - Adding a valid foodplace that is **blacklisted** to wishlist:

Steps:
- Run `blacklist 1` to add the foodplace to blacklist first
- Run `wishlist 1` to remove the foodplace from blacklist and add it to wishlist

Expected:
- First foodplace in the list will be added to wishlist.
- First foodplace in the list will be removed from blacklist.
- Details of the updated foodplace shown in the status message: `Add Foodplace to wishlist: ...`
- Extra details of the updated foodplace shown in the status message: `Additionally removed Foodplace from blacklist`

##### Valid Test case - Displaying wishlist:

Command: `wishlist`

Expected:
- Displays all foodplaces that are added to wishlist.
- Info details shown in the status message: `Listed all foodplaces that are wishlisted`

##### Invalid Test case - Adding / removing a foodplace to wishlist at an **invalid index**:

Command: `wishlist 0`

Expected:
- No foodplace will be updated.
- Error details shown in the status message: `Invalid command format! wishlist: Wishlists the foodplace...`

##### Invalid Test case - Adding / removing a foodplace to wishlist at an **out-of-range index**:

Command: `wishlist 999`

Expected:
- No foodplace will be updated.
- Error details shown in the status message: `The foodplace index provided is invalid`


### Blacklisting a foodplace

Blacklisting a foodplace while all foodplaces are being shown

*Prerequisites:*
* BiteBuddy is running.
* **At least one foodplace** must exist in the list.
* Use the `list` command first to list all foodplaces and note the current details.


##### Valid Test case - Adding a valid foodplace to blacklist:

Command: `blacklist 1`

Expected:
- First foodplace in the list will be added to blacklist.
- Details of the updated foodplace shown in the status message: `Add Foodplace to blacklist: ...`

##### Valid Test case - Removing a valid foodplace from blacklist:

Steps:
- Run `blacklist 1` to add the foodplace to blacklist first
- Run `blacklist 1` again to remove the foodplace from blacklist

Expected:
- First foodplace in the list will be removed from blacklist.
- Details of the updated foodplace shown in the status message: `Remove Foodplace from blacklist: ...`

##### Valid Test case - Adding a valid foodplace that is **wishlisted** to blacklist:

Steps:
- Run `wishlist 1` to add the foodplace to wishlist first
- Run `blacklist 1` to remove the foodplace from wishlist and add it to blacklist

Expected:
- First foodplace in the list will be added to blacklist.
- First foodplace in the list will be removed from wishlist.
- Details of the updated foodplace shown in the status message: `Add Foodplace to blacklist: ...`
- Extra details of the updated foodplace shown in the status message: `Additionally removed Foodplace from wishlist`

##### Valid Test case - Displaying blacklist:

Command: `blacklist`

Expected:
- Displays all foodplaces that are added to blacklist.
- Info details shown in the status message: `Listed all foodplaces that are blacklisted`

##### Invalid Test case - Adding / removing a foodplace to blacklist at an **invalid index**:

Command: `blacklist 0`

Expected:
- No foodplace will be updated.
- Error details shown in the status message: `Invalid command format! blacklist: Blacklists the foodplace...`

##### Invalid Test case - Adding / removing a foodplace to blacklist at an **out-of-range index**:

Command: `blacklist 999`

Expected:
- No foodplace will be updated.
- Error details shown in the status message: `The foodplace index provided is invalid`


### Pinning a foodplace

Pinning a foodplace while all foodplaces are being shown

*Prerequisites:*
* BiteBuddy is running.
* **At least one foodplace** must exist in the list.
* Use the `list` command first to list all foodplaces and note the current details.
* Foodplace being pinned is currently not pinned. 
* There are less than 5 pinned foodplaces.


##### Valid Test case - Pinning an unpinned foodplace when there are less than 5 pins:

Command: `pin 1`

Expected:
- The first foodplace will be pinned.
- Details of the pinned foodplace shown in the status message: `Pinned Foodplace: ...`

##### Invalid Test case - Pinning a **foodplace at an invalid index**:

Command: `pin 0`

Expected:
- No foodplace will be pinned.
- Error details shown in the status message: `Invalid command format! pin: Pins the foodplace...`

##### Invalid Test case - Pinning a previously pinned foodplace:

Steps:
- Pin the first foodplace using `pin 1`
- Attempt to pin the same foodplace again using `pin 1`

Expected:
- The first foodplace will remain pinned.
- Error details shown in the status message: `This foodplace is already pinned`

##### Invalid Test case - Pinning an unpinned foodplace when there are 5 pins already:

Steps:
- Pin 5 foodplaces using `pin x` 5 times.
- Attempt to pin another foodplace using `pin x`

Expected:
- The foodplace will not be pinned.
- Error details shown in the status message: `This foodplace is not pinned. The maximum of 5 pins has been reached`

### Unpinning a foodplace

Unpinning a foodplace while all foodplaces are being shown

*Prerequisites:*
* BiteBuddy is running.
* **At least one foodplace** must exist in the list.
* Use the `list` command first to list all foodplaces and note the current details.
* Foodplace being unpinned is currently pinned.


##### Valid Test case - Unpinning an pinned foodplace:

Command: `unpin 1`

Expected:
- The first foodplace will be unpinned.
- Details of the unpinned foodplace shown in the status message: `Unpinned Foodplace: ...`

##### Invalid Test case - Unpinning a **foodplace at an invalid index**:

Command: `unpin 0`

Expected:
- No foodplace will be pinned.
- Error details shown in the status message: `Invalid command format! unpin: Unpins the foodplace...`

##### Invalid Test case - Unpinning a foodplace that is not pinned:

Command: `unpin 2`

Expected:
- The second foodplace remains the same.
- Error details shown in the status message: `This foodplace was not pinned`

### Finding a foodplace

*Prerequisites:*
* BiteBuddy is running.
* **At least one foodplace** must exist in the list.
* Use the `list` command first to list all foodplaces and note the current details.

##### Valid Test case - Finding a foodplace by keywords:

Command: `find western`

Expected:
- The list updates to show foodplaces with matching fields.
- Info details shown in the status message: `2 foodplaces listed!`

##### Valid Test case - Finding a foodplace by specified fields:

Command: `find t/hawker r/8`

Expected:
- The list updates to show foodplaces with matching fields.
- Info details shown in the status message: `3 foodplaces listed!`

### Comparing two foodplaces

Comparing two foodplaces from the list shown

*Prerequisites:*
* BiteBuddy is running.
* **At least two foodplaces** must exist in the list.
* Use the `list` command first to list all foodplaces and note the current details.


##### Valid Test case - Comparing two different foodplaces:

Command: `compare 1 2`

Expected:
- The first and second foodplace will be compared.
- Details of the comparison shown in the status message.

##### Invalid Test case - Comparing the same foodplace:

Command: `compare 1 1`

Expected:
- No comparison will be made.
- Error details shown in the status message: `The two foodplace indexes cannot be the same`

##### Invalid Test case - Comparing a **foodplace at an invalid index**:

Command: `compare 1 999`

Expected:
- No comparison will be made.
- Error details shown in the status message: `The second foodplace index provided is invalid`

### Getting Help

*Prerequisites:*
* BiteBuddy is running.


##### Valid Test case - Using the help button:

Steps:
- Click the help button on the top left side of the window.

Expected:
- Help window should open with a link to the user guide.

##### Valid Test case - Using the help command to get user guide:

Command: `help`

Expected:
- Help window should open with a link to the user guide.

##### Valid Test case - Using the help command to get specific command usage:

Command: `help list`

Expected:
- Command usage of `list` is shown in status message: `list: Lists all foodplaces in BiteBuddy...`

##### Invalid Test case - Using an **invalid command word** as a parameter for the help command:

Command: `help unknown`

Expected:
- Error details shown in the status message: `Unknown command`

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
