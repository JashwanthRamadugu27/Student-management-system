import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StudentGUI extends JFrame {

    private JTextField idField;
    private JTextField nameField;
    private JTextField courseField;
    private JTextField marksField;
    private JTable table;
    private DefaultTableModel model;
    private StudentDAO dao = new StudentDAO();

    public StudentGUI() {

        setTitle("Student Management System");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // ===== Top Panel =====
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        panel.add(new JLabel("ID:"));
        idField = new JTextField();
        panel.add(idField);

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Course:"));
        courseField = new JTextField();
        panel.add(courseField);

        panel.add(new JLabel("Marks:"));
        marksField = new JTextField();
        panel.add(marksField);

        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");

        panel.add(addBtn);
        panel.add(updateBtn);

        add(panel, BorderLayout.NORTH);

        // ===== Table =====
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Name", "Course", "Marks"});
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== Bottom Panel =====
        JPanel bottomPanel = new JPanel();
        JButton deleteBtn = new JButton("Delete");
        JButton viewBtn = new JButton("View All");

        bottomPanel.add(deleteBtn);
        bottomPanel.add(viewBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        // ===== Button Actions =====
        addBtn.addActionListener(e -> addStudent());
        updateBtn.addActionListener(e -> updateStudent());
        deleteBtn.addActionListener(e -> deleteStudent());
        viewBtn.addActionListener(e -> loadStudents());

        // ===== Row Click Auto Fill =====
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                idField.setText(model.getValueAt(row, 0).toString());
                nameField.setText(model.getValueAt(row, 1).toString());
                courseField.setText(model.getValueAt(row, 2).toString());
                marksField.setText(model.getValueAt(row, 3).toString());
            }
        });

        setVisible(true);

        // 🔥 Load data when GUI starts
        loadStudents();
    }

    // ===== ADD STUDENT =====
    private void addStudent() {
        try {
            int id = Integer.parseInt(idField.getText());

            if (dao.isIdExists(id)) {
                JOptionPane.showMessageDialog(this, "ID already exists!");
                return;
            }

            String name = nameField.getText();
            String course = courseField.getText();
            int marks = Integer.parseInt(marksField.getText());

            Student student = new Student(id, name, course, marks);
            dao.addStudent(student);

            JOptionPane.showMessageDialog(this, "Student Added Successfully!");

            loadStudents();

            clearFields();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid Input!");
        }
    }

    // ===== UPDATE STUDENT =====
    private void updateStudent() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String course = courseField.getText();
            int marks = Integer.parseInt(marksField.getText());

            dao.updateStudent(id, name, course, marks);

            JOptionPane.showMessageDialog(this, "Student Updated!");

            loadStudents();
            clearFields();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid Input!");
        }
    }

    // ===== DELETE STUDENT =====
    private void deleteStudent() {
        try {
            int id = Integer.parseInt(idField.getText());

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                dao.deleteStudent(id);
                JOptionPane.showMessageDialog(this, "Student Deleted!");
                loadStudents();
                clearFields();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Enter valid ID!");
        }
    }

    // ===== LOAD STUDENTS =====
    private void loadStudents() {

        model.setRowCount(0);

        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/studentdb",
                    "root",
                    "Sharma_27"
            );

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("course"),
                        rs.getInt("marks")
                });
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ===== CLEAR FIELDS =====
    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        courseField.setText("");
        marksField.setText("");
    }

    public static void main(String[] args) {
        new StudentGUI();
    }
}