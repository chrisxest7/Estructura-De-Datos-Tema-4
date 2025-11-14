package com.crissystems.grafoconbfs;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.scene.input.MouseEvent;

import java.util.*;

public class GrafoBFSVisual extends Application {
    class Nodo {
        String name; double x,y;
        Nodo(String n,double x,double y){name=n;this.x=x;this.y=y;}
    }
    private Canvas canvas;
    private GraphicsContext gc;
    private final ArrayList<Nodo> nodos = new ArrayList<>();
    private final ArrayList<int[]> edges = new ArrayList<>(); // store as indices
    private final Map<Nodo, Integer> index = new HashMap<>();
    private int contador=0;

    @Override
    public void start(Stage stage) {
        canvas = new Canvas(700,450);
        gc = canvas.getGraphicsContext2D();

        Button btnBFS = new Button("Ejecutar BFS (desde N0)");
        Button btnLimpiar = new Button("Limpiar");
        Label lbl = new Label("Clic para crear nodos. Luego clic en 2 nodos para crear arista.");

        HBox controls = new HBox(10, btnBFS, btnLimpiar, lbl);
        controls.setStyle("-fx-padding:8");

        Nodo[] sel = new Nodo[1];

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            Nodo found = nodoEnPos(e.getX(), e.getY());
            if (found==null) {
                Nodo n = new Nodo("N"+(contador++), e.getX(), e.getY());
                index.put(n, nodos.size());
                nodos.add(n);
                draw();
            } else {
                if (sel[0]==null) { sel[0]=found; lbl.setText("Seleccionado: "+found.name); }
                else { if (sel[0]!=found) {
                    edges.add(new int[]{index.get(sel[0]), index.get(found)});
                    sel[0]=null; lbl.setText("Arista agregada"); draw();
                } else { sel[0]=null; lbl.setText("Selecciona otro"); } }
            }
        });

        btnLimpiar.setOnAction(e-> { nodos.clear(); edges.clear(); index.clear(); contador=0; draw(); });

        btnBFS.setOnAction(e -> {
            if (nodos.isEmpty()) return;
            // Build adjacency
            int n = nodos.size();
            List<List<Integer>> adj = new ArrayList<>();
            for (int i=0;i<n;i++) adj.add(new ArrayList<>());
            for (int[] ar: edges) { adj.get(ar[0]).add(ar[1]); adj.get(ar[1]).add(ar[0]); }

            // BFS
            boolean[] visited = new boolean[n];
            Queue<Integer> q = new LinkedList<>();
            List<Integer> order = new ArrayList<>();
            q.add(0); visited[0]=true;
            while(!q.isEmpty()){
                int u=q.poll(); order.add(u);
                for (int v: adj.get(u)) if (!visited[v]) { visited[v]=true; q.add(v); }
            }
            animateOrder(order);
        });

        BorderPane root = new BorderPane();
        root.setTop(controls);
        root.setCenter(canvas);
        stage.setScene(new Scene(root));
        stage.setTitle("Grafo BFS Visual");
        stage.show();
        draw();
    }

    private Nodo nodoEnPos(double x,double y) {
        for (Nodo n: nodos) if (Math.hypot(n.x-x, n.y-y)<=18) return n;
        return null;
    }

    private void draw() {
        gc.clearRect(0,0,canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.GRAY); gc.setLineWidth(2);
        for (int[] e: edges) {
            Nodo a=nodos.get(e[0]); Nodo b=nodos.get(e[1]);
            gc.strokeLine(a.x,a.y,b.x,b.y);
        }
        for (Nodo n: nodos) {
            gc.setFill(Color.LIGHTBLUE); gc.fillOval(n.x-15,n.y-15,30,30);
            gc.setStroke(Color.BLACK); gc.strokeOval(n.x-15,n.y-15,30,30);
            gc.setFill(Color.BLACK); gc.fillText(n.name, n.x-8, n.y+5);
        }
    }

    private void animateOrder(List<Integer> order) {
        if (order.isEmpty()) return;
        Timer timer = new Timer();
        for (int i=0;i<order.size();i++){
            final int idx=i;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    javafx.application.Platform.runLater(() -> {
                        draw();
                        // color visited up to idx
                        for (int j=0;j<=idx;j++) {
                            Nodo n = nodos.get(order.get(j));
                            gc.setFill(Color.ORANGE); gc.fillOval(n.x-15,n.y-15,30,30);
                            gc.setStroke(Color.BLACK); gc.strokeOval(n.x-15,n.y-15,30,30);
                            gc.setFill(Color.BLACK); gc.fillText(n.name, n.x-8, n.y+5);
                        }
                    });
                }
            }, i*600);
        }
    }

    public static void main(String[] args) { launch(); }
}
