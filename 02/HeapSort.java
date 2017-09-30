package hw02;

import java.io.*;
import java.util.StringTokenizer;

public class HeapSort {
    private Node[] arr;
    private int heapSize;

    public HeapSort(String filePath){
        String data = this.readFile(filePath);
        StringTokenizer str = new StringTokenizer(data,",|\n");
        arr = new Node[str.countTokens()/2 + 1];
        for(int i = 1; str.hasMoreTokens(); i++){
            arr[i] = new Node(Integer.parseInt(str.nextToken()), str.nextToken());
        }
        this.heapSize = arr.length - 1;
    }

    public void doBuildMaxHeap(){
        buildMaxHeap(this.arr);
    }

    public void doInsert(int addJobPriority, String addJobName){
        insert(this.arr, new Node(addJobPriority, addJobName));
        printArr();
    }

    public void doMax(){
        Node maxNode = max(this.arr);
        System.out.println("최대값 : " + maxNode.priorityNumber + maxNode.jobName);
        printArr();
    }

    public boolean doExtractMax(){
        if(this.heapSize > 0){
            extractMax(this.arr);
            doBuildMaxHeap();
            printArr();
            return true;
        }
        return false;

    }

    public boolean doIncreaseKey(int index, int newKeyValue){
        if(increaseKey(this.arr, index, newKeyValue)){
            printArr();
            return true;
        }
        return false;
    }

    public boolean doDelete(int index){
        if(delete(this.arr, index)){
            printArr();
            return true;
        }
        return false;
    }

    private void buildMaxHeap(Node[] arr){
        for(int i = (this.heapSize) / 2 ; i > 0 ; i--){
            maxHeapify(arr, i);
        }
    }

    private void maxHeapify(Node[] arr, int i){
        int largest;
        int leftChildIdx = getLeftChildIdx(i);
        int rightChildIdx = getRightChildIdx(i);
        if(this.heapSize >= leftChildIdx && arr[leftChildIdx].priorityNumber > arr[i].priorityNumber){
            largest = leftChildIdx;
        }else{
            largest = i;
        }
        if(this.heapSize >= rightChildIdx && arr[rightChildIdx].priorityNumber > arr[largest].priorityNumber){
            largest = rightChildIdx;
        }
        if(largest != i){
            swap(arr, largest, i);
            maxHeapify(arr, largest);
        }

    }

    // Max Priority Queue Method

    private Node max(Node[] arr){
        return arr[1];
    }

    private Node extractMax(Node[] arr){
        swap(arr, heapSize, 1);
        this.heapSize--;
        return arr[1];
    }

    private void insert(Node[] arr, Node x){
        Node[] temp = new Node[this.heapSize + 2];
        for(int i = 1 ; i <= this.heapSize; i++){
            temp[i] = arr[i];
        }
        temp[temp.length - 1] = x;
        this.arr = temp;
        this.heapSize = temp.length - 1;
        buildMaxHeap(this.arr);
    }

    private boolean increaseKey(Node[] arr, int index, int newKeyValue){
        if(index > 0 && index < arr.length) {
            arr[index].priorityNumber = newKeyValue;
            buildMaxHeap(arr);
            return true;
        }
        return false;
    }

    private boolean delete(Node[] arr, int index){
        if(index > 0 && index <= this.heapSize) {
            Node[] temp = new Node[this.heapSize];
            for (int i = 1; i < temp.length; i++) {
                if (i >= index) temp[i] = arr[i+1];
                else{
                    temp[i] = arr[i];
                }
            }
            this.arr = temp;
            this.heapSize = this.arr.length - 1;
            buildMaxHeap(this.arr);
            return true;
        }
        return false;

    }

//    public void heapSort(){
//        while(this.heapSize > 1){
//            buildMaxHeap(this.arr);
//            extractMax(this.arr);
//        }
//    }

    private void swap(Node[] arr, int i, int j){
        Node temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private int getLeftChildIdx(int parentIdx){
        return 2*parentIdx;
    }

    private int getRightChildIdx(int parentIdx){
        return 2*parentIdx+1;
    }

    public void printAllArr(){
        for(int i = 1 ; i < this.arr.length ; i++){
            System.out.println(this.arr[i].priorityNumber + " / " + this.arr[i].jobName);
        }
    }

    public void printArr(){
        for(int i = 1 ; i <= this.heapSize ; i++){
            System.out.println("[" + i + "] " + this.arr[i].priorityNumber + " / " + this.arr[i].jobName);
        }
    }


    private String readFile(String filePath) {
        BufferedReader br = null;
        StringTokenizer str = null;
        StringBuilder sb = null;
        String line;

        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "euc-kr"));
            sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
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

    private class Node{
        private int priorityNumber;
        private String jobName;

        public Node(int priorityNumber, String jobName){
            this.priorityNumber = priorityNumber;
            this.jobName = jobName;
        }
    }
}
