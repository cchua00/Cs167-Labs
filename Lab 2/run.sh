#!/usr/bin/env sh
mvn clean package
java -cp target/cchua032_lab2-1.0-SNAPSHOT.jar edu.ucr.cs.cs167.cchua032.App 10 25 2
java -cp target/cchua032_lab2-1.0-SNAPSHOT.jar edu.ucr.cs.cs167.cchua032.App 3 20 3,5
java -cp target/cchua032_lab2-1.0-SNAPSHOT.jar edu.ucr.cs.cs167.cchua032.App 3 20 3v5
