package algos;

import java.io.IOException;

public class custom {
    public static void main(String[] args) throws IOException {
        Flownetwork G = new Flownetwork("C:\\Users\\21260\\Desktop\\effiziente algos\\Miniprojekt\\src\\Files\\newFile.txt");
        Compute algo = new Compute(G);
        float counter=0; float maxFlow=1000;
        while(counter<=1000){
            float value = algo.computeMaxFlow();
            G = new Flownetwork("C:\\Users\\21260\\Desktop\\effiziente algos\\Miniprojekt\\src\\Files\\newFile.txt");
            algo = new Compute(G);

            for (Integer e:G.getNeighbors(G.s)) {
                G.setCapacity(G.s,e,G.capacity(G.s,e)-value);

            }

            counter+=value;
        }
        System.out.println(maxFlow);
    }
}
