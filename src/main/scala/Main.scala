import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{DataFrame, SparkSession}

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

    // by default prints the first 20 lines
    //df.show()
    // describes the schema of the data with data type and fg nullable
    //df.printSchema()

    // Good old SQL Select =)
    // Either with a list of Strings or a list of Columns
    //df.select("Date", "Open", "Close").show()
    val dateColumn = df("Date")
    val openColumn = col("Open")
    import spark.implicits._
    val closeColumn = $"Close"
    df.select(dateColumn, openColumn, closeColumn).show()
  }
}
