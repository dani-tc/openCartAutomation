# POC: Open Cart Demo

## Team Vega 
- Esteban Castro
- José Ramírez
- Santiago Rocha
- Daniel Torres

This is a Proof of Concept (POC) project in the Open Cart web application. The purpose of this project is to automate testing with Selenium and Java.

## Project Structure

### Test Folder
- **reports package** 
- **testdata package**
- **tests package** : Package for the created tests.
- **testsuites package** : Run test suites for specific web functionalities.
- **utilities package** : Classes with extra functionalities for the test runs like taking screenshots.

### Main Folder
- **patterns package** : Includes page object model pattern to have selectors and methods for a respective web view and singleton pattern for the Driver Manager class to create one instance for the web drivers that will be used (Firefox, Edge, Chrome)

### Set up steps
Steps for cloning and executing the project. Before trying to clone this repository, you need to have [Git](https://git-scm.com/) installed on your computer and [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

- **Copy the https clone link** <br> ![go above](https://media.geeksforgeeks.org/wp-content/uploads/20221126085739/clone.png)
- **Create a folder on your computer**
- **Open a terminal or CLI in that folder, execute following command**
- **git clone https://github.com/dani-tc/openCartAutomation.git**
- **Let’s suppose you will open this project on [IntelliJ Community Edition](https://www.jetbrains.com/idea/download). Although, you can use other IDE’s or code editors like [VS Code](https://code.visualstudio.com/)**
- **Open the project when you see the archive pom.xml on file explorer**
- **Go to this location inside project: /src/test/java/testsuites/**
- **Right click on any of them, and then click on run**
