package application;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Experimental test class of the search process using the FlatFile, JournalEntry, and Journal classes.
 * 
 */
//public class ExperimentalJournalSearch {
	
//	public static void main(String[] args) throws Exception {
		
		//DEBUG: the scanner input stuff is supposed to represent the text boxes in the program
		
//		SearchEntry searcher = new SearchEntry();
//		ArrayList<String> words;
//		words = searcher.searchTitles("q", searcher.getEntryList());
//		System.out.println(words);
		
//		FlatFile a = new FlatFile();
//		ArrayList<String> s = new ArrayList<String>();
//		a.renameFile("ds", "pp");
		//System.out.println(a.numList("!!!listOfAllJournalEntries"));
		//a.readJournalArray("qwewq");
		
		//System.out.println(a.readJournalArray("qwewq"));
		//s = a.getContent();
		//System.out.println(a.toString());
		//a.toString();
//		if (a.getContent().isEmpty()) {
//			System.out.println(true);
//		}
		//System.out.println(searcher.getEntryList().size());
//		Scanner input = new Scanner(System.in);//creating Scanner object
//		ArrayList<JournalEntry> entryList = searcher.createEntryList();//creating arraylist of entries
//		
//		System.out.print("Enter a keyword: ");
//		String keyword = input.nextLine();
//		System.out.println();
//		input.close();
//		
//		ArrayList<JournalEntry> resultTitles = searcher.searchTitles(keyword, entryList);
//		ArrayList<JournalEntry> resultContents = searcher.searchContents(keyword, entryList);
//		
//		System.out.print("List of entries with matching titles: ");
//		searcher.printTitles(resultTitles);
//		System.out.println();
//		
//		System.out.print("List of entries with matching contents: ");
//		searcher.printTitles(resultContents);
//		System.out.println();
//	}

//}

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
	 * Getting the list of journals 
	 * @return entries An arraylist of the entries 
	 */
	public ArrayList<String> getEntryList() {
		ArrayList<String> entries = new ArrayList<String>();
		try {
			Scanner readLines = new Scanner(new File(LIST_FILE + ".txt"));//Scanner to read file lines
			while (readLines.hasNext()) {
				entries.add(readLines.nextLine());
			}
			readLines.close();
		} catch (IOException e) {
			e.printStackTrace();//if the file does not exist
		}
		return entries;
		
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
	public ArrayList<String> searchTitles(String keyword, ArrayList<String> entryList) throws Exception{
		ArrayList<String> matchingTitles = new ArrayList<String>();
		for (int i = 0; i < entryList.size(); i++) {
			if (entryList.get(i).contains(keyword) == true) {
				matchingTitles.add(entryList.get(i));//adding journal entry to matching titles
				//System.out.println(matchingTitles);
			}
			else {
				//System.out.println("Not found");
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
	 * Method that prints the titles of the journal entries in a list with the date and time.
	 * If no titles are provided, then "<no title provided>" will be printed with the date and time.
	 * @param entryList Arraylist of JournalEntry objects.
	 */
	public void printTitles(ArrayList<JournalEntry> entryList) {
		System.out.print("[");
		if (entryList.size() > 0) {
			if (entryList.get(0).getTitle().equals("")) {//no title was provided for first entry
				System.out.print("<no title provided>" + "(" + entryList.get(0).getDate() + ", " + entryList.get(0).getTime() + ")");//printing <no title given> with date and time
			} else {//if title was provided for first entry
				System.out.print(entryList.get(0).getTitle() + "(" + entryList.get(0).getDate() + ", " + entryList.get(0).getTime() + ")");//printing first title in list with date and time
			}
			for (int i = 1; i < entryList.size(); i++) {
				System.out.print(", ");
				if (entryList.get(i).getTitle().equals("")) {//no title was provided
					System.out.print("<no title provided>" + "(" + entryList.get(i).getDate() + ", " + entryList.get(i).getTime() + ")");//printing <no title given> in index with date and time
				} else {//title is provided
					System.out.print(entryList.get(i).getTitle() + "(" + entryList.get(i).getDate() + ", " + entryList.get(i).getTime() + ")");//printing each title in index with date and time
				}
				
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


