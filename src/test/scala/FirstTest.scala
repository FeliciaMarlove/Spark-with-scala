import org.apache.spark.sql.types.{DateType, DoubleType, StructField, StructType}
import org.apache.spark.sql.{Encoder, Encoders, Row, SparkSession}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.must.Matchers.contain
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

import java.sql.Date
// Several testing styles available in ScalaTest
// Here using FunSuite

class FirstTest extends AnyFunSuite {

  private val spark = SparkSession
    .builder()
    .appName("Tests")
    .master("local[*]")
    .getOrCreate()
  private val schema = StructType(
    Seq(
      StructField("date", DateType, nullable = true),
      StructField("open", DoubleType, nullable = true),
      StructField("close", DoubleType, nullable = true)
    )
  )

  // to run this test we need the run configuration for java.base/sun.* exports!
  /*
   * --add-exports
   * java.base/sun.nio.ch=ALL-UNNAMED
   * --add-exports
   * java.base/sun.util.calendar=ALL-UNNAMED
   */
  test("get highest closing prices per year") {
    val testRows = Seq(
      Row(Date.valueOf("2022-01-12"), 1.0, 2.0),
      Row(Date.valueOf("2023-03-01"), 1.0, 2.0),
      Row(Date.valueOf("2023-01-12"), 1.0, 3.0)
    )
    // contextual parameter => "using"/"given" in Scala 3
    // if a call doesn't provide explicit arguments, Scala will look for implicitly available values based on type
    // => implicits have to be explicitly typed
    implicit val encoder: Encoder[Row] = Encoders.row(schema)
    val testDf = spark.createDataset(testRows)
    val actualResultRows = Main.getHighestClosingPricesPerYear(testDf)
      .drop("rank")
      .collect()
    val expectedResultRows = Seq(
      Row(Date.valueOf("2022-01-12"), 1.0, 2.0),
      Row(Date.valueOf("2023-01-12"), 1.0, 3.0)
    )
    // there is another function theSameElementsInOrderAs if order matters
    actualResultRows should contain theSameElementsAs expectedResultRows
  }

  test("add(2, 3) returns 5") {
    val result = Main.add(2, 3)
    assertResult(expected = 5)(actual = result)
    assert(result == 5)
  }
}
