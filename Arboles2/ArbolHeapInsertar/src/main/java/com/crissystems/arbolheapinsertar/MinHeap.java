package com.crissystems.arbolheapinsertar;

import java.util.ArrayList;

public class MinHeap {
    ArrayList<Integer> heap = new ArrayList<>();

    void insertar(int valor) {
        heap.add(valor);
        int i = heap.size() - 1;

        while (i > 0 && heap.get(i) < heap.get((i - 1) / 2)) {
            int temp = heap.get(i);
            heap.set(i, heap.get((i - 1) / 2));
            heap.set((i - 1) / 2, temp);
            i = (i - 1) / 2;
        }
    }

    int obtenerMin() {
        return heap.get(0);
    }

    public static void main(String[] args) {
        MinHeap h = new MinHeap();
        h.insertar(40);
        h.insertar(20);
        h.insertar(30);
        h.insertar(10);

        System.out.println(h.obtenerMin()); // 10
    }
}
