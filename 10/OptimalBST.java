package hw10;

import java.io.*;
import java.util.StringTokenizer;

public class OptimalBST {
        private double[] p = null;
        private double[] q = null;
        private int lineCount = 0;
        private double[][] e = null;
        private double[][] w = null;
        private int[][] root = null;

    public OptimalBST(String filePath){
        try {
            lineCount = countLines(filePath) + 1;
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if(lineCount != 0){
                p = new double[lineCount];
                q = new double[lineCount];
                e = new double[lineCount + 1][lineCount + 1];
                w = new double[lineCount + 1][lineCount + 1];
                root = new int[lineCount][lineCount];
            }
        }
        String data = readFile(filePath);
        StringTokenizer str = new StringTokenizer(data, "\t|\n");
        for(int i = 0; str.hasMoreTokens(); i++){
            p[i] = Double.parseDouble(str.nextToken());
            q[i] = Double.parseDouble(str.nextToken());
        }
    }

    public void doOptimalBST(){
        optimalBST(p, q, lineCount - 1);
    }

    private void optimalBST(double[] p, double q[], int n){
        for(int i = 1; i <= n+1 ; i++){
            e[i][i-1] = q[i-1];
            w[i][i-1] = q[i-1]; //튀어나온 부분으로 초기화됨, w[i][i-1]에(정의) 의해 q[i-1]만 남음.
        }
        for(int l = 1; l <= n ; l++){ // l값으로 j값 조정(루트 가능개수)
            for(int i = 1; i <= n-l+1; i++){  // i 값 행 조정
                int j = i + l - 1; // j 값 열 조정
                e[i][j] = Integer.MAX_VALUE;
                w[i][j] = w[i][j-1] + p[j] + q[j]; // w[i][j-1]은 p와 q 테이블에서 튀어나온부분
                for (int r = i; r<= j; r++){ // i와 j를 정해두고 r 조정
                    double t = e[i][r-1] + e[r+1][j] + w[i][j]; // r을 루트로 삼았을 때의 값 계산
                            if( t< e[i][j] ){ // 무한대로 초기화한 위치 업데이트, 제한된 키 범위 안에서 루트가 바뀔때의 값들이 최소가 될때 업데이트 하게 된다.
                                e[i][j] = t;
                                root[i][j] = r; // 부모 업데이트
                            }
                }
            }
        }
    }

    public void printSearchCost(){
        for(int i = 0; i<e[0].length; i++){
            for(int j=0; j<e.length; j++){
                System.out.printf("%3.3f   ",e[i][j]);
            }
            System.out.println();
        }
        System.out.println("최적해 : " + e[1][lineCount - 1]);
    }

    // 키는 1~5까지만 있으므로 1부터 출력
    public void printParent(){
        for(int i = 1; i<root[0].length; i++){
            for(int j = 1; j<root.length; j++){
                System.out.printf("%3d   ",root[i][j]);
            }
            System.out.println();
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

    private int countLines(String filename) throws IOException {
        BufferedInputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            is.close();
        }
    }
}
