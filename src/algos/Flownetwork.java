package algos;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;


public class Flownetwork {
    private HashSet<Integer> nodes;
    public HashMap<Integer, HashMap<Integer,Float>> adjacency;
    public HashMap<String,List<Integer>> beson=new HashMap<>();
    public HashMap<Integer, String> vertexNames;
    public Integer s;
    public Integer t;




    public Flownetwork (String filename) throws IOException {
        init("C:\\Users\\21260\\Desktop\\effiziente algos\\Miniprojekt\\src\\Files\\output.txt");
        nodes = new HashSet<Integer>();
        adjacency = new HashMap<Integer, HashMap<Integer,Float>>();
        HashMap<String, Integer> seenIds = new HashMap<String, Integer>();	// only required during construction
        vertexNames = new HashMap<Integer, String>();
        File file = new File(filename);

        try {
            final LineNumberReader reader = new LineNumberReader(new FileReader(file));
            String str;
            int id = 0;
            String sName = reader.readLine().trim();
            String tName = reader.readLine().trim();
            while ((str = reader.readLine()) != null)
            {
                str = str.trim(); // trim away whitespace at either end of line
                if (!str.startsWith("#") && !str.startsWith("%") ) {// skip comment lines
                    StringTokenizer tokens = new StringTokenizer(str);
                    if (tokens != null && tokens.countTokens() > 1) {	// only consider well-formed lines
                        String vertexA = tokens.nextToken().trim();
                        String vertexB = tokens.nextToken().trim();
                        String lstr = tokens.nextToken().trim();
                        Float l = Float.valueOf(lstr);
                        l = (float) Math.round(l*100)/100;
                        if (l<0.01) continue;
                        if (vertexA.equals(vertexB)) {
                            continue;
                        }
                        if (vertexA.equals(tName)){
                            continue;
                        }
                        if (vertexB.equals(sName)){
                            continue;
                        }
                        if (!seenIds.containsKey(vertexA)) {		// add vertex 0 if never seen before
                            seenIds.put(vertexA, id);
                            addVertex(id,vertexA);
                            id++;
                        }
                        if (!seenIds.containsKey(vertexB)) {		// add vertex 1 if never seen before
                            seenIds.put(vertexB, id);
                            addVertex(id,vertexB);
                            id++;
                        }
                        addVertex(id,"extra"+id);
                        // add edge to adjacency lists
                        addEdge(seenIds.get(vertexA), id,l);
                        addEdge(id,seenIds.get(vertexB), l);
                        id++;
                        //addEdge(seenIds.get(vertexA),seenIds.get(vertexB), l);
                    }
                }
            }
            reader.close();
            s =  seenIds.get(sName);
            t =  seenIds.get(tName);
            System.out.println(s+" "+t);
        } catch (IOException e) {
            System.out.println("Could not locate input file '"+filename+"'.");
            System.exit(0);
        }
    }

    public void init(String filename) throws IOException {
        t=900;
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
        initializeBesonderheiten();

        for(Map.Entry<String, List<Integer>> entry : beson.entrySet()){

            splitVertex(entry.getValue().get(0), Float.valueOf(entry.getValue().get(1)),entry.getKey());
        }

        addVertex(999);
        s=999;
        for (Map.Entry<Integer, String> entry : vertexNames.entrySet()) {
            if (entry.getValue().contains("L")){
                addEdge(s, entry.getKey(),1000.f);
            }
        }

        whenWriteStringUsingBufferedWritter_thenCorrect("C:\\Users\\21260\\Desktop\\effiziente algos\\Miniprojekt\\src\\Files\\newFile.txt");

    }

    public Flownetwork () {
        nodes = new HashSet<Integer>();
        adjacency = new HashMap<Integer, HashMap<Integer,Float>>();
        vertexNames = new HashMap<Integer,String>();
    }


    public void initialize(String name,Integer vertex,Integer capacity){
        Integer[] arr={vertex,capacity};
        beson.put(name, Arrays.asList(arr));
    }

    public void initializeBesonderheiten(){
        String[] names={"KP4","KP17","KP50","KP66","KP72","KP101","KP102","KP111","KP114","KP115"};
        Integer[] vertices={4,17,50,66,72,101,102,111,114,115};
        Integer[] capacities={17,69,49,73,73,84,75,99,59,31};
        for (int i = 0; i <10 ; i++) {
            initialize(names[i],vertices[i],capacities[i]);
        }
    }

    public void splitVertex(Integer vertex,Float capacity,String name){
        HashMap<Integer,Float> hisAdj= (HashMap<Integer, Float>) adjacency.get(vertex).clone();
        adjacency.get(vertex).clear();
        addVertex(vertex*1500,name+"B");
        addEdge(vertex,vertex*1500,capacity);
        adjacency.replace(vertex*1500,hisAdj);
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
            for (Integer u: this.getNeighbors(v)){
                System.out.println(v+" "+u+" "+capacity(v,u));
            }
        }
    }

    public void whenWriteStringUsingBufferedWritter_thenCorrect(String fileName)
            throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(s+"\n");
        writer.write(t+"\n");
        for (Integer v : this.nodes){
            for (Integer u: this.getNeighbors(v)){
                String str= v+" "+u+" "+capacity(v,u);
                writer.write(str+"\n");

            }
        }
        writer.close();
    }




    public static void main(String[] args) throws IOException {
        Flownetwork ta=new Flownetwork();

        //ta.printNetwork();
    }

}
