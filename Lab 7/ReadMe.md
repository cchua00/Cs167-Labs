# Lab 7

## Student information

* Full name: Chris Chua
* E-mail: cchua032@ucr.edu
* UCR NetID: cchua032
* Student ID: cchua032

## Answers

* (Q1) What is the nesting level of this column `root.entities.hashtags.element.text`?
  - root is at level 0
  - entities increases the level to 1
  - hashtags increases the level to 2
  - element does not add to the nesting level as per your note
  text would still be considered at level 2
  - Answer: The nesting level of root.entities.hashtags.element.text is 2.
* (Q2) In Parquet, would this field be stored as a repeated column? Explain your answer.
 - Yes, this field would be stored as a repeated column in Parquet to accommodate the potential for multiple hashtag texts within a single tweet entry.
* (Q3) Based on this schema answer the following:***

    - How many fields does the `place` column contain?
    - 5
    - How many fields does the `user` column contain?
    - 38
    - What is the datatype of the `time` column?
    - string
    - What is the datatype of the `hashtags` column?
    - Array

* (Q4) Based on this new schema answer the following:
    - *How many fields does the `place` column contain?*
    - 14
    - *How many fields does the `user` column contain?*
    - 39
    - *What is the datatype of the `time` column?*
    - Timestamp
    - *What is the datatype of the `hashtags` column?*
    - Array**

* (Q5) What is the size of each folder? Explain the difference in size, knowing that the two folders `tweets.json` and `tweets.parquet` contain the exact same dataframe?
 - JSON (JavaScript Object Notation) is a text-based format that can be read easily by humans. It is structured as a collection of key-value pairs. Being text-based, it can be larger in size because it uses characters to represent data and structure, which includes the keys for each value in the data.

- Parquet, on the other hand, is a binary columnar storage format. It is optimized for use with data processing frameworks like Apache Spark and Hadoop. Parquet stores data in a binary format which is not human-readable, but is highly efficient for both storage and processing. It compresses the data and stores it in a way that allows for efficient, compressed, and encoding-optimized storage of data types. This results in much smaller file sizes compared to JSON.

* (Q6) What is the error that you see? Why isn't Spark able to write this dataframe in the CSV format?
 - Exception in thread "main" org.apache.spark.sql.AnalysisException: [UNSUPPORTED_DATA_TYPE_FOR_DATASOURCE] T
  he CSV datasource doesn't support the column `place` of the type "STRUCT<country_code: STRING, name: STRING
  , place_type: STRING>".

The error you're encountering indicates that Spark is unable to write the dataframe to CSV format because the place column contains complex data types (STRUCTs with nested STRINGs). The CSV format is flat and does not support nested or complex data types like STRUCT. Each column in a CSV file must be a simple data type like string, integer, etc. To save the dataframe with complex types like place to CSV, you would need to flatten the structure into simple columns or convert complex types into a string representation, often using some form of serialization like JSON.
* (Q7.1) What do you see in the output? Copy it here.
  Using Spark master 'local[*]'
 - +-------+------+
 - |country| count|
 - +-------+------+
 - |     US|196740|
 - |     JP|133690|
 - |     GB| 65130|
 - |     PH| 57320|
 - |     BR| 44570|
 - +-------+------+
* (Q7.2) What do you observe in terms of run time for each file? Which file is slowest and which is the fastest? Explain your observation?.
  - Parquet is the fastest at 4.438 seconds, benefiting from its columnar storage format which optimizes for both compression and scanning efficiency.
  Processed JSON follows at 4.814 seconds, likely improved by simplifications and optimizations applied to its structure.
  - The original JSON is the slowest at 11.438 seconds, due to its verbose syntax and lack of optimizations for big data processing.
  - In essence, Parquet's efficiency for analytical queries, thanks to its columnar format and compression techniques, makes it the superior choice for performance in Spark operations.
* (Q8.1) What are the top languages that you see? Copy the output here.
 - +----+------+
 - |lang| count|
 - +----+------+
 - |  en|384960|
 - |  ja|130830|
 - | und| 76000|
 - |  es| 60420|
 - |  in| 47580|
 - +----+------+


* (Q8.2) Do you also observe the same performance for the different file formats?
  - These results indicate a performance advantage for the processed JSON and Parquet formats over the original JSON file. While the difference between the processed JSON and Parquet is minimal, both significantly outperform the original JSON file.
  - The Parquet format shows a slight performance edge over processed JSON, with both substantially faster than the original JSON file. This is consistent with expectations, as Parquet's columnar storage format is optimized for efficiency in both storage and query processing, leading to faster read and aggregation operations. The processed JSON likely benefits from optimizations applied during preprocessing, making it faster than the original JSON but still slightly slower than Parquet due to the inherent advantages of Parquet's storage model.
* (Q9) After step B.3.2, how did the schema change? What was the effect of the `explode` function?
  - After step B.3.2, the schema changed from having the top_langs column as an array of structures to having individual columns for lang and lang_count. The explode function transformed each element of the array into a separate row, paired with the country and count, effectively flattening the array into multiple rows.
* (Q10) For the country with the most tweets, what is the fifth most used language? Also, copy the entire output table here.
 - +-------+------+----+--------------------+
 - |country| count|lang|        lang_percent|
 - +-------+------+----+--------------------+
 - |     US|196740|  en|  0.8595100132154112|
 - |     US|196740| und| 0.08661177188167124|
 - |     US|196740|  es|0.013977838771983329|
 - |     US|196740|  tl|0.006811019619802785|
 - |     US|196740|  ja|0.004269594388533089|
 - |     JP|133690|  ja|  0.9513052584336898|
 - |     JP|133690| und| 0.01668037998354402|
 - |     JP|133690|  en|0.015558381329942405|
 - |     JP|133690|  in|0.005011593986087217|
 - |     JP|133690|  th|0.002019597576482...|
 - |     GB| 65130|  en|  0.8922155688622755|
 - |     GB| 65130| und| 0.07032089666820206|
 - |     GB| 65130|  es|0.004606172270842929|
 - |     GB| 65130|  fr|0.004452633195148165|
 - |     GB| 65130|  ar|0.003992015968063872|
 - |     PH| 57320|  tl|  0.5087229588276343|
 - |     PH| 57320|  en|   0.349092812281926|
 - |     PH| 57320| und| 0.06856245638520586|
 - |     PH| 57320|  in|0.028087927424982555|
 - |     PH| 57320|  es|0.008199581297976273|
 - |     BR| 44570|  pt|  0.8761498765986089|
 - |     BR| 44570| und| 0.04711689477226834|
 - |     BR| 44570|  en| 0.03006506618801885|
 - |     BR| 44570|  es|0.021539151895894098|
 - |     BR| 44570|  it|0.005160421808391295|
  

For the country with the most tweets (US), the fifth most used language is Japanese ('ja'), with a percentage of approximately 0.4269594388533089%.
* (Q11) Does the observed statistical value show a strong correlation between the two columns? Note: a value close to 1 or -1 means there is high correlation, but a value that is close to 0 means there is no correlation.
  Using Spark master 'local[*]'
  0.05958391887410892
  

* (Q12.1) What are the top 10 hashtags? Copy paste your output here.
 - +-------------------+-----+
 - |            hashtag|count|
 - +-------------------+-----+
 - |     ALDUBxEBLoveis| 4110|
 - |                job| 2730|
 - |             trndnl| 2420|
 - |ShowtimeLetsCelebr8| 2200|
 - |             Hiring| 2080|
 - |       FurkanPalal?| 1130|
 - |              no309| 1130|
 - |              LalOn| 1130|
 - |            sbhawks| 1050|
 - |             hiring| 1000|
 - +-------------------+-----+

Operation top-hashtags on file './tweets.json' finished in 6.6714254 seconds
Operation top-hashtags on file './tweets.parquet' finished in 6.827213700000001 seconds
* (Q12.2) For this operation, do you observe difference in performance when comparing the two different input files `tweets.json` and `tweets.parquet`? Explain the reason behind the difference.
  The performance difference between analyzing top hashtags from tweets.json and tweets.parquet is minimal because the operation's efficiency is more influenced by the computational workload of aggregation rather than the initial data read speed. Parquet's columnar storage benefits are less impactful for operations that are CPU-bound, like text aggregation, where both data formats eventually get loaded into Spark's in-memory format for processing.
  
* 
* | Command               | Tweets_1m.json | tweets.json | tweets.parquet  |
* | top-country           | 11.4381678     | 4.8143437   |4.4384247000000006|
* | top-lang              |7.7179321000000005|3.3860430000000004|3.3347417000000004|
* | top-country-with-lang |12.356981300000001|6.178071500000001|5.9616252 |
* | corr                  | 7.853079       |2.5914200000000003| 2.1251372   |
* | top-hashtags          |  N/A           | 6.6714254   | 6.827213700000001|
