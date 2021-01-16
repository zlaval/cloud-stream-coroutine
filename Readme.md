## How to run

---

### Prerequisits:

- Docker
- Java
- Gradle

The `containers` folder contains a docker compose file. It can be started with `docker-compose up` command and creates
the necessary environment.

Then you can start the application and send messages to it via the REST interface.

POST `http://localhost:8080/api/v1/produce-message`

The format of the message body must be JSON.

```
{
    "name": "Bag",
    "price": 30
}
```

There are a Kafka and a RabbitMq UI in the compose to watch the messages.

---

### Kafka

`http://localhost:8098/`

### RabbitMq

`http://localhost:15672/`

---

The compose file also contains a MongoDb image. You can connect to it via command line or Compass.

`localhost:27017`

---

**RabbitMq and Mongo authentication data**

`username: admin`

`password: admin`





