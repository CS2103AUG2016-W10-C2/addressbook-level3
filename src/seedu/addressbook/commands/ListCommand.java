package seedu.addressbook.commands;

import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.UniquePersonList;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {
    boolean shouldLimit = false;
    String leftBound = "";
    String rightBound = "";

    public ListCommand() {

    }

    public ListCommand(String leftBound, String rightBound) {
        this.shouldLimit = true;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
    }

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" 
            + "Displays all persons in the address book as a list with index numbers.\n\t"
            + "Example: " + COMMAND_WORD + "\n"
            + "Alternatively, limit the range of persons to be listed by the first letter of their name: \n"
            + "Example: " + COMMAND_WORD + " A D\n";


    @Override
    public CommandResult execute() {
        List<ReadOnlyPerson> allPersons = addressBook.getAllPersons().immutableListView();
        if (!shouldLimit) {
            return new CommandResult(getMessageForPersonListShownSummary(allPersons), allPersons);
        }

        List<ReadOnlyPerson> filteredPersons = new ArrayList<ReadOnlyPerson>();
        for (ReadOnlyPerson p : allPersons) {
            if (compareFirstLetterOfNameWith(p.getName().toString(), leftBound) >= 0
                    && compareFirstLetterOfNameWith(p.getName().toString(), rightBound) <= 0) {
                filteredPersons.add(p);
            }
        }
        return new CommandResult(getMessageForPersonListShownSummary(filteredPersons), filteredPersons);
    }

    private int compareFirstLetterOfNameWith(String name, String bound) {
        String firstLetter = name.substring(0, 1);
        return firstLetter.compareToIgnoreCase(bound);
    }
}
