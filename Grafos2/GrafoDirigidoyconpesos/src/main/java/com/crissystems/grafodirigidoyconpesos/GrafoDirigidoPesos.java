package com.crissystems.grafodirigidoyconpesos;

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

public class GrafoDirigidoPesos extends Application {
    class Nodo { String id; double x,y; Nodo(String id,double x,double y){this.id=id;this.x=x;this.y=y;} }
    private Canvas canvas;
    private GraphicsContext gc;
    private final ArrayList<Nodo> nodos = new ArrayList<>();
    private final ArrayList<Ar> aristas = new ArrayList<>();
    private final Map<Nodo,Integer> idx = new HashMap<>();
    private int cont=0;

    class Ar { int from,to; double w; Ar(int f,int t,double w){this.from=f;this.to=t;this.w=w;} }

    @Override
    public void start(Stage stage) {
        canvas = new Canvas(800,500); gc = canvas.getGraphicsContext2D();
        Label lbl = new Label("Crear nodos con clic. Selecciona 2 para arista dirigida. Después ingresa peso.");
        TextField txtPeso = new TextField(); txtPeso.setPromptText("Peso (ej: 3.5)");
        Button btnLimpiar = new Button("Limpiar");

        HBox h = new HBox(8, new Label("Peso:"), txtPeso, btnLimpiar);
        VBox top = new VBox(6, lbl, h);
        top.setStyle("-fx-padding:8");

        Nodo[] sel = new Nodo[1];
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
            Nodo n = nodoAt(e.getX(), e.getY());
            if (n==null) { Nodo nn = new Nodo("N"+(cont++), e.getX(), e.getY()); idx.put(nn, nodos.size()); nodos.add(nn); draw(); }
            else {
                if (sel[0]==null) { sel[0]=n; lbl.setText("Origen: "+n.id); }
                else { if (sel[0]!=n) {
                    double w = 1.0;
                    try { w = Double.parseDouble(txtPeso.getText()); } catch (Exception ex){}
                    aristas.add(new Ar(idx.get(sel[0]), idx.get(n), w));
                    sel[0]=null; lbl.setText("Arista dirigida agregada");
                    draw();
                } else sel[0]=null;
                }
            }
        });

        btnLimpiar.setOnAction(e->{nodos.clear(); aristas.clear(); idx.clear(); cont=0; draw();});

        BorderPane root = new BorderPane();
        root.setTop(top); root.setCenter(canvas);
        stage.setScene(new Scene(root)); stage.setTitle("Grafo Dirigido con Pesos");
        stage.show(); draw();
    }

    private Nodo nodoAt(double x,double y){
        for (Nodo n: nodos) if (Math.hypot(n.x-x,n.y-y)<=18) return n;
        return null;
    }

    private void draw() {
        gc.clearRect(0,0,canvas.getWidth(), canvas.getHeight());
        gc.setLineWidth(2);
        for (Ar a: aristas) {
            Nodo A = nodos.get(a.from), B = nodos.get(a.to);
            drawArrow(A.x, A.y, B.x, B.y);
            // peso en medio
            double mx = (A.x+B.x)/2, my=(A.y+B.y)/2;
            gc.setFill(Color.BLACK); gc.fillText(String.format("%.2f", a.w), mx+6, my+6);
        }
        for (Nodo n: nodos) {
            gc.setFill(Color.PEACHPUFF); gc.fillOval(n.x-15,n.y-15,30,30);
            gc.setStroke(Color.BLACK); gc.strokeOval(n.x-15,n.y-15,30,30);
            gc.setFill(Color.BLACK); gc.fillText(n.id, n.x-8, n.y+5);
        }
    }

    private void drawArrow(double x1,double y1,double x2,double y2){
        gc.setStroke(Color.GRAY);
        gc.strokeLine(x1,y1,x2,y2);
        // arrow head
        double ang = Math.atan2(y2-y1, x2-x1);
        double len = 10;
        double ang1 = ang - Math.PI/8;
        double ang2 = ang + Math.PI/8;
        gc.strokeLine(x2, y2, x2 - len*Math.cos(ang1), y2 - len*Math.sin(ang1));
        gc.strokeLine(x2, y2, x2 - len*Math.cos(ang2), y2 - len*Math.sin(ang2));
    }

    public static void main(String[] args) { launch(); }
}
