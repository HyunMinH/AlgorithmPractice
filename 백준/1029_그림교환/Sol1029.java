import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Sol1029{
    public static void main(String[] args){
        try(Scanner input = new Scanner(System.in)){
            int size = input.nextInt();
            Graph graph = new Graph(size);
            
            for(int i=0; i<size; i++){
                String line = input.next();
                for(int j=0; j<size; j++){
                    //if(line.charAt(j) == '0') continue;
                    graph.addEdge(i, j, line.charAt(j) - '0');
                }
            }

            System.out.println(graph.getMaxOwn(0));
        }
    }
}


class Graph{
    private List<List<Node>> adjList;
    private int[][][] cache;

    int getMaxOwn(int picture){
        cache = new int[adjList.size()][10][1 << adjList.size()]; // nodeNum, price, visited
        for(int[][] c1: cache)
            for(int[] c2 : c1) Arrays.fill(c2, -1);

        return dfs(picture, 0, (1<<picture)) + 1; // 자기자신포함
    }

    private int dfs(int u, int price, int visited){
       if(cache[u][price][visited] != -1) return cache[u][price][visited];

        int ret = 0;
        for(Node next : adjList.get(u)){
            if((visited & (1<<next.node)) > 0 || next.price < price) continue;
            ret = Integer.max(ret, 1 + dfs(next.node, next.price, visited | (1<<next.node)));
        }

        cache[u][price][visited] = ret;
        return ret;
    }

    Graph(int size){
        adjList = Stream.generate(() -> new ArrayList<Node>()).limit(size).collect(Collectors.toList());
    }

    void addEdge(int u, int v, int price){
        adjList.get(u).add(new Node(v, price));
    }

    private class Node{
        final int node;
        final int price;
        Node(int node, int price) { this.node = node; this.price = price; }
    }
}