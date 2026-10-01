package projectmate;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;

public class Register extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JTextField departmentField;
    private JTextField yearField;
    private JPasswordField passwordField;

    public Register() {

        setTitle("ProjectMate - Register");
        setSize(520, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(25, 35, 25, 35));
        mainPanel.setBackground(new Color(245, 247, 250));

        // ---------------- HEADER ----------------

        JLabel heading = new JLabel(
                "CREATE ACCOUNT",
                SwingConstants.CENTER
        );

        heading.setFont(
                new Font("Arial", Font.BOLD, 28)
        );

        heading.setForeground(
                new Color(40, 55, 90)
        );

        heading.setBorder(
                new EmptyBorder(0, 0, 25, 0)
        );

        mainPanel.add(
                heading,
                BorderLayout.NORTH
        );

        // ---------------- FORM ----------------

        JPanel formPanel = new JPanel(
                new GridBagLayout()
        );

        formPanel.setBackground(
                new Color(245, 247, 250)
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(10, 10, 10, 10);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1.0;


        // NAME

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;

        formPanel.add(
                new JLabel("Name:"),
                gbc
        );

        gbc.gridx = 1;
        gbc.weightx = 0.7;

        nameField =
                new JTextField(20);

        formPanel.add(
                nameField,
                gbc
        );


        // EMAIL

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;

        formPanel.add(
                new JLabel("Email:"),
                gbc
        );

        gbc.gridx = 1;
        gbc.weightx = 0.7;

        emailField =
                new JTextField(20);

        formPanel.add(
                emailField,
                gbc
        );


        // DEPARTMENT

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;

        formPanel.add(
                new JLabel("Department:"),
                gbc
        );

        gbc.gridx = 1;
        gbc.weightx = 0.7;

        departmentField =
                new JTextField(20);

        formPanel.add(
                departmentField,
                gbc
        );


        // YEAR

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;

        formPanel.add(
                new JLabel("Year:"),
                gbc
        );

        gbc.gridx = 1;
        gbc.weightx = 0.7;

        yearField =
                new JTextField(20);

        formPanel.add(
                yearField,
                gbc
        );


        // PASSWORD

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.3;

        formPanel.add(
                new JLabel("Password:"),
                gbc
        );

        gbc.gridx = 1;
        gbc.weightx = 0.7;

        passwordField =
                new JPasswordField(20);

        formPanel.add(
                passwordField,
                gbc
        );


        // REGISTER BUTTON

        JButton registerButton =
                new JButton("Create Account");

        registerButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        15
                )
        );

        registerButton.setBackground(
                new Color(40, 55, 90)
        );

        registerButton.setForeground(
                Color.WHITE
        );

        registerButton.setFocusPainted(false);


        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;

        gbc.insets =
                new Insets(25, 10, 10, 10);

        formPanel.add(
                registerButton,
                gbc
        );


        mainPanel.add(
                formPanel,
                BorderLayout.CENTER
        );


        // ---------------- REGISTER ACTION ----------------

        registerButton.addActionListener(
                e -> registerUser()
        );


        add(mainPanel);

        setVisible(true);
    }


    private void registerUser() {

        String name =
                nameField.getText().trim();

        String email =
                emailField.getText().trim();

        String department =
                departmentField.getText().trim();

        String yearText =
                yearField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );


        if (
                name.isEmpty()
                || email.isEmpty()
                || department.isEmpty()
                || yearText.isEmpty()
                || password.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields."
            );

            return;
        }


        try {

            int year =
                    Integer.parseInt(
                            yearText
                    );


            Connection con =
                    DBConnection.getConnection();


            String sql =
                    "INSERT INTO users " +
                    "(name, email, department, year, password) " +
                    "VALUES (?, ?, ?, ?, ?)";


            PreparedStatement pst =
                    con.prepareStatement(sql);


            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, department);
            pst.setInt(4, year);
            pst.setString(5, password);


            pst.executeUpdate();


            JOptionPane.showMessageDialog(
                    this,
                    "Account Created Successfully!"
            );


            pst.close();
            con.close();

            dispose();


        } catch (
                NumberFormatException e
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Year must be a number."
            );


        } catch (
                SQLIntegrityConstraintViolationException e
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "This email is already registered."
            );


        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + e.getMessage()
            );

            e.printStackTrace();
        }
    }
}