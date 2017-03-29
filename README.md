# Setup instructions for Aligncare Demo Data application
<b>Connect to Docker CLI</b><p>
On the Mac, you can use the Kitematic UI for this and click “Docker CLI”
Or, you can launch it via the terminal.

Here is what my command looks like.  (You must adjust it to match your local system):<br>
johnkelly$ bash -c "clear && DOCKER_HOST=tcp://192.168.99.100:2376 DOCKER_CERT_PATH=/Users/johnkelly/.docker/machine/machines/default DOCKER_TLS_VERIFY=1 /bin/bash”

<b>Download docker image from Docker Hub</b><p>
You can download the docker image from the following location @ <a href="https://hub.docker.com/r/tigerturnedlion/aligncare-demo-data/">https://hub.docker.com/r/tigerturnedlion/aligncare-demo-data/</a>

Here is the command to pull the image:<br>
docker pull tigerturnedlion/aligncare-demo-data

<b>Check image</b><p>
Once downloaded, run the following command to ensure that the image is available:<br>
docker images

You should see the following:<br>
REPOSITORY     							TAG                IMAGE ID              CREATED            SIZE<br>
tigerturnedlion/aligncare-demo-data     latest              ff8a9bc2564e        ? days ago          1.29 GB<br>

<b>Launch container</b><p>
Now, you can shell into the container via the following command:<br>
docker run -it tigerturnedlion/aligncare-demo-data

<b>Set up mount of local drives - Windows vs Linux</b><p>
In order to load data into postgres, the members, providers, claims files must be accessible via the containers file structure.  You can mount a local drive into the container for this purpose.  Here are the details for mounting a drive for Windows and/or Mac @ <a href="https://docs.docker.com/engine/tutorials/dockervolumes/#adding-a-data-volume">https://docs.docker.com/engine/tutorials/dockervolumes/#adding-a-data-volume</a>

<b>Alternative CURL</b><p>
Alternatively, you can download the files into the container using CURL -O flag command if they are hosted via ftp or http.<p>

<b>Launch Postgres</b><p>
With a shell into the container loaded, you should connect to postgres via the postgres CLI.<p>

To do this, run the following on the command line:<br>
psql postgres root<p>

You should see the following:<br>
psql (9.2.15)<br>
Type "help" for help.<p>

postgres=#<p>

This is your sql prompt.  Check that the schema and tables are available:<br>
postgres=# \dt align.*<p>

You should see the following:<br>
             List of relations<br>
Schema |      Name      | Type  |  Owner<br>
--------+----------------+-------+----------<br>
align  | claims                 | table | postgres<br>
align  | firstnames           | table | postgres<br>
align  | hash_claims        | table | postgres<br>
align  | hash_members   | table | postgres<br>
align  | hash_providers   | table | postgres<br>
align  | lastnames           | table | postgres<br>
align  | members            | table | postgres<br>
align  | providers            | table | postgres<br>
(8 rows)<br>

<b>Truncate tables</b><p>

Truncate the following 6 tables before each run:<p>

postgres=# TRUNCATE align.claims;<br>
postgres=# TRUNCATE align.hash_claims;<br>
postgres=# TRUNCATE align.hash_members;<br>
postgres=# TRUNCATE align.hash_providers;<br>
postgres=# TRUNCATE align.members;<br>
postgres=# TRUNCATE align.providers;<p>

DO NOT truncate the align.firstnames or align.lastnames tables.  If this happens, the image will need to be reloaded and setup again.<p>

<b>Upload files</b><p>

From your mounted drive, you can upload the members, providers, and claims files via the COPY command.
Examples:

postgres=# COPY align.members FROM '/path/to/csv/MEMBER_FILE' DELIMITER ',' CSV;
postgres=# COPY align.providers FROM '/path/to/csv/PROVIDER_FILE' DELIMITER ',' CSV;
postgres=# COPY align.claims FROM '/path/to/csv/CLAIM_FILE' DELIMITER ',' CSV;

Note: “/path/to/csv/“ is the mounted drive.  And, MEMBER_FILE, PROVIDER_FILE, and CLAIM_FILE are replaced with the actual file names.

Also, the fields in the file HEADER i.e. columns must match the fields of the corresponding table.  You can view each table's columns with the following commands:

postgres=# \dS align.members;
postgres=# \dS align.providers;
postgres=# \dS align.claims;

Note: you do not need to include the “id” columns in your upload csv files.  These columns are “SERIAL” which is equivalent to a SQL Server “IDENTITY” column and will be auto-populated for you; just ignore it.

<b>Run Java program</b><p>

Exit the SQL prompt via the following hot-key combination:
Control + D

Navigate to the JAR directory in the container @<br>
/# cd /demo_data/target

Now, run the Java app from the command line.<br>
/# java -jar demo-0.0.1-SNAPSHOT.jar

You will see scrolling print-outs that indicate Spring Boot is running and align care data is processing

<b>Export data</b><p>
Once complete, the Demo Data will be loaded into the following tables:

- align.hash_members
- align.hash_providers
- align.hash_claims

You can output the results using the COPY command again

Examples:

Launch the SQL prompt:
psql postgres root

Then extract the data from the HASH tables
postgres=# COPY align.hash_members TO ‘/MOUNT_DRIVE/OUTPUT_MEMBER_FILE.csv' DELIMITER ',' CSV;
postgres=# COPY align.hash_providers TO ‘/MOUNT_DRIVE/OUTPUT_PROVIDER_FILE.csv' DELIMITER ',' CSV;
postgres=# COPY align.hash_claims TO ‘/MOUNT_DRIVE/OUTPUT_CLAIM_FILE.csv' DELIMITER ',' CSV;

If you want the first row to contain column headers, include the HEADER switch e.g.
postgres=# COPY align.hash_members TO ‘/MOUNT_DRIVE/OUTPUT_MEMBER_FILE.csv' DELIMITER ',’ CSV HEADER;

See this Stackoverflow for details @ <a href="http://stackoverflow.com/questions/1120109/export-postgres-table-to-csv-file-with-headings">http://stackoverflow.com/questions/1120109/export-postgres-table-to-csv-file-with-headings</a>

<b>Postscript on Postgres</b><p>
All the SQL prompt and Java commands can be combined into script for automation.  The details will be specific to your environment.

Please let me know if you have any questions or run into issues, I am available to to work through it.