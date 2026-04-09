package Date_Time;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class date_time {

    private JTextField dateField;
    private JTextField timeField;

    /**
     * Constructor that adds date and time display components to the provided panel.
     * @param panel The JPanel where the date and time will be displayed.
     */
    public date_time(JPanel panel) {
        // Initialize and add UI components to the passed panel
        addDateTimeComponents(panel);
        // Start the clock to display current date and time
        startClock();
    }

    private void addDateTimeComponents(JPanel panel) {
        JLabel dateLabel = new JLabel("Date:- ");
        dateLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        dateLabel.setBounds(30, 30, 150, 25);
        panel.add(dateLabel);

        JLabel timeLabel = new JLabel("Time:- ");
        timeLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        timeLabel.setBounds(30, 60, 150, 25);
        panel.add(timeLabel);

        dateField = new JTextField();
        dateField.setBounds(80, 32, 90, 22);
        dateField.setBackground(Color.yellow);
        dateField.setEditable(false);
        panel.add(dateField);

        timeField = new JTextField();
        timeField.setBounds(80, 62, 90, 22);
        timeField.setBackground(Color.yellow);
        timeField.setEditable(false);
        panel.add(timeField);
    }

    private void startClock() {
        // Set the current date (it does not need to be on a timer)
        dateField.setText(new SimpleDateFormat("dd-MM-YYYY").format(new Date()));

        // Use a Swing Timer to update the time every second
        new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat s = new SimpleDateFormat("hh:mm:ss a");
                timeField.setText(s.format(new Date()));
            }
        }).start();
    }

    // Public getters to allow other classes to retrieve the current date and time
    public String getCurrentDate() {
        return dateField.getText();
    }

    public String getCurrentTime() {
        return timeField.getText();
    }
}