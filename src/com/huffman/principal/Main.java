package com.huffman.principal;

import com.adm.archivo.AdmArchivo;
import com.huffman.arbol.ArbolHuffman;

import java.io.IOException;

public class Main {

    private static ArbolHuffman arbolHuffman;
    private static AdmArchivo admArchivo;
    private static String NOMBRE_ARCHIVO = "/Users/jaacuna/tp-taller-algoritmos-2/pruebaCompresion.txt";
    private static String NOMBRE_ARCHIVO_DESCOMPRIMIR = "/Users/jaacuna/tp-taller-algoritmos-2/pruebaDescompresion.txt";
    private static String NOMBRE_ARCHIVO_TABLA = "/Users/jaacuna/tp-taller-algoritmos-2/tabla_huffman.txt";
    private static String NOMBRE_ARCHIVO_A_COMPRIMIR = "/Users/jaacuna/tp-taller-algoritmos-2/sorteo.txt";

    public static void main(String[] args) {

         admArchivo= new AdmArchivo();
         arbolHuffman= new ArbolHuffman();
         admArchivo.cargarTablaHuffman(NOMBRE_ARCHIVO_TABLA);
         arbolHuffman.setTablaHasHuffmanCodigos(admArchivo.getTablaHasHuffmanCodigos());
         arbolHuffman.procesarListaDeArboles(arbolHuffman.crearListaArboles(admArchivo.getTablaHuffman()));
         arbolHuffman.generarCodigoHuffman(arbolHuffman.getNodoArbol2(),"");
         admArchivo.generarArchivoComprimido(NOMBRE_ARCHIVO_A_COMPRIMIR, NOMBRE_ARCHIVO_A_COMPRIMIR+".compress");
         admArchivo.descomprimir(NOMBRE_ARCHIVO_A_COMPRIMIR+".compress", NOMBRE_ARCHIVO_DESCOMPRIMIR);
         }
          /**

        Quiniela quiniela = new Quiniela();

        try {
            quiniela.cargarSorteoDesdeCSV("/Users/jaacuna/tp-taller-algoritmos-2/sorteo.txt");
            quiniela.agregarTicketDesdeCSV("/Users/jaacuna/tp-taller-algoritmos-2/apuestas.csv");
            quiniela.verificarTickets();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } **/
}
