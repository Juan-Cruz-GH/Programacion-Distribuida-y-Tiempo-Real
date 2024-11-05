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

<h1 align="center">Clase 3 - 9 de septiembre, 2024</h1>

## Virtualización

### Ambientes virtuales

-   Son entornos que permiten ejecutar sistemas operativos o aplicaciones en un entorno aislado.
-   Se pueden crear vía VirtualBox, VMWare, o Windows Subsystem For Linux.

### Automatización de máquinas virtuales

-   Herramientas como Vagrant permiten definir el entorno virtual vía un archivo de configuración (vagrantfile) que automatiza la instalación y configuración de la máquina virtual.
-   En cierto sentido, podemos "programar" la instalación y configuración de una VM.

### Contenedores

-   Los contenedores permiten empaquetar aplicaciones junto con todas sus dependencias en unidades ejecutables ligeras.
-   El ejemplo clásico es Docker.
-   Se pueden manejar muchos contenedores distintos a la vez usando herramientas como Kubernetes.

### "Bare metal"

-   El bare metal se refiere al hardware físico sobre el cual un OS corre.
-   Lo que "ve" el sistema operativo no necesariamente es bare metal (hal = Hardware Abstraction Level).
-   Host: el sistema operativo o entorno que corre directamente sobre el hardware físico y que contiene a las máquinas virtuales (VMs).
-   Guest: son las máquinas virtuales que corren sobre el host.

### Implementación de la virtualización

-   La virtualización se realiza mediante los hipervisores que son software que gestionan a las máquinas virtuales.
-   Hay 2 tipos de hipervisores:
    -   Tipo 1: corre directamente sobre el hardware físico, sin sistema operativo de por medio.
    -   Tipo 2: corre sobre un sistema operativo convencional. Es el más común y el que usaremos mediante Vagrant.
-   Cada OS guest tiene su propio "hardware", no se enteran que están virtualizados, no es bare metal.

### Vagrant

-   Vagrant es una herramienta de automatización para ambientes de desarrollo.
-   Facilita la creación y gestión de máquinas virtuales mediante archivos de configuración y comandos simples.
-   Las "boxes" son imágenes o plantillas preconstruidas que contienen configuraciones de sistemas operativos y herramientas específicas. Se pueden usar para crear máquinas virtuales de manera rápida y estandarizada.
-   Vagrant soporta diferentes herramientas de virtualización como VirtualBox, VMware, y Hyper-V. Estos proveedores son los que gestionan el entorno en el que las máquinas virtuales son ejecutadas.
-   El vagrantfile es el archivo de configuración principal utilizado por Vagrant. En este archivo se describen aspectos como:
    -   Configuración del sistema operativo.
    -   Cantidad de CPU y RAM asignada.
    -   Sincronización de carpetas entre el host y el guest.
    -   Configuración de red para la VM, como redes privadas o públicas.
-   Vagrant acepta varios comandos útiles:
    -   vagrant up: inicia la máquina virtual definida en el Vagrantfile.
    -   vagrant halt: detiene una máquina virtual en ejecución.
    -   vagrant destroy: elimina la máquina virtual completamente.
    -   vagrant ssh: permite conectarse a la máquina virtual a través de SSH.
-   Gestión de redes:
    -   Red privada: El guest y el host pueden comunicarse, pero no es accesible desde fuera del host.
    -   Red pública: El guest puede ser accesible por otras máquinas en la red.

---

<h1 align="center">Clase 4 - 17 de septiembre, 2024</h1>

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

<h1 align="center">Clase 5 - 19 de septiembre, 2024</h1>

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

<h1 align="center">Clase 6 - 2 de octubre, 2024</h1>

## Soporte

### Introducción

-   Los Sistemas Distribuidos necesitan software de soporte.
-   De este software, existen 3 tipos:
    1. SOD (Sistema Operativo Distribuido): Es el que oculta o enmascara a todas las características del sistema distribuido y lo hace ver como si fuera un sistema centralizado.
    2. SOR (Sistema Operativo de Red): Provee servicios para aplicaciones que pueden ser locales o remotas.
    3. Middleware: Usa servicios de un SOR.

### SOD vs SOR vs Middleware

| Item                        | SOD (multiprocesador) | SOD (multicomputadora) | SOR      | Middleware            |
| --------------------------- | --------------------- | ---------------------- | -------- | --------------------- |
| Transparencia               | Muy alta              | Alta                   | Baja     | Alta                  |
| Mismo SO en todos los nodos | Si                    | Si                     | No       | No                    |
| Copias del SO               | 1                     | N                      | N        | N                     |
| Modo de comunicación        | Memoria compartida    | Pasaje de mensajes     | Archivos | Específico del modelo |
| Manejo de recursos          | Global, central       | Global, distribuido    | Por nodo | Por nodo              |
| Escalabilidad               | No                    | Moderado               | Si       | Varía                 |
| Apertura                    | Cerrado               | Cerrado                | Abierto  | Abierto               |

### Procesos e hilos

-   Típicamente, las aplicaciones de usuario del mundo real terminan haciendo uso de varios procesos, y ya sea que la aplicación sea distribuida o no, cada uno de esos procesos tiene como mínimo un hilo. De hecho es común que cada proceso tenga varios hilos y muchas veces resulta útil.
-   El tema de hilos y procesos es muy relevante por la naturaleza concurrente de los sistemas distribuidos.
-   Es común que un servidor tenga varios hilos, pero no muy común que un cliente tenga varios hilos.

#### 3 alternativas para tener varios hilos en un servidor

![Varios hilos en un servidor](https://i.imgur.com/qhSZhVb.png)

1. Crear un nuevo hilo para cada requerimiento que llega (se asemeja a HTTP 1.0).
2. Crear un nuevo hilo para cada conexión, donde cada conexión tendrá N requerimientos (se asemeja a HTTP 1.1).
3. Tener un hilo para cada recurso o cosa que se le puede pedir al servidor.

#### Cliente y Servidor con hilos

![Cliente y Servidor con hilos](https://i.imgur.com/6WNKzGj.png)

-   Cliente con 2 hilos: el primero genera algún tipo de resultado y el segundo, en función de ese resultado, genera requerimientos al servidor.
-   El servidor podría tener un hilo para cada requerimiento o un hilo para cada conexión con el cliente.

### Suposiciones falsas

-   Muchos sistemas distribuidos se vuelven innecesariamente complejos por culpa de errores asociados a suposiciones falsas:

1. La red es confiable.
2. La red es segura.
3. La red es homogénea.
4. La topología no cambia.
5. La latencia es cero.
6. El ancho de banda es infinito.
7. El costo de transporte es cero.
8. Hay un solo administrador.

-   De estas 8, las primeras 7 están puramente relacionadas a las redes, lo cual nos indica que hay que tener un muy buen entendimiento de redes para desarrollar sistemas distribuidos de forma correcta.

---

<h1 align="center">Clase 7 - 9 de octubre, 2024</h1>

## Estilos Arquitecturales

### Arquitecturas de hardware

-   Inicialmente se tenían arquitecturas de hardware independientes del software que se usaría sobre ese hardware.
-   A medida que fue pasando el tiempo, sin embargo, las arquitecturas de hardware se empezaron a pensar a la par del software → continuidad de desarrollo, integridad, etc.

### Arquitecturas de software

-   Se define, según Tanenbaum, como los diferentes componentes de software que constituyen al sistema.
-   Estas arquitecturas de software nos dicen cómo los componentes de software deben organizarse y cómo deben interactuar.
-   Un **estilo arquitectural**, de forma similar, se forma en términos de los componentes, cómo estos están interconectados, qué datos se intercambian entre sí, y cómo están unificadamente configurados en el sistema.
-   Un **componente** es una unidad modular con interfaces bien requeridas y provistas que puede ser reemplazado, siempre y cuando sus interfaces no se modifiquen.
-   Según el profe, estilo arquitectural sería equivalente a una arquitectura de software.
-   Hay varios estilos arquitecturales populares en los Sistemas Distribuidos, todos ellos afectarán a la forma de programar y a los aspectos de tiempo real.

### Arquitecturas de software "Layered"

-   Una capa provee servicios a la capa superior y requiere servicios de la capa inferior.
-   Puede ser definida usando los conceptos de Cliente Servidor, donde cada capa es cliente de la capa inferior y servidor de la capa superior.
-   Las comunicaciones utilizan
-   Componentes: capas.
-   Inferfaces: Cliente/Servidor.
-   Organización: Cliente/Servidor.
-   Comunicaciones: requerimientos y respuestas.

### Arquitecturas de software "Object based"

-   Consiste en pensar en todo el sistema distribuido como objetos que están distribuidos a lo largo del sistema.
-   Componentes: objetos.
-   Inferfaces: métodos.
-   Organización: distribuir a los objetos en el sistema.
-   Comunicaciones: métodos.

### Arquitecturas de software "Resource-centered"

-   Se asocia a arquitecturas RESTful:
    -   Los recursos son identificados por un nombre único.
    -   Todos los servicios tienen la misma interfaz.
    -   Los mensajes tienen toda la información necesaria.
    -   Los componentes no tienen memoria.
-   Componentes: manejadores de recursos.
-   Inferfaces: servicios.
-   Organización: distribuir manejadores en el sistema.
-   Comunicaciones: interfaces, servicios.
-   Las operaciones son PUT GET DELETE POST.

### Arquitecturas de software "Publish-Subscribe"

-   Enfocado directamente en los eventos.
-   Se deben identificar todos los eventos, es decir las cosas que afectan o hacen que algo cambie en el sistema.
-   Para cada evento, se debe analizar:
    -   Cómo se genera.
    -   Qué significa.
    -   Qué datos involucra.
    -   Si son eventos propios, de entrada/salida, etc.
-   Los eventos son heterogeneos.
-   Los componentes del sistema que **generan** eventos son los publicadores.
-   Los componentes del sistema que **manejan** eventos son los suscriptores.
-   Se construye todo el sistema en base a solo estos dos tipos de componentes.
-   Utiliza **comunicación unidireccional y asincrónica**.
-   Ventajas:
    -   Desacopla el sistema en espacio: cada componente interactúa con el middleware, no con otros componentes.
    -   Orienta la identificación de requerimientos (ing. de software).
-   Desventajas:
    -   Acoplamiento en tiempo: se puede evitar utilizando persistencia de datos.

![Esquema Publish-Subscribe](https://i.imgur.com/Tup9TJs.png)

-   El bus es middleware que puede ser un protocolo o una biblioteca.
-   El bus es el que intercomunica a todos los componentes.
-   La invocación puede ser directa o implícita.
-   Los componentes pueden estar distribuidos en distintos sitios o estar todos en un mismo sitio.
-   Resumen:
    -   Componentes: procesos o módulos o hilos.
    -   Inferfaces: publicación y subscripción de eventos.
    -   Organización: distribuir procesos y determinar si es publicador o suscriptor.
    -   Comunicaciones: definidas por el middleware para/de publicación/subscripción.

---

<h1 align="center">Clase 8 - 9 de octubre, 2024</h1>

## Estilo arquitectural en capas detallado

### Teniendo 2 capas

-   Es directamente equivalente a Cliente/Servidor: tenemos una capa que es solo el cliente, y una capa que es solo el servidor.

### Teniendo 3 capas

-   Una capa es solo cliente y especificamente dedicada a interfaz de usuario.
-   La siguiente capa, la intermedia, es servidora de la capa de interfaz de usuario y cliente de la capa de base de datos. Se le suele llamar **lógica de negocio**.
-   La última capa es solo servidora, y especificamente pensada en base de datos, almacenamiento persistente.
-   La primera y la última capa son fáciles de definir y no ambiguas, debido a lo populares que son desde hace mucho tiempo.
-   Ejemplo: motor de búsqueda en Internet:
    ![Motor de búsqueda](https://i.imgur.com/z76uKw3.png)

#### Servidor "gordo" vs "flaco"

![](https://i.imgur.com/nsVKtff.png)

-   En los dos primeros ejemplos (a la izquierda) se tiene un gran servidor y una interfaz de usuario mínima. Esto se denomina servidor "gordo", donde todo el procesamiento está en el servidor, lo cual implica que ese servidor debe tener una gran capacidad de procesamiento, comunicaciones, almacenamiento, etc. Este concepto tiene mucha relación con el rendimiento y la escalabilidad. Este modelo se volvio menos popular a medida que fue pasando el tiempo debido a que las computadoras de los usuarios se volvieron más y más potentes, permitiendo que la carga de procesamiento sea más balanceada.

### Más de 3 capas?

-   La idea en este caso sería hacer subdivisiones de la capa intermedia.
-   Siempre que se tengan más capas se tendrá mayor modularización, lo cual puede simplificar el desarrollo y reducir el acople.
-   A su vez, tener más capas implica tener más interfaces. Esto aumenta la cantidad de datos a transferir y resulta en una mayor necesidad de interacciones y sincronizaciones.
-   No se suele usar, en sistemas distribuidos, más de 3 capas, debido a que se vuelve contraproducente.

---

<h1 align="center">Clase 9 - 16 de octubre, 2024</h1>

## Comunicaciones

### Introducción

-   Las comunicaciones entre procesos ya las conocemos del contexto de los sistemas operativos.
-   En las redes, tenemos los protocolos.
-   En el contexto de sistemas distribuidos, nos interesa analizar las comunicaciones en el sentido de datos entre procesos/módulos.
    -   Se busca que el nivel de abstracción sea lo más cercano a la aplicación que se está desarrollando.
    -   Se considera la distribución de estas comunicaciones.
    -   Se considera el tiempo de estas comunicaciones.
    -

### Aspectos importantes

-   **Confiabilidad**:
    -   Qué tan seguros estamos de que cuando enviamos un dato, ese dato llega a destino y llega de manera correcta.
    -   Es "lo mejor" para programar.
    -   No hay pérdida de datos.
    -   Mantiene el orden, los mensajes llegan en el mismo orden que fueron enviados.
    -   Implica un **costo** en recursos (hardware) y en rendimiento (tiempo).
-   **Sincronismo**:
    -   Se refiere al orden de ejecución.
    -   Algunos eventos requieren una espera "punta a punta", es decir, entre el proceso que envía y el que recibe. Es el tipo de sincronismo más costoso.
    -   Otros eventos, los "locales", (por ejemplo Sockets), cuando un proceso envía un dato, el sincronismo es local, es decir que el mecanismo crea una copia de los datos que el cliente quiere enviar, y luego se libera el que envía, no espera.
    -   Todo sincronismo implica un **costo** en rendimiento.
-   **Persistencia**:
    -   Si las comunicaciones se pueden llevar a cabo sin que los procesos estén necesariamente exactamente al mismo tiempo en ejecución.
    -   Se puede ver como la independencia entre el proceso que envía y el proceso que recibe. Si el receptor no necesita estar ejecutandose para que el mensaje le llegue, la comunicación es persistente.
    -   Tener comunicaciones persistentes implica un **costo** en almacenamiento y también en rendimiento, aunque este último no es tan grande.
-   **Datos**:
    -   A pesar que los datos al final siempre terminan siendo una secuencia de bits, resulta útil en un principio definirlos semánticamente, entender qué es lo que se envía y por qué, con qué formato, etc.
-   **Middleware**: casi todas implican comunicaciones.

### Java Message Service

-   Es una API provista por Java para manejar comunicaciones asincrónicas entre dos o más sistemas.
-   Provee comunicaciones confiables, persistentes, y desacopla los componentes.
-   Posee dos modelos de comunicación principales:
    -   Punto a punto: usa una cola, hay un emisor y un receptor, y el mensaje se recibe una sola vez.
    -   Publish/Subscribe: un emisor, múltiples receptores, el mensaje se envía a todos los suscriptores.

---

<h1 align="center">Clase 10 - 24 de octubre, 2024</h1>

## Movilidad de código

---

<h1 align="center">Clase 11 - 4 de noviembre, 2024</h1>

## Sincronización

---

<h1 align="center">Clase 12 - 4 de noviembre, 2024</h1>

## Sincronización - Relojes físicos

---

<h1 align="center">Clase 13 - 4 de noviembre, 2024</h1>

## Sincronización - Relojes lógicos
