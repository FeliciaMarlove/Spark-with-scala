import org.apache.spark.sql.functions.{col, lit}
import org.apache.spark.sql.types.StringType
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

    // by default prints the first 20 lines
    // df.show()
    // describes the schema of the data with data type and fg nullable
    // df.printSchema()

    // Good old SQL Select =)
    // Either with a list of Strings or a list of Columns
    // df.select("Date", "Open", "Close").show()
//    val dateColumn = df("Date")
    val openColumn = col("Open")
//    import spark.implicits._
//    val closeColumn = $"Close"
//    df.select(dateColumn, openColumn, closeColumn).show()
    val calculatedColumn = openColumn + (2.0)
    val stringColumn = calculatedColumn.cast(StringType)

    // create a Column with a literal value
    val litColumn = lit(2.0)
    val concatenatedColumn = functions.concat(
      openColumn,
      calculatedColumn,
      stringColumn,
      lit("toto"),
      litColumn
    )

//    df.withColumnRenamed("Close", "close")
//      .withColumnRenamed("Open", "open")
//      .show()
//
//    val renamedColumns = List(
//      col("Date").as("date"),
//      concatenatedColumn.as("key"),
//      stringColumn.as("string"),
//      col("Adj Close").as("adjClose")
//    )
//
//    // : _* transform a sequence into individual elements (=> varargs)
//    df.select(renamedColumns: _*).show()

    val stockData = df
      .select(
        df.columns.map(columnName =>
          col(columnName).as(columnName.toLowerCase)
        ): _*
      )
      .withColumn("difference", col("close") - col("open"))
      // "where" is an alias for "filter"
      //.where(col("open")+((col("open") / 100) * 10) < col("close"))
      .where(col("close") > col("open") * 1.1) // closing price is 10% higher than opening price

    stockData.show()
  }
}
