import java.util.*;

public class Sol1214 {
    public static void main(String[] args){
        try(Scanner input = new Scanner(System.in)){
            int price = input.nextInt(); int coin1 = input.nextInt(); int coin2 = input.nextInt();
            Solution sol = new Solution(price, coin1, coin2);
            System.out.println(sol.minimumCost());
        }
    }
}

class Solution{
    private int price;
    private int coin1;
    private int coin2;

    Solution(int price, int coin1, int coin2){
        this.price = price;
        this.coin1 = coin1;
        this.coin2 = coin2;
    }

    int minimumCost(){
        if(price % coin1 == 0 || price % coin2 == 0) return price;
        if(coin2 > coin1) swapCoin();

        int ret = (price / coin1) * coin1 + coin1;
        for(int i=1; i <= price/coin1 + 1; i++){
            int numOfCoin1 = (price / coin1) - i + 1; // coin1을 i개만큼 뺌
            int payWithCoin1 = numOfCoin1 * coin1; 

            if((price - payWithCoin1) % coin2 == 0) return price;

            int payWithCoin2 = ((price - payWithCoin1) / coin2) * coin2 + coin2;
            if(payWithCoin1 + payWithCoin2 == ret) break; 
            ret = Integer.min(ret, payWithCoin1 + payWithCoin2);
        }

        return ret;
    }

    private void swapCoin(){
        int temp = coin1;
        coin1 = coin2;
        coin2 = temp;
    }
}