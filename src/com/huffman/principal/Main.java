package com.huffman.principal;

import com.adm.archivo.AdmArchivo;
import com.huffman.arbol.ArbolHuffman;

public class Main {
	
	private static ArbolHuffman arbolHuffman;
	private static AdmArchivo admArchivo;
	private static String NOMBRE_ARCHIVO="F:\\My Carrier\\Algoritmo Y Estructuras Datos II\\modulo1\\practico\\grafos\\tp4-huffman\\pruebaCompresion.txt";
	private static String NOMBRE_ARCHIVO_DESCOMPRIMIR="F:\\My Carrier\\Algoritmo Y Estructuras Datos II\\modulo1\\practico\\grafos\\tp4-huffman\\pruebaDescompresion.txt";
	
	public static void main(String[] args) {
		
		admArchivo= new AdmArchivo();
		arbolHuffman= new ArbolHuffman();
		admArchivo.cargarTablaHuffman(NOMBRE_ARCHIVO);
		arbolHuffman.setTablaHasHuffmanCodigos(admArchivo.getTablaHasHuffmanCodigos());
		arbolHuffman.procesarListaDeArboles(arbolHuffman.crearListaArboles(admArchivo.getTablaHuffman()));
		arbolHuffman.generarCodigoHuffman(arbolHuffman.getNodoArbol2(),"");
		admArchivo.generarArchivoComprimido(NOMBRE_ARCHIVO, NOMBRE_ARCHIVO+".compress");
		admArchivo.descomprimir(NOMBRE_ARCHIVO+".compress", "NOMBRE_ARCHIVO_DESCOMPRIMIR");
	}
}
