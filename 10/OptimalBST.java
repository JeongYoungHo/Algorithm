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
            w[i][i-1] = q[i-1];
        }
        for(int l = 1; l <= n ; l++){ // root 결정 ? -> 커지면
            for(int i = 1; i <= n-l+1; i++){  // i 범위 줄어듦
                int j = i + l - 1;
                e[i][j] = Integer.MAX_VALUE;
                w[i][j] = w[i][j-1] + p[j] + q[j]; // w[i][j-1]은 p와 q 테이블에서 튀어나온부분
                for (int r = i; r<= j; r++){
                    double t = e[i][r-1] + e[r+1][j] + w[i][j];
                            if( t< e[i][j] ){
                                e[i][j] = t;
                                root[i][j] = r;
                            }
                }
            }
        }
    }

    public void print(){
        for(int i = 0; i<e[0].length; i++){
            for(int j=0; j<e.length; j++){
                System.out.printf("%3.3f   ",e[i][j]);
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
