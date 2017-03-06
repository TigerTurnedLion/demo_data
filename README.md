# demo_data
Program to generate demo data for align care.

<b>Step 1</b>:

Download Java code from github @ https://github.com/TigerTurnedLion/demo_data

This repo contains a java program and SQL scripts needed to produce the demo data for align care.

<b>Step 2</b>:

Setup an instance of Postgres 9.5 or greater and run the SQL script located @
 “<APP_PATH>/demo_data/src/main/resources/seed_data/install.sql” in it.

This will create the align schema and tables needed to generate demo data.

<b>Step 3</b>:

Insert align care data into the following tables:
align.members
align.providers
align.claims

The schema of these tables follows the original files that were provided to create demo_data.  They can be viewed in the “Install.sql” script reference in Step

<b>Step 4</b>:

Change the Postgres connection string @ processControl.initConnection() method.

Note: The connection string within the code is to localhost with the postgres default port: 5432 with no user name or password.  If postgres is install on the same server as the running application, this connection string can be used as is.

<b>Step 5</b>:

Run the application by calling the main() method located in class: “DemoDataApplication"
