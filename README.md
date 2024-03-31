regression-api-automation-cba is a maven project

#Scripting Language: 
Java(Rest Assured libraries to test Rest APIs)

#Framework: 
Cucumber Framework

#Run below command to run all tests
mvn clean verify "-Dcucumber.filter.tags=@reg"

Replace the <tag> with below tags to run respective tests
mvn clean verify "-Dcucumber.filter.tags=@<tag>"
@inventory 
@placeOrder
@purchaseOrder
@deleteOrder

#Report
Generates HTML report and Json report.
cucumber report:
target/cucumber-html-reports
json Report:
target/jsonReports



