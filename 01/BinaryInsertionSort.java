package hw01;

import java.io.*;
import java.util.StringTokenizer;

public class BinaryInsertionSort{
    int[] arr;

    public BinaryInsertionSort(String filePath){
        String data = readFile(filePath);
        StringTokenizer str = new StringTokenizer(data, " ");
        this.arr = new int[str.countTokens()];
        for(int i = 0; str.hasMoreTokens(); i++){
            this.arr[i] = Integer.parseInt(str.nextToken());
        }
    }

    public void binaryInsertionSort(){
        if(this.arr.length == 1) return;
        for(int i = 1; i<arr.length; i++){
            int key = arr[i];
            int start = 0;
            int end = i-1;
            int mid = (start + end) / 2;
            if (arr[i] >= arr[i-1]) continue; // 순서대로 되어있는 경우
            while(start - end != 1){ // start, end 인덱스 역전 막고 자리결정
                if(key >= arr[mid]){
                    start = mid + 1;
                }else{
                    end = mid - 1;
                }
                mid = (start + end) / 2;
            }
            for(int j = i; j > start; j--){
                arr[j] = arr[j - 1];
            }
            arr[start] = key;
        }
    }


    public void printArr(){
        for(int i = 0; i < arr.length; i++){
            System.out.println(arr[i]);
        }
    }

    public void elapsedTime(){
        long start = System.currentTimeMillis();
        binaryInsertionSort();
        long end = System.currentTimeMillis();
        System.out.println("실행 시간 : " + ( end - start ) / 1000.0 +"초");
    }

    private String readFile(String filePath){
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
        } catch (FileNotFoundException e){
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
