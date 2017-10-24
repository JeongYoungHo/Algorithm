package hw05;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import static java.lang.Math.max;

public class MinimizeLateness {
    private Job[] arr;

    public MinimizeLateness(String filePath){
        String data = this.readFile(filePath);
        StringTokenizer str = new StringTokenizer(data, " |\n");
        arr = new Job[Integer.parseInt(str.nextToken())];
        for(int i = 0; str.hasMoreTokens(); i++){
            arr[i] = new Job(Integer.parseInt(str.nextToken()), Integer.parseInt(str.nextToken()));
        }
    }

    public int doMinimizeLateness(){
        return minimizeLateness(this.arr);
    }

    private int minimizeLateness(Job[] arr){
        quickSort(arr, 0, arr.length-1);
        int time = 0;
        int lateness = 0;
        for(int i = 0; i<arr.length; i++){
            int startTime = time;
            int takingTime = arr[i].getJobTakingTime();
            int scheduledEndTime = arr[i].getDeadLineTime();
            int finishTime = time + takingTime;
            time = finishTime;
            lateness = max(lateness, finishTime - scheduledEndTime);
        }
        return lateness;
    }

    private int partition(Job[] arr, int p, int r){
        int target = arr[r].getDeadLineTime();
        int i = p-1;
        int j = p;
        while(j <= r-1){ // j가 끝 인덱스 전까지 탐색하도록
            if(arr[j].getDeadLineTime() <= target){
                i++;
                swap(arr, i, j);
            }
            j++;
        }
        i++;
        swap(arr, i, j);
        return i;
    }

    private void swap(Job[] arr, int i, int j){
        Job temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private void quickSort(Job[] arr, int p, int r){
        if(p < r){
            int pivotIndex = partition(arr, p, r);
            quickSort(arr, p, pivotIndex-1);
            quickSort(arr, pivotIndex+1, r);
        }
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

    class Job{
        private int jobTakingTime, deadLineTime;

        public Job(int time, int deadLine){
            this.jobTakingTime = time;
            this.deadLineTime = deadLine;
        }

        private int getJobTakingTime(){
            return jobTakingTime;
        }

        private int getDeadLineTime(){
            return deadLineTime;
        }
    }
}

