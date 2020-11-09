SpringBootApplication
=============================
This is a simple inventory management micro-service. 

This system is written using spring boot.

All data is accessed using REST API.

SERVICE FUNCTIONAL REQUIREMENTS
------------

User should be able to:

•	Enter new product (product details are listed below)

•	Find  product by name/brand

•	Update/remove product

•	Get all leftovers (leftovers means product which quantity is less than 5)


Product details:

•	Name

•	Brand

•	Price

•	Quantity



QUICK START
-----------

In your IDE: create project from version control.

Execute maven goal: mvn clean package

Run jar file: javac -jar target/SpringBootApplication-{version}.jar (or using IDE running config)

Open you browser at http://localhost:8080/
