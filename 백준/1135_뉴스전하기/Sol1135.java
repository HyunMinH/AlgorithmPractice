import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Sol1135 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        int size = input.nextInt();
        List<Tree> trees = IntStream.range(0, size)
            .mapToObj(i -> new Tree()).collect(Collectors.toList());

        for(int child=0; child<size; child++){
            int parent = input.nextInt();
            if(parent == -1) continue;
            trees.get(parent).addchild(trees.get(child));
        }

        System.out.println(trees.get(0).getMinimalTime());
    }
}

class Tree{
    private List<Tree> children = new ArrayList<>();

    int getMinimalTime(){
        if(children.size() == 0) return 0;

        children.sort(Comparator.comparing(Tree::getMinimalTime).reversed());
        int result=0, time = 1;
        for(Tree child : children){
            result = Integer.max(result, child.getMinimalTime() + time);
            time++;
        }
        return result;
    }

    void addchild(Tree child){ children.add(child); }
}