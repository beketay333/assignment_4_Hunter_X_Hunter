import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainApp {
    public static Connection con() {
        String url = "jdbc:mysql://localhost:3306/comp";
        String username = "root";
        String pass = "";

        try {
            Connection con = DriverManager.getConnection(url, username, pass);
            if (con == null) {
                System.out.println("There is NO connection");
            }
            return con;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void addEmp(Employees emp) throws SQLException {
        Connection conn = con();
        if (conn != null) {
            String sql = "INSERT INTO employees (fname, lname, address, salary, empid) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, emp.getFname());
            statement.setString(2, emp.getLname());
            statement.setString(3, emp.getAddress());
            statement.setInt(4, emp.getSalary());
            statement.setInt(5, emp.getId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("Employee " + emp.getName() + " with id: " + emp.getId() + " inserted!");
            }
        }
    }

    public static void selectEmp() throws SQLException {
        Connection conn = con();
        if (conn != null) {
            String sql = "SELECT * FROM employees";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No Employees in db!");
            } else {
                while (resultSet.next()) {
                    int empid = resultSet.getInt("empid");
                    String empname = resultSet.getString("fname") + " " + resultSet.getString("lname");
                    int salary = resultSet.getInt("salary");
                    System.out.println("ID:" + empid + " - " + empname + "; Salary: " + salary);
                }
            }
        }
    }

    public static Employees selectEmpById(int empid) throws SQLException {
        Connection conn = con();
        if (conn != null) {
            Employees empl;
            String sql = "SELECT * FROM employees WHERE empid=?";
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setInt(1, empid);
            ResultSet resultSet = pStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Database is empty");
                return null;
            } else {
                while (resultSet.next()) {
                    int e_id = resultSet.getInt("empid");
                    String e_first_name = resultSet.getString("fname");
                    String e_last_name = resultSet.getString("lname");
                    String e_address = resultSet.getString("address");
                    int e_salary = resultSet.getInt("salary");
                    empl = new Employees(e_id, e_first_name, e_last_name, e_salary, e_address);
                    return empl;
                }
            }
        } else {
            return null;
        }
        return null;
    }

    public static void createProject(Project proj) throws SQLException {
        Connection conn = con();
        if (conn != null) {
            String sql = "INSERT INTO projects (projID, projName, endTime, total) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, proj.getProjID());
            statement.setString(2, proj.getProjName());
            statement.setInt(3, proj.getEndtime());
            statement.setInt(4, proj.getTotalPrice());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println(proj.getProjName() + " added!");

                for (int i = 0; i < proj.getEmployees().size(); i++) {
                    String sql_inner = "INSERT INTO project_employees (proj_id, empid) VALUES (?, ?)";
                    PreparedStatement statement_inner = conn.prepareStatement(sql_inner);
                    statement_inner.setInt(1, proj.getProjID());
                    statement_inner.setInt(2, proj.getEmployees().get(i).getId());

                    int rows_inner = statement_inner.executeUpdate();

                    if (rows_inner > 0) {
                        System.out.println(proj.getEmployees().get(i).getName() + " added");
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("1: Add Employee\n2: Make Project\n3 - Close");

            int choice = scan.nextInt();

            if (choice == 1) {
                System.out.println("Employees:");
                selectEmp();

                System.out.println();

                System.out.println("Input Employee ID:");
                int id = scan.nextInt();
                System.out.println("Input Employee's First Name:");
                String fname = scan.next();
                System.out.println("Input Employee's Last Name:");
                String lname = scan.next();
                System.out.println("Input Employee's Address:");
                String address = scan.next();
                System.out.println("Input Employee's Salary:");
                int salary = scan.nextInt();
                Employees employee = new Employees(id, fname, lname, salary, address);
                addEmp(employee);
            } else if (choice == 2) {
                System.out.println("Input ID of project:");
                int pid = scan.nextInt();
                System.out.println("Input project name:");
                String pname = scan.next();
                int eid = 0;

                List<Employees> employees = new ArrayList<>();
                System.out.println("Select employees by ID then Input '-9' to end:");
                selectEmp();
                while (true) {
                    eid = scan.nextInt();
                    if (eid == -9) {
                        System.out.println("End of selections");
                        break;
                    } else {
                        employees.add(selectEmpById(eid));
                    }
                }
                System.out.println("Input endtime");
                int endtime = scan.nextInt();
                Project project = new Project(pid, pname, employees, endtime);
                createProject(project);
            } else if (choice == 3) {
                break;
            }
        }

        scan.close();
    }
}