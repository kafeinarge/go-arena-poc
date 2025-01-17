# Türkcell Go Arena POC

POC project of Türkcell's Go Arena.\
\
For the use of Go Arena Sales Management Team and dealer employees. With the Go Arena application, you can easily
monitor and manage your daily operational transactions.

### What you'll need

- [JDK 15](https://www.oracle.com/java/technologies/javase/jdk15-archive-downloads.html)
- [Maven](https://maven.apache.org)
- [Docker](https://hub.docker.com/search?q=&type=edition&offering=community&operating_system=linux)
- [Docker Compose](https://docs.docker.com/compose/install/)

# Architectural Design

![alt text](https://i.hizliresim.com/sI4BrE.jpg)

# How To Build and Run

## 1 - On A Docker Container

Just run the given deploy script  \
`./deploy.sh`\
It'll deploy all microservices and a postgresql server on containers and connect them on a single docker network.

## 2 - Standalone

Get a maven build on root pom\
`mvn clean install -DskipTests`  
Then run the microservices from Application classes as your main class.\
If you are not using any code editor, just execute this command in your terminal\
\
` nohup java -jar 'JAR_PATH' >/dev/null 2>&1 &`

# Ports

#### config-server is served on port `8888` as default.

#### eureka-server is served on port `8761` as default.

#### uaa-server is served on port `5000` as default.

#### api-gateway is served on port `8765` as default.

#### user-service is served on port `8766` as default.

#### dashboard-service is served on port `8767` as default.

#### wall-service is served on port `8768` as default.

# Documentation

This project is documented with OpenAPI 3.OpenAPI url has been redirected from `/swagger-ui.html` to  `/` to make ease
of use.\
For example, on your host machine, you can browse, \
\
http://localhost:8766/ for user service api documentation and \
http://localhost:8767/ for dashbord service api documentation etc.