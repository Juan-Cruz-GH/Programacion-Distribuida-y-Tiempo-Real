import jade.core.*;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.AgentContainer;

public class AgenteA extends Agent {
	@Override
	protected void setup() {
        System.out.println("\n\nHola, soy el agente con nombre local: " 
        + getLocalName() 
        + ", nombre completo: " 
        + getName() 
        + " y en contenedor: " 
        + here().getID()
        + "\n\n");

		// Obtener el contenedor donde está AgenteA actualmente (Container-1).
		AgentContainer container = getContainerController();

		try {

			// Crear AgenteB en el contenedor secundario.
			AgentController agenteB = container.createNewAgent("AgenteB", AgenteB.class.getName(), null);
			agenteB.start();
		} catch (Exception e) {
			System.err.println("Error creando al agente B: " + e.getMessage());
		}

		// Migramos AgenteB al Main-Container.
		try {
			// Esperamos 3s para tener tiempo a que se vea que AgenteB fue creado en Container-1 y será migrado
			// al Main-Container.
			Thread.sleep(3000);
			Location destino = new ContainerID("Main-Container", null);;
			AgentController agenteB = container.getAgent("AgenteB");
			agenteB.move(destino);
		} catch (Exception e) {
			System.err.println("Error migrando al agente B: " + e.getMessage());
		}
	}
}
