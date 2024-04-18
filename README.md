POC: Open Cart Demo
Team Vega - Esteban Castro, José Ramírez, Santiago Rocha y Daniel Torres
 
This a POC project in the Open Cart web application.
The purpose of this project is to automate testing with Selenium and Java
 
In the test folder:
reports package
testdata package
tests package
Package for the created tests.
testsuites package
Run test suites for specific web functionalities
utilities package
Classes with extra functionalities for the test runs like taking screenshots.
In the main folder:
patterns package
Including page object model pattern to have selectors and methods for a respective web view
and singleton pattern for the Driver Manager class to create on instance for the web drivers that will be used (Firefox, Edge, Chrome)
