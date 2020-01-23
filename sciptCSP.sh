#!/bin/bash
echo test CSP solver nb CC : 
read nb
java -jar target/phineloops-1.0-jar-with-dependencies.jar -g 32x32 -x $nb -o output.dat
java -jar target/phineloops-1.0-jar-with-dependencies.jar -s output.dat -o solved.dat
java -jar target/phineloops-1.0-jar-with-dependencies.jar -gui solved.dat
