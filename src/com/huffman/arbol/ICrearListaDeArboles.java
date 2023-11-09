package com.huffman.arbol;

import java.util.HashMap;
import java.util.List;

import com.huffman.nodo.NodoArbol;

public interface ICrearListaDeArboles {
	
	public List<NodoArbol> crearListaArboles(HashMap<Character,Integer> mapTablaHuffman);
	
	public void procesarListaDeArboles(List<NodoArbol> listaArbol);
	
	public void generarCodigoHuffman(NodoArbol nodoArbol,String codigo);

}
