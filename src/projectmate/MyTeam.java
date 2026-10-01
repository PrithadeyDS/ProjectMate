package projectmate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class MyTeam extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public MyTeam() {

        setTitle("My Team");
        setSize(750, 450);
        setLocationRelativeTo(null);

        JLabel heading = new JLabel(
                "MY PROJECT TEAMS",
                SwingConstants.CENTER
        );

        heading.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        model = new DefaultTableModel(
                new String[]{
                        "Project",
                        "Team Member",
                        "Department",
                        "Year",
                        "Role"
                },
                0
        );

        table = new JTable(model);

        JButton refreshButton =
                new JButton("Refresh");

        add(heading, BorderLayout.NORTH);

        add(
                new JScrollPane(table),
                BorderLayout.CENTER
        );

        add(
                refreshButton,
                BorderLayout.SOUTH
        );

        loadTeam();

        refreshButton.addActionListener(
                e -> loadTeam()
        );

        setVisible(true);
    }

    private void loadTeam() {

        model.setRowCount(0);

        String sql =
                """
                SELECT
                    p.title,
                    u.name,
                    u.department,
                    u.year,
                    t.role

                FROM team_members t

                JOIN projects p
                ON t.project_id = p.project_id

                JOIN users u
                ON t.user_id = u.user_id
                """;

        try (
                Connection con =
                        DBConnection.getConnection();

                Statement st =
                        con.createStatement();

                ResultSet rs =
                        st.executeQuery(sql)
        ) {

            while (rs.next()) {

                model.addRow(
                        new Object[]{
                                rs.getString("title"),
                                rs.getString("name"),
                                rs.getString("department"),
                                rs.getInt("year"),
                                rs.getString("role")
                        }
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading team: "
                    + e.getMessage()
            );

            e.printStackTrace();
        }
    }
}