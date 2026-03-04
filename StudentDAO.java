import java.sql.*;

public class StudentDAO {

    private String url = "jdbc:mysql://localhost:3306/studentdb";
    private String user = "root";
    private String password = "Sharma_27";

    public boolean addStudent(Student student) {

        String query = "INSERT INTO students VALUES (?, ?, ?, ?)";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, student.getId());
            ps.setString(2, student.getName());
            ps.setString(3, student.getCourse());
            ps.setInt(4, student.getMarks());

            ps.executeUpdate();
            conn.close();

            return true;

        } catch (SQLException e) {

            if (e.getErrorCode() == 1062) {
                System.out.println("ID already exists!");
            } else {
                e.printStackTrace();
            }

            return false;
        }
    }

    public boolean isIdExists(int id) {

        String query = "SELECT id FROM students WHERE id = ?";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            boolean exists = rs.next();
            conn.close();

            return exists;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void updateStudent(int id, String name, String course, int marks) {

        String query = "UPDATE students SET name=?, course=?, marks=? WHERE id=?";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, name);
            ps.setString(2, course);
            ps.setInt(3, marks);
            ps.setInt(4, id);

            ps.executeUpdate();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStudent(int id) {

        String query = "DELETE FROM students WHERE id=?";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, id);
            ps.executeUpdate();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void viewStudents() {

    String query = "SELECT * FROM students";

    try {
        Connection conn = DriverManager.getConnection(url, user, password);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        System.out.println("-------------------------------------------------");
        System.out.printf("%-10s %-15s %-15s %-10s\n", "ID", "NAME", "COURSE", "MARKS");
        System.out.println("-------------------------------------------------");

        while (rs.next()) {
            System.out.printf("%-10d %-15s %-15s %-10d\n",
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("course"),
                    rs.getInt("marks"));
        }

        System.out.println("-------------------------------------------------");

        conn.close();

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}