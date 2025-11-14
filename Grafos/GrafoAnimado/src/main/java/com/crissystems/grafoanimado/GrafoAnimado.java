package com.crissystems.grafoanimado;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class GrafoAnimado extends Application {

    class Nodo {
        String id; double x,y, vx, vy;
        Nodo(String id,double x,double y,double vx,double vy){this.id=id;this.x=x;this.y=y;this.vx=vx;this.vy=vy;}
        void mover(double w,double h){
            x += vx; y += vy;
            if (x < 20 || x > w-20) vx = -vx;
            if (y < 20 || y > h-20) vy = -vy;
        }
    }

    class Ar { Nodo a,b; Ar(Nodo a,Nodo b){this.a=a;this.b=b;} }

    private final ArrayList<Nodo> nodos = new ArrayList<>();
    private final ArrayList<Ar> aristas = new ArrayList<>();
    private Canvas canvas;
    private GraphicsContext gc;
    private int contador = 0;
    private final Random rnd = new Random();

    @Override
    public void start(Stage stage) {
        canvas = new Canvas(900,520);
        gc = canvas.getGraphicsContext2D();
        Label info = new Label("Clic para agregar nodo (se mueven). Clic en dos para arista.");

        BorderPane root = new BorderPane();
        root.setTop(info);
        root.setCenter(canvas);

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            Nodo n = nodoEn(e.getX(), e.getY());
            if (n == null) {
                Nodo nn = new Nodo("N"+(contador++), e.getX(), e.getY(), rnd.nextDouble()*4-2, rnd.nextDouble()*4-2);
                nodos.add(nn);
            }
        });

        // conectar aleatoriamente algunos al inicio
        for (int i=0;i<6;i++) {
            Nodo nn = new Nodo("N"+(contador++), 50 + rnd.nextDouble()*700, 50 + rnd.nextDouble()*400, rnd.nextDouble()*4-2, rnd.nextDouble()*4-2);
            nodos.add(nn);
        }
        // crear aristas aleatorias
        if (nodos.size() > 1) {
            for (int i=0;i<nodos.size()-1;i++) aristas.add(new Ar(nodos.get(i), nodos.get(i+1)));
        }

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                actualizar();
                dibujar();
            }
        };
        timer.start();

        stage.setScene(new Scene(root));
        stage.setTitle("Grafo animado");
        stage.show();
        dibujar();
    }

    private Nodo nodoEn(double x,double y) {
        for (Nodo n: nodos) if (Math.hypot(n.x-x, n.y-y) <= 18) return n;
        return null;
    }

    private void actualizar() {
        double w = canvas.getWidth(), h = canvas.getHeight();
        for (Nodo n: nodos) n.mover(w,h);
    }

    private void dibujar() {
        gc.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.LIGHTGRAY);
        for (Ar a: aristas) gc.strokeLine(a.a.x, a.a.y, a.b.x, a.b.y);
        for (Nodo n: nodos) {
            gc.setFill(Color.LIGHTBLUE);
            gc.fillOval(n.x-12, n.y-12, 24, 24);
            gc.setStroke(Color.BLACK);
            gc.strokeOval(n.x-12, n.y-12, 24, 24);
            gc.setFill(Color.BLACK);
            gc.fillText(n.id, n.x-8, n.y+4);
        }
    }

    public static void main(String[] args) { launch(); }
}
