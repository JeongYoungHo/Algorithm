package hw04;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class CountingInverison {
    private int[] arr;

    public CountingInverison(String filePath){
        String data = this.readFile(filePath);
        StringTokenizer str = new StringTokenizer(data, " ");
        arr = new int[str.countTokens()];
        for(int i = 0; str.hasMoreTokens(); i++){
            arr[i] = Integer.parseInt(str.nextToken());
        }
    }

    private int[] getArr(){
        return this.arr;
    }

    public int doSortAndCount(){
        return sortAndCount(getArr(), 0, getArr().length - 1);
    }

    private int sortAndCount(int[] arr, int start, int end){
        if(start == end){
            return 0;
        }

        int halfIndex = (start + end) / 2;
        int leftInversionCounting = sortAndCount(arr, start, halfIndex);
        int rightInversionCounting = sortAndCount(arr, halfIndex+1, end);
        int mergeInversionCounting = mergeAndCount(arr, start, halfIndex, end);
        return leftInversionCounting + rightInversionCounting + mergeInversionCounting;
    }

    private int mergeAndCount(int[] arr, int start, int mid, int end){
        int inversionCount = 0;
        int[] tempArr = new int[end - start + 1];
        int count = 0;
        int leftPointerIndex = start;
        int rightPointerIndex = mid + 1;
        int leftArrCount = mid - start + 1;
        int rightArrCount = end - (mid + 1) + 1;
        while(leftArrCount != 0 && rightArrCount != 0){
            if(arr[leftPointerIndex] > arr[rightPointerIndex]){
                tempArr[count] = arr[rightPointerIndex];
                inversionCount += leftArrCount;
                rightArrCount--;
                rightPointerIndex++;
            }else{
                tempArr[count] = arr[leftPointerIndex];
                leftArrCount--;
                leftPointerIndex++;
            }
            count++;
        }
        if(leftArrCount == 0){
            System.arraycopy(arr, rightPointerIndex, tempArr, count, rightArrCount);
        }else if(rightArrCount == 0){
            System.arraycopy(arr, leftPointerIndex, tempArr, count, leftArrCount);
        }
        System.arraycopy(tempArr, 0, getArr(), start, end - start + 1);

        return inversionCount;
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
}
