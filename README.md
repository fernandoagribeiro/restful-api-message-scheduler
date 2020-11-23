# RESTful API to create Scheduled Messages
This repository contains the Java source code for a Restful API that has endpoints to create, list, and delete messages,
where these messages should be sent by a different application when their scheduled date arrives.

## Maven
This project uses Maven in order to manage its packages and plugins.

## Lombok
This project uses Lombok as a way to decrease the need for boiler plate code.
It's necessary to install the Lombok plugin on your IDE if you want to run this project.
- Click [here](https://projectlombok.org/setup/eclipse) to acquire Lombok for Eclipse
- Click [here](https://projectlombok.org/setup/intellij) to acquire Lombok for IntelliJ IDEA
- Click [here](https://projectlombok.org/setup/netbeans) to acquire Lombok for Netbeans

## Docker
As this application was designed to operate with Postgres databases, in order to avoid the need to have an installation
of Postgres, it's possible to run a docker-compose command that will run an image of a Postgres DB.
To do that, first ensure that you have Docker installed and running on your machine.
 You can get Docker [here](https://docs.docker.com/get-docker/).

Navigate to the folder that contains the docker-compose file:

```
cd ./src/main/resources
```

Then, run the command below:

```
docker-compose up -d
```

## Run and Test

Now, to run the application execute the following code on your terminal:

```
mvn spring-boot:run
```

By default, the website will be hosted on http://localhost:8080.

To execute unit tests:

```
mvn test
```

## Jacoco

Unfortunately, I've created only a few Unit Tests as I had tons of trouble to make Maven recognize my UTs.
In order to see how much my code is covered by Unit Testing (much less than I'd like), you can run the following command:

```
mvn jacoco:report
```

Then, navigate to the folder *./target/site/jacoco* and open the **index.html** file in your browser.

## Endpoints

### [POST] Add a message

```
/messagescheduler/v1/messages/create [POST]

Content-Type: application/json

{
    "messageContent": "Message Content 1",
    "messageTypeName": "Email",
    "recipient": "ferna***ag****@gmail.com",
    "dateToSend": "2020-11-23T15:00"
}
```

Upon success, code 200 is returned with the following response:

```
{
    "message": "Scheduled Message successfully saved",
    "objToReturn": {
        "id": 4,
        "messageContent": "messageContent",
        "messageTypeName": "Email",
        "recipient": "ferna***ag****@gmail",
        "dateToSend": "2020-11-23T15:00:00",
        "sent": false
    }
}
```

### [GET] Get a message

```
/messagescheduler/v1/messages/find/{id} [GET]
```

Gets a message with the id that was provided in the requested URL. The response is as follows:

```
{
    "message": "Found",
    "objToReturn": {
        "id": 1,
        "messageContent": "Message Test 1",
        "messageTypeName": "Email",
        "recipient": "ferna***ag****@gmail",
        "dateToSend": "2020-11-22T17:20:00",
        "sent": false
    }
}
```

### [DELETE] Delete a message

```
messagescheduler/v1/messages/delete/{id} [DELETE]
```

Deletes a message with the id that was provided in the requested URL. The response is as follows:

```
{
    "message": "The Scheduled Message was successfully deleted",
    "objToReturn": null
}
```