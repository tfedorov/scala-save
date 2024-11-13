
//Task 1 Finds employees who earn more than their direct manager.
// Task 2. Calculates the total salary for employes subordinates, (without their own salary)
// Bonus 1, evaluate in term of Big O, how You can improve
// Bonus 2, Do task 1 with spark
object HelloWorld {

  // Employee case class representing each employee with a salary and manager
  case class Employee(
                       id: Int,
                       name: String,
                       salary: Long,
                       managerId: Option[Int]
                     )

  def findEmployeesWithHigherSalaryThanManager(
                                                employees: List[Employee]
                                              ): List[Employee] = ???

  def calcSubordinatesSalaries(employees: List[Employee]): Map[Employee, Long] = ???

  def main(args: Array[String]) = {
    val employees = List(
      Employee(1, "Alice", 3000, None), // CEO, no manager
      Employee(2, "Bob", 2000, Some(1)), // Bob reports to Alice
      Employee(3, "Carol", 800, Some(1)), // Carol reports to Alice
      Employee(4, "David", 2500, Some(2)), // David reports to Bob
      Employee(5, "Eve", 900, Some(2)), // Eve reports to Bob
      Employee(6, "Frank", 950, Some(3)) // Frank reports to Carol
    )

    val higherSalaryThanManager = findEmployeesWithHigherSalaryThanManager(
      employees
    )

    assert(
      higherSalaryThanManager == Employee(6, "Frank", 950, Some(3)),
      "Frank earn more than Carol"
    )

    val subordinatesSalaries = calcSubordinatesSalaries(employees)
    val ceoSubordinateSalary = subordinatesSalaries.get(Employee(1, "Alice", 3000, None))
    assert(
      ceoSubordinateSalary == Some(5450),
      "Salary of all employs except of CEO"
    )

    /*
    val spark = SparkSession.builder()
      .appName("EmployeeSalaryAnalysis")
      .master("local[*]") // Use all available cores
      .getOrCreate()

    import spark.implicits._
    
    // Convert the employee list to a DataFrame
    val employeeDF = employees.toDF()

    // Register DataFrame as a temporary table
    employeeDF.createOrReplaceTempView("employees")
    */


  }


  /*
  import java.util._
import java.util.stream.Collectors


public class HelloWorld {


    // Employee class representing each employee with a salary and manager
    static class Employee {
        int id;
        String name;
        long salary;
        Integer managerId; // Using Integer instead of Optional for simplicity in Java

        Employee(int id, String name, long salary, Integer managerId) {
            this.id = id;
            this.name = name;
            this.salary = salary;
            this.managerId = managerId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Employee employee = (Employee) o;
            return id == employee.id && name.equals(employee.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }

        @Override
        public String toString() {
            return "Employee{" + "id=" + id + ", name='" + name + '\'' + ", salary=" + salary + ", managerId=" + managerId + '}';
        }
    }

    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee(1, "Alice", 3000, null),       // CEO, no manager
                new Employee(2, "Bob", 2000, 1),            // Bob reports to Alice
                new Employee(3, "Carol", 800, 1),           // Carol reports to Alice
                new Employee(4, "David", 2500, 2),          // David reports to Bob
                new Employee(5, "Eve", 900, 2),             // Eve reports to Bob
                new Employee(6, "Frank", 950, 3)            // Frank reports to Carol
        );

        List<Employee> higherSalaryThanManager = findEmployeesWithHigherSalaryThanManager(employees);
        System.out.println(higherSalaryThanManager);

        // Check for employees who earn more than their manager
        assert higherSalaryThanManager.contains(new Employee(6, "Frank", 950, 3)) : "Frank earns more than Carol";

        Map<Employee, Long> subordinateSalaries = calcSubordinateSalaries(employees);
        Long ceoSubordinateSalary = subordinateSalaries.get(new Employee(1, "Alice", 3000, null));

        // Assert the CEO's subordinate salary matches expectation
        assert ceoSubordinateSalary != null && ceoSubordinateSalary == 5450 : "Total salary of all employees under the CEO";
    }

    // Task 1: Find employees with a higher salary than their direct manager
    public static List<Employee> findEmployeesWithHigherSalaryThanManager(List<Employee> employees) {
        // Placeholder: Add logic to find employees with higher salary than their manager
        return new ArrayList<>(); // Replace with actual result
    }

    // Task 2: Calculate the total salary of subordinates (excluding the employee's own salary)
    public static Map<Employee, Long> calcSubordinateSalaries(List<Employee> employees) {
        // Placeholder: Add logic to calculate total subordinate salaries
        return new HashMap<>(); // Replace with actual result
    }
}
   */
}
