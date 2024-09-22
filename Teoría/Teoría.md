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

<h1 align="center">Clase 3 - 17 de septiembre, 2024</h1>

## Sistemas Distribuidos vs. Sistemas Centralizados

### Ventajas de los Sistemas Distribuidos

1. **Costo/rendimiento**: resulta mucho más barato comprar muchas computadoras medianamente o poco potentes que comprar una sola computadora muy potente, y a fin de cuentas la performance es similar si no incluso mejor.
2. **Distribución inherente del problema**: si el problema o las tareas que se están resolviendo pueden dividirse o distribuirse fácilmente, un sistema distribuido permite que diferentes componentes trabajen de forma independiente y simultánea, lo cual mejora el rendimiento.
3. **Crecimiento incremental del hardware**: los sistemas distribuidos permiten agregar más hardware de manera gradual a medida que crecen las necesidades. Esto se puede hacer sin afectar la estructura general del sistema, algo que no es tan sencillo en los sistemas centralizados, donde el hardware es más rígido.
4. **Recursos compartidos**: en los sistemas distribuidos, varios usuarios o aplicaciones pueden acceder y compartir los mismos recursos a través de la red, lo cual aumenta la eficiencia. En un sistema centralizado, los recursos suelen estar más aislados y no es tan sencillo compartirlos entre sistemas independientes.

### Desventajas de los Sistemas Distribuidos

1. **Software**: los sistemas distribuidos requieren un software más complejo para coordinar los diferentes componentes y garantizar que todos funcionen juntos de manera eficiente y sin inconvenientes. Esto presenta un problema porque el software en general e históricamente siempre fue pensado primero y principalmente para sistemas centralizados.
2. **Seguridad**: la seguridad puede ser más difícil de gestionar en sistemas distribuidos porque hay más puntos de entrada o vulnerabilidades posibles debido a la gran cantidad de componentes y la dispersión de éstos. Un sistema centralizado tiene menos puntos de acceso, ergo puede ser más fácil de proteger.
3. **Características de las redes actuales**: los sistemas distribuidos dependen profundamente de la red que los conecta. Las redes pueden sufrir fallas, tener diferencias en la velocidad de transmisión o problemas de latencia, lo cual puede afectar negativamente el rendimiento del sistema en su totalidad.
4. **Administración**: administrar un sistema distribuido es más complicado porque implica coordinar múltiples sistemas en lugar de uno solo. Se requiere una gestión más compleja para asegurarse de que todas las partes del sistema funcionen correctamente, estén actualizadas y seguras.

## Tipos de Sistemas Distribuidos

### Distributed Computing Systems

Estos sistemas están diseñados para permitir que múltiples computadoras trabajen juntas en una tarea compartida. El objetivo es mejorar el poder de cómputo y resolver tareas complejas descomponiéndolas en partes que resuelven en paralelo las distintas máquinas.

Ejemplos: Clusters, Grid Computing, Cloud Computing.

### Distributed Information Systems

Estos sistemas se encargan de compartir y distribuir información en muchas máquinas. El énfasis está en asegurar que los datos y servicios pueden ser accedidos y administrados de forma remota.

Ejemplos: Bases de Datos Distribuidas, CDNs, Email.

### Distributed Pervasive Systems

Estos sistemas buscan integrar a la computación en los ambientes cotidianos de forma transparente, para que los usuarios interactúen con los sistemas de cómputo de forma natural.

Ejemplo: Dispositivos y sistemas "smart" que utilizan el Internet of Things, donde los recursos de hardware están embebidos en dispositivos que se comunican entre sí.

---

<h1 align="center">Clase 4 - 19 de septiembre, 2024</h1>

## Tiempo Real

### Introducción

Existen dos usos de la frase "tiempo real":

1. Uno comercial.
2. Otro técnico.

### Aspectos comerciales

Según [getstream.io](https://getstream.io/glossary/real-time-app/), una aplicación de tiempo real está diseñada para funcionar dentro de un plazo de tiempo que es efectivamente instantáneo desde el punto de vista del usuario. La respuesta ocurre tan rápidamente que cualquier interacción **parece** estar ocurriendo en tiempo real. Algunas características de aplicaciones de tiempo real son:

1. Baja latencia.
2. Interacción sincrónica.
3. Mecanismo de "push".

### Definiciones técnicas

Según Butazzo, los sistemas de tiempo real son aquellos que deben reaccionar con restricciones de tiempo precisas a eventos en el ambiente. Como consecuencia, la conducta correcta de estos sistemas depende no solo de la computación si no también del instante de tiempo en el cual se produce el resultado.

Según Burns, la principal característica que distingue la computación en tiempo real de otros tipos de computación es el **tiempo**. Consideremos el significado de las palabras **_tiempo_** y **_real_**:

-   La palabra **_tiempo_** significa que la correctitud del sistema depende no solo del resultado lógico sino también del **momento en que el resultado es producido**.
-   La palabra **_real_** indica que la reacción del sistema a eventos externos debe ocurrir durante su evolución. Como consecuencia de ello, la hora del sistema (tiempo interno) debe medirse con la misma escala de tiempo utilizado para medir el tiempo en el ambiente controlado (tiempo externo).

### Clasificación de sistemas de tiempo real

-   **Duro (hard)**:
    -   Involucra destrucción.
    -   Es relativamente fácil de identificar (destrucción y/o inconsistencia).
    -   Puede ser difícil de resolver.
-   **Blando (soft)**:
    -   Calidad en f(t).
    -   Hay límites de tiempo pero que la respuesta se pase de ese límite no necesariamente implica que se desmorone del sistema, sino que solo afecta a la calidad (ej: control de temperatura de un ambiente).
    -   Existen métricas para definir la calidad o el problema generado.
-   **Firme (firm)**:
    -   Se descartan algunas respuestas.
    -   La eventual ausencia/pérdida de respuesta dentro del límite de tiempo/plazo no genera destrucción pero tampoco tiene valor, se descarta. Ej: streaming,perder un frame o un bloque no se puede procesar más tarde en el tiempo, se descarta y se pierde algo de calidad, pero no se pierde el video/audio.
    -   Existen métricas para definir el problema generado.

Los sistemas de TR usualmente se enfocan en el TR Duro.

### Tareas

-   Las tareas son actividades con límites de tiempo de respuesta bien definidos.
-   Un sistema puede tener muchas tareas, que se pueden combinar entre sí, lo cual complica las cosas.
-   Las tareas "críticas" se asocian típicamente al TR Duro.
-   Podrían tener límites de TR blando/firme y duro.
-   Hay varias clases de tareas según su activación o aparición:
    -   Periódicas - Sincrónicas.
    -   Aperiódicas.
        -   Esporádicas: t mínimo entre activaciones T.
        -   Irregular.
        -   Ráfagas.

### Ejecución de una tarea de tiempo real

![Ejecución de una tarea de tiempo real](https://i.imgur.com/isyhe8G.png)

-   Activación: "cuándo".
-   Plazo: "cuánto".

1. t<sub>0</sub> depende del problema.
2. t<sub>1</sub> depende.
3. t<sub>1</sub> - t<sub>0</sub> podría variar.
4. t<sub>2</sub> - t<sub>1</sub> podría variar.
5. t<sub>3</sub> - t<sub>0</sub> depende del problema.
6. Tiempo de respuesta = t<sub>2</sub> - t<sub>0</sub>
7. Plazo de ejecución = t<sub>3</sub> - t<sub>0</sub>
8. t<sub>1</sub> - t<sub>0</sub>, t<sub>2</sub> - t<sub>1</sub> y por ende t<sub>3</sub> - t<sub>2</sub> dependen no solamente del sistema de cómputo si no también de la cantidad de tareas.

### Conceptos asociados al tiempo real

-   Se usa scheduling de corto plazo de CPU:
    -   Esto produce concurrencia casi natural, al identificar tareas.
    -   Se prioriza cumplir las restricciones de tiempo por sobre el rendimiento.
    -   Si hay menos tareas y/o las tareas son poco heterogeneas, hay menores problemas para asegurar el tiempo real.
    -   Se utiliza preemption intensivamente (no "respetar" el slice).
    -   Se minimizan las tareas sin restricciones de tiempo real para evitar interferencias/competencia por recursos.

### Análisis, desarrollo y simulación

-   El análisis tiene preferencia si es posible, lo que provee formalización y complejidad.
-   El desarrollo involucra entrada/salida, métodos de comunicación, y usualmente muchos detalles de hardware de bajo nivel.
-   La simulación es casi obligatoria en sistemas complejos, e involucra muchas combinaciones de eventos y tareas.

---
