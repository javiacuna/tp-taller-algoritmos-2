package com.huffman.principal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Quiniela {
    private Sorteo sorteoActual;
    private String turno;
    private List<Ticket> tickets;
    private List<Premio> premios;

    public Quiniela(String turno) {
        if(turno.equals("maniana")){
            this.turno = "maniana";
        }else{
            this.turno = "noche";
        }
        this.sorteoActual = null;
        this.tickets = new ArrayList<>();
        this.premios = new ArrayList<>();
    }

    public void cargarSorteo(String rutaArchivo) throws IOException {
        List<Integer> numerosSorteados = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                numerosSorteados.add(Integer.parseInt(partes[1].trim()));
            }
        }

        this.sorteoActual = new Sorteo(numerosSorteados);
    }

    public void agregarTicket(String rutaArchivo) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                int apuesta = Integer.parseInt(partes[0].trim());
                int numero = Integer.parseInt(partes[1].trim());
                int posicion = Integer.parseInt(partes[2].trim());
                this.tickets.add(new Ticket(apuesta, numero, posicion));
            }
        }
    }

    public void verificarTickets() {
        if (sorteoActual == null) {
            System.out.println("Error: No se ha cargado el sorteo. Utilice cargarSorteo() primero.");
            return;
        }
    
        for (Ticket ticket : tickets) {
            int numeroTicket = ticket.getNumero(); //9876
            int posicionTicket = ticket.getPosicion(); //3
        
            double montoTotal = 0;
        
            for (int i = 0; i < posicionTicket; i++) {
                int ultimoDigitoSorteo = sorteoActual.getNumerosSorteados().get(i) % 10;
                int ultimasDosCifrasSorteo = sorteoActual.getNumerosSorteados().get(i) % 100;
                int ultimasTresCifrasSorteo = sorteoActual.getNumerosSorteados().get(i) % 1000;
    
                if (numeroTicket == ultimoDigitoSorteo) {
                    // ganador, factor premio = 7
                    montoTotal += calcularPremio(ticket.getApuesta(), 7, ticket.getPosicion());

                } else if (numeroTicket == ultimasDosCifrasSorteo) {
                    // ganador, factor premio = 70
                    montoTotal += calcularPremio(ticket.getApuesta(), 70, ticket.getPosicion());

                } else if (numeroTicket == ultimasTresCifrasSorteo) {
                    // ganador, factor premio = 500 
                    montoTotal += calcularPremio(ticket.getApuesta(), 500, ticket.getPosicion());
                    
                } else if (numeroTicket == sorteoActual.getNumerosSorteados().get(i)) {
                    // ganador, factor premio = 3500
                    montoTotal += calcularPremio(ticket.getApuesta(), 3500, ticket.getPosicion());
                }
            }
            // descontar apuesta
            montoTotal -= ticket.getApuesta();
            premios.add(new Premio(-montoTotal));
        }
    }

    public double calcularPremio(int apuesta, int factorPremio, int posicion){
        return (apuesta * factorPremio) / posicion;
    }

    public void calcularGananciaJornada(){
        double total = 0;
        for (Premio premio : premios){
            total += premio.getMonto();
        }
        System.out.println("Balance total de la " + this.turno + ": " + total);
    }
}

// private double calcularPremio(int apuesta, int factorPremio, int posicion, int numeroTicket) {
//     double monto = apuesta * factorPremio;
    
//     if (posicion <= 5) {
//         return monto / posicion;
//     } else {
//         int cantidadRepeticiones = 0;
//         for (int numeroSorteo : sorteoActual.getNumerosSorteados()) {
//             if (numeroSorteo == numeroTicket) {
//                 cantidadRepeticiones++;
//                 if (cantidadRepeticiones >= 15) {
//                     break;
//                 }
//             }
//         }
//         return monto * cantidadRepeticiones / 15;
//     }
// }

// private void informarGanador(Ticket ticket, int factorPremio) {
//     int apuesta = ticket.getApuesta();
//     int numero = ticket.getNumero();
//     int posicion = ticket.getPosicion();

//     double premio = calcularPremio(apuesta, factorPremio, posicion, numero);

//     System.out.println("¡Felicidades! El ticket es ganador. Premio: $" + premio);
//     System.out.println("Número del ticket ganador: " + numero);
//     System.out.println("Posición del ticket ganador: " + posicion);
//     System.out.println("---------------------------------------");
// }

// private void informarNoGanador(Ticket ticket) {
//     int numero = ticket.getNumero();

//     System.out.println("Lo siento, el ticket no es ganador.");
//     System.out.println("Número del ticket perdedor: " + numero);
//     System.out.println("---------------------------------------");
// }