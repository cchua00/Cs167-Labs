package edu.ucr.cs.cs167.cchua032;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;
import java.io.IOException;
import java.util.Map;

public class Aggregation {
    public static void main(String[] args) throws IOException {
        final String inputPath = args[0];
        SparkConf conf = new SparkConf();
        if (!conf.contains("spark.master"))
            conf.setMaster("local[*]");
        System.out.printf("Using Spark master '%s'\n", conf.get("spark.master"));
        conf.setAppName("CS167-Lab5");
        try (JavaSparkContext spark = new JavaSparkContext(conf)) {
            JavaRDD<String> logFile = spark.textFile(inputPath);
            JavaPairRDD<String, Integer> codes = logFile.mapToPair(
                    s -> {
                        String[] parts = s.split("\\s+");
                        return new Tuple2<>(parts[parts.length - 2], 1); // Assuming the response code is the second-to-last element
                    }
            );
            Map<String, Long> counts = codes.countByKey();
            for (Map.Entry<String, Long> entry : counts.entrySet()) {
                System.out.printf("Code '%s' : number of entries %d\n", entry.getKey(), entry.getValue());
            }
            System.in.read();
        }
    }
}