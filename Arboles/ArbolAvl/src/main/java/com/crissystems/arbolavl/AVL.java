package com.crissystems.arbolavl;

class NodoAVL {
    int valor, altura;
    NodoAVL izq, der;

    NodoAVL(int v) {
        valor = v;
        altura = 1;
    }
}

public class AVL {
    NodoAVL raiz;

    int altura(NodoAVL n) {
        return n == null ? 0 : n.altura;
    }

    int getBalance(NodoAVL n) {
        return n == null ? 0 : altura(n.izq) - altura(n.der);
    }

    NodoAVL derecha(NodoAVL y) {
        NodoAVL x = y.izq;
        y.izq = x.der;
        x.der = y;
        y.altura = Math.max(altura(y.izq), altura(y.der)) + 1;
        x.altura = Math.max(altura(x.izq), altura(x.der)) + 1;
        return x;
    }

    NodoAVL izquierda(NodoAVL x) {
        NodoAVL y = x.der;
        x.der = y.izq;
        y.izq = x;
        x.altura = Math.max(altura(x.izq), altura(x.der)) + 1;
        y.altura = Math.max(altura(y.izq), altura(y.der)) + 1;
        return y;
    }

    NodoAVL insertarRec(NodoAVL n, int v) {
        if (n == null) return new NodoAVL(v);

        if (v < n.valor) n.izq = insertarRec(n.izq, v);
        else n.der = insertarRec(n.der, v);

        n.altura = 1 + Math.max(altura(n.izq), altura(n.der));

        int balance = getBalance(n);

        if (balance > 1 && v < n.izq.valor) return derecha(n);
        if (balance < -1 && v > n.der.valor) return izquierda(n);
        if (balance > 1 && v > n.izq.valor) {
            n.izq = izquierda(n.izq);
            return derecha(n);
        }
        if (balance < -1 && v < n.der.valor) {
            n.der = derecha(n.der);
            return izquierda(n);
        }
        return n;
    }

    void insertar(int v) {
        raiz = insertarRec(raiz, v);
    }

    void inorder(NodoAVL n) {
        if (n != null) {
            inorder(n.izq);
            System.out.print(n.valor + " ");
            inorder(n.der);
        }
    }

    public static void main(String[] args) {
        AVL a = new AVL();
        a.insertar(10);
        a.insertar(20);
        a.insertar(5);
        a.insertar(4);
        a.insertar(15);

        a.inorder(a.raiz);
    }
}
