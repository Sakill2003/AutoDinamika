# AutoDinamika
This repository contents the project of the Software Engineering Seminar course. 
Frontend developed with HTML, CSS and JavaScript vanilla.
Backend developed in Springboot (Java).

INSTRUCTIONS:

To create a database on PostgreSQL 15.13:
- For Server[localhost]: Press Enter
- For Database[postgres]: Press Enter
- For Port[5432]: Press Enter
- For Username[postgres]: postgres
- For Contraseña para usuario postgres: The one provided when installing PostgresSQL Shell

Then, to create the database:

CREATE DATABASE AutoDinamika;
\c autodinamika   (To enter database)
\i (Path)/AutoDinamika/SQL/script.sql


To run test you must have:
- Apache Maven 3.9.11-bin (If you don't, you must install it and add it to Path on System Enviroment Variable)

How to run tests:
- There are two different ways to run tests, one is using command 'mvn test' on root folder (Backend/api). This command runs all tests.
  There is a consideration to be aware of, sometimes Visual Studio Code doesn't detect any response from tests on the UI, to avoid this, we build the test with some console print messages, so, executing 'mvn test' should show the result messages on the terminal.