package hw02;

import java.io.*;
import java.util.StringTokenizer;

public class QuickSort {
    private int[] arr;

    public QuickSort(String filePath){
        String data = this.readFile(filePath);
        StringTokenizer str = new StringTokenizer(data," ");
        arr = new int[str.countTokens()];
        for(int i = 0; str.hasMoreTokens(); i++){
            arr[i] = Integer.parseInt(str.nextToken());
        }
    }

    public void doQuickSort(){
        quickSort(this.arr, 0, this.arr.length-1);
    }

    private int partition(int[] arr, int p, int r){
        int target = arr[r];
        int i = p-1;
        int j = p;
        while(j <= r-1){ // j가 끝 인덱스 전까지 탐색하도록
            if(arr[j] <= target){
                i++;
                swap(arr, i, j);
            }
            j++;
        }
        i++;
        swap(arr, i, j);
        return i;
    }

    private void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private void quickSort(int[] arr, int p, int r){
        if(p < r){
            int pivotIndex = partition(arr, p, r);
            quickSort(arr, p, pivotIndex-1);
            quickSort(arr, pivotIndex+1, r);
        }
    }

    public void printArr(){
        for(int i =0; i<this.arr.length; i++){
            System.out.println(this.arr[i]);
        }
        System.out.println();
    }

    public void elapsedTime(){
        long start = System.currentTimeMillis();
        doQuickSort();
        long end = System.currentTimeMillis();
        System.out.println("실행 시간 : " + ( end - start ) / 1000.0 +"초");
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
                sb.append(line);
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

    public void writeFile(){
        FileWriter writer =  null;
        StringBuffer sb = null;
        try{
            writer = new FileWriter("201200729_quick.txt");
            sb = new StringBuffer();

            sb.append(this.arr[0]);
            for(int i = 1; i< this.arr.length ; i++){
                sb.append(" "+ this.arr[i]);
            }
            writer.write(sb.toString());

        } catch(IOException e){
            e.printStackTrace();
        } finally {
            if(writer != null){
                try{
                    writer.close();
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
