package com.crissystems.arbolb;

public class BTreeStatic {

    static class BNode {
        int[] keys;
        BNode[] hijos;
        int t; // grado mínimo
        int n; // keys actuales
        boolean leaf;

        BNode(int t, boolean leaf) {
            this.t = t;
            this.leaf = leaf;
            keys = new int[2*t - 1];
            hijos = new BNode[2*t];
            n = 0;
        }
    }

    public static void main(String[] args) {
        BNode root = new BNode(2, true);
        // construir manualmente un pequeño B-tree
        root.keys[0] = 10; root.n = 1;
        root.hijos[0] = new BNode(2, true);
        root.hijos[1] = new BNode(2, true);
        root.hijos[0].keys[0] = 5; root.hijos[0].n = 1;
        root.hijos[1].keys[0] = 20; root.hijos[1].n = 1;

        System.out.println("B-Tree estático (imprimir in-order):");
        imprimir(root);
    }

    static void imprimir(BNode node) {
        if (node == null) return;
        int i;
        for (i = 0; i < node.n; i++) {
            if (!node.leaf) imprimir(node.hijos[i]);
            System.out.print(node.keys[i] + " ");
        }
        if (!node.leaf) imprimir(node.hijos[i]);
    }
}
