package com.clue.game.gamelogic;



import org.jgrapht.Graph;


import java.util.*;

import org.jgrapht.GraphPath;
import  org.jgrapht.alg.*;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.Set;

public class Field {
    /*private static final int  [][] fieldLogicMap={
            {0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,1,0,0,0,1,0,0,0},
            {0,0,0,0,1,0,0,0,1,0,0,0},
            {0,0,0,1,1,1,1,1,1,0,0,0},
            {0,0,0,1,0,0,0,1,0,0,0,0},
            {0,0,0,1,0,0,0,1,0,0,0,0},
            {0,1,1,1,0,0,0,1,0,0,0,0},
            {0,0,0,1,1,1,1,1,1,1,1,1},
            {0,0,0,1,1,0,0,0,0,0,0,0},
            {0,0,0,1,1,0,0,0,0,0,0,0},
            {1,1,1,1,1,0,0,0,0,0,0,0}
    };*/

    private static final int  [][] fieldLogicMap={
            {0,0,1,0,1,0,1,0,1,0,0,0},
            {0,0,0,1,1,0,1,0,1,1,1,0},
            {0,0,0,1,1,1,1,1,1,0,0,0},
            {0,1,0,1,0,1,0,1,0,0,0,0},
            {0,1,0,1,1,0,1,1,1,1,0,0},
            {0,1,1,1,0,1,0,1,0,0,0,0},
            {0,0,0,1,1,1,1,1,1,1,1,1},
            {0,1,0,1,1,0,0,0,0,1,0,0},
            {0,1,0,1,1,1,1,0,0,1,0,0},
            {1,1,1,1,1,0,0,0,0,0,0,0}
    };
    private static Tile[][] setField(){
        Tile[][]field=new Tile[10][12];
        int x=250;
        int y=250;
        for (int i=0;i<field.length;i++) {
            for (int k=0;k<field[i].length;k++) {

                if (fieldLogicMap[i][k]==1){
                    field[i][k]=new Tile(x,y,true);
                }else{
                    field[i][k]=new Tile(x,y,false);
                }
                x+=50;

            }
            x=250;
            y+=50;
        }
        return  field;
    }
    public static Tile[][] fieldMap=  setField();



    public static Graph<Tile, DefaultEdge> fieldGraph=new SimpleGraph<Tile, DefaultEdge>(DefaultEdge.class);

    private static void setFieldVertices(Graph<Tile,DefaultEdge> fieldGraph){
        for (Tile[]row:fieldMap) {
            for (Tile tile:row) {
                fieldGraph.addVertex(tile);
            }
        }
    }

    private static void setFieldEdges(Graph<Tile,DefaultEdge> fieldGraph){
        for (int i=0;i<fieldLogicMap.length;i++){
            for (int k=0;k< fieldLogicMap[i].length;k++){
                if(fieldMap[i][k].passable) {
                    if (i - 1 >= 0 && !(fieldGraph.containsEdge(fieldMap[i][k], fieldMap[i - 1][k]))) {
                        if (fieldMap[i - 1][k].passable) {
                            fieldGraph.addEdge(fieldMap[i][k], fieldMap[i - 1][k]);
                        }
                    }
                    if (i + 1 < fieldMap.length && !(fieldGraph.containsEdge(fieldMap[i][k], fieldMap[i + 1][k]))) {
                        if (fieldMap[i + 1][k].passable) {
                            fieldGraph.addEdge(fieldMap[i][k], fieldMap[i + 1][k]);
                        }
                    }
                    if (k - 1 >= 0 && !(fieldGraph.containsEdge(fieldMap[i][k - 1], fieldMap[i][k]))) {
                        if (fieldMap[i][k - 1].passable) {
                            fieldGraph.addEdge(fieldMap[i][k], fieldMap[i][k - 1]);
                        }

                    }
                    if (k + 1 < fieldMap[i].length && !(fieldGraph.containsEdge(fieldMap[i][k], fieldMap[i][k + 1]))) {
                        if (fieldMap[i][k + 1].passable) {
                            fieldGraph.addEdge(fieldMap[i][k], fieldMap[i][k + 1]);
                        }
                    }

                    if ((k + 1 < fieldMap[i].length)&&(i+1<fieldMap.length) && !(fieldGraph.containsEdge(fieldMap[i][k], fieldMap[i+1][k + 1]))) {
                        if (fieldMap[i+1][k + 1].passable) {
                            fieldGraph.addEdge(fieldMap[i][k], fieldMap[i+1][k + 1]);
                        }
                    }

                    if ((k - 1 >=0)&&(i+1<fieldMap.length) && !(fieldGraph.containsEdge(fieldMap[i][k], fieldMap[i+1][k - 1]))) {
                        if (fieldMap[i+1][k - 1].passable) {
                            fieldGraph.addEdge(fieldMap[i][k], fieldMap[i+1][k - 1]);
                        }
                    }

                    if ((k - 1 >=0)&&(i-1>=0) && !(fieldGraph.containsEdge(fieldMap[i][k], fieldMap[i-1][k - 1]))) {
                        if (fieldMap[i-1][k - 1].passable) {
                            fieldGraph.addEdge(fieldMap[i][k], fieldMap[i-1][k - 1]);
                        }
                    }

                    if ((k + 1 <fieldMap[i].length)&&(i-1>=0) && !(fieldGraph.containsEdge(fieldMap[i][k], fieldMap[i-1][k + 1]))) {
                        if (fieldMap[i-1][k + 1].passable) {
                            fieldGraph.addEdge(fieldMap[i][k], fieldMap[i-1][k + 1]);
                        }
                    }
                }
            }
        }
    }

    public static void createFieldMap(){
        setFieldVertices(fieldGraph);
        setFieldEdges(fieldGraph);
    }


}
