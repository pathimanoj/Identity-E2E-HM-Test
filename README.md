Identity E2E Technical Test
This project will retrieve the vehicle registartion numbers from a text file, pass the values to a car tax check website where it retrives the information of that particular vehicle and compares with an existing output file.

Getting Started
Clone the project: git clone https://github.com/pathimanoj/Identity-E2E-HM-Test.git

Prerequisites
Need Java, Maven, IDE like IntelliJ or Eclipse

Running the tests
mvn compile
mvn clean install

Tests run in MAC OS using chrome browser version 83.0.4103.97.

Test can be run from command line using "mvn clean install" or can be run from the directly from the VehicleTaxCheckTest class.

This test will fail due to the mismatch of the data between input and output files.

Test will run for all 4 registration numbers and the output can be see clearly on console which clearly states, which test passed or failed.


Authors
Manoj Pathi

License
This project is licensed under the MIT License - see the LICENSE.md file for details

