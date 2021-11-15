unzip zestaw3.zip
cp input/datasource1/NYPD_Motor_Vehicle_Collisions.csv data.csv
hadoop fs -copyFromLocal data.csv
bash MapReduce/compile.sh
cp MapReduce/project.jar .
bash runMapReduce.sh