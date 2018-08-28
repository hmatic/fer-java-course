# FER Java Course 

Repository for the solutions of homework assignments for [Introduction to Java Programming Language][1] course by Marko Čupić at Faculty of Electrical Engineering and Computing Zagreb.

## Table of Contents
- [Course overview](#course-overview)
- [Getting started](#getting-started)

## Course overview
While full readme file is in writing process I will write short summary about all homeworks we have done on this course.
Course started with basic object oriented stuff. One of more interesting homeworks in first part of course is parser that 
we built for our simple DSL called SmartScript. 
In 7th homework we created our shell console and improved it in 8th homework. 
It ended up as pretty decent console with around 20 commands.
Then we coped with parallelisation and graphic problems while rendering fractals and 3D spheres.
After that we learned how to use Swing and developed few simple GUI apps such as calculator and barchart viewer.
Finished Swing with fully functional JNotepad++.
Then we started web-oriented part of course by creating multithreaded HTTP server with support for sessions and cookies,
and with an embedded interpreter for evaluation of a our custom SmartScript DSL.
Continued web development with building voting app using servlets and DerbyDB.
Shortly after we learned how to use Hibernate and built multi-user blog app.
To repeat everything we have learned, we had to build simple text search engine 
and Paint-like application able to draw various vector shapes. It actually had many other features
including export to PNG, saving to our custom .jvd format, reordering of shapes, editing shapes and so on.
Near the end we shortly dived into frontend development and created single page image gallery using AJAX and jQuery.
Finished course with simple Android app as introduction into Android development.

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