# Cómo usar vagrant

## Usuario y contraseña en nuestras VM

-   Usuario: vagrant
-   Contraseña: sudoer
-   Nombre VM1: vm1
-   Nombre VM2: vm2

## Instalar y configurar la VM

```sh
vagrant up
```

## Cambios al vagrantfile

Si cambiamos el vagrantfile, conviene hacer:

```sh
vagrant reload --provision
```

## Ejecutar la VM

```sh
vagrant ssh

vagrant ssh <boxname>
```

## Apagar la VM

```sh
vagrant halt
```

## Suspender la VM

```sh
vagrant suspend
```

## Destruir la VM

```sh
vagrant destroy
```

## [Más información](https://gist.github.com/wpscholar/a49594e2e2b918f4d0c4)
