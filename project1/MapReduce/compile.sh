if [ ! -f lombok.jar ]; then
    wget https://projectlombok.org/downloads/lombok.jar
fi
if [ ! -f ./opencsv-5.5.2.jar ]; then
    wget https://repo1.maven.org/maven2/com/opencsv/opencsv/5.5.2/opencsv-5.5.2.jar
fi

mkdir build
rm `dirname "$0"`/src/*Test.java
javac -cp `hadoop classpath`:./* `dirname "$0"`/src/*.java -d build
jar -cvf project.jar -C build/ .