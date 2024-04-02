package edu.ucr.cs.cs167.cchua032

import org.apache.spark.SparkConf
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.{explode, udf}
import org.apache.spark.sql.{DataFrame, SparkSession}

import scala.collection.mutable

object AnalyzeTweets {

  def main(args: Array[String]) {
    val operation: String = args(0)
    val inputfile: String = args(1)

    val conf = new SparkConf
    if (!conf.contains("spark.master"))
      conf.setMaster("local[*]")

    println(s"Using Spark master '${conf.get("spark.master")}'")

    val spark = SparkSession
      .builder()
      .appName("CS167 Project")
      .config(conf)
      .getOrCreate()

    if (!(inputfile.endsWith(".json") || inputfile.endsWith(".parquet"))) {
      Console.err.println(s"Unexpected input format. Expected file name to end with either '.json' or '.parquet'.")
    }
    var df : DataFrame = if (inputfile.endsWith(".json")) {
      spark.read.json(inputfile)
    } else {
      spark.read.parquet(inputfile)
    }
    df.createOrReplaceTempView("tweets")

    try {
      import spark.implicits._
      var valid_operation = true
      val t1 = System.nanoTime
      operation match {
        case "top-hashtags" =>
          // Flattens "hashtags" array in dataframe
          df = df.withColumn("hashtags", explode($"hashtags"))

          // Creates temporary table named "tweets"
          df.createOrReplaceTempView("tweets")

          // Counts occurrences of each hashtag
          // Groups by hashtag,
          // Orders in descending order by counts
          // Limits the output to top 20 hashtags
          // Updates dataframe with results
          df = spark.sql("SELECT hashtags, COUNT(*) AS counts FROM tweets GROUP BY hashtags ORDER BY counts DESC LIMIT 20")

          // Selects hashtags column from dataframe
          // Converts to string array
          // Stores it in topHashtagsArray.
          val topHashtagsArray = df.select("hashtags").as[String].collect()

          // Prints each hashtag in topHashtagsArray
          topHashtagsArray.foreach(println)
        case _ => valid_operation = false
      }
      val t2 = System.nanoTime
      if (valid_operation)
        println(s"Operation $operation on file '$inputfile' finished in ${(t2 - t1) * 1E-9} seconds")
      else
        Console.err.println(s"Invalid operation '$operation'")
    } finally {
      spark.stop
    }
  }
}
