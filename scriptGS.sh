#!/bin/bash
echo test graphics solver nb CC : 
read nb
java -jar target/phineloops-1.0-jar-with-dependencies.jar -g 4x4 -x $nb -o output.dat
java -jar target/phineloops-1.0-jar-with-dependencies.jar -GS output.dat 
