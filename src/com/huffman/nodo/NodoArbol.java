package com.huffman.nodo;

public class NodoArbol {

	char clave;
	int frecuencia;
	NodoArbol izquierda, derecha;

	public NodoArbol(char car, int frec) {
		clave = car;
		frecuencia = frec;
		izquierda = derecha = null;
	}

	public char getClave() {
		return clave;
	}

	public void setClave(char clave) {
		this.clave = clave;
	}

	public int getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(int frecuencia) {
		this.frecuencia = frecuencia;
	}

	public NodoArbol getIzquierda() {
		return izquierda;
	}

	public void setIzquierda(NodoArbol izquierda) {
		this.izquierda = izquierda;
	}

	public NodoArbol getDerecha() {
		return derecha;
	}

	public void setDerecha(NodoArbol derecha) {
		this.derecha = derecha;
	} 
	
	
}
