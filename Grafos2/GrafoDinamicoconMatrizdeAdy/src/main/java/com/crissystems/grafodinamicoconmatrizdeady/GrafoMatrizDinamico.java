package com.crissystems.grafodinamicoconmatrizdeady;

import java.util.Scanner;
import java.util.Arrays;

public class GrafoMatrizDinamico {
    private int[][] M;
    private int capacidad;
    private int n;

    public GrafoMatrizDinamico(int capacidad) {
        this.capacidad = capacidad;
        M = new int[capacidad][capacidad];
        n = 0;
    }

    public int agregarNodo() {
        if (n >= capacidad) {
            System.out.println("Capacidad máxima alcanzada.");
            return -1;
        }
        return n++; // devuelve índice del nodo
    }

    public void agregarArista(int a,int b) {
        if (a<0 || b<0 || a>=n || b>=n) { System.out.println("Índices inválidos."); return; }
        M[a][b] = 1;
        M[b][a] = 1; // no dirigido
    }

    public void imprimir() {
        System.out.println("Matriz de adyacencia (n=" + n + "):");
        for (int i=0;i<n;i++) System.out.println(Arrays.toString(Arrays.copyOf(M[i], n)));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GrafoMatrizDinamico g = new GrafoMatrizDinamico(20);
        boolean quit=false;
        while(!quit) {
            System.out.println("\n1) Agregar nodo\n2) Agregar arista\n3) Imprimir\n4) Salir");
            System.out.print("Opción: ");
            int op = sc.nextInt();
            if (op==1) {
                int idx = g.agregarNodo();
                if (idx>=0) System.out.println("Nodo agregado con índice " + idx);
            } else if (op==2) {
                System.out.print("Índice nodo A: "); int a = sc.nextInt();
                System.out.print("Índice nodo B: "); int b = sc.nextInt();
                g.agregarArista(a,b);
            } else if (op==3) {
                g.imprimir();
            } else quit=true;
        }
        sc.close();
    }
}
