# PhineLoops

This java project is an assignment for the Java Avancé course at University Paris-Dauphine. It implements the InfinityLoop game with multiple functionalities (intelligent solver, GUI, visualizing solver working, etc.)
The puzzle game shows a grid of tiles that can be connected to each others by a simple rotation. The aim is to find a correct orientation for each piece to connect them all.

## Getting Started

### Functionalities
* Generating random levels with customizable dimensions and connex composants
* Checking if the level is impossible to solve or not
* Solving levels with multiple threads
* Displaying GUI and playing the game
* Showing solver searching for a solution
* Multiple solvers implemented

### How to run the project
This project is based on JavaFX and works with Java 8 or a more recent version. It can be compiled with Maven. You can compile and generate a jar of the whole project with the following commands :
```
mvn package
java -jar target/phineloops-1.0-jar-with-dependencies.jar
```

 * To generate a level and save it in a specified ```file``` with size ```h```x```w``` and ```c``` connex composants:
```
java -jar projet.jar --generate hxw--output file[ --nbcc c]
```


 * To check a level stored in the specified ```file``` :
```
java -jar projet.jar --check file
```


 * To solve a level using ```t``` threads stored in the specified ```file``` and save it in ```filesolved``` :
```
java -jar projet.jar --solve file--output filesolved [ --threads t]
```


 * To  a level display a level stored in the specified ```file``` and play (dimensions must be <= 32x32, otherwise it is unreadable):
```
java -jar projet.jar --G file
```


 * To show an exhaustive solver solving a level stored in the specified ```file``` :
```
java -jar projet.jar --GS file
```

### Prerequisites

This project needs the following dependencies (or later versions) :
* JUnit 4.0
* commons-cli 1.4
* org.openjfx 13
* org.choco-solver 4.10.2

See ```pom.xml``` file.


## Running the tests

Tests needs JUnit 4.0 to be run.


## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Authors

* **Karim Amrouche** - [karimamrouche](https://github.com/karimamrouche)
* **Bilal Khaldi** - [BilalKHA95](https://github.com/BilalKHA95)
* **Yves Tran** - [vesran](https://github.com/vesran)

