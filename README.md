#### Description

Sample Configuration Service using Spring Boot.


#### Prerequisite and Installation

You have two choice for installation: either build the project and run it using Tomcat or run it using Docker.
One way to accomplish the former is to install an IDE like Eclipse with Tomcat pre-installed.

* [Java 1.8+](https://www.java.com/it/download/)
* [Eclipse](https://www.eclipse.org)
* [Maven](http://maven.apache.org)
* [Tomcat](http://tomcat.apache.org)

For the latter download and install Docker and the use the following instructions. This is the fastest way and also the one I recommend to get the project up and running in no time.

* [Docker](https://www.docker.com/)

#### Steps for running using Docker

* Checkout or download the project
* Use your terminal to go into the folder you downloaded the project.
* Build and start the container by running:

```
$ docker-compose up --build
```

##### Test application is running with command

```
$ curl localhost:8080/
```


##### Stop Docker Container:
```
docker-compose down
```

#### Example Usage

* POST localhost:8080/foo with body {"name": "Foo", "value": "val0"} to add a new configuration "foo"
* GET localhost:8080/foo to retrieve configuration "foo"
* PUT localhost:8080/foo with body {"name": "Foo", "value": "val1"} to update configuration "foo"
* GET localhost:8080/ to retrieve all the available configurations
* DELETE localhost:8080/foo to delete the configuration foo

#### Additional info on the project
The project has been entirely built using Java and Spring Boot. 
* Controllers are in charge of handling the REST requests. 
* Services handle the logic.
All the tests are in the test package.
Any new functionality added (see below section) should continue following this structure.
For the sake of this sample project the whole data structure is saved in-memory. If you need to persist your data for more then a single session I suggest attaching a DB and changing the ConfigurationService class to persist the configuration data, instead of simply saving it in memory.


#### How To Contribute
Feel free to add your contributions to the project. 
* Fork the project
* Create a new branch
* Add your contribution
* Push your branch
* Submit a pull request
