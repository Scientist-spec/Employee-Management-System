import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class EmployeeManagementSystem {
    private EmployeeDAO employeeDAO;
    private Scanner scanner;

    public EmployeeManagementSystem() {
        this.employeeDAO = new EmployeeDAO();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("=== EMPLOYEE MANAGEMENT SYSTEM ===");
        
        while (true) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1 -> addEmployee();
                case 2 -> viewAllEmployees();
                case 3 -> viewEmployee();
                case 4 -> updateEmployee();
                case 5 -> deleteEmployee();
                case 6 -> searchEmployees();
                case 7 -> generateReports();
                case 8 -> exportData();
                case 9 -> {
                    System.out.println("Saving data and exiting...");
                    employeeDAO.saveToFile();
                    System.out.println("Thank you for using Employee Management System!");
                    return;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
            
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }

    private void displayMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Add New Employee");
        System.out.println("2. View All Employees");
        System.out.println("3. View Employee Details");
        System.out.println("4. Update Employee");
        System.out.println("5. Delete Employee");
        System.out.println("6. Search Employees");
        System.out.println("7. Generate Reports");
        System.out.println("8. Export Data");
        System.out.println("9. Exit");
        System.out.println("=================");
    }

    private void addEmployee() {
        System.out.println("\n=== ADD NEW EMPLOYEE ===");
        
        String firstName = getStringInput("First Name: ");
        String lastName = getStringInput("Last Name: ");
        String email = getStringInput("Email: ");
        String department = getStringInput("Department: ");
        String position = getStringInput("Position: ");
        double salary = getDoubleInput("Salary: ");
        String phone = getStringInput("Phone Number: ");
        String address = getStringInput("Address: ");
        
        boolean success = employeeDAO.addEmployee(firstName, lastName, email, department,
                position, salary, LocalDate.now(), phone, address);
        
        if (success) {
            System.out.println("Employee added successfully!");
        } else {
            System.out.println("Failed to add employee. Employee ID might already exist.");
        }
    }

    private void viewAllEmployees() {
        System.out.println("\n=== ALL EMPLOYEES ===");
        List<Employee> employees = employeeDAO.getAllEmployees();
        
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }
        
        System.out.printf("%-5s %-20s %-15s %-15s %-10s %-12s%n", 
                         "ID", "Name", "Department", "Position", "Salary", "Status");
        System.out.println("-".repeat(82));
        
        for (Employee emp : employees) {
            System.out.printf("%-5d %-20s %-15s %-15s $%-9.2f %-12s%n",
                            emp.getId(), 
                            emp.getFullName(), 
                            emp.getDepartment(),
                            emp.getPosition(), 
                            emp.getSalary(), 
                            emp.isActive() ? "Active" : "Inactive");
        }
        
        System.out.println("\nTotal employees: " + employees.size());
    }

    private void viewEmployee() {
        int id = getIntInput("Enter Employee ID: ");
        Employee employee = employeeDAO.getEmployee(id);
        
        if (employee != null) {
            System.out.println("\n=== EMPLOYEE DETAILS ===");
            System.out.println("ID: " + employee.getId());
            System.out.println("Name: " + employee.getFullName());
            System.out.println("Email: " + employee.getEmail());
            System.out.println("Department: " + employee.getDepartment());
            System.out.println("Position: " + employee.getPosition());
            System.out.println("Salary: $" + employee.getSalary());
            System.out.println("Hire Date: " + employee.getHireDate());
            System.out.println("Years of Service: " + employee.getYearsOfService());
            System.out.println("Phone: " + employee.getPhoneNumber());
            System.out.println("Address: " + employee.getAddress());
            System.out.println("Status: " + (employee.isActive() ? "Active" : "Inactive"));
        } else {
            System.out.println("Employee not found with ID: " + id);
        }
    }

    private void updateEmployee() {
        int id = getIntInput("Enter Employee ID to update: ");
        Employee employee = employeeDAO.getEmployee(id);
        
        if (employee == null) {
            System.out.println("Employee not found with ID: " + id);
            return;
        }
        
        System.out.println("\nCurrent details:");
        System.out.println(employee);
        
        System.out.println("\nEnter new details (press Enter to keep current value):");
        
        String firstName = getStringInput("First Name [" + employee.getFirstName() + "]: ");
        if (!firstName.isEmpty()) employee.setFirstName(firstName);
        
        String lastName = getStringInput("Last Name [" + employee.getLastName() + "]: ");
        if (!lastName.isEmpty()) employee.setLastName(lastName);
        
        String email = getStringInput("Email [" + employee.getEmail() + "]: ");
        if (!email.isEmpty()) employee.setEmail(email);
        
        String department = getStringInput("Department [" + employee.getDepartment() + "]: ");
        if (!department.isEmpty()) employee.setDepartment(department);
        
        String position = getStringInput("Position [" + employee.getPosition() + "]: ");
        if (!position.isEmpty()) employee.setPosition(position);
        
        String salaryInput = getStringInput("Salary [" + employee.getSalary() + "]: ");
        if (!salaryInput.isEmpty()) employee.setSalary(Double.parseDouble(salaryInput));
        
        String phone = getStringInput("Phone [" + employee.getPhoneNumber() + "]: ");
        if (!phone.isEmpty()) employee.setPhoneNumber(phone);
        
        String address = getStringInput("Address [" + employee.getAddress() + "]: ");
        if (!address.isEmpty()) employee.setAddress(address);
        
        if (employeeDAO.updateEmployee(employee)) {
            System.out.println("Employee updated successfully!");
        } else {
            System.out.println("Failed to update employee.");
        }
    }

    private void deleteEmployee() {
        System.out.println("\n=== DELETE EMPLOYEE ===");
        System.out.println("1. Deactivate Employee (Soft Delete)");
        System.out.println("2. Permanently Delete Employee");
        System.out.println("3. Activate Employee");
        
        int choice = getIntInput("Enter choice: ");
        int id = getIntInput("Enter Employee ID: ");
        
        boolean success = false;
        switch (choice) {
            case 1 -> success = employeeDAO.deactivateEmployee(id);
            case 2 -> success = employeeDAO.deleteEmployee(id);
            case 3 -> success = employeeDAO.activateEmployee(id);
            default -> {
                System.out.println("Invalid choice!");
                return;
            }
        }
        
        if (success) {
            System.out.println("Operation completed successfully!");
        } else {
            System.out.println("Operation failed. Employee might not exist.");
        }
    }

    private void searchEmployees() {
        System.out.println("\n=== SEARCH EMPLOYEES ===");
        System.out.println("1. Search by Name");
        System.out.println("2. Search by Department");
        System.out.println("3. Search by Salary Range");
        
        int choice = getIntInput("Enter choice: ");
        List<Employee> results = null;
        
        switch (choice) {
            case 1 -> {
                String name = getStringInput("Enter name to search: ");
                results = employeeDAO.searchEmployeesByName(name);
            }
            case 2 -> {
                String department = getStringInput("Enter department: ");
                results = employeeDAO.getEmployeesByDepartment(department);
            }
            case 3 -> {
                double minSalary = getDoubleInput("Enter minimum salary: ");
                double maxSalary = getDoubleInput("Enter maximum salary: ");
                results = employeeDAO.getEmployeesBySalaryRange(minSalary, maxSalary);
            }
            default -> {
                System.out.println("Invalid choice!");
                return;
            }
        }
        
        if (results == null || results.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }
        
        System.out.println("\nSearch Results:");
        System.out.printf("%-5s %-20s %-15s %-15s %-10s%n", 
                         "ID", "Name", "Department", "Position", "Salary");
        System.out.println("-".repeat(70));
        
        for (Employee emp : results) {
            System.out.printf("%-5d %-20s %-15s %-15s $%-9.2f%n",
                            emp.getId(), emp.getFullName(), emp.getDepartment(),
                            emp.getPosition(), emp.getSalary());
        }
        
        System.out.println("Found " + results.size() + " employee(s).");
    }

    private void generateReports() {
        System.out.println("\n=== REPORTS ===");
        System.out.println("1. Department Statistics");
        System.out.println("2. Salary Report");
        System.out.println("3. Employee Statistics");
        
        int choice = getIntInput("Enter choice: ");
        
        switch (choice) {
            case 1 -> {
                System.out.println("\n=== DEPARTMENT STATISTICS ===");
                var stats = employeeDAO.getDepartmentStatistics();
                stats.forEach((dept, count) -> 
                    System.out.printf("%-20s: %d employees (Avg Salary: $%.2f)%n", 
                                    dept, count, employeeDAO.getAverageSalaryByDepartment(dept)));
            }
            case 2 -> {
                System.out.println("\n=== SALARY REPORT ===");
                Employee highestPaid = employeeDAO.getHighestPaidEmployee();
                if (highestPaid != null) {
                    System.out.printf("Highest Paid: %s - $%.2f%n", 
                                    highestPaid.getFullName(), highestPaid.getSalary());
                }
                System.out.printf("Average Salary: $%.2f%n", employeeDAO.getAverageSalary());
            }
            case 3 -> {
                System.out.println("\n=== EMPLOYEE STATISTICS ===");
                System.out.println("Total Employees: " + employeeDAO.getTotalEmployees());
                System.out.println("Active Employees: " + employeeDAO.getActiveEmployeesCount());
                System.out.printf("Average Salary: $%.2f%n", employeeDAO.getAverageSalary());
                
                Employee longestServing = employeeDAO.getLongestServingEmployee();
                if (longestServing != null) {
                    System.out.printf("Longest Serving: %s - %d years%n", 
                                    longestServing.getFullName(), longestServing.getYearsOfService());
                }
            }
            default -> System.out.println("Invalid choice!");
        }
    }

    private void exportData() {
        String filename = getStringInput("Enter filename for export (e.g., employees.txt): ");
        employeeDAO.exportToTextFile(filename);
    }

    // Utility methods for input
    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public static void main(String[] args) {
        EmployeeManagementSystem system = new EmployeeManagementSystem();
        system.start();
    }
}
