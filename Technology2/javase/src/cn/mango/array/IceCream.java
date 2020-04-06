package cn.mango.array;

import java.util.*;

/**
 * @Author mango
 * @Since 2020/3/15 15:09
 **/
public class IceCream {

    public static void main(String[] args) {
        Map<Integer, Integer> hashMap = new HashMap<>();
        System.out.println("Enter the integers between 1 and 100:");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            Integer number = scanner.nextInt();
            if (number == 0) {
                break;
            }
            Set<Integer> keySet = hashMap.keySet();
            boolean signal = keySet.contains(number);
            if (signal) {
                int value = hashMap.get(number) + 1;
                hashMap.put(number, value);
            } else {
                hashMap.put(number, 1);
            }
        }
        Set<Integer> integers = hashMap.keySet();
        for(int i : integers) {
            if (hashMap.get(i) > 1)
                System.out.println(i + " occures " + hashMap.get(i) + " times");
            else
                System.out.println(i + " occures " + hashMap.get(i) + " time");
        }
    }


}
