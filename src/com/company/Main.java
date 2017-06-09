package com.company;


class Node {
    public int elem;
    public Node next;

    public Node(int elem) {
        this.elem = elem;
    }
}

class Queue {
    private Node first;
    private Node last;

    public Queue() {
        first = new Node(-1);
        last = first;
    }

    public void insert(int x){
        Node P = new Node(x);
        last.next = P;
        last = P;
    }

    public int top (){
        return first.next.elem;
    }

    public void delete(){
        first = first.next;
    }

    public boolean isEmpty(){
        return first==last;
    }
}

class Stack {
    private Node top;

    public Stack (){
        top = null;
    }

    public void push (int x){
        Node P = new Node(x);
        P.next = top;
        top = P;
    }

    public int top() {
        return top.elem;
    }

    public int pop() {
        int x = top.elem;
        top = top.next;
        return x;
    }

    public boolean isEmpty() {
        return top==null;
    }
}

class Vertex {
    private char label;
    private boolean wasVisited;

    public Vertex(char label) {
        this.label = label;
        wasVisited = false;
    }

    public char getLabel() {
        return label;
    }

    public boolean wasVisited() {
        return wasVisited;
    }

    public void setLabel(char label) {
        this.label = label;
    }

    public void setWasVisitedAsTrue() {
        this.wasVisited = true;
    }
}

class Graph {
    private final int maxVerts=20;
    private Vertex vertexList[];
    private int adjMat[][];
    private int nVerts;

    public Graph() {
        vertexList = new Vertex[maxVerts];
        adjMat = new int[maxVerts][maxVerts];
        nVerts = 0;

        for (int i=0; i<maxVerts; i++)
            for (int j=0; j<maxVerts; j++)
                adjMat[i][j]=0;
    }

    public void addVertex(char label){
        vertexList[nVerts++] = new Vertex(label);
    }

    public void addEdge2dir(int start, int end){
        adjMat[start][end] = 1;
        adjMat[end][start] = 1;
    }

    public void addEdge1dir(int start, int end) {
        adjMat[start][end] = 1;
    }

    public void displayVertex(int v){
        System.out.print(vertexList[v].getLabel() + " ");
    }

    public void bfs(int s){
        vertexList[s].setWasVisitedAsTrue();
        displayVertex(s);

        Queue Q = new Queue();

        Q.insert(s);
        int v;

        while (!Q.isEmpty()) {
            while ((v=getAdjUnvisitedVertex(Q.top()))!=-1){
                vertexList[v].setWasVisitedAsTrue();
                displayVertex(v);
                Q.insert(v);
            }

            Q.delete();
        }
    }

    public int getAdjUnvisitedVertex (int v){
        for (int i=0; i<nVerts; i++) if (adjMat[v][i] == 1 && !vertexList[i].wasVisited()) return i;
        return -1;
    }

    public void dfs() {
        vertexList[0].setWasVisitedAsTrue();
        displayVertex(0);

        Stack S = new Stack();
        S.push(0);

        while (!S.isEmpty()){
            int v = getAdjUnvisitedVertex(S.top());

            if (v==-1) S.pop();
            else {
                vertexList[v].setWasVisitedAsTrue();
                displayVertex(v);
                S.push(v);
            }
        }
    }

    public void mst(){
        vertexList[0].setWasVisitedAsTrue();

        Stack S = new Stack();
        S.push(0);

        while (!S.isEmpty()){
            int u = S.top();
            int v = getAdjUnvisitedVertex(u);
            if (v == -1) S.pop();
            else {
                vertexList[v].setWasVisitedAsTrue();
                S.push(v);

                displayVertex(u);
                displayVertex(v);
                System.out.print(", ");
            }
        }
    }

    public boolean reachability(int u, int s) {
        vertexList[u].setWasVisitedAsTrue();

        Stack S = new Stack();
        S.push(u);

        while (!S.isEmpty()){
            int v = getAdjUnvisitedVertex(S.top());

            if (v==-1) S.pop();
            else {
                vertexList[v].setWasVisitedAsTrue();
                if (s==v) return true;
                S.push(v);
            }
        }
        return false;
    }
}

public class Main {

    public static void main(String[] args) {
        Graph theGraph = new Graph();
        theGraph.addVertex('A'); // 0 (początek wyszukiwania)
        theGraph.addVertex('B'); // 1
        theGraph.addVertex('C'); // 2
        theGraph.addVertex('D'); // 3
        theGraph.addVertex('E'); // 4

        theGraph.addEdge2dir(0,1);
        theGraph.addEdge2dir(1,2);
        theGraph.addEdge1dir(0,3);
        theGraph.addEdge2dir(3,4);

        System.out.println(theGraph.reachability(2,4));

        System.out.print("Odwiedzone wierzcholki: ");
        theGraph.mst(); // wyszukiwanie "wszerz" startując od 0
        System.out.println();

    }
}
