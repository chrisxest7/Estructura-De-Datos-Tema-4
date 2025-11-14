package com.crissystems.grafo;

class Arista {
    String a, b;
    Arista(String a, String b){this.a=a;this.b=b;}
}

public class GrafoEstatico6 {
    public static void main(String[] args) {
        Arista[] g = {
                new Arista("A","B"),
                new Arista("A","D"),
                new Arista("B","C")
        };
    }
}
