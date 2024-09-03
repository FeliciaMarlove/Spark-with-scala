import org.apache.spark.sql.functions.{col, lit}
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.{DataFrame, SparkSession, functions}

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
//    val dateColumn = df("Date")
    val openColumn = col("Open")
//    import spark.implicits._
//    val closeColumn = $"Close"
//    df.select(dateColumn, openColumn, closeColumn).show()
    val calculatedColumn = openColumn + (2.0)
    val stringColumn = calculatedColumn.cast(StringType)

    // create a Column with a literal value
    val litColumn = lit(2.0)
    val concatenatedColumn = functions.concat(openColumn, calculatedColumn, stringColumn, lit("toto"), litColumn)

    df.select(openColumn, calculatedColumn, stringColumn, concatenatedColumn)
      .filter(calculatedColumn > 2.5)
      // Must use "===" to differentiate from Scala's "=="
      //.filter(openColumn === calculatedColumn)
      // prevent results from being truncated in the output
      .show(truncate = false)

  }
}
