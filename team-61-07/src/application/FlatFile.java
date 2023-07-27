package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that handles finding, reading, and writing the password and security question/answer from an external file.
 * The file name is JMJPassword.txt and contains the user password and security password.
 * (implemented by Hayk/Richie)
 */
class FlatFile {
	ArrayList<String> content = new ArrayList<String>();
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
	 * Method that creates a formatted array from the file contents for a journal
	 * @param name String input used for the file name
	 * @return journalDetails String array containing file contents for journal
	 */
	public String[] readJournalArray(String name) {
		String[] journalDetails = new String[4];//initializing array
		try {
			int i = 0;
			Scanner readLines = new Scanner(new File(name + ".txt"));//Scanner to read file lines
			while (readLines.hasNext()) {
				if (i >= 4) {
					readContent(readLines.nextLine());
					i++;
				}
				else {
					journalDetails[i] = readLines.nextLine();
					i++;
				}
			}
			readLines.close();
		} catch (IOException e) {
			e.printStackTrace();//if the file does not exist
		}
		return journalDetails;
	}
	
	public void readContent(String words) {
		content.add(words);
		//return content;
	}
	
	public ArrayList<String> getContent() {
		return content;
	}
	
	@Override
	public String toString() {
		String s ="";
		for (int i = 0; i < getContent().size(); i ++) {
			s += "\n" + getContent().get(i);
		}
		return s;
	}
	
	public void renameFile(String oldName, String newName) {
		File file = new File(oldName + ".txt");
		File rename = new File(newName + ".txt");
		file.renameTo(rename);
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
	
	public void modifyFile(String name, String newString) throws FileNotFoundException {
		String oldContent = "";
		FileReader fReader = new FileReader(name + ".txt");
		BufferedReader reader = new BufferedReader(fReader);
		String line;
		try {
			line = reader.readLine();
			while (line != null) {
				oldContent = oldContent + " " + line + " ";
				line = reader.readLine();
			}
  			String newContent = oldContent.replace(oldContent, newString);
			
			FileWriter writer = new FileWriter(name + ".txt");
			writer.write(newContent);
			reader.close();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
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
