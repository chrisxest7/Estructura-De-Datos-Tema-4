package com.crissystems.grafocondfsvisual;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;

import java.util.*;

public class GrafoDFSVisual extends Application {
    class Nodo { String n; double x,y; Nodo(String s,double x,double y){this.n=s;this.x=x;this.y=y;} }
    private Canvas canvas;
    private GraphicsContext gc;
    private final ArrayList<Nodo> nodos = new ArrayList<>();
    private final ArrayList<int[]> aristas = new ArrayList<>();
    private final Map<Nodo,Integer> idx = new HashMap<>();
    private int cont=0;

    @Override
    public void start(Stage stage) {
        canvas = new Canvas(700,450);
        gc = canvas.getGraphicsContext2D();
        Label lbl = new Label("Clic: crear nodo. Clic en 2 nodos: arista.");
        Button btnDFS = new Button("Ejecutar DFS (N0)");
        Button btnClear = new Button("Limpiar");

        HBox h = new HBox(10, btnDFS, btnClear, lbl);
        h.setStyle("-fx-padding:8");

        Nodo[] sel = new Nodo[1];
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            Nodo f = nodoAt(e.getX(), e.getY());
            if (f==null) {
                Nodo n = new Nodo("N"+(cont++), e.getX(), e.getY());
                idx.put(n, nodos.size()); nodos.add(n); draw();
            } else {
                if (sel[0]==null) sel[0]=f;
                else { if (sel[0]!=f) { aristas.add(new int[]{idx.get(sel[0]), idx.get(f)}); sel[0]=null; draw(); } else sel[0]=null; }
            }
        });

        btnClear.setOnAction(e->{nodos.clear(); aristas.clear(); idx.clear(); cont=0; draw();});
        btnDFS.setOnAction(e->{
            if (nodos.isEmpty()) return;
            int n=nodos.size();
            List<List<Integer>> adj=new ArrayList<>();
            for (int i=0;i<n;i++) adj.add(new ArrayList<>());
            for (int[] ar: aristas) { adj.get(ar[0]).add(ar[1]); adj.get(ar[1]).add(ar[0]); }
            boolean[] vis=new boolean[n];
            List<Integer> order=new ArrayList<>();
            dfs(0, adj, vis, order);
            animate(order);
        });

        BorderPane root = new BorderPane();
        root.setTop(h);
        root.setCenter(canvas);
        stage.setScene(new Scene(root));
        stage.setTitle("Grafo DFS Visual");
        stage.show();
        draw();
    }

    private void dfs(int u, List<List<Integer>> adj, boolean[] vis, List<Integer> order) {
        vis[u]=true; order.add(u);
        for (int v: adj.get(u)) if (!vis[v]) dfs(v, adj, vis, order);
    }

    private Nodo nodoAt(double x,double y) {
        for (Nodo n: nodos) if (Math.hypot(n.x-x, n.y-y)<=18) return n;
        return null;
    }

    private void draw() {
        gc.clearRect(0,0,canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.LIGHTGRAY);
        for (int[] a: aristas) {
            Nodo A=nodos.get(a[0]), B=nodos.get(a[1]);
            gc.strokeLine(A.x,A.y,B.x,B.y);
        }
        for (Nodo n: nodos) {
            gc.setFill(Color.LIGHTGREEN); gc.fillOval(n.x-15,n.y-15,30,30);
            gc.setStroke(Color.BLACK); gc.strokeOval(n.x-15,n.y-15,30,30);
            gc.setFill(Color.BLACK); gc.fillText(n.n, n.x-8, n.y+5);
        }
    }

    private void animate(List<Integer> order) {
        for (int i=0;i<order.size();i++) {
            final int idx = i;
            Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    javafx.application.Platform.runLater(() -> {
                        draw();
                        for (int j=0;j<=idx;j++) {
                            Nodo n = nodos.get(order.get(j));
                            gc.setFill(Color.CORNFLOWERBLUE);
                            gc.fillOval(n.x-15,n.y-15,30,30);
                            gc.setStroke(Color.BLACK); gc.strokeOval(n.x-15,n.y-15,30,30);
                            gc.setFill(Color.BLACK); gc.fillText(n.n, n.x-8, n.y+5);
                        }
                    });
                }
            }, i*600);
        }
    }

    public static void main(String[] args) { launch(); }
}
