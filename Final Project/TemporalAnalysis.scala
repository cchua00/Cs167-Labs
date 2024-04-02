package edu.ucr.cs.cs167.cespa014
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf

object TemporalAnalysis {
  def main(args: Array[String]): Unit = {
    // Initialize Spark configuration and create Spark session
    val conf = new SparkConf().setIfMissing("spark.master", "local[*]")
    val spark = SparkSession
      .builder()
      .appName("CS167_proj")
      .config(conf)
      .config("spark.sql.legacy.timeParserPolicy", "LEGACY") // To support older date time pattern
      .getOrCreate()

    // Import implicits for better integration of RDDs, DataFrames, and Datasets

    // Command-line arguments for start and end dates
    val startDate = args(0)
    val endDate = args(1)

    try {
      // Load the JSON dataset and create a temporary view
      spark.read.json("tweets_clean.json").createOrReplaceTempView("tweets")

      // Execute SQL query to filter and process the data
      val tweetCounts = spark.sql(
        s"""
           |SELECT country_code, COUNT(*) AS tweet_count
           |FROM (
           |  SELECT country_code, to_timestamp(created_at, 'EEE MMM dd HH:mm:ss Z yyyy') AS timestamp
           |  FROM tweets
           |  WHERE country_code IS NOT NULL AND created_at IS NOT NULL
           |) filtered_tweets
           |WHERE timestamp BETWEEN to_date('$startDate', 'MM/dd/yyyy') AND to_date('$endDate', 'MM/dd/yyyy')
           |GROUP BY country_code
           |HAVING tweet_count > 50
         """.stripMargin)

      // Write the results to a CSV file, coalescing into a single file
      tweetCounts.coalesce(1)
        .write
        .option("header", "true")
        .csv("CountryTweetsCount")

      // Uncomment the following line to display the result if needed
      // tweetCounts.show()

    } finally {
      // Stop the Spark session
      spark.stop()
    }
  }
}
