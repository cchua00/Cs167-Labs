package edu.ucr.cs.cs167.cespa014
import org.apache.spark.SparkConf
import org.apache.spark.ml.evaluation.{BinaryClassificationEvaluator, MulticlassClassificationEvaluator}
import org.apache.spark.ml.feature.{HashingTF, StringIndexer, Tokenizer, VectorAssembler}
import org.apache.spark.ml.{Pipeline, PipelineModel}
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}
import org.apache.spark.ml.classification.LogisticRegression

object TopicPredictor {

  def main(args : Array[String]) {
    if (args.length != 1) {
      println("Usage <input file>")
      println("  - <input file> path to a JSON file input")
      sys.exit(0)
    }
    val inputfile = args(0)
    val conf = new SparkConf
    if (!conf.contains("spark.master"))
      conf.setMaster("local[*]")
    println(s"Using Spark master '${conf.get("spark.master")}'")

    val spark = SparkSession
      .builder()
      .appName("CS167 Project")
      .config(conf)
      .getOrCreate()

    val t1 = System.nanoTime
    try {
      // TODO: read JSON file as a DataFrame
      var tweets: DataFrame = spark.read.json(inputfile)

      tweets = tweets.na.fill("")
      // TODO: Tokenizer that finds all the tokens (words) from the tweets text and user description.
      val tokenizer_Text = new Tokenizer()
        .setInputCol("text")
        .setOutputCol("text_tokens")

      val tokenizer_UserD = new Tokenizer()
        .setInputCol("user_description")
        .setOutputCol("userD_tokens")

      // TODO: HashingTF transformer that converts the tokens into a set of numeric features
      val hashingTF_Text = new HashingTF()
        .setInputCol("text_tokens")
        .setOutputCol("text_features")

      val hashingTF_UserD = new HashingTF()
        .setInputCol("userD_tokens")
        .setOutputCol("userD_features")

      // TODO: StringIndexer that converts each topic to an index
      val stringIndexer = new StringIndexer()
        .setInputCol("topic")
        .setOutputCol("label")
        .setHandleInvalid("skip")

      // TODO: LogisticRegression that predicts the topic from the set of features.
      val lr = new LogisticRegression()
        .setFeaturesCol("text_features")
        .setFeaturesCol("userD_features")
        .setLabelCol("label")
        .setMaxIter(50)

      val assembler = new VectorAssembler()
        .setInputCols(Array("text_features", "userD_features"))
        .setOutputCol("features")

      // TODO: create a pipeline that includes all the previous transformations and the model
      val pipeline = new Pipeline()
        .setStages(Array(tokenizer_Text, tokenizer_UserD, hashingTF_Text, hashingTF_UserD, stringIndexer, assembler, lr))

      // TODO: split the data into 80% train and 20% test
      val Array(trainingData: Dataset[Row], testData: Dataset[Row]) = tweets.randomSplit(Array(0.8, 0.2))

      // TODO: Fit the model with the trainingData
      val model = pipeline.fit(trainingData)

      // TODO: apply the model to your test set and show sample of the result
      val predictions: DataFrame = model.transform(testData)
      predictions.select("id", "text", "topic", "user_description", "label", "prediction").show()

      // TODO: evaluate the test results
      val precisionEvaluator = new MulticlassClassificationEvaluator()
        .setLabelCol("label")
        .setPredictionCol("prediction")
        .setMetricName("weightedPrecision")
      val precision = precisionEvaluator.evaluate(predictions)
      println(s"Precision = $precision")

      val recallEvaluator = new MulticlassClassificationEvaluator()
        .setLabelCol("label")
        .setPredictionCol("prediction")
        .setMetricName("weightedRecall")
      val recall = recallEvaluator.evaluate(predictions)
      println(s"Recall = $recall")

      val t2 = System.nanoTime
      println(s"Applied TweetsPredictor algorithm on input $inputfile in ${(t2 - t1) * 1E-9} seconds")
    } finally {
      spark.stop
    }
  }
}
