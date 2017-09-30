package hw01;

import java.io.*;
import java.util.StringTokenizer;

public class InsertionSort {
    int[] arr;

    public InsertionSort(String filePath){
        String data = this.readFile(filePath);
        StringTokenizer str = new StringTokenizer(data," ");
        arr = new int[str.countTokens()];
        for(int i = 0; str.hasMoreTokens(); i++){
            arr[i] = Integer.parseInt(str.nextToken());
        }
    }

    public void insertionSort(){
        for(int i = 1; i < this.arr.length; i++){
            int key = arr[i];
            int j = i-1;
            while(j >= 0 && arr[j] > key){
                arr[j+1] = arr[j];
                j = j-1;
            }
            arr[j+1] = key;
        }
    }

    public void printArr(){
        for(int i = 0; i < arr.length ; i++){
            System.out.println(arr[i]);
        }
    }

    public void elapsedTime(){
        long start = System.currentTimeMillis();
        insertionSort();
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
