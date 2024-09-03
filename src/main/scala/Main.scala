import org.apache.spark.sql.SparkSession

object Main {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Scala-with-Spark")
      .master("local[*]") // instantiate a local instance with the number of executors available on the machine
      //.config("spark.driver.bindAddress", "127.0.0.1") // if Spark cannot resolve the local host
      .getOrCreate()

    val df = spark.read // DataFrame reader
      // csv options https://spark.apache.org/docs/latest/sql-data-sources-csv.html#data-source-option
      .option("header", value = true)
      .csv("./src/main/resources/AAPL.csv") // returns a DataFrame

      df.show() // by default prints the first 20 lines

  }
}
