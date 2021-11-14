hadoop fs -rm -r output
hadoop jar project.jar AvgSizeStations -libjars opencsv.jar data.csv output

