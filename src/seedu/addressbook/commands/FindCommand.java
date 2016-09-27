package seedu.addressbook.commands;

import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.tag.Tag;
import seedu.addressbook.data.tag.UniqueTagList;
import seedu.addressbook.data.tag.UniqueTagList.DuplicateTagException;

import java.util.*;

/**
 * Finds and lists all persons in address book whose name or tags contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Finds all persons whose names or tags contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n\t"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n\t"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final Set<String> keywords;

    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Returns copy of keywords in this command.
     */
    public Set<String> getKeywords() {
        return new HashSet<>(keywords);
    }

    @Override
    public CommandResult execute() {
        final Set<ReadOnlyPerson> personsFoundSet = new HashSet<>();
        personsFoundSet.addAll(getPersonsWithNameContainingAnyKeyword(keywords));
        personsFoundSet.addAll(getPersonsWithTagsContainingAnyKeyword(keywords));
        final List<ReadOnlyPerson> personsFound = new ArrayList<>(personsFoundSet);
        return new CommandResult(getMessageForPersonListShownSummary(personsFound), personsFound);
    }

    /**
     * Retrieve all persons in the address book whose names contain some of the specified keywords.
     *
     * @param keywords for searching
     * @return list of persons found
     */
    private List<ReadOnlyPerson> getPersonsWithNameContainingAnyKeyword(Set<String> keywords) {
        final List<ReadOnlyPerson> matchedPersons = new ArrayList<>();
        for (ReadOnlyPerson person : addressBook.getAllPersons()) {
            final Set<String> wordsInName = new HashSet<>(person.getName().getWordsInName());
            if (!Collections.disjoint(wordsInName, keywords)) {
                matchedPersons.add(person);
            }
        }
        return matchedPersons;
    }

    /**
     * Retrieve all persons in the address book whose tags matches any of the specified keywords.
     *
     * @param keywords for searching
     * @return list of persons found
     */
    private List<ReadOnlyPerson> getPersonsWithTagsContainingAnyKeyword(Set<String> keywords) {
        final List<ReadOnlyPerson> matchedPersons = new ArrayList<>();
        UniqueTagList keywordTagList = new UniqueTagList();
        for (String keyword:keywords) {
            try {
                keywordTagList.add(new Tag(keyword));
            } catch (DuplicateTagException e) {
                e.printStackTrace();
            } catch (IllegalValueException e) {
                e.printStackTrace();
            }
        }
        for (ReadOnlyPerson person : addressBook.getAllPersons()) {
            UniqueTagList personTagList = person.getTags();
            if (personTagList.containsAny(keywordTagList)) {
                matchedPersons.add(person);
            }
        }
        return matchedPersons;
    }
}
