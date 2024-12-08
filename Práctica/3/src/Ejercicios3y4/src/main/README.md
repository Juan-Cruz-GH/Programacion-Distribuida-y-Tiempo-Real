# Requerimientos

-   Tener instalado maven.
-   El puerto del servidor es el 50051.
-   Se requiere JDK versión 21.

# Comandos

## Cómo compilar el proyecto inicialmente

```sh
mvn clean compile
```

## Cómo ejecutar el servidor

```sh
mvn exec:java -D"exec.mainClass"="pdytr.ejercicio3.ChatServer"
```

## Cómo ejecutar el cliente

-   Los argumentos son: host 50051 usuario.
-   Por ejemplo: localhost 50051 Lucio

```sh
mvn exec:java -D"exec.mainClass"="pdytr.ejercicio3.ChatClient" -D"exec.args"="host 50051 usuario"
```
