package hw07;

import java.util.Scanner;

public class Knapsack {
    private int M[][];
    private int weightLimit;
    private int itemCnt;
    private int maxValue;
    private Item valueAndWeight[];

    public Knapsack(){
        initData();
    }

    private void initData(){
        System.out.println("데이터를 입력하세요.");
        Scanner sc = new Scanner(System.in);
        String itemCntAndWeightLimit = sc.nextLine();
        String[] splitData = itemCntAndWeightLimit.split(" ");
        this.itemCnt = Integer.parseInt(splitData[0]);
        this.weightLimit = Integer.parseInt(splitData[1]);
        valueAndWeight = new Item[itemCnt];
        for(int i = 0; i<itemCnt ; i++){
            String inputValueAndWeight = sc.nextLine();
            splitData = inputValueAndWeight.split(" ");
            valueAndWeight[i] = new Item(Integer.parseInt(splitData[0]), Integer.parseInt(splitData[1]));
        }
        M = new int[itemCnt+1][weightLimit+1];
        // 0 초기화?
    }

    public void knapsack(){
        for(int i=1; i<=itemCnt; i++){
            for(int w=1; w<=weightLimit; w++){
                if(valueAndWeight[i-1].getWeight() > w){ // 아이템 선택했을 때 무게 한도가 바로 초과되는 경우
                    M[i][w] = M[i-1][w];
                }else{ // 새로운 아이템을 추가하는것과 하지 않는것 중 더 큰 가중치 비교 : 같은 아이템 라인(행)에서 추가되는 무게를 무게제한에서 감소
                    M[i][w] = maxValue = Math.max(M[i-1][w], valueAndWeight[i-1].getValue() + M[i-1][w-valueAndWeight[i-1].getWeight()]);
                }
            }
        }
    }

    private void findItem(int i, int w){
        if(valueAndWeight[i-1].getWeight() == w) { // 가치 및 무게 표 원소 일치 반환
            System.out.print(i + " ");
            return ;
        }
        if(M[i][w] > M[i-1][w]) { // 새롭게 추가된 원소 출력하고 무게 제한 감소된 수치 원소 찾기
            System.out.print(i + " ");
            findItem(i-1,w-valueAndWeight[i-1].getWeight());
        }else{ // 한줄 아래로 이동하여 탐색
            findItem(i-1,w);
        }
    }

    public void print(){
        for(int i=0;i<=itemCnt;i++){
            for(int w=0;w<=weightLimit;w++){
                System.out.print(M[i][w]+"    ");
            }
            System.out.println();
        }
        System.out.println("max : " + maxValue);

        System.out.print("item : ");
        findItem(itemCnt,weightLimit);
    }

    private class Item{
        private int value;
        private int weight;

        public Item(int value, int weight){
            this.value = value;
            this.weight = weight;
        }

        public int getValue() {
            return value;
        }

        public int getWeight() {
            return weight;
        }
    }
}
