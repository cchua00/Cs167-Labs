# Lab 4

## Student information

* Full name: Chris Chua
* E-mail:cchua032@ucr.edu
* UCR NetID:cchua032
* Student ID:862292532

## Answers

* (Q1) What do you think the line `job.setJarByClass(Filter.class);` does?
* - This line tells Hadoop which jar file to send to the nodes to perform map and reduce by telling the filter class.
* (Q2) What is the effect of the line `job.setNumReduceTasks(0);`?
* - Setting the number of reduce tasks to 0 means that no reduce phase will be executed in this MapReduce job.
* (Q3) Where does the `main` function run? (Driver node, Master node, or an executor node).
* - The main function runs on the master and slave node.
* (Q4) How many lines do you see in the output?
* - There are 27,972 files that passed the filter condition. 
* (Q5) How many files are produced in the output?
* - ._SUCCESS.crc 
* - part-m-00000.crc 
* - _SUCCESS 
* - part-m-00000
* (Q6) Explain this number based on the input file size and default block size.
* - The mapper task is run in the Filter program so there is only one output file: part-m-0000.
* - The part-m-0000 now contains 27972 records all with a response code of 200. 
* (Q7) How many files are produced in the output directory and how many lines are there in each file?
* - 2 files: 
* - _SUCCESS 
* - part-m-00000
* (Q8) Explain these numbers based on the number of reducers and number of response codes in the input file.
* -  The mapper task is run in the Filter program so there is only one output file: part-m-0000.
* - The part-m-0000 now contains 27972 records all with a response code of 200.
* (Q9) How many files are produced in the output directory and how many lines are there in each file?
* - 1) _SUCCESS
* - 2) part-r-00000
* - 3) part-r-00001
* - _SUCCESS and part-r-00001: no lines 
* - part-r-0000:
* - 200 481974462
* - 302 26005
* - 304 0
* - 404 0
* (Q10) Explain these numbers based on the number of reducers and number of response codes in the input file.
* - The records went to one of the reducers in the aggregation program and that why is part-r-00001 no lines or output
* - The output line shows the response code of the records and total sum of the bytes of each record corresponding to the same response code.
* (Q11) How many files are produced in the output directory and how many lines are there in each file?
* - 3 files:_SUCCESS, part-r-00000, and part-r-00001.
* - _SUCCESS has no lines or output
* - part-r-00000 5 lines:
* - 200 37585778
* - 302 3682049
* - 304 0
* - 404 0
* - 500 0
* - part-r-00001 - 2 lines:
* - 403 0
* - 501 0
* (Q12) Explain these numbers based on the number of reducers and number of response codes in the input file.
* - There are two reducers in the aggregation program so two outputs are created in the two files: part-r-00000 and part-r-00001
* - The output line shows the response code of the records and total sum of the bytes of each record corresponding to the same response code.