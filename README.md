# FER Java Course 

Repository for the solutions of homework assignments for [Introduction to Java Programming Language][1] course by Marko Čupić at Faculty of Electrical Engineering and Computing Zagreb.

![fer-java-logo](https://i.imgur.com/Eex6wAi.png)

## Table of Contents
- [Course overview](#course-overview)
- [Getting started](#getting-started)

## Course overview
Few words about this course.

## Getting started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 

### Prerequisites
In order to build projects from this repository you need [Maven 3.5.3][2] and Java 9 or above.

### Building
Position yourself in desired directory and clone this repository:
```
git clone https://github.com/hmatic/fer-java-course
```
then position yourself into the project you want to build, for example:
```
cd "fer-java-course/Intro"
```
and build it with Maven:
```
mvn package
```
now you can run it with:
```
java -cp target/hw01-0036487400-1.0-SNAPSHOT.jar hr.fer.zemris.java.hw01.Factorial
```

[1]: https://www.fer.unizg.hr/en/course/itjpl
[2]: https://maven.apache.org/docs/3.5.3/release-notes.html