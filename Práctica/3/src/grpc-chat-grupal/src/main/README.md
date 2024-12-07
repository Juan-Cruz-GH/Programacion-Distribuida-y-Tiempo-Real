# Comandos

## Cómo ejecutar el servidor

```sh
mvn package exec:java -D"exec.mainClass"="pdytr.ejercicio3.ChatServer"
```

## Cómo ejecutar el cliente

```sh
mvn exec:java -D"exec.mainClass"="pdytr.ejercicio3.ChatClient" -D"exec.args"="localhost 50051 Lucio"
```

## Comandos extra que no sé qué son

```sh
mvn package exec:java@run-server
```

```sh
mvn exec:java@run-client
```
