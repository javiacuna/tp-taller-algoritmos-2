package com.huffman.arbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.huffman.nodo.NodoArbol;

public class ArbolHuffman implements ICrearListaDeArboles {

	private NodoArbol nodoArbol2;
	private HashMap<Character, String> tablaHasHuffmanCodigos;

	@Override
	public List<NodoArbol> crearListaArboles(HashMap<Character, Integer> mapTablaHuffman) {
		List<NodoArbol> nodoArbols = new ArrayList<NodoArbol>();
		int pos = 0;
		for (Character key : mapTablaHuffman.keySet()) {
			if (key > 0) {
				pos++;
				NodoArbol nodo = new NodoArbol( key, mapTablaHuffman.get(key));
				nodoArbols.add(nodo);
				// System.out.print("Nodo ");
				// System.out.print(pos);
				// System.out.print(" " + key + "(");
				// System.out.print(mapTablaHuffman.get(key));
				// System.out.print("); ");
			}

		}
		return nodoArbols;
	}

	public void procesarListaDeArboles(List<NodoArbol> listaArbol) {
		List<NodoArbol> listaArbolAux = new ArrayList<NodoArbol>();
		if (listaArbol != null && listaArbol.size() > 1) {
			int i = 0;
			for (; i < listaArbol.size(); i = i + 2) {
				NodoArbol nodoArbol = null;
				if (i + 1 < listaArbol.size()) {
					nodoArbol = new NodoArbol('*',
							listaArbol.get(i).getFrecuencia() + listaArbol.get(i + 1).getFrecuencia());
					nodoArbol.setIzquierda(listaArbol.get(i));
					nodoArbol.setDerecha(listaArbol.get(i + 1));
				} else 
					nodoArbol =listaArbol.get(i);
				listaArbolAux.add(nodoArbol);
			}
			procesarListaDeArboles(listaArbolAux);
		} else
			this.nodoArbol2 = listaArbol.get(0);
	}

	@Override
	public void generarCodigoHuffman(NodoArbol nodoArbol, String codigo) {

		if (nodoArbol.getIzquierda() == null && nodoArbol.getDerecha() == null && nodoArbol.getClave() != 0)
			tablaHasHuffmanCodigos.put( nodoArbol.getClave(), codigo);
		if (nodoArbol.getIzquierda() != null)
			generarCodigoHuffman(nodoArbol.getIzquierda(), codigo + 0);
		if (nodoArbol.getDerecha() != null)
			generarCodigoHuffman(nodoArbol.getDerecha(), codigo + 1);
	}


	public void setTablaHasHuffmanCodigos(HashMap<Character, String> tablaHasHuffmanCodigos) {
		this.tablaHasHuffmanCodigos = tablaHasHuffmanCodigos;
	}

	public NodoArbol getNodoArbol2() {
		return nodoArbol2;
	}
	
}
