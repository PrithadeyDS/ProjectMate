package projectmate;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class CreateProject extends JFrame {

    private JTextField titleField;
    private JTextArea descriptionArea;
    private JTextField skillsField;

    public CreateProject() {

        setTitle("Create Project");
        setSize(550, 500);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 1, 10, 10));

        JLabel heading = new JLabel(
                "CREATE A PROJECT",
                SwingConstants.CENTER
        );

        heading.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        JLabel titleLabel =
                new JLabel("Project Title:");

        titleField =
                new JTextField();

        JLabel descriptionLabel =
                new JLabel("Project Description:");

        descriptionArea =
                new JTextArea();

        JLabel skillsLabel =
                new JLabel(
                        "Required Skills (comma separated):"
                );

        skillsField =
                new JTextField();

        JButton createButton =
                new JButton("Create Project");

        panel.add(heading);
        panel.add(titleLabel);
        panel.add(titleField);
        panel.add(descriptionLabel);
        panel.add(new JScrollPane(descriptionArea));
        panel.add(skillsLabel);
        panel.add(skillsField);
        panel.add(createButton);

        add(panel);

        createButton.addActionListener(
                e -> createProject()
        );

        setVisible(true);
    }


    private void createProject() {

        String title =
                titleField.getText().trim();

        String description =
                descriptionArea.getText().trim();

        String skillsText =
                skillsField.getText().trim();


        if (
                title.isEmpty() ||
                description.isEmpty() ||
                skillsText.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields"
            );

            return;
        }


        try {

            Connection con =
                    DBConnection.getConnection();


            // INSERT PROJECT

            String projectSql =
                    "INSERT INTO projects " +
                    "(owner_id, title, description) " +
                    "VALUES (?, ?, ?)";


            PreparedStatement projectStmt =
                    con.prepareStatement(
                            projectSql,
                            Statement.RETURN_GENERATED_KEYS
                    );


            projectStmt.setInt(1, 1);

            projectStmt.setString(
                    2,
                    title
            );

            projectStmt.setString(
                    3,
                    description
            );


            projectStmt.executeUpdate();


            ResultSet generatedKeys =
                    projectStmt.getGeneratedKeys();


            int projectId = 0;


            if (generatedKeys.next()) {

                projectId =
                        generatedKeys.getInt(1);
            }


            // SPLIT SKILLS

            String[] skills =
                    skillsText.split(",");


            for (String skill : skills) {

                skill =
                        skill.trim();


                if (skill.isEmpty()) {
                    continue;
                }


                // ADD SKILL IF IT DOES NOT EXIST

                String insertSkillSql =
                        "INSERT IGNORE INTO skills " +
                        "(skill_name) VALUES (?)";


                PreparedStatement skillStmt =
                        con.prepareStatement(
                                insertSkillSql
                        );


                skillStmt.setString(
                        1,
                        skill
                );


                skillStmt.executeUpdate();


                // GET SKILL ID

                String findSkillSql =
                        "SELECT skill_id " +
                        "FROM skills " +
                        "WHERE skill_name = ?";


                PreparedStatement findStmt =
                        con.prepareStatement(
                                findSkillSql
                        );


                findStmt.setString(
                        1,
                        skill
                );


                ResultSet rs =
                        findStmt.executeQuery();


                if (rs.next()) {

                    int skillId =
                            rs.getInt(
                                    "skill_id"
                            );


                    // CONNECT SKILL TO PROJECT

                    String requirementSql =
                            "INSERT INTO " +
                            "project_requirements " +
                            "(project_id, skill_id) " +
                            "VALUES (?, ?)";


                    PreparedStatement reqStmt =
                            con.prepareStatement(
                                    requirementSql
                            );


                    reqStmt.setInt(
                            1,
                            projectId
                    );


                    reqStmt.setInt(
                            2,
                            skillId
                    );


                    reqStmt.executeUpdate();

                    reqStmt.close();
                }


                rs.close();
                findStmt.close();
                skillStmt.close();
            }


            JOptionPane.showMessageDialog(
                    this,
                    "Project Created Successfully!"
            );


            titleField.setText("");

            descriptionArea.setText("");

            skillsField.setText("");


            generatedKeys.close();

            projectStmt.close();

            con.close();


        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + e.getMessage()
            );

            e.printStackTrace();
        }
    }
}