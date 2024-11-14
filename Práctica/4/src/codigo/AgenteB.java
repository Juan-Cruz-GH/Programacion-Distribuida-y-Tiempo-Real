import jade.core.*;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class AgenteB extends Agent {
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
        System.out.println("\n\nAgente con nombre local: " + getLocalName());
        System.out.println("y nombre completo: " + getName());
        System.out.println("ha sido migrado exitosamente al contenedor: " + here().getID() + "\n\n");
    }
}
