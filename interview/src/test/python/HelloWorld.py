"""
Environment:
    Python 3.11.9, pip 24.0
    PySpark 3.5.1 (Scala 2.12.18, OpenJDK 17.0.10)

To install packages, use `pip install <package_name>` in the shell.
To save dependencies for future use, run `pip freeze > requirements.txt`

Modify the `Run` command by changing `execute.Run` in `ci-config.json`.
Additional commands can be added in `ci-config.json`.

"""

from pyspark.sql import SparkSession
from dataclasses import dataclass
from typing import List, Optional

@dataclass
class Employee:
    id: int
    name: str
    salary: int
    managerId: Optional[int]


def main():
  spark = SparkSession.builder \
    .appName("CodeInterview.io") \
    .getOrCreate()

  employees = [
    Employee(id=1, name="Alice", salary=3000, managerId=None),
    Employee(id=2, name="Bob", salary=2000, managerId=1),
    Employee(id=4, name="David", salary=2500, managerId=2),
    Employee(id=5, name="Eve", salary=900, managerId=2),
    Employee(id=3, name="Carol", salary=800, managerId=1),
    Employee(id=6, name="Frank", salary=950, managerId=3),
    Employee(id=7, name="John", salary=950, managerId=2),
  ]

  employees_df = spark.createDataFrame(employees)
  employees_df.createOrReplaceTempView("employees")

  spark.sql("???")
 """
+------------+-------------+------+
|manager_name|employee_name|salary|
+------------+-------------+------+
|       Carol|        Frank|   800|
|         Bob|        David|  2000|
+------------+-------------+------+

+-----+---------------------+
| name|count_of_subordinates|
+-----+---------------------+
|Carol|                    1|
|  Bob|                    3|
|Alice|                    2|
+-----+---------------------+

+------------+-------------+------+--------------------------+
|manager_name|employee_name|salary|rank_of_salary_for_manager|
+------------+-------------+------+--------------------------+
|       Alice|        Carol|   800|                         1|
|       Alice|          Bob|  2000|                         2|

|         Bob|          Eve|   900|                         1|
|         Bob|         John|   950|                         2|
|         Bob|        David|  2500|                         3|

|       Carol|        Frank|   950|                         1|
+------------+-------------+------+--------------------------+
  """



  spark.stop()

if __name__ == '__main__':
  main()

  """
  spark.sql("SELECT m.name as manager_name,e.name as employee_name, m.salary FROM employees e JOIN employees m ON e.managerId = m.id WHERE e.salary > m.salary ORDER BY e.salary - m.salary").show()
  spark.sql("SELECT m.name,Count(*) as count_of_subordinates FROM employees e JOIN employees m ON e.managerId = m.id GROUP BY m.name ").show()
  spark.sql("SELECT m.name as manager_name, e.name as employee_name, e.salary, rank(e.salary) OVER (PARTITION BY e.managerId ORDER BY e.salary) AS rank_of_salary_for_manager  FROM employees e JOIN employees m ON e.managerId = m.id ").show()
 """
