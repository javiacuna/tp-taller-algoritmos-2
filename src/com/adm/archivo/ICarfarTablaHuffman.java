package com.adm.archivo;

import java.util.List;

import com.huffman.nodo.NodoArbol;

public interface ICarfarTablaHuffman {
	
	public void cargarTablaHuffman(String nombreArchivo);
	
	public List<NodoArbol> leerTablaHuffman();
	
	public void generarArchivoComprimido(String nomArchivo,String nomArchivoDestino);
	
	public void descomprimir(String nomArchivo,String nomArchivoDestino);

}
