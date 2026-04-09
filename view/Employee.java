package view;

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
import java.util.Enumeration;
import Utils.DatabaseUtils; // Assuming this is a utility class for database operations

public class Employee {

    // --- GUI Components ---
    JFrame emf; // Main frame for the Employee management window
    JPanel panel7, panel, panelsearch; // Panels for layout and background image
    // Path to the employee image
    String imageFilePath = "C:/Users/ACER/MY_J2SE/Hotel-Project/Img/Emploee.png";
    // Array to hold the background image
    final Image[] backgroundImage = {null};
    // Labels for employee information fields
    JLabel titel, id, fullname, father_name, gender, dob, contact_num, doctype, doc_num, address, joining_date, positon, salary;
    // Text fields for employee data input and display
    JTextField autoidtf, idtf, fullnametf, father_nametf, dobtf, dobtypetf, contact_notf, doc_numtf, addresstf, joining_datetf, positontf, salarytf, searchwartf;
    // Combo box for document type selection
    JComboBox<String> doctypecb;
    // Check boxes for gender selection
    JCheckBox Male, Female, Other;
    // Button group to ensure only one gender is selected
    ButtonGroup genderbg;
    // Buttons for actions: save, delete, update, back, search
    JButton save, delete, update, back, searchbutton;
    // Table to display employee records
    private JTable emptable;
    // Scroll pane for the employee table
    JScrollPane ScrollPane;

    // --- Database Connectivity ---
    Connection con = null; // Database connection object
    PreparedStatement pst; // Prepared statement for SQL queries
    ResultSet rs; // Result set to hold query results

    /**
     * Constructor for the Employee class.
     * Initializes the GUI, establishes a database connection, clears input fields,
     * and loads employee data into the table.
     */
    public Employee() {
        frame(); // Set up the main frame and its components
        Connection(); // Establish the database connection
        clear(); // Clear all input fields initially
        loadTable(); // Load existing employee data into the table
    }

    /**
     * Clears all the input fields and resets selections in the form.
     */
    public void clear() {
        autoidtf.setText(""); // Clear the auto-generated ID field (usually hidden)
        idtf.setText(""); // Clear the employee ID field
        fullnametf.setText(""); // Clear the full name field
        father_nametf.setText(""); // Clear the father's name field

        // Clear the gender selection
        genderbg.clearSelection();

        // Clear text fields
        dobtf.setText(""); // Clear the date of birth field
        contact_notf.setText(""); // Clear the contact number field

        // Reset the document type combo box to the default "Please Select" option
        if (doctypecb.getItemCount() > 0) {
            doctypecb.setSelectedIndex(0); // Reset combo box to the first item
            doctypecb.repaint(); // Repaint to reflect the change
        }

        // Clear other text fields
        doc_numtf.setText(""); // Clear the document number field
        addresstf.setText(""); // Clear the address field
        joining_datetf.setText(""); // Clear the joining date field
        positontf.setText(""); // Clear the position field
        salarytf.setText(""); // Clear the salary field
    }

    /**
     * Establishes a connection to the MySQL database.
     * Loads the JDBC driver and attempts to connect using predefined credentials.
     * Displays an error message if the connection fails.
     */
    public void Connection() {
        try {
            // Load the MySQL JDBC driver.
            Class.forName("com.mysql.jdbc.Driver");
            // Define the connection URL, username, and password. Establish the connection.
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root", "");
        } catch (Exception ex) {
            // If connection fails, print the error to the console and show an error message dialog.
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Connection Failed: " + ex.getMessage());
        }
    }

    /**
     * Loads all employee records from the database into the JTable.
     * Uses a helper utility `DatabaseUtils.resultSetToTableModel` to convert
     * the ResultSet into a TableModel.
     */
    public void loadTable() {
        try {
            pst = con.prepareStatement("Select * from employee"); // SQL query to select all from employee table
            rs = pst.executeQuery(); // Execute the query
            emptable.setBackground(Color.ORANGE); // Set a background color for the table
            // Set the table model using the data from the ResultSet
            emptable.setModel(DatabaseUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace(); // Print any exceptions during table loading
        }
    }

    /**
     * Searches for employees in the database based on the input in the search text field.
     * It supports searching by employee name (partial match), father's name (exact match),
     * or contact number (exact match). If the search field is empty, it displays all records.
     */
    public void Searchwar() {
        try {
            // Get the search term from the search text field and remove leading/trailing spaces.
            String searchTerm = searchwartf.getText().trim();

            // Check if the search field is empty.
            if (searchTerm.isEmpty()) {
                // If it's empty, prepare a query to select all employees.
                pst = con.prepareStatement("SELECT * FROM employee");
            } else {
                // If there is a search term, prepare a query to search by name, father's name, or contact number.
                // The LIKE operator with '%' allows for partial matches on the 'name' field.
                // Exact matches are used for 'fname' and 'contact'.
                pst = con.prepareStatement("SELECT * FROM employee WHERE name LIKE ? OR fname = ? OR contact = ?");
                // Set the parameters for the prepared statement.
                pst.setString(1, "%" + searchTerm + "%"); // For partial name match
                pst.setString(2, searchTerm); // For exact father's name match
                pst.setString(3, searchTerm); // For exact contact number match
            }
            // Execute the query.
            rs = pst.executeQuery();
            // Update the table model with the search results.
            emptable.setModel(DatabaseUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            // Print any errors encountered during the search operation.
            e.printStackTrace();
        }
    }

    /**
     * Sets up the main JFrame and all its Swing components, including labels,
     * text fields, buttons, combo boxes, checkboxes, and the table.
     * Also configures event listeners for interactive elements.
     */
    public void frame() {

        emf = new JFrame("Employee"); // Create the main frame with a title
        emf.setLayout(new GridLayout(1, 1)); // Set the layout manager to a single grid cell
        emf.setSize(2000, 1550); // Set the frame size
        emf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation

        // --- Main Panel Setup ---
        panel7 = new JPanel();
        panel7.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null)); // Set an etched border
        panel7.setSize(1365, 750); // Set the panel size
        panel7.setBackground(Color.lightGray); // Set background color
        panel7.setLayout(null); // Use absolute positioning for components
        emf.add(panel7); // Add the main panel to the frame

        // --- Background Image Panel ---
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); // Call the superclass's paintComponent for proper rendering
                // Draw the background image if it has been loaded
                if (backgroundImage[0] != null) {
                    // Draw the image, scaling it to fit the panel's dimensions
                    g.drawImage(backgroundImage[0], 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        panel7.add(panel);
        panel.setBounds(450, 40, 60, 60); // Position the image panel
        try {
            // Load the background image from the specified file path
            backgroundImage[0] = ImageIO.read(new File(imageFilePath));
        } catch (IOException e) {
            e.printStackTrace(); // Print error if image loading fails
            System.err.println("Error loading image from path: " + imageFilePath);
        }

        // --- Title Label ---
        titel = new JLabel("Add Employee");
        titel.setForeground(Color.DARK_GRAY); // Set text color
        titel.setFont(new Font("Castellar", Font.BOLD, 40)); // Set font style and size
        titel.setBounds(530, 00, 700, 150); // Position the title label
        panel7.add(titel);

        // --- Search Panel ---
        panelsearch = new JPanel();
        panelsearch.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null)); // Set border
        panelsearch.setBounds(990, 120, 320, 40); // Position the search panel
        panelsearch.setBackground(Color.red); // Set background color
        panelsearch.setLayout(null); // Use absolute positioning
        panel7.add(panelsearch);

        // --- Employee Information Labels ---
        id = new JLabel("Give Employee Id:-");
        id.setFont(new Font("Garamond", Font.BOLD, 18));
        id.setBounds(20, 170, 230, 25);
        panel7.add(id);

        fullname = new JLabel("Full Name:-");
        fullname.setFont(new Font("Garamond", Font.BOLD, 18));
        fullname.setBounds(20, 200, 230, 25);
        panel7.add(fullname);

        father_name = new JLabel("Father Name:-");
        father_name.setFont(new Font("Garamond", Font.BOLD, 18));
        father_name.setBounds(20, 230, 230, 25);
        panel7.add(father_name);

        gender = new JLabel("Gender:-");
        gender.setFont(new Font("Garamond", Font.BOLD, 18));
        gender.setBounds(20, 260, 230, 25);
        panel7.add(gender);

        dob = new JLabel("Date of Birth:-  ");
        dob.setFont(new Font("Garamond", Font.BOLD, 18));
        dob.setBounds(20, 290, 230, 25);
        panel7.add(dob);

        contact_num = new JLabel("Contact No:-  ");
        contact_num.setFont(new Font("Garamond", Font.BOLD, 18));
        contact_num.setBounds(20, 320, 230, 25);
        panel7.add(contact_num);

        doctype = new JLabel("Document Type:-  ");
        doctype.setFont(new Font("Garamond", Font.BOLD, 18));
        doctype.setBounds(20, 350, 230, 25);
        panel7.add(doctype);

        doc_num = new JLabel("Document  Number:-  ");
        doc_num.setFont(new Font("Garamond", Font.BOLD, 18));
        doc_num.setBounds(20, 380, 230, 25);
        panel7.add(doc_num);

        address = new JLabel("Full Address:-  ");
        address.setFont(new Font("Garamond", Font.BOLD, 18));
        address.setBounds(20, 410, 230, 25);
        panel7.add(address);

        joining_date = new JLabel("Joining Date:-  ");
        joining_date.setFont(new Font("Garamond", Font.BOLD, 18));
        joining_date.setBounds(20, 440, 230, 25);
        panel7.add(joining_date);

        positon = new JLabel("Positon:-  ");
        positon.setFont(new Font("Garamond", Font.BOLD, 18));
        positon.setBounds(20, 470, 230, 25);
        panel7.add(positon);

        salary = new JLabel("Salary:-  ");
        salary.setFont(new Font("Garamond", Font.BOLD, 18));
        salary.setBounds(20, 500, 230, 25);
        panel7.add(salary);

        // --- Text Fields ---
        // Hidden text field for auto-generated ID (used internally by the database)
        autoidtf = new JTextField();
        autoidtf.setEditable(false);
        autoidtf.setBounds(240, 140, 240, 20);
        panel7.add(autoidtf);
        autoidtf.setVisible(false); // Hidden from view

        // Search text field
        searchwartf = new JTextField(12);
        searchwartf.setBounds(20, 9, 180, 20);
        panelsearch.add(searchwartf);

        // Input text fields for employee data
        idtf = new JTextField();
        idtf.setBounds(240, 170, 240, 20);
        panel7.add(idtf);

        fullnametf = new JTextField();
        fullnametf.setBounds(240, 200, 240, 20);
        panel7.add(fullnametf);

        father_nametf = new JTextField();
        father_nametf.setBounds(240, 230, 240, 20);
        panel7.add(father_nametf);

        // --- Gender Checkboxes and ButtonGroup ---
        Male = new JCheckBox("Male");
        Male.setBounds(250, 260, 70, 22);
        Male.setBackground(null); // Transparent background
        panel7.add(Male);

        Female = new JCheckBox("Female");
        Female.setBounds(330, 260, 70, 22);
        Female.setBackground(null);
        panel7.add(Female);

        Other = new JCheckBox("Other");
        Other.setBounds(420, 260, 70, 22);
        Other.setBackground(null);
        panel7.add(Other);

        genderbg = new ButtonGroup(); // Create a button group for radio-like behavior
        genderbg.add(Male);
        genderbg.add(Female);
        genderbg.add(Other);

        // Text fields for Date of Birth and Contact Number
        dobtf = new JTextField();
        dobtf.setBounds(240, 290, 160, 20);
        panel7.add(dobtf);

        // Text field to indicate date format (non-editable)
        dobtypetf = new JTextField("YYYY-MM-DD");
        dobtypetf.setBounds(400, 290, 76, 20);
        dobtypetf.setEditable(false);
        panel7.add(dobtypetf);

        contact_notf = new JTextField();
        contact_notf.setBounds(240, 320, 240, 20);
        panel7.add(contact_notf);

        // --- Document Type ComboBox ---
        doctypecb = new JComboBox<>(new String[]{"Please Select Document type", "Aadhar Card", "Pen Card", "Voter Id", "Driving Licence"});
        doctypecb.setBounds(240, 350, 240, 20);
        panel7.add(doctypecb);

        // Text fields for Document Number, Address, Joining Date, Position, and Salary
        doc_numtf = new JTextField();
        doc_numtf.setBounds(240, 380, 240, 20);
        panel7.add(doc_numtf);

        addresstf = new JTextField();
        addresstf.setBounds(240, 410, 240, 20);
        panel7.add(addresstf);

        joining_datetf = new JTextField();
        joining_datetf.setBounds(240, 440, 240, 20);
        panel7.add(joining_datetf);

        positontf = new JTextField();
        positontf.setBounds(240, 470, 240, 20);
        panel7.add(positontf);

        salarytf = new JTextField();
        salarytf.setBounds(240, 500, 240, 20);
        panel7.add(salarytf);

        // --- Employee Table and Scroll Pane ---
        ScrollPane = new JScrollPane();
        ScrollPane.setBounds(500, 170, 850, 420); // Position and size of the scroll pane
        panel7.add(ScrollPane);

        emptable = new JTable();
        emptable.setDefaultEditor(Object.class, null); // Make table cells non-editable by default
        // Mouse listener to handle row clicks for populating form fields
        emptable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int index = emptable.getSelectedRow(); // Get the selected row index
                TableModel model = emptable.getModel(); // Get the table model

                // Populate text fields from the selected table row
                autoidtf.setText(model.getValueAt(index, 0).toString()); // Auto ID
                idtf.setText(model.getValueAt(index, 1).toString()); // Employee ID
                fullnametf.setText(model.getValueAt(index, 2).toString()); // Full Name
                father_nametf.setText(model.getValueAt(index, 3).toString()); // Father's Name

                // Reset all gender checkboxes before setting the correct one
                Male.setSelected(false);
                Female.setSelected(false);
                Other.setSelected(false);

                // Set the correct gender checkbox based on the table data
                String gender = model.getValueAt(index, 4).toString();
                for (Enumeration<AbstractButton> buttons = genderbg.getElements(); buttons.hasMoreElements(); ) {
                    AbstractButton button = buttons.nextElement();
                    if (button.getText().equalsIgnoreCase(gender)) {
                        button.setSelected(true); // Select the matching gender checkbox
                        break; // Exit loop once found
                    }
                }

                // Populate remaining text fields
                dobtf.setText(model.getValueAt(index, 5).toString()); // Date of Birth
                contact_notf.setText(model.getValueAt(index, 6).toString()); // Contact Number

                // Set the selected item in the document type combo box
                doctypecb.setSelectedItem(model.getValueAt(index, 7).toString()); // Document Type

                // Populate remaining text fields
                doc_numtf.setText(model.getValueAt(index, 8).toString()); // Document Number
                addresstf.setText(model.getValueAt(index, 9).toString()); // Address
                joining_datetf.setText(model.getValueAt(index, 10).toString()); // Joining Date
                positontf.setText(model.getValueAt(index, 11).toString()); // Position
                salarytf.setText(model.getValueAt(index, 12).toString()); // Salary
            }
        });

        emptable.setFont(new Font("Tahoma", Font.PLAIN, 14)); // Set font for the table
        emptable.setRowHeight(30); // Set row height for better readability
        ScrollPane.setViewportView(emptable); // Set the table as the viewport for the scroll pane

        // --- Action Buttons ---
        // Save Button
        save = new JButton("Save");
        save.setBackground(Color.BLUE); // Set button background color
        save.setForeground(Color.white); // Set text color
        save.setBounds(40, 600, 120, 25); // Position the button
        panel7.add(save);
        // ActionListener for the Save button
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Retrieve data from input fields
                String id = idtf.getText();
                String name = fullnametf.getText();
                String fname = father_nametf.getText();

                // Determine selected gender
                String gender = null;
                if (Male.isSelected()) {
                    gender = Male.getText();
                } else if (Female.isSelected()) {
                    gender = Female.getText();
                } else if (Other.isSelected()) {
                    gender = Other.getText();
                }

                String dob = dobtf.getText();
                String contact = contact_notf.getText();
                String doctype = (String) doctypecb.getSelectedItem(); // Get selected document type
                String docnum = doc_numtf.getText();
                String fulladd = addresstf.getText();
                String joindate = joining_datetf.getText();
                String position = positontf.getText();
                String salary = salarytf.getText();

                // --- Input Validations ---
                if (id == null || id.isEmpty() || id.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Add Employee`s Id");
                    idtf.requestFocus(); // Set focus to the ID field
                    return; // Stop further execution
                }
                if (name == null || name.isEmpty() || name.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Add Employee`s Name");
                    fullnametf.requestFocus();
                    return;
                }
                if (fname == null || fname.isEmpty() || fname.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Add Employee`s Father Name");
                    father_nametf.requestFocus();
                    return;
                }
                if (gender == null || gender.isEmpty() || gender.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Select Employee Gender");
                    return;
                }
                if (dob == null || dob.isEmpty() || dob.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Add Employee`s Date of Birth");
                    dobtf.requestFocus();
                    return;
                }
                if (contact == null || contact.isEmpty() || contact.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Add Employee`s Contact");
                    contact_notf.requestFocus();
                    return;
                }
                if (doctype.equals("Please Select Document type")) { // Check if default option is selected
                    JOptionPane.showMessageDialog(null, "Please Select Document Type");
                    return;
                }
                if (docnum == null || docnum.isEmpty() || docnum.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Add Document Number");
                    doc_numtf.requestFocus();
                    return;
                }
                if (fulladd == null || fulladd.isEmpty() || fulladd.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Add Employee`s Address");
                    addresstf.requestFocus();
                    return;
                }
                if (joindate == null || joindate.isEmpty() || joindate.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Add Employee`s Joining Date");
                    joining_datetf.requestFocus();
                    return;
                }
                if (position == null || position.isEmpty() || position.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Add Employee`s Position");
                    positontf.requestFocus();
                    return;
                }
                if (salary == null || salary.isEmpty() || salary.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Add Employee`s Salary");
                    salarytf.requestFocus();
                    return;
                }

                // --- Database Insertion ---
                try {
                    // SQL statement to insert new employee record
                    String sql = ("INSERT INTO employee(id,name,fname,gender,dob,contact,Document_Type,Document_Num,full_Addres,joining_Date,position,salary)values(?,?,?,?,?,?,?,?,?,?,?,?)");
                    pst = con.prepareStatement(sql); // Prepare the SQL statement
                    // Set values for the prepared statement parameters
                    pst.setString(1, id);
                    pst.setString(2, name);
                    pst.setString(3, fname);
                    pst.setString(4, gender);
                    pst.setString(5, dob);
                    pst.setString(6, contact);
                    pst.setString(7, doctype);
                    pst.setString(8, docnum);
                    pst.setString(9, fulladd);
                    pst.setString(10, joindate);
                    pst.setString(11, position);
                    pst.setString(12, salary);
                    pst.executeUpdate(); // Execute the update (insertion)
                    JOptionPane.showMessageDialog(null, "Data insert Success"); // Show success message
                    clear(); // Clear the form fields
                    loadTable(); // Refresh the employee table
                    pst.close() ;
                    con.close();
                } catch (SQLException e1) {
                    e1.printStackTrace(); // Print any SQL exceptions
                }
            }
        });

        // Update Button
        update = new JButton("Update");
        update.setBackground(Color.BLUE);
        update.setForeground(Color.white);
        update.setBounds(200, 600, 120, 25);
        panel7.add(update);
        // ActionListener for the Update button
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Retrieve data from input fields
                String autoid = autoidtf.getText(); // Get the auto-generated ID for updating
                String id = idtf.getText();
                String name = fullnametf.getText();
                String fname = father_nametf.getText();

                // Determine selected gender
                String gender = null;
                if (Male.isSelected()) {
                    gender = Male.getText();
                } else if (Female.isSelected()) {
                    gender = Female.getText();
                } else if (Other.isSelected()) {
                    gender = Other.getText();
                }

                String dob = dobtf.getText();
                String contact = contact_notf.getText();
                String doctype = (String) doctypecb.getSelectedItem(); // Get selected document type
                String docnum = doc_numtf.getText();
                String fulladd = addresstf.getText();
                String joindate = joining_datetf.getText();
                String position = positontf.getText();
                String salary = salarytf.getText();

                // --- Input Validations (same as Save button) ---
                if (id == null || id.isEmpty() || id.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Add Employee`s Id");
                    idtf.requestFocus();
                    return;
                }
                if (name == null || name.isEmpty() || name.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Add Employee`s Name");
                    fullnametf.requestFocus();
                    return;
                }
                if (fname == null || fname.isEmpty() || fname.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Add Employee`s Father Name");
                    father_nametf.requestFocus();
                    return;
                }
                if (gender == null || gender.isEmpty() || gender.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Select Employee Gender");
                    return;
                }
                if (dob == null || dob.isEmpty() || dob.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Add Employee`s Date of Birth");
                    dobtf.requestFocus();
                    return;
                }
                if (contact == null || contact.isEmpty() || contact.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Add Employee`s Contact");
                    contact_notf.requestFocus();
                    return;
                }

                if (doctype.equals("Please Select Document type")) {
                    JOptionPane.showMessageDialog(null, "Please Select Document Type");
                    return;
                }
                if (docnum == null || docnum.isEmpty() || docnum.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Add Document Number");
                    doc_numtf.requestFocus();
                    return;
                }
                if (fulladd == null || fulladd.isEmpty() || fulladd.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Add Employee`s Address");
                    addresstf.requestFocus();
                    return;
                }
                if (joindate == null || joindate.isEmpty() || joindate.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Add Employee`s Joining Date");
                    joining_datetf.requestFocus();
                    return;
                }
                if (position == null || position.isEmpty() || position.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Add Employee`s Position");
                    positontf.requestFocus();
                    return;
                }
                if (salary == null || salary.isEmpty() || salary.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Add Employee`s Salary");
                    salarytf.requestFocus();
                    return;
                }

                // --- Database Update ---
                try {
                    // SQL statement to update an existing employee record based on autoid
                    String sql = ("update employee set id=?,name=?,fname=?,gender=?,dob=?,contact=?,Document_Type=?,Document_Num=?,full_Addres=?,joining_Date=?,position=?,salary=? where autoid=?");
                    pst = con.prepareStatement(sql);
                    // Set values for the prepared statement parameters
                    pst.setString(1, id);
                    pst.setString(2, name);
                    pst.setString(3, fname);
                    pst.setString(4, gender);
                    pst.setString(5, dob);
                    pst.setString(6, contact);
                    pst.setString(7, doctype);
                    pst.setString(8, docnum);
                    pst.setString(9, fulladd);
                    pst.setString(10, joindate);
                    pst.setString(11, position);
                    pst.setString(12, salary);
                    pst.setString(13, autoid); // The ID to identify which record to update
                    pst.executeUpdate(); // Execute the update
                    JOptionPane.showMessageDialog(null, "Data Update Success"); // Show success message
                    clear(); // Clear the form fields
                    loadTable(); // Refresh the employee table
                } catch (SQLException e1) {
                    e1.printStackTrace(); // Print any SQL exceptions
                }

            }
        });

        // Delete Button
        delete = new JButton("Delete");
        delete.setBackground(Color.BLUE);
        delete.setForeground(Color.white);
        delete.setBounds(360, 600, 120, 25);
        panel7.add(delete);
        // ActionListener for the Delete button
        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // Get the auto-generated ID of the record to be deleted
                String autoid = autoidtf.getText();
                // Proceed only if the autoid field is not empty (meaning a row was selected)
                if (!autoidtf.getText().isEmpty()) {

                    // Show a confirmation dialog before deleting
                    int result = JOptionPane.showConfirmDialog(null, "Sure? You want to Delete?", "Delete",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    // If the user confirms deletion (clicks "Yes")
                    if (result == JOptionPane.YES_OPTION) {
                        try {
                            // Prepare SQL statement to delete the record based on autoid
                            pst = con.prepareStatement("delete from employee where autoid=?");
                            pst.setString(1, autoid); // Set the autoid parameter
                            pst.executeUpdate(); // Execute the deletion
                            JOptionPane.showMessageDialog(null, "Data Deleted Success"); // Show success message
                            clear(); // Clear the form fields
                            loadTable(); // Refresh the employee table
                        } catch (SQLException e1) {
                            e1.printStackTrace(); // Print any SQL exceptions
                        }
                    }
                } else {
                    // Inform the user to select a record if no autoid is present
                    JOptionPane.showMessageDialog(null, "Please select a record to delete.");
                }
            }
        });

        // Back Button
        back = new JButton("Back");
        back.setForeground(Color.BLACK);
        back.setFont(new Font("FangSong", Font.PLAIN, 18));
        back.setBorder(BorderFactory.createEmptyBorder()); // Remove button border
        back.setContentAreaFilled(false); // Make button background transparent
        back.setBounds(1220, 30, 150, 30);
        panel7.add(back);
        // ActionListener for the Back button
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigate to the Admin_Home screen and close the current window
                new Admin_Home(); // Assuming Admin_Home is another Swing class
                emf.setVisible(false); // Hide the current employee frame
            }
        });

        // --- Search Button ---
        searchbutton = new JButton("Search");
        searchbutton.setForeground(Color.BLACK);
        searchbutton.setFont(new Font("FangSong", Font.PLAIN, 18));
        searchbutton.setBorder(BorderFactory.createEmptyBorder());
        searchbutton.setBackground(Color.blue); // Note: This color might not be visible due to setContentAreaFilled(false) on its parent panel if that were set. However, here it's applied directly to the button.
        searchbutton.setBounds(220, 8, 80, 20);
        panelsearch.add(searchbutton);
        // ActionListener for the Search button
        searchbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Searchwar(); // Call the search method
                clear(); // Clear the form fields after search
            }
        });

        emf.setVisible(true); // Make the main frame visible
    }
}