package projectmate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Applications extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public Applications() {

        setTitle("Applications");
        setSize(850, 500);
        setLocationRelativeTo(null);

        JLabel heading = new JLabel(
                "PROJECT APPLICATIONS",
                SwingConstants.CENTER
        );

        heading.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        model = new DefaultTableModel(
                new String[]{
                        "Application ID",
                        "Project ID",
                        "Project",
                        "Applicant ID",
                        "Applicant",
                        "Message",
                        "Status"
                },
                0
        );

        table = new JTable(model);

        JButton acceptButton =
                new JButton("Accept");

        JButton rejectButton =
                new JButton("Reject");

        JButton refreshButton =
                new JButton("Refresh");

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(acceptButton);
        buttonPanel.add(rejectButton);
        buttonPanel.add(refreshButton);

        add(heading, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadApplications();

        acceptButton.addActionListener(
                e -> updateApplication("ACCEPTED")
        );

        rejectButton.addActionListener(
                e -> updateApplication("REJECTED")
        );

        refreshButton.addActionListener(
                e -> loadApplications()
        );

        setVisible(true);
    }

    private void loadApplications() {

        model.setRowCount(0);

        String sql =
                """
                SELECT
                    a.application_id,
                    a.project_id,
                    p.title,
                    a.applicant_id,
                    u.name,
                    a.message,
                    a.status
                FROM applications a
                JOIN projects p
                    ON a.project_id = p.project_id
                JOIN users u
                    ON a.applicant_id = u.user_id
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
                                rs.getInt("application_id"),
                                rs.getInt("project_id"),
                                rs.getString("title"),
                                rs.getInt("applicant_id"),
                                rs.getString("name"),
                                rs.getString("message"),
                                rs.getString("status")
                        }
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading applications: "
                            + e.getMessage()
            );

            e.printStackTrace();
        }
    }

    private void updateApplication(String newStatus) {

        int row = table.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Select an application first."
            );

            return;
        }

        int applicationId =
                Integer.parseInt(
                        model.getValueAt(row, 0).toString()
                );

        int projectId =
                Integer.parseInt(
                        model.getValueAt(row, 1).toString()
                );

        int applicantId =
                Integer.parseInt(
                        model.getValueAt(row, 3).toString()
                );

        try {

            Connection con =
                    DBConnection.getConnection();

            String updateSql =
                    "UPDATE applications " +
                    "SET status = ? " +
                    "WHERE application_id = ?";

            PreparedStatement pst =
                    con.prepareStatement(updateSql);

            pst.setString(1, newStatus);
            pst.setInt(2, applicationId);

            pst.executeUpdate();

            if (newStatus.equals("ACCEPTED")) {

                String checkSql =
                        "SELECT * FROM team_members " +
                        "WHERE project_id = ? AND user_id = ?";

                PreparedStatement check =
                        con.prepareStatement(checkSql);

                check.setInt(1, projectId);
                check.setInt(2, applicantId);

                ResultSet rs =
                        check.executeQuery();

                if (!rs.next()) {

                    String insertSql =
                            "INSERT INTO team_members " +
                            "(project_id, user_id, role) " +
                            "VALUES (?, ?, ?)";

                    PreparedStatement insert =
                            con.prepareStatement(insertSql);

                    insert.setInt(1, projectId);
                    insert.setInt(2, applicantId);
                    insert.setString(3, "Team Member");

                    insert.executeUpdate();
                    insert.close();
                }

                rs.close();
                check.close();
            }

            pst.close();
            con.close();

            JOptionPane.showMessageDialog(
                    this,
                    "Application " + newStatus
            );

            loadApplications();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + e.getMessage()
            );

            e.printStackTrace();
        }
    }
}