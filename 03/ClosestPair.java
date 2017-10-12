package hw03;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class ClosestPair {
    private Coordinates[] arr;

    ClosestPair(String filePath){
        String data = this.readFile(filePath);
        StringTokenizer str = new StringTokenizer(data, " |\n");
        arr = new Coordinates[str.countTokens()/2];
        for(int i = 0; str.hasMoreTokens(); i++){
            arr[i] = new Coordinates(Double.parseDouble(str.nextToken()), Double.parseDouble(str.nextToken()));
        }
    }

    public double doClosetPair(){
        quickSortX(this.arr, 0, this.arr.length-1);
        return closestPair(this.arr, 0, this.arr.length-1);
    }

    private double closestPair(Coordinates[] arr, int start, int end){

        // Brute Force
        if(end - start + 1 == 3){ // 점 3개 최소값 비교
            double distance1 = calculateDistance(arr[start], arr[start+1]);
            double distance2 = calculateDistance(arr[start+1], arr[start+2]);
            double distance3 = calculateDistance(arr[start], arr[start+2]);

            double minDistance1 = mininumDistance(distance1, distance2);
            double minDistance = mininumDistance(minDistance1, distance3);

            return minDistance;
        }
        else if(end - start + 1 == 2){ // 점 2개 최소값 비교
            double minDistance = calculateDistance(arr[start], arr[start+1]);
            return minDistance;
        }
        else{ // 점 3개 초과
            int halfIndex = (start + end)/ 2;
            double halfX = (arr[halfIndex].x + arr[halfIndex+1].x) / 2;
            double distance1 = closestPair(arr, start, halfIndex);
            double distance2 = closestPair(arr, halfIndex+1, end);
            double distance = mininumDistance(distance1, distance2);
            Coordinates[] arr2;
            double temp = distance;
            double tempDistance = 0;

            int leftIndex = calcLeftIndex(arr, halfIndex, halfX, distance);
            int rightIndex = calcRightIndex(arr, halfIndex + 1, halfX, distance);
            if(rightIndex != -1 || leftIndex != -1) { // 더 작은 길이가 존재할때
                if(leftIndex == -1) { // 왼쪽면이 d보다 작을때
                    arr2 = new Coordinates[rightIndex - (halfIndex + 1) + 1];
                    System.arraycopy(arr, halfIndex + 1, arr2, 0, arr2.length);
                    quickSortY(arr2, 0, arr2.length - 1);
                }else if(rightIndex == -1){ // 오른쪽면이 d보다 작을때
                    arr2 = new Coordinates[halfIndex - leftIndex + 1];
                    System.arraycopy(arr, leftIndex, arr2, 0, arr2.length);
                    quickSortY(arr2, 0, arr2.length - 1);
                }else{ //양쪽면에 원소가 있을 때
                    arr2 = new Coordinates[rightIndex - leftIndex + 1];
                    System.arraycopy(arr, leftIndex, arr2, 0, arr2.length);
                    quickSortY(arr2, 0, arr2.length - 1);
                }
            }else{ // 작은 길이 없을때
                return distance;
            }

            // y축 거리 최소값 비교
            for(int i = 0; i < arr2.length - 1 ; i++){
                tempDistance = calculateDistance(arr2[i], arr2[i+1]);
                if(tempDistance < temp){
                    temp = tempDistance;
                }
            }
            return temp;
        }
    }


    private int calcLeftIndex(Coordinates[] arr, int halfIndex, double halfX, double distance){
        int leftIndex;
        if(Math.abs(arr[halfIndex].x - halfX) >= distance) return -1;
        for(leftIndex = halfIndex; leftIndex > 0; leftIndex--){
            if(Math.abs(arr[leftIndex].x - halfX) < distance) continue;
            else break;
        }
        return leftIndex;
    }


    private int calcRightIndex(Coordinates[] arr, int halfIndex, double halfX, double distance){
        int rightIndex;
        if(Math.abs(arr[halfIndex].x - halfX) >= distance) return -1;
        for(rightIndex = halfIndex; rightIndex < arr.length - 1; rightIndex++){
            if(Math.abs(arr[rightIndex].x - halfX) < distance) continue;
            else break;
        }
        return rightIndex;
    }

    private double mininumDistance(double distance1, double distance2){
        if(distance1 > distance2) return distance2;
        return distance1;
    }

    private void quickSortY(Coordinates[] arr, int p, int r){
        if(p < r){
            int pivotIndex = partitionY(arr, p, r);
            quickSortY(arr, p, pivotIndex-1);
            quickSortY(arr, pivotIndex+1, r);
        }
    };

    private int partitionY(Coordinates[] arr, int p, int r){
        double target = arr[r].y;
        int i = p-1;
        int j = p;
        while(j <= r-1){ // j가 끝 인덱스 전까지 탐색하도록
            if(arr[j].y <= target){
                i++;
                swap(arr, i, j);
            }
            j++;
        }
        i++;
        swap(arr, i, j);
        return i;
    }

    private void quickSortX(Coordinates[] arr, int p, int r){
        if(p < r){
            int pivotIndex = partitionX(arr, p, r);
            quickSortX(arr, p, pivotIndex-1);
            quickSortX(arr, pivotIndex+1, r);
        }
    }

    private int partitionX(Coordinates[] arr, int p, int r){
        double target = arr[r].x;
        int i = p-1;
        int j = p;
        while(j <= r-1){ // j가 끝 인덱스 전까지 탐색하도록
            if(arr[j].x <= target){
                i++;
                swap(arr, i, j);
            }
            j++;
        }
        i++;
        swap(arr, i, j);
        return i;
    }

    private void swap(Coordinates[] arr, int i, int j){
        Coordinates temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


    private double calculateDistance(Coordinates a, Coordinates b){
        return Math.abs(Math.sqrt(Math.pow(a.x-b.x, 2) + Math.pow(a.y-b.y,2)));
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

    private class Coordinates{
        double x, y;
        public Coordinates(double x, double y){
            this.x = x;
            this.y = y;
        }
    }
}
