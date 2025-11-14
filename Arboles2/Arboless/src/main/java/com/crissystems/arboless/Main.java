package com.example.avl;

public class AVLTree {
    public static class Node {
        int val, height;
        Node left, right;
        Node(int v){ val=v; height=1; }
    }

    private Node root;
    public Node getRoot(){ return root; }

    private int height(Node n){ return n==null?0:n.height; }
    private int balance(Node n){ return n==null?0:height(n.left)-height(n.right); }
    private Node rightRotate(Node y){
        Node x = y.left;
        Node T2 = x.right;
        x.right = y;
        y.left = T2;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.