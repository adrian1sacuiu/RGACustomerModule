RGA Customer Module
===================

## Technologies used:

Programming language: Java;

Java/J2EE Technologies: Servlets, JSP, JavaBeans, REST services

Frameworks: Spring (DI, AOP, MVC, Security), Hibernate, JPA;

Web-Development: HTML 5, CSS, Javascript, JQuery, Bootstrap;

Server: Apache Tomcat 7;

IDE: Eclipse Kepler;

Building tool: Maven;

Testing: JUnit, Mockito, Spring Test;

Database: MySQL;

Agile Methodology: TDD;


##Controller Mappings (application root context: http://localhost:8080/RGACustomerModel/ ):

- "{[/customer/updateCustomer],methods=[POST],params=[],headers=[],consumes=[],produces=[application/json],custom=[]}" onto public java.util.Map<java.lang.String, java.lang.Object> com.rga.customer.controllers.CustomerController.updateCustomer(com.rga.customer.entities.Customer,javax.servlet.http.HttpServletResponse)

- "{[/customer/getAllCustomers],methods=[GET],params=[],headers=[],consumes=[],produces=[application/json],custom=[]}" onto public java.util.Map<java.lang.String, java.lang.Object> com.rga.customer.controllers.CustomerController.getAllCustomers(javax.servlet.http.HttpServletResponse)

- "{[/customer/deleteCustomer/{id}],methods=[DELETE],params=[],headers=[],consumes=[],produces=[application/json],custom=[]}" onto public java.util.Map<java.lang.String, java.lang.Object> com.rga.customer.controllers.CustomerController.deleteCustomer(java.lang.Long,javax.servlet.http.HttpServletResponse)

- "{[/customer/addCustomer],methods=[POST],params=[],headers=[],consumes=[],produces=[application/json],custom=[]}" onto public java.util.Map<java.lang.String, java.lang.Object> com.rga.customer.controllers.CustomerController.addCustomer(com.rga.customer.entities.Customer,javax.servlet.http.HttpServletResponse)

- "{[/customer/getCustomerById/{id}],methods=[GET],params=[],headers=[],consumes=[],produces=[application/json],custom=[]}" onto public java.util.Map<java.lang.String, java.lang.Object> com.rga.customer.controllers.CustomerController.getCustomerById(java.lang.Long,javax.servlet.http.HttpServletResponse)

- "{[/customer/addCustomer],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}" onto public org.springframework.web.servlet.ModelAndView com.rga.customer.controllers.CustomerController.addCustomerModel(javax.servlet.http.HttpServletResponse)

- "{[/customer/updateCustomer/{id}],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}" onto public org.springframework.web.servlet.ModelAndView com.rga.customer.controllers.CustomerController.updateCustomerModel(java.lang.Long,javax.servlet.http.HttpServletResponse)

- "{[/user/registerUser],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}" onto public org.springframework.web.servlet.ModelAndView com.rga.customer.controllers.UserController.addUserModel(javax.servlet.http.HttpServletResponse)

- "{[/user/registerUser],methods=[POST],params=[],headers=[],consumes=[],produces=[application/json],custom=[]}" onto public java.util.Map<java.lang.String, java.lang.Object> com.rga.customer.controllers.UserController.registerUser(com.rga.customer.entities.User,javax.servlet.http.HttpServletResponse)


##Configurations

There are two log4j.prop properties files: one for prod and one for test;
	- prod: 
		-log4j.prop file: src/main/resources/log4j.prop
		-log file:        /RGA_Customer_Module/logs/production/cp.log
	- test: 
		-log4j.prop file: src/test/resources/log4j.prop
		-log file:        /RGA_Customer_Module/logs/test/cp.log
		
Also, there are two data sources for prod and test database configured in the following files:
	- prod: 
		-database: jdbc:mysql://localhost:3306/Rga
		-config file:src/main/resources/spring-config.xml
	- test: 
		-database: jdbc:mysql://localhost:3306/TestRga
		-config file:src/test/resources/test-spring-config.xml


##Scenario
An insurance company needs a system to manage their customer data.  A software engineering team is working on the story below.

*As a system user, I want to add new customer data into the system. After added, I can view a certain customer, and a customer list. Of course, I am also able to modify the data as well as remove it from the system.* 


##Instructions
You are required to provide the functions of:

1. Creating a single customer
2. Updating a single customer
3. Deleting a single customer
4. Reading a single customer
5. Listing all customers
6. Authentication (assume that the system user is existing)
6-1. Login - getting a login token. 
6-2. Logout - destroying a login token.

Except for point 6, those functions(end-points) should be secured, which need a credential to access.

Please implement it in Java. Any frameworks are accepted.


##Acceptance criteria
1. RESTful APIs
2. JSON request and response
3. Evidence of a BDD/TDD approach
4. Clean, maintainable code adhering to SOLID principles


##What we look for
We are especially interested in how you structure your code. It should be fully testable and easy to be understood, extended and modified by others, and comply with the best object-oriented practices.

**Please push your source code to GitHub, Bitbucket or other source control platforms, or just zip it and send it back to me.**

Good luck!
