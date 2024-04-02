#!/usr/bin/env sh
mvn clean package

spark-submit --class edu.ucr.cs.cs167.cespa014.PreprocessTweets  --master "local[*]" ./target/ProjectD22-1.0-SNAPSHOT.jar ./Tweets_1k.json
spark-submit --class edu.ucr.cs.cs167.cespa014.AnalyzeTweets --master "local[*]" ./target/ProjectD22-1.0-SNAPSHOT.jar top-hashtags ./tweets_clean.json
spark-submit --class edu.ucr.cs.cs167.cespa014.TopicExtraction  --master "local[*]" ./target/ProjectD22-1.0-SNAPSHOT.jar ./tweets_clean.json
spark-submit --class edu.ucr.cs.cs167.cespa014.TopicPredictor --master "local[*]" target/ProjectD22-1.0-SNAPSHOT.jar ./tweets_topic.json
spark-submit  --class edu.ucr.cs.cs167.cespa014.TemporalAnalysis --master "local[*]" ./target/ProjectD22-1.0-SNAPSHOT.jar 02/21/2017, 12/22/2017