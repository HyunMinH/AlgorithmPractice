import java.util.*;
import java.util.stream.IntStream;

public class Sol1102{
    public static void main(String[] args){
        try(Scanner input = new Scanner(System.in)){
            int size = input.nextInt();
            Solution sol = new Solution(size);

            for(int i=0; i<size; i++){
                for(int j=0; j<size; j++){
                    sol.addEdge(i, j, input.nextInt());
                }
            }

            String onOff = input.next();
            int visited = 0;
            for(int i=0; i<size; i++){
                if(onOff.charAt(i) == 'Y'){
                   visited |= (1<<i);
                }
            }

            int result = sol.minCost(visited, input.nextInt());
            if(!sol.getSuccess()) System.out.println(-1);
            else System.out.println(result);
        }
    }

}

class Solution{
    private int[][] graph;
    private int[] cache;
    private boolean success = false;

    int minCost(int visited, int numOfTurnOn){
        if(Integer.bitCount(visited) >= numOfTurnOn){
            success = true;
            return 0;
        }

        if(cache[visited] != -1) return cache[visited];
        int ret = Integer.MAX_VALUE;

        for(int u=0; u<graph.length; u++){
            for(int v=0; v<graph.length; v++){
                if(u == v ) continue;
                if(!isVisited(visited, u) || isVisited(visited, v)) continue;
                ret = Integer.min(ret, graph[u][v] + minCost(visited | (1<<v), numOfTurnOn));
            }
        }

        cache[visited] = ret;
        return ret;
    }

    boolean getSuccess() { return this.success; }

    private boolean isVisited(int visited, int i){
        return (visited & (1<<i)) > 0;
    }

    Solution(int size){
        this.graph = new int[size][size];
        this.cache = new int[(1 << size)+1];
        Arrays.fill(cache, -1);
    }

    void addEdge(int u, int v, int cost){
        graph[u][v] = cost;
    }
}