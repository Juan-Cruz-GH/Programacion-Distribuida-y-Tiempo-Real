package pdytr.ejercicio5;

import io.grpc.stub.StreamObserver;


public class ExperimentoServiceImpl extends ExperimentoGrpc.ExperimentoImplBase {
    @Override
    public void respuesta(Request request, StreamObserver<Response> responseObserver) {
        int cantidadDatos = request.getDatos().size();
        System.out.println("Recibí " + cantidadDatos + " bytes del cliente.");

        Response response = Response.newBuilder()
                .setMensaje("Recibido.")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}


