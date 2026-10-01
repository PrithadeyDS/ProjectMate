package projectmate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class BrowseProjects extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JTextField searchField;

    public BrowseProjects() {

        setTitle("Browse Projects");
        setSize(1000, 550);
        setLocationRelativeTo(null);

        // ---------- HEADING ----------

        JLabel heading = new JLabel(
                "BROWSE PROJECTS",
                SwingConstants.CENTER
        );

        heading.setFont(
                new Font("Arial", Font.BOLD, 24)
        );


        // ---------- SEARCH AREA ----------

        JPanel searchPanel = new JPanel();

        JLabel searchLabel =
                new JLabel("Search:");

        searchField =
                new JTextField(20);

        JButton searchButton =
                new JButton("Search");

        JButton showAllButton =
                new JButton("Show All");

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(showAllButton);


        // ---------- TOP PANEL ----------

        JPanel topPanel =
                new JPanel(
                        new BorderLayout()
                );

        topPanel.add(
                heading,
                BorderLayout.NORTH
        );

        topPanel.add(
                searchPanel,
                BorderLayout.SOUTH
        );

        add(
                topPanel,
                BorderLayout.NORTH
        );


        // ---------- TABLE ----------

        model = new DefaultTableModel(

                new String[]{
                        "ID",
                        "Project",
                        "Description",
                        "Required Skills",
                        "Status"
                },

                0
        );


        table =
                new JTable(model);

        table.setRowHeight(25);


        add(
                new JScrollPane(table),
                BorderLayout.CENTER
        );


        // ---------- APPLY BUTTON ----------

        JButton applyButton =
                new JButton(
                        "Apply to Selected Project"
                );


        add(
                applyButton,
                BorderLayout.SOUTH
        );


        // Load everything initially

        loadProjects("");


        // ---------- BUTTON ACTIONS ----------

        searchButton.addActionListener(

                e -> loadProjects(
                        searchField.getText().trim()
                )

        );


        showAllButton.addActionListener(

                e -> {

                    searchField.setText("");

                    loadProjects("");

                }

        );


        applyButton.addActionListener(

                e -> applyToProject()

        );


        setVisible(true);
    }


    // ==================================================
    // LOAD / SEARCH PROJECTS
    // ==================================================

    private void loadProjects(String search) {

        model.setRowCount(0);


        String sql =

                """
                SELECT
                    p.project_id,
                    p.title,
                    p.description,
                    p.status,

                    GROUP_CONCAT(
                        DISTINCT s.skill_name
                        SEPARATOR ', '
                    ) AS required_skills

                FROM projects p

                LEFT JOIN project_requirements pr
                    ON p.project_id = pr.project_id

                LEFT JOIN skills s
                    ON pr.skill_id = s.skill_id

                WHERE

                    p.title LIKE ?

                    OR p.description LIKE ?

                    OR EXISTS (

                        SELECT 1

                        FROM project_requirements pr2

                        JOIN skills s2
                            ON pr2.skill_id = s2.skill_id

                        WHERE
                            pr2.project_id = p.project_id

                        AND
                            s2.skill_name LIKE ?
                    )

                GROUP BY
                    p.project_id,
                    p.title,
                    p.description,
                    p.status
                """;


        try (

                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)

        ) {


            String searchPattern =
                    "%" + search + "%";


            pst.setString(
                    1,
                    searchPattern
            );

            pst.setString(
                    2,
                    searchPattern
            );

            pst.setString(
                    3,
                    searchPattern
            );


            ResultSet rs =
                    pst.executeQuery();


            while (rs.next()) {


                String skills =
                        rs.getString(
                                "required_skills"
                        );


                if (skills == null) {

                    skills =
                            "Not specified";

                }


                model.addRow(

                        new Object[]{

                                rs.getInt(
                                        "project_id"
                                ),

                                rs.getString(
                                        "title"
                                ),

                                rs.getString(
                                        "description"
                                ),

                                skills,

                                rs.getString(
                                        "status"
                                )

                        }

                );
            }


            rs.close();


        } catch (Exception e) {

            JOptionPane.showMessageDialog(

                    this,

                    "Error loading projects: "
                            + e.getMessage()

            );

            e.printStackTrace();
        }
    }


    // ==================================================
    // APPLY TO PROJECT
    // ==================================================

    private void applyToProject() {

        int selectedRow =
                table.getSelectedRow();


        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(

                    this,

                    "Please select a project first."

            );

            return;
        }


        int projectId =

                Integer.parseInt(

                        model.getValueAt(
                                selectedRow,
                                0
                        ).toString()

                );


        String message =

                JOptionPane.showInputDialog(

                        this,

                        "Why do you want to join this project?"

                );


        if (
                message == null ||
                message.trim().isEmpty()
        ) {

            return;
        }


        String sql =

                "INSERT INTO applications " +
                "(project_id, applicant_id, message) " +
                "VALUES (?, ?, ?)";


        try (

                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)

        ) {


            // Selected project

            pst.setInt(
                    1,
                    projectId
            );


            // Current user = user_id 1

            pst.setInt(
                    2,
                    1
            );


            pst.setString(
                    3,
                    message
            );


            pst.executeUpdate();


            JOptionPane.showMessageDialog(

                    this,

                    "Application Submitted Successfully!"

            );


        } catch (Exception e) {

            JOptionPane.showMessageDialog(

                    this,

                    "Error: "
                            + e.getMessage()

            );

            e.printStackTrace();
        }
    }
}