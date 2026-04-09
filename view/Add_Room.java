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

import Utils.DatabaseUtils;

public class Add_Room {
	
	JFrame arf;
	JPanel panel5,panel,panelsearch;
	JLabel arTitel,arnolabel,arcategory,artype,arprice;
	private JTextField autoId,aroomnotf,arpricetf,searchwartf;
	JRadioButton Ac,NonAc;
	ButtonGroup arbuttongrup;
	JComboBox <String>artypechb;
		
	String imageFilePath = "C:/Users/ACER/MY_J2SE/Hotel-Project/Img/bed02.png";
	final Image[] backgroundImage = {null};
	
	JButton arback,save,delete,update,searchbutton;
	private JTable artable;
	JScrollPane arscrollPane;
	
//	Database Connection
  	Connection con = null;
  	PreparedStatement pst;
  	ResultSet rs;
  		
//	Calling Frame,Connection;
	public Add_Room(){
		
		addroom_frame();
		Connection();
		arloadTable();
		clear();
	}	
	
//	Create a Sign Up Clear text field method;
	public void clear() {
		
		autoId.setText("");
		aroomnotf.setText("");
		arbuttongrup.clearSelection();
		
		//
		if (artypechb.getItemCount() > 0) {
			artypechb.setSelectedIndex(0);  // Reset combo box
		    }
		artypechb.repaint();// Cle
		arpricetf.setText("");
		aroomnotf.requestFocus();
		
	}
	
	//	Create Connection
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
	
//	Load Table
		public void arloadTable() {
			try {
				pst = con.prepareStatement("Select * from addroom");
				rs = pst.executeQuery();
				artable.setBackground(Color.orange);
				artable.setModel(DatabaseUtils.resultSetToTableModel(rs));
			}catch(Exception e) {
					e.printStackTrace();
				}
			}
		
//		public void Searchwar() {
//		    try {
//		    	String name = searchwartf.getText();
//		    	if (name == null || name.isEmpty() || name.trim().isEmpty()) {
//			        pst = con.prepareStatement("SELECT * FROM Customer WHERE fullname=?");				
//			        searchwartf.requestFocus();
//					return;
//				}
//		    	else if (name == null || name.isEmpty() || name.trim().isEmpty()) {
//			        pst = con.prepareStatement("SELECT * FROM Customer WHERE autoId==4=?");				
//			        searchwartf.requestFocus();
//					return;
//				}
//		        pst = con.prepareStatement("SELECT * FROM addroom WHERE fullname=? ");
//		    	pst.setString(1, searchwartf.getText().trim());
//		        rs = pst.executeQuery();
//		        artable.setModel(DatabaseUtils.resultSetToTableModel(rs));
//		    } catch (Exception e) {
//		        e.printStackTrace();
//		    } 
//		}

//		  Method to search for customers in the database.
		 public void Searchwar() {
		     try {
		         // Get the search term from the search text field, removing leading/trailing spaces.
		         String searchTerm = searchwartf.getText().trim();

		         // Check if the search field is empty.
		         if (searchTerm.isEmpty()) {
		             // If it's empty, prepare a query to select all customers.
		             pst = con.prepareStatement("SELECT * FROM addroom");
		         } else {
		             // If there is a search term, prepare a query to search by name, contact, or check-in date.
		             pst = con.prepareStatement("SELECT * FROM addroom WHERE AddRoomNo LIKE ? OR AddRoomCategory = ? OR AddRoomPrice = ? OR AddRoomType = ?");
		             // Set the parameters for the query.
		             pst.setString(1, "%" + searchTerm + "%"); // '%' allows for partial matches on the AddRoomNo.
		             pst.setString(2, searchTerm); // Exact match for Add Room Category.
		             pst.setString(3, searchTerm); // Exact match for Add Room Price.
		             pst.setString(4, searchTerm); // Exact match for Add Room Type.
		         }
		         // Execute the query.
		         rs = pst.executeQuery();
		         // Update the table with the search results using a helper utility.
		         artable.setModel(DatabaseUtils.resultSetToTableModel(rs));
		     } catch (Exception e) {
		         // Print any error that occurs to the console.
		         e.printStackTrace();
		     }
		 }
		
//	Creating frame method;
	public void addroom_frame() {
		
		arf=new JFrame("Add Room");
		arf.setTitle("Add Room");
		arf.setLayout(new GridLayout(1,1));
		arf.setSize(2000,1550);
		arf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
//		JPanels;
		panel5=new JPanel();
		panel5.setBorder(new EtchedBorder(EtchedBorder.LOWERED,null,null));
		panel5.setSize(1365,750);
		panel5.setBackground(Color.lightGray);		
		panel5.setLayout(null);
		arf.add(panel5);		
		
//		PNG, JLabel, JtextFields, JButtons, ;
		arTitel=new JLabel("Add Room");
		arTitel.setForeground(Color.DARK_GRAY);
		arTitel.setFont(new Font("Castellar",Font.BOLD,40));
		arTitel.setBounds(580,00, 700, 150);
		panel5.add(arTitel);

		panelsearch=new JPanel();
		panelsearch.setBorder(new EtchedBorder(EtchedBorder.LOWERED,null,null));
		panelsearch.setBounds(990,120,320,40);
		panelsearch.setBackground(Color.red);		
		panelsearch.setLayout(null);
		panel5.add(panelsearch);
		
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
		panel5.add(panel);
		panel.setBounds(500, 40, 60, 60);		
		try {
            // Load the image from the specified path.
            backgroundImage[0] = ImageIO.read(new File(imageFilePath));
        } catch (IOException e) {
            e.printStackTrace();
            // Print an error if the image cannot be loaded.
            System.err.println("Error loading image from path: " + imageFilePath);
        }
		
		arnolabel=new JLabel("Add Room No:- ");
		arnolabel.setFont(new Font("Garamond",Font.BOLD,18));
		arnolabel.setBounds(60, 220, 150, 25);
		panel5.add(arnolabel);
		
		arcategory=new JLabel("Category:-");
		arcategory.setFont(new Font("Garamond",Font.BOLD,18));
		arcategory.setBounds(60, 285, 150, 25);
		panel5.add(arcategory);
		
		artype=new JLabel("Room Type:-");
		artype.setFont(new Font("Garamond",Font.BOLD,18));
		artype.setBounds(60, 350, 150, 25);
		panel5.add(artype);
		
		arprice=new JLabel("Room Price:-");
		arprice.setFont(new Font("Garamond",Font.BOLD,18));
		arprice.setBounds(60, 420, 150, 25);
		panel5.add(arprice);
		
    	autoId=new JTextField(20);
	    autoId.setEditable(false);
		autoId.setBounds(220, 195, 220, 22);
	    panel5.add(autoId);
		autoId.setVisible(false);

 		searchwartf=new JTextField(12);
 		searchwartf.setBounds(20,9,180,20);
 		panelsearch.add(searchwartf);
		
		aroomnotf=new JTextField(20);
		aroomnotf.setBounds(220, 222, 220, 22);
		panel5.add(aroomnotf);
		
		Ac=new JRadioButton("Ac");
		Ac.setBackground(null);
		Ac.setBounds(250, 287, 50, 30);
		panel5.add(Ac);
		
		NonAc=new JRadioButton("Non Ac");
		NonAc.setBackground(null);
		NonAc.setBounds(330, 287, 80, 30);
		panel5.add(NonAc);
		
		arbuttongrup=new ButtonGroup();
		arbuttongrup.add(Ac);
		arbuttongrup.add(NonAc);
				
		artypechb=new JComboBox<>(new String[]{"Please Select Room Type","Single bed ","Dauble bed","Delux","Twin","Suit"});
		artypechb.setBounds(220, 350, 220, 22);
		panel5.add(artypechb);
		
		arpricetf=new JTextField(10);
		arpricetf.setBounds(220, 422, 220, 22);
		panel5.add(arpricetf);
		
		//ScrollPane ///table
		arscrollPane = new JScrollPane();
		arscrollPane.setBounds(500,170,850,420);
		panel5.add(arscrollPane);
		//table
		artable = new JTable();
		artable.setDefaultEditor(Object.class, null);
		artable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int index = artable.getSelectedRow();
				TableModel model = artable.getModel();
				
				// ID NAME AGE CITY
				autoId.setText(model.getValueAt(index, 0).toString());
				aroomnotf.setText(model.getValueAt(index, 1).toString());
				
				String addroomcategory = model.getValueAt(index, 2).toString();
				for (Enumeration<AbstractButton> buttons = arbuttongrup.getElements(); buttons.hasMoreElements();)
				{
                    AbstractButton button = buttons.nextElement();
                    button.setSelected(button.getText().equalsIgnoreCase(addroomcategory));
                }
				artypechb.setSelectedItem(model.getValueAt(index, 3).toString());				
				arpricetf.setText(model.getValueAt(index, 4).toString());
			}
		});
		
		artable.setFont(new Font("Tahoma", Font.PLAIN, 14));
		artable.setRowHeight(30);
		arscrollPane.setViewportView(artable);
		
		save=new JButton("Save");
		save.setBackground(Color.green);
		save.setForeground(Color.white);
		save.setBounds(60, 590, 120, 25);
		panel5.add(save);
			//@Override
			save.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
//			public void actionPerformed(ActionEvent e){
					
				String id = autoId.getText();
				String addroomno = aroomnotf.getText();
				String addroomcategory = null;				
				if(Ac.isSelected()) {
					addroomcategory = Ac.getText();
		        }else if(NonAc.isSelected()) {
		        	addroomcategory = NonAc.getText();
		        }
				String addroomtype = (String)artypechb.getSelectedItem();
				String addroomprice = arpricetf.getText();
				
//				Create a Condections;
				if (addroomno == null || addroomno.isEmpty() || addroomno.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Add Room Number");
					aroomnotf.requestFocus();
					return;
				}
				if (addroomcategory == null || addroomcategory.isEmpty() || addroomcategory.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Select Room Category");
					return;
				}
				if ( addroomtype== "Please Select Room Type" ){ //ComboBox
					JOptionPane.showMessageDialog(null, "Please Select Room Type");
					return;
				}
				if (addroomprice == null || addroomprice.isEmpty() || addroomprice.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Room Price");
					arpricetf.requestFocus();
					return;
				}
				
//				Save the text field data into DBMS;				
				try {
                	String sql = ("insert into addroom(AddRoomNo,AddRoomCategory,AddRoomType,AddRoomPrice)values(?,?,?,?)");
                	pst=con.prepareStatement(sql);
                	pst.setString(1, addroomno);
                	pst.setString(2, addroomcategory);
                	pst.setString(3, addroomtype);
                	pst.setString(4, addroomprice);
                	pst.executeUpdate();
                	JOptionPane.showMessageDialog(null, "Data insert Success");
                	clear();
            		arloadTable();
                	//Call here the Signup home panel ;
                }catch (SQLException e1) {
    				e1.printStackTrace();
    			}					
			}			
		});		
		
		update=new JButton("Update");
		update.setBackground(Color.BLUE);
		update.setForeground(Color.white);
		update.setBounds(200, 590, 120, 25);
		panel5.add(update);

		update.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				// Update Details
				String autoid = autoId.getText();  
				String addroomno = aroomnotf.getText();
				String addroomcategory = null;				
				if(Ac.isSelected()) {
					addroomcategory = Ac.getText();
		        }else if(NonAc.isSelected()) {
		        	addroomcategory = NonAc.getText();
		        }
				String addroomtype = (String)artypechb.getSelectedItem();
				String addroomprice = arpricetf.getText();

				if (addroomno == null || addroomno.isEmpty() || addroomno.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Add Room Number");
					aroomnotf.requestFocus();
					return;
				}
				if (addroomcategory == null || addroomcategory.isEmpty() || addroomcategory.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Select Room Category");
					return;
				}
				
				if ( addroomtype== "Please Select Room Type" ){ //ComboBox
					JOptionPane.showMessageDialog(null, "Please Select Room Type");
					return;
				}
				if (addroomprice == null || addroomprice.isEmpty() || addroomprice.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Room Price");
					arpricetf.requestFocus();
					return;
				}
				
				if (!autoId.getText().isEmpty()) {
					try {
						pst=con.prepareStatement("update addroom set AddRoomNo=?,AddRoomCategory=?,AddRoomType=?, AddRoomPrice=? where autoId=?");
						pst.setString(1, addroomno);
	                	pst.setString(2, addroomcategory);
	                	pst.setString(3, addroomtype);
	                	pst.setString(4, addroomprice);
						pst.setString(5, autoid);
	                	pst.executeUpdate();
	                	JOptionPane.showMessageDialog(null, "Data Update Success");
	                	clear();
	            		arloadTable();
	                	
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});		
		
		delete=new JButton("Delete");
		delete.setBackground(Color.RED);
		delete.setForeground(Color.white);
		delete.setBounds(340, 590, 120, 25);
		panel5.add(delete);
		
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Delete Details
				String autoid = autoId.getText();
				if (!autoId.getText().isEmpty()){

					int result = JOptionPane.showConfirmDialog(null, "Sure? You want to Delete?", "Delete",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (result == JOptionPane.YES_OPTION) {
						try {
							pst = con.prepareStatement("delete from addroom where autoId=?");
							pst.setString(1, autoid);
							pst.executeUpdate();
							JOptionPane.showMessageDialog(null, "Data Deleted Success");
							clear();
							arloadTable();
							//pst=con.prepareStatement("update Customer set Name=?,
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		
		arback=new JButton("Back");
		arback.setForeground(Color.BLACK);
		arback.setFont(new Font("FangSong",Font.PLAIN,18));
		arback.setBorder(BorderFactory.createEmptyBorder());
		arback.setContentAreaFilled(false);
		arback.setBounds(1240, 30, 150, 30);
		panel5.add(arback);
		arback.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			new	Admin_Home();
			arf.setVisible(false);
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
		searchbutton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
	    	Searchwar();
	        clear();
	        }
	    });
		
		arf.setVisible(true);
	}
}