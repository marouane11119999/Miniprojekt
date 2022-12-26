package algos;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Flow;

public class Flownetwork {
    private HashSet<Integer> nodes;
    public HashMap<Integer, HashMap<Integer,Float>> adjacency;
    public HashMap<Integer, String> vertexNames;
    public Integer s;
    public Integer t;



    public Flownetwork(String filename) throws IOException {

        nodes = new HashSet<Integer>();
        adjacency = new HashMap<Integer, HashMap<Integer, Float>>();
        vertexNames = new HashMap<Integer, String>();
        File file = new File(filename);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        String string;
        while ((string = bufferedReader.readLine()) != null) {
            String[] splitted = string.split(" ");
            if (splitted[0].contains("L")) {
                int vertex1 = Integer.parseInt(splitted[0].replaceAll("[^0-9]", "")) * 1000;
                vertexNames.put(vertex1, splitted[0]);
                int vertex2 = Integer.parseInt(splitted[1].replaceAll("[^0-9]", ""));
                Float capacity = Float.valueOf(splitted[2].replaceAll("[^0-9]", ""));
                vertexNames.put(vertex2, splitted[1]);
                nodes.add(vertex1);
                nodes.add(vertex2);
                if (adjacency.containsKey(vertex1)) {
                    adjacency.get(vertex1).put(vertex2, capacity);
                } else {
                    HashMap<Integer, Float> temp = new HashMap<>();
                    temp.put(vertex2, capacity);
                    adjacency.put(vertex1, temp);
                }

            } else if (splitted[1].contains("L")) {
                int vertex1 = Integer.parseInt(splitted[0].replaceAll("[^0-9]", ""));
                vertexNames.put(vertex1, splitted[0]);
                int vertex2 = Integer.parseInt(splitted[1].replaceAll("[^0-9]", "")) * 1000;
                Float capacity = Float.valueOf(splitted[2].replaceAll("[^0-9]", ""));
                vertexNames.put(vertex2, splitted[1]);
                nodes.add(vertex1);
                nodes.add(vertex2);
                if (adjacency.containsKey(vertex1)) {
                    adjacency.get(vertex1).put(vertex2, capacity);
                } else {
                    HashMap<Integer, Float> temp = new HashMap<>();
                    temp.put(vertex2, capacity);
                    adjacency.put(vertex1, temp);
                }
            } else if (splitted[0].contains("Z")) {
                int vertex1 = 900;
                vertexNames.put(vertex1, splitted[0]);
                int vertex2 = Integer.parseInt(splitted[1].replaceAll("[^0-9]", ""));
                Float capacity = Float.valueOf(splitted[2].replaceAll("[^0-9]", ""));
                vertexNames.put(vertex2, splitted[1]);
                nodes.add(vertex1);
                nodes.add(vertex2);
                if (adjacency.containsKey(vertex1)) {
                    adjacency.get(vertex1).put(vertex2, capacity);
                } else {
                    HashMap<Integer, Float> temp = new HashMap<>();
                    temp.put(vertex2, capacity);
                    adjacency.put(vertex1, temp);
                }


            } else if (splitted[1].contains("Z")) {
                int vertex1 = Integer.parseInt(splitted[0].replaceAll("[^0-9]", ""));
                vertexNames.put(vertex1, splitted[0]);
                int vertex2 = 900;
                Float capacity = Float.valueOf(splitted[2].replaceAll("[^0-9]", ""));
                vertexNames.put(vertex2, splitted[1]);
                nodes.add(vertex1);
                nodes.add(vertex2);
                if (adjacency.containsKey(vertex1)) {
                    adjacency.get(vertex1).put(vertex2, capacity);
                } else {
                    HashMap<Integer, Float> temp = new HashMap<>();
                    temp.put(vertex2, capacity);
                    adjacency.put(vertex1, temp);
                }

            } else {
                int vertex1 = Integer.parseInt(splitted[0].replaceAll("[^0-9]", ""));
                vertexNames.put(vertex1, splitted[0]);
                int vertex2 = Integer.parseInt(splitted[1].replaceAll("[^0-9]", ""));
                vertexNames.put(vertex2, splitted[1]);
                Float capacity = Float.valueOf(splitted[2].replaceAll("[^0-9]", ""));
                if (adjacency.containsKey(vertex1)) {
                    adjacency.get(vertex1).put(vertex2, capacity);
                } else {
                    HashMap<Integer, Float> temp = new HashMap<>();
                    temp.put(vertex2, capacity);
                    adjacency.put(vertex1, temp);

                }
            }
        }
    }

    public Flownetwork () {
        nodes = new HashSet<Integer>();
        adjacency = new HashMap<Integer, HashMap<Integer,Float>>();
        vertexNames = new HashMap<Integer,String>();
    }

    public void addVertex (Integer v) {
        nodes.add(v);
        vertexNames.put(v,v.toString());
        adjacency.put(v, new HashMap<Integer,Float>());
    }

    public void addVertex (Integer v, String name) {
        nodes.add(v);
        vertexNames.put(v,name);
        adjacency.put(v, new HashMap<Integer,Float>());
    }


    public void addEdge (Integer v, Integer w, Float l) {
        if (v == w) return;		// No loops!
        adjacency.get(v).put(w,l);
    }

    public int size () {
        return nodes.size();
    }

    /** Returns whether the given vertex ID belongs to the graph. */
    public boolean contains (Integer v) {
        return nodes.contains(v);
    }

    public int degree (Integer v) {
        return adjacency.get(v).size();
    }

    /** Returns whether vertices v and w are adjacent. */
    public boolean adjacent (Integer v, Integer w) {
        return adjacency.get(v).containsKey(w);
    }

    public HashSet<Integer> getVertices () {
        return nodes;
    }

    public int getEdgeCount () { /** Lege private Variable an */
        int edges = 0;
        for (int v : nodes)
            edges += adjacency.get(v).size();
        edges /= 2;
        return edges;
    }


    public Set<Integer> getNeighbors (Integer v) {
        return adjacency.get(v).keySet();
    }


    public float capacity (Integer v, Integer w) {
        if (adjacency.get(v).containsKey(w)){
            return adjacency.get(v).get(w);
        }
        else return Float.MAX_VALUE;
    }

    public void setCapacity (Integer v, Integer w, Float c) {
        if (adjacency.get(v).containsKey(w)){
            adjacency.get(v).put(w,c);
        }
    }


    public void deleteVertex (Integer vertex) {
        for (int neighbor : adjacency.get(vertex).keySet())
            adjacency.get(neighbor).remove(vertex);
        nodes.remove(vertex);
        vertexNames.remove(vertex);
    }

    public void deleteEdge (Integer u, Integer v){
        adjacency.get(u).remove(v);
    }
    public String getVertexName (Integer i) {
        return vertexNames.get(i);
    }

    public void printNetwork(){
        for (Integer v : this.nodes){
            //System.out.println(v);
            for (Integer u: this.getNeighbors(v)){
                System.out.println(v.toString()+" "+u.toString()+" "+Float.toString(this.capacity(v,u)));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Flownetwork ta=new Flownetwork("src/Files/output.txt");
        ta.printNetwork();
    }

}
