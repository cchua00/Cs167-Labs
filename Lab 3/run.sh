#!/usr/bin/env bash

mvn clean package
hadoop_classpath=$(hadoop classpath)
java -cp target/cchua032_lab3-1.0-SNAPSHOT.jar:$hadoop_C:// edu.ucr.cs.cs167.aesti002.App AREAWATER.csv output.csv