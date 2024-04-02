# Lab 9

## Student information
* Full name: Chris Chua
* E-mail: cchua0322@ucr.edu
* UCR NetID: cchua032
* Student ID: 862292532

## Answers

* (Q1) Fill the following table.
* Accuracy of the test set is 0.8138889178710074

                     | Parameter      | Value         |
                     |----------------|---------------|
                     | numFeatures    | 2048  |
                     | fitIntercept   | true  |
                     | regParam       | 0.010000 |
                     | maxIter        | 10.000000     |
                     | threshold      | 0.000000  |
                     | tol            | 0.000100       |

* Applied sentiment analysis algorithm on input sentiment.csv in 271.6607108 seconds
* Accuracy of the test set is 0.8176588215931534
* (Q2) Fill the following table.
* 
                     | Parameter      | Value         |
                     |----------------|---------------|
                     | numFeatures    | 2048  |
                     | fitIntercept   | true  |
                     | regParam       | 0.100000 |
                     | maxIter        | 10.000000     |
                     | threshold      | 0.000000  |
                     | tol            | 0.000100       |
* Applied sentiment analysis algorithm on input hdfs://class-146:9000/user/cs167/sentiment_cchua032.csv in 284.616115729 seconds
* (Q3) What difference do you notice in terms of the best parameters selected, the accuracy, and the run time between running the program locally and on your Spark cluster having multiple nodes?
* The time only differed by a difference of 12 seconds. 
* The time was not that different because on the spark server I only used my computer so there were only 4 cores.
* This limitation restricted the ability to fully utilize parallel processing. 
* If there were more computers connected then I would have seen a big difference due to the use of being able to use more cores, which would have been able to better use parallel processing.
* Since only my computer was used I couldn't parallel process. 
