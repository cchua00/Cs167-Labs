#!/usr/bin/env bash
mvn clean package
spark-submit --class edu.ucr.cs.cs167.cchua032.Filter cchua032_lab5-1.0-SNAPSHOT.jar hdfs://class-146:9000/user/cs167/nasa_19950630.22-19950728.12.tsv hdfs://class-146:9000/user/cs167/filter_output 302
spark-submit --class edu.ucr.cs.cs167.cchua032.Aggregation cchua032_lab5-1.0-SNAPSHOT.jar hdfs://class-146:9000/user/cs167/nasa_19950630.22-19950728.12.tsv

