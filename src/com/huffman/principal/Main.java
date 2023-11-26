package com.huffman.principal;

import com.adm.archivo.AdmArchivo;
import com.huffman.arbol.ArbolHuffman;

import java.io.IOException;

public class Main {

    private static ArbolHuffman arbolHuffman;
    private static AdmArchivo admArchivo;
    private static String NOMBRE_ARCHIVO = "/Users/jaacuna/tp-taller-algoritmos-2/pruebaCompresion.txt";
    private static String NOMBRE_ARCHIVO_DESCOMPRIMIR = "/Users/jaacuna/tp-taller-algoritmos-2/pruebaDescompresion.txt";

    public static void main(String[] args) {

         /**
         admArchivo= new AdmArchivo();
         arbolHuffman= new ArbolHuffman();
         admArchivo.cargarTablaHuffman(NOMBRE_ARCHIVO);
         arbolHuffman.setTablaHasHuffmanCodigos(admArchivo.getTablaHasHuffmanCodigos());
         arbolHuffman.procesarListaDeArboles(arbolHuffman.crearListaArboles(admArchivo.getTablaHuffman()));
         arbolHuffman.generarCodigoHuffman(arbolHuffman.getNodoArbol2(),"");
         admArchivo.generarArchivoComprimido(NOMBRE_ARCHIVO, NOMBRE_ARCHIVO+".compress");
         admArchivo.descomprimir(NOMBRE_ARCHIVO+".compress", NOMBRE_ARCHIVO_DESCOMPRIMIR);
         }
         **/

        Quiniela quiniela = new Quiniela();

        try {
            quiniela.cargarSorteoDesdeCSV("/Users/jaacuna/tp-taller-algoritmos-2/sorteo.csv");
            quiniela.agregarTicketDesdeCSV("/Users/jaacuna/tp-taller-algoritmos-2/apuestas.csv");
            quiniela.verificarTickets();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
