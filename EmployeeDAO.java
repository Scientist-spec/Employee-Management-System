import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class EmployeeDAO {
    private static final String DATA_FILE = "employees.dat";
    private Map<Integer, Employee> employees;
    private int nextId;

    public EmployeeDAO() {
        employees = new HashMap<>();
        nextId = 1;
        loadFromFile();
    }

    // CREATE operations
    public boolean addEmployee(Employee employee) {
        if (employee == null || employees.containsKey(employee.getId())) {
            return false;
        }
        
        // Ensure unique ID
        if (employee.getId() <= 0) {
            employee = new Employee(nextId++, employee.getFirstName(), employee.getLastName(),
                    employee.getEmail(), employee.getDepartment(), employee.getPosition(),
                    employee.getSalary(), employee.getHireDate(), employee.getPhoneNumber(),
                    employee.getAddress());
        }
        
        employees.put(employee.getId(), employee);
        saveToFile();
        return true;
    }

    public boolean addEmployee(String firstName, String lastName, String email, 
                          String department, String position, double salary,
                          LocalDate hireDate, String phoneNumber, String address) {
    
    // Create the employee object
    Employee employee = new Employee(nextId++, firstName, lastName, email, department,
            position, salary, hireDate, phoneNumber, address);
    
    // Add to the collection directly, not recursively
    if (employees.containsKey(employee.getId())) {
        return false; // ID already exists (shouldn't happen with auto-increment)
    }
    
    employees.put(employee.getId(), employee);
    saveToFile();
    return true;
}

    // READ operations
    public Employee getEmployee(int id) {
        return employees.get(id);
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }

    public List<Employee> getActiveEmployees() {
        return employees.values().stream()
                .filter(Employee::isActive)
                .collect(Collectors.toList());
    }

    public List<Employee> getEmployeesByDepartment(String department) {
        return employees.values().stream()
                .filter(emp -> emp.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }

    public List<Employee> searchEmployeesByName(String name) {
        String searchTerm = name.toLowerCase();
        return employees.values().stream()
                .filter(emp -> emp.getFirstName().toLowerCase().contains(searchTerm) ||
                              emp.getLastName().toLowerCase().contains(searchTerm) ||
                              emp.getFullName().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }

    public List<Employee> getEmployeesBySalaryRange(double minSalary, double maxSalary) {
        return employees.values().stream()
                .filter(emp -> emp.getSalary() >= minSalary && emp.getSalary() <= maxSalary)
                .collect(Collectors.toList());
    }

    // UPDATE operations
    public boolean updateEmployee(Employee employee) {
        if (employee == null || !employees.containsKey(employee.getId())) {
            return false;
        }
        employees.put(employee.getId(), employee);
        saveToFile();
        return true;
    }

    public boolean updateEmployeeSalary(int id, double newSalary) {
        Employee employee = getEmployee(id);
        if (employee != null) {
            employee.setSalary(newSalary);
            return updateEmployee(employee);
        }
        return false;
    }

    public boolean updateEmployeeDepartment(int id, String newDepartment) {
        Employee employee = getEmployee(id);
        if (employee != null) {
            employee.setDepartment(newDepartment);
            return updateEmployee(employee);
        }
        return false;
    }

    public boolean updateEmployeePosition(int id, String newPosition) {
        Employee employee = getEmployee(id);
        if (employee != null) {
            employee.setPosition(newPosition);
            return updateEmployee(employee);
        }
        return false;
    }

    public boolean applyRaiseToEmployee(int id, double percentage) {
        Employee employee = getEmployee(id);
        if (employee != null) {
            employee.applyRaise(percentage);
            return updateEmployee(employee);
        }
        return false;
    }

    // DELETE operations
    public boolean deleteEmployee(int id) {
        if (employees.containsKey(id)) {
            employees.remove(id);
            saveToFile();
            return true;
        }
        return false;
    }

    public boolean deactivateEmployee(int id) {
        Employee employee = getEmployee(id);
        if (employee != null) {
            employee.setActive(false);
            return updateEmployee(employee);
        }
        return false;
    }

    public boolean activateEmployee(int id) {
        Employee employee = getEmployee(id);
        if (employee != null) {
            employee.setActive(true);
            return updateEmployee(employee);
        }
        return false;
    }

    // Statistics and Reports
    public int getTotalEmployees() {
        return employees.size();
    }

    public int getActiveEmployeesCount() {
        return (int) employees.values().stream().filter(Employee::isActive).count();
    }

    public Map<String, Long> getDepartmentStatistics() {
        return employees.values().stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
    }

    public double getAverageSalary() {
        return employees.values().stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0.0);
    }

    public double getAverageSalaryByDepartment(String department) {
        return employees.values().stream()
                .filter(emp -> emp.getDepartment().equalsIgnoreCase(department))
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0.0);
    }

    public Employee getHighestPaidEmployee() {
        return employees.values().stream()
                .max(Comparator.comparingDouble(Employee::getSalary))
                .orElse(null);
    }

    public Employee getLongestServingEmployee() {
        return employees.values().stream()
                .max(Comparator.comparingInt(Employee::getYearsOfService))
                .orElse(null);
    }

    // File operations
    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            employees = (Map<Integer, Employee>) ois.readObject();
            // Find the next available ID
            nextId = employees.keySet().stream().max(Integer::compareTo).orElse(0) + 1;
            System.out.println("Employee data loaded successfully. Total employees: " + employees.size());
        } catch (FileNotFoundException e) {
            System.out.println("No existing data file found. Starting with empty database.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading employee data: " + e.getMessage());
            employees = new HashMap<>();
        }
    }

    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(employees);
            System.out.println("Employee data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving employee data: " + e.getMessage());
        }
    }

    public void exportToTextFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Employee Report - Generated on: " + java.time.LocalDateTime.now());
            writer.println("=" .repeat(80));
            writer.printf("%-5s %-20s %-15s %-15s %-10s %-12s%n", 
                         "ID", "Name", "Department", "Position", "Salary", "Status");
            writer.println("-".repeat(80));
            
            for (Employee emp : getAllEmployees()) {
                writer.printf("%-5d %-20s %-15s %-15s $%-9.2f %-12s%n",
                            emp.getId(), 
                            emp.getFullName(), 
                            emp.getDepartment(),
                            emp.getPosition(), 
                            emp.getSalary(), 
                            emp.isActive() ? "Active" : "Inactive");
            }
            
            writer.println("=" .repeat(80));
            writer.printf("Total Employees: %d | Active: %d | Average Salary: $%.2f%n",
                         getTotalEmployees(), getActiveEmployeesCount(), getAverageSalary());
            
            System.out.println("Data exported to " + filename + " successfully.");
        } catch (IOException e) {
            System.out.println("Error exporting data: " + e.getMessage());
        }
    }
}
