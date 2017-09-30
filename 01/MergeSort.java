package hw01;

import java.io.*;
import java.util.StringTokenizer;

public class MergeSort {
    int[] arr;

    public MergeSort(String filePath){
        String data = this.readFile(filePath);
        StringTokenizer str = new StringTokenizer(data," ");
        arr = new int[str.countTokens()];
        for(int i = 0; str.hasMoreTokens(); i++){
            arr[i] = Integer.parseInt(str.nextToken());
        }
    }

    public void mergeSotring(){
        mergeSort(this.arr, 0, arr.length-1);
    }

    private void mergeSort(int[] arr, int start, int end){
        if(start == end) return;
        int mid = (start + end) / 2;
        mergeSort(arr, start, mid);
        mergeSort(arr, mid+1, end);
        merge(arr, start, mid, end);
    }

    private void merge(int[] arr, int start, int mid, int end) {
        int tempArrSize = end - start + 1;
        int[] tempArr = new int[tempArrSize];

            for (int i = start, j = mid + 1, k = 0; k < tempArr.length; k++) {
                if (i < mid+1 && j <= end){ // 둘다 탐색범위 안에 있을때 주의: mid+1을 j로 바꾸는 일을 하지말것 (기준)
                    if(arr[i] < arr[j]){
                        tempArr[k] = arr[i];
                        i++;
                    }else{
                        tempArr[k] = arr[j];
                        j++;
                    }
                }else{ // 양쪽중 한쪽이라도 탐색범위 벗어날경우
                    if(i >= mid+1) {
                        tempArr[k] = arr[j];
                        j++;
                    }else{
                        tempArr[k] = arr[i];
                        i++;
                    }
                }
            }

        for (int i = 0; i < tempArrSize; i++) {
            arr[start++] = tempArr[i];

        }
    }

    public void printArr(int[] arr){
        for(int i = 0; i< arr.length; i++) System.out.println(arr[i]);
    }

    public void elapsedTime(){
        long start = System.currentTimeMillis();
        mergeSotring();
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
            writer = new FileWriter("201200729_output.txt");
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
