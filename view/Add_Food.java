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
import java.util.Enumeration;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.table.TableModel;

import Utils.DatabaseUtils;

// This class represents the GUI for adding, updating, and deleting food items.
public class Add_Food {
		
	JFrame aff;
	JPanel panel6,panel,panelsearch;
	JLabel addfoodtitle,foodname,foodcategory,foodvariety,foodtype,foodprice;
	private JTextField autoId,foodnametf,foodpricetf,searchwartf;
	JTextArea foodvarietytxa;
	
	String imageFilePath = "C:/Users/ACER/MY_J2SE/Hotel-Project/Img/Food01.png";
	final Image[] backgroundImage = {null};
	
	JRadioButton vegcategory,nonvegcategory,fruitarian;
	ButtonGroup afbuttongrup;
	JComboBox<String> aftypechb;
	String[] foodtypecb={"Please Select Food Type","Junk Food","Snacks","Drinks","Meal"};
	JButton save,delete,update,afback,searchbutton;
	private JTable aftable;
	JScrollPane afscrollPane;
	
//	Database Connection
  	Connection con = null;
  	PreparedStatement pst;
  	ResultSet rs;
		
//	Calling Frame,Connection;
	// The constructor initializes the GUI and database connection.
	public Add_Food() {
		addroomframe();
		clear();
		Connection();
		afloadTable();
	}
	
//	Create a Sign Up Clear text field method;
	// This method clears all input fields on the form.
	public void clear(){		
		autoId.setText("");
		foodnametf.setText("");
		afbuttongrup.clearSelection();
		foodvarietytxa.setText("");
		aftypechb.setSelectedIndex(-1); 		
		 if (aftypechb.getItemCount() > 0) {
		        aftypechb.setSelectedIndex(0);  // Reset combo box
		    }
		 aftypechb.repaint();
		 
		foodpricetf.setText("");		
		foodnametf.requestFocus();		
	}
	
	//	Create Connection
	// This method establishes a connection to the MySQL database.
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
	
	 //     Load Table
	// This method loads all data from the 'addfood' table into the JTable.
		public void afloadTable() {
			try {
				pst = con.prepareStatement("Select * from addfood ");
				rs = pst.executeQuery();
				aftable.setModel(DatabaseUtils.resultSetToTableModel(rs));
				aftable.setBackground(Color.orange);
			}catch(Exception e) {
					e.printStackTrace();
				}
			}

//		  Method to search for customers in the database.
		// This method filters the JTable based on the search term entered by the user.
		 public void Searchwar() {
		     try {
		         // Get the search term from the search text field, removing leading/trailing spaces.
		         String searchTerm = searchwartf.getText().trim();

		         // Check if the search field is empty.
		         if (searchTerm.isEmpty()) {
		             // If it's empty, prepare a query to select all customers.
		             pst = con.prepareStatement("SELECT * FROM addfood");
		         } else {
		             // If there is a search term, prepare a query to search by name, contact, or check-in date.
		             pst = con.prepareStatement("SELECT * FROM addfood WHERE AddFoodName LIKE ? OR AddFooodCategory = ? OR AddFoodPrice = ? OR AddFoodType = ?");
		             // Set the parameters for the query.
		             pst.setString(1, "%" + searchTerm + "%"); // '%' allows for partial matches on the Add Food Name.
		             pst.setString(2, searchTerm); // Exact match for Add Food Category.
		             pst.setString(3, searchTerm); // Exact match for Add Food Price.
		             pst.setString(4, searchTerm); // Exact match for Add Food Type.
		         }
		         // Execute the query.
		         rs = pst.executeQuery();
		         // Update the table with the search results using a helper utility.
		         aftable.setModel(DatabaseUtils.resultSetToTableModel(rs));
		     } catch (Exception e) {
		         // Print any error that occurs to the console.
		         e.printStackTrace();
		     }
		 }
	
//	Creating frame method;
	// This method initializes and lays out all the GUI components.
	public void addroomframe(){
		
		aff=new JFrame("Add Food");
		aff.setTitle("Add Food");
		aff.setLayout(new GridLayout(1,1));
		aff.setSize(2000,1550);
		aff.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
//		JPanels;
		panel6=new JPanel();
		panel6.setBorder(new EtchedBorder(EtchedBorder.LOWERED,null,null));
		panel6.setSize(1365,750);
		panel6.setBackground(Color.lightGray);		
		panel6.setLayout(null);
		aff.add(panel6);
		
//		PNG, JLabel, JtextFields, JButtons, ;
		addfoodtitle =new JLabel("Add Food");
		addfoodtitle.setForeground(Color.DARK_GRAY);
		addfoodtitle .setFont(new Font("Castellar",Font.BOLD,40));
		addfoodtitle .setBounds(600,00,700,150);
		panel6.add(addfoodtitle);

		panelsearch=new JPanel();
		panelsearch.setBorder(new EtchedBorder(EtchedBorder.LOWERED,null,null));
		panelsearch.setBounds(990,120,320,40);
		panelsearch.setBackground(Color.red);		
		panelsearch.setLayout(null);
		panel6.add(panelsearch);		
		
//		IMAGE,PNG
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
		panel6.add(panel);
		panel.setBounds(530, 40, 60, 60);		
		try {
            // Load the image from the specified path.
            backgroundImage[0] = ImageIO.read(new File(imageFilePath));
        } catch (IOException e) {
            e.printStackTrace();
            // Print an error if the image cannot be loaded.
            System.err.println("Error loading image from path: " + imageFilePath);
        }
		
		foodname=new JLabel("Food Name:- ");
		foodname.setFont(new Font("Times New Roman",Font.BOLD,18));
		foodname.setBounds(60, 220, 150, 25);
		panel6.add(foodname);
		
		foodcategory=new JLabel("Food Category:-");
		foodcategory.setFont(new Font("Times New Roman",Font.BOLD,18));
		foodcategory.setBounds(60, 263, 150, 25);
		panel6.add(foodcategory);
		
		foodvariety=new JLabel("Food variety:-");
		foodvariety.setFont(new Font("Times New Roman",Font.BOLD,18));
		foodvariety.setBounds(60, 320, 150, 25);
		panel6.add(foodvariety);
		
		foodtype=new JLabel("Food Type:-");
		foodtype.setFont(new Font("Times New Roman",Font.BOLD,18));
		foodtype.setBounds(60, 398, 150, 25);
		panel6.add(foodtype);
		
		foodprice=new JLabel("Food Price:-");
		foodprice.setFont(new Font("Times New Roman",Font.BOLD,18));
		foodprice.setBounds(60, 440, 150, 25);
		panel6.add(foodprice);
		
		autoId=new JTextField(20);
		autoId.setEditable(false);
		autoId.setBounds(230,185, 220, 22);
 		panel6.add(autoId);
		autoId.setVisible(false);

 		searchwartf=new JTextField(12);
 		searchwartf.setBounds(20,9,180,20);
 		panelsearch.add(searchwartf);	
		
		foodnametf=new JTextField(20);
		foodnametf.setBounds(230, 222, 220, 22);
		panel6.add(foodnametf);
		
		vegcategory = new JRadioButton("Veg");
		vegcategory.setBackground(null);
		vegcategory.setBounds(250, 265, 50, 22);
		panel6.add(vegcategory);
		
		nonvegcategory = new JRadioButton("Non Veg");
		nonvegcategory.setBackground(null);
		nonvegcategory.setBounds(330, 265, 90, 22);
		panel6.add(nonvegcategory);
	
		afbuttongrup=new ButtonGroup();
		afbuttongrup.add(vegcategory);
		afbuttongrup.add(nonvegcategory);
		
		foodvarietytxa=new JTextArea();
		foodvarietytxa.setBounds(230, 300, 220, 70);
		panel6.add(foodvarietytxa);
		
		aftypechb=new JComboBox(foodtypecb);
		aftypechb.setBounds(240, 400, 200, 22);
		panel6.add(aftypechb);
		
		foodpricetf=new JTextField();
		foodpricetf.setBounds(230, 442, 220, 22);
		panel6.add(foodpricetf);
		
		//ScrollPane 
			afscrollPane = new JScrollPane();
			afscrollPane.setBounds(500,170,850,420);
			panel6.add(afscrollPane);
				
			aftable = new JTable();
			aftable.setDefaultEditor(Object.class, null);
			// Mouse listener to populate input fields when a table row is clicked.
			aftable.addMouseListener(new MouseAdapter() {
		@Override
			public void mouseClicked(MouseEvent e) {

			int index = aftable.getSelectedRow();
			TableModel model = aftable.getModel();
						
		// ID NAME AGE CITY
			autoId.setText(model.getValueAt(index, 0).toString());
			foodnametf.setText(model.getValueAt(index, 1).toString());
			
			String fooodcategory = model.getValueAt(index, 2).toString();
			for (Enumeration<AbstractButton> buttons = afbuttongrup.getElements(); buttons.hasMoreElements();)
			{
		    AbstractButton button = buttons.nextElement();
		    button.setSelected(button.getText().equalsIgnoreCase(fooodcategory));
		    }
			foodvarietytxa.setText(model.getValueAt(index, 3).toString());
			aftypechb.setSelectedItem(model.getValueAt(index, 4).toString());
			foodpricetf.setText(model.getValueAt(index,5).toString());
			
		}
		});
				
		aftable.setFont(new Font("Tahoma", Font.PLAIN, 14));
		aftable.setRowHeight(30);
		afscrollPane.setViewportView(aftable);
		
//		Button & Action Performed;
		save=new JButton("Save");
		save.setBackground(Color.green);
		save.setForeground(Color.white);
		save.setBounds(60, 600, 120, 25);
		panel6.add(save);		
		// ActionListener for the 'Save' button to insert a new food item into the database.
		save.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//TextField
				String foodname = foodnametf.getText();
				//RadioButton
				String foodcategory = null;				
				if(vegcategory.isSelected()){
					foodcategory = vegcategory.getText();
		        }else if(nonvegcategory.isSelected()){
		        	foodcategory = nonvegcategory.getText();
		        }
				String foodvar=foodvarietytxa.getText();
				//ComboBox
				String addfoodtype = (String)aftypechb.getSelectedItem();
				//TextField
				String foodprice = foodpricetf.getText();
				
//				Create a Condctions;
				// Input validation to ensure all fields are filled before saving.
				if (foodname == null || foodname.isEmpty() || foodname.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Add Food Name");
					foodnametf.requestFocus();
					return;
				}
				if (foodcategory == null || foodcategory.isEmpty() || foodcategory.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Select Food Category");
					return;
				}
				if (foodvar == null || foodvar.isEmpty() || foodvar.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Add Food Variert");
					foodvarietytxa.requestFocus();
					return;
				}
				if (addfoodtype == "Please Select Food Type" ){ //ComboBox
					JOptionPane.showMessageDialog(null, "Please Select Food Type");
					return;
				}
				if (foodprice == null || foodprice.isEmpty() || foodprice.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Price");
					foodpricetf.requestFocus();
					return;
				}
//				Save the text field data into DBMS;
				try {
                	pst=con.prepareStatement("insert into addfood(AddFoodName,AddFooodCategory,foodvariety,AddFoodType,AddFoodPrice)values(?,?,?,?,?)");
                	pst.setString(1, foodname);
                	pst.setString(2, foodcategory);
                	pst.setString(3, foodvar);
                	pst.setString(4, addfoodtype);
                	pst.setString(5, foodprice);
                	pst.executeUpdate();
                	JOptionPane.showMessageDialog(null, "Data insert Success");
                	clear();
                	afloadTable();
                	//Call here the Signup home panel ;
                }
                catch (SQLException e1) {
    				e1.printStackTrace();
    			}				
			}			
		});
		
		update=new JButton("Update");
		update.setBackground(Color.BLUE);
		update.setForeground(Color.white);
		update.setBounds(200, 600, 120, 25);
		panel6.add(update);
		// ActionListener for the 'Update' button to modify an existing food item in the database.
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Update Details
				String autoid = autoId.getText();  
				String foodname = foodnametf.getText();
				String foodcategory = null;				
				if(vegcategory.isSelected()){
					foodcategory = vegcategory.getText();
		        }else if(nonvegcategory.isSelected()){
		        	foodcategory = nonvegcategory.getText();
		        }
				String foodvar=foodvarietytxa.getText();
				String addfoodtype = (String)aftypechb.getSelectedItem();
				String foodprice = foodpricetf.getText();

//				Create a Validation;
				// Input validation to ensure fields are filled before updating.
				if (foodname == null || foodname.isEmpty() || foodname.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Add Food Name");
					foodnametf.requestFocus();
					return;
				}if (foodcategory == null || foodcategory.isEmpty() || foodcategory.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Select Food Category");
					return;
				}
				if (foodvar == null || foodvar.isEmpty() || foodvar.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Food Variety");
					foodvarietytxa.requestFocus();
					return;
				}
				if (addfoodtype == "Please Select Food Type" ){ //ComboBox
					JOptionPane.showMessageDialog(null, "Please Select Food Type");
					return;
				}
				if (foodprice == null || foodprice.isEmpty() || foodprice.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Price");
					foodpricetf.requestFocus();
					return;
				}
				
				if(!autoId.getText().isEmpty()) {
					try {
						// SQL update statement.
						pst=con.prepareStatement("update addfood set AddFoodName=?,AddFooodCategory=?,foodvariety=?,AddFoodType=?,AddFoodPrice=? where autoId=?");
						pst.setString(1, foodname);
	                	pst.setString(2, foodcategory);
	                	pst.setString(3, foodvar);
	                	pst.setString(4, addfoodtype);
	                	pst.setString(5, foodprice);
	                	pst.setString(6, autoid);
	                	pst.executeUpdate();
	                	JOptionPane.showMessageDialog(null, "Data Update Success");
	                	clear();
	            		afloadTable();
	                	
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		delete=new JButton("Delete");
		delete.setBackground(Color.red);
		delete.setForeground(Color.white);
		delete.setBounds(340, 600, 120, 25);
		panel6.add(delete);
		// ActionListener for the 'Delete' button to remove a selected food item from the database.
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Delete Details
				String autoid = autoId.getText();
				if (!autoId.getText().isEmpty()){

					int result = JOptionPane.showConfirmDialog(null, "Sure? You want to Delete?", "Delete",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (result == JOptionPane.YES_OPTION) {
						try {
							// SQL delete statement.
							pst = con.prepareStatement("delete from addfood where autoId=?");
							pst.setString(1, autoid);
							pst.executeUpdate();
							JOptionPane.showMessageDialog(null, "Data Deleted Success");
							clear();
							afloadTable();
							//pst=con.prepareStatement("update Customer set Name=?,
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		
		afback=new JButton("Back");
		afback.setForeground(Color.BLACK);
		afback.setFont(new Font("FangSong",Font.PLAIN,18));
		afback.setBorder(BorderFactory.createEmptyBorder());
		afback.setContentAreaFilled(false);
		afback.setBounds(1240, 30, 150, 30);
		panel6.add(afback);
//		Action Performed;
		// ActionListener for the 'Back' button to navigate to the Admin_Home frame.
		afback.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			new	Admin_Home();
			aff.setVisible(false);	
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
		// ActionListener for the 'Search' button to filter the JTable.
		searchbutton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
	    	Searchwar();
	        clear();
	        }
	    });
		
		aff.setVisible(true);  //	frame Visible here	
	}
}