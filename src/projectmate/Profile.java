package projectmate;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;

public class Profile extends JFrame {

    private JLabel nameValue;
    private JLabel emailValue;
    private JLabel departmentValue;
    private JLabel yearValue;

    public Profile() {

        setTitle("ProjectMate - Profile");
        setSize(500, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        JLabel heading = new JLabel(
                "MY PROFILE",
                SwingConstants.CENTER
        );

        heading.setFont(
                new Font("Arial", Font.BOLD, 26)
        );

        heading.setForeground(
                new Color(40, 55, 90)
        );

        mainPanel.add(
                heading,
                BorderLayout.NORTH
        );


        JPanel profilePanel =
                new JPanel(
                        new GridLayout(
                                4,
                                2,
                                15,
                                20
                        )
                );

        profilePanel.setBorder(
                new EmptyBorder(
                        40,
                        20,
                        20,
                        20
                )
        );


        nameValue = new JLabel();
        emailValue = new JLabel();
        departmentValue = new JLabel();
        yearValue = new JLabel();


        profilePanel.add(
                new JLabel("Name:")
        );

        profilePanel.add(nameValue);


        profilePanel.add(
                new JLabel("Email:")
        );

        profilePanel.add(emailValue);


        profilePanel.add(
                new JLabel("Department:")
        );

        profilePanel.add(departmentValue);


        profilePanel.add(
                new JLabel("Year:")
        );

        profilePanel.add(yearValue);


        mainPanel.add(
                profilePanel,
                BorderLayout.CENTER
        );


        add(mainPanel);

        loadProfile();

        setVisible(true);
    }


    private void loadProfile() {

        String sql =
                "SELECT name, email, department, year " +
                "FROM users WHERE user_id = ?";

        try (

                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)

        ) {

            pst.setInt(
                    1,
                    Session.currentUserId
            );


            ResultSet rs =
                    pst.executeQuery();


            if (rs.next()) {

                nameValue.setText(
                        rs.getString("name")
                );

                emailValue.setText(
                        rs.getString("email")
                );

                departmentValue.setText(
                        rs.getString("department")
                );

                yearValue.setText(
                        String.valueOf(
                                rs.getInt("year")
                        )
                );

            }

            rs.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading profile: "
                    + e.getMessage()
            );

            e.printStackTrace();
        }
    }
}