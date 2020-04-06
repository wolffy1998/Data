package annotations;

/**
 * @Author mango
 * @Since 2020/3/9 22:34
 **/
@ExtractInterface("IMultiplier")
public class Multiplier {

    public int multiply(int x, int y) {
        int total = 0;
        for (int i=0; i<x; i++) {
            total = add(x, y);
        }
        return total;
    }

    public static int add(int x, int y) {
        return x + y;
    }

}
