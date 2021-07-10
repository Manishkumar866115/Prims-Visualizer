package com.example.primsvisualizer;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class MST implements Runnable{

    private int graph[][];  // For maintaining paths
    private Point arr[];    // For storing vertices
    private int parent[];   // For storing parents of corressponding vertices
    private int V;          // Number of vertices
    private int row;        // rows of boxes in the used phone
    private int col;        // columns of boxes in the used phone
    Context context;        // For storing context of main activity and to change UI of main act
    TableLayout table;      // temp storage for table of main activity
    private boolean exit = false;  // variable used for stopping the thread;


    MST(int rows, int columns, Context context){

        // parameterized constructor for our class
        // argument passed : rows-> denoting the number of rows in table of main activity
        //                   columns -> denoting the number of columns in table of main activity
        //                   context -> context of main activity
        // Initializes the members of class

        graph = new int[rows+1][columns+1];
        arr = new Point[100];
        parent = new int[100];
        V = 0;
        row = rows + 1;
        col = columns + 1;
        this.context = context;
        table = (TableLayout)( (Activity)context).findViewById(R.id.main_table);
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++)
                graph[i][j] = 0;
        }
    }

    void addVertex(int x,int y){
        // upon clicking it add vertex to the graph
        // x signifies x coordinate y signifies y coordinate

        if( V >= 100)
            return;

        Point temp = new Point(x,y);
        graph[x][y] = 1;
        arr[V] = temp;
        V++;
    }

    private Button getButton(int i,int j){
        // Returns button in the UI corresponing to index i and j
        TableRow row = (TableRow)table.getChildAt(i);
        return (Button)row.getChildAt(j);
    }

    private void changeColorToTeal(Button button, int i,int j){
        // Change color of the button from anycolor to teal

        try{
            Thread.sleep(5);
        }catch (InterruptedException e){
            System.out.println(e);
        }

        if( graph[i][j] > 0) // If color is black then dont change the color
            return;

        button.setBackground( context.getDrawable(R.drawable.teal_rect));
    }

    private void changeColorToPurple(Button button, int i, int j){
        // Change color of the button from anycolor to purple

        try{
            Thread.sleep(5);
        }catch (InterruptedException e){
            System.out.println(e);
        }

        if( graph[i][j] > 0) // If color is black then dont change the color
            return;

        button.setBackground( context.getDrawable(R.drawable.purple_rect));
    }

    private void changeColorToBlack(Button button){
        // Change color of the button from anycolor to black

        try{
            Thread.sleep(5);
        }catch (InterruptedException e){
            System.out.println(e);
        }

        button.setBackground( context.getDrawable(R.drawable.black_rect));
    }

    private int getQuad(int i1, int j1, int i2, int j2){
        // Given 2 points (i1,i2) and (i2,j2) return the quadrant in which point 2 lies wrt 1

        if( i2 <= i1 && j2 > j1)
            return 0;
        else if( i2 < i1 && j2 <= j1)
            return 1;
        else if( i2 >= i1 && j2 < j1)
            return 2;
        else
            return 3;
    }

    private void plotPath( int i1, int j1, int i2, int j2){
        //Plots the path in black color between (i1,j1) and (i2,j2)

        int quad = getQuad(i1,j1,i2,j2);

        graph[i1][j1]++;
        graph[i2][j2]++;

        Button button;

        if( quad == 0){

            while( i2 != i1 && j2 != j1){
                i1--;
                graph[i1][j1]++;
                button = getButton(i1,j1);
                changeColorToBlack(button);
                j1++;
                graph[i1][j1]++;
                button = getButton(i1,j1);
                changeColorToBlack(button);
            }
            while( i2 != i1){
                i1--;
                graph[i1][j1]++;
                button = getButton(i1,j1);
                changeColorToBlack(button);
            }
            while( j2 != j1){
                j1++;
                graph[i1][j1]++;
                button = getButton(i1,j1);
                changeColorToBlack(button);
            }

        }else if( quad == 1){

            while( i2 != i1 && j2 != j1){
                i1--;
                graph[i1][j1]++;
                button = getButton(i1,j1);
                changeColorToBlack(button);
                j1--;
                graph[i1][j1]++;
                button = getButton(i1,j1);
                changeColorToBlack(button);
            }
            while( i2 != i1){
                i1--;
                graph[i1][j1]++;
                button = getButton(i1,j1);
                changeColorToBlack(button);
            }
            while( j2 != j1){
                j1--;
                graph[i1][j1]++;
                button = getButton(i1,j1);
                changeColorToBlack(button);
            }

        }else if( quad == 2){

            while( i2 != i1 && j2 != j1){
                i1++;
                graph[i1][j1]++;
                button = getButton(i1,j1);
                changeColorToBlack(button);
                j1--;
                graph[i1][j1]++;
                button = getButton(i1,j1);
                changeColorToBlack(button);
            }
            while( i2 != i1){
                i1++;
                graph[i1][j1]++;
                button = getButton(i1,j1);
                changeColorToBlack(button);
            }
            while( j2 != j1){
                j1--;
                graph[i1][j1]++;
                button = getButton(i1,j1);
                changeColorToBlack(button);
            }
        }else{
            while( i2 != i1 && j2 != j1){
                i1++;
                graph[i1][j1]++;
                button = getButton(i1,j1);
                changeColorToBlack(button);
                j1++;
                graph[i1][j1]++;
                button = getButton(i1,j1);
                changeColorToBlack(button);
            }
            while( i2 != i1){
                i1++;
                graph[i1][j1]++;
                button = getButton(i1,j1);
                changeColorToBlack(button);
            }
            while( j2 != j1){
                j1++;
                graph[i1][j1]++;
                button = getButton(i1,j1);
                changeColorToBlack(button);
            }
        }
    }

    private void removePath( int i1, int j1, int i2, int j2, int count){
        // removes the path between (i1,j1) and (i2,j2) from UI

        int quad = getQuad(i1,j1,i2,j2);

        Button button;

        if( quad == 0){

            while( i2 != i1 && j2 != j1){
                i1--;
                graph[i1][j1]--;
                button =getButton(i1,j1);
                if( count % 2 == 0)
                    changeColorToTeal(button,i1,j1);
                else
                    changeColorToPurple(button,i1,j1);
                j1++;
                graph[i1][j1]--;
                button =getButton(i1,j1);
                if( count % 2 == 0)
                    changeColorToTeal(button,i1,j1);
                else
                    changeColorToPurple(button,i1,j1);
            }
            while( i2 != i1){
                i1--;
                graph[i1][j1]--;
                button =getButton(i1,j1);
                if( count % 2 == 0)
                    changeColorToTeal(button,i1,j1);
                else
                    changeColorToPurple(button,i1,j1);
            }
            while( j2 != j1){
                j1++;
                graph[i1][j1]--;
                button =getButton(i1,j1);
                if( count % 2 == 0)
                    changeColorToTeal(button,i1,j1);
                else
                    changeColorToPurple(button,i1,j1);
            }

        }else if( quad == 1){

            while( i2 != i1 && j2 != j1){
                i1--;
                graph[i1][j1]--;
                button =getButton(i1,j1);
                if( count % 2 == 0)
                    changeColorToTeal(button,i1,j1);
                else
                    changeColorToPurple(button,i1,j1);
                j1--;
                graph[i1][j1]--;
                button =getButton(i1,j1);
                if( count % 2 == 0)
                    changeColorToTeal(button,i1,j1);
                else
                    changeColorToPurple(button,i1,j1);
            }
            while( i2 != i1){
                i1--;
                graph[i1][j1]--;
                button =getButton(i1,j1);
                if( count % 2 == 0)
                    changeColorToTeal(button,i1,j1);
                else
                    changeColorToPurple(button,i1,j1);
            }
            while( j2 != j1){
                j1--;
                graph[i1][j1]--;
                button =getButton(i1,j1);
                if( count % 2 == 0)
                    changeColorToTeal(button,i1,j1);
                else
                    changeColorToPurple(button,i1,j1);
            }
        }else if( quad == 2){

            while( i2 != i1 && j2 != j1){
                i1++;
                graph[i1][j1]--;
                button =getButton(i1,j1);
                if( count % 2 == 0)
                    changeColorToTeal(button,i1,j1);
                else
                    changeColorToPurple(button,i1,j1);
                j1--;
                graph[i1][j1]--;
                button =getButton(i1,j1);
                if( count % 2 == 0)
                    changeColorToTeal(button,i1,j1);
                else
                    changeColorToPurple(button,i1,j1);
            }
            while( i2 != i1){
                i1++;
                graph[i1][j1]--;
                button =getButton(i1,j1);
                if( count % 2 == 0)
                    changeColorToTeal(button,i1,j1);
                else
                    changeColorToPurple(button,i1,j1);
            }
            while( j2 != j1){
                j1--;
                graph[i1][j1]--;
                button =getButton(i1,j1);
                if( count % 2 == 0)
                    changeColorToTeal(button,i1,j1);
                else
                    changeColorToPurple(button,i1,j1);
            }

        }else{
            while( i2 != i1 && j2 != j1){
                i1++;
                graph[i1][j1]--;
                button =getButton(i1,j1);
                if( count % 2 == 0)
                    changeColorToTeal(button,i1,j1);
                else
                    changeColorToPurple(button,i1,j1);
                j1++;
                graph[i1][j1]--;
                button =getButton(i1,j1);
                if( count % 2 == 0)
                    changeColorToTeal(button,i1,j1);
                else
                    changeColorToPurple(button,i1,j1);
            }
            while( i2 != i1){
                i1++;
                graph[i1][j1]--;
                button =getButton(i1,j1);
                if( count % 2 == 0)
                    changeColorToTeal(button,i1,j1);
                else
                    changeColorToPurple(button,i1,j1);
            }
            while( j2 != j1){
                j1++;
                graph[i1][j1]--;
                button =getButton(i1,j1);
                if( count % 2 == 0)
                    changeColorToTeal(button,i1,j1);
                else
                    changeColorToPurple(button,i1,j1);
            }
        }
    }

    private int distance(int i1,int i2){
        return Math.abs( arr[i1].x - arr[i2].x ) + Math.abs( arr[i1].y - arr[i2].y);
    }

    private int minKey( int dist[], boolean visit[]){
        // returns point to to next selected by the prims algorithm

        int index = -1 , min = Integer.MAX_VALUE;

        for(int i = 0; i < V; i++){
            if( visit[i] == false && dist[i] < min){
                min = dist[i];
                index = i;
            }
        }

        return index;
    }

    private void visitAllVertex( boolean visitGraph[][], int i, int j, int count){
        if( i < 0 || j < 0 || i >= row || j >= col)
            return;
        if( visitGraph[i][j] == true)
            return;

        visitGraph[i][j] = true;

        if(exit == true)
            return;

        Button button = getButton(i,j);
        if( count %2 == 0)
            changeColorToTeal(button,i,j);
        else
            changeColorToPurple(button,i,j);

        visitAllVertex(visitGraph, i - 1, j, count);
        visitAllVertex(visitGraph, i + 1, j, count);
        visitAllVertex(visitGraph, i, j - 1, count);
        visitAllVertex(visitGraph, i, j + 1, count);
    }

    public void run(){
        // Called when thread is created
        // Contains the implementation of Prims

        int dist[] = new int[V];
        boolean visit[] = new boolean[V];
        exit = false;
        for(int i = 0; i < V; i++){
            visit[i] = false;
            dist[i] = Integer.MAX_VALUE;
            parent[i] = Integer.MAX_VALUE;
        }

        dist[0] = 0;
        parent[0] = -1;

        for( int count = 0; count < V; count++){

            int u = minKey(dist,visit);
            visit[u] = true;
            boolean visitGraph[][] = new boolean[row][col];
            visitAllVertex(visitGraph, arr[u].x, arr[u].y, count);

            if( exit == true)
                return;

            for(int i = 0; i < V; i++){
                if( visit[i] == false && distance(u,i) < dist[i]){
                    if( parent[u] != -1)
                        removePath(arr[parent[i]].x , arr[parent[i]].y, arr[i].x, arr[i].y, count);
                    parent[i] = u;
                    plotPath(arr[parent[i]].x , arr[parent[i]].y, arr[i].x, arr[i].y);
                    dist[i] = distance(u,i);
                }
            }
        }
    }

    public void stopThread(){
        // Stops the thread
        exit = true;
    }

    public void reset(){
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                graph[i][j] = 0;
            }
        }
        V = 0;
    }
}
