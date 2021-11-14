wget https://projectlombok.org/downloads/lombok.jar
mkdir build
rm src/*Test.java
javac -cp `hadoop classpath`:lombok.jar src/*.java -d build
jar -cvf project.jar -C build/ .
