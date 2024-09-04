import org.apache.spark.sql
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.{col, row_number, year}
import org.apache.spark.sql.{DataFrame, SparkSession, functions}

// data sample for exercise https://www.kaggle.com/datasets/jacksoncrow/stock-market-dataset AAPL.csv

object Main {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("Scala-with-Spark")
      .master(
        "local[*]"
      )
      .getOrCreate()

    val df: DataFrame = spark.read
      .option("header", value = true)
      .option("inferSchema", value = true)
      .csv("./src/main/resources/AAPL.csv")

    val renamedColumns = List(
      col("Date").as("date"),
      col("Open").as("open"),
      col("High").as("high"),
      col("Low").as("low"),
      col("Close").as("close"),
      col("Ad Close").as("adjClose"),
      col("Volumne").as("volumne")
    )

    val stockData = df.select(renamedColumns: _*)

    val highestClosingPricesPerYear = getHighestClosingPricesPerYear(stockData)
  }

  def getHighestClosingPricesPerYear(df: DataFrame): DataFrame = {
    val spark = df.sparkSession
    import spark.implicits._
    val window = Window.partitionBy(year($"date").as("yera")).orderBy($"close".desc)
    df
      .withColumn("rank", row_number().over(window))
      .filter($"rank" === 1)
      .sort($"close".desc)
  }

  def add(x: Int, y: Int): Int = x + y

}
