// Description of application:
//  - Main method on the line :20
//  - Employee case class on the line :12
//  - All employees are in line :21
// Task 1: Finds employees who earn more than their direct manager.
// Replace method findEmployeesWithHigherSalaryThanManager line :18
//  - Bonus task 1: Evaluate task in terms of Big O. How Do You can to improve?
//  - Bonus task 2: Do task 1 with spark - line :36
object HelloWorld {

  // Employee case class representing each employee with a salary and manager
  case class Employee(val id: Int,
                      val name: String,
                      val salary: Long,
                      val managerId: Option[Int])

  // Method to modify
  def findEmployeesWithHigherSalaryThanManager(employees: List[Employee]): List[Employee] = {
    employees.filter(emp => emp.salary > employees.filter(man => man.id == emp.managerId.getOrElse(0)).headOption.map(_.salary).getOrElse(0L))
  }


  def main(args: Array[String]): Unit = {
    val employees = List(
      Employee(id = 1, name = "Alice", salary = 3000, managerId = None), // CEO, no manager
      Employee(id = 2, name = "Bob", salary = 2000, managerId = Some(1)), // Bob reports to Alice
      Employee(id = 4, name = "David", salary = 2500, managerId = Some(2)), // David reports to Bob
      Employee(id = 5, name = "Eve", salary = 900, managerId = Some(2)), // Eve reports to Bob
      Employee(id = 3, name = "Carol", salary = 800, managerId = Some(1)), // Carol reports to Alice
      Employee(id = 6, name = "Frank", salary = 950, managerId = Some(3)) // Frank reports to Carol
    )

    val higherSalaryThanManager = findEmployeesWithHigherSalaryThanManager(employees)

    higherSalaryThanManager.foreach(println)
    /*
        //Bonus 2, Do task 1 with spark - line :36
        val spark = SparkSession.builder()
          .appName("EmployeeSalaryAnalysis")
          .master("local[*]") // Use all available cores
          .getOrCreate()

        import spark.implicits._
         //Register DataFrame as a temporary table
        employeeDF.createOrReplaceTempView("employees")
        spark.sql(" select * from (select name, max('salary') as max_salary from employees e group by managerId) as A  where e.max_salary > A.salary")

    */
  }
}
