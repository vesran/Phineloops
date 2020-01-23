#!/bin/bash
echo test playing game.
java -jar target/phineloops-1.0-jar-with-dependencies.jar -g 2x2 -x 1 -o output.dat
java -jar target/phineloops-1.0-jar-with-dependencies.jar -G output.dat
