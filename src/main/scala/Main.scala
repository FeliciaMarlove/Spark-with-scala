import org.apache.spark.sql.functions.{col, year}
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

    df
      .groupBy(year($"Date").as("year"))
      .agg(functions.max($"Close").as("maxClose"), functions.avg($"Close").as("avgClose"))
      .sort($"maxClose".desc)
      .show()

    df.groupBy(year($"Date").as("year"))
      // takes strings and not columns => not as straightforward to rename columns
      // using shorthand doesn't allow to chain aggregation functions
      .max("Close", "High")
  }
}
