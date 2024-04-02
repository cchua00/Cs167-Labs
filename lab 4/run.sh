#!/usr/bin/env bash
mvn clean package
yarn jar cchua032_lab4-1.0-SNAPSHOT.jar edu.ucr.cs.cs167.cchua032.Filter nasa_19950801_cchua032.tsv filter_output_cchua032.tsv 200
yarn jar cchua032_lab4-1.0-SNAPSHOT.jar edu.ucr.cs.cs167.cchua032.Filter nasa_19950630.22-19950728.12_cchua032.tsv filter_output2_cchua032.tsv 200
yarn jar cchua032_lab4-1.0-SNAPSHOT.jar edu.ucr.cs.cs167.cchua032.Aggregation nasa_19950630.22-19950728.12_cchua032.tsv aggregation_large_output_cchua032.tsv
yarn jar cchua032_lab4-1.0-SNAPSHOT.jar edu.ucr.cs.cs167.cchua032.Filter nasa_19950630.22-19950728.12_cchua032.tsv filter_large_output_cchua032.tsv 200
yarn jar cchua032_lab4-1.0-SNAPSHOT.jar edu.ucr.cs.cs167.cchua032.Aggregation filter_large_output_cchua032.tsv aggregation_filter_large_output_cchua032.tsv