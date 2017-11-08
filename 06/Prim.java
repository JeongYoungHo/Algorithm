package hw06;

public class Prim {
    private final int MAX_VALUE = Integer.MAX_VALUE;
    private int[][] adjacencyMatrix ;
    private int key[];
    private int parent[];
    private boolean visited[];

    public Prim(){
        this.adjacencyMatrix = new int[][] {
                { 0,  4, 0,  0,  0,  0, 0,   8,  0 },
                { 4,  0, 8,  0,  0,  0, 0,  11,  0 },
                { 0,  8, 0,  7,  0,  4, 0,   0,  2 },
                { 0,  0, 7,  0,  9, 14, 0,   0,  0 },
                { 0,  0, 0,  9,  0, 10, 0,   0,  0 },
                { 0,  0, 4, 14, 10,  0, 2,   0,  0 },
                { 0,  0, 0,  0,  0,  2, 0,   1,  6 },
                { 8, 11, 0,  0,  0,  0, 1,   0,  7 },
                { 0,  0, 2,  0,  0,  0, 6,   7,  0 }
        };
    }

    public void prim(){
        this.key = new int[9];
        this.parent = new int[9];
        this.visited = new boolean[9];
        for(int i = 0; i < key.length; i++){
            this.key[i] = MAX_VALUE;
            this.parent[i] = -1;
        }
        this.key[0] = 0;
        PriorityQueue q = new PriorityQueue();
        q.insert(new Node(0, 0));

        int MST = 0;

        while(q.getQueueSize() != 0){
            Node minNode = q.extractMin();
            int vertex = minNode.getVertex();
            if(visited[vertex]) continue; // 이미 방문된 정점에 대해서는 인접한 정점들의 최소거리 중복되어 찾지 않도록 방지.
            visited[vertex] = true;
            MST += minNode.getDist();

            for(int i = 0; i<this.adjacencyMatrix[vertex].length; i++){
                if(this.visited[i] == false && this.adjacencyMatrix[vertex][i] != 0 && this.adjacencyMatrix[vertex][i] < key[i]){
                    key[i] = this.adjacencyMatrix[vertex][i];
                    parent[i] = vertex;
                    q.insert(new Node(i, key[i]));
                }
            }
            System.out.println("w<" + (parent[vertex] == -1 ? " " : (char)(parent[vertex] + 97)) +"," + (char)(vertex + 97) +"> = " + minNode.getDist());
        }

        System.out.println("w<MST> = " + MST);
    }

    public void printPrim(){
        for(int i = 0, j = 0; i < this.parent.length; i++){
            System.out.println("w<" + j +"," + parent[j] +"> = " + key[parent[j]]);
            j = parent[j];
        }
    }


    private class PriorityQueue{

        private Node[] heap;
        private int heapSize;

        public PriorityQueue(){

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
            if(this.heapSize >= leftChildIdx && arr[leftChildIdx].dist < arr[i].dist){
                smallest = leftChildIdx;
            }else{
                smallest = i;
            }
            if(this.heapSize >= rightChildIdx && arr[rightChildIdx].dist < arr[smallest].dist){
                smallest = rightChildIdx;
            }
            if(smallest != i){
                swap(arr, smallest, i);
                minHeapify(arr, smallest);
            }
        }

        public Node getNode(int index){
            return this.heap[index+1];
        }

        public int getQueueSize(){
            return this.heapSize;
        }

        public void update(int vertex, int newDist){
            for(int i = 1 ; i <= this.heapSize; i++){
                if(this.heap[i].vertex == vertex){
                    this.heap[i].dist = newDist;
                    break;
                }
            }
            buildMinHeap(this.heap);
        }

        public Node extractMin(){
            Node minNode = heap[1];
            swap(heap, heapSize, 1);
            this.heapSize--;
            buildMinHeap(heap);
            return minNode;
        }


        public void insert(Node x){
            Node[] temp = new Node[this.heapSize + 2];
            for(int i = 1 ; i <= this.heapSize; i++){
                temp[i] = heap[i];
            }
            temp[temp.length - 1] = x;
            this.heap = temp;
            this.heapSize = temp.length - 1;
            if(this.heapSize != 1 && this.heap[this.heapSize/2].dist < this.heap[this.heapSize].dist) return;
            buildMinHeap(this.heap);
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

    }
    private class Node{
        private int dist;
        private int vertex;

        public Node(int vertex, int dist){
            this.vertex = vertex;
            this.dist = dist;
        }

        public int getDist() {
            return dist;
        }

        public int getVertex() {
            return vertex;
        }
    }
}
