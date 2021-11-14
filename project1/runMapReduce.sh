https://repo1.maven.org/maven2/com/opencsv/opencsv/5.5.2/opencsv-5.5.2.jar
hadoop fs -rm -r output
hadoop jar project.jar AvgSizeStations -libjars opencsv-5.5.2.jar data.csv output

