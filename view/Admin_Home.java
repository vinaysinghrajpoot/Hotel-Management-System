package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Admin_Home {

	// JFrame is the main window of the application.
	JFrame amf;
	// JLabel to display the title "Admin Home".
	JLabel addtitle;
	// JPanels are containers to organize components.
	JPanel panel3, panel3_1, panel;
	// JButtons for user interaction.
	JButton customer, addroom, addfood, addemployee, attendence, logoutbt, Backbutton;   
	// String to store the file path of the background image.
	String imageFilePath = "C:/Users/ACER/MY_J2SE/Hotel-Project/Img/Admin.png";
	// Array to hold the background image. An array is used to make it effectively 'final' and mutable within the inner class.
	final Image[] backgroundImage = {null};
	
	public Admin_Home() {
		
		// Constructor calls the frame() method to build the GUI.
		frame();
		
	}
    
	public void frame() {
		
		// Initialize the JFrame.
		amf = new JFrame();
		amf.setTitle("Admin Home");
		amf.setLayout(null); // Use a null layout for absolute positioning of components.
		amf.setSize(2000, 1550); // Set the window size.
		amf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the application when the window is closed.
		
//		JPanels;
		// panel3 is the main content panel.
		panel3 = new JPanel();
		panel3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null)); // Set a lowered etched border.
		panel3.setBounds(200, 00, 1663, 950); // Set position and size.
		panel3.setBackground(Color.lightGray);
		panel3.setLayout(null); // Use null layout for this panel.
		amf.add(panel3);
		
		// panel3_1 is the sidebar panel, using a GridLayout.
		panel3_1 = new JPanel(new GridLayout(5, 1));
		panel3_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel3_1.setSize(200, 735);
		panel3_1.setBackground(Color.black);
		amf.add(panel3_1, BorderLayout.WEST); // Add the panel to the WEST of the frame's BorderLayout (though the frame is set to null layout, this seems to be a remnant or intended for a different layout manager).
		
//		PNG, JLabel, JtextFields, JButtons, ;
		// Initialize and configure the title label.
		addtitle = new JLabel("Admin Home");
		addtitle.setForeground(Color.DARK_GRAY);
		addtitle.setFont(new Font("Castellar", Font.BOLD, 40));
		addtitle.setBounds(370, 00, 700, 150);
		panel3.add(addtitle);
				
//		IMAGE SET					
		// panel is a custom JPanel for drawing the background image.
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
		panel3.add(panel);
		panel.setBounds(280, 40, 60, 60);		
		try {
            // Load the image from the specified file path.
            backgroundImage[0] = ImageIO.read(new File(imageFilePath));
        } catch (IOException e) {
            e.printStackTrace();
            // Print an error message if the image fails to load.
            System.err.println("Error loading image from path: " + imageFilePath);
        }
		
		// Initialize and configure the "Add Customer" button.
		customer = new JButton("Add Customer");
		customer.setBackground(Color.DARK_GRAY);
		customer.setFont(new Font("Times New Roman", Font.BOLD, 15));
		customer.setForeground(Color.white);
		panel3_1.add(customer);
		
//		Creating Action Performed 
		// Add an ActionListener to the "Add Customer" button.
		customer.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			// Open a new customer window and hide the current admin home window.
			new customer();
			amf.setVisible(false);
			
			}
		});
		
		// Initialize and configure the "Add Room" button.
		addroom = new JButton("Add Room");
		addroom.setFont(new Font("Times New Roman", Font.BOLD, 17));
		addroom.setBackground(Color.DARK_GRAY);
		addroom.setForeground(Color.white);
		panel3_1.add(addroom);
		
//		Creating Action Performed 
		// Add an ActionListener to the "Add Room" button.
		addroom.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			// Open a new Add_Room window and hide the current window.
			new Add_Room();
			amf.setVisible(false);
			}
		});
		
		// Initialize and configure the "Add Food" button.
		addfood = new JButton("Add Food");
		addfood.setFont(new Font("Times New Roman", Font.BOLD, 17));
		addfood.setBackground(Color.DARK_GRAY);
		addfood.setForeground(Color.white);
		panel3_1.add(addfood);

//		Creating Action Performed 
		// Add an ActionListener to the "Add Food" button.
		addfood.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			// Open a new Add_Food window and hide the current window.
			new Add_Food();
			amf.setVisible(false);
			}
		});
		
		// Initialize and configure the "Add Employee" button.
		addemployee = new JButton("Add Employee");
		addemployee.setFont(new Font("Times New Roman", Font.BOLD, 17));
		addemployee.setBackground(Color.DARK_GRAY);
		addemployee.setForeground(Color.white);
		panel3_1.add(addemployee);
		
//		Creating Action Performed 
		// Add an ActionListener to the "Add Employee" button.
		addemployee.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			// Open a new Employee window and hide the current window.
			new Employee();
			amf.setVisible(false);
			}
		});
		
		// Initialize and configure the "Attendance" button.
		attendence = new JButton("Attendance");
		attendence.setFont(new Font("Times New Roman", Font.BOLD, 17));
		attendence.setBackground(Color.DARK_GRAY);
		attendence.setForeground(Color.white);	
		panel3_1.add(attendence);
		
//		Creating Action Performed 
		// Add an ActionListener to the "Attendance" button.
		attendence.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			// Open a new attendance window and hide the current window.
			new attendance();
			amf.setVisible(false);
			}
		});
		
		// Initialize and configure the "Logout" button on the main panel.
		logoutbt = new JButton("Logout");
		logoutbt.setForeground(Color.red);
		logoutbt.setFont(new Font("FangSong", Font.PLAIN, 20));
		logoutbt.setBorder(BorderFactory.createEmptyBorder());
		logoutbt.setContentAreaFilled(false);
		logoutbt.setBounds(1210, 40, 150, 30);
		panel3.add(logoutbt);
		
		// Add an ActionListener to the first "Logout" button.
		logoutbt.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			// Show a confirmation dialog box for logging out.
			int result = JOptionPane.showConfirmDialog(null, "Sure? You want to Logout?", "Logout",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if (result == JOptionPane.YES_OPTION) {
			
				// If the user confirms, open the login page and hide the current window.
				new loginpain();
				amf.setVisible(false);
				}
			}			
		});
		// Initialize and configure the "Backbutton" which also performs a logout action.
		Backbutton = new JButton("Logout");
		Backbutton.setForeground(Color.BLACK);
		Backbutton.setFont(new Font("FangSong", Font.PLAIN, 18));
		Backbutton.setBorder(BorderFactory.createEmptyBorder());
		Backbutton.setContentAreaFilled(false);
		Backbutton.setBounds(1020, 30, 150, 30);
		panel3.add(Backbutton);
	
//		Action Performed;
		// Add an ActionListener to the "Backbutton".
		Backbutton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
				
			// Open the login page and hide the current window.
			new loginpain();
			amf.setVisible(false);	
			}
		});
		
		// Make the main frame visible.
		amf.setVisible(true);
		}
	}