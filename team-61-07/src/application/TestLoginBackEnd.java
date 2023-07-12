package application;

import java.util.*;

/**
 * Main test class of the login process using the FlatFile, LoginPassword, and ModifyPassword classes.
 * 
 */
public class TestLoginBackEnd {
	
	public static void main(String[] args) throws Exception {
		
		/*DEBUG: the scanner input stuff is supposed to represent the text boxes in the program
		 * also for the change/reset methods having the program exit after an incorrect password/answer 
		*/
		
		Account test = new Account();//creating test account
		Scanner input = new Scanner(System.in);//creating Scanner object
		System.out.println("DEBUG: default password is 'p', answer is 'answer'");
		
		test.changePassword(input);//testing changing password
		test.resetPassword(input);//testing resetting password
		
	}

}

/**
 * Class that handles finding, reading, and writing the password and security question/answer from an external file.
 * The file name is JMJPassword.txt and contains the user password and security password.
 * (will be implemented by Hayk)
 */
class FlatFile {
	
}

/**
 * Class that handles the password and includes methods of changing and resetting the password using a security question and answer.
 * This class uses FlatFile for changing and resetting the password.
 * (will be implemented by Richie)
 */
class Account {
	private String password;
	private String secQuestion;
	private String secAnswer;//instance variables
	
	public Account() {
		this.password = "p";
		this.secQuestion = "question";
		this.secAnswer = "answer";
	}//default constructor with no arguments
	
	public Account(String password, String secQuestion, String secAnswer) {
		this.password = password;
		this.secQuestion = secQuestion;
		this.secAnswer = secAnswer;
	}//constructor with arguments
	
	public String getPassword() {
		return this.password;
	}
	public String getQuestion() {
		return this.secQuestion;
	}
	public String getAnswer() {
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
	 * @param input Scanner object used to input password
	 * @return newPass the new password after verification 
	 */
	public String verifyPassword(Scanner input) {
		String newPass = "";
		String newPassVerify = "";//initializing new password variables
		
		System.out.print("Enter new password: ");
		newPass = input.nextLine();//input new password
		System.out.println();
		System.out.print("Verify new password: ");
		newPassVerify = input.nextLine();//verify new password
		System.out.println();
		
		while (newPassVerify.equals(newPass) != true) {//runs if password is not verified
			System.err.println("Inputed passwords are not the same");
			System.out.print("Reverify new password: ");
			newPassVerify = input.nextLine();//reverify new password
			System.out.println();
		}
		System.out.println("New password verified");//new password is verified
		return newPass;
	}
	
	/**
	 * Method that changes the password using the old and new passwords and creates a security question and answer for changing the password.
	 * @param input Scanner object used to input passwords, security question, and answer
	 */
	public void changePassword(Scanner input) {
		String oldPassVerify = "";//initializing old password verification
		
		System.out.print("Verify old password: ");
		oldPassVerify = input.nextLine();//verify old password
		System.out.println();
		
		if (oldPassVerify.equals(getPassword()) == true) {
			System.out.println("Old password verified; changing password");
			
			String newPass = verifyPassword(input);//calling verifyPassword method for new password
			setPassword(newPass);//setting Account password to new password
			
			System.out.print("Enter security question: ");
			String newQuestion = input.nextLine();//input security question
			setQuestion(newQuestion);//setting Account question to new question
			System.out.println();
			System.out.print("Enter answer to security question: ");
			String newAnswer = input.nextLine();//input answer to security question
			setAnswer(newAnswer);//setting Account question to new answer
			System.out.println();
			
		} else {
			System.err.println("Password is incorrect; unable to change password");
		}
		
	}
	
	/**
	 * Method that resets the password using the security question and answer for verification and sets the new password used.
	 * @param input Scanner object used to input security answer and new password
	 */
	public void resetPassword(Scanner input) {
		String verifyAnswer = "";//initializing security verification
		
		System.out.println(getQuestion());
		System.out.print("Enter answer to the security question: ");
		verifyAnswer = input.nextLine();//input answer for verification
		System.out.println();
		
		if (verifyAnswer.equals(getAnswer()) == true) {
			System.out.println("Answer successfully verified");
			
			String newPass = verifyPassword(input);//using verifyPassword method for new password
			setPassword(newPass);//setting Account password to new password
		} else {
			System.err.println("Answer is incorrect; unable to reset password");
		}
		
	}
}
