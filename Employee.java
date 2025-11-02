import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String department;
    private String position;
    private double salary;
    private LocalDate hireDate;
    private String phoneNumber;
    private String address;
    private boolean active;

    public Employee(int id, String firstName, String lastName, String email, 
                   String department, String position, double salary, 
                   LocalDate hireDate, String phoneNumber, String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.department = department;
        this.position = position;
        this.salary = salary;
        this.hireDate = hireDate;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.active = true;
    }

    // Getters
    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getDepartment() { return department; }
    public String getPosition() { return position; }
    public double getSalary() { return salary; }
    public LocalDate getHireDate() { return hireDate; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAddress() { return address; }
    public boolean isActive() { return active; }

    // Setters with validation
    public void setFirstName(String firstName) {
        if (firstName != null && !firstName.trim().isEmpty()) {
            this.firstName = firstName;
        }
    }

    public void setLastName(String lastName) {
        if (lastName != null && !lastName.trim().isEmpty()) {
            this.lastName = lastName;
        }
    }

    public void setEmail(String email) {
        if (email != null && email.contains("@")) {
            this.email = email;
        }
    }

    public void setDepartment(String department) {
        if (department != null && !department.trim().isEmpty()) {
            this.department = department;
        }
    }

    public void setPosition(String position) {
        if (position != null && !position.trim().isEmpty()) {
            this.position = position;
        }
    }

    public void setSalary(double salary) {
        if (salary >= 0) {
            this.salary = salary;
        }
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
            this.phoneNumber = phoneNumber;
        }
    }

    public void setAddress(String address) {
        if (address != null && !address.trim().isEmpty()) {
            this.address = address;
        }
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    // Business methods
    public int getYearsOfService() {
        return Period.between(hireDate, LocalDate.now()).getYears();
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public void applyRaise(double percentage) {
        if (percentage > 0) {
            this.salary += this.salary * (percentage / 100);
        }
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s %s | Dept: %-15s | Position: %-15s | Salary: $%,.2f | Status: %s",
                id, firstName, lastName, department, position, salary, active ? "Active" : "Inactive");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Employee employee = (Employee) obj;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
