#!/bin/bash
echo test graphics solver nb CC of the first grid :
read nb1
echo test graphics slover nb CC of the second grid :
read nb2
java -jar target/phineloops-1.0-jar-with-dependencies.jar -g 10x12 -x $nb1 -o output1.dat 
java -jar target/phineloops-1.0-jar-with-dependencies.jar -g 8x8 -x $nb2 -o output2.dat
java -jar target/phineloops-1.0-jar-with-dependencies.jar -gui output1.dat
java -jar target/phineloops-1.0-jar-with-dependencies.jar -gui output2.dat
