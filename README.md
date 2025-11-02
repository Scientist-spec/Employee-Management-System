Employee Management System
A comprehensive Java-based Employee Management System that demonstrates object-oriented programming principles with file persistence, CRUD operations, and data encapsulation.

Table of Contents
Features

System Architecture

Installation

Usage

Class Documentation

CRUD Operations

File Persistence

Examples

Validation Rules

Features
👥 Employee Management
Complete CRUD Operations: Create, Read, Update, Delete employees

Auto-increment Employee IDs: Automatic unique ID generation

Comprehensive Employee Data:

Personal information (name, email, phone, address)

Employment details (department, position, salary)

Hire date and years of service calculation

Active/inactive status management

🔍 Advanced Search & Filtering
Search employees by name

Filter by department

Search by salary range

Active employee filtering

Department-based employee lists

📊 Reporting & Analytics
Department statistics and headcount

Salary analysis and averages

Employee tenure reporting

Highest paid employee identification

Export to text file functionality

💾 Data Persistence
Automatic save/load using Java Serialization

File-based data storage (employees.dat)

Export capabilities to readable text format

Data integrity between sessions

✅ Data Validation
Input validation for all fields

Email format validation

Salary range validation

Duplicate email prevention

Phone number format checking

System Architecture
The system follows a layered architecture with clear separation of concerns:

text
EmployeeManagementSystem (Presentation Layer)
         ↓
    EmployeeDAO (Data Access Layer)
         ↓
   File Storage (Persistence Layer)
Class Relationships:
Employee: Entity class representing employee data

EmployeeDAO: Data Access Object handling all CRUD operations

EmployeeManagementSystem: Main application with user interface

SampleDataGenerator: Utility class for generating test data

Installation
Prerequisites
Java JDK 8 or higher

Any Java IDE (Eclipse, IntelliJ, VS Code) or command line

Steps
Download the source files:

Employee.java

EmployeeDAO.java

EmployeeManagementSystem.java

SampleDataGenerator.java (optional)

Compile all Java files:

bash
javac *.java
Run the application:

bash
java EmployeeManagementSystem
Usage
Starting the System
bash
java EmployeeManagementSystem
Main Menu Options
Add New Employee - Register new employees with auto-generated ID

View All Employees - Display all employees in formatted table

View Employee Details - Show detailed information for specific employee

Update Employee - Modify existing employee records

Delete Employee - Deactivate or permanently remove employees

Search Employees - Advanced search and filtering

Generate Reports - Analytical reports and statistics

Export Data - Export employee data to text file

Exit - Save data and exit system

Class Documentation
Employee Class
Purpose: Represents an employee entity with all relevant attributes

Key Attributes:

id: Unique employee identifier (auto-generated)

firstName, lastName: Employee name

email: Contact email with validation

department, position: Job details

salary: Compensation with validation

hireDate: Employment start date

phoneNumber, address: Contact information

active: Employment status

Key Methods:

getYearsOfService(): Calculates tenure

getFullName(): Returns formatted name

applyRaise(percentage): Applies salary increase

Validation in all setter methods

EmployeeDAO Class
Purpose: Data Access Object handling all persistence and business logic

Key Features:

CRUD Operations: Complete Create, Read, Update, Delete functionality

File Persistence: Automatic save/load from employees.dat

Search & Filter: Multiple search criteria

Statistics: Analytical methods for reporting

Data Validation: Input validation and duplicate checking

Key Methods:

addEmployee(): Multiple overloaded versions

getEmployeesByDepartment(), searchEmployeesByName()

updateEmployeeSalary(), applyRaiseToEmployee()

getDepartmentStatistics(), getAverageSalary()

saveToFile(), loadFromFile(), exportToTextFile()

EmployeeManagementSystem Class
Purpose: Main application class with user interface

Key Features:

Menu-driven console interface

Input validation and error handling

User-friendly prompts and messages

Integration with EmployeeDAO

CRUD Operations
Create
java
// Add employee with individual parameters
employeeDAO.addEmployee("John", "Doe", "john@company.com", 
                       "Engineering", "Developer", 75000, 
                       LocalDate.now(), "555-1234", "123 Main St");

// Add employee with object
Employee emp = new Employee(0, "Jane", "Smith", "jane@company.com", 
                           "Marketing", "Manager", 65000, 
                           LocalDate.now(), "555-5678", "456 Oak St");
employeeDAO.addEmployee(emp);
Read
java
// Get single employee
Employee emp = employeeDAO.getEmployee(101);

// Get all employees
List<Employee> allEmployees = employeeDAO.getAllEmployees();

// Search and filter
List<Employee> results = employeeDAO.searchEmployeesByName("John");
List<Employee> deptEmployees = employeeDAO.getEmployeesByDepartment("Engineering");
Update
java
// Update entire employee
employeeDAO.updateEmployee(updatedEmployee);

// Update specific fields
employeeDAO.updateEmployeeSalary(101, 80000);
employeeDAO.updateEmployeeDepartment(101, "IT");
employeeDAO.applyRaiseToEmployee(101, 10.0); // 10% raise
Delete
java
// Soft delete (deactivate)
employeeDAO.deactivateEmployee(101);

// Hard delete (permanent)
employeeDAO.deleteEmployee(101);

// Reactivate employee
employeeDAO.activateEmployee(101);
File Persistence
Data Storage
Binary Format: employees.dat - Java serialized objects

Export Format: Text files with formatted reports

Automatic Save: Data persisted after every modification

Automatic Load: Data loaded at application startup

Backup and Export
java
// Export to text file
employeeDAO.exportToTextFile("employee_report.txt");

// Manual save
employeeDAO.saveToFile();
Examples
Complete Workflow
java
// Initialize system
EmployeeManagementSystem system = new EmployeeManagementSystem();

// The system automatically loads existing data
// User interacts through menu system
// Data automatically saved on changes
Sample Data Generation
java
// Generate test data (optional)
SampleDataGenerator.generateSampleData(employeeDAO);
Custom Implementation
java
// Create your own implementation
EmployeeDAO dao = new EmployeeDAO();

// Add employees
dao.addEmployee("Alice", "Johnson", "alice@company.com", 
               "HR", "Specialist", 60000, 
               LocalDate.now(), "555-1111", "789 Pine St");

// Generate reports
Map<String, Long> deptStats = dao.getDepartmentStatistics();
double avgSalary = dao.getAverageSalary();

// Export data
dao.exportToTextFile("my_employee_report.txt");
Validation Rules
Employee Data Validation
Names: Non-empty strings

Email: Valid format with '@' symbol, unique in system

Salary: Non-negative values

Phone: Valid format (various formats accepted)

Department/Position: Non-empty strings

Address: Non-empty string

Business Rules
Employee IDs are auto-generated and unique

Email addresses must be unique across all employees

Salary cannot be negative

Employees can be deactivated instead of deleted

Years of service calculated from hire date

Error Handling
Comprehensive input validation

User-friendly error messages

Graceful handling of file I/O errors

Duplicate prevention mechanisms

File Structure
text
employee-management-system/
├── Employee.java                 # Employee entity class
├── EmployeeDAO.java              # Data access and business logic
├── EmployeeManagementSystem.java # Main application class
├── SampleDataGenerator.java      # Test data generator (optional)
└── employees.dat                 # Data file (auto-created)
Extending the System
Potential Enhancements
Database integration (MySQL, PostgreSQL)

Web-based user interface

REST API endpoints

Advanced reporting with charts

Email notifications

Role-based access control

Attendance tracking

Payroll integration

Adding New Features
Extend Employee class with new attributes

Add corresponding methods in EmployeeDAO

Update EmployeeManagementSystem with new menu options

Maintain data validation and persistence

Support
For issues or questions:

Check that all files are in the same directory

Ensure Java version compatibility

Verify file permissions for data storage

Check console for specific error messages

License
This project is open source and available under the MIT License.
