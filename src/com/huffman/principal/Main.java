package com.huffman.principal;

import com.adm.archivo.AdmArchivo;
import com.huffman.arbol.ArbolHuffman;

import java.io.File;
import java.io.IOException;

public class Main {

    private static AdmArchivo admSorteo;
    private static ArbolHuffman sorteoHuffman;
    private static AdmArchivo admJugada;
    private static ArbolHuffman jugadaHuffman;
    
    private static String JUGADA_ARCHIVO_A_COMPRIMIR = "C:\\Users\\ignac\\OneDrive\\Escritorio\\Scripts\\tp-taller-algoritmos-2\\src\\txt\\jugada\\jugadaAComprimir.txt";
    private static String JUGADA_ARCHIVO_DESCOMPRIMIR = "C:\\Users\\ignac\\OneDrive\\Escritorio\\Scripts\\tp-taller-algoritmos-2\\src\\txt\\jugada\\jugadaDescomprimir.txt";
    private static String JUGADA_ARCHIVO_TABLA = "C:\\Users\\ignac\\OneDrive\\Escritorio\\Scripts\\tp-taller-algoritmos-2\\src\\txt\\jugada\\jugadaTabla.txt";

    private static String SORTEO_ARCHIVO_A_COMPRIMIR = "C:\\Users\\ignac\\OneDrive\\Escritorio\\Scripts\\tp-taller-algoritmos-2\\src\\txt\\sorteo\\sorteoAComprimir.txt";
    private static String SORTEO_ARCHIVO_DESCOMPRIMIR = "C:\\Users\\ignac\\OneDrive\\Escritorio\\Scripts\\tp-taller-algoritmos-2\\src\\txt\\sorteo\\sorteoDescomprimir.txt";
    private static String SORTEO_ARCHIVO_TABLA = "C:\\Users\\ignac\\OneDrive\\Escritorio\\Scripts\\tp-taller-algoritmos-2\\src\\txt\\sorteo\\sorteoTabla.txt";

    public static void main(String[] args) {

        Quiniela quinielaManiana = new Quiniela("maniana");
        // Quiniela quinielaNoche = new Quiniela("noche");

        admSorteo = new AdmArchivo();
        sorteoHuffman = new ArbolHuffman();

        admJugada = new AdmArchivo();
        jugadaHuffman = new ArbolHuffman();

        //SORTEO
        admSorteo.cargarTablaHuffman(SORTEO_ARCHIVO_TABLA);
        sorteoHuffman.setTablaHasHuffmanCodigos(admSorteo.getTablaHasHuffmanCodigos());
        sorteoHuffman.procesarListaDeArboles(sorteoHuffman.crearListaArboles(admSorteo.getTablaHuffman()));
        sorteoHuffman.generarCodigoHuffman(sorteoHuffman.getNodoArbol2(),"");
        admSorteo.generarArchivoComprimido(SORTEO_ARCHIVO_A_COMPRIMIR, SORTEO_ARCHIVO_A_COMPRIMIR+".compress");
        admSorteo.descomprimir(SORTEO_ARCHIVO_A_COMPRIMIR+".compress", SORTEO_ARCHIVO_DESCOMPRIMIR);

        //JUGADA
        admJugada.cargarTablaHuffman(JUGADA_ARCHIVO_TABLA);
        jugadaHuffman.setTablaHasHuffmanCodigos(admJugada.getTablaHasHuffmanCodigos());
        jugadaHuffman.procesarListaDeArboles(jugadaHuffman.crearListaArboles(admJugada.getTablaHuffman()));
        jugadaHuffman.generarCodigoHuffman(jugadaHuffman.getNodoArbol2(),"");
        admJugada.generarArchivoComprimido(JUGADA_ARCHIVO_A_COMPRIMIR, JUGADA_ARCHIVO_A_COMPRIMIR+".compress");
        admJugada.descomprimir(JUGADA_ARCHIVO_A_COMPRIMIR+".compress", JUGADA_ARCHIVO_DESCOMPRIMIR);

        // Archivos DESCOMPRIMIDOS en QUINIELA
        try {
            quinielaManiana.cargarSorteo(SORTEO_ARCHIVO_DESCOMPRIMIR);
            quinielaManiana.agregarTicket(JUGADA_ARCHIVO_DESCOMPRIMIR);
            quinielaManiana.verificarTickets();
            quinielaManiana.calcularGananciaJornada();

            // quinielaNoche.cargarSorteo(SORTEO_ARCHIVO_DESCOMPRIMIR);
            // quinielaNoche.agregarTicket(JUGADA_ARCHIVO_DESCOMPRIMIR);
            // quinielaNoche.verificarTickets();
            // quinielaNoche.calcularGananciaJornada();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

