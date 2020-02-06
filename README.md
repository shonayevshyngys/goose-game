# goose-game


## prerequisites
JDK 11

Postgresql 10 (or docker image of postgres)

Maven

Free port 1234 and 7000


So docker command:

## docker run --name goose  -p 1234:5432  -it -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=goosegame postgres:10

This command will create docker image mapped to port 1234 with readt database and user. For this version clone the docker branch

## Manually:
Create database named goosegame and manually change the hibernate.cfg according to your postgres. 

 Running the application

 mvn clean

 mvn clean compile assembly:single

 cd target

 java -jar [JAR file with dependencies]
