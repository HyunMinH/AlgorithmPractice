import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.IntStream;

public class Sol1146{
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        int size = input.nextInt();
        Solution solve = new Solution(size);
        System.out.println(solve.getAllCount());
    }
}

class Solution{
    private int[][] cache;
    private static int MOD_NUM = 1000000;

    int getAllCount(){
        if(cache.length == 1) return 1;

        int ret=0;
        for(int i=1; i<=cache.length; i++){
            int left = i-1; int right = cache.length - i;
            ret = (ret + count(left, right)) % MOD_NUM;
            ret = (ret + count(right, left)) % MOD_NUM;
        }
        return ret % MOD_NUM;
    }

    Solution(int size){
        cache = new int[size][size];
        for(int[] c : cache) Arrays.fill(c, -1);
    }

    private int count(int left, int right){
        if(cache[left][right] != -1) return cache[left][right];
        if(left == 0 && right ==0) return 1;
        if(left < 0 || right < 0) return 0; 

        int result = 0;

        for(int i=1; i<=right; i++)
            result = (result + count(right-i, left+i-1)) % MOD_NUM;
        
        cache[left][right] = result;
        return result;
    }
}