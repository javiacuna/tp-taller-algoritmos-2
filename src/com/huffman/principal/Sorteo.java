package com.huffman.principal;

import java.util.List;

public class Sorteo {
    private List<Integer> numerosSorteados;

    public Sorteo(List<Integer> numerosSorteados) {
        this.numerosSorteados = numerosSorteados;
    }

    public List<Integer> getNumerosSorteados() {
        return numerosSorteados;
    }
}
