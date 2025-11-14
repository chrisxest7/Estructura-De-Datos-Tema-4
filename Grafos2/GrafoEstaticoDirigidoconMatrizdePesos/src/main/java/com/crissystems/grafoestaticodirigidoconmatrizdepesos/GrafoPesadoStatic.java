package com.crissystems.grafoestaticodirigidoconmatrizdepesos;

public class GrafoPesadoStatic {
    public static void main(String[] args) {
        // -1 indica sin conexión
        int[][] P = {
                {  0,  4, -1, -1 },
                { -1,  0,  2, -1 },
                { -1, -1,  0,  7 },
                {  3, -1, -1,  0 }
        };

        System.out.println("Matriz de pesos (directed):");
        for (int i=0;i<P.length;i++) {
            for (int j=0;j<P[i].length;j++) {
                System.out.printf("%4d", P[i][j]);
            }
            System.out.println();
        }
    }
}
