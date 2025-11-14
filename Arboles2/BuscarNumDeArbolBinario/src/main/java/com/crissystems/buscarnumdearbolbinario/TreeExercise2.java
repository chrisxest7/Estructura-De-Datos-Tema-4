package com.crissystems.buscarnumdearbolbinario;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TreeExercise2 extends Application {

    static class Nodo {
        int valor;
        Nodo izquierda, derecha;

        Nodo(int valor) { this.valor = valor; }
    }

    Nodo raiz;

    Nodo insertar(Nodo actual, int valor) {
        if (actual == null) return new Nodo(valor);
        if (valor < actual.valor) actual.izquierda = insertar(actual.izquierda, valor);
        else if (valor > actual.valor) actual.derecha = insertar(actual.derecha, valor);
        return actual;
    }

    boolean buscar(Nodo actual, int valor) {
        if (actual == null) return false;
        if (actual.valor == valor) return true;
        return valor < actual.valor
                ? buscar(actual.izquierda, valor)
                : buscar(actual.derecha, valor);
    }

    @Override
    public void start(Stage stage) {
        TextField campoInsertar = new TextField();
        campoInsertar.setPromptText("Número a insertar");

        TextField campoBuscar = new TextField();
        campoBuscar.setPromptText("Número a buscar");

        TextArea salida = new TextArea();

        Button btnInsertar = new Button("Insertar");
        Button btnBuscar = new Button("Buscar");

        btnInsertar.setOnAction(e -> {
            try {
                int val = Integer.parseInt(campoInsertar.getText());
                raiz = insertar(raiz, val);
                salida.appendText("Insertado: " + val + "\n");
                campoInsertar.clear();
            } catch (Exception ex) {
                salida.appendText("Introduce un número válido\n");
            }
        });

        btnBuscar.setOnAction(e -> {
            try {
                int val = Integer.parseInt(campoBuscar.getText());
                boolean encontrado = buscar(raiz, val);
                salida.appendText(encontrado ? "Encontrado: " + val + "\n" : "No encontrado\n");
            } catch (Exception ex) {
                salida.appendText("Error al buscar\n");
            }
        });

        VBox root = new VBox(10, campoInsertar, btnInsertar, campoBuscar, btnBuscar, salida);
        stage.setScene(new Scene(root, 400, 300));
        stage.setTitle("Búsqueda en Árbol");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}