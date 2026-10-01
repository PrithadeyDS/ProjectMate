package projectmate;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Dashboard extends JFrame {

    public Dashboard() {

        setTitle("ProjectMate");
        setSize(800, 600);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);


        JPanel mainPanel =
                new JPanel(
                        new BorderLayout()
                );

        mainPanel.setBackground(
                new Color(245, 247, 250)
        );


        // HEADER

        JPanel header =
                new JPanel();

        header.setLayout(
                new BoxLayout(
                        header,
                        BoxLayout.Y_AXIS
                )
        );

        header.setBackground(
                new Color(40, 55, 90)
        );

        header.setBorder(
                new EmptyBorder(
                        25,
                        20,
                        25,
                        20
                )
        );


        JLabel title =
                new JLabel(
                        "ProjectMate"
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        32
                )
        );

        title.setForeground(
                Color.WHITE
        );

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        JLabel welcome =
                new JLabel(
                        "Welcome, "
                        + Session.currentUserName
                );

        welcome.setForeground(
                Color.WHITE
        );

        welcome.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        16
                )
        );

        welcome.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        JLabel subtitle =
                new JLabel(
                        "Find Projects. Find Skills. Build Together."
                );

        subtitle.setForeground(
                new Color(220, 225, 235)
        );

        subtitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        header.add(title);

        header.add(
                Box.createVerticalStrut(8)
        );

        header.add(welcome);

        header.add(
                Box.createVerticalStrut(5)
        );

        header.add(subtitle);


        mainPanel.add(
                header,
                BorderLayout.NORTH
        );


        // BUTTON AREA

        JPanel centerPanel =
                new JPanel(
                        new GridLayout(
                                3,
                                2,
                                20,
                                20
                        )
                );


        centerPanel.setBorder(
                new EmptyBorder(
                        40,
                        60,
                        40,
                        60
                )
        );


        centerPanel.setBackground(
                new Color(245, 247, 250)
        );


        JButton createButton =
                createButton(
                        "Create Project",
                        "Post a new project idea"
                );


        JButton browseButton =
                createButton(
                        "Browse Projects",
                        "Find projects by skills"
                );


        JButton applicationsButton =
                createButton(
                        "Applications",
                        "Accept or reject applicants"
                );


        JButton teamButton =
                createButton(
                        "My Team",
                        "View your project teams"
                );


        JButton profileButton =
                createButton(
                        "My Profile",
                        "View student information"
                );


        JButton logoutButton =
                createButton(
                        "Logout",
                        "Return to login"
                );


        centerPanel.add(createButton);
        centerPanel.add(browseButton);

        centerPanel.add(applicationsButton);
        centerPanel.add(teamButton);

        centerPanel.add(profileButton);
        centerPanel.add(logoutButton);


        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );


        // BUTTON ACTIONS

        createButton.addActionListener(
                e -> new CreateProject()
        );


        browseButton.addActionListener(
                e -> new BrowseProjects()
        );


        applicationsButton.addActionListener(
                e -> new Applications()
        );


        teamButton.addActionListener(
                e -> new MyTeam()
        );


        profileButton.addActionListener(
                e -> new Profile()
        );


        logoutButton.addActionListener(

                e -> {

                    int choice =
                            JOptionPane.showConfirmDialog(
                                    this,
                                    "Do you want to logout?",
                                    "Logout",
                                    JOptionPane.YES_NO_OPTION
                            );


                    if (
                            choice ==
                            JOptionPane.YES_OPTION
                    ) {

                        Session.currentUserId = 0;

                        Session.currentUserName = null;

                        Session.currentUserEmail = null;

                        dispose();

                        new Login();
                    }
                }
        );


        add(mainPanel);

        setVisible(true);
    }


    private JButton createButton(
            String title,
            String subtitle
    ) {

        JButton button =
                new JButton(

                        "<html><center>" +

                        "<b>" +
                        title +
                        "</b><br>" +

                        "<small>" +
                        subtitle +
                        "</small>" +

                        "</center></html>"

                );


        button.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        16
                )
        );


        button.setBackground(
                Color.WHITE
        );


        button.setForeground(
                new Color(
                        40,
                        55,
                        90
                )
        );


        button.setFocusPainted(false);


        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );


        return button;
    }
}