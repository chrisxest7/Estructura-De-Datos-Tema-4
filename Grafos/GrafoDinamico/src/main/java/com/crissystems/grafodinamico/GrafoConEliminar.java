package com.crissystems.grafodinamico;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

public class GrafoConEliminar extends Application {

    class Nodo {
        String id; double x,y;
        Nodo(String id,double x,double y){this.id=id;this.x=x;this.y=y;}
    }
    class Arista { Nodo a,b; Arista(Nodo a,Nodo b){this.a=a;this.b=b;} }

    private final ArrayList<Nodo> nodos = new ArrayList<>();
    private final ArrayList<Arista> aristas = new ArrayList<>();
    private Canvas canvas;
    private GraphicsContext gc;
    private int contador = 0;
    private Nodo seleccion = null;

    @Override
    public void start(Stage stage) {
        canvas = new Canvas(800,520);
        gc = canvas.getGraphicsContext2D();

        Button btnLimpiar = new Button("Limpiar");
        Button btnEliminarNodo = new Button("Eliminar nodo seleccionado");
        Button btnEliminarAristas = new Button("Eliminar aristas seleccionadas");

        Label info = new Label("Clic: crear nodo. Selecciona para crear arista. Clic derecho: eliminar.");

        HBox top = new HBox(8, btnLimpiar, btnEliminarNodo, btnEliminarAristas, info);
        top.setStyle("-fx-padding:8");

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                Nodo n = nodoEn(e.getX(), e.getY());
                if (n == null) {
                    Nodo nn = new Nodo("N" + (contador++), e.getX(), e.getY());
                    nodos.add(nn);
                    seleccion = null;
                } else {
                    if (seleccion == null) seleccion = n;
                    else if (seleccion != n) {
                        aristas.add(new Arista(seleccion, n));
                        seleccion = null;
                    } else seleccion = null;
                }
            } else if (e.getButton() == MouseButton.SECONDARY) {
                Nodo n = nodoEn(e.getX(), e.getY());
                if (n != null) {
                    eliminarNodo(n);
                } else {
                    // intentar eliminar arista cercana
                    Arista a = aristaEn(e.getX(), e.getY());
                    if (a != null) aristas.remove(a);
                }
            }
            dibujar();
        });

        btnLimpiar.setOnAction(e -> { nodos.clear(); aristas.clear(); contador=0; seleccion=null; dibujar(); });
        btnEliminarNodo.setOnAction(e -> { if (seleccion!=null) eliminarNodo(seleccion); seleccion=null; dibujar(); });
        btnEliminarAristas.setOnAction(e -> { aristas.clear(); dibujar(); });

        BorderPane root = new BorderPane();
        root.setTop(top);
        root.setCenter(canvas);

        stage.setScene(new Scene(root));
        stage.setTitle("Grafo dinámico con eliminar");
        stage.show();
        dibujar();
    }

    private void eliminarNodo(Nodo n) {
        nodos.remove(n);
        // eliminar aristas que involucren el nodo
        Iterator<Arista> it = aristas.iterator();
        while (it.hasNext()) {
            Arista a = it.next();
            if (a.a == n || a.b == n) it.remove();
        }
    }

    private Nodo nodoEn(double x,double y) {
        for (Nodo n: nodos) if (Math.hypot(n.x-x, n.y-y) <= 18) return n;
        return null;
    }

    private Arista aristaEn(double x,double y) {
        for (Arista a: aristas) {
            double mx = (a.a.x + a.b.x)/2, my = (a.a.y + a.b.y)/2;
            if (Math.hypot(mx - x, my - y) <= 10) return a;
        }
        return null;
    }

    private void dibujar() {
        gc.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        gc.setLineWidth(2);
        gc.setStroke(Color.GRAY);
        for (Arista a: aristas) gc.strokeLine(a.a.x, a.a.y, a.b.x, a.b.y);
        for (Nodo n: nodos) {
            gc.setFill(Color.LIGHTGREEN);
            gc.fillOval(n.x-15, n.y-15, 30, 30);
            gc.setStroke(Color.BLACK);
            gc.strokeOval(n.x-15, n.y-15, 30, 30);
            gc.setFill(Color.BLACK);
            gc.fillText(n.id, n.x-8, n.y+5);
        }
        if (seleccion != null) {
            gc.setStroke(Color.ORANGE);
            gc.strokeOval(seleccion.x-18, seleccion.y-18, 36, 36);
        }
    }

    public static void main(String[] args) { launch(); }
}
