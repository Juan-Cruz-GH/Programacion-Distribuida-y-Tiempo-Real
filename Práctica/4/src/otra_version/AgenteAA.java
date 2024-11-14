import jade.core.Agent;
import jade.core.AID;
import jade.core.Location;
import jade.core.behaviours.OneShotBehaviour;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import jade.core.ContainerID;

public class AgenteAA extends Agent {

    @Override
    protected void setup() {
        System.out.println("\n\nHola, soy el agente con nombre local: " 
        + getLocalName() 
        + ", nombre completo: " 
        + getName() 
        + " y en contenedor: " 
        + here().getID()
        + "\n\n");

        		// To migrate the agent
		try {
			ContainerID destino = new ContainerID("Main-Container", null);
            AgentController controller = createNewAgent("agenteB", "AgenteB", null);
            controller.start();

			System.out.println("Migrando al AgenteB hacia " + destino.getID());
            controller.move(destino);

		} catch (Exception e) {
			System.err.println("Error al intentar migrar a AgenteB: " + e.getMessage());
		}
    }
}
