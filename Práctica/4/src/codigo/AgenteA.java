import jade.core.*;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

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

		/* 		// Obtener el runtime de JADE
		jade.core.Runtime runtime = jade.core.Runtime.instance();

		// Crear el contenedor secundario (Container-1)
		Profile profile = new ProfileImpl();
		profile.setParameter(Profile.CONTAINER_NAME, "Container-1");
		profile.setParameter(Profile.MAIN_HOST, "localhost");
		ContainerController container = runtime.createAgentContainer(profile); */

		// Obtener el contenedor donde está AgenteA inicialmente

		try {
			// Crear AgenteB en el contenedor secundario
			AgentController agenteB = container.createNewAgent("AgenteB", AgenteB.class.getName(), null);
			agenteB.start();
			System.out.println("AgenteB creado en Container-1");
		} catch (Exception e) {
			System.err.println("Error creating agent: " + e.getMessage());
		}

		// Migramos AgenteB a Main-Container
		try {
			Location destino = new ContainerID("Main-Container", null);; // Crea el Main-Container
			AgentController agenteB = container.getAgent("AgenteB"); // Obtener el AgenteB en Container-1
			agenteB.move(destino);
			System.out.println("AgenteB migrado a Main-Container");
		} catch (Exception e) {
			System.err.println("Error migrating agent: " + e.getMessage());
		}

	}
}
