import java.util.*;

public class Sol1114 {
    public static void main(String[] args){
        try(Scanner input = new Scanner(System.in)){
            int size = input.nextInt();
            int numOfCanSlice = input.nextInt(); 
            int numOfMaxSlice = input.nextInt();

            Solution sol = new Solution(size);
            for(int i=0; i<numOfCanSlice; i++) sol.addSlicePos(input.nextInt());

            List<Integer> result = sol.minimizeLongestSlice(numOfMaxSlice);
            System.out.println(result.get(0) + " " + result.get(1));
        }
    }
}

class Solution{
    private PriorityQueue<Tree> trees = new PriorityQueue<>(Collections.reverseOrder());
    private TreeSet<Integer> slice = new TreeSet<>();

    List<Integer> minimizeLongestSlice(int numOfMaxSlice){
        Map<Tree, Integer> slicedAtMap = new HashMap<>();
        slicedAtMap.put(trees.peek(), -1);

        while(trees.size() -1 < numOfMaxSlice){
            Tree pick = trees.peek(); //자를 통나무 고르기
            int mid = (pick.x2 + pick.x1) / 2;
            Integer ceiling = slice.ceiling(mid);
            Integer floor = slice.floor(mid);

            if((ceiling == null || !pick.canSlice(ceiling))
                && (floor == null || !pick.canSlice(floor))) break; // 더 이상 제일 긴 나무를 자를 수 없을 때

            trees.poll(); // 자를 수 있으면 pq에서 빼기

            int sliceAt;
            if(ceiling == null || !pick.canSlice(ceiling)) 
                sliceAt = floor;
            else if(floor == null || !pick.canSlice(floor)) 
                sliceAt = ceiling;
            else
                sliceAt = ceiling - mid < mid - floor ? ceiling : floor;
            
            List<Tree> divieds = pick.divide(sliceAt);
            trees.addAll(divieds);
            for(Tree t : divieds)
                slicedAtMap.put(t, sliceAt);
        }

        Tree longest = trees.peek();
        return Arrays.asList(longest.x2 - longest.x1, slicedAtMap.get(longest));
    }

    Solution(int size){
        trees.add(new Tree(0, size));
    }

    void addSlicePos(int pos) { slice.add(pos); }
}

class Tree implements Comparable<Tree>{
    final int x1;
    final int x2;

    Tree(int x1, int x2) { this.x1 = x1; this.x2 = x2; }
    List<Tree> divide(int x) {
        assert x1 < x && x < x2;
        return Arrays.asList(new Tree(x1,x), new Tree(x,x2));
    }
    
    boolean canSlice(int x){
        return x1 < x && x < x2;
    }

    @Override
    public boolean equals(Object obj) {
        Tree tree = (Tree) obj;
        return x1 == tree.x1 && x2 == tree.x2;
    }

    @Override
    public int hashCode() {
        return 31 * Integer.hashCode(x1) + Integer.hashCode(x2);
    }

    @Override
    public int compareTo(Tree o) {
        return Comparator.comparingInt((Tree t) -> t.x2 - t.x1).compare(this, o);
    }
}