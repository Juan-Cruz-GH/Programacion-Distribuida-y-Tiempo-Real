import jade.core.*;
import jade.wrapper.ContainerController;
import jade.wrapper.AgentController;
import java.util.ArrayList;

public class AgenteA extends Agent {
	private ArrayList<String> containers = new ArrayList<>();
	private Location origen;

	@Override
	protected void setup() {
		this.origen = here();

		// Crear contenedores que AgenteB recorrerá
		for (int i = 2; i < 7; i++) {
			String nombreContainer = "Container-" + i;
			crearContainer(nombreContainer);
			containers.add(nombreContainer);
		}

		// Agregar el contenedor de origen al final de la lista de contenedores
		containers.add(this.origen.getName());

		try {
			// Crear el AgenteB y pasarle la lista de los contenedores que debe recorrer,
			// incluyendo el container de origen para que sepa cuándo terminar
			AgentController agenteB = getContainerController().createNewAgent(
					"AgenteB",
					AgenteB.class.getName(),
					new Object[] { containers }
			);

			// Iniciar el AgenteB
			System.out.println("\nCreando AgenteB para recopilar información...\n");
			agenteB.start();
		} catch (Exception e) {
			System.out.println("Error al crear AgenteB: " + e.getMessage());
		}
	}

	private void crearContainer(String nombreContainer) {
		jade.core.Runtime runtime = jade.core.Runtime.instance();
		Profile profile = new ProfileImpl();
		profile.setParameter(Profile.CONTAINER_NAME, nombreContainer);
		profile.setParameter(Profile.MAIN_HOST, "localhost");
		runtime.createAgentContainer(profile);
	}
}