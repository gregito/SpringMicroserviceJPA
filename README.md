Prerequisites:

For building and packaging
> [Maven](https://maven.apache.org/)

For containerized run (beside Maven):
> [Docker](https://docs.docker.com/install/)

To run in docker container run the following commands from the project's root folder:

Build the project into a jar file
```
mvn clean build
```

Build the image:
```
docker build -t to-do-service .
```

Run the image with the necessary exposed port:
```
docker run -d -p 8383:8383 to-do-service
```
