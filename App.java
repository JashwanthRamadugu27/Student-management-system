import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        StudentDAO dao = new StudentDAO();

        while (true) {

            System.out.println("\n=================================================");
            System.out.println("        STUDENT MANAGEMENT SYSTEM");
            System.out.println("=================================================");
            System.out.println(" 1  ->  Add Student");
            System.out.println(" 2  ->  View All Students");
            System.out.println(" 3  ->  Delete Student");
            System.out.println(" 4  ->  Update Student");
            System.out.println(" 5  ->  Exit");
            System.out.println("-------------------------------------------------");
            System.out.print(" Enter your choice: ");

            int choice;

            try {
                choice = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter number only.");
                sc.nextLine();
                continue;
            }

            if (choice == 1) {

                int id;

                while (true) {
                    System.out.print("Enter ID: ");
                    id = sc.nextInt();
                    sc.nextLine();

                    if (dao.isIdExists(id)) {
                        System.out.println("ID already exists! Enter a different ID.\n");
                    } else {
                        break; // ID is valid
                    }
                }

                System.out.print("Enter Name: ");
                String name = sc.nextLine();

                System.out.print("Enter Course: ");
                String course = sc.nextLine();

                System.out.print("Enter Marks: ");
                int marks = sc.nextInt();

                Student student = new Student(id, name, course, marks);
                dao.addStudent(student);
}

            else if (choice == 2) {

                dao.viewStudents();

            }

            else if (choice == 3) {

                System.out.print("Enter ID to delete: ");
                int id = sc.nextInt();
                dao.deleteStudent(id);

            }

            else if (choice == 4) {

                System.out.print("Enter ID to update: ");
                int id = sc.nextInt();
                sc.nextLine();

                System.out.print("Enter New Name: ");
                String name = sc.nextLine();

                System.out.print("Enter New Course: ");
                String course = sc.nextLine();

                System.out.print("Enter New Marks: ");
                int marks = sc.nextInt();

                dao.updateStudent(id, name, course, marks);

            }

            else if (choice == 5) {

                System.out.println("Exiting... Thank you!");
                break;

            }

            else {

                System.out.println("Invalid Option! Please choose between 1-5.");

            }
        }

        sc.close();
    }
}