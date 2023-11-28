package com.huffman.principal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Quiniela {
    private Sorteo sorteoActual;
    private List<Ticket> tickets;

    public Quiniela() {
        this.sorteoActual = null;
        this.tickets = new ArrayList<>();
    }

    public void cargarSorteoDesdeCSV(String rutaArchivo) throws IOException {
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

    public void agregarTicketDesdeCSV(String rutaArchivo) throws IOException {
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
            System.out.println("Error: No se ha cargado el sorteo. Utilice cargarSorteoDesdeCSV() primero.");
            return;
        }

        for (Ticket ticket : tickets) {
            int numeroTicket = ticket.getNumero();
            int posicionTicket = ticket.getPosicion();

            int ultimoDigitoSorteo = sorteoActual.getNumerosSorteados().get(posicionTicket - 1) % 10;
            int ultimasDosCifrasSorteo = sorteoActual.getNumerosSorteados().get(posicionTicket - 1) % 100;
            int ultimasTresCifrasSorteo = sorteoActual.getNumerosSorteados().get(posicionTicket - 1) % 1000;

            if (numeroTicket == ultimoDigitoSorteo) {
                informarGanador(ticket, 7);
            } else if (numeroTicket == ultimasDosCifrasSorteo) {
                informarGanador(ticket, 70);
            } else if (numeroTicket == ultimasTresCifrasSorteo) {
                informarGanador(ticket, 500);
            } else if (numeroTicket == sorteoActual.getNumerosSorteados().get(posicionTicket - 1)) {
                informarGanador(ticket, 3500);
            } else {
                informarNoGanador(ticket);
            }
        }
    }

    private void informarGanador(Ticket ticket, int factorPremio) {
        int apuesta = ticket.getApuesta();
        int numero = ticket.getNumero();
        int posicion = ticket.getPosicion();

        double premio = calcularPremio(apuesta, factorPremio, posicion, numero);

        System.out.println("¡Felicidades! El ticket es ganador. Premio: $" + premio);
        System.out.println("Número del ticket ganador: " + numero);
        System.out.println("Posición del ticket ganador: " + posicion);
        System.out.println("---------------------------------------");
    }

    private double calcularPremio(int apuesta, int factorPremio, int posicion, int numeroTicket) {
        double monto = apuesta * factorPremio;

        if (posicion <= 5) {
            return monto / posicion;
        } else {
            int cantidadRepeticiones = 0;
            for (int numeroSorteo : sorteoActual.getNumerosSorteados()) {
                if (numeroSorteo == numeroTicket) {
                    cantidadRepeticiones++;
                    if (cantidadRepeticiones >= 15) {
                        break;
                    }
                }
            }
            return monto * cantidadRepeticiones / 15;
        }
    }

    private void informarNoGanador(Ticket ticket) {
        int numero = ticket.getNumero();

        System.out.println("Lo siento, el ticket no es ganador.");
        System.out.println("Número del ticket perdedor: " + numero);
        System.out.println("---------------------------------------");
    }

    public static void main(String[] args) {
        Quiniela quinielaManager = new Quiniela();

        try {
            quinielaManager.cargarSorteoDesdeCSV("sorteo.txt");
            quinielaManager.agregarTicketDesdeCSV("apuestas.csv");
            quinielaManager.verificarTickets();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
