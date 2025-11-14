package com.crissystems.arboles;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TreeExercise1 extends Application {

    static class Nodo {
        int valor;
        Nodo izquierda, derecha;

        Nodo(int valor) { this.valor = valor; }
    }

    Nodo raiz;

    void insertar(int valor) {
        raiz = insertarRec(raiz, valor);
    }

    Nodo insertarRec(Nodo actual, int valor) {
        if (actual == null) return new Nodo(valor);
        if (valor < actual.valor)
            actual.izquierda = insertarRec(actual.izquierda, valor);
        else if (valor > actual.valor)
            actual.derecha = insertarRec(actual.derecha, valor);
        return actual;
    }

    void inOrden(Nodo nodo, StringBuilder sb) {
        if (nodo != null) {
            inOrden(nodo.izquierda, sb);
            sb.append(nodo.valor).append(" ");
            inOrden(nodo.derecha, sb);
        }
    }

    @Override
    public void start(Stage stage) {
        TextField input = new TextField();
        input.setPromptText("Número a insertar");
        TextArea salida = new TextArea();
        Button insertarBtn = new Button("Insertar y mostrar inorden");

        insertarBtn.setOnAction(e -> {
            try {
                int val = Integer.parseInt(input.getText());
                insertar(val);
                StringBuilder sb = new StringBuilder();
                inOrden(raiz, sb);
                salida.setText("Recorrido inorden: " + sb.toString());
                input.clear();
            } catch (Exception ex) {
                salida.setText("Error: introduce un número válido");
            }
        });

        VBox root = new VBox(10, input, insertarBtn, salida);
        Scene scene = new Scene(root, 400, 250);
        stage.setScene(scene);
        stage.setTitle("Árbol Binario Básico");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}