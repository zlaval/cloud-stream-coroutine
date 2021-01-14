## How to use

---

### Prerequisits:

- Docker
- Java
- Gradle

The `containers` folder contains a docker compose file. It can be started with `docker-compose up` command and creates
the necessary environment.

Then you can start the application and send a message to it over the REST interface.

POST `http://localhost:8080/api/v1/produce-message`

The message format must be JSON.

```
{
    "name": "Bag",
    "price": 30
}
```

There are a Kafka and RabbitMq UI to watch the messages.

### Kafka

`http://localhost:8098/`

### RabbitMq

`http://localhost:15672/`

---

The compose file also contains a MongoDb image. You can connect to it wia command line or Compass on localhost at
port `27017`.

**RabbitMq and Mongo authentication data**

> username: admin
>
> password: admin





