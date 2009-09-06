JavaWars README
WWW: http://github.com/coriander/JavaWars


1. Working with JavaWars:

COMPILING
Currently the only known method to compile JavaWars is to install:
- Netbeans (tested with version 6.1) 
- ...with GWT plugin (GWT4nb) 

and use this IDE. All the files necessary for the Netbeans to recognise JavaWars as the Netbeans project are in the ./nbproject folder (do not delete).

BASIC CONFIGURATION
A. Password to the database should be set in the ./web/WEB-INF/rpc-servlet.xml file. 
B. In the same file there are various tomcat-specific paths especially in the serviceProviderTarget bean. They must be adjusted according to the server structure. 

DATABASE
The database type (postgre, mysql, other) is set in the configuration .xml (./web/WEB-INF/rpc-servlet.xml), it has been already configured to use MySQL.
After a successfull connection the Hibernate library should automatically create all the necessary tables.

APPLICATION SERVER
The project is suited for deployment on a Tomcat application container, version 6.
To publish the project copy the .war archive from the ./dist folder into the tomcat's webapps directory (should be automatically unpacked).


2. Project structure:

./trunk         - the most up-to-date stable build
    ./src       - sources
    ./web       - www data, general config files
    ./build     - compiled binaries
    ./dist      - .war package, ready for tomcat deployment
    ./nbproject - Netbeans project files

