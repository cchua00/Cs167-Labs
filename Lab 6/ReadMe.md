# Lab 6

## Student information

* Full name: Chris Chua
* E-mail: cchua032@ucr.edu
* UCR NetID: chua032
* Student ID: 862292532

## Answers

* (Q1) What are these two arguments?
- First is the command, it will determine what transformation or action we use.
- Second is the name of our input file.
* (Q2) If you do this bonus part, copy and paste your code in the README file as an answer to this question.
``` 
val averages: RDD[(String, (Long, Long))] = loglinesByCode.aggregateByKey((0L, 0L))(
  (acc, value) => (acc._1 + value, acc._2 + 1), // SeqOp
  (acc1, acc2) => (acc1._1 + acc2._1, acc1._2 + acc2._2) // CombOp
  )

          println(s"Average bytes per code for the file '$inputfile'")
          println("Code,Avg(bytes)")
          averages.collect().sorted.foreach(pair => println(s"${pair._1},${pair._2._1.toDouble / pair._2._2}"))
```
* (Q3) What is the type of the attributes `time` and `bytes` this time? Why?
- When the option("inferSchema", "true") is active, Spark SQL automatically infers the data types of each column in the CSV file based on the actual data it contains. This means that if the data in the time and bytes columns are numeric, Spark will infer their types as integer.
- If you comment out the .option("inferSchema", "true"), Spark will not attempt to infer the data types and will default all columns to string type. This is because, without explicit instructions to analyze the data content and determine the most appropriate data type, Spark opts for the safest choice, which is treating everything as text.
- This behavior is crucial for data processing and analysis tasks where the accurate representation of data types can significantly affect the outcomes of your computations and queries.
* (Q4) If you do this bonus part, copy and paste your code in the README file as an answer to this question.
```
    val filterTimestamp: Long = args(2).toLong
        val comparisonQuery = s"""
             SELECT
               response,
               SUM(CASE WHEN time < $filterTimestamp THEN 1 ELSE 0 END) AS countBefore,
               SUM(CASE WHEN time >= $filterTimestamp THEN 1 ELSE 0 END) AS countAfter
             FROM log_lines
             GROUP BY response
             ORDER BY response
             """

        println(s"Comparison of the number of lines per code before and after $filterTimestamp on file '$inputfile'")
        println("Code,CountBefore,CountAfter")
          spark.sql(comparisonQuery).collect().foreach(row => println(s"${row.getAs[String]("response")},${row.getAs[Long]("countBefore")},${row.getAs[Long]("countAfter")}"))

```
