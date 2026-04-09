//Specifies the package where this class belongs. It helps in organizing code.
package view;

//Import necessary classes for GUI, image handling, events, and SQL database connection.
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Utils.DatabaseUtils;// A utility class, likely for converting database results to a table model.

//The main class for the customer management window.
public class customer{

	// Declare all GUI components (window, panels, labels, text fields, etc.).
	 JFrame acf; // The main window frame.
	 JPanel panel4, panelsearch, panel; // Panels to organize components.
	 String imageFilePath = "C:/Users/ACER/MY_J2SE/Hotel-Project/Img/Customer.png"; // Path to an icon.
	 final Image[] backgroundImage = {null}; // Array to hold the loaded image.
	 JLabel acTitel, acname, acdob, accontact, acdoctype, acdocnum, acgender, acaddress, acroom_categary, acroom_type, accheckin_date, accheckout_date, Price;
	 private JTextField autoId, actfname, actfdob, acdobtxbox, actfcontact, actfdocnum, actfaddress, actfcheckin_date, accitxbox, actfcheckout_date, accotxbox, searchwartf, Pricetf;
	 JComboBox<String> doctype, room_type; // Drop-down menus.
	 JCheckBox Male, Female, Other; // Checkboxes for gender.
	 JRadioButton Ac, NonAc; // Radio buttons for room category.
	 ButtonGroup roomcatebg, genderbg; // Groups to ensure only one radio button/checkbox is selected.
	 private JTable ctable; // The table to display customer data.
	 JScrollPane scrollpane; // A scrollable container for the table.
	 JButton save, update, delete, acback, searchbutton; // Action buttons.

	 // Database connection objects.
	 Connection con = null;
	 PreparedStatement pst; // Used for executing parameterized SQL queries.
	 ResultSet rs; // Stores the result of a database query.

	 // Constructor: This method is called when a new 'customer' object is created.
	 public customer() {
	     frame(); // Initialize and set up the GUI frame.
	     Connection(); 
	     clear(); // Clear all input fields.
	     loadTable(); // Load customer data from the database into the table.
	 }

//	  Method to search for customers in the database.
	 public void Searchwar() {
	     try {
	         // Get the search term from the search text field, removing leading/trailing spaces.
	         String searchTerm = searchwartf.getText().trim();

	         // Check if the search field is empty.
	         if (searchTerm.isEmpty()) {
	             // If it's empty, prepare a query to select all customers.
	             pst = con.prepareStatement("SELECT * FROM Customer");
	         } else {
	             // If there is a search term, prepare a query to search by name, contact, or check-in date.
	             pst = con.prepareStatement("SELECT * FROM Customer WHERE fullname LIKE ? OR contactno = ? OR CheakinDate = ?");
	             // Set the parameters for the query.
	             pst.setString(1, "%" + searchTerm + "%"); // '%' allows for partial matches on the Full Name.
	             pst.setString(2, searchTerm); // Exact match for contact number.
	             pst.setString(3, searchTerm); // Exact match for check-in date.
	         }
	         // Execute the query.
	         rs = pst.executeQuery();
	         // Update the table with the search results using a helper utility.
	         ctable.setModel(DatabaseUtils.resultSetToTableModel(rs));
	     } catch (Exception e) {
	         // Print any error that occurs to the console.
	         e.printStackTrace();
	         }
	     }
	 
//	 Method to clear all input fields and reset selections.
	 public void clear() {
		 autoId.setText(""); // Clear the hidden ID field.
		 actfname.setText(""); // Clear the full name field.
		 actfdob.setText(""); // Clear the date of birth field.
		 actfcontact.setText(""); // Clear the contact number field.
		 doctype.setSelectedIndex(0); // Reset the document type dropdown to the first item.
		 room_type.setSelectedIndex(0); // Reset the room type dropdown.
		 actfdocnum.setText(""); // Clear the document number field.
		 genderbg.clearSelection(); // Deselect all gender checkboxes.
		 actfaddress.setText(""); // Clear the address field.
		 roomcatebg.clearSelection(); // Deselect all room category radio buttons.
		 actfcheckin_date.setText(""); // Clear the check-in date field.
		 actfcheckout_date.setText(""); // Clear the checkout date field.
		 searchwartf.setText(""); // Clear the search field.
		 Pricetf.setText(""); // Clear the price field.
		}
	 
//	 Create Connection
		public void Connection() {
		try{
	         // Load the MySQL JDBC driver.
			Class.forName("com.mysql.jdbc.Driver");
	         // Define the connection URL, username, and password Establish the connection.
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel","root","");
		}catch(Exception ex){
	         // If connection fails, print the error and show an error message dialog.
			ex.printStackTrace();
	         JOptionPane.showMessageDialog(null, "Database Connection Failed: " + ex.getMessage());
		    }
		}
		
//	  Method to load all customer data from the database into the JTable.
	 public void loadTable() {
	     try {
	         // Prepare the SQL statement to select all records from the 'Customer' table.
	         pst = con.prepareStatement("Select * from Customer");
	         // Execute the query.
	         rs = pst.executeQuery();
	         // Use the utility to set the table's data model from the ResultSet.
	         ctable.setModel(DatabaseUtils.resultSetToTableModel(rs));
	         // Set a background color for the table.
	         ctable.setBackground(Color.orange);
	     } catch (Exception e) {
	         // Print any error that occurs.
	         e.printStackTrace();
	     }
	 }
			
//	 This is the main method that builds the entire graphical user interface.
	 public void frame() {
	     // --- FRAME SETUP ---
	     acf = new JFrame("Add Customer"); // Create the main window with a title.
	     acf.setLayout(new GridLayout(1, 1)); // Set a simple grid layout.
	     acf.setSize(2000, 1550); // Set the size of the window.
	     acf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensure the application closes when the window is closed.

	     // --- MAIN PANEL SETUP ---
	     panel4 = new JPanel(); // Create the main panel to hold all components.
	     panel4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null)); // Add a border.
	     panel4.setSize(1365, 750); // Set the size of the panel.
	     panel4.setBackground(Color.lightGray); // Set the background color.
	     panel4.setLayout(null); // Use absolute positioning for components (not recommended, but used here).
	     acf.add(panel4); // Add the panel to the frame.

	     // --- ICON PANEL SETUP ---
	     // Create a custom panel that can draw an image.
	     panel = new JPanel() {
	         @Override
	         protected void paintComponent(Graphics g) {
	             super.paintComponent(g); // Call the parent method to handle default painting.
	             if (backgroundImage[0] != null) { // Check if the image was loaded.
	                 // Draw the image, scaled to fit the panel's dimensions.
	                 g.drawImage(backgroundImage[0], 0, 0, getWidth(), getHeight(), this);
	             }
	         }
	     };
	     panel4.add(panel); // Add the image panel to the main panel.
	     panel.setBounds(450, 40, 60, 60); // Set its position and size.
	     try {
	         // Load the image from the specified file path.
	         backgroundImage[0] = ImageIO.read(new File(imageFilePath));
	     } catch (IOException e) {
	         // If the image can't be loaded, print an error.
	         e.printStackTrace();
	         System.err.println("Error loading image from path: " + imageFilePath);
	     }

	     // --- SEARCH PANEL SETUP ---
	     panelsearch = new JPanel(); // Panel for the search bar and button.
	     panelsearch.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null)); // Add a border.
	     panelsearch.setBounds(990, 120, 320, 40); // Set its position and size.
	     panelsearch.setBackground(Color.red); // Set background color.
	     panelsearch.setLayout(null); // Use absolute positioning.
	     panel4.add(panelsearch); // Add it to the main panel.

	     // --- LABELS, TEXTFIELDS, AND OTHER INPUT COMPONENTS ---
	     // Title Label
	     acTitel = new JLabel("Add Customer");
	     acTitel.setForeground(Color.DARK_GRAY);
	     acTitel.setFont(new Font("Castellar", Font.BOLD, 40));
	     acTitel.setBounds(530, 0, 500, 150);
	     panel4.add(acTitel);

	     // Customer Name
	     acname = new JLabel("Customer Full Name:- ");
	     acname.setFont(new Font("Times New Roman", Font.BOLD, 18));
	     acname.setBounds(20, 195, 180, 25);
	     panel4.add(acname);

	     actfname = new JTextField(20);
	     actfname.setBounds(250, 200, 240, 20);
	     panel4.add(actfname);

	     // Date of Birth
	     acdob = new JLabel("Date of Birth:- ");
	     acdob.setFont(new Font("Times New Roman", Font.BOLD, 18));
	     acdob.setBounds(20, 225, 180, 25);
	     panel4.add(acdob);

	     actfdob = new JTextField(20);
	     actfdob.setBounds(250, 230, 160, 20);
	     panel4.add(actfdob);

	     acdobtxbox = new JTextField("YYYY-MM-DD"); // A hint for the date format.
	     acdobtxbox.setEditable(false); // Make it non-editable.
	     acdobtxbox.setBounds(410, 230, 80, 20);
	     panel4.add(acdobtxbox);

	     // Contact Number
	     accontact = new JLabel("Contact Number:- ");
	     accontact.setFont(new Font("Times New Roman", Font.BOLD, 18));
	     accontact.setBounds(20, 255, 180, 25);
	     panel4.add(accontact);

	     actfcontact = new JTextField(20);
	     actfcontact.setBounds(250, 260, 240, 20);
	     panel4.add(actfcontact);

	     // Document Type (Dropdown)
	     acdoctype = new JLabel("Document Type:- ");
	     acdoctype.setFont(new Font("Times New Roman", Font.BOLD, 18));
	     acdoctype.setBounds(20, 285, 180, 25);
	     panel4.add(acdoctype);

	     doctype = new JComboBox<>(new String[]{"Please Select Document Type", "Aadhar card", "Pan Card", "Voter Id", "Driving Lisense"});
	     doctype.setSelectedIndex(0);
	     doctype.setBounds(250, 290, 240, 20);
	     panel4.add(doctype);

	     // Document Number
	     acdocnum = new JLabel("Document Number:- ");
	     acdocnum.setFont(new Font("Times New Roman", Font.BOLD, 18));
	     acdocnum.setBounds(20, 315, 180, 25);
	     panel4.add(acdocnum);

	     actfdocnum = new JTextField(20);
	     actfdocnum.setBounds(250, 320, 240, 20);
	     panel4.add(actfdocnum);

	     // Gender (Checkboxes)
	     acgender = new JLabel("Gender:- ");
	     acgender.setFont(new Font("Times New Roman", Font.BOLD, 18));
	     acgender.setBounds(20, 345, 180, 25);
	     panel4.add(acgender);

	     Male = new JCheckBox("Male");
	     Male.setBounds(250, 350, 70, 22);
	     Male.setBackground(null); // Make background transparent.
	     panel4.add(Male);

	     Female = new JCheckBox("Female");
	     Female.setBounds(330, 350, 70, 22);
	     Female.setBackground(null);
	     panel4.add(Female);

	     Other = new JCheckBox("Other");
	     Other.setBounds(420, 350, 70, 22);
	     Other.setBackground(null);
	     panel4.add(Other);

	     genderbg = new ButtonGroup(); // Create a group for gender checkboxes.
	     genderbg.add(Male);
	     genderbg.add(Female);
	     genderbg.add(Other);

	     // Full Address
	     acaddress = new JLabel("Full Address:- ");
	     acaddress.setFont(new Font("Times New Roman", Font.BOLD, 18));
	     acaddress.setBounds(20, 375, 180, 25);
	     panel4.add(acaddress);

	     actfaddress = new JTextField(20);
	     actfaddress.setBounds(250, 380, 240, 20);
	     panel4.add(actfaddress);

	     // Room Category (Radio Buttons)
	     acroom_categary = new JLabel("Room Category:- ");
	     acroom_categary.setFont(new Font("Times New Roman", Font.BOLD, 18));
	     acroom_categary.setBounds(20, 405, 180, 25);
	     panel4.add(acroom_categary);

	     Ac = new JRadioButton("Ac");
	     Ac.setBackground(null);
	     Ac.setBounds(290, 410, 50, 30);
	     panel4.add(Ac);

	     NonAc = new JRadioButton("Non Ac");
	     NonAc.setBackground(null);
	     NonAc.setBounds(380, 410, 80, 30);
	     panel4.add(NonAc);

	     roomcatebg = new ButtonGroup(); // Group for radio buttons to ensure only one is selected.
	     roomcatebg.add(Ac);
	     roomcatebg.add(NonAc);

	     // Room Type (Dropdown)
	     acroom_type = new JLabel("Room Type:- ");
	     acroom_type.setFont(new Font("Times New Roman", Font.BOLD, 18));
	     acroom_type.setBounds(20, 435, 180, 25);
	     panel4.add(acroom_type);

	     room_type = new JComboBox<>(new String[]{"Please Select Room Type", "Single bed", "Dauble bed", "Triple bed", "Suit", "Deluxe"});
	     room_type.setBounds(250, 440, 240, 20);
	     panel4.add(room_type);

	     // Check-in Date
	     accheckin_date = new JLabel("Checkin Date:- ");
	     accheckin_date.setFont(new Font("Times New Roman", Font.BOLD, 18));
	     accheckin_date.setBounds(20, 465, 180, 25);
	     panel4.add(accheckin_date);

	     actfcheckin_date = new JTextField(20);
	     actfcheckin_date.setBounds(250, 470, 160, 20);
	     panel4.add(actfcheckin_date);

	     accitxbox = new JTextField("YYYY-MM-DD"); // Date format hint.
	     accitxbox.setEditable(false);
	     accitxbox.setBounds(410, 470, 80, 20);
	     panel4.add(accitxbox);

	     // Checkout Date
	     accheckout_date = new JLabel("Checkout Date:- ");
	     accheckout_date.setFont(new Font("Times New Roman", Font.BOLD, 18));
	     accheckout_date.setBounds(20, 495, 180, 25);
	     panel4.add(accheckout_date);

	     actfcheckout_date = new JTextField(20);
	     actfcheckout_date.setBounds(250, 500, 160, 20);
	     panel4.add(actfcheckout_date);

	     accotxbox = new JTextField("YYYY-MM-DD"); // Date format hint.
	     accotxbox.setEditable(false);
	     accotxbox.setBounds(410, 500, 80, 20);
	     panel4.add(accotxbox);

	     // Price
	     Price = new JLabel("Price:- ");
	     Price.setFont(new Font("Times New Roman", Font.BOLD, 18));
	     Price.setBounds(20, 525, 180, 25);
	     panel4.add(Price);

	     Pricetf = new JTextField(20);
	     Pricetf.setBounds(250, 530, 240, 20);
	     panel4.add(Pricetf);

	     // Hidden AutoId field to store the primary key of the selected customer.
	     autoId = new JTextField(20);
	     autoId.setEditable(false); // User cannot edit this.
	     autoId.setBounds(250, 170, 240, 20);
	     panel4.add(autoId);
	     autoId.setVisible(false); // Make it invisible.

	     // Search text field (part of the search panel).
	     searchwartf = new JTextField(12);
	     searchwartf.setBounds(20, 9, 180, 20);
	     panelsearch.add(searchwartf);

	     // --- TABLE SETUP ---
	     scrollpane = new JScrollPane(); // Create a scroll pane.
	     scrollpane.setBounds(500, 170, 850, 420); // Set its position and size.
	     panel4.add(scrollpane); // Add it to the main panel.

	     ctable = new JTable(); // Create the table.
	     ctable.setDefaultEditor(Object.class, null); // Make cells non-editable by default.
	     // Add a mouse listener to handle clicks on table rows.
	     ctable.addMouseListener(new MouseAdapter() {
	         @Override
	         public void mouseClicked(MouseEvent e) {
	             // Get the index of the clicked row.
	             int index = ctable.getSelectedRow();
	             // Get the table's data model.
	             TableModel model = ctable.getModel();

	             // Populate all form fields with data from the selected row.
	             autoId.setText(model.getValueAt(index, 0).toString());
	             actfname.setText(model.getValueAt(index, 1).toString());
	             actfdob.setText(model.getValueAt(index, 2).toString());
	             actfcontact.setText(model.getValueAt(index, 3).toString());
	             doctype.setSelectedItem(model.getValueAt(index, 4).toString());
	             actfdocnum.setText(model.getValueAt(index, 5).toString());
	             
	             // Handle gender checkboxes.
	             Male.setSelected(false);
	             Female.setSelected(false);
	             Other.setSelected(false);
	             String genderValue = model.getValueAt(index, 6).toString();
	             if(genderValue.equalsIgnoreCase("Male")) Male.setSelected(true);
	             else if(genderValue.equalsIgnoreCase("Female")) Female.setSelected(true);
	             else if(genderValue.equalsIgnoreCase("Other")) Other.setSelected(true);

	             actfaddress.setText(model.getValueAt(index, 7).toString());

	             // Handle room category radio buttons.
	             String croomcate = model.getValueAt(index, 8).toString();
	             if(croomcate.equalsIgnoreCase("Ac")) Ac.setSelected(true);
	             else if(croomcate.equalsIgnoreCase("Non Ac")) NonAc.setSelected(true);
	             
	             // Set dropdowns and text fields.
	             room_type.setSelectedItem(model.getValueAt(index, 9).toString());
	             actfcheckin_date.setText(model.getValueAt(index, 10).toString());
	             actfcheckout_date.setText(model.getValueAt(index, 11).toString());
	             Pricetf.setText(model.getValueAt(index, 12).toString());
	         }
	     });
	     ctable.setFont(new Font("Tahoma", Font.PLAIN, 14)); // Set font for table content.
	     ctable.setRowHeight(30); // Set the height of each row.
	     scrollpane.setViewportView(ctable); // Place the table inside the scroll pane.

	     // --- BUTTONS and ACTION LISTENERS ---
	     // Save Button
	     save = new JButton("Save");
	     save.setBackground(Color.green);
	     save.setForeground(Color.white);
	     save.setBounds(40, 620, 120, 25);
	     panel4.add(save);
	     // Action to perform when "Save" is clicked.
	     save.addActionListener(new ActionListener() {
	         @Override
	         public void actionPerformed(ActionEvent e) {
	             // Get data from all input fields.
	             String fullname = actfname.getText();
	             String dob = actfdob.getText();
	             String contactno = actfcontact.getText();
	             String documtype = (String) doctype.getSelectedItem();
	             String documentno = actfdocnum.getText();
	             String gender = null;
	             if (Male.isSelected()) gender = Male.getText();
	             else if (Female.isSelected()) gender = Female.getText();
	             else if (Other.isSelected()) gender = Other.getText();
	             String caddress = actfaddress.getText();
	             String croomcategory = null;
	             if (Ac.isSelected()) croomcategory = Ac.getText();
	             else if (NonAc.isSelected()) croomcategory = NonAc.getText();
	             String Roomtype = (String) room_type.getSelectedItem();
	             String cheakinDate = actfcheckin_date.getText();
	             String cheakoutDate = actfcheckout_date.getText();
	             String Price = Pricetf.getText();
	             
	             // Input validation.
	             if (fullname == null || fullname.isEmpty() || fullname.trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Please Add Customer Name");
						actfname.requestFocus();
						return;
					}
					if (dob == null || dob.isEmpty() || dob.trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Please Add Date Of Birth");
						actfdob.requestFocus();
						return;
					}
					if (contactno == null || contactno.isEmpty() || contactno.trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Please Add Contact Number");
						actfcontact.requestFocus();
						return;
					}
					if ( documtype== "Please Select Document Type" ){
						JOptionPane.showMessageDialog(null, "Please Select Document Type");
						doctype.requestFocus();
						return;
					}
					if (documentno == null || documentno .isEmpty() || documentno.trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Please Add Document Number");
						actfdocnum.requestFocus();
						return;
					}
					if(gender == null){
						JOptionPane.showMessageDialog(null, "Please select Gender");
						return;					
					}
					if (caddress == null || caddress.isEmpty() || caddress.trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Please Add Full Address");
						actfaddress.requestFocus();
						return;
					}
					if (croomcategory == null) {
						JOptionPane.showMessageDialog(null, "Please Select Room Category");
						return;
					}
					if ( Roomtype == "Please Select Room Type" ){
						JOptionPane.showMessageDialog(null, "Please Select Room Type");
						room_type.requestFocus();
						return;
					}
					if (cheakinDate == null || cheakinDate.isEmpty() || cheakinDate.trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Please Add Checkin Date");
						actfcheckin_date.requestFocus();
						return;
					}
					if (cheakoutDate == null || cheakoutDate.isEmpty() ||cheakoutDate.trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Please Add Checkout Date");
						actfcheckout_date.requestFocus();
						return;
					}

	             try {
	                 // Create the SQL INSERT statement.
	                 String sql = "insert into Customer(fullname,dob,contactno,documtype,documentno,gender,address,roomcategory,Roomtype,CheakinDate,CheakoutDate,Price)values(?,?,?,?,?,?,?,?,?,?,?,?)";
	                 pst = con.prepareStatement(sql);
	                 // Set the values for the placeholders '?'.
	                 pst.setString(1, fullname);
	                 pst.setString(2, dob);
	                 pst.setString(3, contactno);
	                 pst.setString(4, documtype);
	                 pst.setString(5, documentno);
	                 pst.setString(6, gender);
	                 pst.setString(7, caddress);
	                 pst.setString(8, croomcategory);
	                 pst.setString(9, Roomtype);
	                 pst.setString(10, cheakinDate);
	                 pst.setString(11, cheakoutDate);
	                 pst.setString(12, Price);
	                 pst.executeUpdate(); // Execute the insert command.
	                 clear(); // Clear the form.
	                 loadTable(); // Refresh the table.
	                 JOptionPane.showMessageDialog(null, "Data Inserted Successfully");
	             } catch (SQLException e1) {
	                 e1.printStackTrace();
	                 JOptionPane.showMessageDialog(null, "Failed to Save Customer: " + e1.getMessage());
	             }
	         }
	     });
	     
		update=new JButton("Update");
		update.setBackground(Color.BLUE);
		update.setForeground(Color.white);
		update.setBounds(200, 620, 120, 25);
		panel4.add(update);
		update.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
//				 Get all data from fields, including the hidden ID.
				String id = autoId.getText();
   				String fullname = actfname.getText();
	            String dob = actfdob.getText();
	            String contactno = actfcontact.getText();
	            String documtype = (String) doctype.getSelectedItem();
	            String documentno = actfdocnum.getText();
	            String gender = null;
	            if (Male.isSelected()) gender = Male.getText();
	            else if (Female.isSelected()) gender = Female.getText();
	            else if (Other.isSelected()) gender = Other.getText();
	            String caddress = actfaddress.getText();
	            String croomcategory = null;
	            if (Ac.isSelected()) croomcategory = Ac.getText();
	            else if (NonAc.isSelected()) croomcategory = NonAc.getText();
	            String Roomtype = (String) room_type.getSelectedItem();
	            String cheakinDate = actfcheckin_date.getText();
	            String cheakoutDate = actfcheckout_date.getText();
	            String Price = Pricetf.getText();

					if (fullname == null || fullname.isEmpty() || fullname.trim().isEmpty()) {
							JOptionPane.showMessageDialog(null, "Please Add Customer Name");
							actfname.requestFocus();
							return;
						}
					if (dob == null || dob.isEmpty() || dob.trim().isEmpty()) {
							JOptionPane.showMessageDialog(null, "Please Add Date Of Birth");
							actfdob.requestFocus();
							return;
						    }
					if (contactno == null || contactno.isEmpty() || contactno.trim().isEmpty()) {
							JOptionPane.showMessageDialog(null, "Please Add Contact Number");
							actfcontact.requestFocus();
							return;
						}
					if ( documtype== "Please Select Document Type" ){
							JOptionPane.showMessageDialog(null, "Please Select Document Type");
							doctype.requestFocus();
							return;
						}
					if (documentno == null || documentno .isEmpty() || documentno.trim().isEmpty()) {
							JOptionPane.showMessageDialog(null, "Please Add Document Number");
							actfdocnum.requestFocus();
							return;
						}
					if(gender == null){
							JOptionPane.showMessageDialog(null, "Please select Gender");
							return;					
						}
					if (caddress == null || caddress.isEmpty() || caddress.trim().isEmpty()) {
							JOptionPane.showMessageDialog(null, "Please Add Full Address");
							actfaddress.requestFocus();
							return;
						}
					if (croomcategory == null) {
							JOptionPane.showMessageDialog(null, "Please Select Room Category");
							return;
						}
					if ( Roomtype == "Please Select Room Type" ){
							JOptionPane.showMessageDialog(null, "Please Select Room Type");
							room_type.requestFocus();
							return;
						}
					if (cheakinDate == null || cheakinDate.isEmpty() || cheakinDate.trim().isEmpty()) {
							JOptionPane.showMessageDialog(null, "Please Add Checkin Date");
							actfcheckin_date.requestFocus();
							return;
						}
					if (cheakoutDate == null || cheakoutDate.isEmpty() ||cheakoutDate.trim().isEmpty()) {
							JOptionPane.showMessageDialog(null, "Please Add Checkout Date");
							actfcheckout_date.requestFocus();
							return;
						}
					try {
						// Create the SQL UPDATE statement with a WHERE clause to specify which record to update.
						pst = con.prepareStatement("UPDATE Customer SET fullname=?, dob=?, contactno=?, documtype=?, documentno=?, gender=?, address=?, roomcategory=?, Roomtype=?, CheakinDate=?, CheakoutDate=? ,Price=? WHERE autoId=?");
						// Set parameters for the update.
						pst.setString(1, fullname);
	                	pst.setString(2, dob);
	                	pst.setString(3, contactno);
	                	pst.setString(4, documtype);
	                	pst.setString(5, documentno);
	                	pst.setString(6, gender);
	                	pst.setString(7, caddress);
	                	pst.setString(8, croomcategory);
	                	pst.setString(9, Roomtype);
	                	pst.setString(10, cheakinDate);
	                	pst.setString(11, cheakoutDate);
	                    // (Set all other parameters...)
	                	pst.setString(12, Price);
	                	pst.setString(13, id);  // The ID is the last parameter for the WHERE clause.
	                	pst.executeUpdate();  // Execute the update.
	                	clear(); // Clear the form.
		                loadTable(); // Refresh the table.
	                	JOptionPane.showMessageDialog(null, "Data insert Success");
	                }
	                catch (SQLException e1) {
	    				e1.printStackTrace();
	    			}				
				}			 
		});

		 // Delete Button
	     delete = new JButton("Delete");
	     delete.setBackground(Color.red);
	     delete.setForeground(Color.white);
	     delete.setBounds(340, 620, 120, 25);
	     panel4.add(delete);
	     // Action to perform when "Delete" is clicked.
	     delete.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	             String autoid = autoId.getText();
	             // Check if an ID is present (meaning a row is selected).
	             if (!autoid.isEmpty()) {
	                 // Ask for confirmation before deleting.
	                 int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?", "Delete Confirmation",
	                		 JOptionPane.YES_NO_OPTION);
	                 if (result == JOptionPane.YES_OPTION) {
	                     try {
	                         // Prepare the DELETE statement.
	                         pst = con.prepareStatement("delete from customer where autoId=?");
	                         pst.setString(1, autoid);
	                         pst.executeUpdate();
	                         JOptionPane.showMessageDialog(null, "Data Deleted Successfully");
	                         clear();
	                         loadTable();
	                     } catch (SQLException e1) {
	                         e1.printStackTrace();
	                         JOptionPane.showMessageDialog(null, "Failed to delete Customer: " + e1.getMessage());
	                     }
	                 }
	             }
	         }
	     });
	     
	  // Back Button
	     acback = new JButton("Back");
	     acback.setForeground(Color.BLACK);
	     acback.setFont(new Font("FangSong", Font.PLAIN, 18));
	     acback.setBorder(BorderFactory.createEmptyBorder()); // Remove button border.
	     acback.setContentAreaFilled(false); // Make background transparent.
	     acback.setBounds(1240, 30, 150, 30);
	     panel4.add(acback);
	     acback.addActionListener(new ActionListener() {
	         @Override
	         public void actionPerformed(ActionEvent e) {
	             new Admin_Home(); // Create an instance of the admin home screen.
	             acf.setVisible(false); // Hide the current customer window.
	         }
	     });

	     // Search Button
	     searchbutton = new JButton("Search");
	     searchbutton.setForeground(Color.BLACK);
	     searchbutton.setFont(new Font("FangSong", Font.PLAIN, 18));
	     searchbutton.setBorder(BorderFactory.createEmptyBorder());
	     searchbutton.setBackground(Color.blue);
	     searchbutton.setBounds(220, 8, 80, 20);
	     panelsearch.add(searchbutton);
	     // Action for the search button.
	     searchbutton.addActionListener(new ActionListener() {
	         @Override
	         public void actionPerformed(ActionEvent e) {
	             Searchwar(); // Call the search method.
	             clear(); // Clear the form fields after search.
	         }
	     });

	     acf.setVisible(true); // Make the entire window visible.
	     }
	}