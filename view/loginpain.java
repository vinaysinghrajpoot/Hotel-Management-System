// Specifies that this class belongs to the 'view' package, organizing the project structure.
package view;

// --- IMPORT STATEMENTS ---
// Imports all classes from the Abstract Window Toolkit (AWT) for basic GUI functionalities like graphics and layout.
import java.awt.*; 
// Imports AWT event handling classes for responding to user interactions like button clicks.
import java.awt.event.*; 
// Imports the File class to handle file and directory pathnames.
import java.io.File; 
// Imports the IOException class to handle errors that occur during input or output operations.
import java.io.IOException; 
// Imports the Connection interface for managing a session with a specific database.
import java.sql.Connection; 
// Imports the DriverManager class to manage a set of JDBC drivers for database connections.
import java.sql.DriverManager; 
// Imports the PreparedStatement interface to execute precompiled SQL queries efficiently and securely.
import java.sql.PreparedStatement; 
// Imports the ResultSet interface to hold the data retrieved from a database after a query execution.
import java.sql.ResultSet; 
// Imports the SQLException class to handle database access errors.
import java.sql.SQLException; 
// Imports the SimpleDateFormat class for formatting and parsing dates in a specific style.
import java.text.SimpleDateFormat; 
// Imports the Date class to represent a specific instant in time.
import java.util.Date; 
// Imports the ImageIO class, which provides methods to read and write images.
import javax.imageio.ImageIO; 
// Imports all classes from the Swing library for creating modern, platform-independent GUI components.
import javax.swing.*; 
// Imports the EtchedBorder class to create a decorative "etched" border around a component.
import javax.swing.border.EtchedBorder; 

// Defines the main class for the login panel GUI.
public class loginpain {
	
	// --- GUI COMPONENT DECLARATIONS ---
	// Declares the main window for the login screen.
	JFrame f2;	
	// Declares a main container panel and a specific panel for the login icon.
	JPanel panel1,panel; 
	// Stores the file path for the login icon image as a String.
	String imageFilePath = "C:/Users/ACER/MY_J2SE/Hotel-Project/Img/login.png"; 
	// Declares an array to hold the background image; using an array allows it to be modified inside an anonymous inner class.
	final Image[] backgroundImage = {null}; 
    // Declares a dropdown menu component.
	private JMenu menu; 
    // Declares the menu bar that will be placed at the top of the frame.
	private JMenuBar menuBar; 
    // Declares the individual items that will appear in the dropdown menu.
	private JMenuItem loginMenuItem,signupMenuItem; 
	// Declares labels to display static text like "User Name", "Password", "Date", "Time", and the title.
	JLabel logTitel,usernamelabel,passwordlabel,date,time; 
	// Declares text fields for user input and for displaying the date and time.
	private JTextField username,tfdate,tftime; 
	// Declares a specialized text field that masks the characters entered for the password.
	private JPasswordField password; 
	// Declares buttons for the "Login", "Sign up", and "Forgot Password" actions.
	JButton btlog,btsign,forgot; 
	
	// --- DATABASE VARIABLE DECLARATIONS ---
  	// Declares a Connection object to manage the database connection, initialized to null.
	Connection con = null; 
  	// Declares a PreparedStatement object to execute parameterized SQL queries.
	PreparedStatement pst; 
  	// Declares a ResultSet object to store the results returned from a SQL query.
	ResultSet rs; 
	
	/**
	 * A method to clear the text from the username and password fields.
	 * It also sets the cursor focus back to the username field for user convenience.
	 */
	public void clearlogin() {
		
		// Sets the text of the username text field to an empty string.
		username.setText(""); 
		// Sets the text of the password field to an empty string.
		password.setText(""); 
		// Requests that the input focus be placed on the username text field.
		username.requestFocus(); 
		
	} // End of clearlogin method.
	
	/**
	 * A method to establish a connection to the MySQL database.
	 */
	public void Connection() {
		// Starts a try block to handle potential exceptions during the database connection process.
		try {
			// Loads the MySQL JDBC driver class into the JVM, making it available for use.
			Class.forName("com.mysql.jdbc.Driver");
			// Establishes a connection to the database at the specified URL with the provided username and password.
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel","root", "");
		// Catches any exception that might occur (e.g., driver not found, database server down).
		} catch (Exception ex) { 
			// Prints the exception's stack trace to the standard error stream for debugging purposes.
			ex.printStackTrace(); 
		} // End of try-catch block.
	} // End of Connection method.
	
	/**
	 * A method to validate user credentials against the database.
	 * @param usernames The username entered by the user.
	 * @param passwords The password entered by the user.
	 * @return boolean Returns true if the credentials are valid, false otherwise.
	 */
	private boolean loginvalidate(String usernames,String passwords) {
  		// Starts a try block to handle potential SQL exceptions.
		try {
  			// Prepares a SQL statement to select all columns from the 'signup' table where username and password match.
  			pst=con.prepareStatement ("SELECT * FROM signup WHERE userName = ? AND password = ? " );  			
  					
  			// Replaces the first placeholder (?) in the SQL query with the user-provided username.
  			pst.setString(1, usernames);
			// Replaces the second placeholder (?) in the SQL query with the user-provided password.
        	pst.setString(2, passwords);
			// Executes the prepared SQL query and stores the results in a ResultSet object.
        	ResultSet rs=pst.executeQuery();
			// Moves the cursor to the first row of the ResultSet; if a row exists, it means a match was found.
        	if(rs.next())
        	{
        		// If a match is found, return true, indicating a successful login.
				return true; 
        	}
        // Catches any SQLException that might occur during the database query.
		}catch(SQLException e){ 
        			// Prints the SQL exception's stack trace for debugging.
					e.printStackTrace();        		
        } // End of try-catch block.
  		// If no match is found or an exception occurs, return false, indicating a failed login.
		return false; 
  	} // End of loginvalidate method.

	/**
	 * A method to create, configure, and display the current date and time on the panel.
	 */
	public void date_time()
	{
		// Creates a new JLabel with the text "Date- ".
		date=new JLabel("Date- ");
		// Sets the font of the date label to "Times New Roman", bold, and size 18.
		date.setFont(new Font("Times New Roman",Font.BOLD,18)); 
		// Sets the position (x=30, y=30) and size (width=150, height=25) of the date label.
		date.setBounds(30, 30, 150, 25); 
		// Makes the date label initially invisible.
		date.setVisible(false); 
		// Adds the date label to the main panel.
		panel1.add(date); 
		
		// Creates a new JLabel with the text "Time- ".
		time=new JLabel("Time- ");
		// Sets the font of the time label to "Times New Roman", bold, and size 18.
		time.setFont(new Font("Times New Roman",Font.BOLD,18)); 
		// Sets the position (x=30, y=60) and size (width=150, height=25) of the time label.
		time.setBounds(30, 60, 150, 25); 
		// Makes the time label initially invisible.
		time.setVisible(false); 
		// Adds the time label to the main panel.
		panel1.add(time);	
		
		// Creates a new JTextField to display the date.
		tfdate=new JTextField();
		// Sets the position (x=80, y=32) and size (width=90, height=22) of the date text field.
		tfdate.setBounds(80, 32, 90, 22); 
		// Sets the background color of the date text field to yellow.
		tfdate.setBackground(Color.yellow); 
		// Makes the date text field initially invisible.
		tfdate.setVisible(false); 
		// Adds the date text field to the main panel.
		panel1.add(tfdate); 
		
		// Creates a new JTextField to display the time.
		tftime=new JTextField();
		// Sets the position (x=80, y=62) and size (width=90, height=22) of the time text field.
		tftime.setBounds(80, 62, 90, 22); 
		// Sets the background color of the time text field to yellow.
		tftime.setBackground(Color.yellow); 
		// Makes the time text field initially invisible.
		tftime.setVisible(false); 
		// Adds the time text field to the main panel.
		panel1.add(tftime); 
		
		// Creates a new Date object to get the current system date and time.
		Date d= new Date();
		// Creates a SimpleDateFormat object to format the date as "dd-MM-YYYY".
		SimpleDateFormat dateform=new SimpleDateFormat("  dd-MM-YYYY");
		// Formats the current date using the specified format and sets it as the text of the date field.
		tfdate.setText(dateform.format(d));
		
		// Creates a new Swing Timer that will fire an event every second (the initial delay is 0).
		 new Timer(0, new ActionListener() {
	            // Specifies that this method overrides a method from a superclass or interface.
				@Override
	            // This method is called each time the Timer fires an event.
				public void actionPerformed(ActionEvent e) {
	                // Gets the current date and time again inside the timer's action.
					Date d = new Date(); 
					// Creates a SimpleDateFormat object to format the time as "hh:mm:ss a" (e.g., 06:04:56 PM).
					SimpleDateFormat s = new SimpleDateFormat(" hh:mm:ss a");
					// Formats the current time and updates the text of the time field.
					tftime.setText(s.format(d));
	            }
	        // Starts the timer, causing it to begin firing events.
			}).start(); 
	} // End of date_time method.
		
	/**
	 * The constructor for the loginpain class.
	 * It initializes the GUI, date/time display, and database connection when a new object is created.
	 */
	public loginpain() {		
		// Calls the method that builds and configures the login window.
		loginfame(); 
		// Calls the method that sets up the date and time display.
		date_time(); 
		// Calls the method that establishes the connection to the database.
		Connection(); 
	} // End of constructor.
	
	/**
	 * This method creates and configures the entire login frame and all its components.
	 */
	public void loginfame() {
		
		// --- MENU BAR SETUP ---
		// Creates a new JMenuBar object.
		menuBar = new JMenuBar(); 
        // Creates a new JMenu with the title "Options".
		menu = new JMenu("Options"); 
		
        // Creates a JMenuItem with the label "Admin".
		loginMenuItem = new JMenuItem("Admin"); 
        // Creates a JMenuItem with the label "Customer".
		signupMenuItem = new JMenuItem("Customer"); 
        
        // Adds the "Admin" menu item to the "Options" menu.
		menu.add(loginMenuItem); 
        // Adds the "Customer" menu item to the "Options" menu.
		menu.add(signupMenuItem); 
        // Adds the "Options" menu to the menu bar (this part is not actually added to the frame in the code).
		menuBar.add(menu); 
        
		// --- MAIN FRAME (JFrame) SETUP ---
		// Creates a new JFrame object, which is the main window.
		f2=new JFrame(); 
		// Sets the title that appears in the window's title bar.
		f2.setTitle("Login"); 
		// Sets the layout manager for the frame to a 1x1 grid.
		f2.setLayout(new GridLayout(1,1)); 
		// Sets the initial size of the window to be very large (effectively fullscreen on many monitors).
		f2.setSize(2000,1550); 
		// Sets the default close operation, so the application terminates when the window is closed.
		f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		// --- MAIN PANEL (JPanel) SETUP ---
		// Creates a new JPanel, which will act as the main container for other components.
		panel1=new JPanel(); 
		// Sets a decorative etched border around the panel.
		panel1.setBorder(new EtchedBorder(EtchedBorder.LOWERED,null,null)); 
		// Sets the size of the panel (since layout is null, this is needed).
		panel1.setSize(1365,750); 
		// Sets the background color of the panel to light gray.
		panel1.setBackground(Color.lightGray);		
		// Sets the layout manager to null, allowing for absolute positioning of components using setBounds().
		panel1.setLayout(null); 
		// Adds the main panel to the frame.
		f2.add(panel1); 
		
		// --- IMAGE PANEL SETUP ---
		// Creates an anonymous inner class of JPanel to override its painting method for displaying an image.
		panel = new JPanel() {
            // Indicates that this method is overriding the paintComponent method from the JComponent superclass.
			@Override 
            // This method is called whenever the panel needs to be painted or repainted.
			protected void paintComponent(Graphics g) {
                // Calls the superclass's paintComponent method to ensure proper background painting.
				super.paintComponent(g); 
                // Checks if the backgroundImage has been successfully loaded and is not null.
				if (backgroundImage[0] != null) {
                    // Draws the loaded image onto the panel, scaling it to fill the panel's entire width and height.
					g.drawImage(backgroundImage[0], 0, 0, getWidth(), getHeight(), this);
                } // End of if statement.
            } // End of paintComponent method.
        }; // End of anonymous inner class definition.
		// Adds the custom image panel to the main container panel.
		panel1.add(panel); 
		// Sets the absolute position (x=440, y=40) and size (width=60, height=60) of the image panel.
		panel.setBounds(440, 40, 60, 60); 
		// Starts a try block to handle potential IOExceptions when reading the image file.
		try {
            // Reads the image file from the specified path and stores it in the backgroundImage array.
			backgroundImage[0] = ImageIO.read(new File(imageFilePath));
        // Catches any IOException that might occur if the file is not found or cannot be read.
		} catch (IOException e) { 
            // Prints the stack trace of the exception for debugging.
			e.printStackTrace(); 
            // Prints a custom error message to the console indicating which image failed to load.
			System.err.println("Error loading image from path: " + imageFilePath); 
        } // End of try-catch block.
		
		// --- LOGIN FORM COMPONENT SETUP ---
		// Creates the main title label with the text " Member Login ".
		logTitel =new JLabel(" Member Login ");
		// Sets the text color of the title to dark gray.
		logTitel.setForeground(Color.DARK_GRAY); 
		// Sets the font of the title to "Castellar", bold, and size 40.
		logTitel.setFont(new Font("Castellar",Font.BOLD,40)); 
		// Sets the position (x=500, y=0) and size (width=700, height=150) of the title label.
		logTitel.setBounds(500,00, 700, 150); 
		// Adds the title label to the main panel.
		panel1.add(logTitel); 
		
		// Creates a label for the username field.
		usernamelabel=new JLabel("User Name :- ");
		// Sets the font for the "User Name" label.
		usernamelabel.setFont(new Font("Times New Roman",Font.BOLD,18));
		// Sets the position and size for the "User Name" label.
		usernamelabel.setBounds(430, 220, 150, 25);
		// Adds the "User Name" label to the main panel.
		panel1.add(usernamelabel);
		
		// Creates a label for the password field.
		passwordlabel=new JLabel("Password :-");
		// Sets the font for the "Password" label.
		passwordlabel.setFont(new Font("Times New Roman",Font.BOLD,18));
		// Sets the position and size for the "Password" label.
		passwordlabel.setBounds(430, 300, 90, 25);
		// Adds the "Password" label to the main panel.
		panel1.add(passwordlabel);
		
		// Creates a text field for the user to enter their username.
		username=new JTextField();
		// Sets the position and size of the username text field.
		username.setBounds(580, 220, 220, 22);
		// Adds the username text field to the main panel.
		panel1.add(username);		
		
		// Creates a password field, which hides the typed characters.
		password=new JPasswordField();
		// Sets the position and size of the password field.
		password.setBounds(580, 300, 220, 22);
		// Adds the password field to the main panel.
		panel1.add(password);
		
		// Creates a button with the text "Login".
		btlog=new JButton("Login");
		// Sets the background color of the login button to blue.
		btlog.setBackground(Color.BLUE); 
		// Sets the text color of the login button to white.
		btlog.setForeground(Color.WHITE); 
		// Sets the position and size of the login button.
		btlog.setBounds(590, 435, 150, 25);
		// Adds the login button to the main panel.
		panel1.add(btlog);
		
		// --- ACTION LISTENER FOR LOGIN BUTTON ---
		// Adds an ActionListener to the login button to handle click events.
		btlog.addActionListener(new ActionListener() {
			// Specifies that this method overrides a method from the ActionListener interface.
			@Override
			// This method is executed when the login button is clicked.
			public void actionPerformed(ActionEvent e) {
				
				// Retrieves the text entered in the username field.
				String usernames = username.getText();
				// Retrieves the password from the password field as a char array and converts it to a String.
				String passwords = new String(password.getPassword());
				
				// --- INPUT VALIDATION ---
				// Checks if the username field is empty or contains only whitespace.
				if (usernames == null || usernames.isEmpty() || usernames.trim().isEmpty()) {
					// Shows a popup message dialog asking the user to enter a username.
					JOptionPane.showMessageDialog(null, "Please Enter UserName"); 
					// Sets the input focus back to the username field.
					username.requestFocus(); 
					// Exits the method to prevent further execution.
					return; 
				} // End of username validation.
				// Checks if the password field is empty or contains only whitespace.
				if (passwords == null || passwords.isEmpty() || passwords.trim().isEmpty()) {
					// Shows a popup message dialog asking the user to enter a password.
					JOptionPane.showMessageDialog(null, "Please Enter Password"); 
					// Sets the input focus back to the password field.
					password.requestFocus(); 
					// Exits the method to prevent further execution.
					return; 
				} // End of password validation.
				
				// --- AUTHENTICATION ---
				// Calls the loginvalidate method to check if the entered credentials exist in the database.
				if(loginvalidate(usernames,passwords))
				{
					// If login is successful, call the method to clear the username and password fields.
					clearlogin(); 
					// Shows a success message in a popup dialog.
					JOptionPane.showMessageDialog(null, "Login Successfull!","SUCCESS",JOptionPane.INFORMATION_MESSAGE); 
					// Hides the login window.
					f2.setVisible(false); 
					// Creates a new instance of the Admin_Home class, opening the admin homepage.
					new Admin_Home(); 
				}
				// Executes if the login validation fails.
				else
				{
					// Shows an error message in a popup dialog indicating invalid credentials.
					JOptionPane.showMessageDialog(null,"Invalid Username or Password ","Login Faild",JOptionPane.ERROR_MESSAGE);
				} // End of if-else block.								
			} // End of actionPerformed method.
		}); // End of ActionListener for login button.
		
		// Creates a button with the text "forgot Password ?".
		forgot=new JButton("forgot Password ?");
		// Sets the text color to magenta.
		forgot.setForeground(Color.MAGENTA); 
		// Sets the font for the button text.
		forgot.setFont(new Font("Times New Roman",Font.PLAIN,18)); 
		// Removes the default button border to make it look like a link.
		forgot.setBorder(BorderFactory.createEmptyBorder()); 
		// Makes the button's background transparent.
		forgot.setContentAreaFilled(false); 
		// Sets the position and size of the "forgot password" button.
		forgot.setBounds(590, 485, 150, 30);
		// Adds the button to the main panel.
		panel1.add(forgot);
		
		// --- ACTION LISTENER FOR FORGOT PASSWORD BUTTON ---
		// Adds an ActionListener to the "forgot password" button.
		forgot.addActionListener(new ActionListener() {
			// Specifies that this method is overriding the one from the ActionListener interface.
			@Override
			// This method is executed when the "forgot password" button is clicked.
			public void actionPerformed(ActionEvent e) {
				// Shows an error message dialog indicating the feature is not yet available.
				JOptionPane.showMessageDialog(null,"Sorry this facility is not available yet ","ForgetFaild",JOptionPane.ERROR_MESSAGE);
			} // End of actionPerformed method.
		}); // End of ActionListener for forgot password button.
			
		// Creates a button with the text "Sign up".
		btsign=new JButton("Sign up");
		// Sets the background color of the sign up button to blue.
		btsign.setBackground(Color.BLUE);
		// Sets the text color of the sign up button to white.
		btsign.setForeground(Color.white);
		// Sets the position and size of the sign up button.
		btsign.setBounds(590, 530, 150, 25);
		// Adds the sign up button to the main panel.
		panel1.add(btsign);

		// --- ACTION LISTENER FOR SIGN UP BUTTON ---
		// Adds an ActionListener to the sign up button.
		btsign.addActionListener(new ActionListener() {
			// Specifies that this method is overriding the one from the ActionListener interface.
			@Override
			// This method is executed when the sign up button is clicked.
			public void actionPerformed(ActionEvent e) {
			// Creates a new instance of the Signup_panel class, opening the sign up window.
			new	Signup_panel();
			// Hides the current login window.
			f2.setVisible(false);
			} // End of actionPerformed method.
		}); // End of ActionListener for sign up button.

		// Makes the entire frame and all its components visible to the user.
		f2.setVisible(true);
	} // End of loginfame method.
} // End of loginpain class.