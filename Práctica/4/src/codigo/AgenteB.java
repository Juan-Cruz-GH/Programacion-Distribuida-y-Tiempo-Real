import jade.core.*;

public class AgenteB extends Agent {

    private Location destino;

    @Override
    protected void setup() {
        System.out.println("\n\nHola, soy el agente con nombre local: " 
        + getLocalName() 
        + ", nombre completo: " 
        + getName() 
        + " y en contenedor: " 
        + here().getID()
        + "\n\n");

		Object[] args = getArguments();
        this.destino = (Location) args[0];
    }
    @Override
    protected void afterMove() {
        System.out.println("\n\nAgente con nombre local: " 
        + getLocalName() 
        + ", nombre completo: " 
        + getName() 
        + " ha sido migrado exitosamente al contenedor: " 
        + here().getID()
        + "\n\n");

        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Error de threading.");
        }
        this.doMove(this.destino);
    }
}
