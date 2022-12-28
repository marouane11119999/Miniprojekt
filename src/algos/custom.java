package algos;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.UnaryOperator;

public class custom {
    public static void main(String[] args) throws IOException {
        float maxFlow=1000;
        Flownetwork G = new Flownetwork("C:\\Users\\21260\\Desktop\\effiziente algos\\Miniprojekt\\src\\Files\\newFile.txt",maxFlow);
        Compute algo = new Compute(G);
        float counter=0;
        int i=1;
        while(counter<1000){
            System.out.println("Iteration : "+i);
            float value = algo.computeMaxFlow(counter);
            maxFlow-=value;
            G = new Flownetwork("C:\\Users\\21260\\Desktop\\effiziente algos\\Miniprojekt\\src\\Files\\newFile.txt",maxFlow);
            algo = new Compute(G);

            counter+=value;
            i++;
        }
        System.out.println("Alle Geschenke wurden versendet "+(counter-81));

       //finding unused paths
        LinkedList<Integer> list=new LinkedList<>();
        //algo.findAllPaths(algo.g.s,algo.g.t,list).removeAll(Compute.usedPaths);
    }
}
