object HelloWorld {

  case class Employee(val id: Int,
                      val name: String,
                      val salary: Long,
                      val managerId: Option[Int])

  // Method to modify
  def findLuckyEmployee(employees: List[Employee]): List[Employee] = ???

  def main(args: Array[String]): Unit = {
    val employees = List(
      Employee(id = 1, name = "Alice", salary = 3000, managerId = None), // CEO, no manager
      Employee(id = 2, name = "Bob", salary = 2000, managerId = Some(1)), // Bob reports to Alice
      Employee(id = 4, name = "David", salary = 2500, managerId = Some(2)), // David reports to Bob
      Employee(id = 5, name = "Eve", salary = 900, managerId = Some(2)), // Eve reports to Bob
      Employee(id = 3, name = "Carol", salary = 800, managerId = Some(1)), // Carol reports to Alice
      Employee(id = 6, name = "Frank", salary = 950, managerId = Some(3)) // Frank reports to Carol
    )

    val luckyEmployee = findLuckyEmployee(employees)

    luckyEmployee.foreach(println)
    /*
        //Bonus 2, Do task 1 with spark - line :36
        val spark = SparkSession.builder()
          .appName("EmployeeSalaryAnalysis")
          .master("local[*]") // Use all available cores
          .getOrCreate()

        import spark.implicits._
         //Register DataFrame as a temporary table
        employeeDF.createOrReplaceTempView("employees")
        spark.sql("")
*/
    

    findEmployeesWithHigherSalaryThanManager(employees)
  }
}
