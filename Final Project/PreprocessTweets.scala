package edu.ucr.cs.cs167.cespa014
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.udf

import scala.collection.mutable

object PreprocessTweets {
  def main(args: Array[String]): Unit = {
    val inputfile: String = args(0)
    val outputfile: String = "tweets_clean"

    val getHashtagTexts: UserDefinedFunction = udf((x: mutable.WrappedArray[GenericRowWithSchema]) => {
      x match {
        case x: mutable.WrappedArray[GenericRowWithSchema] => x.map(_.getAs[String]("text"))
        case _ => null
      }
    })

    val conf = new SparkConf
    if (!conf.contains("spark.master"))
      conf.setMaster("local[*]")
     println(s"Using Spark master '${conf.get("spark.master")}'")

    val spark = SparkSession
      .builder()
      .appName("CS167 Project")
      .config(conf)
      .getOrCreate()
    spark.udf.register("getHashtagTexts", getHashtagTexts)

    try {

      // Reads json file
      var df = spark.read.json(inputfile)

      // Creates temporary table named "tweets"
      df.createOrReplaceTempView("tweets")

      // Using SQL to select relevant columns
      df = spark.sql(
        """SELECT
       id,
       text,
       created_at,
       place.country_code,
       getHashtagTexts(entities.hashtags) AS hashtags,
       user.description AS user_description,
       retweet_count,
       reply_count,
       quoted_status_id
       FROM tweets""")

      // Prints schema
      df.printSchema()

      // Saves dataframe as a JSON file and overwrites file with same name
      df.write.mode("overwrite").json(outputfile + ".json")
    } finally {
      spark.stop
    }
  }
}