// package whatever; // don't place package name!
// Installed Libraries: JSON-Simple, JUNit 4, Apache Commons Lang3

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Description of application:
//  - Main method on the line :20
//  - Employee case class on the line :12
//  - All employees are in line :21
// Task 1: Finds employees who earn more than their direct manager.
// Replace method findEmployeesWithHigherSalaryThanManager line :18
//  - Bonus task 1: Evaluate task in terms of Big O. How Do You can to improve?
//  - Bonus task 2: Do task 1 with spark - line :36
class Employee {
    int id;
    String name;
    long salary;
    Optional<Integer> managerId;

    public Employee(int id, String name, long salary, Optional<Integer> managerId) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.managerId = managerId;
    }
}

class MyCode {

    public static List<Employee> findEmployeesWithHigherSalaryThanManager(List<Employee> employees) {
        List<Employee> result = new ArrayList<>();
        return result;
    }

    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Alice", 3000, Optional.empty())); // CEO, no manager
        employees.add(new Employee(2, "Bob", 2000, Optional.of(1))); // Bob reports to Alice
        employees.add(new Employee(4, "David", 2500, Optional.of(2))); // David reports to Bob
        employees.add(new Employee(3, "Carol", 800, Optional.of(1))); // Carol reports to Alice
        employees.add(new Employee(5, "Eve", 900, Optional.of(2))); // Eve reports to Bob
        employees.add(new Employee(6, "Frank", 950, Optional.of(3))); // Frank reports to Carol

        List<Employee> higherSalaryThanManager = findEmployeesWithHigherSalaryThanManager(employees);
        System.out.println(higherSalaryThanManager);
    }
}