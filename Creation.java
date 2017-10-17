import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;

public abstract class Creation extends JPanel implements Impl {

	private JLabel nameLabel, change, balanceLabel, weightLabel, goalLabel, BMILabel, poundsLabel, topLabel, userLabel,
			passLabel;
	private JTextField name, balance, weight, goal, BMI, pounds, txuser, userField, passField, initBal;
	private JTextArea nameArea, changeArea, heightArea, weightArea, goalArea, infoArea, BMIarea, userArea, passArea;
	private JButton submit, update, calculate, graph, blogin, baccount;
	private JPasswordField pass;
	JPanel panel, updated, calculated, account, welcome, back;
	File file;
	Font font = new Font("Arial", 12, 24);
	String Record, puname, ppaswd;
	Account driver;
	String nameText = "", heightText = "", weightText = "", goalText = "", newName = "", userName = "", password = "",
			userText = "", passText = "", newHeight = "", newGoal = "", newWeight = "";
	double heightNum, weightNum, goalNum;
	newLinkedList<Record> recordStack;
	ArrayList<Point2D.Double> points = new ArrayList<Point2D.Double>();
	Archive dropdown = null;
	JButton fetchButton;
	JTextArea oldRecords;
	JLabel seeOld;

	public Creation() {
		super();
	}

	public Creation(LayoutManager layout) {
		super(layout);
	}

	public Creation(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public Creation(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

	// sets up the initial panel for logging in
	protected void initUI() {
		JLabel comp = new JLabel("AxiFi: Developed by Cyberdyne");
		blogin = new JButton("Login");
		baccount = new JButton("Make an account");
		txuser = new JTextField(15);
		pass = new JPasswordField(15);
		userLabel = new JLabel("Username: ");
		passLabel = new JLabel("Password: ");

		panel = new JPanel();
		panel.setPreferredSize(new Dimension(400, 500));
		panel.setBorder(new EmptyBorder(new Insets(100, 100, 100, 100)));
		panel.setLocation(500, 280);
		panel.setLayout(null);

		comp.setBounds(120, 10, 200, 20);
		userLabel.setBounds(100, 40, 200, 20);
		passLabel.setBounds(100, 90, 200, 20);
		txuser.setBounds(100, 60, 200, 20);
		pass.setBounds(100, 110, 200, 20);
		blogin.setBounds(140, 150, 120, 20);
		baccount.setBounds(100, 185, 200, 20);

		panel.add(comp);
		panel.add(baccount);
		panel.add(blogin);
		panel.add(txuser);
		panel.add(pass);
		panel.add(userLabel);
		panel.add(passLabel);
		blogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkLogin();
			}
		});
		baccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setVisible(false);
				createAccount();
			}
		});
		add(panel);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mainPackage.Impl#checkLogin()
	 */
	@Override
	public void checkLogin() {

		puname = txuser.getText();
		ppaswd = pass.getText();
		file = new File(puname);
		Scanner scanner = null;

		try {
			scanner = new Scanner(file).useDelimiter(",");
		} catch (FileNotFoundException e1) {
		}

		if (!file.exists()) {
			JOptionPane.showMessageDialog(panel, "Username does not exist. Please make an account.");
		}

		else {
			password = scanner.next().trim();

			if (ppaswd.equals(password)) {
				driver = new Account(file);
				recordStack = driver.recordStack;
				loginSuccess();
				panel.setVisible(false);
			}

			else {
				JOptionPane.showMessageDialog(panel, "Incorrect username/password");
				pass.setText("");
				pass.requestFocus();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mainPackage.Impl#loginSuccess()
	 */
	@Override
	public void loginSuccess() {
		welcome = new JPanel();
		welcome.setLayout(new BoxLayout(welcome, BoxLayout.Y_AXIS));
		welcome.setPreferredSize(new Dimension(400, 500));
		welcome.setBorder(new EmptyBorder(new Insets(40, 100, 100, 100)));

		topLabel = new JLabel("Your account: ");
		welcome.add(topLabel);
		welcome.add(Box.createRigidArea(new Dimension(0, 50)));

		nameLabel = new JLabel("Name: ");
		name = new JTextField(puname);
		welcome.add(nameLabel);
		welcome.add(name);
		welcome.add(Box.createRigidArea(new Dimension(0, 25)));

		balanceLabel = new JLabel("Balance: ");
		balance = new JTextField(driver.getBalanceString());
		balanceLabel.add(balance);
		welcome.add(balanceLabel);
		welcome.add(balance);
		welcome.add(Box.createRigidArea(new Dimension(0, 25)));

		update = new JButton("Update");
		welcome.add(update);
		welcome.add(Box.createRigidArea(new Dimension(0, 5)));
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkNumInput(balance, weight, goal) == false) {
					updateButtonPressed();
					welcome.setVisible(false);
				}
			}

		});

		add(welcome);
		
		//addOlder(welcome);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mainPackage.Impl#createAccount()
	 */
	@Override
	public void createAccount() {
		account = new JPanel();
		account.setLayout(new BoxLayout(account, BoxLayout.Y_AXIS));
		account.setPreferredSize(new Dimension(400, 500));
		account.setBorder(new EmptyBorder(new Insets(40, 100, 100, 100)));

		userLabel = new JLabel("Choose a username: ");
		userField = new JTextField();
		account.add(userLabel);
		account.add(userField);
		account.add(Box.createRigidArea(new Dimension(0, 25)));

		passLabel = new JLabel("Choose a password: ");
		passField = new JTextField();
		account.add(passLabel);
		account.add(passField);
		account.add(Box.createRigidArea(new Dimension(0, 25)));

		nameLabel = new JLabel("Your name: ");
		name = new JTextField();
		account.add(nameLabel);
		account.add(name);
		account.add(Box.createRigidArea(new Dimension(0, 25)));

		balanceLabel = new JLabel("Initial deposit: ");
		initBal = new JTextField();
		balanceLabel.add(initBal);
		account.add(balanceLabel);
		account.add(initBal);
		account.add(Box.createRigidArea(new Dimension(0, 25)));

		/*weightLabel = new JLabel("Current weight (in pounds): ");
		weight = new JTextField();
		account.add(weightLabel);
		account.add(weight);
		account.add(Box.createRigidArea(new Dimension(0, 25)));

		goalLabel = new JLabel("Goal weight (in pounds): ");
		goal = new JTextField();
		account.add(goalLabel);
		account.add(goal);
		account.add(Box.createRigidArea(new Dimension(0, 25)));*/

		submit = new JButton("Create");
		account.add(submit);
		account.add(Box.createRigidArea(new Dimension(0, 5)));

		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*if (checkWordInput(userField, passField, name) == false)*/ {
					String fileName = userField.getText();
					file = new File(fileName);
					if (!file.exists()) {
						account.setVisible(false);
						puname = name.getText();
						selectionButtonPressed();
					}

					else {
						JOptionPane.showMessageDialog(null, "Account already exists, please choose another username.");
						userField.setText("");
						txuser.requestFocus();
					}
				}
			}
		});

		add(account);

	}

	//checks user input in text boxes
	public boolean checkNumInput(JTextField height, JTextField weight, JTextField goal) {
		boolean broke = false;
		if (weight.getText().equals("") || !(Pattern.matches("\\d\\d\\d", weight.getText()))) {
			JOptionPane.showMessageDialog(account, "Invalid weight");
			weight.setFocusable(true);
			broke = true;
		} else if (height.getText().equals("") || !(Pattern.matches("\\d\\d", height.getText()))) {
			JOptionPane.showMessageDialog(account, "Invalid height");
			height.setFocusable(true);
			broke = true;
		} else if (goal.getText().equals("") || !(Pattern.matches("\\d\\d\\d", goal.getText()))) {
			JOptionPane.showMessageDialog(account, "Invalid goal. Please be healthy!");
			goal.setFocusable(true);
			broke = true;
		}
		return broke;
	}

	//checks user input in the other text boxes for making an account
	public boolean checkWordInput(JTextField userField, JTextField passField, JTextField name) {
		boolean broke = false;
		if (userField.getText().equals("") || !(Pattern.matches("^[a-zA-Z]+$$", userField.getText()))) {
			JOptionPane.showMessageDialog(account, "Username has invalid characters! Only A-Z allowed.");
			userField.setFocusable(true);
			broke = true;

		} else if (passField.getText().equals("") || !(Pattern.matches("^[a-zA-Z]+$$", passField.getText()))) {
			JOptionPane.showMessageDialog(account, "Password has invalid characters! Only A-Z allowed.");
			passField.setFocusable(true);
			broke = true;

		} else if (name.getText().equals("") || !(Pattern.matches("^[a-zA-Z]+$$", name.getText()))) {
			JOptionPane.showMessageDialog(account, "Name has invalid characters! Only A-Z allowed.");
			name.setFocusable(true);
			broke = true;
		}
		return broke;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mainPackage.Impl#selectionButtonPressed()
	 */
	@Override
	public void selectionButtonPressed() {
		userText = userField.getText();
		passText = passField.getText();
		nameText = name.getText();
/*		heightText = height.getText();
		weightText = weight.getText();
		goalText = goal.getText();*/

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		/*heightNum = Double.parseDouble(heightText);
		weightNum = Double.parseDouble(weightText);
		goalNum = Double.parseDouble(goalText);*/

		// writes information to file
		String fileName = userText;
		file = new File(fileName);
		try {
			file.createNewFile();
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			// writes password to first line
			bw.write(passText + ",");
			bw.newLine();
			// then each Record will go on subsequent lines
			bw.write(timestamp + " ");
			bw.close();
			// makes Weight object of that file
			driver = new Account(file);
			recordStack = driver.recordStack;
			// adds the new Record to the Stack
			//recordStack.push(new Record(timestamp, heightNum));

		} catch (IOException e2) {
			e2.printStackTrace();
		}

		updated = new JPanel();
		updated.setLayout(new BoxLayout(updated, BoxLayout.Y_AXIS));
		updated.setPreferredSize(new Dimension(400, 500));
		updated.setBorder(new EmptyBorder(new Insets(40, 100, 100, 100)));

		topLabel = new JLabel("Info Logged!");
		updated.add(topLabel);
		updated.add(Box.createRigidArea(new Dimension(0, 50)));

		nameLabel = new JLabel("Name: ");
		name = new JTextField();
		name.setText(nameText);
		updated.add(nameLabel);
		updated.add(name);
		updated.add(Box.createRigidArea(new Dimension(0, 25)));

		/*heightLabel = new JLabel("Your height (in inches) is: ");
		height = new JTextField();
		height.setText(heightText);
		heightLabel.add(height);
		updated.add(heightLabel);
		updated.add(height);
		updated.add(Box.createRigidArea(new Dimension(0, 25)));

		weightLabel = new JLabel("Your weight is: ");
		weight = new JTextField();
		weight.setText(weightText);
		updated.add(weightLabel);
		updated.add(weight);
		updated.add(Box.createRigidArea(new Dimension(0, 25)));

		goalLabel = new JLabel("Your goal is: ");
		goal = new JTextField();
		goal.setText(goalText);
		updated.add(goalLabel);
		updated.add(goal);
		updated.add(Box.createRigidArea(new Dimension(0, 25)));
		add(updated);*/

		/*add(updated);

		calculate = new JButton("See stats");
		updated.add(calculate);
		updated.add(Box.createRigidArea(new Dimension(0, 5)));
		calculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkNumInput(height, weight, goal) == false) {
					calculatePressed();
					updated.setVisible(false);
				}
			}

		});*/

		add(updated);
		panel.setVisible(false);
		updated.setVisible(true);
		addOlder(updated);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mainPackage.Impl#updateButtonPressed()
	 */
	@Override
	public void updateButtonPressed() {
		/*newName = name.getText();
		newWeight = weight.getText();
		newHeight = height.getText();
		newGoal = goal.getText();

		// creates new Record object of user input and adds it to linked list
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		heightNum = Double.parseDouble(newHeight);
		weightNum = Double.parseDouble(newWeight);
		goalNum = Double.parseDouble(newGoal);
		recordStack.push(new Record(timestamp, heightNum));

		// writes that user input (the Record) to file
		FileWriter fw;
		try {
			fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.newLine();
			bw.write(timestamp + " ");
			bw.close();
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		updated = new JPanel();
		updated.setLayout(new BoxLayout(updated, BoxLayout.Y_AXIS));
		updated.setPreferredSize(new Dimension(400, 500));
		updated.setBorder(new EmptyBorder(new Insets(40, 100, 100, 100)));

		topLabel = new JLabel("Your new weigh in:  ");
		updated.add(topLabel);
		updated.add(Box.createRigidArea(new Dimension(0, 50)));

		nameLabel = new JLabel("Name: ");
		name = new JTextField();
		name.setText(newName);
		updated.add(nameLabel);
		updated.add(name);
		updated.add(Box.createRigidArea(new Dimension(0, 25)));

		balanceLabel = new JLabel("Your new height is: ");
		balance = new JTextField();
		balance.setText(newHeight);
		updated.add(balanceLabel);
		updated.add(balance);
		updated.add(Box.createRigidArea(new Dimension(0, 25)));
		driver.setUserHeight(heightNum);

		weightLabel = new JLabel("Your new weight is: ");
		weight = new JTextField();
		weight.setText(newWeight);
		updated.add(weightLabel);
		updated.add(weight);
		updated.add(Box.createRigidArea(new Dimension(0, 25)));
		driver.setUserWeight(weightNum);

		goalLabel = new JLabel("Your goal is: ");
		goal = new JTextField();
		goal.setText(newGoal);
		updated.add(goalLabel);
		updated.add(goal);
		updated.add(Box.createRigidArea(new Dimension(0, 25)));
		add(updated);
		driver.setUserGoal(goalNum);

		calculate = new JButton("See new stats");
		updated.add(calculate);
		updated.add(Box.createRigidArea(new Dimension(0, 5)));
		calculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkNumInput(balance, weight, goal) == false) {

					calculatePressed();
					updated.setVisible(false);
				}
			}

		});

		add(updated);

		panel.setVisible(false);
		updated.setVisible(true);
		addOlder(updated);*/

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mainPackage.Impl#backUpdate()
	 */
	@Override
	public void backUpdate() {
		/*back = new JPanel();
		back.setLayout(new BoxLayout(back, BoxLayout.Y_AXIS));
		back.setPreferredSize(new Dimension(400, 500));
		back.setBorder(new EmptyBorder(new Insets(40, 100, 100, 100)));

		topLabel = new JLabel("Your last weigh in: ");
		back.add(topLabel);
		back.add(Box.createRigidArea(new Dimension(0, 50)));

		nameLabel = new JLabel("Name: ");
		name = new JTextField(puname);
		back.add(nameLabel);
		back.add(name);
		back.add(Box.createRigidArea(new Dimension(0, 25)));

		balanceLabel = new JLabel("Height (in inches): ");
		balance = new JTextField(driver.getHeightString());
		balanceLabel.add(balance);
		back.add(balanceLabel);
		back.add(balance);
		back.add(Box.createRigidArea(new Dimension(0, 25)));

		weightLabel = new JLabel("Current weight (in pounds): ");
		weight = new JTextField(driver.getWeightString());
		back.add(weightLabel);
		back.add(weight);
		back.add(Box.createRigidArea(new Dimension(0, 25)));

		goalLabel = new JLabel("Goal weight (in pounds): ");
		goal = new JTextField(driver.getGoalString());
		back.add(goalLabel);
		back.add(goal);
		back.add(Box.createRigidArea(new Dimension(0, 25)));

		update = new JButton("Update");
		back.add(update);
		back.add(Box.createRigidArea(new Dimension(0, 5)));
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkNumInput(balance, weight, goal) == false) {

					updateButtonPressed();
					back.setVisible(false);
				}
			}

		});
		add(back);
		addOlder(back);*/
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mainPackage.Impl#calculatePressed()
	 */

	@Override
	public void calculatePressed() {
		/*newName = name.getText();
		newHeight = height.getText();
		newWeight = weight.getText();
		newGoal = goal.getText();

		heightNum = Double.parseDouble(newHeight);
		weightNum = Double.parseDouble(newWeight);
		goalNum = Double.parseDouble(newGoal);

		calculated = new JPanel();
		calculated.setLayout(new BoxLayout(calculated, BoxLayout.Y_AXIS));
		calculated.setPreferredSize(new Dimension(400, 500));
		calculated.setBorder(new EmptyBorder(new Insets(40, 80, 80, 40)));

		BMILabel = new JLabel("Your BMI: ");
		infoArea = new JTextArea();
		infoArea.setText(newName + ", if you weigh " + newWeight + " pounds and are " + newHeight
				+ " inches tall, your BMI is: ");
		infoArea.setLineWrap(true);
		infoArea.setWrapStyleWord(true);
		BMIarea = new JTextArea();
		BMIarea.setText(driver.getBMI());
		BMIarea.setFont(font);
		BMIarea.setLineWrap(true);
		BMIarea.setWrapStyleWord(true);

		calculated.add(BMILabel);
		calculated.add(infoArea);
		calculated.add(BMIarea);
		calculated.add(Box.createRigidArea(new Dimension(0, 25)));

		weightLabel = new JLabel("Your current weight: ");
		weightArea = new JTextArea();
		weightArea.setText(newWeight + " pounds");
		weightArea.setFont(font);
		weightArea.setLineWrap(true);
		weightArea.setWrapStyleWord(true);
		calculated.add(weightLabel);
		calculated.add(weightArea);
		calculated.add(Box.createRigidArea(new Dimension(0, 25)));

		goalLabel = new JLabel("Pounds left to goal: ");
		goalArea = new JTextArea();
		goalArea.setText(driver.getGoal(weightNum) + " pounds");
		goalArea.setFont(font);
		goalArea.setLineWrap(true);
		goalArea.setWrapStyleWord(true);
		calculated.add(goalLabel);
		calculated.add(goalArea);
		calculated.add(Box.createRigidArea(new Dimension(0, 25)));

		// checks to make sure user has enough records (need at least two
		// entries to be able to compare!) before
		// calculating and displaying the change in the last two weigh-ins
		if (weightStack.size() > 2) {
			change = new JLabel("Since your last weigh-in: ");
			changeArea = new JTextArea();
			Record first = (Record) weightStack.get(1);
			Record second = (Record) weightStack.get(2);

			double delta = (second.userWeight - first.userWeight);
			if (delta > 0) {
				changeArea.setText("You have lost " + delta + " pounds!");
			} else {
				changeArea.setText("You have gained " + Math.abs(delta) + " pounds");
			}

			changeArea.setFont(font);
			changeArea.setLineWrap(true);
			changeArea.setWrapStyleWord(true);
			calculated.add(change);
			calculated.add(changeArea);
			calculated.add(Box.createRigidArea(new Dimension(0, 25)));
		}

		add(calculated);

		update = new JButton("Go back to update");
		calculated.add(update);
		calculated.add(Box.createRigidArea(new Dimension(0, 5)));
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backUpdate();
				calculated.setVisible(false);
			}

		});

		panel.setVisible(false);
		calculated.setVisible(true);
		addOlder(calculated);*/

	}

	//sets up the panel for looking at older records
	public void addOlder(JPanel current) {
		JPanel olderSet = new JPanel();
		olderSet.setLayout(new BoxLayout(olderSet, BoxLayout.Y_AXIS));
		olderSet.setBorder(new EmptyBorder(new Insets(100, 50, 50, 50)));

		seeOld = new JLabel();
		seeOld.setText("See Older Records");
		current.add(seeOld);

		JPanel box = new JPanel();
		box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
		dropdown = new Archive(recordStack);
		box.add(dropdown.getDropdown());
		fetchButton = new JButton("Go");
		box.add(fetchButton);
		current.add(box);
		
		oldRecords = new JTextArea();

		ActionListener action1 = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Record> returned = new ArrayList<Record>();
				returned = dropdown.findRecords(recordStack);
				String output = "";
				oldRecords.setText(output);
				for (int i = 0; i < returned.size(); i++) {
					output = output + returned.get(i) + "\n";
				}
				JOptionPane.showMessageDialog(current, output);
			}
		};
		fetchButton.addActionListener(action1);
	}

	
}