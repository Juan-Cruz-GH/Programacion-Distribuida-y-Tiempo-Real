import jade.core.*;
import jade.util.leap.Serializable;
import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;
import java.util.ArrayList;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class AgenteB extends Agent {
    private ArrayList<String> containers;
    private int indiceActual = 0;
    private Location origen;
    private ArrayList<ContainerInfo> info = new ArrayList<>();
    private long tiempoInicial;
    private long tiempoFinal;

    public static class ContainerInfo implements Serializable {
        private String nombrePC;
        private long memoriaDisponible;
        private double cargaCPU;

        public void setNombrePC(String nombrePC) {
            this.nombrePC = nombrePC;
        }

        public void setMemoriaDisponible(long memoriaDisponible) {
            this.memoriaDisponible = memoriaDisponible;
        }

        public void setCargaCPU(double cargaCPU) {
            this.cargaCPU = cargaCPU;
        }

        public String getInfoPC() {
            return "\nNombre de la computadora: " + nombrePC +
                    "\nMemoria disponible: " + (memoriaDisponible / (1024 * 1024)) + " MB" +
                    "\nCarga de la CPU: " + String.format("%.2f", cargaCPU) + "%\n";
        }
    }

    @Override
    protected void setup() {
        tiempoInicial = System.currentTimeMillis();

        // Recibir parámetros iniciales
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            containers = (ArrayList<String>) args[0];
        } else {
            throw new IllegalArgumentException("No se proporcionaron los contenedores para recorrer.");
        }

        origen = here();
        System.out.println("\nSoy el AgenteB, inicio en: " + origen.getName() + "\n");
        moverAlSiguienteContainer();
    }

    private void timeout(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            System.out.println("Error al dormir el hilo: " + e.getMessage());
        }
    }

    private void moverAlSiguienteContainer() {

        /*
        Esperar un poco antes de movernos al siguiente contenedor para poder ver
        en la GUI los movimientos (esto afecta a la performance del tiempo final obviamente,
        es solo para explicar el ejercicio de forma clara)
        */
        // timeout(2000);
        try {
            String containerSiguiente = containers.get(indiceActual++);
            Location destino = new ContainerID(containerSiguiente, null);
            doMove(destino);
        } catch (Exception e) {
            System.out.println("Error al intentar moverse de container: " + e.getMessage());
        }
    }

    @Override
    protected void afterMove() {
        Location currentLocation = here();
        System.out.println("\nAgenteB se movió a: " + currentLocation.getName() + "\n");

        /*
        Si no estoy en el contenedor donde empecé, recolecto información.
        Si estoy en el contenedor donde empecé, informo los resultados y computo
        el tiempo final que tomó todo el recorrido
         */
        if (!currentLocation.getName().equals(origen.getName())) {
            recolectarInformacion(currentLocation.getName());
            moverAlSiguienteContainer();
        } else {
            System.out.println("\nInformando los datos recopilados de las computadoras: \n");
            for (ContainerInfo containerInfo : info) {
                System.out.println(containerInfo.getInfoPC());
            }
            tiempoFinal = System.currentTimeMillis();
            System.out.println("------------------------------------------------------------");
            System.out.println("Tiempo total de la recopilación de información: " + (tiempoFinal - tiempoInicial) + " ms");
        }
    }

    private void recolectarInformacion(String containerName) {
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        ContainerInfo containerInfo = new ContainerInfo();
        try {
            InetAddress ip = InetAddress.getLocalHost();
            String hostname = ip.getHostName();
            containerInfo.setNombrePC(containerName + " (" + hostname + ")");
        } catch (UnknownHostException e) {
            System.out.println("Error al obtener el nombre del host: " + e.getMessage());
        }
        containerInfo.setMemoriaDisponible(osBean.getFreePhysicalMemorySize());
        containerInfo.setCargaCPU(osBean.getSystemCpuLoad() * 100);

        info.add(containerInfo);
    }
}