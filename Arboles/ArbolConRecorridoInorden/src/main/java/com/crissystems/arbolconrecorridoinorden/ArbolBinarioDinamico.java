package com.crissystems.arbolconrecorridoinorden;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class ArbolBinarioDinamico extends Application {
    class Nodo {
        int val; Nodo iz, de;
        double x,y;
        Nodo(int v){val=v;}
    }

    Nodo raiz = null;
    Canvas canvas;
    GraphicsContext gc;
    List<Integer> inorderList = new ArrayList<>();

    @Override
    public void start(Stage stage) {
        canvas = new Canvas(900,420); gc = canvas.getGraphicsContext2D();

        TextField txt = new TextField(); txt.setPromptText("Valor entero");
        Button btnInsert = new Button("Insertar");
        ListView<String> lista = new ListView<>();

        btnInsert.setOnAction(e -> {
            try {
                int v = Integer.parseInt(txt.getText().trim());
                raiz = insertar(raiz, v);
                posiciones(raiz, canvas.getWidth()/2, 50, canvas.getWidth()/4);
                inorderList.clear();
                inorder(raiz);
                lista.getItems().clear();
                for (int val: inorderList) lista.getItems().add(String.valueOf(val));
                dibujar();
                txt.clear();
            } catch (Exception ex) { /* ignore */ }
        });

        HBox controls = new HBox(8, txt, btnInsert);
        controls.setPadding(new Insets(8));
        BorderPane root = new BorderPane();
        root.setTop(controls);
        root.setCenter(canvas);
        root.setRight(new VBox(new Label("InOrder:"), lista));

        stage.setScene(new Scene(root));
        stage.setTitle("Árbol binario dinámico - InOrder en tiempo real");
        stage.show();
        dibujar();
    }

    private Nodo insertar(Nodo r,int v) {
        if (r==null) return new Nodo(v);
        if (v < r.val) r.iz = insertar(r.iz, v);
        else r.de = insertar(r.de, v);
        return r;
    }

    private void inorder(Nodo r) {
        if (r==null) return;
        inorder(r.iz);
        inorderList.add(r.val);
        inorder(r.de);
    }

    private void posiciones(Nodo r, double x, double y, double offset) {
        if (r==null) return;
        r.x = x; r.y = y;
        posiciones(r.iz, x - offset, y + 70, offset/1.8);
        posiciones(r.de, x + offset, y + 70, offset/1.8);
    }

    private void dibujar() {
        gc.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        gc.setLineWidth(2);
        dibujarNodo(raiz);
    }

    private void dibujarNodo(Nodo r) {
        if (r==null) return;
        if (r.iz != null) {
            gc.setStroke(Color.GRAY);
            gc.strokeLine(r.x, r.y, r.iz.x, r.iz.y);
        }
        if (r.de != null) {
            gc.setStroke(Color.GRAY);
            gc.strokeLine(r.x, r.y, r.de.x, r.de.y);
        }
        gc.setFill(Color.BEIGE);
        gc.fillOval(r.x-18, r.y-18, 36, 36);
        gc.setStroke(Color.BLACK);
        gc.strokeOval(r.x-18, r.y-18, 36, 36);
        gc.setFill(Color.BLACK);
        gc.fillText(String.valueOf(r.val), r.x-6, r.y+4);
        dibujarNodo(r.iz);
        dibujarNodo(r.de);
    }

    public static void main(String[] args) { launch(); }
}
