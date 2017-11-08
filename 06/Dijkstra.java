package hw06;

public class Dijkstra {
    private final int MAX_VALUE = Integer.MAX_VALUE;
    private int[][] adjacencyMatrix ;
    private int dist[];

    public Dijkstra(){
        this.adjacencyMatrix = new int[][] {
                {         0,        10,         3, MAX_VALUE, MAX_VALUE },
                { MAX_VALUE,         0,         1,         2, MAX_VALUE },
                { MAX_VALUE,         4,         0,         8,         2 },
                { MAX_VALUE, MAX_VALUE, MAX_VALUE,         0,         7 },
                { MAX_VALUE, MAX_VALUE, MAX_VALUE,         9,         0 }
        };
    }

    public void dijkstra(){
        this.dist = new int[5];
        for(int i = 0; i < 5; i++){
            this.dist[i] = MAX_VALUE;
        }
        this.dist[0] = 0;
        int count = 0;
        PriorityQueue q = new PriorityQueue();
        q.insert(new Node(0,0));
        for(int i = 0; i < dist.length - 1 ; i++){
            q.insert(new Node(i+1, MAX_VALUE));
        }
        while(q.getQueueSize() != 0){
            Node minNode = q.extractMin();
            int vertex = minNode.getVertex();

            System.out.println("===========================================================");
            System.out.println("S["+ count +"] : d[" + (char)(vertex + 65) +"] = "+ dist[vertex]);
            System.out.println("-----------------------------------------------------------");

            for(int i = 0; i < q.getQueueSize() ; i++){
                int adjVertex = q.getNode(i).getVertex();
                System.out.print("Q[" + i + "] : d[" + (char)(adjVertex + 65) + "] = " + dist[adjVertex]);
                if(this.adjacencyMatrix[vertex][adjVertex] != MAX_VALUE && dist[adjVertex] > dist[vertex] + this.adjacencyMatrix[vertex][adjVertex]){
                    dist[adjVertex] = dist[vertex] + this.adjacencyMatrix[vertex][adjVertex];
                    System.out.println(" => d[" + (char)(adjVertex + 65) + "] = " + dist[adjVertex]);
                    q.update(adjVertex, dist[adjVertex]);
                    continue;
                }
                System.out.println();
            }
            System.out.println();
//            System.out.println("===========================================================");
            count++;
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
