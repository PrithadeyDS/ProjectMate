
package projectmate;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.sql.*;

public class Login extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;

    public Login() {

        setTitle("ProjectMate | Sign In");
        setSize(950, 620);
        setMinimumSize(new Dimension(800, 550));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main background

        JPanel root = new JPanel(new GridLayout(1, 2));
        root.setBackground(Theme.CREAM);

        // LEFT SECTION

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Theme.CREAM);
        leftPanel.setLayout(new BoxLayout(
                leftPanel, BoxLayout.Y_AXIS));

        leftPanel.setBorder(new EmptyBorder(85, 55, 60, 40));

        JLabel brand = new JLabel("ProjectMate");
        brand.setFont(new Font("Serif", Font.BOLD, 34));
        brand.setForeground(Theme.FOREST);
        brand.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel line = new JLabel(
                "STUDENT COLLABORATION PLATFORM");

        line.setFont(new Font("SansSerif", Font.BOLD, 10));
        line.setForeground(Theme.BROWN);
        line.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel heading = new JLabel(
                "<html>Find your team.<br>Build your ideas.</html>");

        heading.setFont(new Font("Serif", Font.BOLD, 36));
        heading.setForeground(Theme.FOREST);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel description = new JLabel(
                "<html>Connect with students, discover projects<br>"
                + "and turn your ideas into something meaningful."
                + "</html>");

        description.setFont(Theme.NORMAL);
        description.setForeground(Theme.BROWN);
        description.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel accent = new JPanel();
        accent.setBackground(Theme.SAGE);
        accent.setMaximumSize(new Dimension(55, 4));
        accent.setAlignmentX(Component.LEFT_ALIGNMENT);

        leftPanel.add(brand);
        leftPanel.add(Box.createVerticalStrut(12));
        leftPanel.add(line);
        leftPanel.add(Box.createVerticalStrut(65));
        leftPanel.add(accent);
        leftPanel.add(Box.createVerticalStrut(25));
        leftPanel.add(heading);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(description);

        // RIGHT SECTION

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Theme.SAGE);

        JPanel card = new JPanel();
        card.setBackground(Theme.WHITE);

        card.setLayout(new BoxLayout(
                card, BoxLayout.Y_AXIS));

        card.setBorder(new CompoundBorder(
                new LineBorder(new Color(225, 225, 215), 1),
                new EmptyBorder(38, 35, 35, 35)
        ));

        card.setPreferredSize(new Dimension(350, 410));

        JLabel welcome = new JLabel("Welcome back");
        welcome.setFont(new Font("Serif", Font.BOLD, 29));
        welcome.setForeground(Theme.FOREST);

        JLabel instruction = new JLabel(
                "Sign in to continue to your account.");

        instruction.setFont(Theme.NORMAL);
        instruction.setForeground(Theme.BROWN);

        JLabel emailLabel = new JLabel("Email address");
        JLabel passwordLabel = new JLabel("Password");

        styleLabel(emailLabel);
        styleLabel(passwordLabel);

        emailField = new JTextField();
        passwordField = new JPasswordField();

        styleField(emailField);
        styleField(passwordField);

        JButton loginButton = new JButton("Sign In");

        loginButton.setFont(Theme.BUTTON);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(Theme.FOREST);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(new EmptyBorder(12, 15, 12, 15));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        loginButton.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 46));

        JButton registerButton =
                new JButton("Create a new account");

        registerButton.setFont(Theme.BUTTON);
        registerButton.setForeground(Theme.FOREST);
        registerButton.setBackground(Theme.WHITE);
        registerButton.setBorderPainted(false);
        registerButton.setFocusPainted(false);
        registerButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add components to card

        card.add(welcome);
        card.add(Box.createVerticalStrut(8));
        card.add(instruction);

        card.add(Box.createVerticalStrut(32));

        card.add(emailLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(emailField);

        card.add(Box.createVerticalStrut(20));

        card.add(passwordLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(passwordField);

        card.add(Box.createVerticalStrut(28));

        card.add(loginButton);

        card.add(Box.createVerticalStrut(18));

        JLabel newUser = new JLabel("New to ProjectMate?");
        newUser.setFont(Theme.NORMAL);
        newUser.setForeground(Theme.BROWN);
        
        newUser.setCursor(
        	    Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        	);

        	newUser.addMouseListener(
        	    new java.awt.event.MouseAdapter() {

        	        @Override
        	        public void mouseClicked(
        	                java.awt.event.MouseEvent e) {

        	            new Register();
        	        }
        	    }
        	);

        card.add(newUser);
        card.add(Box.createVerticalStrut(3));
        card.add(registerButton);

        rightPanel.add(card);

        // Main layout

        root.add(leftPanel);
        root.add(rightPanel);

        add(root);

        // Button functionality

        loginButton.addActionListener(e -> login());

        registerButton.addActionListener(e -> {
                 new Register();
        });

        setVisible(true);
    }

    private void styleLabel(JLabel label) {

        label.setFont(Theme.BUTTON);
        label.setForeground(Theme.FOREST);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private void styleField(JTextField field) {

        field.setFont(Theme.NORMAL);
        field.setForeground(Theme.TEXT);
        field.setBackground(Theme.WHITE);

        field.setBorder(new CompoundBorder(
                new LineBorder(Theme.SAGE, 1),
                new EmptyBorder(10, 10, 10, 10)
        ));

        field.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 43));

        field.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    // DATABASE LOGIN - ORIGINAL FUNCTIONALITY

    private void login() {

        String email = emailField.getText().trim();

        String password = new String(
                passwordField.getPassword());

        String sql =
                "SELECT user_id, name, email " +
                "FROM users " +
                "WHERE email = ? AND password = ?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql)
        ) {

            pst.setString(1, email);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                Session.currentUserId =
                        rs.getInt("user_id");

                Session.currentUserName =
                        rs.getString("name");

                Session.currentUserEmail =
                        rs.getString("email");

                JOptionPane.showMessageDialog(
                        this,
                        "Welcome " + Session.currentUserName + "!"
                );

                dispose();
                new Dashboard();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid email or password"
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login Error: " + e.getMessage()
            );

            e.printStackTrace();
        }
    }
}
