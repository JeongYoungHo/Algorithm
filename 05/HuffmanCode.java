package hw05;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

public class HuffmanCode {
    private Node[] arr;
    private int heapSize;
    private String str;
    private HashMap<Character, Integer> map;
    private Node huffmanTree;

    public HuffmanCode(String filePath){
        this.str = this.readFile(filePath);
        createFrequencyMap();
        this.arr = new Node[map.size() + 1];
        mapToArr();
        this.heapSize = arr.length - 1;
        doBuildMinHeap(); // 초기 MinHeap 구성
    }

    public void huffmanCode(){
        for(int i = this.heapSize ; i > 1 ; i--) {
            Node left = extractMin(this.arr);
            Node right = extractMin(this.arr);
            Node newNode = new Node(left.getFrequency() + right.getFrequency(), left, right);
            insert(newNode);
        }
        huffmanTree = this.arr[1];
    }

    private void createFrequencyMap(){
        this.map = new HashMap<Character, Integer>();
        String str = this.str;
        for(int i = 0; i < str.length(); i++){
            char character = str.charAt(i);
            if(this.map.containsKey(character)){
                int frequency = this.map.get(character);
                this.map.put(character, ++frequency);
            }else{
                this.map.put(character, 1);
            }
        }
    }

    public void printHuffmanTree(){
        recursionHuffmanTree(huffmanTree, "");
    }

    private void recursionHuffmanTree(Node root, String code){
        if(root != null){
            if(root.getLeft() != null && root.getRight() != null){
                recursionHuffmanTree(root.getLeft(), code + "0");
                recursionHuffmanTree(root.getRight(), code + "1");
            }
            else{
                System.out.println(root.getAlphabet() + ", " + code);
            }
        }
    }

    private void insert(Node x){
        Node[] temp = new Node[this.heapSize + 2];
        for(int i = 1 ; i <= this.heapSize; i++){
            temp[i] = arr[i];
        }
        temp[temp.length - 1] = x;
        this.arr = temp;
        this.heapSize = temp.length - 1;
        buildMinHeap(this.arr);
    }



    private void mapToArr(){
        int i = 1;
        for(HashMap.Entry<Character, Integer> elem : map.entrySet()){
            arr[i] = new Node(elem.getKey(), elem.getValue());
            i++;
        }
    }

    private Node extractMin(Node[] arr){
        Node minNode = arr[1];
        swap(arr, heapSize, 1);
        this.heapSize--;
        doBuildMinHeap();
        return minNode;
    }

    public void doBuildMinHeap(){
        buildMinHeap(this.arr);
    }


    private void buildMinHeap(Node[] arr){
        for(int i = (this.heapSize) / 2 ; i > 0 ; i--){
            minHeapify(arr, i);
        }
    }

    private void minHeapify(Node[] arr, int i){
        int smallest;
        int leftChildIdx = getLeftChildIdx(i);
        int rightChildIdx = getRightChildIdx(i);
        if(this.heapSize >= leftChildIdx && arr[leftChildIdx].frequency < arr[i].frequency){
            smallest = leftChildIdx;
        }else{
            smallest = i;
        }
        if(this.heapSize >= rightChildIdx && arr[rightChildIdx].frequency < arr[smallest].frequency){
            smallest = rightChildIdx;
        }
        if(smallest != i){
            swap(arr, smallest, i);
            minHeapify(arr, smallest);
        }

    }

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

    class Node{
        private Node left, right;
        private char alphabet;
        private int frequency;

        public Node(char alphabet, int frequency){
            this.alphabet = alphabet;
            this.frequency = frequency;
            this.left = null;
            this.right = null;
        }

        public Node(int frequency, Node left, Node right){
            this.alphabet = ' ';
            this.frequency = frequency;
            this.left = left;
            this.right = right;
        }

        public Node(char alphabet, int frequency, Node left, Node right){
            this.alphabet = alphabet;
            this.frequency = frequency;
            this.left = left;
            this.right = right;
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }

        public char getAlphabet() {
            return alphabet;
        }

        public int getFrequency() {
            return frequency;
        }
    }
}
