# LSMSDB Project - Click&Go

This repository contains the source code, data, and configuration files for the Click&Go Carpooling platform.

This project was developed as the final exam for the **Large Scale and Multi-Model Data Bases (LSMSDB)** course.

**Authors:**
* [Samuel Scarabelli](https://github.com/Snasmuel)
* [Francesco Rossi Paccani](https://github.com/FrancescoRossiPaccani)
* [Slim Jabou](https://github.com/jabjub)

## Repository Structure

* **lsmsdb-project/**: Contains the entire Java Spring Boot source code, including the `pom.xml` file.
* **docker_compose_vms/**: Contains three subdirectories. Each subdirectory holds the `docker-compose.yaml` file required to configure the specific environment for each of the 3 Virtual Machines (VMs).
* **DataSet/**: Contains the JSON, CSV, and .cypher files used to populate the databases.
* **loadDb.sh**: A Bash script used to populate the databases. **Note:** This script clears the databases completely before inserting the new data.

## System Requirements

The system is designed to run on a cluster of 3 Virtual Machines. The software requirements for each machine are:

* **Primary VM (Master):** Docker, Java, MongoDB, Redis, Neo4j.
* **Replica VMs (VM 2 & VM 3):** Docker, MongoDB, Redis.

## Build Instructions

To generate the executable JAR file, navigate to the project directory and run the following command. This will compile the code and skip the unit tests during the build process:

```bash
./mvnw clean package -DskipTests
```

## Database Initialization

To populate the system with the initial dataset, execute the loadDb.sh script.

* **Important**: This script performs a cleanup operation first. It deletes all existing data in the databases before loading the files found in the DataSet folder.

## Running the Application

Once the build is complete and the infrastructure is operational, transfer the generated JAR file to the **Primary VM**. Execute the application using the following command:

```bash
java -jar lsmsdb-project/target/lsmsdb-project-0.0.1-SNAPSHOT.jar
```

## Note on Deployment Testing
When the application starts via the deployment command above, an automatic end-to-end test script is executed. Unlike the initialization script, this test sequence does not clear the database, preserving the existing state.
