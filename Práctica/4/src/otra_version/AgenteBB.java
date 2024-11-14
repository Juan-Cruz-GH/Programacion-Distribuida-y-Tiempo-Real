import jade.core.Agent;
import jade.core.AID;
import jade.core.Location;
import jade.core.behaviours.OneShotBehaviour;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import jade.core.ContainerID;

public class AgenteBB extends Agent {

    @Override
    protected void setup() {
        System.out.println("\n\nHola, soy el agente con nombre local: " 
        + getLocalName() 
        + ", nombre completo: " 
        + getName() 
        + " y en contenedor: " 
        + here().getID()
        + "\n\n");
    }

    @Override
    protected void afterMove() {
        // Método que se llama automáticamente después de un doMove()
        System.out.println("AgenteB : " + here().getID());

        System.out.println("\n\nAgente con nombre local: " + getLocalName());
        System.out.println("y nombre completo: " + getName());
        System.out.println("ha sido migrado exitosamente al contenedor: " + here().getID() + "\n\n");
    }
}
