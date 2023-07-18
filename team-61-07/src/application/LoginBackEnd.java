package application;

import java.io.*;
import java.util.*;

/**
 * Main test class of the login process using the FlatFile, LoginPassword, and ModifyPassword classes.
 * 
 */
//public class LoginBackEnd {
	
//	public static void main(String[] args) throws Exception {
//		
//		/*DEBUG: the scanner input stuff is supposed to represent the text boxes in the program
//		 * also for the change/reset methods having the program exit after an incorrect password/answer 
//		*/
//		
//		final String FILE_NAME = "JMJAccount";//file name for storage
//		
//		FlatFile fileManager = new FlatFile();//initializing FlatFile class
//		Account mainAccount = new Account();//initializing account
//		Scanner input = new Scanner(System.in);//creating Scanner object
//		
//		if (fileManager.searchFile(FILE_NAME) == true) {//if file exists; usual login
//			System.out.println("Existing account found");
//			mainAccount.accountFromArray(fileManager.readFileArray(FILE_NAME));//setting program account to file account
//		} else {//if file does not exist; first time login
//			System.out.println("First time login; creating default account");
//			Account defaultAccount = new Account();//creating default account
//			fileManager.createEmptyFile(FILE_NAME);//creating file
//			fileManager.writeFile(FILE_NAME, defaultAccount.getPassword() + "\n" + defaultAccount.getQuestion() + "\n" + defaultAccount.getAnswer());//writing default account to file
//			mainAccount.accountFromArray(fileManager.readFileArray(FILE_NAME));//setting program account to file account
//			System.out.println("Default account password is '" + defaultAccount.getPassword() + "'");//displays p as default password
//			System.out.println("User is required to change passwords from the default account");
//			mainAccount.changePassword(input);//running changePassword method to change after default password
//			fileManager.writeFile(FILE_NAME, mainAccount.getPassword() + "\n" + mainAccount.getQuestion() + "\n" + mainAccount.getAnswer());//saving account to file after change
//		}
//		
//		System.out.println();
//		
//		System.out.print("Enter 'login' for normal login, 'change' to change password, 'reset' to reset password: ");//simulating ui buttons
//		String choice = input.nextLine();
//		
//		switch (choice) {
//			case "login"://simulating login process
//				mainAccount.accountLogin(input);
//				break;
//			case "change"://simulating user pressing "change password" button
//				mainAccount.changePassword(input);//testing changing password
//				fileManager.writeFile(FILE_NAME, mainAccount.getPassword() + "\n" + mainAccount.getQuestion() + "\n" + mainAccount.getAnswer());//saving account to file
//				break;
//			case "reset"://simulating user pressing "reset password" button
//				mainAccount.resetPassword(input);//testing resetting password
//				fileManager.writeFile(FILE_NAME, mainAccount.getPassword() + "\n" + mainAccount.getQuestion() + "\n" + mainAccount.getAnswer());//saving account to file
//				break;
//			default://none of the above choices
//				System.err.println("Invalid choice");
//				break;
//		}
//		
//	}

//}

/**
 * Class that handles finding, reading, and writing the password and security question/answer from an external file.
 * The file name is JMJPassword.txt and contains the user password and security password.
 * (implemented by Hayk/Richie)
 */
class FlatFile {
	
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
 * Class that handles the password and includes methods of changing and resetting the password using a security question and answer.
 * This class uses FlatFile for changing and resetting the password.
 * (implemented by Richie)
 */
class Account {
	private String password;
	private String secQuestion;
	private String secAnswer;//instance variables
	
	/**
	 * Default constructor with no arguments.
	 */
	public Account() {
		this.password = "p";
		this.secQuestion = "question";
		this.secAnswer = "answer";
	}
	
	/**
	 * Constructor with arguments.
	 * @param password The inputed password
	 * @param secQuestion The inputed security question
	 * @param secAnswer The inputed answer to the security question
	 */
	public Account(String password, String secQuestion, String secAnswer) {
		this.password = password;
		this.secQuestion = secQuestion;
		this.secAnswer = secAnswer;
	}
	
	public String getPassword() {
		String fileName = "JMJAccount";
		FlatFile fileManager = new FlatFile();
		String[] readFile = fileManager.readFileArray(fileName);
		this.password = readFile[0];
		return this.password;
	}
	public String getQuestion() {
		String fileName = "JMJAccount";
		FlatFile fileManager = new FlatFile();
		String[] readFile = fileManager.readFileArray(fileName);
		this.password = readFile[1];
		return this.secQuestion;
	}
	public String getAnswer() {
		String fileName = "JMJAccount";
		FlatFile fileManager = new FlatFile();
		String[] readFile = fileManager.readFileArray(fileName);
		this.password = readFile[2];
		return this.secAnswer;
	}//getters for variables
	
	public void setPassword(String password) {
		this.password = password;
	}
	public void setQuestion(String secQuestion) {
		this.secQuestion = secQuestion;
	}
	public void setAnswer(String secAnswer) {
		this.secAnswer = secAnswer;
	}//setters for variables
	
	/**
	 * Method that verifies an inputed String for a new password.
	 * @param newPassword The new password that the user wants. 
	 * @param verify Repeating the new password to make sure it is typed correctly
	 * @return newPass the new password after verification 
	 */
	public String verifyPassword(String newPassword, String verify) {
		String newPass = "";
		String newPassVerify = "";//initializing new password variables
		
		//System.out.print("Enter new password: ");
		newPass = newPassword;//input new password
		//System.out.println();
		//System.out.print("Verify new password: ");
		newPassVerify = verify;//verify new password
		//System.out.println();
		
		while (newPassVerify.equals(newPass) != true) {//runs if password is not verified
			//System.err.println("Inputed passwords are not the same");
			//System.out.print("Reverify new password: ");
			newPassVerify = verify;//reverify new password
			//System.out.println();
		}
		//System.out.println("New password verified");//new password is verified
		return newPass;
	}
	
	/**
	 * Method that reads the account details from a file and modifies the program account from the file.
	 * @param accountDetails String array containing password [0], security question [1], and answer to security question [2]
	 */
	public void accountFromArray(String[] accountDetails) {
		setPassword(accountDetails[0]);
		setQuestion(accountDetails[1]);
		setAnswer(accountDetails[2]);//using setter methods for each index
	}
	
	/**
	 * Method that checks if password is correct to login
	 * @param password Getting the password 
	 */
	public boolean accountLogin(String password) {
		String loginVerify = "";//initializing password verification
		
		//System.out.print("Enter the password: ");
		loginVerify = password;//verify input
		//System.out.println();
		
		if (loginVerify.equals(getPassword()) == true) {//if input matches password
			//System.out.println("Password verified; logging in");
			return true;
		} else {//incorrect password
			//System.err.println("Incorrect password");
			return false;
		}
	}
	
	/**
	 * Method that changes the password using the old and new passwords and creates a security question and answer for changing the password.
	 * First time password change
	 * @param oldPass The old password 
	 * @param newPasswords The new password
	 * @param newVerify Repeating the new password 
	 * @param securityQ The security question 
	 * @param ansSecQ The answer to security question 
	 */
	public void changePassword(String oldPass, String newPasswords, String newVerify, String securityQ, String ansSecQ) {
		String oldPassVerify = "";//initializing old password verification
		
		//System.out.print("Verify old password: ");
		oldPassVerify = oldPass;//verify old password
		//System.out.println();
		
		if (oldPassVerify.equals("p") == true) {
			//System.out.println("Old password verified; changing password");
			
			String newPass = verifyPassword(newPasswords, newVerify);//calling verifyPassword method for new password
			setPassword(newPass);//setting Account password to new password
			
			//System.out.print("Enter security question: ");
			String newQuestion = securityQ;//input security question
			setQuestion(newQuestion);//setting Account question to new question
			//System.out.println();
			//System.out.print("Enter answer to security question: ");
			String newAnswer = ansSecQ;//input answer to security question
			setAnswer(newAnswer);//setting Account question to new answer
			//System.out.println();
			//System.out.println("Successfully changed password");
			
		} else {
			//System.err.println("Password is incorrect; unable to change password");
		}
		
	}
	
	/**
	 * A method for changing the flatfile 
	 * 
	 * @param oldPass The old password 
	 * @param newPassword The new password 
	 * @param repeatNewPass The new password repeated 
	 * @param question The security question 
	 * @param answer The answer to the security question 
	 */
	public void changeFile (String oldPass, String newPassword, String repeatNewPass, String question, String answer) {
		String fileName = "JMJAccount";
		FlatFile fileManager = new FlatFile();
		fileManager.createEmptyFile(fileName);//creating file
		fileManager.writeFile(fileName, "p" + "\n" + "question" + "\n" + "answer");//writing default account to file
	
		accountFromArray(fileManager.readFileArray(fileName));//setting program account to file account
		changePassword(oldPass, newPassword, repeatNewPass, question, answer);//running changePassword method to change after default password
		fileManager.writeFile(fileName, newPassword + "\n" + question + "\n" + answer);//writing default account to file
		
	}
	
	/**
	 * Method that resets the password using the security question and answer for verification and sets the new password used.
	 * @param ansSec The answer to security question 
	 * @param newPassword The new password 
	 * @param verify Repeating the new password 
	 */
	public void resetPassword(String ansSec, String newPassword, String verify) {
		String verifyAnswer = "";//initializing security verification
		
		System.out.println(getQuestion());
		System.out.print("Enter answer to the security question: ");
		verifyAnswer = ansSec;//input answer for verification
		System.out.println();
		
		if (verifyAnswer.equals(getAnswer()) == true) {
			System.out.println("Answer successfully verified");
			
			String newPass = verifyPassword(newPassword, verify);//using verifyPassword method for new password
			setPassword(newPass);//setting Account password to new password
			System.out.println("Successfully reset password");
		} else {
			System.err.println("Answer is incorrect; unable to reset password");
		}
		
	}
}
