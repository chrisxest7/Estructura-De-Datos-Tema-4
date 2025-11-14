package com.crissystems.arbolternarioestaticohijos;

public class TernarioEstatico {

    static class NodoT {
        int v; NodoT a,b,c;
        NodoT(int v){this.v=v;}
    }

    public static void main(String[] args){
        NodoT r = new NodoT(1);
        r.a = new NodoT(2);
        r.b = new NodoT(3);
        r.c = new NodoT(4);
        r.a.a = new NodoT(5);
        r.a.b = new NodoT(6);
        r.b.c = new NodoT(7);

        System.out.println("Impresión pre-order del árbol ternario (estático):");
        preorder(r);
    }

    static void preorder(NodoT n) {
        if (n==null) return;
        System.out.print(n.v + " ");
        preorder(n.a);
        preorder(n.b);
        preorder(n.c);
    }
}
