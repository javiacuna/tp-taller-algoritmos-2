package com.adm.archivo;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;

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
			System.out.print("tama�o: ");
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
				if(strBuffer.length() >= 8) {
					byte byteValue = stringByteToByte(strBuffer.substring(0, 8));
					archivoDestino.writeByte(byteValue);
					strBuffer = strBuffer.substring(8);
				}
				cont++;
			}
			// Procesar el último carácter
			if(strBuffer.length() > 0) {
				while(strBuffer.length() < 8) {
					strBuffer = strBuffer + "0";
				}
				byte byteValue = stringByteToByte(strBuffer);
				archivoDestino.writeByte(byteValue);
			}
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
			byte byteValue = stringByteToByte(strIingByte);
			System.out.println("byteValue: " + byteValue);
			archivo.writeByte(byteValue);
		}
		return Auxstr;
	}

	public byte stringByteToByte(String strByte) {
		int intValue = Integer.parseInt(strByte, 2);
		byte byteValue = (byte) intValue;
		return byteValue;
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
			int i = 0;
			while(i < strCharacter.length()) {
				codigo = codigo + strCharacter.charAt(i);
				if(this.tablaHasInvHuffmanCodigos.containsKey(codigo)) {
					char caracter = (char)this.tablaHasInvHuffmanCodigos.get(codigo);
					// No escribir el carácter de relleno
					if(i < strCharacter.length() - 1 || caracter != '0') { // Suponiendo que '`' es el carácter de relleno
						archivoDestino.writeByte(caracter);
					}
					codigo = "";
				}
				i++;
			}
			archivoOrigen.close();
			archivoDestino.close();

			// Verificar la longitud del contenido del archivo descomprimido
			String originalContent = new String(Files.readAllBytes(Paths.get(nomArchivo)));
			String decompressedContent = new String(Files.readAllBytes(Paths.get(nomArchivoDestino)));
			if(decompressedContent.length() > originalContent.length()) {
				// Eliminar los caracteres adicionales
				decompressedContent = decompressedContent.substring(0, originalContent.length());
				Files.write(Paths.get(nomArchivoDestino), decompressedContent.getBytes());
			}
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
