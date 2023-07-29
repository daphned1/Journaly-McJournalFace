package application;

import java.io.*;
import java.text.DateFormat;
import java.util.*;
import java.text.*;

/**
 * Main test class of the journal process using the FlatFile, JournalEntry, and Journal classes.
 * 
 *///public class JournalBackEnd {
	
//	public static void main(String[] args) throws Exception {
//		
//		//DEBUG: the scanner input stuff is supposed to represent the text boxes in the program
//		
//		Journal journalManager = new Journal();//initializing FlatFile class
//		Scanner input = new Scanner(System.in);//creating Scanner object
//		
//		System.out.print("Provide a journal entry title: ");
//		String title = input.nextLine();
//		System.out.println();
//		
//		System.out.print("Create a journal entry (line only for test): ");
//		String contents = input.nextLine();
//		System.out.println();
//		
//		System.out.print("Save the journal entry? (y/n): ");
//		String choice = input.nextLine();
//		System.out.println();
//		
//		switch (choice) {
//		case "y"://user saves journal
//			JournalEntry testEntry = new JournalEntry(new Date(), title, contents, "tempFileName");//creating entry with title and contents
//			journalManager.exportEntry(testEntry);//exporting entry to file
//			System.out.println("Journal entry successfully saved to " + testEntry.getFileName() + ".txt");
//			break;
//		case "n"://user does not save journal
//			System.out.println("Journal entry discarded");
//			break;
//		default://none of the above options
//			System.err.println("Invalid choice");
//			break;
//		}
//		
//		input.close();
//	}
//
//}


/**
 * Class that creates a .txt file with a journal entry inside
 * This class uses the FlatFile to read and write data from .txt files, it does not actually
 * This class also uses the JournalEntry class to export the entry to a file and add it to a list of entries. 
 * (implemented by Hayk/Richie)
 */
class Journal {
	FlatFile file;
	final String JOURNAL_ENTRY_LIST = "!!!listOfAllJournalEntries";//initializing variables
	/**
	 * creates the journal and creates a new file called listOfAllJournalEntries if it does not exist already
	 */
	public Journal() {
		file = new FlatFile();
		verifyListFile();//checking if list file exists
	}
	
	/**
	 * Method that verifies if the list file exists and creates one if otherwise.
	 */
	public void verifyListFile() {
		boolean listExists = file.searchFile(JOURNAL_ENTRY_LIST);
		if (listExists == false) {
			file.createEmptyFile(JOURNAL_ENTRY_LIST);//create list of journal entries if file does not exist
		}
	}
	
	/**
	 * creates a new journal and adds its name to the file with the name of all journals
	 * @param entry JournalEntry Object to get the title and file name of the entry.
	 */
	public void createJournal(JournalEntry entry) {
		entry.createFileName();
		file.addLineToFile(JOURNAL_ENTRY_LIST, entry.getFileName());
		file.createEmptyFile(entry.getFileName());
	}
	/**
	 * reads the journal and outputs its entry as a string
	 * @param nameOfJournal The name of the journal 
	 * @return output The name of the journal
	 * @throws IOException
	 */
	public String readJournal(String nameOfJournal) throws IOException {
		String output = "";
		output = file.printFile(nameOfJournal);
		return output;
	}
	/**
	 * replaces the journal
	 * @param nameOfJournal
	 * @param entry
	 */
	public void replaceJournalEntry(String nameOfJournal, String entry) {
		file.writeFile(nameOfJournal, entry);
	}
	/**
	 * wipes the journal
	 * @param nameOfJournal
	 */
	public void wipeEntry(String nameOfJournal) {
		replaceJournalEntry(nameOfJournal,"");
	}
	/**
	 * adds a line to the journal without completely overriding its contents
	 * @param nameOfJournal
	 * @param addition
	 */
	public void addToEntry(String nameOfJournal, String addition) {
		file.addLineToFile(nameOfJournal, addition);
	}
	
	/**
	 * Method that exports a journal entry to a file and adding it to a list of entries.
	 * @param entry JournalEntry Object for a journal entry
	 */
	public void exportEntry(JournalEntry entry) {
		entry.createFileName();//creating file name for journal entry
		verifyListFile();//checking if list file exists
		boolean entryFileExists = file.searchFile(entry.getFileName());
		if (entryFileExists == false) {
			createJournal(entry);//creating new journal entry into list if entry file does not exist
		}
		wipeEntry(entry.getFileName());
		addToEntry(entry.getFileName(), entry.getDate());
		addToEntry(entry.getFileName(), entry.getTime());
		addToEntry(entry.getFileName(), entry.getTitle());
		addToEntry(entry.getFileName(), entry.getContents());
		
	}
	
	/**
	 * Method that exports a journal entry to a file and adding it to a list of entries.
	 * @param entry JournalEntry Object for a journal entry
	 * @throws FileNotFoundException 
	 */
	public void modifyEntry(JournalEntry entry) throws FileNotFoundException {
		
		entry.createFileName();//creating file name for journal entry
//		verifyListFile();//checking if list file exists
//		boolean entryFileExists = file.searchFile(entry.getFileName());
//		if (entryFileExists) {
//			createJournal(entry);//creating new journal entry into list if entry file does not exist
//		}
		
		file.renameFile(entry.getOldFileName(), entry.getFileName());
		file.modifyFile(JOURNAL_ENTRY_LIST, entry.getFileName(), entry.getOldFileName());
		wipeEntry(entry.getFileName());
		addToEntry(entry.getFileName(), entry.getDate());
		addToEntry(entry.getFileName(), entry.getTime());
		addToEntry(entry.getFileName(), entry.getTitle());
		addToEntry(entry.getFileName(), entry.getContents());
		
	}
	
	
	//TODO: add a function that changes the name of the journal, add a function that deletes the journal
}

/**
 * Class that manages a journal entry with a title, contents, associated file name for export, and the date.
 * The file name is the journal entry title but without whitespaces and other characters. (Journal Entry #1 would have the file name JournalEntry1)
 * (implemented by Richie)
 */
class JournalEntry {
	FlatFile file = new FlatFile();
	private String date;
	private String time;
	private String title;
	private String contents;
	private String oldFileName;
	private String fileName;//instance variables for journal entry
	final String JOURNAL_ENTRY_LIST = "!!!listOfAllJournalEntries";//initializing variables
	
	
	/**
	 * Default constructor without arguments.
	 */
	public JournalEntry() {
		this.date = "";
		this.time = "";
		this.title = "title";
		this.contents = "contents";
		this.fileName = "fileName";
	}
	
	/**
	 * Constructor with arguments.
	 * @param inputDate String used for the journal entry date.
	 * @param inputTime String used for the journal entry time 
	 * @param inputTitle String used for the title of the journal entry.
	 * @param inputContents String used for the journal entry contents.
	 * @param inputFileName String used for the journal entry file name for export.
	 */
	public JournalEntry(String inputDate, String inputTime, String inputTitle, String inputContents, String inputFileName) {
		this.date = inputDate;
		this.time = inputTime;
		this.title = inputTitle;
		this.contents = inputContents;
		this.fileName = inputFileName;
	}
	
	/**
	 * Constructor with arguments.
	 * @param inputDate String used for the journal entry date.
	 * @param inputTime String used for the journal entry time 
	 * @param inputTitle String used for the title of the journal entry.
	 * @param inputContents String used for the journal entry contents.
	 * @param inputFileName String used for the journal entry file name for export.
	 */
	public JournalEntry(String inputDate, String inputTime, String inputTitle, String inputFileName) {
		this.date = inputDate;
		this.time = inputTime;
		this.title = inputTitle;
		this.fileName = inputFileName;
	}
	
	/**
	 * Getter for journal entry date.
	 * @return date The date of the journal entry.
	 */
	public String getDate() {
		return this.date;
	}
	
	/**
	 * Getter for journal entry date.
	 * @return date The date of the journal entry.
	 */
	public String getDate(String name) {
		FlatFile fileManager = new FlatFile();
		String[] readFile = fileManager.readJournalArray(name);
		this.date = readFile[0];
		return this.date;
	}
	
	/**
	 * Getter for journal entry date.
	 * @return time The time of the journal entry.
	 */
	public String getTime() {
		return this.time;
	}
	
	/**
	 * Getter for journal entry date.
	 * @return time The time of the journal entry.
	 */
	public String getTime(String name) {
		FlatFile fileManager = new FlatFile();
		String[] readFile = fileManager.readJournalArray(name);
		this.time = readFile[1];
		return this.time;
	}
	
	/**
	 * Getter for journal entry title.
	 * @return title The title of the journal entry.
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Getter for journal entry title.
	 * @return title The title of the journal entry.
	 */
	public String getTitle(String name) {
		FlatFile fileManager = new FlatFile();
		String[] readFile = fileManager.readJournalArray(name);
		if (readFile[3] == null) {
			this.title ="";
		}
		else {
			this.title = readFile[2];
		}
		return this.title;
	}
	
	/**
	 * Getter for journal entry contents.
	 * @return contents The contents of the journal entry.
	 */
	public String getContents() {
		
		return this.contents;
	}
	
	/**
	 * Getter for journal entry contents.
	 * @return contents The contents of the journal entry.
	 */
	public String getContents(String name) {
		FlatFile fileManager = new FlatFile();
		String[] readFile = fileManager.readJournalArray(name);
		if (readFile[3] == null) {
			this.contents = readFile[2];
		}
		else {
			this.contents = readFile[3];
			if (!fileManager.getContent().isEmpty()) {
				contents += fileManager.toString();
			}
		}
		return this.contents;
	}
	
	
	
	/**
	 * Getter for journal entry file name.
	 * @return fileName The file name of the journal entry.
	 */
	public String getFileName() {
		return this.fileName;
	}
	
	/**
	 * Getter for journal entry file name.
	 * @return fileName The file name of the journal entry.
	 */
	public String setOldFileName(String name) {
		oldFileName = name;
		return oldFileName;
	}
	
	/**
	 * Getter for journal entry file name.
	 * @return fileName The file name of the journal entry.
	 */
	public String getOldFileName() {
		return this.oldFileName;
	}
	
	
	/**
	 * Setter for journal entry date.
	 * @param date String for new journal entry date.
	 */
	public void setDate(String date) {
		this.date = date;
	}
	
	/**
	 * Setter for journal entry date.
	 * @param time String for new journal entry time.
	 */
	public void setTime(String time) {
		this.time = time;
	}
	
	/**
	 * Setter for journal entry title.
	 * @param title String input for new journal entry title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Setter for journal entry contents.
	 * @param contents String input for new journal entry contents.
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	/**
	 * Setter for journal entry file name.
	 * @param fileName String input for the new journal entry file name.
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * Method that creates a file name for a journal entry based on the title and sets the file name for the journal entry.
	 * This method also creates a file name based on the first 3 words of the entry contents if no title is provided.
	 */
	public void createFileName() {
		final String DELIMITERS = "[ @<>{}#$+%`~&*/.!?\\\\-]";//delimiters for file name
		String fileName = "";//initial file name 
		if (getTitle().equals("")) {//if no title is provided
			String contents = getContents();//initializing contents conversion
			String[] contentsArray = contents.split(DELIMITERS);//creating string array based on delimiters
			fileName = fileName + contentsArray[0];//appending first word of contents to file name
			setFileName(fileName);//setting file name
		} else { //if title is provided
			String oldTitle = getTitle();//initializing title conversion
			String[] titleArray = oldTitle.split(DELIMITERS);//creating string array based on delimiters
			for (int i = 0; i < titleArray.length; i++) {
				fileName = fileName + titleArray[i];//appending string array contents to file name
			}
			setFileName(fileName);//setting file name
		}
		
	}
	
	/**
	 * Deletes the journal entry
	 * @param theFileName The filename to delete
	 * @throws IOException
	 */
	public void deleteEntry(String theFileName) throws IOException {
		file.deleteFile(theFileName);
		file.removeFileFromList(JOURNAL_ENTRY_LIST, theFileName);
	}
	
}