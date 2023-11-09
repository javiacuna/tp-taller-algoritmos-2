package com.adm.archivo;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.huffman.nodo.NodoArbol;

public class AdmArchivo implements ICarfarTablaHuffman {

	private List<NodoArbol> nodoArboles;

	private HashMap<Character, String> tablaHasHuffmanCodigos;
	private HashMap<Character, Integer> tablaHuffman;
	private HashMap<String, Character> tablaHasInvHuffmanCodigos;

	@Override
	public void cargarTablaHuffman(String nombreArchivo) {

		nodoArboles = new ArrayList<NodoArbol>();
		tablaHasHuffmanCodigos = new HashMap<Character, String>();
		tablaHuffman = new HashMap<Character, Integer>();
		System.out.println("Cargar archivo: " + nombreArchivo);
		try {
			RandomAccessFile file = new RandomAccessFile(nombreArchivo, "r");
			char dato;
			int entero;
			long cont = 0;
			long tamano = file.length();
			System.out.print("tamaño: ");
			System.out.println(tamano);
			while (cont < tamano) {
				file.seek(cont);
				dato = (char) file.readByte();
				if (!tablaHuffman.containsKey(dato)) {
					tablaHuffman.put(dato, 1);
					tablaHasHuffmanCodigos.put(dato, "");
				} else
					tablaHuffman.put(dato, tablaHuffman.get(dato) + 1);
				cont++;
			}
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<NodoArbol> leerTablaHuffman() {
		return nodoArboles;
	}

	@Override
	public void generarArchivoComprimido(String nomArchivo, String nomArchivoDestino) {
		String strBuffer = "";
		String strBuffertmp = "";
		File arch = new File(nomArchivoDestino);
		if (arch.delete())
			System.out.println("archivo borrado");
		try {
			RandomAccessFile archivoOrigen = new RandomAccessFile(nomArchivo, "r");
			RandomAccessFile archivoDestino = new RandomAccessFile(nomArchivoDestino, "rw");
			char dato;
			long cont = 0;
			long tamano = archivoOrigen.length();
			while (cont < tamano) {
				archivoOrigen.seek(cont);
				dato = (char) archivoOrigen.readByte();
				strBuffer = strBuffer + this.tablaHasHuffmanCodigos.get(dato);
				strBuffertmp = strBuffertmp + " " + this.tablaHasHuffmanCodigos.get(dato);
				strBuffer = procesarbuffer(strBuffer, archivoDestino);
				cont++;
			}
			System.out.println(strBuffertmp);
			archivoOrigen.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String procesarbuffer(String strBuff, RandomAccessFile archivo) throws IOException {
		String Auxstr = strBuff, strIingByte = "";
		while (Auxstr.length() >= 8) {
			strIingByte = Auxstr.substring(0, 8);
			Auxstr = Auxstr.substring(8, Auxstr.length());
			archivo.writeByte(stringByteToByte(strIingByte));
		}
		return Auxstr;
	}

	private byte stringByteToByte(String strToByte) {
		System.out.println("strToByte: "+strToByte);
		byte Byteresult = 0;
		int Intresult = 0;

		if (strToByte.length() > 0)
			if (Integer.parseInt(strToByte.substring(0, 1)) > 0)
				Intresult = Intresult + 128;
		if (strToByte.length() > 1)
			if (Integer.parseInt(strToByte.substring(1, 2)) > 0)
				Intresult = Intresult + 64;
		if (strToByte.length() > 2)
			if (Integer.parseInt(strToByte.substring(2, 3)) > 0)
				Intresult = Intresult + 32;
		if (strToByte.length() > 3)
			if (Integer.parseInt(strToByte.substring(3, 4)) > 0)
				Intresult = Intresult + 16;
		if (strToByte.length() > 4)
			if (Integer.parseInt(strToByte.substring(4, 5)) > 0)
				Intresult = Intresult + 8;
		if (strToByte.length() > 5)
			if (Integer.parseInt(strToByte.substring(5, 6)) > 0)
				Intresult = Intresult + 4;
		if (strToByte.length() > 6)
			if (Integer.parseInt(strToByte.substring(6, 7)) > 0)
				Intresult = Intresult + 2;
		if (strToByte.length() > 7)
			if (Integer.parseInt(strToByte.substring(7, 8)) > 0)
				Intresult = Intresult + 1;
		Byteresult = (byte) Intresult;
		return Byteresult;
	}
	
	public HashMap<Character, String> getTablaHasHuffmanCodigos() {
		return this.tablaHasHuffmanCodigos;
	}

	public void setTablaHasHuffmanCodigos(HashMap<Character, String> tablaHasHuffmanCodigos) {
		this.tablaHasHuffmanCodigos = tablaHasHuffmanCodigos;
	}

	public HashMap<Character, Integer> getTablaHuffman() {
		return tablaHuffman;
	}

	public void setTablaHuffman(HashMap<Character, Integer> tablaHuffman) {
		this.tablaHuffman = tablaHuffman;
	}

	@Override
	public void descomprimir(String nomArchivo, String nomArchivoDestino) {
		File arch = new File(nomArchivoDestino);
		String codigo="";
		if (arch.delete())
			System.out.println("archivo borrado");
		invertirTablatHuffman();
		try {
			RandomAccessFile archivoOrigen = new RandomAccessFile(nomArchivo, "r");
			RandomAccessFile archivoDestino = new RandomAccessFile(nomArchivoDestino, "rw");
			String strCharacter="";
			long cont = 0;
			long tamano = archivoOrigen.length();
			while (cont < tamano) {
				archivoOrigen.seek(cont);
				strCharacter=strCharacter+completarByte(Integer.toBinaryString((char) archivoOrigen.readByte() & 0xff));
				cont++;
			}
			for(char c: strCharacter.toCharArray()) {
				if(!this.tablaHasInvHuffmanCodigos.containsKey(codigo))
					codigo=codigo+String.valueOf(c);
				else {
					archivoDestino.writeByte((char)this.tablaHasInvHuffmanCodigos.get(codigo));
					codigo=String.valueOf(c);
				}	
			}
			archivoOrigen.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String completarByte(String strByte) {
		String strIni="";
		for(int i=strByte.length();i<8;i++) {
			strIni=strIni+"0";
		}
		return (strIni!=null && strIni.length()>0)?strIni+strByte:strByte;
	}
	
	private void invertirTablatHuffman() {
		this.tablaHasInvHuffmanCodigos=new HashMap<String, Character>();
		if (!this.tablaHasHuffmanCodigos.isEmpty()) {
			for (Character key : this.tablaHasHuffmanCodigos.keySet()) {
				this.tablaHasInvHuffmanCodigos.put(this.tablaHasHuffmanCodigos.get(key), key);
			}
		}
	}
}
