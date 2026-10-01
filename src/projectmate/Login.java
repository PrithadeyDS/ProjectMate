package projectmate;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;

public class Login extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;

    public Login() {

        setTitle("ProjectMate - Login");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBorder(
                new EmptyBorder(30, 50, 30, 50)
        );

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel heading =
                new JLabel("ProjectMate");

        heading.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        30
                )
        );

        heading.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel subtitle =
                new JLabel(
                        "Student Project Team Finder"
                );

        subtitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        emailField =
                new JTextField();

        passwordField =
                new JPasswordField();


        JButton loginButton =
                new JButton("Login");

        JButton registerButton =
                new JButton(
                        "Create New Account"
                );


        loginButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        registerButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        panel.add(heading);

        panel.add(
                Box.createVerticalStrut(5)
        );

        panel.add(subtitle);

        panel.add(
                Box.createVerticalStrut(30)
        );

        panel.add(
                new JLabel("Email:")
        );

        panel.add(emailField);

        panel.add(
                Box.createVerticalStrut(15)
        );

        panel.add(
                new JLabel("Password:")
        );

        panel.add(passwordField);

        panel.add(
                Box.createVerticalStrut(25)
        );

        panel.add(loginButton);

        panel.add(
                Box.createVerticalStrut(10)
        );

        panel.add(registerButton);


        add(panel);


        loginButton.addActionListener(
                e -> login()
        );

        registerButton.addActionListener(
                e -> new Register()
        );


        setVisible(true);
    }


    private void login() {

        String email =
                emailField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );


        String sql =
                "SELECT user_id, name, email " +
                "FROM users " +
                "WHERE email = ? AND password = ?";


        try (

                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)

        ) {

            pst.setString(
                    1,
                    email
            );

            pst.setString(
                    2,
                    password
            );


            ResultSet rs =
                    pst.executeQuery();


            if (rs.next()) {

                Session.currentUserId =
                        rs.getInt(
                                "user_id"
                        );

                Session.currentUserName =
                        rs.getString(
                                "name"
                        );

                Session.currentUserEmail =
                        rs.getString(
                                "email"
                        );


                JOptionPane.showMessageDialog(
                        this,
                        "Welcome "
                        + Session.currentUserName
                        + "!"
                );


                dispose();

                new Dashboard();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid email or password"
                );
            }


            rs.close();


        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login Error: "
                    + e.getMessage()
            );

            e.printStackTrace();
        }
    }
}