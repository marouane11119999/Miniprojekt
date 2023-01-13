package algos;

import java.io.IOException;
import java.util.*;

public class Compute {

    Flownetwork g;
    private HashMap<Integer, HashMap<Integer,Float>> flow; // current flow values
    private LinkedList<Integer> P;//augmenting path
    public static HashSet<LinkedList> usedPaths=new LinkedHashSet<>();
    private Float b; //value of augmentation
    public Compute(Flownetwork g){
        this.g = g;
    }



    public float computeMaxFlow(Float max){
        float val = 0.0f;
        //initialize flow
        flow = new HashMap<Integer, HashMap<Integer,Float>>();
        for (Integer v: g.getVertices()){
            flow.put(v,new HashMap<Integer,Float>());
            for (Integer u: g.getNeighbors(v)){
                flow.get(v).put(u,0.0f);
            }
        }
        while (augment()){
            val = val+b;
            System.out.println("Current flow value "+val);
        }
        return val;
    }

    private boolean augment(){//returns true if and only if augmenting path has been found
        HashSet <Integer> visited = new HashSet<Integer>();
        P = new LinkedList<Integer>();
        b = Float.MAX_VALUE;

        if (DFS(g.s,visited)){//residual graph has augmenting path
            //update flow and residual graph
            //System.out.println("Found augmenting path, increasing flow by "+b);
            //System.out.println(P);
            usedPaths.add(P);
            Integer prev = g.s;
            for (Integer v: P){
                if (v==g.s) continue;
                //if e ∈ E : f (e) ← f (e) + b
                //network edge goes from prev to v
                if (flow.get(prev).containsKey(v)){
                    float fe = flow.get(prev).get(v);
                    flow.get(prev).put(v,fe+b);
                    Float c = g.capacity(prev,v);
                    if (c<=b+0.01){
                        //System.out.println("removing edge from residual graph");
                        g.deleteEdge(prev,v);
                    }
                    else {
                        g.setCapacity(prev,v,c-b);
                    }
                    if (g.adjacent(v,prev)){
                        c = g.capacity(v,prev);
                        g.setCapacity(v,prev,c+b);
                    }
                    else {
                        g.addEdge(v,prev,b);
                    }
                }
                else {//network edge goes from v to prev , so eR ∈ E : f (eR ) ← f (eR ) − b
                    float fe = flow.get(v).get(prev);
                    flow.get(v).put(prev,fe-b);
                    //check if edge from v to prev is in the residual graph
                    if (g.adjacent(v,prev)){
                        Float c = g.capacity(v,prev);
                        g.setCapacity(prev,v,c+b);
                    }
                    else {
                        g.addEdge(v,prev,b);
                    }
                    //edge from prev to v must be in the residual graph
                    Float c = g.capacity(prev,v);
                    if (c<=b+0.00001){
                        g.deleteEdge(prev,v);
                    }
                    else {
                        g.setCapacity(v,prev,c-b);
                    }
                }
                prev=v;
            }

            return true;
        }
        else {
            return false;
        }

    }
    private boolean DFS(Integer v, HashSet<Integer> visited){
        if (g.toAvoid.contains(v)) return false;
        if (v==g.t){
            P.addFirst(v);
            return true;
        }
        else {
            visited.add(v);
            for (Integer u: g.getNeighbors(v)){
                if (!visited.contains(u)){
                    if (DFS(u,visited)){
                        P.addFirst(v);
                        b = Math.min(b,g.capacity(v,u));
                        return true;
                    }

                }
            }
            return false;
        }
    }


    public  HashSet<List<Integer>> findAllPaths(
            Integer start,
            Integer end,
            List<Integer> path
    ) {
        List<Integer> newPath = new ArrayList<>(path);
        newPath.add(start);

        if (start.equals(end)) {
            HashSet<List<Integer>> result = new HashSet<>();
            result.add(newPath);
            return result;
        }

        if (!g.adjacency.containsKey(start)) {
            return new HashSet<>();
        }

        HashSet<List<Integer>> paths = new HashSet<>();
        for (Integer node : g.getNeighbors(start)) {
            if (!newPath.contains(node)) {
                HashSet<List<Integer>> newPaths = findAllPaths( node, end, newPath);
                paths.addAll(newPaths);
            }
        }

        return paths;
    }


}
