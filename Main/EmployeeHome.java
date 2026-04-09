package Main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeHome extends JFrame{

    /**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	JPanel sidebarPanel,mainContentPanel;
    CardLayout cardLayout;

    // Names for the CardLayout views
    private static final String VIEW_TABLE = "Table";
    private static final String VIEW_ADD_EMPLOYEE = "AddEmployee";
    private static final String VIEW_ADD_SALARY = "AddSalary";
    private static final String VIEW_ADD_ATTENDANCE = "AddAttendance";
    private static final String VIEW_LOGOUT = "Logout";// Placeholder for an action

    // 2. Add Buttons
    String[] buttonLabels = {"👥 Add Employees", "💵 Add Salary", "🕓 Add Attendance", "👁️ View", "🚪 Logout"};

    // Define Column Names and Data
    String[] columnNames = {"Name", "Employee ID", "Department", "Salary", "Status"};
    Object[][] data = {
        {"John Doe", "EMP001", "Sales", "$75,000", "Active"},
        {"Jane Smith", "EMP002", "Marketing", "$60,000", "Active"},
        {"Peter Jones", "EMP003", "Specialist", "$60,000", "Active"},
        {"Mary Brown", "EMP004", "HR", "$50,000", "Active"},
        {"David Green", "EMP005", "Sales", "$90,000", "Intern"},
        {"Laura White", "EMP006", "Intern", "$40,000", "Active"}
    };
    DefaultTableModel model;
    JTable table;
    
    public EmployeeHome() {
        // --- Frame Setup ---
        setTitle("Management Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLayout(new BorderLayout());

        // --- 1. Create and add the Left Sidebar Panel (WEST) ---
        sidebarPanel = createSidebar();
        add(sidebarPanel, BorderLayout.WEST);

        // --- 2. Setup the Main Content Panel with CardLayout (CENTER) ---
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);

        // Add the different views/cards to the main content panel
        mainContentPanel.add(createAddEmployeePanel(), VIEW_ADD_EMPLOYEE);
        mainContentPanel.add(createAddSalaryPanel(), VIEW_ADD_SALARY);
        mainContentPanel.add(createAddAttendancePanel(), VIEW_ADD_ATTENDANCE);
        mainContentPanel.add(createTablePanel(), VIEW_TABLE); // Default view
        
        // Add the main content panel to the frame center
        add(mainContentPanel, BorderLayout.CENTER);

        // --- Finalize Frame ---
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // --- Method to create the Sidebar (WEST) ---
    private JPanel createSidebar() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.DARK_GRAY);
        panel.setPreferredSize(new Dimension(250, getHeight()));

        JLabel TitleLabel = new JLabel("⚙️ Management");
        TitleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        TitleLabel.setForeground(Color.white);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        TitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(TitleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));

        for (String text : buttonLabels) {
            JButton button = new JButton(text);
            button.setFont(new Font("SansSerif", Font.PLAIN, 14));
            button.setMaximumSize(new Dimension(200, 40));
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setBackground(new Color(50, 150, 180));
            button.setForeground(Color.WHITE);
            button.setOpaque(true);
            button.setBorderPainted(false);
            
            // *** Add ActionListener to the button ***
            button.addActionListener(new SidebarButtonListener(text)); 

            panel.add(button);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        panel.add(Box.createVerticalGlue());
        return panel;
    }

    // --- Method to create the Main Table Panel (VIEW: VIEW_TABLE) ---
    private JScrollPane createTablePanel() {
        // Create the Table Model and Table
        model = new DefaultTableModel(data, columnNames);
        table = new JTable(model);
        
        table.setAutoCreateRowSorter(true); 
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        // Put the table inside a ScrollPane
        return new JScrollPane(table);
    }
    
    // --- Placeholder Panel for Add Employee (VIEW: VIEW_ADD_EMPLOYEE) ---
    private JPanel createAddEmployeePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.lightGray);
        JLabel label = new JLabel("Add New Employee Form Goes Here");
        label.setFont(new Font("SansSerif", Font.BOLD, 24));
        panel.add(label);
        return panel;
    }

    // --- Placeholder Panel for Add Salary (VIEW: VIEW_ADD_SALARY) ---
    private JPanel createAddSalaryPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 230, 200));
        JLabel label = new JLabel("Add Salary/Payroll Management Goes Here");
        label.setFont(new Font("SansSerif", Font.BOLD, 24));
        panel.add(label);
        return panel;
    }

    // --- Placeholder Panel for Add Attendance (VIEW: VIEW_ADD_ATTENDANCE) ---
    private JPanel createAddAttendancePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(200, 255, 230));
        JLabel label = new JLabel("Attendance Tracking Form Goes Here");
        label.setFont(new Font("SansSerif", Font.BOLD, 24));
        panel.add(label);
        return panel;
    }

    // --- ActionListener Class for Sidebar Buttons ---
    private class SidebarButtonListener implements ActionListener {
        private String buttonText;

        public SidebarButtonListener(String text) {
            this.buttonText = text;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            
            switch (buttonText) {
                case "👥 Add Employees":
                    // Show the Add Employee Panel
                    cardLayout.show(mainContentPanel, VIEW_ADD_EMPLOYEE);
                    break;
                case "💵 Add Salary":
                    // Show the Add Salary Panel
                    cardLayout.show(mainContentPanel, VIEW_ADD_SALARY);
                    break;
                case "🕓 Add Attendance":
                    // Show the Add Attendance Panel
                    cardLayout.show(mainContentPanel, VIEW_ADD_ATTENDANCE);
                    break;
                case "👁️ View":
                    // Show the default Employee Table
                    cardLayout.show(mainContentPanel, VIEW_TABLE);
                    break;
                case "🚪 Logout":
                    // Close the current frame and open a new Signup/Login frame
                    // For now, we'll just print a message and dispose.
                    System.out.println("Logging out and showing Signup Panel...");
                    JOptionPane.showMessageDialog(EmployeeHome.this, 
                                                  "Logging Out! (A Signup Panel would open now)", 
                                                  "Logout", 
                                                  JOptionPane.INFORMATION_MESSAGE);
                    // Dispose of the current frame (the dashboard)
                    EmployeeHome.this.dispose(); 
                    // You would typically call a new SignupFrame() here
                    // new SignupFrame().setVisible(true); 
                    break;
            }
        }
    }

    // --- Main Method to run the application ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(EmployeeHome::new);
    }
}
