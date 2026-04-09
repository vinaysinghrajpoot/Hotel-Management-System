package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

public class Signup_panel extends JFrame{
	
	/**
	 * */
	// A unique identifier for the class, required for serializable classes.
	private static final long serialVersionUID = 1L;
	
	// Declare JLabel components for the signup form fields and title.
	JLabel signupTitel,namel,email,contactnum,username,password,repassword;
	// Declare JPanels for layout and drawing the background image.
	JPanel panel2,panel;
	// Define the file path for the background image.
	String imageFilePath = "C:/Users/ACER/MY_J2SE/Hotel-Project/Img/sign-up.png";
	// An array to hold the background image, allowing it to be a final variable.
	final Image[] backgroundImage = {null};
	// Declare JTextField components for user input.
	JTextField nametf,emailtf,contactnumtf,uernametf,passwordtf,repasswordtf;
	// Declare JButton components for the signup and back actions.
	JButton signupbt,Signupback;
	
//	Database Connection
	// Declare Connection, PreparedStatement, and ResultSet objects for database operations.
  	Connection con = null;
  	PreparedStatement pst;
  	ResultSet rs;
	
//  	Create Connection 
  	// Method to establish a connection to the MySQL database.
  	public void Connection() {
		try {
			// Load the MySQL JDBC driver.
			Class.forName("com.mysql.jdbc.Driver");
			// Establish the connection to the 'hotel' database.
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel","root", "");
		} catch (Exception ex) {
			// Print the stack trace if a connection error occurs.
			ex.printStackTrace();
		}
	}
	
//  	Calling Frame and Connection;
	// The constructor for the Signup_panel class.
	public Signup_panel() {		
		// Call the method to set up the GUI frame.
		frame();
		// Call the method to establish the database connection.
		Connection();
	}
	
//	Create a Sign Up Clear text field method;
	// Method to clear all text fields in the signup form.
	public void signupclear() {
		
	nametf.setText("");
	emailtf.setText("");
	contactnumtf.setText("");
	uernametf.setText("");
	passwordtf.setText("");
	repasswordtf.setText("");
	// Set the focus back to the name text field after clearing.
	nametf.requestFocus();	
	}
	
//	Frame and Panel,
	// Method to set up the main JFrame and its components.
	public void frame() {
		
		// Set the frame title.
		setTitle("signup");
		// Use a GridLayout for a simple layout.
		setLayout(new GridLayout(1,1));
		// Set the frame size.
		setSize(2000,1550);
		// Set the default close operation.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Initialize the main panel (panel2).
		panel2=new JPanel();
		panel2.setBorder(new EtchedBorder(EtchedBorder.LOWERED,null,null));
		panel2.setSize(1365,750);
		panel2.setBackground(Color.LIGHT_GRAY);		
		// Set the layout to null for absolute positioning of components.
		panel2.setLayout(null);
		// Add the panel to the frame.
		add(panel2);
		
//		IMAGE,PNG
		// Initialize a custom JPanel (panel) to display the background image.
		panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                // Call the superclass's paintComponent to ensure proper rendering.
                super.paintComponent(g);
                // Draw the image on the panel if it's successfully loaded.
                try {
                    // Load the image from the specified path.
                    backgroundImage[0] = ImageIO.read(new File(imageFilePath));
                } catch (IOException e) {
                    e.printStackTrace();
                    // Print an error if the image cannot be loaded.
                    System.err.println("Error loading image from path: " + imageFilePath);
                }
                if (backgroundImage[0] != null) {
                    // Draw the image scaled to the panel's dimensions.
                    g.drawImage(backgroundImage[0], 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
		panel2.add(panel);
		panel.setBounds(350, 40, 60, 60);		
		
//		JLabels,		
		// Initialize and configure the "Member Sign Up" title label.
		signupTitel=new JLabel(" Member Sign Up");
		signupTitel.setForeground(Color.DARK_GRAY);
		signupTitel.setFont(new Font("Castellar",Font.BOLD,50));
		signupTitel.setBounds(400,00, 750, 150);
		panel2.add(signupTitel);
		
		// Initialize and configure the "Full Name" label.
		namel=new JLabel("Full Name :-");
		namel.setForeground(Color.BLACK);
		namel.setBounds(450, 180, 150, 30);
		namel.setFont(new Font("Times New Roman",Font.BOLD,18));
		panel2.add(namel);
		
		// Initialize and configure the "Email Id" label.
		email=new JLabel("Email Id :-");
		email.setForeground(Color.BLACK);
		email.setBounds(450, 235, 150, 30);
		email.setFont(new Font("Times New Roman",Font.BOLD,18));
		panel2.add(email);
		
		// Initialize and configure the "Contact num" label.
		contactnum=new JLabel("Contact num :-");
		contactnum.setForeground(Color.BLACK);
		contactnum.setBounds(450, 290, 150, 30);
		contactnum.setFont(new Font("Times New Roman",Font.BOLD,18));
		panel2.add(contactnum);
		
		// Initialize and configure the "User Name" label.
		username=new JLabel("User Name :-");
		username.setForeground(Color.BLACK);
		username.setBounds(450, 340, 150, 30);
		username.setFont(new Font("Times New Roman",Font.BOLD,18));
		panel2.add(username);
		
		// Initialize and configure the "Password" label.
		password=new JLabel("Password :-");
		password.setForeground(Color.BLACK);
		password.setBounds(450, 392, 150, 30);
		password.setFont(new Font("Times New Roman",Font.BOLD,18));
		panel2.add(password);
		
		// Initialize and configure the "Re Enter Password" label.
		repassword=new JLabel("Re Enter Password :-");
		repassword.setForeground(Color.BLACK);
		repassword.setBounds(450, 445, 170, 30);
		repassword.setFont(new Font("Times New Roman",Font.BOLD,18));
		panel2.add(repassword);
		
//		JTextField,
		// Initialize and configure the text field for the full name.
		nametf=new JTextField();
		nametf.setBounds(640, 185, 250, 22);  //165
		panel2.add(nametf);
		
		// Initialize and configure the text field for the email.
		emailtf=new JTextField();
		emailtf.setBounds(640, 240, 250, 22);  //210
		panel2.add(emailtf);
		
		// Initialize and configure the text field for the contact number.
		contactnumtf=new JTextField();
		contactnumtf.setBounds(640, 295, 250, 22);
		panel2.add(contactnumtf);
		
		// Initialize and configure the text field for the username.
		uernametf=new JTextField();  
		uernametf.setBounds(640, 345, 250, 22);
		panel2.add(uernametf);
		
		// Initialize and configure the text field for the password.
		passwordtf=new JTextField();
		passwordtf.setBounds(640, 395, 250, 22);
		panel2.add(passwordtf);
		
		// Initialize and configure the text field for re-entering the password.
		repasswordtf=new JTextField();
		repasswordtf.setBounds(640, 450, 250, 22);
		panel2.add(repasswordtf);
		
//		JButton,		
		// Initialize and configure the "Sign Up" button.
		signupbt=new JButton("Sign Up");
		signupbt.setForeground(Color.WHITE);
		signupbt.setBackground(Color.BLUE);
		signupbt.setBounds(600, 600, 150, 25);
		panel2.add(signupbt);
		
//		Button Action Performed;
		// Add an ActionListener to the "Sign Up" button.
		signupbt.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
			
				// Get the text from all the input fields.
				String name = nametf.getText();
				String email = emailtf.getText();
				String contact = contactnumtf.getText();
				String username= uernametf.getText();
				String password= passwordtf.getText();
				String repassword = repasswordtf.getText();
				
//				Create a Condections;
				// Validate that the name field is not empty.
				if (name == null || name.isEmpty() || name.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Name");
					nametf.requestFocus();
					return;
				}
				// Validate that the email field is not empty.
				if (email== null || email.isEmpty() || email.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Email");
					emailtf.requestFocus();
					return;
				}
				// Validate that the contact field is not empty.
				if (contact == null || contact.isEmpty() || contact.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Contact");
					contactnumtf.requestFocus();
					return;
				}
				// Validate that the username field is not empty.
				if (username == null || username.isEmpty() || username.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Username");
					uernametf.requestFocus();
					return;
				}
				// Validate that the password field is not empty.
				if (password == null || password.isEmpty() || password.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Password");
					passwordtf.requestFocus();
					return;
				}
				// Validate that the re-entered password field is not empty.
				if (repassword == null || repassword.isEmpty() || repassword.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please ReEnter Password");
					repasswordtf.requestFocus();
					return;
				}
//				Save the text field data into DBMS;
				try {
                	// Prepare the SQL statement for inserting data into the 'signup' table.
                	pst=con.prepareStatement("insert into signup(name,email,contact,userName,password,repassword)values(?,?,?,?,?,?)");                	                	
                	// Set the values for the prepared statement parameters.
                	pst.setString(1, name);
                	pst.setString(2, email);
                	pst.setString(3, contact);
                	pst.setString(4, username);
                	pst.setString(5, password);
                	pst.setString(6, repassword);
                	// Execute the update query.
                	pst.executeUpdate();
                	// Show a success message to the user.
                	JOptionPane.showMessageDialog(null, "Data insert Successfully");
                	// Clear the input fields.
                	signupclear();
                	// Create a new login panel and make it visible.
                new	loginpain();
    			// Hide the current signup frame.
    			setVisible(false);	
                	//Call here the Signup home panel ;
                }
                catch (SQLException e1) {
    				// Print the stack trace if a SQL exception occurs.
    				e1.printStackTrace();
    			}
			}
		});	
		
		// Initialize and configure the "Back" button.
		Signupback=new JButton("Back");
		Signupback.setForeground(Color.BLACK);
		Signupback.setFont(new Font("FangSong",Font.PLAIN,18));
		Signupback.setBorder(BorderFactory.createEmptyBorder());
		Signupback.setContentAreaFilled(false);
		Signupback.setBounds(1240, 30, 150, 30);
		panel2.add(Signupback);
		
//		Action Performed;
		// Add an ActionListener to the "Back" button.
		Signupback.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			// Create a new login panel.
			new	loginpain();
			// Hide the current signup frame.
			setVisible(false);	
			}
		});
		
		// Make the frame visible.
		setVisible(true);
		}
	}