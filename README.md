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

