package pdytr.ejercicio5;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExperimentoClient {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext(true)
                .build();

        ExperimentoGrpc.ExperimentoBlockingStub stub = ExperimentoGrpc.newBlockingStub(channel);

        List<byte[]> datos = List.of(new byte[100_000], new byte[1_000_000]); // 10^5 bytes y 10^6 bytes
        List<String> descripciones = List.of("Experimento enviando 10^5 bytes", "Experimento enviando 10^6 bytes");

        for (int i = 0; i < datos.size(); i++) {
            byte[] currentData = datos.get(i);
            String descripcion = descripciones.get(i);

            List<Double> tiempos = new ArrayList<>();

            for (int j = 0; j < 1000; j++) {
                long tiempoInicio = System.nanoTime();
                stub.respuesta(Request.newBuilder().setDatos(com.google.protobuf.ByteString.copyFrom(currentData)).build());
                long tiempoFin = System.nanoTime();

                double tiempoEnMs = (tiempoFin - tiempoInicio) / 1_000_000.0;
                tiempos.add(tiempoEnMs);
            }

            double tiempoPromedio = tiempos.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
            double tiempoMinimo = Collections.min(tiempos);
            double desviacionEstandar = calcularDesviacionEstandar(tiempos, tiempoPromedio);

            System.out.println(descripcion + "\n");
            System.out.printf("Tiempo mínimo: %.6f ms\n", tiempoMinimo);
            System.out.printf("Tiempo promedio: %.6f ms\n", tiempoPromedio);
            System.out.printf("Desviación estándar: %.6f ms\n\n", desviacionEstandar);
        }

        channel.shutdown();
    }

    private static double calcularDesviacionEstandar(List<Double> tiempos, double promedio) {
        double suma = 0.0;
        for (double tiempo : tiempos) {
            suma += Math.pow(tiempo - promedio, 2);
        }
        return Math.sqrt(suma / tiempos.size());
    }
}
