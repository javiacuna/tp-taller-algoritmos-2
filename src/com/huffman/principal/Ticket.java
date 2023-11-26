package com.huffman.principal;

public class Ticket {
    private int apuesta;
    private int numero;
    private int posicion;

    public Ticket(int apuesta, int numero, int posicion) {
        this.apuesta = apuesta;
        this.numero = numero;
        this.posicion = posicion;
    }

    public int getApuesta() {
        return apuesta;
    }

    public int getNumero() {
        return numero;
    }

    public int getPosicion() {
        return posicion;
    }
}
