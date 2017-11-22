package hw08;

import java.util.Scanner;

public class SequenceAlignment {
    private int gapCost = 1;
    private int mismatchCost = 2;
    private String str1 = null, str2 = null;
    private int[][] M = null;

    public SequenceAlignment(){
        Scanner sc = new Scanner(System.in);
        System.out.print("String1 : ");
        str1 = sc.nextLine();
        System.out.print("String2 : ");
        str2 = sc.nextLine();
        M = new int[str1.length()+1][str2.length()+1];
    }

    public void sequenceAlignment(){
        for(int i = 0; i<str2.length() + 1; i++){
            M[0][i] = i * gapCost;
        }

        for(int i = 0; i<str1.length() + 1; i++){
            M[i][0] = i * gapCost;
        }

        for(int i = 1; i<str1.length()+1 ;i ++){
            for(int j = 1; j<str2.length()+1 ; j++){
                M[i][j] = Math.min(Math.min(calcMismatchCost(i - 1,j - 1) + M[i-1][j-1], gapCost + M[i-1][j]), gapCost + M[i][j-1]);
            }
        }
    }

    private int calcMismatchCost(int x, int y){
        if(str1.charAt(x) == str2.charAt(y)){
            return 0;
        }
        return mismatchCost;
    }

    // 배열로도 구할수 있지 않을까?
    private void printRoute(int x, int y){
        System.out.print("M[" + x +"][" + y +"] => ");
        if(x == 0 && y == 0){
            return ;
        }else if(y == 0){
            printRoute(x - 1, y);
        }else if(x == 0){
            printRoute(x, y-1);
        }else if(calcMismatchCost(x - 1,y - 1) + M[x-1][y-1] < gapCost + M[x-1][y]){
            if(calcMismatchCost(x - 1,y - 1) + M[x-1][y-1] < gapCost + M[x][y-1]){
                printRoute(x-1,y-1);
            }
            else{
                printRoute(x,y-1);
            }
        }else{
            if(gapCost + M[x-1][y] < gapCost + M[x][y-1]){
                printRoute(x-1,y);
            }else{
                printRoute(x,y-1);
            }
        }
    }

    public void print(){
        System.out.println("MIN COST : " + M[str1.length()][str2.length()]);

        System.out.print("      ");
        for(int i = 0; i<str2.length(); i++){
            System.out.printf("%3s", str2.charAt(i));
        }
        System.out.println();

        for(int i = 0; i<str1.length()+1 ;i ++){
            for(int j = 0; j<str2.length()+1 ; j++){
                if( i == 0  && j == 0) System.out.print("   ");
                else if( i > 0 && j == 0 ) System.out.printf("%3s", str1.charAt(i-1));
                System.out.printf("%3s", M[i][j]);
            }
            System.out.println();
        }

        printRoute(str1.length(), str2.length());
    }
}
