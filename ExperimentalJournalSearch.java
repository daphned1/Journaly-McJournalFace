package application;

import java.io.*;
import java.util.*;

/**
 * Experimental test class of the search process using the FlatFile, JournalEntry, and Journal classes.
 * 
 */
public class ExperimentalJournalSearch {
	
	public static void main(String[] args) throws Exception {
		
		//DEBUG: the scanner input stuff is supposed to represent the text boxes in the program
		
		SearchEntry searcher = new SearchEntry();
		Scanner input = new Scanner(System.in);//creating Scanner object
		ArrayList<JournalEntry> entryList = searcher.createEntryList();//creating arraylist of entries
		
		System.out.print("Enter a keyword: ");
		String keyword = input.nextLine();
		System.out.println();
		input.close();
		
		ArrayList<JournalEntry> resultTitles = searcher.searchTitles(keyword, entryList);
		ArrayList<JournalEntry> resultContents = searcher.searchContents(keyword, entryList);
		
		System.out.print("List of entries with matching titles: ");
		searcher.printTitles(resultTitles);
		System.out.println();
		
		System.out.print("List of entries with matching contents: ");
		searcher.printTitles(resultContents);
		System.out.println();
	}

}

/**
 * Experimental class that handles searching for an entry given a list of journal titles.
 * This class relies on the FlatFile class to manage files.
 * (implemented by Richie)
 */
class SearchEntry {
	private final String LIST_FILE = "!!!listOfAllJournalEntries";
	FlatFile file;
	
	/**
	 * Constructor without arguments.
	 */
	public SearchEntry() {
		file = new FlatFile();
	}
	
	/**
	 * Method that creates an arraylist of JournalEntry objects using a list of journal entry file names.
	 * @return entryList ArrayList of JournalEntry objects.
	 */
	public ArrayList<JournalEntry> createEntryList() throws Exception {
		ArrayList<JournalEntry> entryList = new ArrayList<JournalEntry>();
		boolean listExists = file.searchFile(LIST_FILE);//initializing list file search
		boolean entryExists;//initializing entry check
		String readDate;
		String readTime;
		String readTitle;
		String readContents;//initializing variables for JournalEntry objects
		if (listExists == true) {
			File listFile = new File(LIST_FILE + ".txt");
			Scanner scrollEntries = new Scanner(listFile);
			String entryFile;
			while (scrollEntries.hasNextLine()) {
				entryFile = scrollEntries.nextLine();//setting entryFile to file name in list
				entryExists = file.searchFile(entryFile);
				if (entryExists == true) {
					Scanner readEntry = new Scanner(new File(entryFile + ".txt"));//reading journal entry in list
					readDate = readEntry.nextLine();
					readTime = readEntry.nextLine();
					readTitle = readEntry.nextLine();
					readContents = readEntry.nextLine();
					readEntry.close();
					entryList.add(new JournalEntry(readDate, readTime, readTitle, readContents, entryFile));//adding JournalEntry object to list
				} else {
					System.err.println(entryFile + ".txt does not exist");
				}
			}
			scrollEntries.close();
		} else {
			System.err.println("Journal entry list file not found; unable to search for entry");
		}
		
		return entryList;
	}
	
	/**
	 * Method that searches an arraylist of JournalEntry objects using a keyword and returns an arraylist of JournalEntry objects with matching titles.
	 * @param keyword String input for the search keyword.
	 * @param entryList Arraylist of JournalEntry objects.
	 * @return matchingTitles ArrayList of JournalEntry objects containing the search keyword in their titles.
	 */
	public ArrayList<JournalEntry> searchTitles(String keyword, ArrayList<JournalEntry> entryList) throws Exception{
		ArrayList<JournalEntry> matchingTitles = new ArrayList<JournalEntry>();
		for (int i = 0; i < entryList.size(); i++) {
			if (entryList.get(i).getTitle().contains(keyword) == true) {
				matchingTitles.add(entryList.get(i));//adding journal entry to matching titles
			}
		}

		return matchingTitles;
	}
	/**
	 * Method that searches an arraylist of JournalEntry objects using a keyword and returns an arraylist of JournalEntry objects with matching contents.
	 * @param keyword String input for the search keyword.
	 * @param entryList Arraylist of JournalEntry objects.
	 * @return matchingContents ArrayList of JournalEntry objects containing the search keyword in their contents.
	 */
	public ArrayList<JournalEntry> searchContents(String keyword, ArrayList<JournalEntry> entryList) throws Exception{
		ArrayList<JournalEntry> matchingContents = new ArrayList<JournalEntry>();
		for (int i = 0; i < entryList.size(); i++) {
			if (entryList.get(i).getContents().contains(keyword) == true) {
				matchingContents.add(entryList.get(i));//adding journal entry to matching contents
			}
		}

		return matchingContents;
	}
	
	/**
	 * Method that prints the titles of the journal entries in a list.
	 * @param entryList Arraylist of JournalEntry objects.
	 */
	public void printTitles(ArrayList<JournalEntry> entryList) {
		System.out.print("[");
		if (entryList.size() > 0) {
			System.out.print(entryList.get(0).getTitle());//printing first title in list
			for (int i = 1; i < entryList.size(); i++) {
				System.out.print(", ");
				System.out.print(entryList.get(i).getTitle());//printing each title in index
			}
		}
		System.out.print("]");
	}
	/**
	 * Method that prints the contents of the journal entries in a list.
	 * @param entryList Arraylist of JournalEntry objects.
	 */
	public void printContents(ArrayList<JournalEntry> entryList) {
		System.out.print("[");
		if (entryList.size() > 0) {
			System.out.print(entryList.get(0).getContents());//printing first title in list
			for (int i = 1; i < entryList.size(); i++) {
				System.out.print(", ");
				System.out.print(entryList.get(i).getContents());//printing each title in index
			}
		}
		System.out.print("]");
	}
}
//to figure out for the above two methods: find a way to merge them and set the parameter to a type of JournalEntry instance variable (title, date, time, contents)


/**
 * Class that handles finding, reading, and writing the password and security question/answer from an external file.
 * The file name is JMJPassword.txt and contains the user password and security password.
 * (implemented by Hayk/Richie)
 */
class FlatFile {
	// this is a test of my git, sup guys :D
	/**
	 * Method that searches for a flat file.
	 * @param name String input used for the file name
	 * @return isFileFound Boolean to represent if the search is successful
	 */
	public boolean searchFile(String name) {
		File f = new File(name + ".txt");
		boolean isFileFound = f.isFile();
		return isFileFound;
	}
	
	/**
	 * Method that creates an empty file through the writeFile method.
	 * @param name String input used for the file name
	 */
	public void createEmptyFile(String name)  {
		writeFile(name, "");
	}
	
	/**
	 * Method that creates a formatted array from the file contents for an account (password, security question, answer).
	 * @param name String input used for the file name
	 * @return accountDetails String array containing file contents for account
	 */
	public String[] readFileArray(String name) {
		String[] accountDetails = new String[3];//initializing array
		try {
			Scanner readLines = new Scanner(new File(name + ".txt"));//Scanner to read file lines
			accountDetails[0] = readLines.nextLine();//password
			accountDetails[1] = readLines.nextLine();//security question
			accountDetails[2] = readLines.nextLine();//answer
			readLines.close();
		} catch (IOException e) {
			e.printStackTrace();//if the file does not exist
		}
		return accountDetails;
	}
	
	/**
	 * Method that writes content into an external flat file.
	 * @param name String input used for the file name 
	 * @param contents String input for contents to be written into the file
	 */
	public void writeFile(String name, String contents) {
		try {
			
			/* commented out printFile usage
			 * String current;
			 * current = printFile(name);
			*/
			
			FileWriter check = new FileWriter(name + ".txt", false);//override contents of file
			BufferedWriter writer = new BufferedWriter(check);
			/* commented out for the writing to override contents of file
			if(current != null && current.equals("") == false) {
				writer.write(current + "\n");
			}
			*/
			writer.write(contents);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Method that adds a line to a flatfile instead of replacing it
	 * @param name String input used for the file name 
	 * @param contents String input for contents to be written into the file
	 */
	public void addLineToFile(String name, String contents) {
		try {
			
			//commented out printFile usage
		 	String current;
			current = printFile(name);
			
			
			FileWriter check = new FileWriter(name + ".txt", false);//override contents of file
			BufferedWriter writer = new BufferedWriter(check);
			//commented out for the writing to override contents of file
			if(current != null && current.equals("") == false) {
				writer.write(current + "\n");
			}
			writer.write(contents);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void removeText(String name, String text) throws IOException {
		String check = printFile(name);
		String output = check.substring(0, check.indexOf(text)) + check.substring(check.indexOf(text)+text.length());;
		writeFile(name, output);
	}
	/**
	 * (currently unused) Method that prints the contents of a file into the program.
	 * @param name String input used to find the file to print
	 * @return output The content read by the program
	 * @throws IOException
	 */
	public String printFile(String name) throws IOException {
		String output = "";
		String check;
		try {
			FileReader fReader = new FileReader(name + ".txt");
			BufferedReader reader = new BufferedReader(fReader);
			while((check = reader.readLine()) != null) {				
				if(output.equals("")) {
					output = output + check;
				}
				else {
					output = output + "\n" + check;
				}
			}
			reader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return output;
		
	}
	
}

/**
 * Class that manages a journal entry with a title, contents, associated file name for export, and the date.
 * The file name is the journal entry title but without whitespaces and other characters. (Journal Entry #1 would have the file name JournalEntry1)
 * (implemented by Richie)
 */
class JournalEntry {
	private String date;
	private String time;
	private String title;
	private String contents;
	private String fileName;//instance variables for journal entry
	
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
	 * Getter for journal entry date.
	 * @return date The date of the journal entry.
	 */
	public String getDate() {
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
	 * Getter for journal entry title.
	 * @return title The title of the journal entry.
	 */
	public String getTitle() {
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
	 * Getter for journal entry file name.
	 * @return fileName The file name of the journal entry.
	 */
	public String getFileName() {
		return this.fileName;
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
	 */
	public void createFileName() {
		final String DELIMITERS = "[ @<>{}#$+%`~&*/.!?\\\\-]";//delimiters for file name
		String fileName = "";//initial file name 
		String oldTitle = getTitle();//initializing title conversion
		String[] titleArray = oldTitle.split(DELIMITERS);//creating string array based on delimiters
		for (int i = 0; i < titleArray.length; i++) {
			fileName = fileName + titleArray[i];//appending string array contents to file name
		}
		setFileName(fileName);//setting file name
	}
	
}