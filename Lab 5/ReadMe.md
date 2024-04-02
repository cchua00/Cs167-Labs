# Lab 5

## Student information

* Full name: Chris Chua
* E-mail: cchua032@ucr.edu
* UCR NetID: cchua032
* Student ID: 862292532

## Answers

* (Q1) Do you think it will use your local cluster? Why or why not?
* I think the local cluster was used because no applications appeared in the spark interface server.
* (Q2) Does the application use the cluster that you started? How did you find out?
* Yeah,the application ran on the cluster I started because there's one completed applications
    in the spark web interface and one under the worker node under the finished executors.
* (Q3) What is the Spark master printed on the standard output on IntelliJ IDEA?
* Using Spark master 'local[*]'
* Number of lines in the log file 30970
* (Q4) What is the Spark master printed on the standard output on the terminal?
* Using Spark master 'spark://class-146:7077'
* Number of lines in the log file 30970
* (Q5) For the previous command that prints the number of matching lines, how many tasks were created, and how much time it took to process each task.
- There are 4 tasks created (TID: 0, 1, 2, 3).
- (TID 0) in 1363 ms
-  (TID 1) in 1330 ms
-  (TID 2) in 1456 ms
- (TID 3) in 1530 ms
* (Q6) For the previous command that counts the lines and prints the output, how many tasks in total were generated?
- There is 6 tasks created 
-  (TID 1) in 1356 ms 
-  (TID 0) in 1377 ms 
-  (TID 3) in 103 ms 
-  (TID 2) in 1301 ms 
-  (TID 5) in 313 ms 
-  (TID 4) in 1891 ms
* (Q7) Compare this number to the one you got earlier.
- There are 2 added tasks when comparing the task from Q5 and Q6. 
* (Q8) Explain why we get these numbers.
- The input data is split into partitions, which Spark processes in parallel.
- The default number of partitions is determined by the size of the data and the number of available cores which could explain how we got these numbers.
* (Q9) What can you do to the current code to ensure that the file is read only once?
* JavaRDD<String> logFile = spark.textFile(inputfile).cache();
* (Q10) How many stages does your program have, and what are the steps in each stage?
* Stage 1: This involves reading the data from HDFS (textFile operation) and then mapping each log line to a pair of response code and count (mapToPair). This stage ends with the mapToPair transformation.
* Stage 2: The aggregation operation (countByKey) triggers a shuffle of the data because Spark needs to collect all values for each key to count them. This results in a new stage after the shuffle.
* (Q11) Why does your program have two stages?
* The first stage is driven by transformations that can be performed on individual partitions of the dataset without requiring data from other partitions
* The second stage is introduced by the countByKey action, which is an aggregation operation requiring data shuffling across the cluster to gather all values associated with each key. This necessitates a break in the lineage graph and the start of a new stage, as Spark must now perform network operations to move data between executors or nodes.
