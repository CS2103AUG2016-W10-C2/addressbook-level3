package seedu.addressbook.common;

import java.io.File;
import java.util.Scanner;

/**
 * Container for user visible messages.
 */
public class Messages {
    private static final String MESSAGE_FILE = "message.txt";
    
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSON_NOT_IN_ADDRESSBOOK = "Person could not be found in address book";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_PROGRAM_LAUNCH_ARGS_USAGE = "Launch command format: " +
            "java seedu.addressbook.Main [STORAGE_FILE_PATH]";
    public static final String MESSAGE_WELCOME_DEFAULT = "Welcome to your Address Book!";
    
    public static final String MESSAGE_USING_STORAGE_FILE = "Using storage file : %1$s";
   
    /**
     * Return the welcome message
     * If a MESSAGE_FILE is present, load the content of that file
     * Else return MESSAGE_WELCOME_DEFAULT
     */
    public static String getWelcomeMessage() {
        try {
            File inputFile = new File(MESSAGE_FILE);
            Scanner scanner = new Scanner(inputFile);
            StringBuilder welcomeMessage = new StringBuilder();
            
            while (scanner.hasNextLine()) {
                welcomeMessage.append(scanner.nextLine());
                welcomeMessage.append("\n");
            }
            
            return welcomeMessage.toString();
        } catch (Exception e) { 
            // Return the default message instead
            System.err.println(e);
            return MESSAGE_WELCOME_DEFAULT;
        } 
    }
}
