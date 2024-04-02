package edu.ucr.cs.cs167.cespa014
import org.apache.spark.SparkConf
import org.apache.spark.sql.functions.{array_intersect, explode}
import org.apache.spark.sql.{SparkSession, functions}


object TopicExtraction {

  def main(args: Array[String]): Unit = {
    val inputfile: String = args(0)
    val outputfile: String = "tweets_topic"

    val conf = new SparkConf //config object
    if (!conf.contains("spark.master")) //check if spark master url is set
      conf.setMaster("local[*]")//if not run locally
    println(s"Using Spark master '${conf.get("spark.master")}'")

    val spark = SparkSession //create the sparksession
      .builder()
      .appName("CS167 Project")
      .config(conf)
      .getOrCreate()

    try {
      import spark.implicits._

      // Reads json file
      var df = spark.read.json(inputfile)

      // Compute the most frequent hashtags
      val topHashtags = df
        .select(explode($"hashtags").as("hashtag"))
        .groupBy("hashtag").count()
        .orderBy($"count".desc)
        .limit(20).select("hashtag")// the top 20 hashtags
        .collect()
        .map(_.getString(0))

      //store the top 20 hashtags in a df
      val topHashtagsDF = Seq(topHashtags).toDF("top20")
      // Join the original DataFrame with the DataFrame containing top hashtags
      df = df.crossJoin(topHashtagsDF)

      // Add a new column for the topic using array_intersect between hashtags and top20
      df = df.withColumn("topic", array_intersect($"hashtags", $"top20"))
        .filter(functions.size($"topic") > 0) // Filter out records with empty topic

      df = df.drop("hashtags") //drop hashtags
      df = df.drop("top20") //drop top20
      df = df.withColumn("topic", $"topic"(0).cast("string"))//account for multiple intersection and choose only the first one

      //select all the column in the order specified
      df = df.select("id", "text", "created_at",
        "country_code", "topic", "user_description",
        "retweet_count", "reply_count", "quoted_status_id")
      df.show() //print df
      df.printSchema() //print schema

      // Saves dataframe as a JSON file and overwrites file with same name
      df.write.mode("overwrite").json(outputfile + ".json")

      // print total records
      println(s"Total number of records: ${df.count()}")

    } finally {
      spark.stop
    }

  }
}
