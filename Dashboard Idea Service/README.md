## Boite a idées Novencia

## Dev environment Requirement:

- JDK for Java8
- git client
- Eclipse
- Gradle 3.2
- MongoDB 3.4


## Install:

Gradle https://gradle.org/gradle-download/

MongoDB Community Edition 3.4 https://docs.mongodb.com/manual/tutorial/install-mongodb-on-windows/

## Configure:

- Git clone import project into eclipse workspace
- Configure project in eclipse : gradle cleanEclipse eclipse
- Package application : gradle clean build
- Generate javadoc : gradle javadoc
- Run unit test : gradle test

## Data Initial:

Execute initial script "src/main/resources/MongoDB_Initial_Script" through MongoDB shell "MongoDB\Server\3.4\bin\mongo\mongo.exe"

## Execution environment:

start database : MongoDB\Server\3.4\bin\mongod.exe

start application : gradle bootRun

