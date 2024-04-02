# Lab 10

## Student information

* Full name:Chris Chua
* E-mail: cchua032@ucr.edu
* UCR NetID: cchua032
* Student ID: 862292532

## Answers

* (Q1) What is the schema of the file after loading it as a Dataframe

    ```
        root
            |-- Timestamp: timestamp (nullable = true)
            |-- Text: string (nullable = true)
            |-- Latitude: double (nullable = true)
            |-- Longitude: double (nullable = true)

    ```

* (Q2) Why in the second operation, convert, the order of the objects in the  tweetCounty RDD is (tweet, county) while in the first operation, count-by-county, the order of the objects in the spatial join result was (county, tweet)?

    ``` 
        In the "convert" process, spatial join is utilized to enrich the tweet dataset with county data, while the "count-by-county" task employs spatial join in reverse, integrating tweet data into the county dataset.
    ```

  * (Q3) What is the schema of the tweetCounty Dataframe?

  ``` 
      root
      |-- g: geometry (nullable = true)
      |-- Timestamp: string (nullable = true)
      |-- Text: string (nullable = true)
      |-- CountyID: string (nullable = true)
  ```

  * (Q4) What is the schema of the convertedDF Dataframe?

  ```
    root
    |-- CountyID: string (nullable = true)
    |-- keywords: array (nullable = true)
    |    |-- element: string (containsNull = false)
    |-- Timestamp: string (nullable = true)
  ```

* (Q5) For the tweets_10k dataset, what is the size of the decompressed ZIP file as compared to the converted Parquet file?

  | Size of the original decompressed file | Size of the Parquet file |
      |----------------------------------------|--------------------------|
  | `791KB`                                | `45KB`                   |

* (Q6) (Bonus) Write down the SQL query(ies) that you can use to compute the ratios as described above. Briefly explain how your proposed solution works.

    ```SQL
    -- Enter the SQL query(ies) here
    ```

    ```text
    Use this space to explain how it works.
    ```