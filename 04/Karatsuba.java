package hw04;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class Karatsuba {

    private int[] arr;

    public Karatsuba(String filePath){
        String data = this.readFile(filePath);
        StringTokenizer str = new StringTokenizer(data, " |\n");
        arr = new int[str.countTokens()];
        for(int i = 0; str.hasMoreTokens(); i++){
            arr[i] = Integer.parseInt(str.nextToken());
        }

    }

    private int[] getArr(){
        return this.arr;
    }

    public BigInteger doKaratsuba(){
        return karatsuba(getArr()[0], getArr()[1]);
    }

//    private int karatsuba(int a, int b){
//        int aCount = calculateIntNumber(a);
//        int bCount = calculateIntNumber(b);
//        int maxValueCount = max(aCount, bCount);
//        if(maxValueCount == 1){
//            return a * b;
//        }
//        int exponent = maxValueCount / 2;
//        int divisor = getDivisor(exponent);
//        int x1 = a / divisor;
//        int x2 = a - (x1 * divisor);
//        int y1 = b / divisor;
//        int y2 = b - (y1 * divisor);
//        int z0 = karatsuba(x2, y2);
//        int z2 = karatsuba(x1, y1);
//        int z1 = karatsuba((x2 + x1), (y2 + y1)) - z2 - z0;
//
//        return (z2 * divisor * divisor) + z1 * divisor + z0;
//    }

    private BigInteger karatsuba(int a, int b){
        int aCount = calculateIntNumber(a);
        int bCount = calculateIntNumber(b);
        int maxValueCount = max(aCount, bCount);
        if(maxValueCount == 1){
            return BigInteger.valueOf(a * b);
        }
        int exponent = maxValueCount / 2;
        int divisor = getDivisor(exponent);
        int x1 = a / divisor;
        int x2 = a - (x1 * divisor);
        int y1 = b / divisor;
        int y2 = b - (y1 * divisor);
        BigInteger z0 = karatsuba(x2, y2);
        BigInteger z2 = karatsuba(x1, y1);
        BigInteger z1 = karatsuba((x2 + x1), (y2 + y1)).subtract(z2).subtract(z0);

        BigInteger bigDivisor = BigInteger.valueOf(divisor);
        return z2.multiply(bigDivisor.pow(2)).add(z1.multiply(bigDivisor)).add(z0);
    }

    private int getDivisor(int exponent){
        int number = 1;
        while(exponent > 0){
            number *= 10;
            exponent--;
        }
        return number;
    }

    private int max(int a, int b){
        return (a >= b) ? a : b;
    }


    public int calculateIntNumber(int number){
        int count = 0;
        while(number > 0){
            number /= 10;
            count++;
        }
        return count;
    }

    private String readFile(String filePath) {
        BufferedReader br = null;
        StringTokenizer str = null;
        StringBuilder sb = null;
        String line;

        try {
            br = new BufferedReader(new FileReader(filePath));
            sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }
    }
}
