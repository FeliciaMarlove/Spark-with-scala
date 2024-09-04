import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.{col, row_number, year}
import org.apache.spark.sql.{DataFrame, SparkSession, functions}

// data sample for exercise https://www.kaggle.com/datasets/jacksoncrow/stock-market-dataset AAPL.csv

object Main {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("Scala-with-Spark")
      // instantiate a local instance with the number of executors available on the machine
      .master(
        "local[*]"
      )
      // if Spark cannot resolve the local host:
      // .config("spark.driver.bindAddress", "127.0.0.1")
      .getOrCreate()

    // type DataFrame = Dataset[Row]
    // "collection of generic rows"
    val df: DataFrame = spark.read // DataFrame reader
      // csv options https://spark.apache.org/docs/latest/sql-data-sources-csv.html#data-source-option
      .option("header", value = true)
      // tries to infer the schema (= guess the types instead of giving "string" type to all columns)
      // /!\ requires 1 extra pass over the data => compute cost!
      .option("inferSchema", value = true)
      // returns a DataFrame
      .csv("./src/main/resources/AAPL.csv")

    import spark.implicits._

    val window = Window.partitionBy(year($"Date").as("year")).orderBy($"Close".desc)
    df
      .withColumn("rank", row_number().over(window))
      .filter($"rank" === 1)
      .sort($"Close".desc)
      .drop($"rank")
      .show()
  }
}
