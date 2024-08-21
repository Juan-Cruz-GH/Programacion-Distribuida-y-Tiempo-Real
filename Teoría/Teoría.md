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

<h1 align="center">Clase 2 - ? de ?, 2024</h1>

##

---

<h1 align="center">Clase 3 - ? de ?, 2024</h1>

##

---

<h1 align="center">Clase 4 - ? de ?, 2024</h1>

##

---
