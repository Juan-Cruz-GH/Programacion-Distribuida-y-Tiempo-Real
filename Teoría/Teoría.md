<h1 align="center">Clase 1 - 21 de agosto, 2024</h1>

## Definiciones y evolución

### Definición de Sistema Distribuido

-   Según Tanenbaum, un Sistema Distribuido es una colección de computadoras independientes que se muestra a los usuarios como un sistema único.
-   Según Coulouris, hay distintas partes del sistema en distintas computadoras y éstas están conectadas entre sí vía una red y se comunican vía mensajes.
-   Según Liu, un Sistema Distribuido es una colección de computadoras independientes interconectadas con una red, capaz de colaborar para llevar a cabo una tarea.

### Evolución de los Sistemas Distribuidos

#### Introducción

-   Inicialmente, las PCs eran autónomas, independientes.
-   Cuando en las oficinas empezaron a haber muchas PCs, empezó a aparecer la necesidad de compartir hardware.
-   Por ejemplo, no tiene sentido tener 5 impresoras. Puede haber una sola a la cual todas las PCs tengan conexión. Esto simplifica muchísimo el mantenimiento: es mucho más fácil mantener/reparar/cuidar una sola impresora que 20 impresoras distintas.
-   Otro ejemplo es el almacenamiento. Se descubrió rápidamente que es mucho más eficiente tener almacenamiento compartido, por ejemplo un disco rígido con gran capacidad en el cual muchas PCs leen y escriben, en vez de cada PC un disco rígido pequeño. Esto simplifica muchísimo los backups: en vez de hacer un backup por cada disco de cada computadora, se hace uno solo.
-   Otro ejemplo son los datos, los archivos. Que las computadoras puedan transmitir archivos entre ellas.

#### Redes locales

-   Todo esto dio lugar a la creación del modelo Cliente/Servidor y la creación de los protocolos de red local.

#### Redes globales

-   Eventualmente, se necesitó comunicar PCs en redes distintas y esto dio lugar a la creación de los protocolos de Internet (IPs, ports, APIs, sockets, telnet, ftp, email, etc).
-   El protocolo central de las aplicaciones sobre IP (Internet) resultó ser HTTP.
-   Esto llevó a una explosión enorme en cantidad de computadoras y cantidad de conexiones entre ellas a nivel mundial.
-   Todo esto impulsó la creación de las Aplicaciones Web, cuya funcionalidad aumentó drásticamente a la vez que la potencia de los Web Browsers fue en crecida.

#### Aplicaciones Web

-   Proveen muchas ventajas:
    1. Ubicuidad (mantenimiento, soporte técnico, usuarios): lo globales y accesibles que son los web browsers hace que las aplicaciones web también lo sean.
    2. Distribución de Software: El Web Browser resuelve por sí solo toda la interfaz gráfica de la aplicación. Además, como la aplicación se encuentra alojada en el servidor y los usuarios todos acceden al mismo servidor vía la URL, no es necesario distribuir el programa ni que los usuarios descarguen o instalen nada.
    3. Explotación de la capacidad de los navegadores: Los browsers hoy en día son extremadamente poderosos, ofreciendo Plug-Ins, JavaScript. Pasaron de ser interpretes tontos a ser quasi-máquinas virtuales.

#### Cloud Computing

-   Relacionado a las Apps Web está el Cloud Computing, que es la metodología que provee los servicios de hardware masivos para apps web que reciben millones de peticiones por hora.

#### Estadísticas

-   Coulouris y Liu muestran estadísticas que representan el crecimiento exponencial que ha habido desde 1990 hasta hoy no solo de las computadoras personales, si no también de los servidores web.

## Características

Los sistemas distribuidos tienen varias características principales:

1. **No tienen estado global**: no se puede conocer el estado del hardware y el software en su totalidad en cualquier momento. Solo se puede conocer esto en un período de tiempo relativamente corto pero no de todo el sistema. Por ejemplo, no hay una memoria global. Cada parte del sistema distribuido tiene su propia memoria que es manejada por SU sistema operativo.
2. **Concurrencia y recursos compartidos**: si bien la concurrencia no es propia de los sistemas distribuidos, éstos se relacionan estrechamente con este concepto. El objetivo principal de los sistemas distribuidos ES la concurrencia y la habilidad de compartir recursos.
3. **Escalabilidad**: todos los sistemas distribuidos deberían tener como objetivo ser escalables. Que sea escalable significa que puede crecer significativamente en forma eficiente.
4. **Seguridad**: debido a que los sistemas distribuidos involucran muchas computadoras interconectadas, se vuelve más complejo/difícil asegurar la seguridad y falta de vulnerabilidades en todo el sistema (algunos S.D pueden sufrir de Single Point of Failure).
5. **Apertura-Extensibilidad**: implica hasta qué punto un S.D puede extender su funcionalidad y que ésto no dependa solo del dueño del S.D.
6. **Transparencia**: qué se puede ocultar y qué se debe mostrar (abstracción). Colouris define muchos tipos de transparencia:
    1. **Transparencia de acceso**: los recursos, tanto locales como remotos, se acceden vía la misma operación.
    2. **Transparencia de locación**: los recursos se pueden acceder sin necesitar saber su locación física o de red.
    3. **Transparencia de concurrencia**: permite que procesos se ejecuten concurrentemente sin interferencia.
    4. **Transparencia de replicación**: permite que varias instancias de un mismo recurso puedan usarse de forma transparente a los usuarios.
    5. **Transparencia de fallo**: permite que los programas sigan su curso y puedan terminar su ejecución a pesar de que ocurrieron fallos, estos fallos son invisibles para el usuario.
    6. **Transparencia de movilidad**: permite que los recursos se muevan libremente en el sistema sin que ésto afecte las operaciones de los usuarios o programas.
    7. **Transparencia de performance**: permite que el sistema pueda ser reconfigurado.
    8. **Transparencia de escalabilidad**: permite que el sistema y sus aplicaciones puedan extenderse en escala sin modificar la estructura del sistema ni los algoritmos de las aplicaciones.

---

<h1 align="center">Clase 2 - 2 de septiembre, 2024</h1>

## Procesamiento Cliente Servidor

### Introducción

-   Abreviaremos Cliente Servidor con C/S.
-   La idea del modelo de procesamiento Cliente Servidor nace de la realidad de tener procesos que necesitan recursos que no tienen, y otros procesos que sí tienen estos recursos y se los pueden compartir. Por ejemplo: las aplicaciones de usuario como cliente, y el sistema operativo como servidor.
-   Existen muchas variantes del modelo C/S debido a la amplia gama de necesidades y aplicaciones que existe en los sistemas.

### Esquema temporal de procesamiento para el cliente

![Esquema temporal de procesamiento para el cliente](https://i.imgur.com/AF1ofwT.png)

1. El cliente ejecuta sus sentencias propias hasta que en algún momento necesita algo que no tiene.
2. En ese momento, realiza un requerimiento al servidor.
3. Hasta que el servidor no le responda, el cliente no puede seguir.
4. El servidor le responde con el resultado y ahora el cliente puede seguir su ejecución.

### Esquema temporal de procesamiento para el servidor

![Esquema temporal de procesamiento para el servidor](https://i.imgur.com/ZPvc6UR.png)

1. El servidor "no hace nada" hasta que recibe un requerimiento.
2. Al recibirlo, lo resuelve.
3. Al terminar de resolverlo, le envía el resultado al cliente.
4. Luego de enviar el resultado al cliente, vuelve al estado de "no hacer nada" donde puede aceptar nuevos requerimientos.

### Overhead de comunicaciones (envío y recepción)

![Overhead de comunicaciones (envío y recepción)](https://i.imgur.com/nUSAfgC.png)

-   El requerimiento del cliente al servidor y la respuesta del servidor al cliente no son instantáneos, llevan un tiempo (overhead) ya que se deben transmitir estos mensajes por la red.

### Características

1. El modelo posee 2 tipos de procesos que interactúan, clientes y servidores. Puede haber una cantidad indeterminada de ambos.
2. Los clientes son **activos**, ya que son ellos los que inician los requerimientos.
3. Los servidores son **pasivos**, ya que no saben cuándo llegará una petición nueva.
4. Los clientes son los que usan o necesitan recursos que le piden al servidor.
5. Los servidores poseen y/o administran los recursos, pero no los usan.
6. Los clientes no saben nada del servidor más allá de cómo pedirles algo (abstracción).
7. Los servidores conocen el estado de todos los recursos.
8. Ni los clientes ni los servidores se ocupan de la transferencia de información entre ellos (overhead de comunicación).
9. Los clientes y los servidores tienen una interfaz bien definida:
    1. Cómo pedir un servicio y qué datos necesita el servidor que le envíen.
    2. Cómo se retorna la respuesta al cliente.

---

<h1 align="center">Clase 3 - ? de ?, 2024</h1>

##

---

<h1 align="center">Clase 4 - ? de ?, 2024</h1>

##

---
