if $(test -f ./opencsv-5.5.2.jar); then wget https://repo1.maven.org/maven2/com/opencsv/opencsv/5.5.2/opencsv-5.5.2.jar; fi
sudo cp opencsv-5.5.2.jar /usr/lib/hadoop/lib/
rm -r output
hadoop fs -rm -r output
hadoop jar project.jar AvgSizeStations -libjars opencsv-5.5.2.jar data.csv output
hadoop fs -copyToLocal output

