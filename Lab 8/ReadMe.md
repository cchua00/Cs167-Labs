# Lab 8

## Student information

* Full name: Chris Chua
* E-mail: cchua032@ucr.edu
* UCR NetID: cchua032
* Student ID: 862292532

## Answers

* (Q1) What is the schema of the file? Copy it to the README file and keep it for your reference.

    ```
  root
  |-- hashtags: array (nullable = true)
  |    |-- element: string (containsNull = true)
  |-- id: long (nullable = true)
  |-- lang: string (nullable = true)
  |-- place: struct (nullable = true)
  |    |-- country_code: string (nullable = true)
  |    |-- name: string (nullable = true)
  |    |-- place_type: string (nullable = true)
  |-- text: string (nullable = true)
  |-- time: string (nullable = true)
  |-- user: struct (nullable = true)
  |    |-- followers_count: long (nullable = true)
  |    |-- statuses_count: long (nullable = true)
  |    |-- user_id: long (nullable = true)
  |    |-- user_name: string (nullable = true)
    ```

* (Q2) What is your command to import the `tweets.json` file?

    ```shell
        mongoimport --collection tweets --file tweets.json
    ```

* (Q3) What is the output of the import command?

    ```text
    2024-02-29T15:55:06.456-0800    connected to: mongodb://localhost/
    2024-02-29T15:55:06.503-0800    1000 document(s) imported successfully. 0 document(s) failed to import.
    ```

* (Q4) What is your command to count the total number of records in the `tweets` collection and what is the output of the command?

    ```javascript
        test> db.tweets.find().count()
            1000

    ```

  * (Q5) What is your command for this query?

      ```javascript
          db.tweets.find(
        {"place.country_code": "JP", "user.statuses_count": {$gt: 50000}},
        {"user.screen_name": 1, "user.followers_count": 1, "user.statuses_count": 1, _id: 0}
          ).sort({"user.followers_count": 1})
         db.tweets.countDocuments({"place.country_code": "JP", "user.statuses_count": {$gt: 50000}})

      ```

* (Q6) How many records does your query return?
* 16

* (Q7) What is the command that retrieves the results without the _id field?

    ```javascript
            db.tweets.find(
              {"place.country_code": "JP", "user.statuses_count": {$gt: 50000}},
              {"user.screen_name": 1, "user.followers_count": 1, "user.statuses_count": 1, _id: 0}
                ).sort({"user.followers_count": 1})

    ```

* (Q8) What is the command to insert the sample document? What is the result of running the command?

    ```javascript
            db.collection.insertOne({
            id: NumberLong('921633456941125634'), 
            place: { country_code: 'JP', name: 'Japan', place_type: 'city' }, 
            user: {user_name: 'xyz2', followers_count: [2100, 5000], statuses_count: 55000}, 
            hashtags: ['nature'], 
            lang: 'ja'
            })


    ```


* (Q9) Does MongoDB accept this document while the followers_count field has a different type than other records?
* MongoDB accepts documents with fields having different types across records. In this case, followers_count being an array in the given document, whereas it might be a single value in others, is acceptable. MongoDB's schema-less nature allows for flexibility in data representation, accommodating variations in document structure within the same collection.
* (Q10) What is your command to insert this record?

    ```javascript
            db.tweets.insertOne({ id: NumberLong('921633456941121354'), place: { country_code: 'JP', name: 'Japan
    ', place_type: 'city' }, user: { user_name: 'xyz3', followers_count: { last_month: 550, this_month: 2200 },
    statuses_count: 112000 }, hashtags: ['art', 'tour'], lang: 'ja' })
    {
    acknowledged: true,
    insertedId: ObjectId('65e11d461eb6085af6b96773')
    }

    ```


* (Q11) Where did the two new records appear in the sort order?
* The two new records, xyz2 and xyz3, appear at the near the beginning of the sort order.

* (Q12) Why did they appear at these specific locations?
* They appear in these specific locations due to their followers_count field types, which are different from the single numeric values in the rest of the documents. 
* MongoDB sorts these records based on the field type, with numeric values sorted first, and then arrays or objects. 


* (Q13) Where did the two records appear in the ascending sort order? Explain your observation.
* They appeared , in the second half of the sort order.
*  Since xyz2 has an array and xyz3 has an object for followers_count, MongoDB's sorting behavior places them at the end of the sorted list, after all documents with numerical followers_count.

* (Q14) Is MongoDB able to build the index on that field with the different value types stored in the `user.followers_count` field?
* MongoDB can build an index on the user.followers_count field despite the different value types (e.g., single value, array, object) stored in this field. 
* However, the indexing behavior and performance might vary based on the data type diversity and how the index is utilized in queries.

* (Q15) What is your command for building the index?

    ```javascript
            db.tweets.createIndex({"user.followers_count": 1})
    ```

* (Q16) What is the output of the create index command?

    ```text
            user.followers_count_1
    ```

* (Q17) What is your command for this query?

    ```javascript
            db.tweets.find(
  { "hashtags": { "$in": ["job", "hiring", "IT"] } },
  { "text": 1, "hashtags": 1, "user.user_name": 1, "user.followers_count": 1, _id: 0 }
    ).sort({"user.followers_count": 1})
        test> db.tweets.countDocuments({ "hashtags": { "$in": ["job", "hiring", "IT"] } })

    ```

* (Q18) How many records are returned from this query?

    ```
        24
    ```

* (Q19) What is your command for this query?
    ```javascript
            db.tweets.aggregate([
  { $group: {
      _id: "$place.country_code",
      totalTweets: { $sum: 1 }
  }},
  { $sort: { totalTweets : -1 } },
  { $limit: 5 }
  ])

    ```

* (Q20) What is the output of the command?
    ```
       [
  { _id: 'US', totalTweets: 153 },
  { _id: 'JP', totalTweets: 107 },
  { _id: 'GB', totalTweets: 89 },
  { _id: 'TR', totalTweets: 65 },
  { _id: 'IN', totalTweets: 56 }
    ]

    ```
* (Q21) What is your command for this query?
    ```javascript
            db.tweets.aggregate([
  { $unwind: "$hashtags" },
  { $group: {
      _id: "$hashtags",
      count: { $sum: 1 }
  }},
  { $sort: { count: -1 } },
  { $limit: 5 }
    ])

    ```

* (Q22) What is the output of the command?
```
    [
  { _id: 'ALDUBxEBLoveis', count: 56 },
  { _id: 'FurkanPalalı', count: 31 },
  { _id: 'no309', count: 31 },
  { _id: 'LalOn', count: 31 },
  { _id: 'job', count: 19 }
    ]

```
