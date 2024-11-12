![](/src/main/resources/static/favicon.ico)

# cherrykeep

> Keep your thoughts. Or forget them.

## Running development environment

### Environment variables

| Variable | Description/Notes |
|----------|-------------------|

### Installing dependencies

```sh
npm i
```

### Spin up the database and Spring Boot development server

 ```sh
docker compose up -d
./gradlew bootRun --args='--spring.profiles.active=dev'
```

### Start the Webpack development server proxy

npm run devserver

## Production deployment


