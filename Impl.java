

public interface Impl {

/*	this method checks for account(file). If found, it then compares the password to see if the login attempt is successful or not
	if there is no account(file), will pop up dialog box with that information. */
	void checkLogin();

	//if the login is found to be successful, this method pulls the information from the existing file (using the Weight driver class)
	//and then makes a panel and puts that information in it
	void loginSuccess();
	
	//this method is called when the Create Account button is clicked. Makes a panel for user to enter their information
	//when submit button is pressed, it checks to see whether file(account) already exists before moving on 
	void createAccount();

	//this method is called when the user finishes filling out the new account form and clicks submit
	//it then makes the file and writes the initial information to it
	void selectionButtonPressed();

	//this method pulls the information the user has entered into the text boxes and creates new Records from it
	//it then adds the Record object to the arraylist and writes the info to the file
	void updateButtonPressed();
	
	//this method is called when the user is going back from viewing their stats to update their records again
	//made separate from the updateButton method because updateButton includes writing to the file again when it is pressed
	//no need to write the information from Calculate in again before they update - was duplicating records.
	//essentially a stripped-down version of updateButtonPressed
	void backUpdate();

	//this method takes the user to a new Panel with their data and calculations on it for them to view
	//because a new user can access this, the delta information (change in last two Records) is restricted only to larger file sizes
	void calculatePressed();

}