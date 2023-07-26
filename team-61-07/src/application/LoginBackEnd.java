package application;

import java.io.*;
import java.util.*;


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
		this.secQuestion = readFile[1];
		return this.secQuestion;
	}
	public String getAnswer() {
		String fileName = "JMJAccount";
		FlatFile fileManager = new FlatFile();
		String[] readFile = fileManager.readFileArray(fileName);
		this.secAnswer = readFile[2];
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
			changeFile( newPasswords, securityQ, ansSecQ);
			
		} 
		else if (oldPassVerify.equals(getPassword()) == true) {
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
			changeFile( newPasswords, securityQ, ansSecQ);
			
		}
		else {
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
	public void changeFile (String newPassword, String question, String answer) {
		String fileName = "JMJAccount";
		FlatFile fileManager = new FlatFile();
		fileManager.createEmptyFile(fileName);//creating file
		fileManager.writeFile(fileName, "p" + "\n" + "question" + "\n" + "answer");//writing default account to file
	
		accountFromArray(fileManager.readFileArray(fileName));//setting program account to file account
		//changePassword(oldPass, newPassword, repeatNewPass, question, answer);//running changePassword method to change after default password
		fileManager.writeFile(fileName, newPassword + "\n" + question + "\n" + answer);//writing default account to file
		
	}
	
	/**
	 * Method that resets the password using the security question and answer for verification and sets the new password used.
	 * @param ansSec The answer to security question 
	 * @param newPassword The new password 
	 * @param verify Repeating the new password 
	 */
	public void resetPassword(String ansSec, String newPassword, String verify, String question) {
		if (ansSec.equals(getAnswer()) == true && newPassword.equals(verify) && question.equals(getQuestion())) {
			changeFile(newPassword, question, ansSec);
			
		} else {
			//System.err.println("Answer is incorrect; unable to reset password");
		}
		
	}
}
