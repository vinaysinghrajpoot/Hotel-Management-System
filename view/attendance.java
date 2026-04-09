package view;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.table.TableModel;

import Utils.DatabaseUtils;

public class attendance {
	
	JFrame attend_employee;
	JPanel panel8,panel,panelsearch;
	// Define the file path for the background image.
	String imageFilePath = "/C:/Users/ACER/MY_J2SE/Hotel-Project/Img/attendance.png";
	// A final array to hold the background image, allowing it to be modified within the paintComponent method.
	final Image[] backgroundImage = {null};
	JLabel title,empname,empid,empattendence,date,time,reason;
	JTextField autoId,emptfname,emptfid,tfdate,tftime,searchwartf;
	JTextArea txtarea;
	JCheckBox Present,Absent,Leave;
	ButtonGroup butgrop;
	JTable table;
	JScrollPane sp;
	JButton save,delete,update,back,searchbutton;

//	Database Connection
  	Connection con = null;
  	PreparedStatement pst;
  	ResultSet rs;
	
	public attendance() {
		// Initialize the main frame and its components.		
		frame();
		// Clear text fields and selections on startup.
		clear();
		// Set up and display the current date and time.
		date_time();
		// Establish a connection to the database.
		Connection();
		// Load data from the database into the JTable.
		Table();
		}
	
	//	Create Connection
	public void Connection(){
		try{
			// Load the MySQL JDBC driver.
			Class.forName("com.mysql.jdbc.Driver");
			// Define the connection URL, username, and password and establish the connection.
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel","root","");
		}catch(Exception ex){
			// If connection fails, print the error and show an error message dialog.
		ex.printStackTrace();
		JOptionPane.showMessageDialog(null, "Database Connection Failed: " + ex.getMessage());
		}
	}
		
	public void Table() {
		try {
			// Prepare and execute a SQL query to fetch all records from the 'Attendence' table.
			pst = con.prepareStatement("Select * from Attendence");
			rs = pst.executeQuery();
			table.setBackground(Color.ORANGE);
			// Populate the JTable with the results from the ResultSet.
			table.setModel(DatabaseUtils.resultSetToTableModel(rs));
		}catch(Exception e) {
				e.printStackTrace();
			}
	}	
	
	public void clear() {
		// Reset text fields and clear the button group selection.
		autoId.setText("");
		emptfname.setText("");
		emptfid.setText("");
		butgrop.clearSelection();		
		txtarea.setText("");
		emptfname.requestFocus();
	}
	

	public void date_time() {
		
		date=new JLabel("Date:- ");
		date.setFont(new Font("Times New Roman",Font.BOLD,18));
		date.setBounds(30, 30, 150, 25);
		panel8.add(date);
		
		time=new JLabel("Time:- ");
		time.setFont(new Font("Times New Roman",Font.BOLD,18));
		time.setBounds(30, 60, 150, 25);
		panel8.add(time);
		
		tfdate=new JTextField();
		tfdate.setBounds(80, 32, 90, 22);
		tfdate.setBackground(Color.yellow);
		panel8.add(tfdate);
		
		tftime=new JTextField();
		tftime.setBounds(80, 62, 90, 22);
		tftime.setBackground(Color.yellow);
		panel8.add(tftime);
		
		// Set the current date in the text field.
		Date d= new Date();
		SimpleDateFormat dateform=new SimpleDateFormat("  dd-MM-YYYY");
		tfdate.setText(dateform.format(d));
		
		// Use a Swing Timer to update the current time every second.
		 new Timer(0, new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                Date d = new Date();
	        SimpleDateFormat s = new SimpleDateFormat(" hh:mm:ss a");
	        tftime.setText(s.format(d));
	            }
	        }).start();
	}
	
//  Method to search for customers in the database.
	public void Searchwar() {
		try {
			// Get the search term from the search text field, removing leading/trailing spaces.
			String searchTerm = searchwartf.getText().trim();
			// Check if the search field is empty.
			if (searchTerm.isEmpty()) {
				// If it's empty, prepare a query to select all customers.
				pst = con.prepareStatement("SELECT * FROM Attendence");
			} else {
			// If there is a search term, prepare a query to search by name, contact, or check-in date.
				pst = con.prepareStatement("SELECT * FROM Attendence WHERE Employee_Name LIKE ? OR Employee_Id = ? OR Attendance = ?");
				// Set the parameters for the query.
				pst.setString(1, "%" + searchTerm + "%"); // '%' allows for partial matches on the Employee Name.
				pst.setString(2, searchTerm); // Exact match for Employee Id.
				pst.setString(3, searchTerm); // Exact match for Attendance.
			}
			// Execute the query.
			rs = pst.executeQuery();
			// Update the table with the search results using a helper utility.
			table.setModel(DatabaseUtils.resultSetToTableModel(rs));
			} catch (Exception e) {
				// Print any error that occurs to the console.
				e.printStackTrace();
				}
		}
	
	public void frame() {
		
		attend_employee=new JFrame("Attendence Employee");
		attend_employee.setLayout(new GridLayout(1,1));
		attend_employee.setTitle("Attendence Employee");
		attend_employee.setSize(2000,1550);
		attend_employee.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel8=new JPanel();
		panel8.setBorder(new EtchedBorder(EtchedBorder.LOWERED,null,null));
		panel8.setSize(1365,750);
		panel8.setBackground(Color.lightGray);
		panel8.setLayout(null);
		attend_employee.add(panel8);
		
		
//		IMAGE SET					
		panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                // Call the superclass's paintComponent to ensure proper rendering.
                super.paintComponent(g);
                // Draw the image on the panel if it's successfully loaded.
                if (backgroundImage[0] != null) {
                    // Draw the image scaled to the panel's dimensions.
                    g.drawImage(backgroundImage[0], 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
		panel8.add(panel);
		panel.setBounds(330, 40, 60, 60);		
		try {
            // Load the image from the specified path.
            backgroundImage[0] = ImageIO.read(new File(imageFilePath));
        } catch (IOException e) {
            e.printStackTrace();
            // Print an error if the image cannot be loaded.
            System.err.println("Error loading image from path: " + imageFilePath);
        }
		
		title=new JLabel("Attendence Employee");
		title.setForeground(Color.DARK_GRAY);
		title.setFont(new Font("Castellar",Font.BOLD,40));
		title.setBounds(400, 00, 700, 150);
		panel8.add(title);

		panelsearch=new JPanel();
		panelsearch.setBorder(new EtchedBorder(EtchedBorder.LOWERED,null,null));
		panelsearch.setBounds(990,120,320,40);
		panelsearch.setBackground(Color.red);		
		panelsearch.setLayout(null);
		panel8.add(panelsearch);
		
		empname=new JLabel("Employee Name- ");
		empname.setFont(new Font("Times New Roman",Font.BOLD,18));
		empname.setBounds(40, 200, 180, 25);
		panel8.add(empname);
		
		empid=new JLabel("Emplooyee Id- ");
		empid.setFont(new Font("Times New Roman",Font.BOLD,18));
		empid.setBounds(40, 245, 150, 25);
		panel8.add(empid);
		
		empattendence=new JLabel("Attendence- ");
		empattendence.setFont(new Font("Times New Roman",Font.BOLD,18));
		empattendence.setBounds(40, 290, 150, 25);
		panel8.add(empattendence);		
		
		reason=new JLabel("Leave Reason- ");
		reason.setFont(new Font("Times New Roman",Font.BOLD,18));
		reason.setBounds(40, 355, 150, 25);
		panel8.add(reason);
		
		autoId=new JTextField(20);
		autoId.setEditable(false);
		autoId.setBounds(190, 440, 240, 22);
		panel8.add(autoId);
		autoId.setVisible(false);

 		searchwartf=new JTextField(12);
 		searchwartf.setBounds(20,9,180,20);
 		panelsearch.add(searchwartf);
		
		emptfname=new JTextField();
		emptfname.setBounds(190, 200, 250, 22);
		panel8.add(emptfname);
		
		emptfid=new JTextField();
		emptfid.setBounds(190, 245, 250, 22);
		panel8.add(emptfid);
		
		Present=new JCheckBox("Present");
		Present.setBounds(195, 290, 70, 22);
		Present.setBackground(null);
		panel8.add(Present);
		
		Absent=new JCheckBox("Absent");
		Absent.setBounds(280, 290, 65, 22);
		Absent.setBackground(null);
		panel8.add(Absent);
		
		Leave=new JCheckBox("Leave");
		Leave.setBounds(370, 290, 60, 22);
		Leave.setBackground(null);
		panel8.add(Leave);
		
		butgrop= new ButtonGroup();
		butgrop.add(Present);
		butgrop.add(Absent);
		butgrop.add(Leave);
		
		txtarea=new JTextArea();
		txtarea.setBounds(190, 335, 250, 80);
		panel8.add(txtarea);
		
//		Scroll Pane		
		sp=new JScrollPane();
		sp.setBounds(500,170,850,420);
		panel8.add(sp);
		
		table=new JTable();
		// Disable cell editing directly in the table.
		table.setDefaultEditor(Object.class,null);
		// Add a mouse listener to handle row selection in the table.
		table.addMouseListener(new MouseAdapter() {
		
			public void mouseClicked(MouseEvent e){				
				
				int index = table.getSelectedRow();
				TableModel model =table.getModel();
				
//				Direct Set values
				// Populate form fields with data from the selected table row.
				autoId.setText(model.getValueAt(index,0).toString());
				emptfname.setText(model.getValueAt(index,1).toString());
				emptfid.setText(model.getValueAt(index,2).toString());				

//				CheckBox
				// Select the correct checkbox based on the attendance value from the table.
				String  Attendance= model.getValueAt(index, 3).toString();
				for (Enumeration<AbstractButton> buttons = butgrop.getElements();
						buttons.hasMoreElements();) {
			    AbstractButton button = buttons.nextElement();
			    button.setSelected(button.getText().equalsIgnoreCase(Attendance));
			    }
								
				// Clear the text area if attendance is not "Leave."
				if(Absent.isSelected()) {
					txtarea.setText("");	
				}
				if(Present.isSelected()) {
					txtarea.setText("");	
				}
				// Set the reason text, which will be empty for Present/Absent.
				txtarea.setText(model.getValueAt(index,4).toString());
				tfdate.setText(model.getValueAt(index,5).toString());
				tftime.setText(model.getValueAt(index,6).toString());
			}
		});	

		table.setFont(new Font("Times New Roman",Font.PLAIN,14));
		table.setRowHeight(30);
		sp.setViewportView(table);
		
		save=new JButton("Save");
		save.setBackground(Color.GREEN);
		save.setForeground(Color.WHITE);
		save.setBounds(40, 590, 120, 25);
		panel8.add(save);
		// Add an action listener for the Save button.
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
//				TextFiend
				String autoid=autoId.getText();
				String empname=emptfname.getText();
				String empid=emptfid.getText();
				
				String Attendance= null;
				// Determine the selected attendance status.
				if(Present.isSelected()) {
					Attendance = Present.getText();
				}else if(Absent.isSelected()){
					Attendance = Absent.getText();
		        }else if(Leave.isSelected()) {
		        	Attendance = Leave.getText();
		        }
				
				String leave=txtarea.getText();
				String date=tfdate.getText();
				String time=tftime.getText();
				
				// Validate input fields to ensure they are not empty.
				if(empname ==null || empname.isEmpty()||empname.trim().isEmpty()){
					JOptionPane.showMessageDialog(null,"Please add Employee Name");
					emptfname.requestFocus();
					return;
				}
				if(empid ==null || empid.isEmpty()||empid.trim().isEmpty()){
					JOptionPane.showMessageDialog(null,"Please add Employee`s Id Number");
					emptfid.requestFocus();
					return;
				}
				if(Attendance == null || Attendance.isEmpty()|| Attendance.trim().isEmpty()){
					JOptionPane.showMessageDialog(null,"Please Select Attendance");
					return;
				}
				// Check if 'Leave' is selected, requiring a reason.
				if(Leave.isSelected() && (leave==null|| leave.isEmpty()|| leave.trim().isEmpty())){
					JOptionPane.showMessageDialog(null,"Please State the reason why you are taking Leave");					
					txtarea.requestFocus();					
					return;
		        }
				try {
					// Prepare and execute an SQL INSERT statement to add a new record.
					pst=con.prepareStatement("insert into Attendence(Employee_Name,Employee_Id,Attendance,leave_Reason,Date,Time)values(?,?,?,?,?,?)");
					pst.setString(1, empname);
					pst.setString(2,empid);
					pst.setString(3,Attendance);
					pst.setString(4,leave);
					pst.setString(5,date);
					pst.setString(6,time);
					pst.executeUpdate();
					clear();
					JOptionPane.showMessageDialog(null,"Data insert Successfully");
					Table();
				}catch(SQLException e1){
					e1.printStackTrace();
				}
			}
		});
		
		update=new JButton("Update");
		update.setBackground(Color.BLUE);
		update.setForeground(Color.WHITE);
		update.setBounds(180, 590, 120, 25);
		panel8.add(update);
		// Add an action listener for the Update button.
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				TextFiend
				String autoid=autoId.getText();
				String empname=emptfname.getText();
				String empid=emptfid.getText();
				
				String Attendance= null;
				if(Present.isSelected()) {
					Attendance = Present.getText();
				}else if(Absent.isSelected()){
					Attendance = Absent.getText();
		        }else if(Leave.isSelected()) {
		        	Attendance = Leave.getText();
		        }
				
				String leave=txtarea.getText();
				String date=tfdate.getText();
				String time=tftime.getText();
				
				// Validate input fields.
				if(empname ==null || empname.isEmpty()||empname.trim().isEmpty()){
					JOptionPane.showMessageDialog(null,"Please add Employee Name");
					emptfname.requestFocus();
					return;
				}
				if(empid ==null || empid.isEmpty()||empid.trim().isEmpty()){
					JOptionPane.showMessageDialog(null,"Please add Employee`s Id Number");
					emptfid.requestFocus();
					return;
				}
				if(Attendance == null || Attendance.isEmpty()|| Attendance.trim().isEmpty()){
					JOptionPane.showMessageDialog(null,"Please Select Attendance");
					return;
				}
				if(Leave.isSelected() && (leave==null|| leave.isEmpty()|| leave.trim().isEmpty())){
					JOptionPane.showMessageDialog(null,"Please State the reason why you are taking Leave");					
					txtarea.requestFocus();					
					return;
		        }
				
				try {
					// Prepare and execute an SQL UPDATE statement.
					pst=con.prepareStatement("UPDATE Attendence set Employee_Name=?,Employee_Id=?,Attendance=?,leave_Reason=?,Date=?,Time=? where autoId=?");
					pst.setString(1, empname);
					pst.setString(2,empid);
					pst.setString(3,Attendance);
					pst.setString(4,leave);
					pst.setString(5,date);
					pst.setString(6,time);
					pst.setString(7,autoid);
					pst.executeUpdate();
					clear();
					JOptionPane.showMessageDialog(null,"Data insert Successfully");
					Table();
					}catch(SQLException e2){
						e2.printStackTrace();
						}
				}
			});
		
		delete=new JButton("Delete");
		delete.setBackground(Color.RED);
		delete.setForeground(Color.WHITE);
		delete.setBounds(320, 590, 120, 25);
		panel8.add(delete);
		// Add an action listener for the Delete button.
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				Delete Details
				String autoid = autoId.getText();
				// Check if a row is selected for deletion.
				if (!autoId.getText().isEmpty()){
					// Show a confirmation dialog before deleting the record.
					int result = JOptionPane.showConfirmDialog(null, "Sure? You want to Delete?", "Delete",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (result == JOptionPane.YES_OPTION) {
						try {
							// Prepare and execute an SQL DELETE statement.
							pst = con.prepareStatement("delete from Attendence where autoId=?");
							pst.setString(1, autoid);
							pst.executeUpdate();
							clear();
							JOptionPane.showMessageDialog(null, "Data Deleted Success");
							Table();
							}catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
					}
				}
			});
		
		back=new JButton("Back");
		back.setForeground(Color.BLACK);
		back.setFont(new Font("FangSong",Font.PLAIN,18));
		back.setBorder(BorderFactory.createEmptyBorder());
		back.setContentAreaFilled(false);
		back.setBounds(1230, 30, 150, 30);
		panel8.add(back);
//		Action Performed;
		// Add an action listener to return to the Admin_Home frame.
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			new	Admin_Home();
			attend_employee.setVisible(false);
			}
		});

//		Search Button
		searchbutton=new JButton("Search");
		searchbutton.setForeground(Color.BLACK);
		searchbutton.setFont(new Font("FangSong",Font.PLAIN,18));
		searchbutton.setBorder(BorderFactory.createEmptyBorder());
		searchbutton.setBackground(Color.blue);
		searchbutton.setBounds(220, 8, 80, 20);
		panelsearch.add(searchbutton);
//		Action Performed search war;
		// Add an action listener to trigger the search function.
		searchbutton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
	    	Searchwar();
	        clear();
	        }
	    });
		
		attend_employee.setVisible(true);	
	}
}