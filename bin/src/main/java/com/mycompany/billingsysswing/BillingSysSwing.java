package com.mycompany.billingsysswing;

// Import necessary packages
import javax.swing.SwingUtilities;
import com.mycompany.billingsysswing.forms.RegistrationForm; // Import the RegistrationForm class
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class BillingSysSwing {
    public static void main(String[] args) {
        // Set the look and feel (optional)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Use SwingUtilities to ensure thread safety
        SwingUtilities.invokeLater(() -> {
            // Create an instance of RegistrationForm
            RegistrationForm registrationForm = new RegistrationForm();
            registrationForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensure the app exits on close
            registrationForm.setVisible(true); // Make the registration form visible
        });
    }
}

